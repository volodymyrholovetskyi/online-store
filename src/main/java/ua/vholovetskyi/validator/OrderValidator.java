package ua.vholovetskyi.validator;

import ua.vholovetskyi.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderValidator<T> implements Validator<Order> {

    private final Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(Order order) {

        if (order == null) {
            errors.put("Order object", "null");
            return errors;
        }

        if (order.getCustomer() == null) {
            errors.put("Customer object", "null");
            return errors;
        }

        var items = order.getItems();
        if (items == null || items.isEmpty()) {
            errors.put("Item object", "The order must contain at least one item");
            return errors;
        }
        return errors;
    }
}
