package pages;

import annotations.Path;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import components.Packages;

@Path("/subscription/")
public class SubscriptionPage extends AbsBasePage {

    private final Packages packages;

    @Inject
    public SubscriptionPage(Page page, Packages packages) {
        super(page);
        this.packages = new Packages(page);
    }

    public Packages packages() {
        return packages;
    }
}
