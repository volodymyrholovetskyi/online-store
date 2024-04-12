package ua.vholovetskyi.service;

import ua.vholovetskyi.dao.OrderStatisticsDao;
import ua.vholovetskyi.handler.StatisticsHandler;
import ua.vholovetskyi.service.dto.Statistics;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-4
 */
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    private static final Logger LOG = Logger.getLogger(OrderStatisticsServiceImpl.class.getName());
    private final OrderStatisticsDao orderStatisticsDao;

    public OrderStatisticsServiceImpl(OrderStatisticsDao orderStatisticsDao) {
        this.orderStatisticsDao = orderStatisticsDao;
    }

    @Override
    public Statistics getStatistics(StatisticsHandler statisticsHandler) {
        LOG.log(Level.INFO, "Start processing statistics...");
        Statistics statistics = orderStatisticsDao.getStatistics(statisticsHandler);
        LOG.log(Level.INFO, "End of processing statistics...");
        return statistics;
    }

    @Override
    public void saveStatistics(Statistics statistics) {
        LOG.log(Level.INFO, "Start saving items...");
        orderStatisticsDao.saveStatistics(statistics);
        LOG.log(Level.INFO, "The size of the saved statistics items: [%d].".formatted(statistics.getItems().size()));
    }
}
