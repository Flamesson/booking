package org.izumi.jmix.booking.ui.screen.office;

import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.entity.Office;

@UiController("Office.browse")
@UiDescriptor("office-browse.xml")
@LookupComponent("officesTable")
public class OfficeBrowse extends StandardLookup<Office> {
}
