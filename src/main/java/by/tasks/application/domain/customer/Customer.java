package by.tasks.application.domain.customer;

import java.util.Objects;
import java.util.UUID;

public class Customer {
    private UUID id;
    private String name;
    private CustomerType customerType;

    public Customer(String name, CustomerType customerType) {
        this.name = name;
        this.customerType = customerType;
    }

    public Customer(UUID id, String name, CustomerType customerType) {
        this.id = id;
        this.name = name;
        this.customerType = customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && customerType == customer.customerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customerType);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerType=" + customerType +
                '}';
    }
}
