package config;

import java.sql.Connection;

public abstract class BaseCrud<T> {

    public Connection conexion = ManagerBD.getInstance().getConnection();

    public abstract boolean create(T obj);
    public abstract T read(int id);
    public abstract boolean delete(int id);
    public abstract boolean update(T obj, int id);
}
