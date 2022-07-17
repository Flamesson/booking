package org.izumi.jmix.booking.ui.screen.main;

import java.util.Locale;

import io.jmix.core.Messages;
import io.jmix.ui.Dialogs;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import org.izumi.jmix.booking.component.CurrentUserManager;
import org.izumi.jmix.booking.component.Locales;
import org.izumi.jmix.booking.ui.BookingUi;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private ScreenTools screenTools;
    @Autowired
    private CurrentUserManager currentUserManager;
    @Autowired
    private Locales locales;
    @Autowired
    private BookingUi bookingUi;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private Messages messages;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private ComboBox<Locale> localeBox;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        localeBox.setOptionsMap(this.locales.getAvailableLocalesMap());
        localeBox.setValue(bookingUi.getLocale());
        localeBox.addValueChangeListener(this::updateLocale);
        localeBox.setOptionStyleProvider(locale ->
                locale.equals(bookingUi.getLocale()) ? "selected-locale" : null
        );
    }

    @Subscribe
    private void onAfterShow(final AfterShowEvent event) {
        screenTools.openDefaultScreen(UiControllerUtils.getScreenContext(this).getScreens());
        screenTools.handleRedirect();
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(final Button.ClickEvent event) {
        if (drawer.isCollapsed()) {
            expandSideMenu();
        } else {
            shrinkSideMenu();
        }
    }

    private void expandSideMenu() {
        drawer.toggle();
        collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
    }

    private void shrinkSideMenu() {
        drawer.toggle();
        collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
    }

    private void updateLocale(HasValue.ValueChangeEvent<Locale> event) {
        dialogs.createOptionDialog()
                .withCaption(messages.getMessage("option-dialog.switch-data.caption"))
                .withMessage(messages.getMessage("option-dialog.switch-data.message"))
                .withActions(
                        new DialogAction(DialogAction.Type.YES).withHandler(e -> {
                            final var newLocale = event.getValue();
                            currentUserManager.setLocale(newLocale);
                            bookingUi.updateLocale(newLocale);
                        }),
                        new DialogAction(DialogAction.Type.NO).withHandler(e -> {})
                )
                .show();
    }
}
