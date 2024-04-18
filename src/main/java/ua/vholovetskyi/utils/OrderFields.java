package ua.vholovetskyi.utils;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-06
 */
public final class OrderFields {

    private OrderFields() {
        throw new UnsupportedOperationException("Not allowed to create an instance!");
    }

    public static final String ID = "id";
    public static final String ITEMS = "items";
    public static final String CUSTOMER = "customer";
    public static final String STATUS = "status";
    public static final String ORDER_DATE = "orderDate";

}
