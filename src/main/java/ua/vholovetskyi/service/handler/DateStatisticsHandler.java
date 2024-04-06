package ua.vholovetskyi.statistics;

import ua.vholovetskyi.dao.OrderDao;
import ua.vholovetskyi.model.AttributeType;

import java.util.logging.Logger;

public class DateStatisticsHandler extends BaseStatisticsHandler {
    private static Logger LOG = Logger.getLogger(DateStatisticsHandler.class.getName());

    private static final String COMMAND_NAME = "date";

    public DateStatisticsHandler(OrderDao orderDao) {
        super(orderDao);
    }

    @Override
    protected String getArgName() {
        return COMMAND_NAME;
    }

    @Override
    public Statistics handle(AttributeType attribute) {
        return null;
    }
}
