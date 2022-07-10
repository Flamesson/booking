package org.izumi.jmix.booking.entity.resource;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
@DiscriminatorValue("Room")
@JmixEntity
@Table(name = "ROOM")
@Entity
public class Room extends AbstractResource {

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "OFFICE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Office office;

    @Column(name = "HAS_PROJECTOR", nullable = false)
    @NotNull
    private Boolean hasProjector = false;

    @Column(name = "HAS_BOARD", nullable = false)
    @NotNull
    private Boolean hasBoard = false;

    @Max(512)
    @Positive
    @Column(name = "CAPACITY", nullable = false)
    @NotNull
    private Integer capacity;

    @DependsOnProperties("office")
    @JmixProperty
    @Override
    public Office getBelonging() {
        return office;
    }

    @InstanceName
    @Override
    public String getName() {
        return super.getName();
    }
}
