package ua.vholovetskyi.handler;

import ua.vholovetskyi.model.Order;

import java.util.Map;

import static ua.vholovetskyi.utils.Attributes.ITEM;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
public class ItemHandler extends BaseHandler {

    @Override
    protected String getArgumentName() {
        return ITEM;
    }

    @Override
    public void handle(Order order, Map<String, Integer> itemCount) {
        countItems(order, itemCount);
    }

    private void countItems(Order order, Map<String, Integer> itemCount) {
        for (String item : order.getItems()) {
            if (itemCount.containsKey(item)) {
                Integer value = itemCount.get(item);
                itemCount.put(item, value + 1);
            } else {
                itemCount.put(item, 1);
            }
        }
    }
}

