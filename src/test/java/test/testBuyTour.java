package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.*;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import org.openqa.selenium.InvalidSelectorException;
import static data.SQLHelper.*;
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

    @Test
    //@DisplayName("Позитивный сценарий через \"Оплату по карте\" для пользователя с валидными данными")
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


}
