package ua.vholovetskyi.utils;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-06
 */
public final class CustomerFields {
    private CustomerFields() {
        throw new UnsupportedOperationException("Not allowed to create an instance!");
    }

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE = "phone";
    public static final String STREET = "street";
    public static final String CITY = "city";
    public static final String ZIP_CODE = "zipCode";
}
