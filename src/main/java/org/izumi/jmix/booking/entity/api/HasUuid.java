package org.izumi.jmix.booking.entity.api;

import java.util.UUID;

public interface HasUuid {
    UUID getUuid();
    void setUuid(final UUID uuid);
}
