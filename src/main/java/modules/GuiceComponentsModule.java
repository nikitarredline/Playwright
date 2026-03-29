package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Page;
import components.CoursesBlock;
import components.CoursesFilters;
import components.TeacherPopup;
import components.TeachersBlock;

public class GuiceComponentsModule extends AbstractModule {

    private final Page page;

    public GuiceComponentsModule(Page page) {
        this.page = page;
    }

    @Provides
    @Singleton
    public TeachersBlock provideTeachersBlock() {
        return new TeachersBlock(page);
    }

    @Provides
    @Singleton
    public TeacherPopup provideTeacherPopup() {
        return new TeacherPopup(page);
    }

    @Provides
    @Singleton
    public CoursesFilters provideCoursesFilters() {
        return new CoursesFilters(page);
    }

    @Provides
    @Singleton
    public CoursesBlock provideCoursesBlock() {
        return new CoursesBlock(page);
    }
}