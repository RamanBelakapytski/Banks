package by.tasks.application.domain.account;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Account {
    private UUID id;
    private UUID customerId;
    private UUID bankId;
    private Currency currency;
    private BigDecimal balance;


    public Account(UUID id, UUID customerId, UUID bankId, Currency currency, BigDecimal balance) {
        this.id = id;
        this.customerId = customerId;
        this.bankId = bankId;
        this.currency = currency;
        this.balance = balance;
    }

    public Account(UUID customerId, UUID bankId, Currency currency, BigDecimal balance) {
        this.customerId = customerId;
        this.bankId = bankId;
        this.currency = currency;
        this.balance = balance;
    }

    private Account(Builder builder) {
        setId(builder.id);
        setCustomerId(builder.customerId);
        setBankId(builder.bankId);
        setCurrency(builder.currency);
        setBalance(builder.balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(customerId, account.customerId) && Objects.equals(bankId, account.bankId) && Objects.equals(currency, account.currency) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, bankId, currency, balance);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getBankId() {
        return bankId;
    }

    public void setBankId(UUID bankId) {
        this.bankId = bankId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", bankId=" + bankId +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }

    public static final class Builder {
        private UUID id;
        private UUID customerId;
        private UUID bankId;
        private Currency currency;
        private BigDecimal balance;

        public Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder customerId(UUID val) {
            customerId = val;
            return this;
        }

        public Builder bankId(UUID val) {
            bankId = val;
            return this;
        }

        public Builder currency(Currency val) {
            currency = val;
            return this;
        }

        public Builder balance(BigDecimal val) {
            balance = val;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
