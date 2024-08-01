package com.example.checkoutportal;

import com.example.checkoutportal.config.PromotionsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PromotionsConfig.class)
public class CheckoutPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckoutPortalApplication.class, args);
    }

}
