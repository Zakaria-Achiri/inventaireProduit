# Système de Gestion d'Inventaire avec Accès Distant

## Explication rapide du projet

Ce projet est un système de gestion d'inventaire conçu pour une petite entreprise. Il repose sur une architecture client-serveur utilisant des sockets TCP/IP. 
Le serveur centralise la gestion de l'inventaire en interagissant avec une base de données MySQL, tandis que les employés utilisent une application cliente pour gérer les produits à distance.

### Objectifs principaux :
- Offrir un accès distant sécurisé et en temps réel.
- Intégrer une base de données robuste pour stocker les informations des produits.
- Garantir simplicité et performance avec une architecture légère.

## Instructions pour exécuter le code

### 1. Pré-requis :
- Installer Java Development Kit (JDK) version **21.0.1**.
- Installer MySQL version **8.0.30**.
- Utiliser un IDE ( Eclipse "dans notre cas").
- Télécharger et configurer le driver JDBC.

### 2. Configuration de la base de données :
- **Démarrer MySQL** (via Laragon).
- **Créer la base de données** :
  ```sql
  CREATE DATABASE inventorydb;
  ```
- **Créer la table des produits** :
  ```sql
  CREATE TABLE produits (
      id INT AUTO_INCREMENT PRIMARY KEY,
      nom VARCHAR(100) NOT NULL,
      category VARCHAR(50),
      quantity INT NOT NULL,
      price DECIMAL(10, 2) NOT NULL
  );
  ```

### 3. Exécution de l'application :

#### 3.1. Serveur :
- Lancer le serveur en exécutant la classe `InventoryServer` qui démarre un socket sur le port 6000 et gère les connexions des clients.
- Le serveur utilise la classe `ClientHandler` pour traiter les requêtes des clients. Cette classe gère les commandes comme "ADD", "UPDATE", "DELETE" et "SEARCH_BY_NAME" en interagissant avec la base de données via la classe `ProductDAO`.

#### 3.2. Client :
- Lancer le client en exécutant la classe `InventoryClient`.
- Cette classe permet d'envoyer des commandes au serveur via un menu interactif pour :
  - Ajouter un produit : Fournir le nom, la catégorie, la quantité et le prix.
  - Modifier un produit : Spécifier l'ID du produit à modifier et ses nouvelles valeurs.
  - Supprimer un produit : Indiquer l'ID du produit à supprimer.
  - Rechercher un produit par nom : Fournir le nom du produit pour afficher les résultats.

### 4. Exemple d'utilisation :
1. Démarrer le serveur en premier :
   
   java server.InventoryServer
   
2. Démarrer un ou plusieurs clients :
   
   java client.InventoryClient
   
3. Naviguer dans le menu pour effectuer des opérations sur l'inventaire.
