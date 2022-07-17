package org.izumi.jmix.booking.repository;

import java.awt.print.Book;

import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.resource.AbstractResource;
import org.izumi.jmix.booking.entity.user.Employee;
import org.izumi.jmix.booking.repository.api.AbstractStandardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractResourceRepository extends AbstractStandardRepository<AbstractResource> {

    @Autowired
    public AbstractResourceRepository(DataManager dataManager, Metadata metadata) {
        super(AbstractResource.class, dataManager, metadata);
    }

    public boolean isBookedAtCurrentTime(AbstractResource resource) {
        return !dataManager.load(Booking.class)
                .query("SELECT b FROM Booking b " +
                        "WHERE b.resource = :resource " +
                            "AND b.periodStart <= CURRENT_TIMESTAMP " +
                            "AND b.periodEnd >= CURRENT_TIMESTAMP")
                .parameter("resource", resource)
                .fetchPlan(builder -> builder.add("id"))
                .list()
                .isEmpty();
    }

    public boolean isNotBookedAtCurrentTime(AbstractResource resource) {
        return !isBookedAtCurrentTime(resource);
    }

    public boolean hasCurrentOrFutureBookings(AbstractResource resource) {
        return !dataManager.load(Booking.class)
                .query("SELECT b FROM Booking b " +
                        "WHERE b.resource = :resource " +
                        "AND b.periodEnd >= CURRENT_TIMESTAMP")
                .parameter("resource", resource)
                .fetchPlan(builder -> builder.add("id"))
                .list()
                .isEmpty();
    }

    public boolean isBookedByEmployeeAtCurrentTime(AbstractResource resource, Employee employee) {
        return !dataManager.load(Booking.class)
                .query("SELECT b FROM Booking b " +
                        "WHERE b.resource = :resource " +
                            "AND b.employee = :employee " +
                            "AND b.periodStart <= CURRENT_TIMESTAMP " +
                            "AND b.periodEnd >= CURRENT_TIMESTAMP")
                .parameter("resource", resource)
                .parameter("employee", employee)
                .fetchPlan(builder -> builder.add("id"))
                .list()
                .isEmpty();
    }
}
