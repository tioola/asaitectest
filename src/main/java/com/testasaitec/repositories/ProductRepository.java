package com.testasaitec.repositories;

import com.testasaitec.domain.product.Product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProductRepository {

    private static final Set<Product> products = new HashSet<>();

    public static void addProduct(Product product){
        products.add(product);
    }

    public static Optional<Product> findProductByCode(String code){
        return products.stream()
                .filter(product -> product.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

}
