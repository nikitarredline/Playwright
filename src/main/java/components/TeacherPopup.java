package components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import commons.AbsCommon;

public class TeacherPopup extends AbsCommon {

    private final Locator root;

    public TeacherPopup(Page page) {
        super(page);
        this.root = page.locator("#__PORTAL__");
    }

    public void clickNext() {
        root.locator("button:last-child div").nth(1);
    }

    public void clickPrev() {
        root.locator("button:nth-child(2)").nth(0).click();
    }

    public boolean teacherNameIsVisible(String name) {
        Locator teacherHeading = page.locator("section:has(h2:has-text('Преподаватели')) h3")
                .filter(new Locator.FilterOptions().setHasText(name));
        return teacherHeading.isVisible();
    }
}
