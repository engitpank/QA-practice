package lesson4;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
/*
На главной странице GitHub выберите меню Solutions -> Enterprise с помощью команды hover для Solutions.
Убедитесь что загрузилась нужная страница (например что заголовок - "The enterprise-ready platform that developers know and love."
 */
public class GithubEnterprisePageSearch {
    @Test
    void shouldGoToEnterprisePage() {
        open("https://github.com/");
        SelenideElement SolutionsDropDown = $(Selectors.byText("Solutions"));
        SolutionsDropDown.hover();
        SolutionsDropDown.sibling(0).$(byText("Enterprises")).click();
        $("main react-app section header").shouldHave(Condition.text("The enterprise-ready platform that developers know and love"));
    }
}
