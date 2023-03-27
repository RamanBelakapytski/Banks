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
    private boolean manualTransactionManagement = false;
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

            connection.setAutoCommit(false);

            createBankTable();
            createCustomerTable();
            createAccountTable();
            createTransactionTable();

            System.out.println("DB initialization completed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setManualTransactionManagement(boolean manual) {
        this.manualTransactionManagement = manual;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createBankTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE bank (
                    id UUID PRIMARY KEY,
                    name VARCHAR NOT NULL,
                    natural_fee NUMERIC NOT NULL,
                    legal_fee NUMERIC NOT NULL
                    )
                    """);
        }
    }

    private void createCustomerTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE customer (
                    id UUID PRIMARY KEY,
                    name VARCHAR NOT NULL,
                    customer_type VARCHAR NOT NULL
                    )
                    """);
        }
    }

    private void createAccountTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE account (
                    id UUID PRIMARY KEY,
                    customer_id UUID REFERENCES customer(id) NOT NULL,
                    bank_id UUID REFERENCES bank(id) NOT NULL,
                    currency VARCHAR NOT NULL,
                    balance NUMERIC NOT NULL
                    )
                    """);
        }
    }

    private void createTransactionTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE transaction (
                    id UUID PRIMARY KEY,
                    account_to_id UUID REFERENCES account(id) NOT NULL,
                    account_from_id UUID REFERENCES account(id) NOT NULL,
                    currency VARCHAR NOT NULL,
                    amount NUMERIC NOT NULL,
                    fee NUMERIC NOT NULL,
                    datetime TIMESTAMP NOT NULL
                    )
                    """);
        }
    }

    private class ConnectionProxyHandler implements InvocationHandler {

        private final Connection original;

        public ConnectionProxyHandler(Connection original) {
            this.original = original;
        }

        public Object invoke(Object proxy, Method method, Object[] args)
                throws IllegalAccessException, IllegalArgumentException,
                InvocationTargetException {

            if (method.getName().equals("close") && !manualTransactionManagement) {
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
