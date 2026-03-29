package commons;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.w3c.dom.Text;

public abstract class AbsCommon {

    private final Page page;

    protected AbsCommon(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Page must not be null");
        }
        this.page = page;
    }

    protected Page getPage() {
        return page;
    }

    protected void scrollToCenter(Locator locator) {
        locator.scrollIntoViewIfNeeded();
        locator.evaluate(
                "el => el.scrollIntoView({behavior: 'instant', block: 'center', inline: 'center'})"
        );
    }

    protected void click(Locator locator) {
        scrollToCenter(locator);
        locator.click();
    }

    protected void type(Locator locator, String text) {
        scrollToCenter(locator);
        locator.fill(text);
    }

    public void clickButton(String buttonText) {
        Locator locator = page.locator(String.format("button:has-text('%s')", buttonText));
        scrollToCenter(locator);
        locator.click();
    }
}