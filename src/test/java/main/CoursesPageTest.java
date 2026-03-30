package main;

import com.google.inject.Inject;
import extensions.PlaywrightUiExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursesPage;

import java.util.List;

@ExtendWith(PlaywrightUiExtension.class)
public class CoursesPageTest {

    @Inject
    private CoursesPage coursesPage;

    @Test
    public void coursesFilterTest() {
        coursesPage.open();
        coursesPage.filters().checkboxIsChecked("Все направления");
        coursesPage.filters().checkboxIsChecked("Любой уровень");
        List<String> defaultTitles = coursesPage.block().getCourseTitles();
        coursesPage.filters().dragDurationSlider(3, true);
        coursesPage.filters().dragDurationSlider(10, false);
        coursesPage.block().waitUntilDurationsInRange(3, 10);
        coursesPage.block().checkDurationsInRange(3, 10);
        List<String> oldTitles = coursesPage.block().getCourseTitles();
        coursesPage.filters().toggleCheckbox("Архитектура");
        coursesPage.block().waitUntilCoursesUpdated(oldTitles);
        List<String> newTitles = coursesPage.block().getCourseTitles();
        Assertions.assertNotEquals(oldTitles, newTitles, "Список курсов не изменился после фильтрации!");
        coursesPage.filters().clickClearFiltersButton();
        coursesPage.block().waitUntilCoursesUpdated(newTitles);
        Assertions.assertNotEquals(newTitles, defaultTitles, "Список курсов не изменился после фильтрации!");
    }
}
