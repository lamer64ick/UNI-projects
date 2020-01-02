CREATE TABLE customers (
    customer_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30),
    customerSince TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);
CREATE TABLE orders (
    order_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    customer int unsigned DEFAULT 0,
    FOREIGN KEY (customer) REFERENCES customers(customer)
);
CREATE TABLE comments (
    comment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    body VARCHAR
    product int unsigned DEFAULT 0,
    FOREIGN KEY (product) REFERENCES products (product)
)
CREATE TABLE products (
    product_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    price FLOAT,
    in_stock BOOLEAN,
);
CREATE TABLE order_line (
    product INT UNSIGNED DEFAULT 0,
    amount INT,
    price FLOAT
);