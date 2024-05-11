-- Création de la base de données
CREATE DATABASE IF NOT EXISTS library_db;

-- Utilisation de la base de données
USE library_db;

-- Création de la table member
CREATE TABLE IF NOT EXISTS member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    address VARCHAR(100),
    phoneNumber DOUBLE,
    email VARCHAR(100)
);
INSERT INTO member (id,firstName, lastName, address, phoneNumber, email) 
VALUES (1,'Mohamed', 'kone', '123 Main Street', 123456789, 'john.doe@example.com');
INSERT INTO member (first_name, last_name, address, phone_number, email) 
VALUES (2,'Louise', 'Doe', '123 compton Street', 123561149, 'alice.doe@example.com');
