package lesson4;

import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

/*
Selenide.actions()
- Откройте https://the-internet.herokuapp.com/drag_and_drop
- Перенесите прямоугольник А на место В
- Проверьте, что прямоугольники действительно поменялись
- В Selenide есть команда $(element).dragAndDrop($(to-element)), проверьте работает ли тест, если использовать её вместо actions()
 */
public class DragAndDropTest {
    @Test
    void shouldDragAndDropViaActions() {
        Selenide.open("https://the-internet.herokuapp.com/drag_and_drop");
        SelenideElement columnA = $("#column-a");
        SelenideElement columnB = $("#column-b");
        columnA.dragAndDrop(DragAndDropOptions.to(columnB));
        columnA.shouldHave(text("B"));
        columnB.shouldHave(text("A"));
    }
}
