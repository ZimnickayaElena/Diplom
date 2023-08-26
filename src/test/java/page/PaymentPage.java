package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class PaymentPage {

        private SelenideElement buyButton = $(byText("Купить"));
        private SelenideElement creditButton = $(byText("Купить в кредит"));
        private SelenideElement headingPay = $(byText("Оплата по карте"));
        private SelenideElement headingCredit = $(byText("Кредит по данным карты"));
        private SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000']");
        private SelenideElement monthField = $("[placeholder='08']");
        private SelenideElement yearField = $("[placeholder='22']");
        private SelenideElement ownerField = $$("[class='input__control']").get(3);;
        private SelenideElement codeCardField = $("[placeholder='999']");
        private SelenideElement errorField = $(".input__sub")
                .find(String.valueOf(exactText("Неверный формат")));
        private SelenideElement emptySpase = $(".input__sub")
                .find(String.valueOf(exactText("Поле обязательно для заполнения")));
        private SelenideElement continueButton = $(byText("Продолжить"));
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

        public void fillForm (String cardNumber, String month, String year, String cardOwner, String code) {
                numberCardField.sendKeys(cardNumber);
                monthField.sendKeys(month);
                yearField.sendKeys(year);
                ownerField.sendKeys(cardOwner);
                codeCardField.sendKeys(code);
                continueButton.click();
        }

        public void bankAccept() {
                notificationSuccessfully.shouldBe(visible, Duration.ofSeconds(15));
        }
        public void bankReject() {
                notificationError.shouldBe(visible);
        }


}
