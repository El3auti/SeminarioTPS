package controller;

import config.BaseCrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ticket extends BaseCrud<Ticket> {
    private String descripcion;
    private String mensaje;
    private Persona persona;
    private Producto producto;

    public Ticket(String descripcion, String mensaje, Persona persona, Producto producto) {
        this.descripcion = descripcion;
        this.mensaje = mensaje;
        this.persona = persona;
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean create(Ticket obj) {
        Ticket ticket = (Ticket) obj;
        try {

            // Crear persona asociada al ticket
            if (!persona.create(ticket.getPersona())) {
                return false; // No se pudo crear la persona
            }
            String PersonaEmail = ticket.getPersona().getEmail();

            // Crear producto asociado al ticket
            if (!producto.create(ticket.getProducto())) {
                return false; // No se pudo crear el producto
            }
            String Problemaproducto = ticket.getProducto().getDescripcionProblema();

            // Insertar el ticket en la base de datos
            String query = "INSERT INTO Ticket (Descripcion, Mensaje, PersonaID, ProductoID) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, ticket.getDescripcion());
            pstmt.setString(2, ticket.getMensaje());
            pstmt.setInt(3, ticket.getPersona().getIDbyEmail(PersonaEmail));
            pstmt.setInt(4, ticket.getProducto().getIDbyProblema(Problemaproducto));
            pstmt.executeUpdate();

            return true; // El ticket se creó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Ocurrió un error al crear el ticket
        }
    }

    @Override
    public Ticket read(int id) {
        String query = "SELECT * FROM Ticket WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return implementOBJ(rs);
            } else {
                return null; // No se encontró ningún ticket con el ID especificado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Ticket WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Ticket ticket, int id) {
        try {
            String email = ticket.getPersona().getEmail();
            // Obtener ID de la persona
            int personaID = ticket.getPersona().getIDbyEmail(email);
            if (personaID == -1) {
                // Si la persona no existe, crearla
                if (!persona.create(ticket.getPersona())) {
                    System.out.println("Error: No se pudo crear la persona.");
                    return false;
                }
                // Obtener el nuevo ID de la persona creada
                email = ticket.getPersona().getEmail();
                // Obtener ID de la persona
                personaID = ticket.getPersona().getIDbyEmail(email);
            }

            String descripcion_problema = ticket.getProducto().getDescripcionProblema();

            // Obtener ID del producto
            int productoID = ticket.getProducto().getIDbyProblema(descripcion_problema);
            if (productoID == -1) {
                // Si el producto no existe, crearlo
                if (!producto.create(ticket.getProducto())) {
                    System.out.println("Error: No se pudo crear el producto.");
                    return false;
                }
                // Obtener el nuevo ID del producto creado
                descripcion_problema = ticket.getProducto().getDescripcionProblema();
                productoID = ticket.getProducto().getIDbyProblema(descripcion_problema);
            }

            // Actualizar el ticket en la base de datos
            String query = "UPDATE Ticket SET Descripcion = ?, Mensaje = ?, PersonaID = ?, ProductoID = ? WHERE ID = ?";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, ticket.getDescripcion());
            pstmt.setString(2, ticket.getMensaje());
            pstmt.setInt(3, personaID);
            pstmt.setInt(4, productoID);
            pstmt.setInt(5, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Ticket implementOBJ(ResultSet rs) throws SQLException {
        Ticket ticket = null;
        if (rs.next()) {
            try {
                String descripcion = rs.getString("Descripcion");
                String mensaje = rs.getString("Mensaje");
                int personaId = rs.getInt("PersonaID");
                int productoId = rs.getInt("ProductoID");
                // Aquí deberías cargar la persona y el producto asociados utilizando los métodos readOBJ de Persona y Producto
                Persona persona = getPersona(personaId);
                Producto producto = getProducto(productoId);
                ticket = new Ticket(descripcion, mensaje, persona, producto);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ticket;
    }

    private Persona getPersona(int personaId) {
        String query = "SELECT * FROM Persona WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            return Persona.selectPersona(pstmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Producto getProducto(int productoId) {
        String query = "SELECT * FROM Producto WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            return Producto.implementOBJ(pstmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

