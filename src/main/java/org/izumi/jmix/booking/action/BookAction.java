package org.izumi.jmix.booking.action;

import javax.annotation.Nonnull;

import java.util.Optional;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import io.jmix.core.Messages;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.ActionType;
import io.jmix.ui.action.ListAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.data.meta.EntityDataUnit;
import io.jmix.ui.meta.StudioAction;
import org.izumi.jmix.booking.component.UserSource;
import org.izumi.jmix.booking.entity.Booking;
import org.izumi.jmix.booking.entity.resource.AbstractResource;
import org.izumi.jmix.booking.entity.user.Employee;
import org.izumi.jmix.booking.exception.BookingException;
import org.izumi.jmix.booking.repository.AbstractResourceRepository;
import org.izumi.jmix.booking.repository.EmployeeRepository;
import org.izumi.jmix.booking.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;

@StudioAction(
        target = "io.jmix.ui.component.ListComponent",
        description = "Creating a screen to book a resource",
        availableInScreenWizard = true
)
@ActionType(BookAction.ID)
public class BookAction extends ListAction implements Action.ExecutableAction {
    public static final String ID = "book";

    private AbstractResourceRepository abstractResourceRepository;
    private EmployeeRepository employeeRepository;
    private UserSource userSource;
    private Screens screens;
    private ScreenBuilders screenBuilders;
    private Supplier<Employee> employeeSupplier;

    public BookAction() {
        this(BookAction.ID);
    }

    public BookAction(String id) {
        super(id);
    }

    @Override
    public void execute() {
        final var abstractResource = Preconditions.checkNotNull((AbstractResource) target.getSingleSelected());
        final var employee = getEmployee();

        final var root = screens.getOpenedScreens().getRootScreen();
        screenBuilders.editor(Booking.class, root)
                .withInitializer(booking -> {
                    booking.setResource(abstractResource);
                    booking.setEmployee(employee);
                })
                .build()
                .show();
    }

    @Override
    public void actionPerform(final @Nonnull Component component) {
        final var standardBehavior = !hasSubscriptions(ActionPerformedEvent.class);
        if (standardBehavior) {
            execute();
        } else {
            super.actionPerform(component);
        }
    }

    @Override
    protected boolean isPermitted() {
        if (checkTarget().isNotValid() || checkMetaClass().isNotValid()) {
            return false;
        }

        final var bookerOptional = getEmployeeOptional();
        if (bookerOptional.isEmpty()) {
            return false;
        }

        final var booker = bookerOptional.get();
        final var toBook = (AbstractResource) target.getSingleSelected();
        if (abstractResourceRepository.isBookedAtCurrentTime(toBook) || areNotInSameOffice(booker, toBook)) {
            return false;
        }

        return super.isPermitted();
    }

    public void setEmployeeSupplier(Supplier<Employee> employeeSupplier) {
        this.employeeSupplier = employeeSupplier;
    }

    @Autowired
    public void setMessages(Messages messages) {
        setCaption(messages.getMessage("", "action.book.caption"));
    }

    @Autowired
    public void setAbstractResourceRepository(AbstractResourceRepository abstractResourceRepository) {
        this.abstractResourceRepository = abstractResourceRepository;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setUserSource(UserSource userSource) {
        this.userSource = userSource;
    }

    @Autowired
    public void setScreens(Screens screens) {
        this.screens = screens;
    }

    @Autowired
    public void setScreenBuilders(ScreenBuilders screenBuilders) {
        this.screenBuilders = screenBuilders;
    }

    private boolean areInSameOffice(Employee booker, AbstractResource toBook) {
        return booker.getOffice().equals(toBook.getBelonging());
    }

    private boolean areNotInSameOffice(Employee booker, AbstractResource toBook) {
        return !areInSameOffice(booker, toBook);
    }

    private Employee getEmployee() {
        return getEmployeeOptional()
                .orElseThrow(() -> new BookingException("Effective user is not bound to an employee. " +
                        "It is forbidden to book"));
    }

    private Optional<Employee> getEmployeeOptional() {
        if (employeeSupplier != null) {
            return Optional.of(employeeSupplier.get());
        }

        return employeeRepository.findByUser(userSource.getEffective());
    }

    private ValidationResult checkTarget() {
        if (target == null || target.getSingleSelected() == null || !(target.getItems() instanceof EntityDataUnit)) {
            return ValidationResult.failure();
        }

        return ValidationResult.success();
    }

    private ValidationResult checkMetaClass() {
        final var metaClass = ((EntityDataUnit) target.getItems()).getEntityMetaClass();
        if (metaClass == null || !AbstractResource.class.isAssignableFrom(metaClass.getJavaClass())) {
            return ValidationResult.failure();
        }

        return ValidationResult.success();
    }
}
