package com.example.checkoutportal.services;

import com.example.checkoutportal.config.BeerDiscountConfig;
import com.example.checkoutportal.config.PromotionsConfig;
import com.example.checkoutportal.config.VegetableDiscountConfig;
import com.example.checkoutportal.data.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PromotionService {
    private final PromotionsConfig promotionsConfig;
    private String discountAppliedOnBread;
    private String discountAppliedOnVegetable;
    private String discountAppliedOnBeer;

    @Autowired
    public PromotionService(PromotionsConfig promotionsConfig) {
        this.promotionsConfig = promotionsConfig;
    }

    public double calculateBreadPrice(Product product, int quantity) {
        long daysOld = ChronoUnit.DAYS
                .between(
                        product.getManufactureDate(),
                        LocalDate.now()
                );

        double pricePerItem = product.getPricePerItem();
        BigDecimal totalPrice;
        int noDiscountLife = promotionsConfig.getBread().getNoDiscount();
        int buy1Get2Life = promotionsConfig.getBread().getBuy1Get2();
        int buy1Get3Life = promotionsConfig.getBread().getBuy1Get3();

        if (daysOld <= noDiscountLife) {
            totalPrice = BigDecimal.valueOf(pricePerItem * quantity);
            this.discountAppliedOnBread = String.format("None (baked " + (daysOld == 0 ? "fresh today)": "%d day(s) before)"), daysOld);
        } else if (daysOld <= buy1Get2Life) {
            int chargedQuantity = calculateChargedQuantity(quantity, 2);
            totalPrice = BigDecimal.valueOf(pricePerItem * chargedQuantity);
            this.discountAppliedOnBread = String.format("Buy 1 Get 2 (baked %d days before)", daysOld);
        } else if (daysOld == buy1Get3Life) {
            int chargedQuantity = calculateChargedQuantity(quantity, 3);
            totalPrice = BigDecimal.valueOf(pricePerItem * chargedQuantity);
            this.discountAppliedOnBread = String.format("Buy 1 Get 3 (baked %d days before)", daysOld);
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

        for(VegetableDiscountConfig.DiscountDetail discountDetail : promotionsConfig.getVegetable().getDiscounts()){
            Integer maxWeight = discountDetail.getMaxWeight();
            Integer minWeight = discountDetail.getMinWeight();
            double discountValue = discountDetail.getDiscount();
            if (productWeight >= minWeight && product.getWeight() <= maxWeight) {
                discount = discountValue / 100;
                this.discountAppliedOnVegetable = String.format("%.2f%%", discountValue);
                break;
            }
        }

        double pricePerItem = product.getPricePerItem();
        double totalPrice = pricePerItem * quantity;
        double discountedAmount = totalPrice * discount;
        return BigDecimal.valueOf(totalPrice - discountedAmount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public double calculateBeerPrice(Product product, int quantity) {
        double pricePerItem = product.getPricePerItem();
        String productOrigin = product.getOrigin();
        double totalPrice;
        double discount = 0;
        this.discountAppliedOnBeer = "None";
        int packSize = promotionsConfig.getBeer().getPackSize();

        if (quantity >= packSize) {
            for(BeerDiscountConfig.OriginDiscount originDiscount : promotionsConfig.getBeer().getOriginDiscounts()){
                if(originDiscount.getOrigin().equals(productOrigin)){
                    discount = originDiscount.getDiscount();
                    this.discountAppliedOnBeer = String
                            .format("€ %.2f off on each pack(6 beers) for %s beers",
                                    discount, productOrigin);
                }
            }
            totalPrice = (pricePerItem * quantity) - (discount * (int)(quantity / packSize));
        } else {
            totalPrice = pricePerItem * quantity;
        }

        return BigDecimal
                .valueOf(totalPrice)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
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

