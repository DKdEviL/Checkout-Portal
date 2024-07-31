package com.example.checkoutportal.services;

import com.example.checkoutportal.data.model.Product;
import com.example.checkoutportal.services.types.BreadDiscountType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PromotionService {

    private static final Map<BreadDiscountType, Integer> BREAD_DISCOUNTS = Map.of(
            BreadDiscountType.NO_DISCOUNT, 2,
            BreadDiscountType.BUY_1_GET_2, 5,
            BreadDiscountType.BUY_1_GET_3, 6,
            BreadDiscountType.MAX_AGE, 6
    );

    private static final List<Map<String, Object>> VEGETABLE_DISCOUNTS = List.of(
            Map.of("maxWeight", 100, "minWeight", 0, "discount", 5.0),
            Map.of("maxWeight", 500,"minWeight", 101, "discount", 7.0),
            Map.of("maxWeight", Integer.MAX_VALUE,"minWeight", 501, "discount", 10.0)
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
        long daysOld = ChronoUnit.DAYS
                .between(
                        product.getManufactureDate(),
                        LocalDate.now()
                );

        double pricePerItem = product.getPricePerItem();
        BigDecimal totalPrice;

        if (daysOld <= BREAD_DISCOUNTS.get(BreadDiscountType.NO_DISCOUNT)) {
            totalPrice = BigDecimal.valueOf(pricePerItem * quantity);
            this.discountAppliedOnBread = "None";
        } else if (daysOld <= BREAD_DISCOUNTS.get(BreadDiscountType.BUY_1_GET_2)) {
            int chargedQuantity = calculateChargedQuantity(quantity, 2);
            totalPrice = BigDecimal.valueOf(pricePerItem * chargedQuantity);
            this.discountAppliedOnBread = "Buy 1 Get 2";
        } else if (daysOld == BREAD_DISCOUNTS.get(BreadDiscountType.BUY_1_GET_3)) {
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
        Integer productWeight = product.getWeight();
        for (Map<String, Object> discountDetail : VEGETABLE_DISCOUNTS) {
            Integer maxWeight = (Integer) discountDetail.get("maxWeight");
            Integer minWeight = (Integer) discountDetail.get("minWeight");
            double discountValue = (double) discountDetail.get("discount");
            if (productWeight >= minWeight && product.getWeight() <= maxWeight) {
                discount = discountValue / 100;
                this.discountAppliedOnVegetable = String.format("%.2f%%", discountValue);
                break;
            }
        }
        double pricePerItem = product.getPricePerItem();
        double totalPrice = pricePerItem * quantity;
        double discountValue = totalPrice * discount;
        return BigDecimal.valueOf(totalPrice - discountValue)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public double calculateBeerPrice(Product product, int quantity) {
        double pricePerItem = product.getPricePerItem();
        double totalPrice;
        double discount = 0;
        this.discountAppliedOnBeer = "None";
        int packSize = (int)BEER_DISCOUNTS.get("packSize");

        if (quantity >= packSize) {
            List<Map<String, Object>> originDiscounts =
                    (List<Map<String, Object>>) BEER_DISCOUNTS.get("originDiscounts");
            for (Map<String, Object> originDiscount : originDiscounts) {
                if (originDiscount.get("origin").equals(product.getOrigin())) {
                    discount = (double) originDiscount.get("discount");
                    this.discountAppliedOnBeer = String
                            .format("€ %.2f off on each pack(6 beers) for %s beers",
                                    discount, product.getOrigin());
                    break;
                }
            }
            totalPrice = (pricePerItem * quantity) - (discount * (int)(quantity / packSize));
        } else {
            totalPrice = pricePerItem * quantity;
        }

        return BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
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

