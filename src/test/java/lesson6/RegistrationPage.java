package lesson6;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lesson6.components.CalendarComponent;
import lesson6.components.RegistrationResultsModal;

import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {
    private final String TITLE_TEXT = "Practice Form";
    private final String RELATIVE_URL = "/automation-practice-form";

    private final CalendarComponent calendar = new CalendarComponent();
    private final RegistrationResultsModal registrationResultsModal = new RegistrationResultsModal();
    private final SelenideElement
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            phoneNumberInput = $("#userNumber"),
            genderInput = $("#genterWrapper"),
            dateOfBirthInput = $("#dateOfBirthInput"),
            hobbiesInput = $("#hobbiesWrapper"),
            subjectsInput = $("#subjectsInput"),
            pictureUploader = Selenide.$("#uploadPicture"),
            addressInput = $("#currentAddress"),
            stateInput = $("#state"),
            cityInput = $("#city"),
            stateCityWrapper = $("#stateCity-wrapper"),
            submitButton = $("#submit");


    public RegistrationPage openPage() {
        open(RELATIVE_URL);
        $(".practice-form-wrapper").shouldHave(Condition.text(TITLE_TEXT));
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        return this;
    }

    public RegistrationPage setFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    public RegistrationPage setLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    public RegistrationPage setEmail(String value) {
        emailInput.setValue(value);
        return this;
    }

    public RegistrationPage setPhoneNumber(String value) {
        phoneNumberInput.setValue(value);
        return this;
    }

    public RegistrationPage setGender(String value) {
        genderInput.$(byText(value)).click();
        return this;
    }

    public RegistrationPage setBirthDate(String day, String month, String year) {
        dateOfBirthInput.click();
        calendar.setDate(day, month, year);
        return this;
    }

    public RegistrationPage setHobbies(List<String> hobbies) {
        hobbies.forEach(value -> hobbiesInput.$(byText(value)).click());
        return this;
    }

    public RegistrationPage setSubjects(List<String> subjects) {
        subjects.forEach(subject -> subjectsInput.setValue(subject).pressEnter());
        return this;
    }

    public RegistrationPage uploadPictureFromClassPath(String path) {
        pictureUploader.uploadFromClasspath(path);
        return this;
    }

    public RegistrationPage setAddressInfo(String value) {
        addressInput.setValue(value);
        return this;
    }

    public RegistrationPage setState(String value) {
        stateInput.click();
        stateCityWrapper.$(byText(value)).click();
        return this;
    }

    public RegistrationPage setCity(String value) {
        cityInput.click();
        stateCityWrapper.$(byText(value)).click();
        return this;
    }

    public void submit() {
        submitButton.click();
    }

    public RegistrationPage verifyResultsModalAppears() {
        registrationResultsModal.verifyModalAppears();
        return this;
    }

    public RegistrationPage verifyResult(String key, String value) {
        registrationResultsModal.verifyResult(key, value);
        return this;
    }
}