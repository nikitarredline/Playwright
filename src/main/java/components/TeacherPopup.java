package components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import commons.AbsCommon;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TeacherPopup extends AbsCommon {

    private final Locator root;

    public TeacherPopup(Page page) {
        super(page);
        this.root = page.locator("#__PORTAL__");
    }

    public void shouldHaveTeacherName(String name) {
        Locator teacherName = root.locator(".swiper-slide.swiper-slide-active h3");

        assertThat(teacherName).hasText(name);
    }

    public void clickNextAndWait() {
        root.locator("button:last-child").click();
        waitForSliderStable();
    }

    public void clickPrevAndWait() {
        waitForSliderStable();
        root.locator("button").nth(-2).click();
        waitForSliderStable();
    }

    private void waitForSliderStable() {
        getPage().waitForFunction(
                """
                () => {
                    const el = document.querySelector('#__PORTAL__ .swiper-wrapper');
                    if (!el) return false;
        
                    const x = el.getBoundingClientRect().x;
                    if (!window.__lastX) {
                        window.__lastX = x;
                        return false;
                    }
        
                    const stable = Math.abs(window.__lastX - x) < 0.5;
                    window.__lastX = x;
        
                    return stable;
                }
                """
        );
    }
}
