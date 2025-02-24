-- Creazione database (se necessario)
CREATE DATABASE IF NOT EXISTS rubrica_db;
USE rubrica_db;

-- Tabella contact
CREATE TABLE IF NOT EXISTS contact (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(100),
    phone_number VARCHAR(20),
    age INT
);

-- Tabella user
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Inserimento utente di esempio per login test
INSERT INTO user (username, password) VALUES ('admin', 'admin123');
