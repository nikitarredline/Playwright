package components;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import commons.AbsCommon;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CoursesBlock extends AbsCommon {

    private final Locator catalogue;
    private final Locator durationElements;

    public CoursesBlock(Page page) {
        super(page);
        this.catalogue = page.locator("section:has(h1:has-text('Каталог'))");
        this.durationElements = this.catalogue.locator("a").locator("div:has-text('месяц')");
    }

    public void waitUntilDurationsInRange(int min, int max) {
        catalogue.waitFor();
        ElementHandle rootHandle = catalogue.elementHandle();

        page().waitForFunction(
                "([root, min, max]) => {" +
                        "const els = root.querySelectorAll('a div');" +
                        "if (els.length === 0) return false;" +

                        "return Array.from(els)" +
                        "  .filter(el => el.textContent.includes('месяц'))" +
                        "  .every(el => {" +
                        "    const m = el.textContent.match(/(\\d+)\\s*месяц/);" +
                        "    if (!m) return false;" +
                        "    const val = parseInt(m[1]);" +
                        "    return val >= min && val <= max;" +
                        "  });" +
                        "}",
                new Object[]{rootHandle, min, max}
        );
    }

    public List<Integer> getAllCourseDurations() {
        durationElements.first().waitFor();

        Pattern pattern = Pattern.compile("(\\d+)\\s*месяц");

        return durationElements.allTextContents()
                .stream()
                .map(text -> {
                    Matcher m = pattern.matcher(text);
                    return m.find() ? Integer.parseInt(m.group(1)) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void checkDurationsInRange(int min, int max) {
        List<Integer> durations = getAllCourseDurations();

        if (durations.isEmpty()) {
            throw new AssertionError("Список курсов пуст");
        }

        for (Integer d : durations) {
            if (d < min || d > max) {
                throw new AssertionError("Курс вне диапазона: " + d);
            }
        }
    }

    public List<String> getCourseTitles() {
        return catalogue.locator("a[href^='/lessons/'] h6 div")
                .allTextContents();
    }

    public void waitUntilCoursesUpdated(List<String> oldTitles) {
        Locator courseTitlesLocator = catalogue.locator("a[href^='/lessons/'] h6 div");

        page().waitForCondition(() -> {
            List<String> currentTitles = courseTitlesLocator.allTextContents();
            return !currentTitles.equals(oldTitles);
        });
    }
}