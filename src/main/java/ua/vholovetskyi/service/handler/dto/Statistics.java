package ua.vholovetskyi.service.handler.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "statistics")
public class Statisticse {

    @JacksonXmlElementWrapper(localName = "orders")
    @JacksonXmlProperty(localName = "order")
    private List<OrderStatistics> orders = new ArrayList<>();

    public void addStatistics(OrderStatistics statistics) {
        if (statistics == null) {
            throw new IllegalArgumentException("OrderStatistics cannot be null!!!");
        }
        orders.add(statistics);
    }

    public List<OrderStatistics> getOrders() {
        return orders;
    }

    public void addAll(List<OrderStatistics> orders) {
        this.orders = orders;
    }
}
