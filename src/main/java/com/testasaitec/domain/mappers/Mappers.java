package com.testasaitec.domain.mappers;

import com.testasaitec.domain.order.Order;
import com.testasaitec.domain.order.OrderItem;
import com.testasaitec.domain.product.Product;
import com.testasaitec.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class Mappers {

    public static List<Product> mapLinesToProducts(List<String> lines){
        return lines.stream()
                .map(line -> line.split(","))
                .map(lineSplit -> Product.of(lineSplit[0].trim(), Double.parseDouble(lineSplit[1].trim())))
                .collect(Collectors.toList());
    }

    public static Order mapLinesToOrder(List<String> lines){

        List<OrderItem> orderItems = lines.stream()
                .map(line -> line.split(","))
                .map(lineSplit -> OrderItem.of(
                        ProductRepository.findProductByCode(lineSplit[0].trim()).get(),
                        Long.parseLong(lineSplit[1].trim())))
                .collect(Collectors.toList());

        return Order.of(orderItems);
    }

}
