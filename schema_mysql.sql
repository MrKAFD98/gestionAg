

CREATE DATABASE IF NOT EXISTS gestion_agricole;
USE gestion_agricole;

CREATE TABLE IF NOT EXISTS culture (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    variete VARCHAR(100),
    date_semis DATE,
    date_recolte_prevue DATE,
    statut VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS travail_agricole (
    id INT AUTO_INCREMENT PRIMARY KEY,
    culture_id INT NOT NULL,
    type_travail VARCHAR(100),
    date_travail DATE,
    superficie DOUBLE,
    FOREIGN KEY (culture_id) REFERENCES culture(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS intrant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(100),
    cout DOUBLE,
    quantite DOUBLE
);

CREATE TABLE IF NOT EXISTS finances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    montant DOUBLE NOT NULL,
    date_operation DATE,
    categorie VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS recolte (
    id INT AUTO_INCREMENT PRIMARY KEY,
    culture_id INT NOT NULL,
    date_recolte DATE,
    quantite DOUBLE,
    prix_vente DOUBLE,
    FOREIGN KEY (culture_id) REFERENCES culture(id) ON DELETE CASCADE
);
