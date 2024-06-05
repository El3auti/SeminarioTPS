package controller;

import config.BaseCrud;

import java.sql.*;

public class Persona extends BaseCrud<Persona> {

    private int id;
    private String nombre;
    private String telefono;
    private String email;

    public Persona() {
        this.id = -1;
        this.nombre = "";
        this.telefono = "";
        this.email = "";
    }

    public Persona(String nombre, String telefono, String email) {
        this.id = -1;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public Persona(int id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public Persona(Persona persona) {
        this.id = persona.id;
        this.nombre = persona.nombre;
        this.telefono = persona.telefono;
        this.email = persona.email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean create(Persona persona) {
        String query = "INSERT INTO Persona (Nombre, Email, Telefono) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getEmail());
            pstmt.setString(3, persona.getTelefono());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
   public Persona read(int id) {
        String query = "SELECT * FROM Persona WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id);
            Persona persona = selectPersona(pstmt.executeQuery());
            return persona;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Persona WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Persona persona, int id) {
        String query = "UPDATE Persona SET Nombre = ?, Email = ?, Telefono = ? WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getEmail());
            pstmt.setString(3, persona.getTelefono());
            pstmt.setInt(4, id);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   static public Persona selectPersona(ResultSet rs) throws SQLException {
        while (rs.next()) {
            try {
                int id = rs.getInt("id");
                String nombre = rs.getString("Nombre");
                String telefono = rs.getString("Telefono");
                String email = rs.getString("Email");
                return new Persona(id, nombre, telefono, email);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public int getIDbyEmail(String email) {
        try {
            String query = "SELECT ID FROM Persona WHERE Email = ?";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Si no se encuentra el ID, retornamos -1
    }
}
