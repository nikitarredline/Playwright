package main;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import components.CoursesFilters;
import extensions.PlaywrightUiExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursesPage;

@ExtendWith(PlaywrightUiExtension.class)
public class CoursesPageTest {

    @Inject
    private Page page;

    @Inject
    private CoursesPage coursesPage;

    @Inject
    private CoursesFilters coursesFilters;

    @Test
    public void coursesFilterTest() throws InterruptedException {
        coursesPage.open();
        coursesFilters.checkboxIsChecked("Все направления");
        coursesFilters.checkboxIsChecked("Любой уровень");
        coursesFilters.dragDurationSlider(3, true);
        coursesFilters.dragDurationSlider(10, false);
    }
}
