package by.tasks.application.domain.customer;

import by.tasks.application.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerDao {
    private final Database database;

    public CustomerDao(Database database) {
        this.database = database;
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(UUID.randomUUID());
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("INSERT INTO customer (id, name, customer_type) VALUES (?, ?, ?)")
            ) {
                statement.setObject(1, customer.getId());
                statement.setString(2, customer.getName());
                statement.setString(3, customer.getCustomerType().name());
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("UPDATE customer SET name = ?, customer_type = ? WHERE id = ?")
            ) {
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getCustomerType().name());
                statement.setObject(3, customer.getId());
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return customer;
    }

    public List<Customer> findAll() {
        final var arrayList = new ArrayList<Customer>();

        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("SELECT id, name, customer_type FROM customer");
             var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                arrayList.add(toCustomer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    public Optional<Customer> findById(UUID id) {
        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("SELECT id, name, customer_type FROM customer WHERE id = ?")) {
            statement.setObject(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(toCustomer(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    private Customer toCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(
                (UUID) resultSet.getObject("id"),
                resultSet.getString("name"),
                CustomerType.valueOf(resultSet.getString("customer_type")));
    }

    public boolean existsByName(String name) {
        try (var connection = database.getConnection();
             var statement = existsStatement(connection, name);
             var resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    private PreparedStatement existsStatement(Connection connection, String name) throws SQLException {
        var statement = connection.prepareStatement("SELECT COUNT(*) FROM customer WHERE name = ?");
        statement.setString(1, name);
        return statement;
    }

}
