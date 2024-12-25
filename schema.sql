-- Création de la base de données
CREATE DATABASE IF NOT EXISTS inventory_system;

-- Utilisation de la base de données
USE inventory_system;

-- Création de la table products
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);
