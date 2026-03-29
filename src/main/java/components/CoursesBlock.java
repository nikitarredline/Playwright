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

    private final Locator root;
    private final Locator durationElements;

    public CoursesBlock(Page page) {
        super(page);

        // root каталога
        this.root = page.locator("section:has(h1:has-text('Каталог'))");

        // все элементы с длительностью
        this.durationElements = root.locator("a").locator("div:has-text('месяц')");
    }

    /**
     * Ждём, пока ВСЕ курсы будут соответствовать диапазону
     */
    public void waitUntilDurationsInRange(int min, int max) {
        root.waitFor(); // ждём появление каталога
        ElementHandle rootHandle = root.elementHandle();

        getPage().waitForFunction(
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

    /**
     * Получаем все длительности курсов
     */
    public List<Integer> getAllCourseDurations() {
        durationElements.first().waitFor(); // ждём появления

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

    /**
     * Проверяем диапазон (assert)
     */
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
        return root.locator("a[href^='/lessons/'] h6 div")
                .allTextContents();
    }

    public void waitUntilCoursesUpdated(List<String> oldTitles) {
        // Локатор всех названий курсов
        Locator courseTitlesLocator = root.locator("a[href^='/lessons/'] h6 div");

        // Ждём, пока хотя бы одно название изменится
        getPage().waitForCondition(() -> {
            List<String> currentTitles = courseTitlesLocator.allTextContents();
            return !currentTitles.equals(oldTitles);
        });
    }
}