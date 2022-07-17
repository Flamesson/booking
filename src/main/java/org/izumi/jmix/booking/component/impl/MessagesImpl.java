package org.izumi.jmix.booking.component.impl;

import javax.annotation.Nonnull;

import java.util.Locale;
import java.util.Objects;

import org.izumi.jmix.booking.component.UserSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class MessagesImpl extends io.jmix.core.impl.MessagesImpl {
    private final UserSource userSource;

    public MessagesImpl(@Lazy final UserSource userSource) {
        this.userSource = userSource;
    }

    @Nonnull
    @Override
    public String getMessage(@Nonnull final String key) {
        return getMessage(key, getUserLocale());
    }

    @Nonnull
    @Override
    public String getMessage(@Nonnull final Class caller, @Nonnull final String key) {
        return getMessage(caller, key, getUserLocale());
    }

    @Nonnull
    @Override
    public String getMessage(@Nonnull final Enum caller) {
        return getMessage(caller, getUserLocale());
    }

    @Nonnull
    @Override
    public String getMessage(@Nonnull final String group, @Nonnull final String key) {
        return getMessage(group, key, getUserLocale());
    }

    @Nonnull
    @Override
    protected Locale getUserLocale() {
        final var user = userSource.getAuthenticated();
        final var languageTag = user.getLanguageTag();

        if (Objects.isNull(languageTag)) {
            return super.getUserLocale();
        }

        return Locale.forLanguageTag(languageTag);
    }

    @Nonnull
    @Override
    protected Locale getDefaultLocale() {
        return super.getDefaultLocale();
    }
}
