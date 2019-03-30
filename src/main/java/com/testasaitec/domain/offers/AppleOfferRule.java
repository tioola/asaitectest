package com.testasaitec.domain.offers;

import com.testasaitec.domain.order.Order;

import java.util.List;
import java.util.Optional;

import static com.testasaitec.domain.offers.OfferUtils.duplicateOffer;

public class AppleOfferRule implements OfferRule {

    private static final String APPLE_CODE = "Apple";

    private static final long AMOUNT_OF_APPLES_TO_APPLY_DISCOUNT = 3;

    private static final String OFFER_DESCRIPTION = "Buy 3 apples, pay 2";

    public static final String ID = "AppleOfferRule";

    public boolean isApplicable(Order order) {
        return amountOfApplesFound(order) >= AMOUNT_OF_APPLES_TO_APPLY_DISCOUNT;
    }

    public Optional<List<Offer>> createOfferIfApplicable(Order order) {

        if (!isApplicable(order)) return Optional.empty();

        Optional<Double> priceOfProduct = order.getPriceOfProductCode(APPLE_CODE);

        if(!priceOfProduct.isPresent()) throw new RuntimeException("Something is wrong in apple offer rule, value of the product should be returned");

        //Always checking fresh value from database.
        Offer offerTemplate = Offer.of(ID, OFFER_DESCRIPTION, priceOfProduct.get());

        long timesToApplyOffer = (long) Math.floor(amountOfApplesFound(order) / AMOUNT_OF_APPLES_TO_APPLY_DISCOUNT);
        return Optional.of(duplicateOffer(offerTemplate, timesToApplyOffer));
    }

    @Override
    public String getRuleId() {
        return ID;
    }

    private long amountOfApplesFound(Order order) {
        return order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getProduct().getCode().equals(APPLE_CODE))
                .mapToLong(orderItem -> orderItem.getAmount())
                .sum();
    }


}
