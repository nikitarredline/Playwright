package main;

import com.google.inject.Inject;
import extensions.PlaywrightUiExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.LoginRegisterPage;
import pages.SubscriptionPage;

@ExtendWith(PlaywrightUiExtension.class)
public class SubscriptionPageTest {

    @Inject
    private SubscriptionPage subscriptionPage;

    @Inject
    private LoginRegisterPage loginRegisterPage;

    @Test
    public void subscriptionTest() {
        subscriptionPage.open();
        subscriptionPage.headlessCheck("Basic");
        subscriptionPage.headlessCheck("Standard");
        subscriptionPage.headlessCheck("Professional");
        subscriptionPage.packages().packagesBasicOpen();
        subscriptionPage.packages().packagesBasicClose();
        subscriptionPage.packages().clickBasicBuy();
        loginRegisterPage.registerPageOpened();
    }
}
