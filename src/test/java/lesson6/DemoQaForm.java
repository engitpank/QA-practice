package lesson6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DemoQaForm extends TestBase {
    RegistrationPage registrationPage = new RegistrationPage();

    private final String
            firstName = "Ivan",
            lastName = "Ivanov",
            email = "ivanov@gmail.com",
            phoneNumber = "8312323212",
            gender = "Male",
            birthDay = "20",
            birthMonth = "July",
            birthYear = "2000",
            picturePath = "eva.jpg",
            address = "Some address 1",
            state = "NCR",
            city = "Delhi";
    private final List<String>
            hobbies = List.of("Sports", "Reading", "Music"),
            subjects = List.of("Maths", "Physics", "Computer Science");

    @Test
    @DisplayName("Автотест на проверку формы https://demoqa.com/automation-practice-form")
    void RegistrationPageShouldSubmitFilledForm() {
        registrationPage.openPage()
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
                .setCity(city)
                .submit();

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
    }
}