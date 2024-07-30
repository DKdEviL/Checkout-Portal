package com.example.checkoutportal.services;

import com.example.checkoutportal.data.model.Product;
import com.example.checkoutportal.services.PromotionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PromotionServiceTest {

    private final PromotionService promotionService = new PromotionService();

    @Test
    public void testCalculateBreadPrice_noDiscount() {
        Product bread = new Product();
        bread.setPricePerItem(2.0);
        bread.setManufactureDate(LocalDate.now());

        double price = promotionService.calculateBreadPrice(bread, 4);
        assertEquals(8.00, price);
    }

    @Test
    public void testCalculateBreadPrice_buy1Get2() {
        Product bread = new Product();
        bread.setPricePerItem(2.0);
        bread.setManufactureDate(LocalDate.now().minusDays(4));

        double price = promotionService.calculateBreadPrice(bread, 5);
        assertEquals(6.00, price);
    }

    @Test
    public void testCalculateBreadPrice_buy1Get3() {
        Product bread = new Product();
        bread.setPricePerItem(2.0);
        bread.setManufactureDate(LocalDate.now().minusDays(6));

        double price = promotionService.calculateBreadPrice(bread, 10);
        assertEquals(8.00, price);
    }

    @Test
    public void testCalculateBreadPrice_buy1Get3_2() {
        Product bread = new Product();
        bread.setPricePerItem(2.0);
        bread.setManufactureDate(LocalDate.now().minusDays(6));

        double price = promotionService.calculateBreadPrice(bread, 11);
        assertEquals(8.00, price);
    }

    @Test
    public void testCalculateBreadPrice_olderThan6Days() {
        Product bread = new Product();
        bread.setPricePerItem(2.0);
        bread.setManufactureDate(LocalDate.now().minusDays(7));

        assertThrows(RuntimeException.class, () -> promotionService.calculateBreadPrice(bread, 4));
    }

    @Test
    public void testCalculateVegetablePrice() {
        Product vegetable = new Product();
        vegetable.setPricePerItem(5.0);
        vegetable.setWeight(200);

        double price = promotionService.calculateVegetablePrice(vegetable, 3);
        assertEquals(13.95, price, String.valueOf(0.01));
    }

    @Test
    public void testCalculateBeerPrice_noDiscount() {
        Product beer = new Product();
        beer.setPricePerItem(3.0);
        beer.setOrigin("Dutch");

        double price = promotionService.calculateBeerPrice(beer, 5);
        assertEquals(15.0, price);
    }

    @Test
    public void testCalculateGermanBeerPrice_withDiscount() {
        Product beer = new Product();
        beer.setPricePerItem(3.0);
        beer.setOrigin("German");

        double price = promotionService.calculateBeerPrice(beer, 6);
        assertEquals(14.0, price);
    }

    @Test
    public void testCalculateBelgiumBeerPrice_withDiscount() {
        Product beer = new Product();
        beer.setPricePerItem(3.0);
        beer.setOrigin("Belgium");

        double price = promotionService.calculateBeerPrice(beer, 6);
        assertEquals(15.0, price);
    }
}

