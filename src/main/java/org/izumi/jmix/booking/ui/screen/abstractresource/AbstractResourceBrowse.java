package org.izumi.jmix.booking.ui.screen.abstractresource;

import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.component.Entities;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.resource.AbstractResource;
import org.izumi.jmix.booking.entity.resource.Room;
import org.izumi.jmix.booking.entity.resource.Thing;
import org.izumi.jmix.booking.utils.Collections;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("AbstractResource.browse")
@UiDescriptor("abstract-resource-browse.xml")
public class AbstractResourceBrowse extends Screen {

    @Autowired
    private Entities entities;

    @Autowired
    private CollectionContainer<AbstractResource> abstractResourcesDc;
    @Autowired
    private CollectionLoader<AbstractResource> abstractResourcesDl;
    @Autowired
    private CollectionContainer<Room> roomsDc;
    @Autowired
    private CollectionLoader<Room> roomsDl;
    @Autowired
    private CollectionContainer<Thing> thingsDc;
    @Autowired
    private CollectionLoader<Thing> thingsDl;
    @Autowired
    private CollectionLoader<Booking> relatedBookingsDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        roomsDl.load();
        thingsDl.load();

        final var resources = Collections.join(roomsDc.getItems(), thingsDc.getItems());
        abstractResourcesDc.setItems(resources);
    }

    @Subscribe(id = "abstractResourcesDc", target = Target.DATA_CONTAINER)
    public void onAbstractResourcesDcCollectionChange(CollectionContainer.CollectionChangeEvent<AbstractResource> ev) {
        final var resources = ev.getSource().getItems();
        final var ids = entities.getStandardIds(resources);
        abstractResourcesDl.setParameter("ids", ids); //TODO may be used in the filter
        relatedBookingsDl.setParameter("ids", ids);
        relatedBookingsDl.load();
    }

    @Subscribe(id = "roomsDc", target = Target.DATA_CONTAINER)
    public void onRoomsDcCollectionChange(CollectionContainer.CollectionChangeEvent<Room> event) {
        switch (event.getChangeType()) {
            case ADD_ITEMS -> abstractResourcesDc.getMutableItems().addAll(0, event.getChanges());
            case SET_ITEM, REFRESH -> {
                final var resources = Collections.join(roomsDc.getItems(), thingsDc.getItems());
                abstractResourcesDc.setItems(resources);
            }
            case REMOVE_ITEMS -> abstractResourcesDc.getMutableItems().removeAll(event.getChanges());
        }
    }

    @Subscribe(id = "thingsDc", target = Target.DATA_CONTAINER)
    public void onThingsDcCollectionChange(CollectionContainer.CollectionChangeEvent<Thing> event) {
        switch (event.getChangeType()) {
            case ADD_ITEMS -> abstractResourcesDc.getMutableItems().addAll(0, event.getChanges());
            case SET_ITEM, REFRESH -> {
                final var resources = Collections.join(roomsDc.getItems(), thingsDc.getItems());
                abstractResourcesDc.setItems(resources);
            }
            case REMOVE_ITEMS -> abstractResourcesDc.getMutableItems().removeAll(event.getChanges());
        }
    }
}
