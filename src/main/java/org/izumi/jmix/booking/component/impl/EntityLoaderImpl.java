package org.izumi.jmix.booking.component.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.FetchPlanRepository;
import io.jmix.core.FetchPlans;
import io.jmix.core.Id;
import io.jmix.core.metamodel.model.MetaProperty;
import org.izumi.jmix.booking.component.EntityLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityLoaderImpl implements EntityLoader {
    private static final Logger LOG = LoggerFactory.getLogger(EntityLoaderImpl.class);

    private final EntityStates entityStates;
    private final DataManager dataManager;
    private final FetchPlans plans;
    private final FetchPlanRepository fetchPlanRepository;

    @Autowired
    public EntityLoaderImpl(EntityStates entityStates,
                            DataManager dataManager,
                            FetchPlans plans,
                            FetchPlanRepository fetchPlanRepository) {
        this.entityStates = entityStates;
        this.dataManager = dataManager;
        this.plans = plans;
        this.fetchPlanRepository = fetchPlanRepository;
    }

    @Override
    public <E> E reloadIfNecessary(final E entity, final FetchPlan plan) {
        if (entityStates.isLoadedWithFetchPlan(entity, plan)) {
            return entity;
        }

        return dataManager.load(Id.of(entity)).fetchPlan(plan).one();
    }

    @Override
    public <E> E reloadIfNecessary(final E entity,
                                   final Class<E> entityClass,
                                   final Consumer<FetchPlanBuilder> builderConsumer) {
        final FetchPlanBuilder builder = plans.builder(entityClass);
        builderConsumer.accept(builder);
        final FetchPlan plan = builder.build();
        return reloadIfNecessary(entity, plan);
    }

    @Override
    public <E> E reloadIfNecessary(E entity, Consumer<FetchPlanBuilder> builderConsumer) {
        return reloadIfNecessary(entity, (Class<E>) entity.getClass(), builderConsumer);
    }

    @Override
    public <E> E reloadIfNecessary(E entity, Collection<MetaProperty> properties) {
        boolean loadCovers = true;
        for (MetaProperty property : properties) {
            if (!entityStates.isLoaded(entity, property.getName())) {
                loadCovers = false;
                break;
            }
        }

        if (loadCovers) {
            return entity;
        }

        Consumer<FetchPlanBuilder> configurer = builder -> properties.forEach(property -> {
            final var type = property.getType();
            if (type == MetaProperty.Type.ASSOCIATION || type == MetaProperty.Type.COMPOSITION) {
                builder.add(property.getName(), FetchPlan.INSTANCE_NAME);
            } else {
                builder.add(property.getName());
            }
        });

        return dataManager.load(Id.of(entity))
                .fetchPlan(configurer)
                .one();
    }

    @Override
    public <E> E reloadIfNecessary(E entity, String fetchPlanName) {
        final var plan = fetchPlanRepository.getFetchPlan(entity.getClass(), fetchPlanName);
        return reloadIfNecessary(entity, plan);
    }

    @Override
    public <E> Collection<E> reloadIfNecessary(Collection<E> entities, Consumer<FetchPlanBuilder> builderConsumer) {
        return entities.stream()
                .map(entity -> reloadIfNecessary(entity, builderConsumer))
                .collect(Collectors.toList());
    }

    @Override
    public <E> Collection<E> reloadIfNecessary(Collection<E> entities, Collection<MetaProperty> properties) {
        return entities.stream()
                .map(entity -> reloadIfNecessary(entity, properties))
                .collect(Collectors.toList());
    }

    @Override
    public <E> Collection<E> reloadIfNecessary(Collection<E> entities, String fetchPlanName) {
        return entities.stream()
                .map(entity -> reloadIfNecessary(entity, fetchPlanName))
                .collect(Collectors.toList());
    }

    @Override
    public <E> E reloadIfNecessaryAppend(E entity, Consumer<FetchPlanBuilder> builderConsumer) {
        if (isLoadedWith(entity, builderConsumer)) {
            return entity;
        }

        final var currentPlan = entityStates.getCurrentFetchPlan(entity);
        final FetchPlanBuilder builder = plans.builder(entity.getClass());
        builderConsumer.accept(builder);
        builder.addFetchPlan(currentPlan);
        final var plan = builder.build();

        return reloadIfNecessary(entity, plan);
    }

    @Override
    public <E> boolean isLoadedWith(E entity, Consumer<FetchPlanBuilder> builderConsumer) {
        return entityStates.isLoadedWithFetchPlan(entity, buildPlan(entity, builderConsumer));
    }

    @Override
    public <E> boolean areLoadedWith(Collection<E> entities, Consumer<FetchPlanBuilder> builderConsumer) {
        if (entities.isEmpty()) {
            return false;
        }

        final var any = entities.iterator().next();
        final var builder = plans.builder(any.getClass());
        builderConsumer.accept(builder);
        final var plan = builder.build();

        for (E entity : entities) {
            if (!entityStates.isLoadedWithFetchPlan(entity, plan)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public <E> Collection<E> reload(Collection<E> collection, Consumer<FetchPlanBuilder> builderConsumer) {
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }

        final var clazz = (Class<E>) collection.iterator().next().getClass();
        final var ids = collection.stream()
                .map(Id::of)
                .map(Id::getValue)
                .collect(Collectors.toList());

        final var reloaded = dataManager.load(clazz)
                .ids(ids)
                .fetchPlan(builderConsumer)
                .list();

        if (collection.size() != reloaded.size()) {
            LOG.warn("Reloaded less than given. Given: {}, reloaded: {}.", collection.size(), reloaded.size());
        }

        return reloaded;
    }

    @Override
    public <E> E reload(E entity, String fetchPlanName) {
        return dataManager.load(Id.of(entity))
                .fetchPlan(fetchPlanName)
                .one();
    }

    @Override
    public <E> E reload(E entity, FetchPlan plan) {
        return dataManager.load(Id.of(entity))
                .fetchPlan(plan)
                .one();
    }

    @Override
    public <E> E reload(E entity, Consumer<FetchPlanBuilder> builderConsumer) {
        return reload(entity, buildPlan(entity, builderConsumer));
    }

    private FetchPlan buildPlan(Object entity, Consumer<FetchPlanBuilder> builderConsumer) {
        return buildPlan(entity.getClass(), builderConsumer);
    }

    private FetchPlan buildPlan(Class<?> entityClass, Consumer<FetchPlanBuilder> builderConsumer) {
        final var builder = plans.builder(entityClass);
        builderConsumer.accept(builder);
        return builder.build();
    }
}
