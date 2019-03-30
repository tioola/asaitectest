package com.testasaitec.domain.offers;

import com.testasaitec.domain.order.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class PromotionManager {

    private static PromotionManager promotionManager = new PromotionManager();

    private static final List<OfferRule> offersAvailable = Arrays.asList(
            new AppleOfferRule(),
            new PearsDiscountOfferRule(),
            new PearsAndOrangeOfferRule()
    );

    private PromotionManager() {}

    //Creating static constructor as a good practice for flexibility and scalability, for now this will be singleton .
    public static PromotionManager getInstance() {
        return promotionManager;
    }

    public Order applyOffersFor(Order order) {

        List<Offer> offers = offersAvailable.stream()
                .map(offersAvailable -> offersAvailable.createOfferIfApplicable(order))
                .filter(Optional::isPresent)
                .flatMap(presentOffers -> presentOffers.get().stream())
                .collect(Collectors.toList());

        if(isEmpty(offers)){
            return order;
        }

        return Order.withMoreOffers(order, offers);
    }


}
