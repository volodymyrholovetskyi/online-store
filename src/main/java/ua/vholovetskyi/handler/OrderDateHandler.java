package ua.vholovetskyi.handler;

import ua.vholovetskyi.model.Order;

import java.util.Map;

import static ua.vholovetskyi.utils.Attributes.*;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
public class OrderDateHandler extends BaseHandler {
    @Override
    protected String getArgumentName() {
        return ORDER_DATE;
    }

    @Override
    public void handle(Order order, Map<String, Integer> itemCount) {
        countOrderDate(order, itemCount);
    }

    private void countOrderDate(Order order, Map<String, Integer> orderDateCount) {
        var orderDate = getOrderDateString(order);
        if (orderDateCount.containsKey(orderDate)) {
            Integer value = orderDateCount.get(orderDate);
            orderDateCount.put(orderDate, value + 1);
        } else {
            orderDateCount.put(orderDate, 1);
        }
    }

    private static String getOrderDateString(Order order) {
        return String.valueOf(order.getOrderDate());
    }
}
