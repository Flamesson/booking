package org.izumi.jmix.booking.component;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public interface Locales {
    Collection<Locale> getAll();
    Map<String, Locale> getAvailableLocalesMap();
}
