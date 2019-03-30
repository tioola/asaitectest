package com.testasaitec.domain.order;

import com.testasaitec.domain.product.Product;

import static java.util.Objects.requireNonNull;

public class OrderItem {

    private Product product;

    private Long amount;

    //To prevent the fact that the value of the item can be changed but the value of the order shoudn`t,
    private Double productValue;


    public OrderItem(Product product, Long amount) {
        requireNonNull(product, "Product must not be null");
        requireNonNull(amount, "Amount must not be null");

        this.product = product;
        this.amount = amount;
        this.productValue = product.getValue();
    }

    public static OrderItem of(Product product, Long amount) {
        return new OrderItem(product, amount);
    }

    public Product getProduct() {
        return product;
    }

    public Long getAmount() {
        return amount;
    }

    public Double getProductValue() {
        return productValue;
    }

    public Double getTotal(){
        return productValue * amount;
    }

    @Override
    public String toString() {
        return product.getCode().concat(", ")
                .concat(getProductValue().toString().concat(", ")
                .concat(getAmount().toString()).concat(", ")
                .concat(getTotal().toString()));
    }
}
