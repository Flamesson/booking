package org.izumi.jmix.booking.ui.screen.room;

import io.jmix.ui.component.Component;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.resource.Room;
import org.izumi.jmix.booking.ui.screen.abstractresource.FragmentUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("RoomsFragment")
@UiDescriptor("rooms-fragment.xml")
public class RoomsFragment extends ScreenFragment {

    @Autowired
    private FragmentUtils fragmentUtils;

    @Autowired
    private CollectionContainer<Booking> relatedBookingsDc;

    @Install(to = "roomsTable", subject = "styleProvider")
    private String roomsTableStyleProvider(final Room entity, @SuppressWarnings("unused") final String property) {
        if (fragmentUtils.isBooked(entity, relatedBookingsDc.getItems())) {
            return FragmentUtils.BOOKED_TABLE_ROW_STYLE_NAME;
        }

        return null;
    }

    @Install(to = "roomsTable.bookedBy", subject = "columnGenerator")
    private Component roomsTableBookedByColumnGenerator(final Room room) {
        return fragmentUtils.generateBookedByColumn(room, relatedBookingsDc);
    }
}
