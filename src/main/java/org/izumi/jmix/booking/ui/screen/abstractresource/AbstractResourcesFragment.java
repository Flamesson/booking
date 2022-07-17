package org.izumi.jmix.booking.ui.screen.abstractresource;

import java.util.Objects;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.OpenMode;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.resource.AbstractResource;
import org.izumi.jmix.booking.entity.resource.Room;
import org.izumi.jmix.booking.entity.resource.Thing;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("AbstractResourcesFragment")
@UiDescriptor("abstract-resources-fragment.xml")
public class AbstractResourcesFragment extends ScreenFragment {

    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private FragmentUtils fragmentUtils;

    @Autowired
    private CollectionContainer<AbstractResource> abstractResourcesDc;
    @Autowired
    private CollectionContainer<Room> roomsDc;
    @Autowired
    private CollectionContainer<Thing> thingsDc;
    @Autowired
    private CollectionContainer<Booking> relatedBookingsDc;

    @Autowired
    private GroupTable<AbstractResource> abstractResourcesTable;

    @Subscribe("createBtn.createRoom")
    public void onCreateBtnCreateRoom(Action.ActionPerformedEvent event) {
        final var editor = screenBuilders.editor(Room.class, this)
                .newEntity()
                .withOpenMode(OpenMode.DIALOG)
                .build();
        editor.addAfterCloseListener(e -> {
            if (!Objects.equals(e.getCloseAction(), WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                return;
            }

            final var edited = ((StandardEditor<Room>) e.getSource()).getEditedEntity();
            abstractResourcesDc.getMutableItems().add(0, edited);
            roomsDc.getMutableItems().add(0, edited);
            abstractResourcesTable.setSelected(edited);
            abstractResourcesTable.focus();
        });
        editor.show();
    }

    @Subscribe("createBtn.createThing")
    public void onCreateBtnCreateThing(Action.ActionPerformedEvent event) {
        final var editor = screenBuilders.editor(Thing.class, this)
                .newEntity()
                .withOpenMode(OpenMode.DIALOG)
                .build();
        editor.addAfterCloseListener(e -> {
            if (!Objects.equals(e.getCloseAction(), WINDOW_COMMIT_AND_CLOSE_ACTION)) {
                return;
            }

            final var edited = ((StandardEditor<Thing>) e.getSource()).getEditedEntity();
            abstractResourcesDc.getMutableItems().add(0, edited);
            thingsDc.getMutableItems().add(0, edited);
            abstractResourcesTable.setSelected(edited);
            abstractResourcesTable.focus();
        });
        editor.show();
    }

    @Install(to = "abstractResourcesTable", subject = "styleProvider")
    private String abstractResourcesTableStyleProvider(AbstractResource entity,
                                                       @SuppressWarnings("unused") String property) {

        if (fragmentUtils.isBooked(entity, relatedBookingsDc.getItems())) {
            return FragmentUtils.BOOKED_TABLE_ROW_STYLE_NAME;
        }

        return null;
    }

    @Install(to = "abstractResourcesTable.bookedBy", subject = "columnGenerator")
    private Component abstractResourcesTableBookedByColumnGenerator(AbstractResource abstractResource) {
        return fragmentUtils.generateBookedByColumn(abstractResource, relatedBookingsDc);
    }
}
