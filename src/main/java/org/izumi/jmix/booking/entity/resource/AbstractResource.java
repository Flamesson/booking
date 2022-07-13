package org.izumi.jmix.booking.entity.resource;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import lombok.Getter;
import lombok.Setter;
import org.izumi.jmix.booking.entity.Office;
import org.izumi.jmix.booking.entity.StandardEntity;

@Setter
@Getter
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
@JmixEntity
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "ABSTRACT_RESOURCE")
public abstract class AbstractResource extends StandardEntity {

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JmixProperty
    public abstract Office getBelonging();
}
