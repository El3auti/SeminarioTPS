package controller;

import config.BaseCrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Producto extends BaseCrud<Producto> {
    private String nombre;
    private String descripcionProblema;

    public Producto() {

    }

    public Producto(String nombre, String descripcionProblema) {
        this.nombre = nombre;
        this.descripcionProblema = descripcionProblema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    @Override
    public boolean create(Producto obj) {
        Producto producto = (Producto) obj;

        String query = "INSERT INTO Producto (Nombre, DescripcionProblema) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcionProblema());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Producto read(int id) {
        String query = "SELECT * FROM Producto WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return implementOBJ(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Producto WHERE ID = ?";
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
    public boolean update(Producto obj, int id) {
        Producto producto = (Producto) obj;
        String query = "UPDATE Producto SET Nombre = ?, DescripcionProblema = ? WHERE ID = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcionProblema());
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    static public Producto implementOBJ(ResultSet rs) throws SQLException {
        Producto producto = null;
        if (rs.next()) {
            try {
                String nombre = rs.getString("Nombre");
                String descripcionProblema = rs.getString("DescripcionProblema");
                producto = new Producto(nombre, descripcionProblema);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return producto;
    }

    public int getIDbyProblema(String descripcionProblema) {
        try {
            String query = "SELECT ID FROM Producto WHERE DescripcionProblema = ?";
            PreparedStatement pstmt = conexion.prepareStatement(query);
            pstmt.setString(1, descripcionProblema);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", descripcionProblema='" + descripcionProblema + '\'' +
                '}';
    }
}
