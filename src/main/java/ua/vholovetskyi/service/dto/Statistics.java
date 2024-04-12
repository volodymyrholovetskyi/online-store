package ua.vholovetskyi.service.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "statistics")
public class Statistics {
    @JacksonXmlElementWrapper(localName = "orders")
    @JacksonXmlProperty(localName = "item")
    private List<ItemStatistics> items = new ArrayList<>();

    public record ItemStatistics(@JacksonXmlProperty(localName = "value") String item,
                                 @JacksonXmlProperty(localName = "count") Integer count) {
    }
}


