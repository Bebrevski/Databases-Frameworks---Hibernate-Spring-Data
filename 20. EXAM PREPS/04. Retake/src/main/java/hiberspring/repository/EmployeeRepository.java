package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("" +
            "SELECT e " +
            "FROM hiberspring.domain.entities.Employee AS e " +
            "JOIN e.employeeCard AS c " +
            "JOIN e.branch AS b " +
            "JOIN b.products AS p " +
            "WHERE SIZE(p) > 0 " +
            "ORDER BY e.firstName ASC, e.lastName ASC, SIZE(p) DESC ")
    List<Employee> productiveEmployees();
}
