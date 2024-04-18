package ua.vholovetskyi.service;

import ua.vholovetskyi.dao.OrderStatisticsDao;
import ua.vholovetskyi.handler.ItemHandler;
import ua.vholovetskyi.handler.OrderDateHandler;
import ua.vholovetskyi.handler.StatisticsHandler;
import ua.vholovetskyi.handler.StatusHandler;
import ua.vholovetskyi.input.UserInputArgument;
import ua.vholovetskyi.report.OrderStatisticalReport;
import ua.vholovetskyi.service.dto.Statistics;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-04
 */
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    private static final Logger LOG = Logger.getLogger(OrderStatisticsServiceImpl.class.getName());
    private final OrderStatisticsDao orderStatisticsDao;
    private final OrderStatisticalReport orderStatisticalReport;
    private final UserInputArgument input;
    private final List<StatisticsHandler> handlers;

    public OrderStatisticsServiceImpl(UserInputArgument input, OrderStatisticsDao orderStatisticsDao,
                                      OrderStatisticalReport orderStatisticalReport) {
        this.input = input;
        this.orderStatisticalReport = orderStatisticalReport;
        this.orderStatisticsDao = orderStatisticsDao;
        this.handlers = List.of(
                new StatusHandler(input.getStatusParams()),
                new ItemHandler(),
                new OrderDateHandler()
        );
    }

    @Override
    public void generateStatistics() {
        LOG.log(Level.INFO, "Start generating statistics...");
        Statistics statistics = orderStatisticsDao.getStatisticsDescOrder(getCurrentHandler());
        orderStatisticalReport.saveReport(statistics);
        LOG.log(Level.INFO, "The size of the saved statistics items: [%d].".formatted(statistics.getItems().size()));
    }

    private StatisticsHandler getCurrentHandler() {
        Optional<StatisticsHandler> currentHandler = Optional.empty();
        for (StatisticsHandler handler : handlers) {
            if (handler.supports(input.getAttribute())) {
                currentHandler = Optional.of(handler);
                break;
            }
        }
        return currentHandler
                .orElseThrow(() -> new IllegalArgumentException("Unsupported handler for the attribute: %s".formatted(input.getAttribute())));
    }
}
