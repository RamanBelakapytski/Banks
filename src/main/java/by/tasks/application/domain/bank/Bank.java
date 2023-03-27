package by.tasks.application.domain.bank;

import by.tasks.application.domain.customer.CustomerType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Bank {
    private UUID id;

    private String name;

    private Map<CustomerType, BigDecimal> fee;

    public Bank(UUID id, String name, Map<CustomerType, BigDecimal> fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
    }

    public Bank(String name, BigDecimal legalFee, BigDecimal naturalFee) {
        this.name = name;
        this.fee = Map.of(CustomerType.LEGAL, legalFee, CustomerType.NATURAL, naturalFee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(name, bank.name) && Objects.equals(fee, bank.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fee);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<CustomerType, BigDecimal> getFee() {
        return fee;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFee(Map<CustomerType, BigDecimal> fee) {
        this.fee = fee;
    }

    public void setFee(BigDecimal legalFee, BigDecimal naturalFee) {
        this.fee = Map.of(CustomerType.LEGAL, legalFee, CustomerType.NATURAL, naturalFee);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fee=" + fee +
                '}';
    }
}
