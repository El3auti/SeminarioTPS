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

            if (!persona.create(ticket.getPersona())) {
                return false;
            }
            String PersonaEmail = ticket.getPersona().getEmail();

            if (!producto.create(ticket.getProducto())) {
                return false;
            }
            String Problemaproducto = ticket.getProducto().getDescripcionProblema();

            String query = "INSERT INTO ticket (Descripcion, Mensaje, PersonaID, ProductoID) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, ticket.getDescripcion());
            pstmt.setString(2, ticket.getMensaje());
            pstmt.setInt(3, ticket.getPersona().getIDbyEmail(PersonaEmail));
            pstmt.setInt(4, ticket.getProducto().getIDbyProblema(Problemaproducto));
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Ticket read(int id) {
        String query = "SELECT * FROM ticket WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return implementOBJ(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM ticket WHERE ID = ?";
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
            int personaID = ticket.getPersona().getIDbyEmail(email);
            if (personaID == -1) {
                if (!persona.create(ticket.getPersona())) {
                    System.out.println("Error: No se pudo crear la persona.");
                    return false;
                }
                email = ticket.getPersona().getEmail();
                personaID = ticket.getPersona().getIDbyEmail(email);
            }

            String descripcion_problema = ticket.getProducto().getDescripcionProblema();

            int productoID = ticket.getProducto().getIDbyProblema(descripcion_problema);
            if (productoID == -1) {
                if (!producto.create(ticket.getProducto())) {
                    System.out.println("Error: No se pudo crear el producto.");
                    return false;
                }
                descripcion_problema = ticket.getProducto().getDescripcionProblema();
                productoID = ticket.getProducto().getIDbyProblema(descripcion_problema);
            }

            String query = "UPDATE ticket SET Descripcion = ?, Mensaje = ?, PersonaID = ?, ProductoID = ? WHERE ID = ?";
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
        while(rs.next()){
            try {
                String descripcion = rs.getString("Descripcion");
                String mensaje = rs.getString("Mensaje");
                int personaId = rs.getInt("PersonaID");
                int productoId = rs.getInt("ProductoID");
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
        return new Persona().read(personaId);
    }

    private Producto getProducto(int productoId) {
       return new Producto().read(productoId);
    }
}

