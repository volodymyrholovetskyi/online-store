package ua.vholovetskyi;

import ua.vholovetskyi.dao.OrderStatisticsDao;
import ua.vholovetskyi.dao.OrderStatisticsDaoImpl;
import ua.vholovetskyi.handler.ItemHandler;
import ua.vholovetskyi.handler.OrderDateHandler;
import ua.vholovetskyi.handler.StatisticsHandler;
import ua.vholovetskyi.handler.StatusHandler;
import ua.vholovetskyi.input.UserInputArgument;
import ua.vholovetskyi.input.UserInputManager;
import ua.vholovetskyi.service.OrderStatisticsService;
import ua.vholovetskyi.service.OrderStatisticsServiceImpl;
import ua.vholovetskyi.service.dto.Statistics;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-3
 */
public class OnlineStoreApplication {

    private static final Logger LOG = Logger.getLogger(OnlineStoreApplication.class.getName());
    private static final String FILE_TEMPLATE = "statistics_by_%s.xml";

    public static void main(String[] args) {
        new OnlineStoreApplication().start(args);
    }

    /**
     * Is the entry point to our program
     *
     * @param args parameters entered by the user
     */
    public void start(String[] args) {
        LOG.info("Start app...");

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            var userInputManager = new UserInputManager(args);
            var input = userInputManager.processArgs();

            for (String path : input.getFiles()) {
                executorService.execute(() -> {
                    //get statistics
                    OrderStatisticsDao orderStatisticsDao = new OrderStatisticsDaoImpl(path);
                    OrderStatisticsService orderService = new OrderStatisticsServiceImpl(orderStatisticsDao);
                    Statistics statistics = orderService.getStatistics(getCurrentHandler(input));

                    //save statistics
                    OrderStatisticsDao saveOrderStatistics = new OrderStatisticsDaoImpl(getFileName(input.getAttribute()));
                    OrderStatisticsService saveOrderService = new OrderStatisticsServiceImpl(saveOrderStatistics);
                    saveOrderService.saveStatistics(statistics);
                });
            }
            executorService.shutdown();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.WARNING, "Validation exception: %s".formatted(e.getMessage()));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unknown error", e);
        }
    }

    private StatisticsHandler getCurrentHandler(UserInputArgument input) {
        List<StatisticsHandler> handlers = listHandlers(input);

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

    private List<StatisticsHandler> listHandlers(UserInputArgument input) {
        return List.of(
                new ItemHandler(),
                new StatusHandler(input.getStatusParams()),
                new OrderDateHandler());
    }

    private String getFileName(String attribute) {
        return FILE_TEMPLATE.formatted(attribute);
    }
}