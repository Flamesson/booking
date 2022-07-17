package org.izumi.jmix.booking.ui.screen.room;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.resource.Room;

@UiController("Room.edit")
@UiDescriptor("room-edit.xml")
@EditedEntityContainer("roomDc")
public class RoomEdit extends StandardEditor<Room> {
}
