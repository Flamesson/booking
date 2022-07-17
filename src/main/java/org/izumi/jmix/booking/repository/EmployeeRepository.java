package org.izumi.jmix.booking.repository;

import java.util.Optional;
import java.util.function.Consumer;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.Metadata;
import org.izumi.jmix.booking.component.UserSource;
import org.izumi.jmix.booking.entity.user.Employee;
import org.izumi.jmix.booking.entity.user.User;
import org.izumi.jmix.booking.repository.api.AbstractStandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository extends AbstractStandardRepository<Employee> {
    private final UserSource userSource;

    @Autowired
    public EmployeeRepository(DataManager dataManager, Metadata metadata, UserSource userSource) {
        super(Employee.class, dataManager, metadata);
        this.userSource = userSource;
    }

    public Optional<Employee> findByUser(User user) {
        return dataManager.load(Employee.class)
                .query("SELECT e FROM Employee e WHERE e.user = :user")
                .parameter("user", user)
                .fetchPlan(FetchPlan.BASE)
                .optional();
    }

    public Optional<Employee> findByUser(Consumer<FetchPlanBuilder> fetchPlanConfigurer, User user) {
        return dataManager.load(Employee.class)
                .query("SELECT e FROM Employee e WHERE e.user = :user")
                .parameter("user", user)
                .fetchPlan(fetchPlanConfigurer)
                .optional();
    }

    public Optional<Employee> findByEffectiveUser() {
        return findByUser(userSource.getEffective());
    }
}
