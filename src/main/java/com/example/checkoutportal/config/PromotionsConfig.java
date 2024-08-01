package com.example.checkoutportal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "promotions")
public class PromotionsConfig {
    private BreadDiscountConfig bread;
    private VegetableDiscountConfig vegetable;
    private BeerDiscountConfig beer;

    public BreadDiscountConfig getBread() {
        return bread;
    }

    public void setBread(BreadDiscountConfig bread) {
        this.bread = bread;
    }

    public VegetableDiscountConfig getVegetable() {
        return vegetable;
    }

    public void setVegetable(VegetableDiscountConfig vegetable) {
        this.vegetable = vegetable;
    }

    public BeerDiscountConfig getBeer() {
        return beer;
    }

    public void setBeer(BeerDiscountConfig beer) {
        this.beer = beer;
    }
}