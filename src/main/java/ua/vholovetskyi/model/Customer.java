package ua.vholovetskyi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String city;
    private String zipCod;

}
