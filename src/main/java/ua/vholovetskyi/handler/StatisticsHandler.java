package ua.vholovetskyi.handler;

import ua.vholovetskyi.model.Order;

import java.util.Map;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-05
 */
public interface StatisticsHandler {

    /**
     * Checks if the CommandHandler handles the given attribute
     *
     * @param attributeName attribute from the user
     * @return true if the handler can handle the user's attribute and false if not
     */
    boolean supports(String attributeName);

    /**
     * Count items
     *
     * @param order the object from which the data for the corresponding handler will be extracted
     * @param itemCount accumulates data (key - value extract from object, value - count item)
     */
    void handle(Order order, Map<String, Integer> itemCount);
}
