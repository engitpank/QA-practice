package lesson3;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

public class SoftAssertionsSearch {
    @Test
    void wikiPageShouldHaveSoftAssertionsInfo() {
        Selenide.open("https://github.com/selenide/selenide/wiki");
        SelenideElement wikiPageBody = Selenide.$("#wiki-body ul");
        String wikiPageTarget = "Soft assertions";

        wikiPageBody.shouldHave(Condition.text(wikiPageTarget));
        wikiPageBody.$(Selectors.byText(wikiPageTarget)).click();
        Selenide.$("#wiki-body").shouldHave(Condition.text("Using JUnit5 extend test class"));
    }
}
