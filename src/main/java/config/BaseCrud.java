package config;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseCrud<T> {

    protected Connection conexion;

    public BaseCrud() {
        try {
            this.conexion = ManagerBD.getInstance().getConnection();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
    }

    public abstract boolean create(T obj);
    public abstract T read(int id);
    public abstract boolean delete(int id);
    public abstract boolean update(T obj, int id);
}
