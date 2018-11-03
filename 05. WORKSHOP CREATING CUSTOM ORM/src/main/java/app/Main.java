package app;

import app.db.EntityDbContext;
import app.db.base.DbContext;
import app.entities.Department;
import app.entities.Employee;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/soft_uni_simple?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Connection connection = getConnection();

        DbContext<Employee> employeeDbContext = getContext(connection, Employee.class);

        Employee employee = new Employee("Pesho", "Peshev", 22, "");
        employeeDbContext.persist(employee);

        DbContext<Department> departmentDbContext = getContext(connection, Department.class);
        Department department = new Department("Java Devs", "Programista");
        departmentDbContext.persist(department);

        //employeeDbContext.delete("employee_id = 1");
        employeeDbContext.delete("first_name = 'Pesho'");

        connection.close();
    }

    private static <T> DbContext<T> getContext(Connection connection, Class<T> klass) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
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
