package ua.vholovetskyi.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum Attribute {

    ITEM("items"),
    ORDER_DATE("orderDate"),

    STATUS("status");

    private final String name;

    Attribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Attribute parseString(String name) {
        return Arrays.stream(values())
                .filter(it -> StringUtils.equalsIgnoreCase(it.getName(), name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported attribute!"));
    }
}
