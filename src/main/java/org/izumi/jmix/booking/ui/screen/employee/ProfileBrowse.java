package org.izumi.jmix.booking.ui.screen.employee;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.user.Employee;

@UiController("Profile.browse")
@UiDescriptor("profile-browse.xml")
@EditedEntityContainer("employeeDc")
public class ProfileBrowse extends StandardEditor<Employee> {
}
