package com.testasaitec.domain.offers;

import com.testasaitec.domain.order.Order;

import java.util.List;
import java.util.Optional;


import static com.testasaitec.domain.offers.OfferUtils.duplicateOffer;

/**
 * As far as I understood this rule will be applied only for the first orange, even if there is more offers it shouldn`t be
 * applied
 */
public class PearsDiscountOfferRule implements OfferRule{

    private static final String PEAR_CODE = "Pear";

    private static final long MINIMUM_AMOUNT_OF_MONEY_WASTED = 4;

    private static final double DISCOUNT_TO_BE_APPLIED = 1.0;

    private static final String OFFER_DESCRIPTION = "For every 4 € spent on Pears, we will deduct one euro from your final invoice";

    public static final String ID = "PearsDiscountOfferRule";

    public boolean isApplicable(Order order) {
        return amountOfMoneyWastedInPears(order) >= MINIMUM_AMOUNT_OF_MONEY_WASTED;

    }

    @Override
    public Optional<List<Offer>> createOfferIfApplicable(Order order){

        if(!isApplicable(order)) return Optional.empty();

        long timesToApplyOffer = (long) Math.floor(amountOfMoneyWastedInPears(order) / MINIMUM_AMOUNT_OF_MONEY_WASTED);

        Offer offerTemplate = Offer.of(ID, OFFER_DESCRIPTION, DISCOUNT_TO_BE_APPLIED);

        return Optional.of(duplicateOffer(offerTemplate, timesToApplyOffer));
    }

    @Override
    public String getRuleId() {
        return ID;
    }

    private double amountOfMoneyWastedInPears(Order order) {
        return order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getProduct().getCode().equals(PEAR_CODE))
                .mapToDouble(orderItem -> orderItem.getTotal() )
                .sum();
    }


}
