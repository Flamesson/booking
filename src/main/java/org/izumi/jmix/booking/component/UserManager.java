package org.izumi.jmix.booking.component;

import java.util.Locale;
import java.util.Optional;

import org.izumi.jmix.booking.entity.user.User;

public interface UserManager {
    Optional<Locale> getLocale(User user);
    void setLocale(User user, Locale locale);
}
