package org.izumi.jmix.booking.ui.screen.booking;

import javax.inject.Named;

import java.util.stream.Stream;

import io.jmix.ui.action.list.EditAction;
import io.jmix.ui.action.list.RemoveAction;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.component.UserSource;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Booking.browse")
@UiDescriptor("booking-browse.xml")
@LookupComponent("bookingsTable")
public class BookingBrowse extends StandardLookup<Booking> {

    @Autowired
    private UserSource userSource;

    @Named("bookingsTable.edit")
    private EditAction<Booking> bookingsTableEdit;
    @Named("bookingsTable.remove")
    private RemoveAction<Booking> bookingsTableRemove;

    @Autowired
    private GroupTable<Booking> bookingsTable;

    private User effective;

    @Subscribe
    private void onBeforeShow(final BeforeShowEvent event) {
        this.effective = userSource.getEffective();

        Stream.of(bookingsTableEdit, bookingsTableRemove)
                .forEach(action -> action.addEnabledRule(this::isSelectedBookingManagedByEffectiveUser));
    }

    private boolean isSelectedBookingManagedByEffectiveUser() {
        final var selected = bookingsTable.getSelected();
        if (selected.size() != 1) {
            return false;
        }

        final var booking = selected.iterator().next();
        return effective.equals(booking.getEmployee().getUser());
    }
}
