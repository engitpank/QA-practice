package lesson5;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static com.codeborne.selenide.Selenide.*;
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


    private SelenideElement firstNameInput = $("#firstName");
    private SelenideElement lastNameInput = $("#lastName");
    private SelenideElement emailInput = $("#userEmail");
    private SelenideElement numberInput = $("#userNumber");
    private SelenideElement maleRadio = $("[for=gender-radio-1]");
    private SelenideElement femaleRadio = $("[for=gender-radio-2]");
    private SelenideElement otherRadio = $("[for=gender-radio-3]");
    private SelenideElement dateInput = $(".react-datepicker__input-container");
    private SelenideElement dayPicker = $("[role='listbox']");
    private SelenideElement subjectInput = $("#subjectsInput");;
    private SelenideElement sportHobby = $("#hobbies-checkbox-1");;
    private SelenideElement readingHobby = $("#hobbies-checkbox-2");;
    private SelenideElement musicHobby = $("#hobbies-checkbox-3");;
    private SelenideElement pictureUpload = $("#uploadPicture");;
    private SelenideElement state = $("#state");;
    private SelenideElement city = $("#city");;
    private SelenideElement submitButton = $("#submit");;
    private SelenideElement formHeader = $(".practice-form-wrapper");
    private SelenideElement currentAddressInput = $("#currentAddress");;
    private SelenideElement yearPicker = $(".react-datepicker__year-select");
    private SelenideElement monthPicker = $(".react-datepicker__month-select");

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "2560x1440";
        Configuration.pageLoadTimeout = 600000;
        Configuration.timeout = 60000;
    }

    /*
    Автотест на проверку формы https://demoqa.com/automation-practice-form
     */
    @Test
    void filledFormShouldBeSubmitted() {
        open("https://demoqa.com/automation-practice-form");

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