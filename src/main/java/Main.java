import Router_View.Menu;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Menu.StartMenu();
        try {
            Menu.getManagerdb().getConnection().close();
        } catch (SQLException e) {
            Menu.printMessageResult("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
}