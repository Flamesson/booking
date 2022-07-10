package org.izumi.jmix.booking.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotation to validate time period.</p>
 *
 * <p>Start must be earlier than end (or equals to).</p>
 *
 * <p>In case the object of the validation - period is invalid.</p>
 * <p>In case start and end are null - period is valid.</p>
 * <p>In case the start is null and the end is not null
 *     or the start is not null and the end is null - period is invalid.</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidChronoLocalDateTimePeriodValidator.class)
public @interface ValidChronoLocalDateTimePeriod {

    String message() default "Period must be valid. Start be before than (or equals to) end";

    /**
     * <p>Manage groups which contain this constraint. Groups may be used for granular validation.
     *     A group may be a marker interface.</p>
     *
     * @return An array of groups which contain this constraint.
     *
     * @see io.jmix.core.validation.group.UiCrossFieldChecks
     * @see io.jmix.core.validation.group.UiComponentChecks
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
