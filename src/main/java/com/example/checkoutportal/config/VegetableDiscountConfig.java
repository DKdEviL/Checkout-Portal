package com.example.checkoutportal.config;

import java.util.List;

public class VegetableDiscountConfig {
    private List<DiscountDetail> discounts;

    public List<DiscountDetail> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDetail> discounts) {
        this.discounts = discounts;
    }

    public static class DiscountDetail {
        private int maxWeight;
        private int minWeight;
        private double discount;

        public int getMaxWeight() {
            return maxWeight;
        }

        public void setMaxWeight(int maxWeight) {
            this.maxWeight = maxWeight;
        }

        public int getMinWeight() {
            return minWeight;
        }

        public void setMinWeight(int minWeight) {
            this.minWeight = minWeight;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }
    }
}
