package ua.vholovetskyi.service;

import ua.vholovetskyi.handler.StatisticsHandler;
import ua.vholovetskyi.input.UserInputArgument;
import ua.vholovetskyi.service.dto.Statistics;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-4
 */
public interface OrderStatisticsService {

    /**
     * Generate statistics.
     */
    void generateStatistics();

}
