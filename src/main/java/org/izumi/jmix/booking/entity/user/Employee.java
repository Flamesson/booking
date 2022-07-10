package org.izumi.jmix.booking.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;
import org.izumi.jmix.booking.entity.Office;
import org.izumi.jmix.booking.entity.StandardEntity;

@Setter
@Getter
@JmixEntity
@Table(name = "EMPLOYEE")
@Entity
public class Employee extends StandardEntity {

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.CASCADE)
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @JoinColumn(name = "OFFICE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Office office;

    @NotBlank
    @Column(name = "FIRST_NAME", nullable = false)
    @NotNull
    private String firstName;

    @NotBlank
    @Column(name = "LAST_NAME", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "POSITION_")
    private String position;

    @InstanceName
    public String getInstanceName() {
        if (Objects.nonNull(firstName) && Objects.nonNull(lastName)) {
            if (Objects.nonNull(user)) {
                return String.format("%s %s [%s]", firstName, lastName, user.getUsername());
            } else {
                return String.format("%s %s", firstName, lastName);
            }
        } else if (Objects.nonNull(user)) {
            return user.getUsername();
        } else {
            return getId().toString();
        }
    }
}
