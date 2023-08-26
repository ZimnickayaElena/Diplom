package data;


import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;


public class DataHelper {
    private static final Faker faker = new Faker(new Locale("ru"));
    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("app", "pass");
    }
    public static String getValidCard() {
        return "4444 4444 4444 4441";
    }

    public static String getStatusValidCard = "APPROVED";
    public static String getIinvalidCard = "4444 4444 4444 4442";
    public static String getStatusInvalidCard = "DECLINED";
    public static String getValidMonth() {
        Random random = new Random();
        String [] month = new String [] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        return month[random.nextInt(12)];
    }
    public static String getMonthMorePossible() {
        return "15";
    }
    public static String getInvalidMonth() {
        return "5";
    }
    public static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }
    private static String getInvalidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    }
    public static String getYearFuture() {
        return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String getYearPast() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String getValidOwner() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }
    public static String getInvalidOwner() {
        Random random = new Random();
        String [] name = new String [] {"Юлия", "Александр", "Ян", "12345", "Agent007", "Darina!", "Анна-Мария"};
        return name[random.nextInt(7)];
    }
    public static String getValidCode() {
        return faker.number().digits(3);
    }
    public static String getInvalidLittleCode() {
        return IntStream.range(1, 100).toString();
    }
    public static String getInvalidBigCode() {
        return IntStream.of( 1000).toString();
    }
    public static String getEmptySpace() { return " "; }

    @Value
    public static class CardInfo {
        private String numberCard;
        private String month;
        private String year;
        private String owner;
        private String code;
    }


    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Data
    @RequiredArgsConstructor
    public class CreditRequestEntityInfo {
        private String id;
        private String bank_id;
        private String created;
        private String status;
    }

    @Data
    @RequiredArgsConstructor
    public class PaymentEntityInfo {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id;
    }

    @Data
    @RequiredArgsConstructor
    public class OrderEntityInfo {
        private String id;
        private String created;
        private String credit_id;
        private String payment_id;
    }

}
