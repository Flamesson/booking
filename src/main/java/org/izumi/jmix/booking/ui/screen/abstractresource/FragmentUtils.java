package org.izumi.jmix.booking.ui.screen.abstractresource;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import io.jmix.ui.model.CollectionContainer;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.resource.AbstractResource;
import org.izumi.jmix.booking.ui.ComponentFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class FragmentUtils {
    public static final String BOOKED_TABLE_ROW_STYLE_NAME = "booked-table-row";

    private final ComponentFactory componentFactory;

    public boolean isBooked(AbstractResource resource, Collection<Booking> bookings) {
        return findActualBooking(resource, bookings).isPresent();
    }

    public Optional<Booking> findActualBooking(AbstractResource resource, Collection<Booking> bookings) {
        final var now = LocalDateTime.now();
        for (Booking booking : bookings) {
            if (resource.equals(booking.getResource())
                    && booking.getPeriodStart().isBefore(now)
                    && booking.getPeriodEnd().isAfter(now)) {

                return Optional.of(booking);
            }
        }

        return Optional.empty();
    }

    public io.jmix.ui.component.Component generateBookedByColumn(AbstractResource resource,
                                                                 CollectionContainer<Booking> relatedBookingsDc) {
        final var bookings = relatedBookingsDc.getItems();
        final var optionalRelatedBooking = findActualBooking(resource, bookings);

        if (optionalRelatedBooking.isEmpty()) {
            return componentFactory.createEmptyLabel();
        }

        final var relatedBooking = optionalRelatedBooking.get();

        return componentFactory.createLabelOf(relatedBooking.getEmployee().getInstanceName());
    }
}
