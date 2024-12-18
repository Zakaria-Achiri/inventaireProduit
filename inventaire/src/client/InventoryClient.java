package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import model.Product;

public class InventoryClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 6000);
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {
                System.out.println("--- Menu ---:");
                System.out.println("1. Ajouter un produit");
                System.out.println("2. Modifier un produit");
                System.out.println("3. Supprimer un produit");
                System.out.println("4. Rechercher un produit par nom");
                System.out.println("5. Quitter");
                System.out.print("Votre choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne
                switch (choix) {
                    case 1 -> {
                        System.out.println("Ajout d'un produit");
                        System.out.print("Nom : ");
                        String name = scanner.nextLine();
                        System.out.print("Catégorie : ");
                        String category = scanner.nextLine();
                        System.out.print("Quantité : ");
                        int quantity = scanner.nextInt();
                        System.out.print("Prix : ");
                        double price = scanner.nextDouble();

                        output.writeObject("ADD");
                        output.writeObject(new Product(0, name, category, quantity, price));
                        String response = (String) input.readObject();
                        System.out.println("Réponse du serveur : " + response);
                    }
                    case 2 -> {
                        System.out.println("Modification d'un produit");
                        System.out.print("ID du produit : ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consommer le retour à la ligne
                        System.out.print("Nouveau nom : ");
                        String name = scanner.nextLine();
                        System.out.print("Nouvelle catégorie : ");
                        String category = scanner.nextLine();
                        System.out.print("Nouvelle quantité : ");
                        int quantity = scanner.nextInt();
                        System.out.print("Nouveau prix : ");
                        double price = scanner.nextDouble();

                        output.writeObject("UPDATE");
                        output.writeObject(new Product(id, name, category, quantity, price));
                        String response = (String) input.readObject();
                        System.out.println("Réponse du serveur : " + response);
                    }
                    case 3 -> {
                        System.out.println("Suppression d'un produit");
                        System.out.print("ID du produit : ");
                        int id = scanner.nextInt();

                        output.writeObject("DELETE");
                        output.writeObject(id);
                        String response = (String) input.readObject();
                        System.out.println("Réponse du serveur : " + response);
                    }
                    case 4 -> {
                        System.out.println("Recherche d'un produit par nom");
                        System.out.print("Nom : ");
                        String name = scanner.nextLine();

                        output.writeObject("SEARCH_BY_NAME");
                        output.writeObject(name);
                        Object response = input.readObject();
                        if (response instanceof Product[]) {
                            Product[] products = (Product[]) response;
                            if (products.length > 0) {
                                System.out.println("Produits trouvés :");
                                for (Product product : products) {
                                    // Afficher les détails dans la console
                                    System.out.println("---------------------------------");
                                    System.out.println("ID : " + product.getId());
                                    System.out.println("Nom : " + product.getName());
                                    System.out.println("Catégorie : " + product.getCategory());
                                    System.out.println("Quantité : " + product.getQuantity());
                                    System.out.println("Prix : " + product.getPrice());
                                    System.out.println("---------------------------------");
                                }
                            } else {
                                System.out.println("Aucun produit trouvé pour le nom : " + name);
                            }
                        } else {
                            System.out.println("Réponse du serveur : " + response);
                        }
                    }
                    case 5 -> {
                        running = false;
                        System.out.println("Déconnexion...");
                    }
                    default -> System.out.println("Choix invalide.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
