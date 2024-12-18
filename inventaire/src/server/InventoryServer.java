package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

import dao.ProductDAO;
import util.DatabaseConnection;

public class InventoryServer{
    public static void main(String[] args) {
        try (
        		ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Serveur démarré sur le port 6000...");
            Connection connection = DatabaseConnection.getConnection();
            ProductDAO productDAO = new ProductDAO(connection);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté !");
                new Thread(new ClientHandler(clientSocket, productDAO)).start();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
