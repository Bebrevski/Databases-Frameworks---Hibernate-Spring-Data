package app;

import app.db.EntityDbContext;
import app.db.base.DbContext;
import app.entities.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/soft_uni";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        DbContext<Employee> employeeDbContext = getContext(connection, Employee.class);

        connection.close();
    }

    private static DbContext<Employee> getContext(Connection connection, Class<Employee> klass) {
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
