package com.example.checkoutportal.services;

import com.example.checkoutportal.config.BeerDiscountConfig;
import com.example.checkoutportal.config.BreadDiscountConfig;
import com.example.checkoutportal.config.PromotionsConfig;
import com.example.checkoutportal.config.VegetableDiscountConfig;
import com.example.checkoutportal.data.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PromotionServiceTest {

    private PromotionService promotionService;

    @BeforeEach
    public void setUp() {
        PromotionsConfig promotionsConfig = new PromotionsConfig();

        // Bread discount config
        BreadDiscountConfig breadDiscountConfig = new BreadDiscountConfig();
        breadDiscountConfig.setNoDiscount(1);
        breadDiscountConfig.setBuy1Get2(5);
        breadDiscountConfig.setBuy1Get3(6);
        promotionsConfig.setBread(breadDiscountConfig);

        // Vegetable discount config
        VegetableDiscountConfig vegetableDiscountConfig = new VegetableDiscountConfig();
        VegetableDiscountConfig.DiscountDetail discount1 = new VegetableDiscountConfig.DiscountDetail();
        discount1.setMaxWeight(100);
        discount1.setMinWeight(0);
        discount1.setDiscount(5);

        VegetableDiscountConfig.DiscountDetail discount2 = new VegetableDiscountConfig.DiscountDetail();
        discount2.setMaxWeight(500);
        discount2.setMinWeight(101);
        discount2.setDiscount(7);

        VegetableDiscountConfig.DiscountDetail discount3 = new VegetableDiscountConfig.DiscountDetail();
        discount3.setMaxWeight(Integer.MAX_VALUE);
        discount3.setMinWeight(501);
        discount3.setDiscount(10);

        vegetableDiscountConfig.setDiscounts(Arrays.asList(discount1, discount2, discount3));
        promotionsConfig.setVegetable(vegetableDiscountConfig);

        // Beer discount config
        BeerDiscountConfig beerDiscountConfig = new BeerDiscountConfig();
        beerDiscountConfig.setPackSize(6);

        BeerDiscountConfig.OriginDiscount dutchBeerDiscount = new BeerDiscountConfig.OriginDiscount();
        dutchBeerDiscount.setOrigin("Dutch");
        dutchBeerDiscount.setDiscount(2);

        BeerDiscountConfig.OriginDiscount germanBeerDiscount = new BeerDiscountConfig.OriginDiscount();
        germanBeerDiscount.setOrigin("German");
        germanBeerDiscount.setDiscount(4);

        BeerDiscountConfig.OriginDiscount belgiumBeerDiscount = new BeerDiscountConfig.OriginDiscount();
        belgiumBeerDiscount.setOrigin("Belgium");
        belgiumBeerDiscount.setDiscount(3);

        beerDiscountConfig.setOriginDiscounts(Arrays.asList(dutchBeerDiscount, germanBeerDiscount, belgiumBeerDiscount));
        promotionsConfig.setBeer(beerDiscountConfig);

        promotionService = new PromotionService(promotionsConfig);
    }

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
    public void testCalculateVegetablePrice_HighWeight() {
        Product vegetable = new Product();
        vegetable.setPricePerItem(10.0);
        vegetable.setWeight(600);

        double price = promotionService.calculateVegetablePrice(vegetable, 3);
        assertEquals(27.00, price);
    }

    @Test
    public void testCalculateBeerPrice_Dutch() {
        Product beer = new Product();
        beer.setPricePerItem(3.0);
        beer.setOrigin("Dutch");

        double price = promotionService.calculateBeerPrice(beer, 5);
        assertEquals(15.0, price);
    }

    @Test
    public void testCalculateBeerPrice_GermanBeer() {
        Product beer = new Product();
        beer.setPricePerItem(3.0);
        beer.setOrigin("German");

        double price = promotionService.calculateBeerPrice(beer, 6);
        assertEquals(14.0, price);
    }

    @Test
    public void testCalculateBeerPrice_BelgiumBeer() {
        Product beer = new Product();
        beer.setPricePerItem(3.0);
        beer.setOrigin("Belgium");

        double price = promotionService.calculateBeerPrice(beer, 6);
        assertEquals(15.0, price);
    }

    @Test
    public void testCalculateBeerPrice_UnknownOrigin() {
        Product beer = new Product();
        beer.setPricePerItem(2.0);
        beer.setOrigin("England");

        double price = promotionService.calculateBeerPrice(beer, 6);
        assertEquals(12.0, price);
    }
}

