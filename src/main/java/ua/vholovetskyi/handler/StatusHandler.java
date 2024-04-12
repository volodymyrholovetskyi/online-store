package ua.vholovetskyi.handler;

import ua.vholovetskyi.model.Order;
import ua.vholovetskyi.model.OrderStatus;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static ua.vholovetskyi.utils.Attributes.STATUS;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
public class StatusHandler extends BaseHandler {

    private static final Logger LOG = Logger.getLogger(StatusHandler.class.getName());
    private final Set<OrderStatus> statusParams;

    public StatusHandler(Set<OrderStatus> statusParams) {
        this.statusParams = statusParams;
    }

    @Override
    protected String getArgumentName() {
        return STATUS;
    }

    @Override
    public void handle(Order order, Map<String, Integer> statusCount) {
        countOrderStatus(order, statusCount);
    }

    private void countOrderStatus(Order order, Map<String, Integer> statusCount) {
        var status = getStatusString(order);
        if (statusParams.isEmpty() || statusParams.contains(order.getStatus())) {
            if (statusCount.containsKey(status)) {
                Integer value = statusCount.get(status);
                statusCount.put(status, value + 1);
            } else {
                statusCount.put(status, 1);
            }
        }
    }

    private static String getStatusString(Order order) {
        return String.valueOf(order.getStatus());
    }
}

