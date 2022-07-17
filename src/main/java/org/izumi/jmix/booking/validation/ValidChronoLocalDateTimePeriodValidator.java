package org.izumi.jmix.booking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import org.izumi.jmix.booking.entity.api.HasChronoLocalDateTimePeriod;

public class ValidChronoLocalDateTimePeriodValidator implements
        ConstraintValidator<ValidChronoLocalDateTimePeriod, HasChronoLocalDateTimePeriod> {

    @Override
    public boolean isValid(final HasChronoLocalDateTimePeriod value, final ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        final var start = value.getStart();
        final var end = value.getEnd();
        if (Objects.equals(start, end)) {
            return true;
        }

        if (start == null) {
            return false;
        }
        if (end == null) {
            return false;
        }

        return start.isBefore(end);
    }
}
