package ua.vholovetskyi.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String city;
    private String zipCod;
    private List<Order> orders = new ArrayList<>();
}
