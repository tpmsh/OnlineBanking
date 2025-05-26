-- create database bank_db;
use bank_db;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255),
    code VARCHAR(255),
    verified INT DEFAULT 0,
    verified_at DATE,
    create_at DATETIME,
    updated_at DATETIME
);



INSERT INTO users (user_id, first_name, last_name, email, password, token, code, verified, verified_at, create_at, updated_at)
VALUES ('123', 'John', 'Doe', 'john.doe@example.com', 'password123', 'some-token', 'some-code', 1, '2025-03-28', NOW(), NOW());
