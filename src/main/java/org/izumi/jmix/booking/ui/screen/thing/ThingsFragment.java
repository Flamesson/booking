package org.izumi.jmix.booking.ui.screen.thing;

import io.jmix.ui.component.Component;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.resource.Thing;
import org.izumi.jmix.booking.ui.screen.abstractresource.FragmentUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ThingsFragment")
@UiDescriptor("things-fragment.xml")
public class ThingsFragment extends ScreenFragment {

    @Autowired
    private FragmentUtils fragmentUtils;

    @Autowired
    private CollectionContainer<Booking> relatedBookingsDc;

    @Install(to = "thingsTable", subject = "styleProvider")
    private String thingsTableStyleProvider(Thing entity, @SuppressWarnings("unused") String property) {
        if (fragmentUtils.isBooked(entity, relatedBookingsDc.getItems())) {
            return FragmentUtils.BOOKED_TABLE_ROW_STYLE_NAME;
        }

        return null;
    }

    @Install(to = "thingsTable.bookedBy", subject = "columnGenerator")
    private Component thingsTableBookedByColumnGenerator(Thing thing) {
        return fragmentUtils.generateBookedByColumn(thing, relatedBookingsDc);
    }
}
