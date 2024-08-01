package com.example.checkoutportal.config;

import java.util.List;

public class BeerDiscountConfig {
    private int packSize;
    private List<OriginDiscount> originDiscounts;

    public int getPackSize() {
        return packSize;
    }

    public void setPackSize(int packSize) {
        this.packSize = packSize;
    }

    public List<OriginDiscount> getOriginDiscounts() {
        return originDiscounts;
    }

    public void setOriginDiscounts(List<OriginDiscount> originDiscounts) {
        this.originDiscounts = originDiscounts;
    }

    public static class OriginDiscount {
        private String origin;
        private double discount;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

    }
}
