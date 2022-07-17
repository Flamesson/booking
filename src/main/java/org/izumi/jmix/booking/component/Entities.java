package org.izumi.jmix.booking.component;

import java.util.Collection;
import java.util.UUID;

import org.izumi.jmix.booking.entity.StandardEntity;

public interface Entities {
    <I> Collection<I> getCastedIds(Collection<Object> entities);
    Collection<Object> getIds(Collection<Object> entities);
    Collection<UUID> getStandardIds(Collection<? extends StandardEntity> entities);
}
