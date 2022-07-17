package org.izumi.jmix.booking.ui;

import io.jmix.ui.component.Label;

public interface ComponentFactory {
    Label<String> createLabelOf(String value);
    Label<String> createEmptyLabel();
}
