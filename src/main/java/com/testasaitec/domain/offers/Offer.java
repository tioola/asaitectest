package com.testasaitec.domain.offers;

import java.util.Objects;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.StringUtils.isEmpty;

public class Offer {

    private String id;

    private String offerDescription;

    private Double discountValue;

    public Offer(String id, String offerDescription, Double discountValue) {

        if(isEmpty(offerDescription)) throw new IllegalArgumentException("Offer should have a description");
        if(isEmpty(id)) throw new IllegalArgumentException("Offer should have an Id");
        if(isNull(discountValue)) throw new IllegalArgumentException("You should inform the discount value");
        if(discountValue <= 0.0 ) throw new IllegalArgumentException("The discount value should be positive and different of 0");

        this.offerDescription = offerDescription;
        this.discountValue = discountValue;
        this.id = id;

    }


    // USing static constructor to keep the application more readable and more flexible (good practice)
    /**
     * @param offerDescription description of the offer
     * @param discountValue value that will be discounted, this value should be higher than 0.0
     * @return
     */
    public static Offer of(String id, String offerDescription, Double discountValue){
        return new Offer(id, offerDescription, discountValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer discounts = (Offer) o;
        return offerDescription.equals(discounts.offerDescription) &&
                discountValue.equals(discounts.discountValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerDescription, discountValue);
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public String getId() { return id; }

    @Override
    public String toString() {
        return offerDescription.concat(" , ").concat(getDiscountValue().toString());

    }
}

