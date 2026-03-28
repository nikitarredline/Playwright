package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Page;
import components.TeacherPopup;
import components.TeachersBlock;
import pages.ClickhousePage;
import pages.CoursesPage;

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
    public CoursesPage provideCoursesPage() {
        return new CoursesPage(page);
    }
}