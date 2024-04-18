package ua.vholovetskyi;

import ua.vholovetskyi.dao.OrderStatisticsDao;
import ua.vholovetskyi.dao.OrderStatisticsDaoImpl;
import ua.vholovetskyi.input.UserInputArgument;
import ua.vholovetskyi.input.UserInputManager;
import ua.vholovetskyi.report.OrderStatisticalReport;
import ua.vholovetskyi.report.OrderStatisticsReportXml;
import ua.vholovetskyi.service.OrderStatisticsService;
import ua.vholovetskyi.service.OrderStatisticsServiceImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-03
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

            for (String fileName : input.getFiles()) {
                executorService.execute(() -> {
                    OrderStatisticsDao orderStatisticsDao = new OrderStatisticsDaoImpl(fileName);
                    OrderStatisticalReport statisticalReport = new OrderStatisticsReportXml(getFileName(input));
                    OrderStatisticsService orderService = new OrderStatisticsServiceImpl(input, orderStatisticsDao, statisticalReport);
                    orderService.generateStatistics();
                });
            }
            executorService.shutdown();
        } catch (IllegalArgumentException e) {
            LOG.log(Level.WARNING, "Validation exception: %s".formatted(e.getMessage()));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unknown error", e);
        }
    }

    private String getFileName(UserInputArgument input) {
        return FILE_TEMPLATE.formatted(input.getAttribute());
    }
}