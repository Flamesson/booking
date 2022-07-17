package org.izumi.jmix.booking.component;

import java.util.Collection;
import java.util.function.Consumer;

import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.metamodel.model.MetaProperty;

/**
 * Provides operation to load entities or check loaded attributes of entities.
 */
public interface EntityLoader {

    /**
     * <p>Same as {@link #reloadIfNecessary(Object, Class, Consumer)}, but a {@link FetchPlan} is used
     *     instead of the fetch plan builder.</p>
     */
    <E> E reloadIfNecessary(final E entity, final FetchPlan plan);

    /**
     * <p>Checks are the given attributes loaded to an entity. In case they are not loaded - will load them.</p>
     *
     * <p>Extra attributes may be lost in case they were not specified in given {@link Consumer} builderConsumer
     *     and overloading of missing attributes happened.</p>
     *
     * @param entity Entity to check.
     * @param entityClass The class of the entity.
     * @param builderConsumer Object, which contains required attributes.
     * @return entity with loaded attributes.
     *     In case there were no need to reload anything - output and input references to entities will be the same.
     *     In case the reloading happened - output and input object entities references will be different.
     */
    <E> E reloadIfNecessary(final E entity,
                            final Class<E> entityClass,
                            final Consumer<FetchPlanBuilder> builderConsumer);

    /**
     * <p>Same as {@link #reloadIfNecessary(Object, Class, Consumer)},
     *     but class will be taken from the given entity object.</p>
     */
    <E> E reloadIfNecessary(final E entity, final Consumer<FetchPlanBuilder> builderConsumer);

    /**
     * <p>Same as {@link #reloadIfNecessary(Object, Class, Consumer)}, but a collection of {@link MetaProperty} is used
     *     instead of the fetch plan builder.</p>
     */
    <E> E reloadIfNecessary(final E entity, final Collection<MetaProperty> properties);

    /**
     * <p>Same as {@link #reloadIfNecessary(Object, Class, Consumer)},
     *     but a name of a {@link FetchPlan} is used instead of the fetch plan builder.</p>
     */
    <E> E reloadIfNecessary(final E entity, final String fetchPlanName);

    /**
     * <p>Same as {@link #reloadIfNecessary(Object, FetchPlan)}, but for a collection of entities and
     *     a fetch plan builder is used instead of a {@link FetchPlan}.</p>
     */
    <E> Collection<E> reloadIfNecessary(final Collection<E> entities, final Consumer<FetchPlanBuilder> builderConsumer);

    /**
     * <p>Same as {@link #reloadIfNecessary(Object, FetchPlan)}, but for a collection of entities and
     *     a collection of {@link MetaProperty} is used instead of a {@link FetchPlan}.</p>
     */
    <E> Collection<E> reloadIfNecessary(final Collection<E> entities, final Collection<MetaProperty> properties);

    /**
     * <p>Same as {@link #reloadIfNecessary(Object, String)}, but for a collection of entities.</p>
     */
    <E> Collection<E> reloadIfNecessary(final Collection<E> entities, final String fetchPlanName);

    /**
     * <p>Checks are given attributes given for an entity. In case they are not loaded - loads them.</p>
     *
     * <p>Previous loaded attributes will stay loaded.</p>
     *
     * @param entity Entity to check.
     * @param builderConsumer Object, which contains required attributes.
     * @return entity with loaded attributes.
     *     In case there were no need to reload anything - output and input references to entities will be the same.
     *     In case the reloading happened - output and input object entities references will be different.
     */
    <E> E reloadIfNecessaryAppend(final E entity, final Consumer<FetchPlanBuilder> builderConsumer);

    /**
     * <p>Checks are given attributes loaded in an entity.</p>
     *
     * @param entity Entity to check.
     * @param builderConsumer Object, which contains required attributes.
     * @return true - in case required attributes are loaded, otherwise - false.
     */
    <E> boolean isLoadedWith(final E entity, final Consumer<FetchPlanBuilder> builderConsumer);

    /**
     * <p>Same as {@link #isLoadedWith(Object, Consumer)}, but for a collection of entities.
     *     If any of the entities is not loaded with required attributes - false will be returned.</p>
     */
    <E> boolean areLoadedWith(final Collection<E> entities, final Consumer<FetchPlanBuilder> builderConsumer);

    /**
     * <p>Same as {@link #reload(Object, FetchPlan)}, but for a collection of entities and
     *     a fetch plan builder is used instead of a {@link FetchPlan}.</p>
     */
    <E> Collection<E> reload(final Collection<E> collection, final Consumer<FetchPlanBuilder> builderConsumer);

    /**
     * <p>Same as {@link #reload(Object, FetchPlan)},
     *     but a name of {@link FetchPlan} is used instead of a fetch plan.</p>
     */
    <E> E reload(final E entity, final String fetchPlanName);

    /**
     * <p>Reloads given entity with given fetch plan builder.</p>
     *
     * <p>In case any entity was not found in storage by identifier - it will be ignored.</p>
     *
     * @param entity Entity to reload.
     * @param plan Object, which contains required attributes.
     * @return entity with required attributes.
     */
    <E> E reload(final E entity, final FetchPlan plan);

    /**
     * <p>Same as {@link #reload(Object, FetchPlan)}, but a fetch plan builder is used instead of
     *     a {@link FetchPlan}.</p>
     */
    <E> E reload(final E entity, final Consumer<FetchPlanBuilder> builderConsumer);
}
