package app;

import app.db.EntityDbContext;
import app.db.base.DbContext;
import app.entities.Employee;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/soft_uni_simple";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Connection connection = getConnection();
        DbContext<Employee> employeeDbContext = getContext(connection, Employee.class);

        //

        connection.close();
    }

    private static DbContext<Employee> getContext(Connection connection, Class<Employee> klass) throws SQLException {
        return new EntityDbContext<>(connection, klass);
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                CONNECTION_STRING,
                USER,
                PASSWORD
        );
    }
}
