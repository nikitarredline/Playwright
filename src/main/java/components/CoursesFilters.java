package components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Mouse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import commons.AbsCommon;

public class CoursesFilters extends AbsCommon {

    private Page page;

    public CoursesFilters(Page page) {
        super(page);
        this.page = page;
    }

    public void checkboxIsChecked(String checkboxText) {
        String xpath = String.format("//input[@type='checkbox']/following::label[text()='%s']", checkboxText);

        Locator checkbox = page.locator(xpath);

        if (!checkbox.isChecked()) {
            throw new AssertionError(String.format("Чекбокс '%s' не выбран!", checkboxText));
        }
    }

    public void dragDurationSlider(int months, boolean isMinSlider) {
        if (months < 0 || months > 15) {
            throw new IllegalArgumentException("Значение должно быть от 0 до 15");
        }

        int min = 0;
        int max = 15;

        // Находим нужный ползунок
        Locator slider = page.locator("div[role=slider]").nth(isMinSlider ? 0 : 1);
        scrollToCenter(slider);

        // Полоска (track) — родитель slider
        Locator track = slider.locator("..");

        BoundingBox sliderBox = slider.boundingBox();
        BoundingBox trackBox = track.boundingBox();

        if (sliderBox == null || trackBox == null) {
            throw new RuntimeException("Не удалось получить размеры slider или track");
        }

        // Начальная позиция — центр ползунка
        double startX = sliderBox.x + sliderBox.width / 2;
        double startY = sliderBox.y + sliderBox.height / 2;

        // Конечная позиция — нужное значение месяцев
        double targetX = trackBox.x + (months - min) / (double)(max - min) * trackBox.width;
        double targetY = startY;

        // Drag & Drop
        page.mouse().move(startX, startY);
        page.mouse().down();
        page.mouse().move(targetX, targetY, new Mouse.MoveOptions().setSteps(20));
        page.mouse().up();
    }
}