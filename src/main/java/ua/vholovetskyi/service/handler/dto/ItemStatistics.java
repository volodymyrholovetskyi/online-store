package ua.vholovetskyi.service.handler.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class OrderStatistics {
    @JacksonXmlProperty(localName = "item")
    private final String item;
    @JacksonXmlProperty(localName = "count")
    private final long count;

    public OrderStatistics(String item, long count) {
        this.item = item;
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "OrderStatistics{" +
                "fieldName='" + item + '\'' +
                ", count=" + count +
                '}';
    }
}
