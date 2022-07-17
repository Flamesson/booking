package org.izumi.jmix.booking.component;

import java.util.Locale;
import java.util.Optional;

public interface CurrentUserManager {
    Optional<Locale> getLocale();
    void setLocale(Locale locale);
}
