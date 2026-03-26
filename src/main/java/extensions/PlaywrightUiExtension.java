package extensions;

import com.google.inject.Guice;
import com.microsoft.playwright.*;
import modules.GuiceComponentsModule;
import modules.GuiceModule;
import modules.GuicePagesModule;
import org.junit.jupiter.api.extension.*;

public class PlaywrightUiExtension implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback {

    private Playwright playwright;

    private BrowserContext browserContext;

    private Browser browser;

    private Page page;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        browserContext = browser.newContext();
        page = browserContext.newPage();

        Guice.createInjector(new GuiceModule(page), new GuicePagesModule(page), new GuiceComponentsModule(page)).injectMembers(context.getTestInstance().get());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        browser.close();
        playwright.close();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        page.close();
        browserContext.close();
    }
}
