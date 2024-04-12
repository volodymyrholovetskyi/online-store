package ua.vholovetskyi.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ua.vholovetskyi.dao.OrderStatisticsDaoImpl;
import ua.vholovetskyi.handler.ItemHandler;
import ua.vholovetskyi.handler.OrderDateHandler;
import ua.vholovetskyi.handler.StatusHandler;
import ua.vholovetskyi.service.dto.Statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderStatisticsServiceIT {
    private OrderStatisticsService orderService;
    private String readPath = "./order_test/test.json";
    private String savePath = "./order_test/statistics_by_%s_test.xml";

    @AfterAll
    static void deleteFiles() throws IOException {
        Files.deleteIfExists(Paths.get("order_test/statistics_by_status_test.xml"));
        Files.deleteIfExists(Paths.get("order_test/statistics_by_item_test.xml"));
        Files.deleteIfExists(Paths.get("order_test/statistics_by_orderDate_test.xml"));
    }

    @Test
    void should_return_statistics_for_status() {
        //given
        var orderStatisticsDao = new OrderStatisticsDaoImpl(readPath);
        this.orderService = new OrderStatisticsServiceImpl(orderStatisticsDao);
        Statistics expectedStatistics = givenStatisticsForStatus();

        //when
        Statistics actualStatistics = orderService.getStatistics(new StatusHandler(Set.of()));

        //then
        assertThat(actualStatistics).isEqualTo(expectedStatistics);
    }

    @Test
    void should_save_statistics_for_status() {
        //given
        var orderStatisticsDao = new OrderStatisticsDaoImpl(savePath.formatted("status"));
        this.orderService = new OrderStatisticsServiceImpl(orderStatisticsDao);
        Statistics expectedStatistics = givenStatisticsForStatus();

        //when
        orderService.saveStatistics(expectedStatistics);

        //then
        assertTrue(Files.exists(Paths.get("order_test/statistics_by_status_test.xml")));
    }

    @Test
    void should_return_statistics_for_item() {
        //given
        var orderStatisticsDao = new OrderStatisticsDaoImpl(readPath);
        this.orderService = new OrderStatisticsServiceImpl(orderStatisticsDao);
        var expectedStatistics = givenStatisticsForItem();

        //when
        Statistics actualStatistics = orderService.getStatistics(new ItemHandler());

        //then
        assertThat(actualStatistics).isEqualTo(expectedStatistics);
    }

    @Test
    void should_save_statistics_for_item() {
        //given
        var orderStatisticsDao = new OrderStatisticsDaoImpl(savePath.formatted("item"));
        this.orderService = new OrderStatisticsServiceImpl(orderStatisticsDao);
        var statistics = givenStatisticsForItem();

        //when
        orderService.saveStatistics(statistics);

        //then
        assertTrue(Files.exists(Paths.get("order_test/statistics_by_item_test.xml")));

    }

    @Test
    void should_return_statistics_for_orderDate() {
        //given
        var orderStatisticsDao = new OrderStatisticsDaoImpl(readPath);
        this.orderService = new OrderStatisticsServiceImpl(orderStatisticsDao);
        var expectedStatistics = givenStatisticsForOrderDate();

        //when
        Statistics actualStatistics = orderService.getStatistics(new OrderDateHandler());

        //then
        assertEquals(expectedStatistics, actualStatistics);
    }

    @Test
    void should_save_statistics_for_orderDate() {
        //given
        var orderStatisticsDao = new OrderStatisticsDaoImpl(savePath.formatted("orderDate"));
        this.orderService = new OrderStatisticsServiceImpl(orderStatisticsDao);
        var statistics = givenStatisticsForOrderDate();

        //when
        orderService.saveStatistics(statistics);

        //then
        assertTrue(Files.exists(Paths.get("order_test/statistics_by_orderDate_test.xml")));
    }

    private Statistics givenStatisticsForStatus() {
        List<Statistics.ItemStatistics> countItem = List.of(
                new Statistics.ItemStatistics("PAID", 5),
                new Statistics.ItemStatistics("NEW", 2),
                new Statistics.ItemStatistics("SHIPPED", 2),
                new Statistics.ItemStatistics("CANCELED", 1));

        return new Statistics(countItem);
    }

    private Statistics givenStatisticsForItem() {
        List<Statistics.ItemStatistics> countItem = List.of(
                new Statistics.ItemStatistics("Java 8 in Action", 5),
                new Statistics.ItemStatistics("Java Database", 4),
                new Statistics.ItemStatistics("Effective Java", 4),
                new Statistics.ItemStatistics("Java Concurrency in practice", 3),
                new Statistics.ItemStatistics("Design Patterns", 2));
        return new Statistics(countItem);
    }

    private Statistics givenStatisticsForOrderDate() {
        List<Statistics.ItemStatistics> countItem = List.of(
                new Statistics.ItemStatistics("2024-01-25", 5),
                new Statistics.ItemStatistics("2024-02-04", 3),
                new Statistics.ItemStatistics("2024-01-26", 1),
                new Statistics.ItemStatistics("2024-02-05", 1));
        return new Statistics(countItem);
    }
}