package com.example.checkoutportal.services.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductType {
    BREAD("Bread"),
    VEGETABLE("Vegetable"),
    BEER("Beer");

    private final String value;

    ProductType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ProductType fromString(String value) {
        for (ProductType type : ProductType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
