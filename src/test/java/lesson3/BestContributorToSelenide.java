package lesson3;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class BestContributorToSelenide {
    @Test
    void solntsevShouldBeTheTopContributor() {
//        Открыть страницу репозитория selenide
        open("https://github.com/selenide/selenide/");
//        Открыть блог Contributors
        $$(".BorderGrid a").findBy(Condition.text("Contributors")).ancestor(".BorderGrid-cell").$$("ul li").first().hover();
//        Проверить, что на первом месте solntsev
        $("[aria-label='User login and name'] a").shouldHave(Condition.text("asolntsev"));
    }
}
