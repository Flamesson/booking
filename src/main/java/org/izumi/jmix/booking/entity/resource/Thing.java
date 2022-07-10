package org.izumi.jmix.booking.entity.resource;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import lombok.Getter;
import lombok.Setter;
import org.izumi.jmix.booking.entity.Office;

@Setter
@Getter
@DiscriminatorValue("Thing")
@JmixEntity
@Table(name = "THING")
@Entity
public class Thing extends AbstractResource {

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "CURRENT_LOCATION_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Office currentLocation;

    @Column(name = "DESCRIPTION", length = 1023)
    private String description;

    @DependsOnProperties({"currentLocation"})
    @JmixProperty
    @Override
    public Office getBelonging() {
        return currentLocation;
    }

    @InstanceName
    @Override
    public String getName() {
        return super.getName();
    }
}
