package org.izumi.jmix.booking.ui.screen.thing;

import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.Office;
import org.izumi.jmix.booking.entity.resource.Thing;
import org.izumi.jmix.booking.repository.AbstractResourceRepository;
import org.izumi.jmix.booking.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Thing.edit")
@UiDescriptor("thing-edit.xml")
@EditedEntityContainer("thingDc")
public class ThingEdit extends StandardEditor<Thing> {

    @Autowired
    private AbstractResourceRepository abstractResourceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityPicker<Office> currentLocationField;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        currentLocationField.setEditable(isCurrentLocationEditable());
    }

    private boolean isCurrentLocationEditable() {
        final var thing = getEditedEntity();

        if (abstractResourceRepository.hasCurrentOrFutureBookings(thing)) {
            final var employeeOptional = employeeRepository.findByEffectiveUser();
            if (employeeOptional.isEmpty()) {
                return false;
            }

            return abstractResourceRepository.isBookedByEmployeeAtCurrentTime(thing, employeeOptional.get());
        }

        return true;
    }
}
