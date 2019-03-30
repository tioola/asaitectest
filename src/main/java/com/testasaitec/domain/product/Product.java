package com.testasaitec.domain.product;

import java.util.Objects;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.StringUtils.isEmpty;

public class Product {

    //In the example given, the name of the fruit should be the identifier, once the file only contains 2 fields.
    private final String code;

    private final Double value;

    private Product(String code, Double value) {

        if (isEmpty(code)) throw new IllegalArgumentException("Product code must be informed");
        if (isNull(value)) throw new IllegalArgumentException("Value must be informed");

        this.code = code;
        this.value = value;
    }

    // USing static constructor to keep the application more readable and more flexible (good practice)
    public static Product of(String code, Double value){
        return new Product(code,value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    //I won`t provide setters for this class to prevent mutability, the more immutability the better
    public String getCode() {
        return code;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return code.concat(" , ").concat(value.toString()).concat("\n");
    }

}
