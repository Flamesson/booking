package org.izumi.jmix.booking.component.impl;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import io.jmix.core.CoreProperties;
import io.jmix.core.MessageTools;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.component.Locales;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocalesImpl implements Locales {
    private final CoreProperties coreProperties;
    private final MessageTools messageTools;

    @Override
    public Collection<Locale> getAll() {
        return coreProperties.getAvailableLocales();
    }

    @Override
    public Map<String, Locale> getAvailableLocalesMap() {
        return messageTools.getAvailableLocalesMap();
    }
}
