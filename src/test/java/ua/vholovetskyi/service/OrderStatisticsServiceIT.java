package ua.vholovetskyi.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.vholovetskyi.dao.OrderStatisticsDaoImpl;
import ua.vholovetskyi.input.UserInputArgument;
import ua.vholovetskyi.report.OrderStatisticsReportXml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("supportAttributes")
    void should_generate_statistics_report_for_all_attributes(String attribute) {
        //given
        var fileName = savePath.formatted(attribute);
        var input = new UserInputArgument(new String[]{"order_test", attribute});
        var orderStatisticsDao = new OrderStatisticsDaoImpl(readPath);
        var statisticsReport = new OrderStatisticsReportXml(fileName);
        this.orderService = new OrderStatisticsServiceImpl(input, orderStatisticsDao, statisticsReport);

        //when
        orderService.generateStatistics();

        //then
        assertTrue(Files.exists(Paths.get(fileName)));
    }

    private static Stream<Arguments> supportAttributes() {
        return Stream.of(
                Arguments.of("item"),
                Arguments.of("status"),
                Arguments.of("orderDate")
        );
    }
}