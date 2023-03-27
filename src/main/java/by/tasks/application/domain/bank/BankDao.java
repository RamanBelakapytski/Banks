package by.tasks.application.domain.bank;

import by.tasks.application.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    private Bank toBank(ResultSet resultSet) throws SQLException {
        return new Bank(
                (java.util.UUID) resultSet.getObject("id"),
                resultSet.getString("name"),
                Map.of(
                        LEGAL, resultSet.getBigDecimal("legal_fee"),
                        NATURAL, resultSet.getBigDecimal("natural_fee")
                ));
    }
}
