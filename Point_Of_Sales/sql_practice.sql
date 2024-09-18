use  inventory;


CREATE TABLE PaymentTransactions (
    transactionId INT AUTO_INCREMENT PRIMARY KEY,
    cardNumber VARCHAR(16),
    cvv VARCHAR(4),
    expiryDate VARCHAR(10),
    totalAmount DOUBLE,
    merchantName VARCHAR(255) DEFAULT 'DEFAULT',
    orderNumber VARCHAR(50),
    paymentStatus VARCHAR(20)
);

CREATE TABLE CardPayments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cardNumber VARCHAR(16),
    expiryDate VARCHAR(7),
    cvv VARCHAR(3),
    amount DECIMAL(10, 2)
);


ALTER TABLE categories
 MODIFY COLUMN  createddate  datetime DEFAULT NULL DEFAULT CURRENT_TIMESTAMP;
 
 ALTER TABLE products
 MODIFY COLUMN  entrydate  datetime DEFAULT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE PaymentTransactions    
MODIFY COLUMN orderNumber INT AUTO_INCREMENT PRIMARY key; 

ALTER TABLE products
ADD COLUMN barcode VARCHAR(30) NOT NULL FIRST;

ALTER TABLE products CHANGE COLUMN barcode   VARCHAR(1000);


SELECT * FROM inventory.products;
# barcode, id, categoryId, supplierId, name, price, quantity, description, entrydate, variantProd_gms
'8900500001287', '128', '8', '25', 'sugar', '40', '25500', 'sugar of 25500 gms ', '2024-03-13 00:00:00', '1000'

UPDATE Products SET quantity =--  WHERE id = 4;
UPDATE Products SET quantity = quantity - ? WHERE id = 4;
ALTER TABLE products ADD COLUMN variantProd_gms double NOT NULL;