package org.izumi.jmix.booking.entity.api;

import javax.annotation.Nullable;
import java.time.chrono.ChronoLocalDateTime;

public interface HasChronoLocalDateTimePeriod {

    @Nullable
    ChronoLocalDateTime<?> getStart();

    @Nullable
    ChronoLocalDateTime<?> getEnd();
}
