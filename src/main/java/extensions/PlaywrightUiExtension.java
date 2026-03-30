package extensions;

import com.google.inject.Guice;
import com.microsoft.playwright.*;
import modules.GuiceComponentsModule;
import modules.GuiceModule;
import modules.GuicePagesModule;
import org.junit.jupiter.api.extension.*;

import java.nio.file.Paths;

public class PlaywrightUiExtension implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    @Override
    public void beforeAll(ExtensionContext context) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)  // GUI
                        .setSlowMo(100)      // замедление действий
        );
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        browserContext = browser.newContext();
        page = browserContext.newPage();

        // Включаем трассировку для каждого теста
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
        );

        Guice.createInjector(
                new GuiceModule(page),
                new GuicePagesModule(page),
                new GuiceComponentsModule(page)
        ).injectMembers(context.getTestInstance().get());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        try {
            if (browserContext != null && page != null) {
                String tracePath = "trace-" + context.getDisplayName().replace(" ", "_") + ".zip";
                browserContext.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get(tracePath))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null) page.close();
            if (browserContext != null) browserContext.close();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    public Page page() {
        return page;
    }
}