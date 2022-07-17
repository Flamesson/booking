package org.izumi.jmix.booking.component.impl;

import java.util.Optional;

import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.component.UserSource;
import org.izumi.jmix.booking.entity.user.User;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSourceImpl implements UserSource {
    private final CurrentUserSubstitution currentUserSubstitution;

    @Override
    public User getAuthenticated() {
        return (User) currentUserSubstitution.getAuthenticatedUser();
    }

    @Override
    public Optional<User> getSubstituted() {
        return Optional.ofNullable((User) currentUserSubstitution.getSubstitutedUser());
    }

    @Override
    public User getEffective() {
        return (User) currentUserSubstitution.getEffectiveUser();
    }

    @Override
    public boolean hasSubstitution() {
        return currentUserSubstitution.getSubstitutedUser() != null;
    }
}
