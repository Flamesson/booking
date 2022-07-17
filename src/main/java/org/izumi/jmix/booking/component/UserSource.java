package org.izumi.jmix.booking.component;

import java.util.Optional;

import org.izumi.jmix.booking.entity.user.User;

public interface UserSource {
    User getAuthenticated();
    Optional<User> getSubstituted();
    User getEffective();

    boolean hasSubstitution();
    default boolean doesNotHaveSubstitution() {
        return !hasSubstitution();
    }
}
