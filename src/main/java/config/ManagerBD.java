package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagerBD {

    private static final String URL = "jdbc:mysql://localhost:3306/bautioficial";
    private static final String USER = "root";
    private static final String PASSWORD = "sqlserver";

    private static Connection connection;
    private static ManagerBD instance;

    // Constructor privado para evitar instanciación externa
    private ManagerBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createTables();
        } catch (SQLException | ClassNotFoundException ex) {
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
                    + "Nombre VARCHAR(255), "  // Asegúrate de que coincide con tu clase Producto
                    + "DescripcionProblema VARCHAR(255)"
                    + ")";
            statement.executeUpdate(createProductoTable);

            String createPersonaTable = "CREATE TABLE IF NOT EXISTS Persona ("
                    + "ID int PRIMARY KEY AUTO_INCREMENT, "
                    + "Nombre VARCHAR(255), "
                    + "Email VARCHAR(255), "
                    + "Telefono VARCHAR(20)"  // Telefono como VARCHAR si puede tener guiones, etc.
                    + ")";
            statement.executeUpdate(createPersonaTable);

            String createTicketsTable = "CREATE TABLE IF NOT EXISTS ticket ("
                    + "ID int PRIMARY KEY AUTO_INCREMENT, "
                    + "personaID int, "
                    + "productoID int, "
                    + "Descripcion TEXT, "
                    + "Mensaje TEXT, "  // Campo adicional que falta en la definición original
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
