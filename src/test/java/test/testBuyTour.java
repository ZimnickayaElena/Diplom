package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testBuyTour {

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
    @DisplayName("Позитивный сценарий через \"Оплату по карте\" для пользователя с валидными данными")
    void shouldSuccessfullyPayWithValidUser() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankAccept();
        var expected = DataHelper.getStatusValidCard;
        var actual = SQLHelper.getPaymentInfo();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для невалидного номера карты")
    void shouldPayWithInvalidCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getIinvalidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
        var expected = DataHelper.getStatusInvalidCard;
        var actual = SQLHelper.getPaymentInfo();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного номера карты (менее 16)")
    void shouldPayWithErrorLessCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getErrorLessCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного номера карты")
    void shouldPayWithRandomCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для пустого поля номера карты")
    void shouldPayWithEmptyCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getEmptySpace();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного месяца срока действия карты")
    void shouldPayWithMoreMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getMonthMorePossible();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного формата месяца срока действия карты")
    void shouldPayWithErrorMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getInvalidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для пустого поля месяца срока действия карты")
    void shouldPayWithEmptyMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getEmptySpace();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного формата года срока действия карты")
    void shouldPayWithInvalidYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getInvalidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного года срока действия карты (более 6 лет)")
    void shouldPayWithMoreYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearFuture();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного года срока действия карты (ранее текущей даты)")
    void shouldPayWithBeforeYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearPast();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorYearCard();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для пустого поля года срока действия карты")
    void shouldPayWithEmptyYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptySpace();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного имени пользователя")
    void shouldPayWithErrorOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для пустого имени пользователя")
    void shouldPayWithEmptyOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmptySpace();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для неверного кода карты (менее 3 цифр)")
    void shouldPayWithLittleCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getInvalidLittleCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }

    @Test
    @DisplayName("Негативный сценарий через \"Оплату по карте\" для пустого поля кода карты")
    void shouldPayWithEmptyCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getEmptySpace();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }





}
