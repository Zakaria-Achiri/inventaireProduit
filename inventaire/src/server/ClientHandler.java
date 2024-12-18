package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import dao.ProductDAO;
import model.Product;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ProductDAO productDAO;

    public ClientHandler(Socket clientSocket, ProductDAO productDAO) {
        this.clientSocket = clientSocket;
        this.productDAO = productDAO;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            while (true) {
                String command = (String) input.readObject();

                switch (command) {
                    case "ADD" -> {
                        Product productToAdd = (Product) input.readObject();
                        productDAO.addProduct(productToAdd);
                        output.writeObject("Produit ajouté avec succès.");
                    }
                    case "UPDATE" -> {
                        Product productToUpdate = (Product) input.readObject();
                        if (productDAO.getProductById(productToUpdate.getId()) != null) {
                            productDAO.updateProduct(productToUpdate);
                            output.writeObject("Produit mis à jour avec succès.");
                        } else {
                            output.writeObject("Erreur : Produit avec l'ID " + productToUpdate.getId() + " introuvable.");
                        }
                    }
                    case "DELETE" -> {
                        int productId = (Integer) input.readObject();
                        if (productDAO.getProductById(productId) != null) {
                            productDAO.deleteProduct(productId);
                            output.writeObject("Produit supprimé avec succès.");
                        } else {
                            output.writeObject("Erreur : Produit avec l'ID " + productId + " introuvable.");
                        }
                    }
                    case "SEARCH_BY_NAME" -> {
                        String name = (String) input.readObject();
                        List<Product> products = productDAO.searchByName(name);
                        output.writeObject(products);
                    }
                    case "QUIT" -> {
                        output.writeObject("Déconnexion réussie.");
                        return; // Quitter la boucle pour terminer le thread
                    }
                    default -> output.writeObject("Commande inconnue : " + command);
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
