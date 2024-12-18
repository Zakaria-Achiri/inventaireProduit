package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/InventoryDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            // Charger explicitement le pilote JDBC (recommandé pour la compatibilité)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Le driver JDBC MySQL n'a pas été trouvé !");
            e.printStackTrace();
            throw new SQLException("Impossible de charger le pilote JDBC.");
        }

        // Retourne la connexion
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
