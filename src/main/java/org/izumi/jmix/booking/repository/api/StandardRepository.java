package org.izumi.jmix.booking.repository.api;

import java.util.UUID;

import org.izumi.jmix.booking.entity.StandardEntity;

public interface StandardRepository<T extends StandardEntity> {
    T save(T t);
    boolean existsById(UUID id);
}
