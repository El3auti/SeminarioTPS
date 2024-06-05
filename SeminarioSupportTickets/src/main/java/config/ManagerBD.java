package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagerBD {

    private static final String URL = "jdbc:mysql://localhost:3306/universidad_test";
    private static final String USER = "newuser";
    private static final String PASSWORD = "password";

    private static Connection connection;
    private static ManagerBD instance;

    // Constructor privado para evitar instanciación externa
    private ManagerBD() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createTables();
        } catch (SQLException ex) {
            throw new RuntimeException("Exception occurred in creating singleton instance", ex);
        }
    }

    // Método público para obtener la instancia única
    public static synchronized ManagerBD getInstance() {
        if (instance == null) {
            instance = new ManagerBD();
        }
        return instance;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        return connection;
    }

    // Método privado para crear las tablas
    private void createTables() {
        try (Statement statement = connection.createStatement()) {
            String createProductoTable = "CREATE TABLE IF NOT EXISTS Producto ("
                    + "ID int PRIMARY KEY AUTO_INCREMENT, "
                    + "DescripcionProblema VARCHAR(255)"
                    + ")";
            statement.executeUpdate(createProductoTable);

            String createPersonaTable = "CREATE TABLE IF NOT EXISTS Persona ("
                    + "ID int PRIMARY KEY AUTO_INCREMENT, "
                    + "Nombre VARCHAR(255), "
                    + "Email VARCHAR(255), "
                    + "Telefono int"
                    + ")";
            statement.executeUpdate(createPersonaTable);

            String createTicketsTable = "CREATE TABLE IF NOT EXISTS Tickets ("
                    + "ID int PRIMARY KEY AUTO_INCREMENT, "
                    + "personaID int, "
                    + "productoID int, "
                    + "Estado VARCHAR(50), "
                    + "Descripcion TEXT, "
                    + "FechaCreacion date, "
                    + "FOREIGN KEY (personaID) REFERENCES Persona(ID), "
                    + "FOREIGN KEY (productoID) REFERENCES Producto(ID)"
                    + ")";
            statement.executeUpdate(createTicketsTable);

            System.out.println("Tablas creadas exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
