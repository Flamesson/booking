package org.izumi.jmix.booking.ui;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import com.vaadin.spring.annotation.UIScope;
import io.jmix.ui.App;
import io.jmix.ui.AppUI;
import io.jmix.ui.Screens;
import io.jmix.ui.navigation.RedirectHandler;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.OpenMode;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.StandardOutcome;
import lombok.RequiredArgsConstructor;
import org.izumi.jmix.booking.utils.Collections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@UIScope
@Component
public class BookingUi {
    private final ApplicationContext applicationContext;
    private final AppUI ui;
    private final UrlRouting urlRouting;

    @PostConstruct
    public void init() {
        initRedirectHandler();
    }

    public void updateLocale(final Locale locale) {
        final var handler = ui.getUrlChangeHandler().getRedirectHandler();
        if (handler != null) {
            handler.schedule(urlRouting.getState());
        }

        final var app = ui.getApp();
        app.setLocale(locale);
        app.addCookie(App.COOKIE_LOCALE, locale.toLanguageTag());
        recreateScreens();
    }

    @Nullable
    public Locale getLocale() {
        return ui.getLocale();
    }

    private void initRedirectHandler() {
        final var urlChangeHandler = ui.getUrlChangeHandler();
        if (urlChangeHandler.getRedirectHandler() == null) {
            final var redirectHandler = applicationContext.getBean(RedirectHandler.class, ui);
            urlChangeHandler.setRedirectHandler(redirectHandler);
        }
    }

    private void recreateScreens() {
        final var screens = ui.getScreens();
        final var opened = screens.getOpenedScreens();

        final var stacks = opened.getWorkAreaStacks();
        final var activeScreens = getRootOfActiveStacks(stacks);

        closeAll(stacks);

        ui.getApp().createTopLevelWindow();

        activeScreens.stream()
                .filter(screen -> !(screen instanceof StandardEditor<?>))
                .map(Screen::getId)
                .filter(Objects::nonNull)
                .map(screenId -> screens.create(screenId, OpenMode.NEW_TAB))
                .forEach(screens::show);
    }

    private Collection<Screen> getRootOfActiveStacks(final Collection<Screens.WindowStack> stacks) {
        return stacks.stream()
                .map(Screens.WindowStack::getBreadcrumbs)
                .map(Collections::getLast)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private void closeAll(final Collection<Screens.WindowStack> stacks) {
        stacks.stream()
                .map(Screens.WindowStack::getBreadcrumbs)
                .flatMap(Collection::stream)
                .forEach(screen -> screen.close(StandardOutcome.DISCARD));
    }
}
