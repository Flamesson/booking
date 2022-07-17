package org.izumi.jmix.booking.repository.api;

import java.util.UUID;

import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.metamodel.model.MetaClass;
import org.izumi.jmix.booking.entity.StandardEntity;

public abstract class AbstractStandardRepository<T extends StandardEntity> implements StandardRepository<T> {
    protected final DataManager dataManager;
    private final MetaClass metaClass;

    public AbstractStandardRepository(final Class<T> clazz,
                                      final DataManager dataManager,
                                      final Metadata metadata) {
        this.metaClass = metadata.getClass(clazz);
        this.dataManager = dataManager;
    }

    @Override
    public T save(final T t) {
        return dataManager.save(t);
    }

    @Override
    public boolean existsById(final UUID id) {
        return dataManager.load(metaClass.getJavaClass())
                .query("SELECT e FROM " + metaClass.getName() + " e " +
                        "WHERE e.id = :id")
                .parameter("id", id)
                .fetchPlan(builder -> builder.add("id"))
                .optional()
                .isPresent();
    }
}
