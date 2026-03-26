package main;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import extensions.PlaywrightUiExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.ClickhousePage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(PlaywrightUiExtension.class)
public class ClickhousePageTest {

    @Inject
    private Page page;

    @Inject
    private ClickhousePage clickhousePage;

    @Test
    public void teachersBlockAreVisible() {
        clickhousePage.open();
        String before = clickhousePage.teachersBlock().getActiveTeacherName();
        clickhousePage.teachersBlock().drag();
        String after = clickhousePage.teachersBlock().getActiveTeacherName();
        Assertions.assertNotEquals(before, after);
        clickhousePage.teachersBlock().clickActiveTeacher();
        clickhousePage.teacherPopup().teacherNameIsVisible(after);
        clickhousePage.teacherPopup().clickNext();
        assertTrue(clickhousePage.headlessCheck("Андрей Поляков"));
//        assertFalse(clickhousePage.headlessCheck(after));
//        clickhousePage.teacherPopup().clickPrev();
//        assertTrue(clickhousePage.headlessCheck(after));
    }
}
