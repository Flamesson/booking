package org.izumi.jmix.booking.ui.screen.booking;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.Booking;

@UiController("Booking.edit")
@UiDescriptor("booking-edit.xml")
@EditedEntityContainer("bookingDc")
public class BookingEdit extends StandardEditor<Booking> {
}
