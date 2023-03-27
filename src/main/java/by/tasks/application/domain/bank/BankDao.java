package by.tasks.application.domain.bank;

import by.tasks.application.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static by.tasks.application.domain.customer.CustomerType.LEGAL;
import static by.tasks.application.domain.customer.CustomerType.NATURAL;

public class BankDao {

    private final Database database;

    public BankDao(Database database) {
        this.database = database;
    }

    public List<Bank> findAll() {
        final var arrayList = new ArrayList<Bank>();

        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("select id, name, legal_fee, natural_fee from bank");
             var resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                arrayList.add(toBank(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    public Bank save(Bank bank) {
        if (bank.getId() == null) {
            bank.setId(UUID.randomUUID());
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("INSERT INTO bank (id, name, legal_fee, natural_fee) VALUES (?, ?, ?, ?)")
            ) {
                statement.setObject(1, bank.getId());
                statement.setString(2, bank.getName());
                statement.setBigDecimal(3, bank.getFee().get(LEGAL));
                statement.setBigDecimal(4, bank.getFee().get(NATURAL));
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        } else {
            try (var connection = database.getConnection();
                 var statement = connection.prepareStatement("UPDATE bank SET name = ?, legal_fee = ?, natural_fee = ? WHERE id = ?")
            ) {
                statement.setString(1, bank.getName());
                statement.setBigDecimal(2, bank.getFee().get(LEGAL));
                statement.setBigDecimal(3, bank.getFee().get(NATURAL));
                statement.setObject(4, bank.getId());
                statement.executeUpdate();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return bank;
    }

    public Optional<Bank> findById(UUID id) {
        try (var connection = database.getConnection();
             var statement = connection.prepareStatement("select id, name, legal_fee, natural_fee from bank where id = ?")) {
            statement.setObject(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(toBank(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
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

    private Bank toBank(ResultSet resultSet) throws SQLException {
        return new Bank(
                (java.util.UUID) resultSet.getObject("id"),
                resultSet.getString("name"),
                Map.of(
                        LEGAL, resultSet.getBigDecimal("legal_fee"),
                        NATURAL, resultSet.getBigDecimal("natural_fee")
                ));
    }

    private PreparedStatement existsStatement(Connection connection, String name) throws SQLException {
        var statement = connection.prepareStatement("SELECT COUNT(*) FROM bank WHERE name = ?");
        statement.setString(1, name);
        return statement;
    }
}
