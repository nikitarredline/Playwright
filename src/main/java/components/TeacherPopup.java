package components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import commons.AbsCommon;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TeacherPopup extends AbsCommon {

    private final Locator teachersPopup;

    public TeacherPopup(Page page) {
        super(page);
        this.teachersPopup = page.locator("#__PORTAL__");
    }

    public void shouldHaveTeacherName(String name) {
        Locator teacherName = teachersPopup.locator(".swiper-slide.swiper-slide-active h3");

        assertThat(teacherName).hasText(name);
    }

    public void clickNextAndWait() {
        teachersPopup.locator("button:last-child").click();
        waitForSliderStable();
    }

    public void clickPrevAndWait() {
        waitForSliderStable();
        teachersPopup.locator("button").nth(-2).click();
        waitForSliderStable();
    }

    private void waitForSliderStable() {
        page().waitForFunction(
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
