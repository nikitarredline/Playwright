package pages;

import annotations.Path;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import components.TeacherPopup;
import components.TeachersBlock;

@Path("/lessons/clickhouse/")
public class ClickhousePage extends AbsBasePage {

    private final TeachersBlock teachersBlock;

    private final TeacherPopup teacherPopup;

    @Inject
    public ClickhousePage(Page page, TeachersBlock teachersBlock, TeacherPopup teacherPopup) {
        super(page);
        this.teachersBlock = teachersBlock;
        this.teacherPopup = teacherPopup;
    }

    public TeachersBlock teachersBlock() {
        return teachersBlock;
    }

    public TeacherPopup teacherPopup() {
        return teacherPopup;
    }
}