package pages;

import annotations.Path;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;

@Path("/catalog/courses/")
public class CoursesPage extends AbsBasePage {

    @Inject
    public CoursesPage(Page page) {
        super(page);
    }
}
