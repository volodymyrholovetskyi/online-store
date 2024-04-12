package ua.vholovetskyi.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.vholovetskyi.model.Customer;
import ua.vholovetskyi.model.Order;
import ua.vholovetskyi.model.OrderStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;


import static ua.vholovetskyi.utils.Attributes.*;
import static org.assertj.core.api.Assertions.assertThat;

class StatisticsHandlerTest {

    private List<StatisticsHandler> statisticsHandlers;

    @BeforeEach
    void setUp() {
        statisticsHandlers = List.of(
                new StatusHandler(Set.of()),
                new ItemHandler(),
                new OrderDateHandler()
        );
    }

    @ParameterizedTest
    @MethodSource("supportAttributes")
    void should_support_user_input_attributes(String attribute) {
        //given
        //when
        boolean support = statisticsHandlers.stream()
                .anyMatch(statisticsHandler -> statisticsHandler.supports(attribute));

        //then
        assertThat(support).isTrue();
    }

    @ParameterizedTest
    @MethodSource("unsupportedAttributes")
    void should_throw_exception_for_unsupported_attribute(String attribute) {
        //given
        //when
        boolean support = statisticsHandlers.stream()
                .anyMatch(statisticsHandler -> statisticsHandler.supports(attribute));

        //then
        assertThat(support).isFalse();
    }


    @Test
    void should_handle_status() {
        //given
        StatisticsHandler statusHandler = getCurrentHandler(STATUS);
        Map<String, Integer> items = givenItems();
        List<Order> orders = givenOrders();

        //when
        orders.forEach(o -> statusHandler.handle(o, items));

        //then
        assertThat(items.get(OrderStatus.NEW.name())).isEqualTo(2);
        assertThat(items.get(OrderStatus.PAID.name())).isEqualTo(1);
    }

    @Test
    void should_handle_item() {
        //given
        StatisticsHandler itemHandle = getCurrentHandler(ITEM);
        Map<String, Integer> items = givenItems();
        List<Order> orders = givenOrders();

        //when
        orders.forEach(o -> itemHandle.handle(o, items));

        //then
        assertThat(items.get("Effective Java")).isEqualTo(3);
        assertThat(items.get("Java 8 in Action")).isEqualTo(1);
        assertThat(items.get("Design Patterns")).isEqualTo(1);
    }

    @Test
    void should_handle_orderDate() {
        //given
        StatisticsHandler orderDateHandler = getCurrentHandler(ORDER_DATE);
        Map<String, Integer> items = givenItems();
        List<Order> orders = givenOrders();

        //when
        orders.forEach(order -> orderDateHandler.handle(order, items));

        //then
        assertThat(items.get("2024-02-10")).isEqualTo(3);
    }


    private static Stream<Arguments> supportAttributes() {
        return Stream.of(
                Arguments.of("item"),
                Arguments.of("status"),
                Arguments.of("orderDate")
        );
    }

    private static Stream<Arguments> unsupportedAttributes() {
        return Stream.of(
                Arguments.of("customer"),
                Arguments.of("id"),
                Arguments.of("name"),
                Arguments.of(null, null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("\n")
        );
    }

    private static List<Order> givenOrders() {
        return List.of(
                new Order(
                        1L,
                        List.of("Effective Java", "Java 8 in Action", "Design Patterns"),
                        new Customer(
                                1L,
                                "fcustomer@gmail.com",
                                "First",
                                "Customer",
                                "+380965643222",
                                "Ruska 1",
                                "Ternopil",
                                "46001"
                        ),
                        OrderStatus.NEW,
                        LocalDate.parse("2024-02-10")

                ),
                new Order(
                        2L,
                        List.of("Effective Java"),
                        new Customer(
                                2L,
                                "scustomer@gmail.com",
                                "Second",
                                "Customer",
                                "+380965643222",
                                "Ruska 1",
                                "Kyiv",
                                "47001"
                        ),
                        OrderStatus.NEW,
                        LocalDate.parse("2024-02-10")
                ),
                new Order(
                        3L,
                        List.of("Effective Java"),
                        new Customer(
                                3L,
                                "tcustomer@gmail.com",
                                "Third",
                                "Customer",
                                "+380965643222",
                                "Ruska 1",
                                "Kyiv",
                                "47001"
                        ),
                        OrderStatus.PAID,
                        LocalDate.parse("2024-02-10")
                )
        );
    }

    private static Map<String, Integer> givenItems() {
        return new HashMap<>();
    }

    private StatisticsHandler getCurrentHandler(String attribute) {
        return statisticsHandlers.stream().
                filter(statisticsHandler -> statisticsHandler.supports(attribute))
                .findFirst().orElseThrow();
    }
}