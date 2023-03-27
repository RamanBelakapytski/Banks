package by.tasks.application.domain.account;

import by.tasks.application.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountDao {

    private final Database database;

    public AccountDao(Database database) {
        this.database = database;
    }

    public Account save(Account account) {
        if (account.getId() == null) {
            account.setId(UUID.randomUUID());
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("INSERT INTO account (id, customer_id, bank_id, currency, balance) VALUES (?, ?, ?, ?, ?)")
            ) {
                statement.setObject(1, account.getId());
                statement.setObject(2, account.getCustomerId());
                statement.setObject(3, account.getBankId());
                statement.setString(4, account.getCurrency().name());
                statement.setBigDecimal(5, account.getBalance());
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("UPDATE account SET customer_id = ?, bank_id = ?, currency = ?, balance = ? WHERE id = ?")
            ) {
                statement.setObject(1, account.getCustomerId());
                statement.setObject(2, account.getBankId());
                statement.setString(3, account.getCurrency().name());
                statement.setBigDecimal(4, account.getBalance());
                statement.setObject(5, account.getId());
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return account;
    }

    public Optional<Account> findById(UUID id) {
        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("SELECT id, customer_id, bank_id, currency, balance FROM account WHERE id = ?")) {
            statement.setObject(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(toAccount(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public List<Account> getCustomerAccounts(UUID customerId) {
        final var arrayList = new ArrayList<Account>();

        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("SELECT id, customer_id, bank_id, currency, balance FROM account WHERE customer_id = ? ")
        ) {
            statement.setObject(1, customerId);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    arrayList.add(toAccount(resultSet));
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return arrayList;
    }

    public boolean existsByBankAndCustomer(UUID bankId, UUID customerId) {
        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("SELECT COUNT(*) FROM account WHERE bank_id = ? AND customer_id = ?")
        ) {
            statement.setObject(1, bankId);
            statement.setObject(2, customerId);

            try (var resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    private Account toAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                (UUID) resultSet.getObject("id"),
                (UUID) resultSet.getObject("customer_id"),
                (UUID) resultSet.getObject("bank_id"),
                Currency.valueOf(resultSet.getString("currency")),
                resultSet.getBigDecimal("balance"));
    }
}
