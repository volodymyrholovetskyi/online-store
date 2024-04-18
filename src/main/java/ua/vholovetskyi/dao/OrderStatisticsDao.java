package ua.vholovetskyi.dao;

import ua.vholovetskyi.handler.StatisticsHandler;
import ua.vholovetskyi.service.dto.Statistics;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
public interface OrderStatisticsDao {

    /**
     * Retrieves statistics.
     *
     * @param handler passes the found handler to process statistics.
     * @return a list of ItemStatistics in descending order.
     */
    Statistics getStatisticsDescOrder(StatisticsHandler handler);

}
