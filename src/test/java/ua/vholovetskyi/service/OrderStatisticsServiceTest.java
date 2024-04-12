package ua.vholovetskyi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.vholovetskyi.dao.OrderStatisticsDao;
import ua.vholovetskyi.handler.StatisticsHandler;
import ua.vholovetskyi.service.dto.Statistics;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderStatisticsServiceTest {

    @Mock
    OrderStatisticsDao orderStatisticsDao;
    @Mock
    StatisticsHandler statisticsHandler;
    @InjectMocks
    private OrderStatisticsServiceImpl orderStatisticsService;

    @Test
    void should_return_statistics() {
        //given
        var items = givenItems();

        //when
        when(orderStatisticsDao.getStatistics(statisticsHandler)).thenReturn(items);
        Statistics statistics = orderStatisticsService.getStatistics(statisticsHandler);

        //then
        verify(orderStatisticsDao, times(1)).getStatistics(any(StatisticsHandler.class));
        assertThat(statistics.getItems().size()).isEqualTo(3);
    }

    @Test
    void should_return_empty_statistics() {
        //given
        var items = givenEmptyItems();

        //when
        when(orderStatisticsDao.getStatistics(statisticsHandler)).thenReturn(items);
        Statistics statistics = orderStatisticsService.getStatistics(statisticsHandler);

        //then
        verify(orderStatisticsDao, times(1)).getStatistics(any(StatisticsHandler.class));
        assertThat(statistics.getItems().size()).isEqualTo(0);
    }

    private Statistics givenItems() {
        List<Statistics.ItemStatistics> items = List.of(
                new Statistics.ItemStatistics("CANCELED", 3),
                new Statistics.ItemStatistics("NEW", 2),
                new Statistics.ItemStatistics("PAID", 1)
        );
        return new Statistics(items);
    }

    private Statistics givenEmptyItems() {
        return new Statistics(List.of());
    }
}
