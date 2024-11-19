import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTests {

    @Test
    void unSuccessfulLoginWithCommentsTest() {
//        Открыть форму авторизации
//        https://qa.guru/cms/system/login
//        Ввести адрес электронной почты
//        Ввести пароль
//        Нажать кнопку "Войти"
//        Проверить, что отображается ошибка

        Configuration.holdBrowserOpen = true;
        Configuration.browser = Browsers.CHROME;
        Configuration.browserSize = "1920x1080";

//        Открыть форму авторизации
        open("https://qa.guru/cms/system/login");

//        Ввести адрес электронной почты
        $("[name=email]").setValue("qagurubot@gmail.com");
//        Ввести пароль
        $("[name=password]").setValue("qagurupassword");
//        Нажать кнопку "Войти"
        $(".btn-success").click();
//        Неуспешный пароль
        $(".btn-error").shouldHave(text("Неверный пароль"));
    }
}