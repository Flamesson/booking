package org.izumi.jmix.booking.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Collections {

    public static <T> Collection<T> join(final Collection<? extends T>... collections) {
        if (collections.length == 0) {
            return java.util.Collections.emptyList();
        }

        final var size = Arrays.stream(collections)
                .map(Collection::size)
                .reduce(0, Integer::sum);

        final Collection<T> result = new ArrayList<>(size);
        for (Collection<? extends T> collection : collections) {
            result.addAll(collection);
        }

        return result;
    }

    public static <T> Optional<T> getLast(final Collection<T> collection) {
        if (collection.isEmpty()) {
            return Optional.empty();
        }

        final var iterator = collection.iterator();
        var element = iterator.next();

        while (iterator.hasNext()) {
            element = iterator.next();
        }

        return Optional.of(element);
    }
}
