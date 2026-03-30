package components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import commons.AbsCommon;

public class Packages extends AbsCommon {

    private final Locator packages;

    public Packages(Page page) {
        super(page);
        this.packages = page.locator("section#packages");
    }

    public void packagesBasicOpen() {
        scrollToCenter(packages);
        packages.locator("button", new Locator.LocatorOptions()
                        .setHasText("Подробнее"))
                .first()
                .click();
        packages.getByText("Можно продлить подписку на 3, 6 или 12 месяцев в личном кабинете").first().isVisible();
        packages.locator("button", new Locator.LocatorOptions()
                        .setHasText("Свернуть"))
                .first()
                .isVisible();
    }

    public void packagesBasicClose() {
        packages.locator("button", new Locator.LocatorOptions()
                        .setHasText("Свернуть"))
                .first()
                .click();
        packages.getByText("Можно продлить подписку на 3, 6 или 12 месяцев в личном кабинете").first().isHidden();
    }

    public void clickBasicBuy() {
        packages.locator("button", new Locator.LocatorOptions()
                        .setHasText("Купить"))
                .first()
                .click();
    }
}
