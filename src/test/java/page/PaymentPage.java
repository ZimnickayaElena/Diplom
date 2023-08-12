package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class PaymentPage {

        private SelenideElement buyButton = $(byText("Купить"));
        private SelenideElement creditButton = $(byText("Купить в кредит"));
        private SelenideElement headingPay = $(byText("Оплата по карте"));
        private SelenideElement headingCredit = $(byText("Кредит по данным карты"));
        private SelenideElement numberCardField = $(".input__top")
                .find(String.valueOf(exactText("Номер карты")));
        private SelenideElement monthField = $(".input__top")
                .find(String.valueOf(exactText("Месяц")));
        private SelenideElement yearField = $(".input__top")
                .find(String.valueOf(exactText("Год")));
        private SelenideElement ownerField = $(".input__top")
                .find(String.valueOf(exactText("Владелец")));
        private SelenideElement codeCardField = $(".input__top")
                .find(String.valueOf(exactText("CVC/CVV")));
        private SelenideElement errorField = $(".input__sub")
                .find(String.valueOf(exactText("Неверный формат")));
        private SelenideElement emptySpase = $(".input__sub")
                .find(String.valueOf(exactText("Поле обязательно для заполнения")));
        private SelenideElement continueButton = $("button")
                .find(String.valueOf(exactText("Продолжить")));
        private final SelenideElement notificationSuccessfully = $(".notification_status_ok");
        private final SelenideElement notificationError = $(".notification_status_error");

        public void cardPayment() {
                buyButton.click();
                headingPay.shouldBe(visible);
        }
        public void cardCredit() {
                creditButton.click();
                headingCredit.shouldBe(visible);
        }

        public void fillingForms(String cardNumber, String month, String year, String cardOwner, String code) {
                numberCardField.sendKeys(cardNumber);
                monthField.sendKeys(month);
                yearField.sendKeys(year);
                ownerField.sendKeys(cardOwner);
                codeCardField.sendKeys(code);
                continueButton.click();
        }

        public void bankAccept() {
                notificationSuccessfully.shouldBe(visible);
        }
        public void bankReject() {
                notificationError.shouldBe(visible);
        }


}
