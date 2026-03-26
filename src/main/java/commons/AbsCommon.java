package commons;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class AbsCommon {

    protected Page page;

    public AbsCommon(Page page) {
        this.page = page;
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
}
