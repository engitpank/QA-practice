package lesson5;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static com.codeborne.selenide.Selenide.$;

public class DemoQaForm {
    private final String formHeaderValue = "Practice Form";
    private final String firstNameValue = "FirstName";
    private final String lastNameValue = "LastName";
    private final String mailValue = "randomMail@mail.com";
    private final String numberValue = "3423122231";
    private final String[] subjectValues = new String[]{"Maths", "Physics"};
    private final String pictureName = "eva.jpg";
    private final String currentAddressValue = "1-1 Tomioka, Gunma 370-2316, Japan";
    private final String stateValue = "NCR";
    private final String cityValue = "Delhi";
    private final String monthValue = "October";
    private final String yearValue = "2000";
    private final String dayNumberValue = "5";
    private final String addressValue = "05 October,2000";


    private SelenideElement firstNameInput;
    private SelenideElement lastNameInput;
    private SelenideElement emailInput;
    private SelenideElement numberInput;
    private SelenideElement maleRadio;
    private SelenideElement femaleRadio;
    private SelenideElement otherRadio;
    private SelenideElement dateInput;
    private SelenideElement dayPicker;
    private SelenideElement subjectInput;
    private SelenideElement sportHobby;
    private SelenideElement readingHobby;
    private SelenideElement musicHobby;
    private SelenideElement pictureUpload;
    private SelenideElement state;
    private SelenideElement city;
    private SelenideElement submitButton;
    private SelenideElement formHeader;
    private SelenideElement currentAddressInput;
    private SelenideElement yearPicker;
    private SelenideElement monthPicker;

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "2560x1440";
        Configuration.pageLoadTimeout = 600000;
    }

    /*
    Автотест на проверку формы https://demoqa.com/automation-practice-form
     */

    @BeforeEach
    void defineInputData() {
        formHeader = $(".practice-form-wrapper");

        firstNameInput = $("#firstName");
        lastNameInput = $("#lastName");
        emailInput = $("#userEmail");
        numberInput = $("#userNumber");

        // Gender values
        maleRadio = Selenide.$("[for=gender-radio-1]");
        femaleRadio = Selenide.$("[for=gender-radio-2]");
        otherRadio = Selenide.$("[for=gender-radio-3]");

        dateInput = $(".react-datepicker__input-container");
        dayPicker = $("[role='listbox']");
        yearPicker = $(".react-datepicker__year-select");
        monthPicker = $(".react-datepicker__month-select");


        subjectInput = $("#subjectsInput");

        sportHobby = Selenide.$("#hobbies-checkbox-1");
        readingHobby = Selenide.$("#hobbies-checkbox-2");
        musicHobby = Selenide.$("#hobbies-checkbox-3");

        currentAddressInput = $("#currentAddress");

        pictureUpload = Selenide.$("#uploadPicture");

        state = $("#state");
        city = $("#city");

        submitButton = $("#submit");
    }

    @Test
    void filledFormShouldBeSubmitted() {
        Selenide.open("https://demoqa.com/automation-practice-form");

        fillAndSubmitForm();

        SelenideElement table = $(".table-responsive>table");
        table.$(Selectors.byText("Student Name")).sibling(0).shouldHave(Condition.text(firstNameValue + " " + lastNameValue));
        table.$(Selectors.byText("Student Email")).sibling(0).shouldHave(Condition.text(mailValue));
        String targetRadio = maleRadio.getText();
        table.$(Selectors.byText("Gender")).sibling(0).shouldHave(Condition.text(targetRadio));
        table.$(Selectors.byText("Date of Birth")).sibling(0).shouldHave(Condition.text(addressValue));
        table.$(Selectors.byText("Subjects")).sibling(0).shouldHave(Condition.text(String.join(", ", subjectValues)));
        table.$(Selectors.byText("Address")).sibling(0).shouldHave(Condition.text(currentAddressValue));
        table.$(Selectors.byText("State and City")).sibling(0).shouldHave(Condition.text(stateValue + " " + cityValue));
        table.$(Selectors.byText("Picture")).sibling(0).shouldHave(Condition.text(pictureName));
    }

    private void fillAndSubmitForm() {
        formHeader.shouldHave(Condition.text(formHeaderValue));

        firstNameInput.setValue(firstNameValue);
        lastNameInput.setValue(lastNameValue);
        emailInput.setValue(mailValue);
        numberInput.setValue(numberValue);

        otherRadio.click();
        femaleRadio.click();
        maleRadio.click();

        dateInput.click();
        monthPicker.click();
        monthPicker.find(Selectors.byText(monthValue)).click();
        yearPicker.click();
        yearPicker.find(Selectors.byText(yearValue)).click();
        dayPicker.find(Selectors.byText(dayNumberValue)).click();


        Arrays.stream(subjectValues).forEach(v -> subjectInput.setValue(v).pressEnter());

        sportHobby.parent().click();
        readingHobby.parent().click();
        musicHobby.parent().click();

        File file = new File("src/test/resources/" + pictureName);
        pictureUpload.uploadFile(file);

        currentAddressInput.setValue(currentAddressValue);

        state.click();
        state.find(Selectors.byText(stateValue)).click();

        city.click();

        city.find(Selectors.byText(cityValue)).click();

        submitButton.submit();
    }
}