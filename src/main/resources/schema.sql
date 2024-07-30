CREATE TABLE ORDERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_price DOUBLE NOT NULL
);

CREATE TABLE PRODUCT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    price_per_item DOUBLE NOT NULL,
    origin VARCHAR(255) NULL,
    manufacture_date DATE NULL,
    weight DOUBLE NULL ,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT check_manufacture_date
        CHECK (manufacture_date IS NULL OR manufacture_date >= CURDATE() - 6 DAY),
    CONSTRAINT check_price_per_item
        CHECK (price_per_item >= 1.00 OR type != 'Beer' AND origin != 'German')
);

CREATE TABLE ITEM (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES PRODUCT(id),
    FOREIGN KEY (order_id) REFERENCES ORDERS(id),
    UNIQUE (order_id, product_id)
);

CREATE TABLE PROMOTIONS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    details VARCHAR(255) NOT NULL
);

CREATE INDEX idx_order_id ON ITEM(order_id);


