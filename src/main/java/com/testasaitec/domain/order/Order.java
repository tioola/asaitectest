package com.testasaitec.domain.order;

import com.testasaitec.domain.offers.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class Order {

    private List<OrderItem> orderItems;

    private Optional<List<Offer>> offers;


    public Order(List<OrderItem> orderItems, List<Offer> offers){

        if (isEmpty(orderItems)) throw new IllegalArgumentException("Order should have at least one item");

        this.orderItems = orderItems;
        this.offers = Optional.ofNullable(offers);
    }


    public static final Order of(List<OrderItem> orderItems){
        return new Order(orderItems, null);
    }

    public static final Order withMoreOffers(Order order, List<Offer> offers){

        if(isEmpty(offers)) throw new IllegalArgumentException("Offers should be passed");

        List<Offer> appendedOffersList = new ArrayList<>();

        if(order.getOffers().isPresent()){
            appendedOffersList.addAll(order.getOffers().get());
        }

        appendedOffersList.addAll(offers);
        //Remember that we are trying to keep immutability for the sake of scalability and resilience,
        // therefore we need to create a new order here.
        return new Order(order.getOrderItems(), appendedOffersList);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Optional<List<Offer>> getOffers() {
        return offers;
    }


    //We need to make sure that we will use the price of the item in the order.
    public Optional<Double> getPriceOfProductCode(String productCode){
        return orderItems.stream()
                .filter(item -> item.getProduct().getCode().equals(productCode))
                .map(OrderItem::getProductValue)
                .findFirst();
    }

    @Override
    public String toString() {

        String productsDesc = this.orderItems.stream()
                .map(orderItem -> orderItem.toString())
                .collect(Collectors.joining("\n"));

        String offersDesc = "";

        if(offers.isPresent()){
            offersDesc = this.offers.get().stream()
                    .map(offer -> offer.toString())
                    .collect(Collectors.joining("\n"));
        }

        return "-----Invoice----- \n"
                .concat("PRODUCT, VALUE, AMOUNT, TOTAL \n")
                .concat("\n\n")
                .concat(productsDesc)
                .concat("\n\n")
                .concat("OFFER, DISCOUNT \n")
                .concat("\n\n")
                .concat(offersDesc)
                .concat("\n\n")
                .concat("Total > " + getTotal().toString());

    }


    public Double getTotal(){

        double totalOfProducts = getOrderItems().stream()
                .mapToDouble(orderItems -> orderItems.getTotal())
                .sum();

        double totalOfDiscounts = 0.0;

        if(offers.isPresent() && isNotEmpty(offers.get())){
            totalOfDiscounts = offers.get()
                    .stream()
                    .mapToDouble(offer -> offer.getDiscountValue())
                    .sum();

        }
        return totalOfProducts - totalOfDiscounts;
    }


}
