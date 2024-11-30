package lesson6.jenkins_test_run_demo;

import com.github.javafaker.Faker;
import lesson6.RegistrationPage;
import lesson6.util.RandomElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.qameta.allure.Allure.step;

public class QaFormTest extends TestBaseExtended {
    RegistrationPage registrationPage = new RegistrationPage();
    Faker faker = new Faker(new Locale("en"));

    private final LocalDate birthDate = LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneId.systemDefault());
    private final Map<String, List<String>> stateAndCity = Map.of(
            "NCR", List.of("Delhi", "Gurgaon", "Noida"),
            "Uttar Pradesh", List.of("Agra", "Lucknow", "Merrut"),
            "Haryana", List.of("Karnal", "Panipat"),
            "Rajasthan", List.of("Jaipur", "Jaiselmer")
    );
    private final Map.Entry<String, String> randomStateAndCity = RandomElement.fromMap(stateAndCity);
    private final List<String> genders = List.of("Male", "Female", "Other");
    private final String
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            phoneNumber = faker.phoneNumber().subscriberNumber(10),
            gender = RandomElement.fromList(genders),
            birthDay = String.format("%02d", birthDate.getDayOfMonth()),
            birthMonth = birthDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
            birthYear = String.valueOf(birthDate.getYear()),
            picturePath = "eva.jpg",
            address = faker.address().fullAddress(),
            state = randomStateAndCity.getKey(),
            city = randomStateAndCity.getValue();
    private final List<String>
            hobbies = List.of("Sports", "Reading", "Music"),
            subjects = List.of("Maths", "Physics", "Computer Science");

    @Test
    @Tag("remote")
    @DisplayName("Автотест на проверку формы https://demoqa.com/automation-practice-form")
    void RegistrationPageShouldSubmitFilledForm() {
        step("Open form", () -> {
            registrationPage.openPage();
        });

        step("Fill form", () -> {
            registrationPage
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPhoneNumber(phoneNumber)
                    .setGender(gender)
                    .setBirthDate(birthDay, birthMonth, birthYear)
                    .setSubjects(subjects)
                    .setHobbies(hobbies)
                    .uploadPictureFromClassPath(picturePath)
                    .setAddressInfo(address)
                    .setState(state)
                    .setCity(city);
        });

        step("Submit form", () -> {
            registrationPage.submit();
        });

        step("Verify results", () -> {
            registrationPage.verifyResultsModalAppears()
                    .verifyResult("Student Name", String.format("%s %s", firstName, lastName))
                    .verifyResult("Student Email", email)
                    .verifyResult("Gender", gender)
                    .verifyResult("Mobile", phoneNumber)
                    .verifyResult("Date of Birth", String.format("%s %s,%s", birthDay, birthMonth, birthYear))
                    .verifyResult("Subjects", String.join(", ", subjects))
                    .verifyResult("Hobbies", String.join(", ", hobbies))
                    .verifyResult("Address", address)
                    .verifyResult("State and City", String.format("%s %s", state, city))
                    .verifyResult("Picture", picturePath);
        });
    }
}