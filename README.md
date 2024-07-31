# Checkout Portal Microservice

## Table of Contents
1. [How to Run this Project](#how-to-run-this-project)
    1. [Prerequisites](#prerequisites)
    2. [Adding Data](#-setting-up-data)
    3. [Steps](#steps)
2. [Description](#description)
3. [Architecture Diagram](#architecture-diagram)
4. [Tools and Frameworks](#tools-and-frameworks)
    1. [Spring Boot](#spring-boot)
    2. [H2 Database](#h2-database)
    3. [Thymeleaf](#thymeleaf)
    4. [JUnit and Mockito](#junit-and-mockito)
5. [Why These Tools Were Chosen](#why-these-tools-were-chosen)
6. [Project Structure](#project-structure)
    1. [Controllers](#controllers)
    2. [Services](#services)
    3. [Templates](#templates)
    4. [Database Schema](#database-schema)
    5. [Data In Database](#data-in-database)
    6. [Dependencies](#dependencies)

## How to Run this Project

### Prerequisites

- Java 17
- Maven

### Setting up data
- Refer to the [data.sql](/src/main/resources/data.sql) file
- Insert a new order in the `ORDERS` table using:
  ```sql 
    INSERT INTO ORDERS (total_price) VALUES (0.00);
  ```
- Add the products in `PRODUCT` table
    ```sql
    INSERT INTO PRODUCT (price_per_item, manufacture_date, type) VALUES (2.00, CURDATE(), 'Bread');
    ```
  Note: The above query is for adding only "Bread", refer to the [data.sql](/src/main/resources/data.sql) file to see how to add different types of products.
- Add the items in the `ITEMS` table to map the products with specific orders
    ```sql
    INSERT INTO ITEM (order_id, product_id, quantity)
        VALUES (1, 1, 5), (1, 7, 2), (1, 10, 15);
    ```
  Note: `order_id` & `product_id` are incremental and auto-generated unless you provide a specific one, so these will point to the order in the sequence of your query.

### Steps

1. **Clone the repository**:
    ```bash
    git clone https://github.com/DKdEviL/Checkout-Portal.git
    cd Checkout-Portal
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```
   Note: If above command throw error ` command not found: mvn` then replace `mvn` with `./mvnw` in above commands like:
    ```bash
   ./mvnw clean install
    ```

4. **Access the application**:
    - The web interface for viewing orders will be available at `http://localhost:8080/orders`
    - The API will be available at `http://localhost:8080/api/orders`

## Description

This project is a microservice for a grocery store checkout system. It manages orders and applies various promotions to calculate the final price for products like bread, vegetables, and beer.

## Architecture Diagram

![Architecture Diagram](/architecture.jpg)

## Tools and Frameworks

### H2 Database
- **Advantages**:
    - Lightweight, fast, and in-memory database, ideal for development and testing.
    - Easy integration with Spring Boot.
- **Limitations**:
    - Not suitable for production environments due to limited scalability and durability.
  #### Note: I could've used `PostgreSQL` if needed to make the service more robust, scalable and production ready, but for simplicity of development work I chose to use H2 database.

### Thymeleaf
- **Advantages**:
    - Natural templating, making it easy to create templates that can also be used as static prototypes.
    - Strong integration with Spring Boot.
- **Limitations**:
    - Learning curve if not familiar with the syntax and features.
    - May not be as fast as some other templating engines in high-load environments.

### JUnit and Mockito
- **Advantages**:
    - Essential for unit testing and mocking dependencies.
    - Helps ensure code quality and reliability through automated tests.
- **Limitations**:
    - Requires extra effort to write and maintain tests.

## Why These Tools Were Chosen

- **H2 Database**: For quick and easy setup of a development database.
- **Thymeleaf**: To create dynamic web pages that are integrated seamlessly with Spring Boot.
- **JUnit and Mockito**: To facilitate robust testing and ensure that the application behaves as expected under various conditions.

## Project Structure

### Controllers
- **OrderController**: Handles API requests for retrieving order details.
- **WebServiceController**: Manages web interface requests for displaying orders.

### Services
- **OrderService**: Retrieves order information from the repository and applies promotions.
- **PromotionService**: Contains logic for calculating discounts on products.

### Templates
- **orderDetail.html**: Displays detailed information about a specific order.
- **allOrders.html**: Lists all orders with links to their detailed views.

### Database Schema
- Defined in `resources/schema.sql`, it includes tables for orders, products, items, and promotions.

### Data In Database
- Insertion queries in `resources/data.sql`, it includes data for orders, products, and items.

### Dependencies
- Managed in `pom.xml`, which includes Spring Boot starters for web, data JPA, Thymeleaf, H2 database, and testing libraries.
