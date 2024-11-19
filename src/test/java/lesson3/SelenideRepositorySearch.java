package lesson3;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideRepositorySearch {
    @Test
    void shouldFindSelenideRepositoryAtTheTop() {
        open("https://github.com/");
        $(".search-input").click();
        $("#query-builder-test").setValue("Selenide").pressEnter();

        $("[data-testid=results-list] a").click(); // or $$().first().click();
        $("#repository-container-header").shouldHave(Condition.text("selenide / selenide"));
    }
}
