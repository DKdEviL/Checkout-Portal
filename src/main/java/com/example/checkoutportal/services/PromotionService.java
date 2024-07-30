package com.example.checkoutportal.services;

import com.example.checkoutportal.data.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PromotionService {
    private static final Map<String, Integer> BREAD_DISCOUNTS = Map.of(
            "noDiscountDays", 2,
            "buy1Get2Days", 5,
            "buy1Get3Days", 6,
            "maxAge", 6
    );

    private static final List<Map<String, Object>> VEGETABLE_DISCOUNTS = List.of(
            Map.of("maxWeight", 100, "discount", 5.0),
            Map.of("maxWeight", 500, "discount", 7.0),
            Map.of("maxWeight", 1000, "discount", 10.0)
    );

    private static final Map<String, Object> BEER_DISCOUNTS = Map.of(
            "packSize", 6,
            "originDiscounts", List.of(
                    Map.of("origin", "Dutch", "discount", 2.0),
                    Map.of("origin", "Belgium", "discount", 3.0),
                    Map.of("origin", "German", "discount", 4.0)
            )
    );

    private String discountAppliedOnBread;
    private String discountAppliedOnVegetable;
    private String discountAppliedOnBeer;

    public double calculateBreadPrice(Product product, int quantity) {
        long daysOld = ChronoUnit.DAYS.between(product.getManufactureDate(), LocalDate.now());

        double pricePerItem = product.getPricePerItem();
        BigDecimal totalPrice;

        if (daysOld <= BREAD_DISCOUNTS.get("noDiscountDays")) {
            totalPrice = BigDecimal.valueOf(pricePerItem * quantity);
            this.discountAppliedOnBread = "None";
        } else if (daysOld <= BREAD_DISCOUNTS.get("buy1Get2Days")) {
            int chargedQuantity = calculateChargedQuantity(quantity, 2);
            totalPrice = BigDecimal.valueOf(pricePerItem * chargedQuantity);
            this.discountAppliedOnBread = "Buy 1 Get 2";
        } else if (daysOld == BREAD_DISCOUNTS.get("buy1Get3Days")) {
            int chargedQuantity = calculateChargedQuantity(quantity, 3);
            totalPrice = BigDecimal.valueOf(pricePerItem * chargedQuantity);
            this.discountAppliedOnBread = "Buy 1 Get 3";
        } else {
            throw new RuntimeException("Bread older than 6 days cannot be added");
        }

        return totalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private int calculateChargedQuantity(int totalQuantity, int getCount) {
        int chargedGroups = totalQuantity / getCount;
        int remainingItems = totalQuantity % getCount;
        if (remainingItems > 0) {
            chargedGroups++;
        }
        return chargedGroups;
    }

    public double calculateVegetablePrice(Product product, int quantity) {
        double discount = 0;
        for (Map<String, Object> discountDetail : VEGETABLE_DISCOUNTS) {
            Integer maxWeight = (Integer) discountDetail.get("maxWeight");
            double discountValue = (double) discountDetail.get("discount");
            if (maxWeight == null || product.getWeight() <= maxWeight) {
                discount = discountValue / 100;
                break;
            }
            this.discountAppliedOnVegetable = discountValue + "%";
        }
        double pricePerItem = product.getPricePerItem();
        BigDecimal totalPrice = BigDecimal.valueOf(pricePerItem * quantity * (1 - discount));

        return totalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double calculateBeerPrice(Product product, int quantity) {
        BigDecimal pricePerItem = BigDecimal.valueOf(product.getPricePerItem());
        BigDecimal totalPrice;
        double discount = 0;

        if (quantity >= (int) BEER_DISCOUNTS.get("packSize")) {
            List<Map<String, Object>> originDiscounts = (List<Map<String, Object>>) BEER_DISCOUNTS.get("originDiscounts");
            for (Map<String, Object> originDiscount : originDiscounts) {
                if (originDiscount.get("origin").equals(product.getOrigin())) {
                    discount = (double) originDiscount.get("discount");
                    break;
                }
            }
            this.discountAppliedOnBeer = discount + " off per pack of 6 for " + product.getOrigin() + " beers";
            totalPrice = pricePerItem.multiply(BigDecimal.valueOf(quantity))
                    .subtract(BigDecimal.valueOf(discount));
        } else {
            totalPrice = pricePerItem.multiply(BigDecimal.valueOf(quantity));
        }

        return totalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


    public String getDiscountAppliedOnBread() {
        return discountAppliedOnBread;
    }

    public String getDiscountAppliedOnVegetable() {
        return discountAppliedOnVegetable;
    }

    public String getDiscountAppliedOnBeer() {
        return discountAppliedOnBeer;
    }
}
