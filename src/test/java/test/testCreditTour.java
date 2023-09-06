package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.*;
import page.PaymentPage;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testCreditTour {

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080");
    }
    @AfterEach
    void teardown() {
        SQLHelper.cleanDatabase();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Позитивный сценарий через \"Кредит по данным карты\" для пользователя с валидными данными")
    void shouldSuccessfullyCreditWithValidUser() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankAccept();
        var expected = DataHelper.getStatusValidCard;
        var actual = SQLHelper.getCreditRequestInfo();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для невалидного номера карты")
    void shouldCreditWithInvalidCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getIinvalidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
        var expected = DataHelper.getStatusInvalidCard;
        var actual = SQLHelper.getCreditRequestInfo();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного номера карты (менее 16)")
    void shouldCreditWithErrorLessCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getErrorLessCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного номера карты")
    void shouldCreditWithRandomCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для пустого поля номера карты")
    void shouldCreditWithEmptyCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getEmptySpace();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного месяца срока действия карты")
    void shouldCreditWithMoreMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getMonthMorePossible();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного формата месяца срока действия карты")
    void shouldCreditWithErrorMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getInvalidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для пустого поля месяца срока действия карты")
    void shouldCreditWithEmptyMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getEmptySpace();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного формата года срока действия карты")
    void shouldCreditWithInvalidYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getInvalidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного года срока действия карты (более 6 лет)")
    void shouldCreditWithMoreYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearFuture();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного года срока действия карты (ранее текущей даты)")
    void shouldCreditWithBeforeYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearPast();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorYearCard();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для пустого поля года срока действия карты")
    void shouldCreditWithEmptyYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptySpace();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного имени пользователя")
    void shouldCreditWithErrorOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для пустого имени пользователя")
    void shouldCreditWithEmptyOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmptySpace();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для неверного кода карты (менее 3 цифр)")
    void shouldCreditWithLittleCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getInvalidLittleCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Кредит по данным карты\" для пустого поля кода карты")
    void shouldCreditWithEmptyCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getEmptySpace();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }
}

