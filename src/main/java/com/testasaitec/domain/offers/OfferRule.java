package com.testasaitec.domain.offers;

import com.testasaitec.domain.order.Order;

import java.util.List;
import java.util.Optional;

interface OfferRule {

    Optional<List<Offer>> createOfferIfApplicable(Order order);

    String getRuleId();

}
