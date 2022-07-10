package org.izumi.jmix.booking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JmixEntity
@Table(name = "OFFICE")
@Entity
public class Office extends StandardEntity {

    @NotBlank
    @NotNull
    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @InstanceName
    public String getInstanceName() {
        if (name != null) {
            return name;
        }

        return address;
    }
}
