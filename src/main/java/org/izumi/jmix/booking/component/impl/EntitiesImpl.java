package org.izumi.jmix.booking.component.impl;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import io.jmix.core.entity.EntityValues;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.component.Entities;
import org.izumi.jmix.booking.entity.StandardEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EntitiesImpl implements Entities {

    @Override
    public <I> Collection<I> getCastedIds(final Collection<Object> entities) {
        return entities.stream().map(entity -> (I) EntityValues.getId(entity)).collect(Collectors.toList());
    }

    @Override
    public Collection<Object> getIds(final Collection<Object> entities) {
        return entities.stream().map(EntityValues::getId).collect(Collectors.toList());
    }

    @Override
    public Collection<UUID> getStandardIds(final Collection<? extends StandardEntity> entities) {
        return entities.stream().map(StandardEntity::getId).collect(Collectors.toList());
    }
}
