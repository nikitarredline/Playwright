package pages;

import annotations.Path;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import commons.AbsCommon;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class AbsBasePage extends AbsCommon {

    public AbsBasePage(Page page) {
        super(page);
    }

    private String getPath() {
        Path path = getClass().getDeclaredAnnotation(Path.class);
        return path != null ? path.value() : "";
    }

    public void open() {
        page().navigate(System.getProperty("base.url") + getPath());
    }

    public boolean headlessCheck(String title) {
        assertThat(
                page().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(title))
        ).isVisible();
        return true;
    }

    public void checkTextVisible(String text) {
        assertThat(page().getByText(text)).isVisible();
    }
}
