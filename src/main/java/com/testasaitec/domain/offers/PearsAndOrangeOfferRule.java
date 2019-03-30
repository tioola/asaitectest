package com.testasaitec.domain.offers;

import com.testasaitec.domain.order.Order;
import com.testasaitec.domain.order.OrderItem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PearsAndOrangeOfferRule implements OfferRule {

    private static final String PEAR_CODE = "Pear";

    private static final String ORANGE_CODE = "Orange";

    private static final long AMOUNT_OF_PEARS_TO_APPLY_DISCOUNT = 2;

    private static final long AMOUNT_OF_ORANGES_TO_APPLY_DISCOUNT = 1;

    private static final String OFFER_DESCRIPTION = "Take 2 Pears and the first Orange is free.";

    public static final String ID = "PearsAndOrangeOfferRule";


    public boolean isApplicable(Order order) {
        return amountOfPersFound(order) >= AMOUNT_OF_PEARS_TO_APPLY_DISCOUNT &&
                amountOfOrangesFound(order) >= AMOUNT_OF_ORANGES_TO_APPLY_DISCOUNT;
    }

    @Override
    public Optional<List<Offer>> createOfferIfApplicable(Order order) {

        Optional<Double> orangePrice = order.getPriceOfProductCode(ORANGE_CODE);

        if (!isApplicable(order)) return Optional.empty();

        if (!orangePrice.isPresent())
            throw new RuntimeException("Something is wrong in pears and orangeoffer rule, value of the product should be returned");


        Offer offerTemplate = Offer.of(ID, OFFER_DESCRIPTION, orangePrice.get());

        return Optional.of(Arrays.asList(offerTemplate));
    }

    @Override
    public String getRuleId() {
        return ID;
    }

    private long amountOfPersFound(Order order) {
        return order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getProduct().getCode().equals(PEAR_CODE))
                .mapToLong(OrderItem::getAmount)
                .sum();
    }

    private long amountOfOrangesFound(Order order) {
        return order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getProduct().getCode().equals(ORANGE_CODE))
                .mapToLong(OrderItem::getAmount)
                .sum();
    }
}
