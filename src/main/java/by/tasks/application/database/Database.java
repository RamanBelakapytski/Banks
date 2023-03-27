package by.tasks.application.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final Connection connection;
    private static final String URL = "jdbc:h2:mem:";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public Database() {
        try {
            System.out.println("DB initialization started");

            connection = (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class[]{Connection.class},
                    new ConnectionProxyHandler(DriverManager.getConnection(URL, USER, PASSWORD))
            );


            createBankTable();
            createCustomerTable();

            System.out.println("DB initialization completed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void createBankTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE bank (id uuid, name varchar, natural_fee NUMERIC, legal_fee NUMERIC)");
        }
    }

    private void createCustomerTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE customer (id uuid, name varchar, customer_type varchar)");
        }
    }

    private record ConnectionProxyHandler(Connection original) implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args)
                    throws IllegalAccessException, IllegalArgumentException,
                    InvocationTargetException {

                if (method.getName().equals("close")) {
                    try {
                        original.commit();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }

                return method.invoke(original, args);
            }
        }
}
