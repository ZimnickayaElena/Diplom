package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;


import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }
    private static String datasource = System.getProperty("datasource.url");

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(datasource, "app", "pass");
    }

   @SneakyThrows
    public static DataHelper.CreditRequestEntityInfo getCreditRequestInfo() {
        var runner = new QueryRunner();
        var creditRequestInfo = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            return runner.query(connection, creditRequestInfo, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getPaymentInfo() {
        QueryRunner runner = new QueryRunner();
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            return runner.query(connection, SqlStatus, new ScalarHandler<>());
        }
    }

   @SneakyThrows
    public static void cleanDatabase() {
        QueryRunner runner = new QueryRunner();
        try (var connection = getConnection()){
            runner.execute(connection, "DELETE FROM credit_request_entity");
            runner.execute(connection, "DELETE FROM order_entity");
            runner.execute(connection, "DELETE FROM payment_entity");
        }
    }
}
