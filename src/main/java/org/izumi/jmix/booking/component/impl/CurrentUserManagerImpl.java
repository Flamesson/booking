package org.izumi.jmix.booking.component.impl;

import java.util.Locale;
import java.util.Optional;

import io.jmix.core.security.CurrentAuthentication;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.component.CurrentUserManager;
import org.izumi.jmix.booking.component.UserManager;
import org.izumi.jmix.booking.component.UserSource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CurrentUserManagerImpl implements CurrentUserManager {
    private final UserSource userSource;
    private final UserManager userManager;
    private final CurrentAuthentication currentAuthentication;

    @Override
    public Optional<Locale> getLocale() {
        final var user = userSource.getAuthenticated();
        final var optional = userManager.getLocale(user);
        if (optional.isPresent()) {
            return optional;
        }

        return Optional.of(currentAuthentication.getLocale());
    }

    @Override
    public void setLocale(final Locale locale) {
        final var user = userSource.getAuthenticated();
        userManager.setLocale(user, locale);
    }
}
