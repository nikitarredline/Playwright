package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.playwright.Page;
import components.TeacherPopup;
import components.TeachersBlock;
import pages.ClickhousePage;

public class GuicePagesModule extends AbstractModule {

    private Page page;

    public GuicePagesModule(Page page) {
        this.page = page;
    }

    @Provides
    @Singleton
    public ClickhousePage clickhousePage(Page page, TeachersBlock teachersBlock, TeacherPopup teacherPopup) {
        return new ClickhousePage(page, teachersBlock, teacherPopup);
    }
}
