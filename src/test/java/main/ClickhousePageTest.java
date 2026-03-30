package main;

import com.google.inject.Inject;
import extensions.PlaywrightUiExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.ClickhousePage;

@ExtendWith(PlaywrightUiExtension.class)
public class ClickhousePageTest {

    @Inject
    private ClickhousePage clickhousePage;

    @Test
    public void clickhouseTeachersTest() {
        clickhousePage.open();
        String before = clickhousePage.teachersBlock().getActiveTeacherName();
        clickhousePage.teachersBlock().drag();
        String after = clickhousePage.teachersBlock().getActiveTeacherName();
        String next = clickhousePage.teachersBlock().getNextTeacherName();
        Assertions.assertNotEquals(before, after);
        clickhousePage.teachersBlock().clickActiveTeacher();
        clickhousePage.teacherPopup().shouldHaveTeacherName(after);
        clickhousePage.teacherPopup().clickNextAndWait();
        clickhousePage.teacherPopup().shouldHaveTeacherName(next);
        clickhousePage.teacherPopup().clickPrevAndWait();
        clickhousePage.teacherPopup().shouldHaveTeacherName(after);
    }
}
