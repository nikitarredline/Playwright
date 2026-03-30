package pages;

import annotations.Path;
import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import components.CoursesBlock;
import components.CoursesFilters;

@Path("/catalog/courses/")
public class CoursesPage extends AbsBasePage {

    private final CoursesFilters filters;
    private final CoursesBlock block;

    @Inject
    public CoursesPage(Page page, CoursesFilters filters, CoursesBlock block) {
        super(page);
        this.filters = new CoursesFilters(page);
        this.block = new CoursesBlock(page);
    }

    public CoursesFilters filters() {
        return filters;
    }

    public CoursesBlock block() {
        return block;
    }
}
