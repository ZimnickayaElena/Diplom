package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.DriverManager;


public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }
    private static String datasource = System.getProperty("datasource.url");

   @SneakyThrows
    public static DataHelper.CreditRequestEntityInfo getCreditRequestInfo() {
        var runner = new QueryRunner();
        var creditRequestInfo = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            return runner.query(connection, creditRequestInfo, new BeanHandler<>(DataHelper.CreditRequestEntityInfo.class));
        }
    }

    @SneakyThrows
    public static DataHelper.PaymentEntityInfo getPaymentInfo() {
        var runner = new QueryRunner();
        var paymentInfo = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            return runner.query(connection, paymentInfo, new BeanHandler<>(DataHelper.PaymentEntityInfo.class));
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var runner = new QueryRunner();
        var deleteFromOrder = "DELETE FROM order_entity;";
        var deleteFromCredit = "DELETE FROM credit_request_entity;";
        var deleteFromPayment = "DELETE FROM payment_entity;";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            runner.update(connection, deleteFromOrder);
            runner.update(connection, deleteFromCredit);
            runner.update(connection, deleteFromPayment);
        }
    }
}
