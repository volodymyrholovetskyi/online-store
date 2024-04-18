package ua.vholovetskyi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private List<String> items = new ArrayList<>();
    private Customer customer;
    private OrderStatus status;
    private LocalDate orderDate;

}
