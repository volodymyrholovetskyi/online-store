package ua.vholovetskyi.validator;

import org.apache.commons.lang3.StringUtils;
import ua.vholovetskyi.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-6
 */
public class OrderValidator<T> implements Validator<Order> {

    private final Map<String, String> errors = new HashMap<>();

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    @Override
    public Map<String, String> validate(Order order) {

        if (order == null) {
            errors.put("Order object", "Order cannot be empty!");
            return errors;
        }

        var id = order.getId();
        if (id == null) {
            errors.put("ID", "The field cannot be empty!");
            return errors;
        }

        var customer = order.getCustomer();
        if (customer == null) {
            errors.put("Customer object", "Customer cannot be empty!");
            return errors;
        }

        var items = order.getItems();
        if (items == null || items.isEmpty()) {
            errors.put("Item object", "The order must contain at least one item!");
            return errors;
        }

        var orderDate = order.getOrderDate().toString();
        if (StringUtils.isBlank(orderDate) || !orderDateIsValid(orderDate)) {
            errors.put("Date", "Invalid field format!");
            return errors;
        }
        return errors;
    }
    private boolean orderDateIsValid(String orderDate) {
        return DATE_PATTERN.matcher(orderDate).matches();
    }
}
