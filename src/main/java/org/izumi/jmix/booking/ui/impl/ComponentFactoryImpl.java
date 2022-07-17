package org.izumi.jmix.booking.ui.impl;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Label;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.ui.ComponentFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ComponentFactoryImpl implements ComponentFactory {
    private final UiComponents uiComponents;

    @Override
    public Label<String> createLabelOf(final String value) {
        final Label<String> label = uiComponents.create(Label.NAME);
        label.setValue(value);
        return label;
    }

    @Override
    public Label<String> createEmptyLabel() {
        return uiComponents.create(Label.NAME);
    }
}
