package ua.vholovetskyi.dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ua.vholovetskyi.handler.StatisticsHandler;
import ua.vholovetskyi.model.Customer;
import ua.vholovetskyi.model.Order;
import ua.vholovetskyi.model.OrderStatus;
import ua.vholovetskyi.service.dto.Statistics;
import ua.vholovetskyi.utils.CustomerFields;
import ua.vholovetskyi.validator.CustomerValidator;
import ua.vholovetskyi.validator.OrderValidator;
import ua.vholovetskyi.validator.Validator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ua.vholovetskyi.utils.CustomerFields.*;
import static ua.vholovetskyi.utils.OrderFields.ID;
import static ua.vholovetskyi.utils.OrderFields.*;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
public class OrderStatisticsDaoImpl implements OrderStatisticsDao {
    private static final Logger LOG = Logger.getLogger(OrderStatisticsDaoImpl.class.getName());
    private final Validator<Order> orderValidator;
    private final Validator<Customer> customerValidator;
    private final String fileName;
    private final ObjectMapper jsonMapper;
    private final ObjectMapper xmlMapper;

    public OrderStatisticsDaoImpl(String fileName) {
        this.orderValidator = new OrderValidator<>();
        this.customerValidator = new CustomerValidator<>();
        this.fileName = fileName;
        this.jsonMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Statistics getStatistics(StatisticsHandler handler) {
        LOG.log(Level.INFO, "The beginning of the formation of statistics...");
        final var countItem = new HashMap<String, Integer>();
        try (JsonParser jsonParser = jsonMapper.createParser(Files.readString(Paths.get(fileName), StandardCharsets.UTF_8))) {
            if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    Order order = mappingOrder(jsonParser);
                    orderValidator.validate(order);
                    customerValidator.validate(order.getCustomer());
                    handler.handle(order, countItem);
                }
            }
            LOG.log(Level.INFO, "The end of the formation of statistics...");
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error in getStatistics() method! Error message: %s".formatted(e.getMessage()));
        }
        return new Statistics(sortItem(countItem));
    }

    @Override
    public void saveStatistics(Statistics statistics) {
        try (var bufferedWriter = Files.newBufferedWriter(Paths.get(fileName))) {
            xmlMapper.writeValue(bufferedWriter, statistics);
            LOG.log(Level.INFO, "Done! Statistics written to [%s].".formatted(fileName));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error in saveStatistics() method! Error message: %s".formatted(e.getMessage()));
        }
    }

    private Order mappingOrder(JsonParser jsonParser) throws IOException {
        Order order = new Order();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            var fieldName = jsonParser.currentName();
            if (ID.equals(fieldName)) {
                jsonParser.nextToken();
                order.setId(jsonParser.getLongValue());
            } else if (STATUS.equals(fieldName)) {
                jsonParser.nextToken();
                order.setStatus(OrderStatus.parseString(jsonParser.getText()));
            } else if (ITEMS.equals(fieldName)) {
                List<String> items = new ArrayList<>();
                if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                        items.add(jsonParser.getText());
                    }
                }
                order.setItems(items);
            } else if (CUSTOMER.equals(fieldName)) {
                Customer customer = new Customer();
                if (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                        String innerFieldName = jsonParser.currentName();
                        if (CustomerFields.ID.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setId(jsonParser.getLongValue());
                        } else if (EMAIL.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setEmail(jsonParser.getText());
                        } else if (FIRST_NAME.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setFirstName(jsonParser.getText());
                        } else if (LAST_NAME.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setLastName(jsonParser.getText());
                        } else if (PHONE.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setPhone(jsonParser.getText());
                        } else if (STREET.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setStreet(jsonParser.getText());
                        } else if (CITY.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setCity(jsonParser.getText());
                        } else if (ZIP_CODE.equals(innerFieldName)) {
                            jsonParser.nextToken();
                            customer.setZipCod(jsonParser.getText());
                        }
                        order.setCustomer(customer);
                    }
                }
            } else if (ORDER_DATE.equals(fieldName)) {
                jsonParser.nextToken();
                order.setOrderDate(LocalDate.parse(jsonParser.getText()));
            }
        }
        return order;
    }

    private List<Statistics.ItemStatistics> sortItem(Map<String, Integer> countItem) {
        return countItem.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(item -> new Statistics.ItemStatistics(item.getKey(), item.getValue()))
                .toList();
    }
}
