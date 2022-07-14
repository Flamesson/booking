package org.izumi.jmix.booking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import lombok.Getter;
import lombok.Setter;
import org.izumi.jmix.booking.entity.resource.AbstractResource;
import org.izumi.jmix.booking.entity.user.Employee;
import org.izumi.jmix.booking.validation.ValidChronoLocalDateTimePeriod;

@Setter
@Getter
@ValidChronoLocalDateTimePeriod(
        groups = UiCrossFieldChecks.class,
        message = "{msg:///validation.period-is-not-valid.message}"
)
@JmixEntity
@Table(name = "BOOKING")
@Entity
public class Booking extends StandardEntity {

    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "RESOURCE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AbstractResource resource;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Employee employee;

    @Column(name = "START_", nullable = false)
    @NotNull
    private LocalDateTime st;

    @Column(name = "END_", nullable = false)
    @NotNull
    private LocalDateTime en;
}
