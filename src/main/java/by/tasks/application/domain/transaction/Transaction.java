package by.tasks.application.domain.transaction;

import by.tasks.application.domain.account.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    UUID id;
    BigDecimal amount;
    BigDecimal fee;
    LocalDateTime dateTime;
    Currency currency;
    UUID accountFrom;
    UUID accountTo;


    public Transaction(UUID id, BigDecimal amount, BigDecimal fee, LocalDateTime dateTime, Currency currency, UUID accountFrom, UUID accountTo) {
        this.id = id;
        this.amount = amount;
        this.fee = fee;
        this.dateTime = dateTime;
        this.currency = currency;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    public Transaction(BigDecimal amount, BigDecimal fee, LocalDateTime dateTime, Currency currency, UUID accountFrom, UUID accountTo) {
        this.amount = amount;
        this.fee = fee;
        this.dateTime = dateTime;
        this.currency = currency;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", fee=" + fee +
                ", dateTime=" + dateTime +
                ", currency=" + currency +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(fee, that.fee) && Objects.equals(dateTime, that.dateTime) && currency == that.currency && Objects.equals(accountFrom, that.accountFrom) && Objects.equals(accountTo, that.accountTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, fee, dateTime, currency, accountFrom, accountTo);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public UUID getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(UUID accountFrom) {
        this.accountFrom = accountFrom;
    }

    public UUID getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(UUID accountTo) {
        this.accountTo = accountTo;
    }
}
