package ua.vholovetskyi.statistics;

import ua.vholovetskyi.dao.OrderDao;
import ua.vholovetskyi.model.AttributeType;

import java.util.logging.Logger;

public class StatusStatisticsHandler extends BaseStatisticsHandler{

    private static Logger LOG = Logger.getLogger(StatusStatisticsHandler.class.getName());

    private static final String COMMAND_NAME = "status";

    public StatusStatisticsHandler(OrderDao orderDao) {
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

