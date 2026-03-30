package pages;

import com.microsoft.playwright.Page;

public class LoginRegisterPage extends AbsBasePage {

    public LoginRegisterPage(Page page) {
        super(page);
    }

    public void registerPageOpened() {
        checkTextVisible("Подписка на доступ к курсам");
    }
}
