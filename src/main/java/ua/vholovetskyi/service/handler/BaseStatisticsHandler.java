package ua.vholovetskyi.statistics;

import ua.vholovetskyi.dao.OrderDao;
import ua.vholovetskyi.model.AttributeType;

public abstract class BaseStatisticsHandler implements StatisticsHandler {

    protected final OrderDao orderDao;
    protected BaseStatisticsHandler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    @Override
    public boolean supports(AttributeType attributeName) {
        return getArgName().equals(attributeName.getName());
    }

    protected abstract String getArgName();
}
