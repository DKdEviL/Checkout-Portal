INSERT INTO ORDERS (total_price)
    VALUES (0.00), (0.00), (0.00),
           (0.00), (0.00);

-- 1. Bread with Current Date
INSERT INTO PRODUCT (price_per_item, manufacture_date, type) VALUES (2.00, CURDATE(), 'Bread');

-- 2. 3 days old Bread
INSERT INTO PRODUCT (price_per_item, manufacture_date, type) VALUES (1.8, CURDATE() - 3 DAY, 'Bread');

-- 3. 5 days old Bread
INSERT INTO PRODUCT (price_per_item, manufacture_date, type) VALUES (3.25, CURDATE() - 5 DAY, 'Bread');

-- 4. 6 days old Bread
INSERT INTO PRODUCT (price_per_item, manufacture_date, type) VALUES (2.05, CURDATE() - 6 DAY, 'Bread');

-- 5. 50g Vegetable
INSERT INTO PRODUCT (price_per_item, weight, type) VALUES (1.50, 50, 'Vegetable');

-- 6. 100g Vegetable
INSERT INTO PRODUCT (price_per_item, weight, type) VALUES (2.50, 100, 'Vegetable');

-- 7. 330g Vegetable
INSERT INTO PRODUCT (price_per_item, weight, type) VALUES (1.00, 330, 'Vegetable');

-- 8. 700g Vegetable
INSERT INTO PRODUCT (price_per_item, weight, type) VALUES (2.00, 700, 'Vegetable');

-- 9. German Beer
INSERT INTO PRODUCT (price_per_item, origin, type) VALUES (1.50, 'German', 'Beer');

-- 10. Dutch Beer
INSERT INTO PRODUCT (price_per_item, origin, type) VALUES (2.00, 'Dutch', 'Beer');

-- 11. Belgium Beer
INSERT INTO PRODUCT (price_per_item, origin, type) VALUES (1.30, 'Belgium', 'Beer');

INSERT INTO ITEM (order_id, product_id, quantity)
    VALUES (1, 1, 5), (1, 7, 2), (1, 10, 15);
INSERT INTO ITEM (order_id, product_id, quantity)
    VALUES (2, 3, 3), (2, 9, 3);
INSERT INTO ITEM (order_id, product_id, quantity)
    VALUES (3, 4, 1), (3, 8, 3), (3, 11, 8);
INSERT INTO ITEM (order_id, product_id, quantity)
    VALUES (4, 2, 10), (4, 5, 1);
INSERT INTO ITEM (order_id, product_id, quantity)
    VALUES (5, 6, 11), (5, 4, 7);
--
-- INSERT INTO PROMOTIONS (type, details)
--     VALUES
--         ('Bread', '{ ' ||
--                   '"noDiscountDays": 1,' ||
--                   '"buy1Get2Days": 3,' ||
--                   '"buy1Get3Days": 6,' ||
--                   '"maxAge": 6' ||
--                   '}'),
--         ('Vegetable', '{' ||
--                       '"weightDiscounts": [' ||
--                       '{"maxWeight": 100, "discount": 0.05},' ||
--                       '{"maxWeight": 500, "discount": 0.07},' ||
--                       '{"maxWeight": null, "discount": 0.10}]' ||
--                       '}'),
--         ('Beer', '{' ||
--                  '"packSize": 6,' ||
--                  '"originDiscounts": [' ||
--                  '{"origin": "Dutch", "discount": 2},' ||
--                  '{"origin": "Belgium", "discount": 3},' ||
--                  '{"origin": "German", "discount": 4}]' ||
--                  '}');