package org.izumi.jmix.booking.ui.impl;

import java.util.function.Consumer;

import com.vaadin.spring.annotation.UIScope;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.screen.OpenMode;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.ui.MenuItems;
import org.izumi.jmix.booking.component.UserSource;
import org.izumi.jmix.booking.entity.user.Employee;
import org.izumi.jmix.booking.exception.BookingException;
import org.izumi.jmix.booking.repository.EmployeeRepository;
import org.izumi.jmix.booking.ui.screen.employee.ProfileBrowse;
import org.springframework.stereotype.Component;

@UIScope
@RequiredArgsConstructor
@Component(MenuItems.NAME)
public class MenuItemsImpl implements MenuItems {
    private final Screens screens;
    private final ScreenBuilders screenBuilders;
    private final UserSource userSource;
    private final EmployeeRepository employeeRepository;

    @Override
    public void openProfile() {
        final Consumer<FetchPlanBuilder> fetchPlanConfigurer = builder -> builder
                .addFetchPlan(FetchPlan.BASE)
                .add("user", FetchPlan.BASE)
                .add("office", FetchPlan.BASE);
        final var employee = employeeRepository.findByUser(fetchPlanConfigurer, userSource.getAuthenticated())
                .orElseThrow(() -> new BookingException("No employee is bound to authenticated user"));

        final var root = screens.getOpenedScreens().getRootScreen();
        screenBuilders.editor(Employee.class, root)
                .withScreenClass(ProfileBrowse.class)
                .withOpenMode(OpenMode.NEW_TAB)
                .editEntity(employee)
                .build()
                .show();
    }
}
