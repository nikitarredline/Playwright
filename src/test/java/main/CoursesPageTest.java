package main;

import com.google.inject.Inject;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import components.CoursesBlock;
import components.CoursesFilters;
import extensions.PlaywrightUiExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursesPage;

import java.util.List;

@ExtendWith(PlaywrightUiExtension.class)
public class CoursesPageTest {

    @Inject
    private Page page;

    @Inject
    private CoursesPage coursesPage;

    @Inject
    private CoursesFilters coursesFilters;

    @Inject
    private CoursesBlock coursesBlock;

    @Test
    public void coursesFilterTest() throws InterruptedException {
        coursesPage.open();
        coursesFilters.checkboxIsChecked("Все направления");
        coursesFilters.checkboxIsChecked("Любой уровень");
        List<String> defaultTitles = coursesBlock.getCourseTitles();
        coursesFilters.dragDurationSlider(3, true);
        coursesFilters.dragDurationSlider(10, false);
        coursesBlock.waitUntilDurationsInRange(3, 10);
        coursesBlock.checkDurationsInRange(3, 10);
        List<String> oldTitles = coursesBlock.getCourseTitles();
        coursesFilters.toggleCheckbox("Архитектура");
        coursesBlock.waitUntilCoursesUpdated(oldTitles);
        List<String> newTitles = coursesBlock.getCourseTitles();
        Assertions.assertNotEquals(oldTitles, newTitles, "Список курсов не изменился после фильтрации!");
        coursesFilters.clickClearFiltersButton();
        coursesBlock.waitUntilCoursesUpdated(newTitles);
        Assertions.assertNotEquals(newTitles, defaultTitles, "Список курсов не изменился после фильтрации!");
    }
}
