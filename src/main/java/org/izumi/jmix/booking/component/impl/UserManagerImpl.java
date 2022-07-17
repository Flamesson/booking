package org.izumi.jmix.booking.component.impl;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import io.jmix.core.DataManager;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.component.EntityLoader;
import org.izumi.jmix.booking.component.UserManager;
import org.izumi.jmix.booking.entity.user.User;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserManagerImpl implements UserManager {
    private final DataManager dataManager;
    private final EntityLoader entityLoader;

    @Override
    public Optional<Locale> getLocale(final User user) {
        final var optional = dataManager.load(User.class)
                .id(user)
                .fetchPlan(builder -> builder.add("languageTag"))
                .optional();

        if (optional.isEmpty()) {
            return Optional.empty();
        }

        final var languageTag = optional.get().getLanguageTag();
        if (Objects.isNull(languageTag)) {
            return Optional.empty();
        }

        final var locale = Locale.forLanguageTag(languageTag);
        return Optional.of(locale);
    }

    @Override
    public void setLocale(final User user, final Locale locale) {
        final var languageTag= locale.toLanguageTag();
        user.setLanguageTag(languageTag);

        final var reloaded = entityLoader.reload(user, builder -> builder.add("languageTag"));
        reloaded.setLanguageTag(languageTag);
        dataManager.save(reloaded);
    }
}
