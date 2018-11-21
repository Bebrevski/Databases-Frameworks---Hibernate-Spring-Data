package app.services.employees;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    void seedEmployees() throws IOException;

    List<String> getAllEmployees();
}
