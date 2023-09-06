package page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class PaymentPage {

    private final SelenideElement notificationSuccessfully = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement buyButton = $(byText("Купить"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));
    private final SelenideElement headingPay = $(byText("Оплата по карте"));
    private final SelenideElement headingCredit = $(byText("Кредит по данным карты"));
    private final SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
        private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement ownerField = $$("[class='input__control']").get(3);
    private final SelenideElement codeCardField = $("[placeholder='999']");
    private final SelenideElement errorField = $(byText("Неверный формат"));
    private final SelenideElement errorCard = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement emptySpase = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement errorYearCard = $(byText("Истёк срок действия карты"));
    private final SelenideElement continueButton = $(byText("Продолжить"));

    public void cardPayment() {
        buyButton.click();
        headingPay.shouldBe(visible);
    }

    public void cardCredit() {
        creditButton.click();
        headingCredit.shouldBe(visible);
    }

    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
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

    public void bankReject() { notificationError.shouldBe(visible, Duration.ofSeconds(15)); }

    public void errorField() {errorField.shouldBe(visible, Duration.ofSeconds(15));}
    public void errorCard() {errorCard.shouldBe(visible, Duration.ofSeconds(15));}
    public void errorYearCard() {errorYearCard.shouldBe(visible, Duration.ofSeconds(15));}


    public void emptyField() {emptySpase.shouldBe(visible, Duration.ofSeconds(15));}


}
