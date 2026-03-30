package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Page;
import components.*;
import pages.ClickhousePage;
import pages.CoursesPage;
import pages.LoginRegisterPage;
import pages.SubscriptionPage;

public class GuicePagesModule extends AbstractModule {

    private final Page page;

    public GuicePagesModule(Page page) {
        this.page = page;
    }

    @Provides
    @Singleton
    public ClickhousePage provideClickhousePage(TeachersBlock teachersBlock, TeacherPopup teacherPopup) {
        return new ClickhousePage(page, teachersBlock, teacherPopup);
    }

    @Provides
    @Singleton
    public CoursesPage provideCoursesPage(CoursesFilters filters, CoursesBlock block) {
        return new CoursesPage(page, filters, block);
    }

    @Provides
    @Singleton
    public SubscriptionPage provideSubscriptionPage(Packages packages) {
        return new SubscriptionPage(page, packages);
    }

    @Provides
    @Singleton
    public LoginRegisterPage loginRegisterPage() {
        return new LoginRegisterPage(page);
    }
}