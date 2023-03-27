package by.tasks.application.domain.customer;

import by.tasks.application.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                 var statement = connection.prepareStatement("INSERT INTO customer (id, name, legal_fee, natural_fee) VALUES (?, ?, ?, ?)")
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
                 var statement = connection.prepareStatement("UPDATE customer SET name = ?, customerType = ? WHERE id = ?")
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
             var statement = connection.prepareStatement("select id, name, customer_type from customer");
             var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                arrayList.add(toCustomer(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    private Customer toCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(
                (java.util.UUID) resultSet.getObject("id"),
                resultSet.getString("name"),
                CustomerType.valueOf(resultSet.getString("customer_type")));
    }

}
