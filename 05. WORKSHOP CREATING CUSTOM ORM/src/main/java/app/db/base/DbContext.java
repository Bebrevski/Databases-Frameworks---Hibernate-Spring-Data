package app.db.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface DbContext<T> {

    //insert + update
    boolean persist(T entity) throws IllegalAccessException, SQLException;

    List<T> find() throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    List<T> find(String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    List<T> find(String template, String where) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    T findFirst() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    T findFirst(String where) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    T findById(long id) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    boolean delete(String where) throws SQLException;
}
