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

    public static String getValidCard() {
        return "4444 4444 4444 4441";
    }
    public static String getStatusValidCard = "APPROVED";
    public static String getIinvalidCard() {
        return "4444 4444 4444 4442";
    }
    public static String getStatusInvalidCard = "DECLINED";

    public static String getErrorLessCard() {
        return "4444 4444 4444 444";
    }

    public static String getRandomCard() {
        return "1234 5678 9123 4567";
    }

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
    public static String getInvalidYear() { return "8"; }
    public static String getYearFuture() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String getYearPast() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String getValidOwner() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }
    public static String getInvalidOwner() {
        Random random = new Random();
        String [] name = new String [] {"Yulia", "Alex", "12345", "Agent007", "Darina!", "Анна-Мария"};
        return name[random.nextInt(6)];
    }
    public static String getValidCode() {
        return faker.number().digits(3);
    }
    public static String getInvalidLittleCode() {
        return faker.number().digits(2);
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
