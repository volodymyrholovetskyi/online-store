package ua.vholovetskyi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Order {

    private List<String> items = new ArrayList<>();
    private Customer customer;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(List<String> items, Customer customer, OrderStatus status) {
        this.items = items;
        this.customer = customer;
        this.status = Optional.ofNullable(status).orElseGet(() -> OrderStatus.NEW);
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(items, order.items) && status == order.status && Objects.equals(createdAt, order.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, status, createdAt);
    }

    public int calculateNumberOfProduct() {
        return items.size();
    }
}
