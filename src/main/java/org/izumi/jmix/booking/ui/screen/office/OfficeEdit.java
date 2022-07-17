package org.izumi.jmix.booking.ui.screen.office;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.Office;

@UiController("Office.edit")
@UiDescriptor("office-edit.xml")
@EditedEntityContainer("officeDc")
public class OfficeEdit extends StandardEditor<Office> {
}
