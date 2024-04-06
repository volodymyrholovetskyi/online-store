package ua.vholovetskyi.service.statistics;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ua.vholovetskyi.dao.OrderDao;
import ua.vholovetskyi.dao.OrderStatistics;
import ua.vholovetskyi.dao.Statisticse;
import ua.vholovetskyi.model.AttributeType;
import ua.vholovetskyi.model.Order;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static java.util.stream.Collectors.partitioningBy;

public class ItemStatisticsHandler extends BaseStatisticsHandler {

    private static Logger LOG = Logger.getLogger(ItemStatisticsHandler.class.getName());
    private static final String ATTRIBUTE_NAME = AttributeType.ITEM.getName();

    private final OrderDao orderDao;
    public ItemStatisticsHandler(String fileName) {
        super(fileName);
        this.orderDao = new OrderDao();
    }

    @Override
    protected String getArgName() {
        return ATTRIBUTE_NAME;
    }

    public Statisticse call() {
        var statistics = new Statisticse();
        Map<String, Integer> maps = new HashMap<>();
        var xml = new XmlMapper();
        try (JsonParser jsonParser = new JsonFactory().createParser(new File(fileName))) {
            if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    Optional<Order> order = orderDao.loadOrder(jsonParser);
                    countItems(order, maps);
                }
            }
            statistics.addAll(toStatistics(maps));
            xml.writeValue(new File("statistics-item.xml"), statistics);
        } catch (IOException e) {

        }
        return null;
    }

    private Map<String, Integer> countItems(Optional<Order> order, Map<String, Integer> maps) {
        if (order.isPresent()) {
            Order order1 = order.get();
            for (String item : order1.getItems()) {
                if (maps.containsKey(item)) {
                    Integer count = maps.get(item);
                    maps.put(item, count + 1);
                } else {
                    maps.put(item, 1);
                }
            }
        }
        return maps;
    }

    private List<OrderStatistics> toStatistics(Map<String, Integer> statistics) {
        return statistics.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(e -> new OrderStatistics(e.getKey(), e.getValue()))
                .toList();
    }
}

