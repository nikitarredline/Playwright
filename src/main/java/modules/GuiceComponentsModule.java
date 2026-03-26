package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Page;
import components.TeacherPopup;
import components.TeachersBlock;

public class GuiceComponentsModule extends AbstractModule {

    private Page page;

    public GuiceComponentsModule(Page page) {
        this.page = page;
    }

    @Provides
    @Singleton
    public TeachersBlock teachersBlock(Page page) {
        return new TeachersBlock(page);
    }

    @Provides
    @Singleton
    public TeacherPopup teacherPopup(Page page) {
        return new TeacherPopup(page);
    }
}
