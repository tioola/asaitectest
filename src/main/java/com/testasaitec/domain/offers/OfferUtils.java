package com.testasaitec.domain.offers;

import java.util.ArrayList;
import java.util.List;

public class OfferUtils {

    public static final List<Offer> duplicateOffer(Offer offer, long times){

        List<Offer> duplicatedOffers = new ArrayList<>();

        for(int i =0; i< times; i++){
            duplicatedOffers.add(offer);
        }

        return duplicatedOffers;
    }
}
