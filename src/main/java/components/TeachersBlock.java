package components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Mouse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import commons.AbsCommon;

public class TeachersBlock extends AbsCommon {

    private final Locator root;

    public TeachersBlock(Page page) {
        super(page);
        this.root = page.locator("section:has(h2:has-text('Преподаватели'))");
    }

    public Locator activeSlide() {
        return root.locator(".swiper-slide-active");
    }

    public void drag() {
        scrollToCenter(root);

        BoundingBox box = root.locator(".swiper-wrapper").boundingBox();

        page.mouse().move(box.x + box.width - 20, box.y + box.height / 2);
        page.mouse().down();
        page.mouse().move(box.x + 50, box.y + box.height / 2, new Mouse.MoveOptions().setSteps(25));
        page.mouse().up();
    }

    public String getActiveTeacherName() {
        return activeSlide().locator("p").first().innerText();
    }

    public void clickActiveTeacher() {
        Locator active = root.locator(".swiper-slide.swiper-slide-active");
        scrollToCenter(active);
        active.click();
    }
}
