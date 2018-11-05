package app;

import app.entities.*;
import app.interfaces.Runnable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {

    private static final String SOME_STUPID_TEXT = "Enter problem number to get started or 'end' to shut down the app: ";

    private final EntityManager entityManager;
    private Scanner scanner;

    public Engine(EntityManager entityManager, Scanner scanner) {
        this.entityManager = entityManager;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println(SOME_STUPID_TEXT);

        String input;
        while (!"end".equals(input = this.scanner.nextLine())) {
            int problem = Integer.parseInt(input);

            switch (problem) {
                case 2:
                    removeObjects();
                    break;
                case 3:
                    containsEmployee();
                    break;
                case 4:
                    getEmployeesWithSalaryOver50K();
                    break;
                case 5:
                    getEmployeesFromDepartment();
                    break;
                case 6:
                    addingNewAddressAndUpdatingEmployee();
                    break;
                case 7:
                    addressesWithEmployeeCount();
                    break;
                case 8:
                    getEmployeeWithProject();
                    break;
                case 9:
                    findLatest10Projects();
                    break;
                case 10:
                    increaseSalaries();
                    break;
                case 11:
                    removeTowns();
                    break;
                case 12:
                    findEmployeesByFirstName();
                    break;
                case 13:
                    employeesMaximumSalaries();
                    break;
                default:
                    System.out.println("You have entered invalid problem number. Enter between 2 and 13 inclusive!");
                    break;
            }

            System.out.println(SOME_STUPID_TEXT);
        }
    }

    //2.	Remove Objects
    private void removeObjects() {
        this.entityManager.getTransaction().begin();

        List<Town> towns = this.entityManager.createQuery("FROM Town", Town.class).getResultList();

        //Detach towns with name length > 5
        for (Town town : towns) {
            if (town.getName().length() > 5) {
                this.entityManager.remove(town);
            }
        }

        //Transform town names
        for (Town town : towns) {
            if (town.getName().length() > 5) {
                town.setName(town.getName().toLowerCase());
            }
        }

        //Persist towns
        for (Town town : towns) {
            this.entityManager.persist(town);
        }

        this.entityManager.getTransaction().commit();

        //Print towns
        for (Town town : towns) {
            System.out.println(town.getId() + " " + town.getName());
        }
    }

    //3.	Contains Employee
    private void containsEmployee() {
        System.out.println("Enter requested name: ");
        String name = this.scanner.nextLine();

        this.entityManager.getTransaction().begin();

        try {
            Employee employee = this.entityManager
                    .createQuery(
                            "FROM Employee WHERE concat(first_name, ' ', last_name) = :name"
                            , Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();

            System.out.println("Yes");
        } catch (NoResultException ex) {
            System.out.println("No");
        }

        this.entityManager.getTransaction().commit();
    }

    //4.	Employees with Salary Over 50 000
    private void getEmployeesWithSalaryOver50K() {
        this.entityManager.createQuery("SELECT e From Employee AS e WHERE e.salary > 50000", Employee.class)
                .getResultList()
                .forEach(e -> System.out.println(e.getFirstName()));
    }

    //5.	Employees from Department
    private void getEmployeesFromDepartment() {
        this.entityManager.createQuery("" +
                "SELECT e FROM Employee AS e " +
                "WHERE e.department.name = 'Research and Development' " +
                "ORDER BY e.salary, e.id", Employee.class)
                .getResultList()
                .forEach(e -> System.out.println(String.format("%s %s from %s - $%.2f"
                        , e.getFirstName()
                        , e.getLastName()
                        , e.getDepartment()
                        , e.getSalary())));
    }

    //6.	Adding a New Address and Updating Employee
    private void addingNewAddressAndUpdatingEmployee() {
        System.out.println("Enter requested employee name: ");
        String name = this.scanner.nextLine();

        try {
            Employee employee = this.entityManager.createQuery("" +
                    "SELECT e FROM Employee AS e " +
                    "WHERE e.lastName = :lastName", Employee.class)
                    .setParameter("lastName", name)
                    .getSingleResult();

            Town sofia = this.entityManager.createQuery("" +
                    "SELECT t FROM Town AS t " +
                    "WHERE t.name = 'Sofia'", Town.class)
                    .getSingleResult();

            Address address = new Address();
            address.setText("Vitoshka 15");
            address.setTown(sofia);

            this.entityManager.getTransaction().begin();

            this.entityManager.persist(address);

            employee.setAddress(address);

            entityManager.getTransaction().commit();

            System.out.printf("%s %s changed address to %s, %s%n",
                    employee.getFirstName(), employee.getLastName(),
                    employee.getAddress().getText(), employee.getAddress().getTown().getName());
        } catch (NoResultException ex) {
            System.out.println("You have entered invalid Last Name. Start over!");
            this.entityManager.getTransaction().rollback();
        }
    }

    //7.	Addresses with Employee Count
    private void addressesWithEmployeeCount() {
        this.entityManager.createQuery("" +
                "SELECT a FROM Address AS a " +
                "ORDER BY a.employees.size DESC, a.town.id", Address.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(a -> System.out.println(String.format("%s, %s - %d employees"
                        , a.getText()
                        , a.getTown().getName()
                        , a.getEmployees().size())));
    }

    //8.	Get Employee with Project
    private void getEmployeeWithProject() {
        System.out.println("Enter employee Id: ");
        int employeeId = Integer.parseInt(this.scanner.nextLine());

        try {

            Employee employee = this.entityManager.createQuery("" +
                    "SELECT e FROM Employee AS e " +
                    "WHERE e.id = :id ", Employee.class)
                    .setParameter("id", employeeId)
                    .getSingleResult();

            StringBuilder sb = new StringBuilder();
            sb
                    .append(employee.getFirstName())
                    .append(" ")
                    .append(employee.getLastName())
                    .append(" - ")
                    .append(employee.getJobTitle())
                    .append(System.lineSeparator());

            employee.getProjects().stream()
                    .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                    .forEach(p -> sb.append("\t").append(p.getName()).append(System.lineSeparator()));

            System.out.println(sb);

        } catch (NoResultException ex) {
            System.out.println("You have entered invalid id. Start over!");
        }
    }

    //9.	Find Latest 10 Projects
    private void findLatest10Projects() {

        StringBuilder sb = new StringBuilder();

        this.entityManager.createQuery("" +
                "SELECT p FROM Project AS p " +
                "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> sb
                        .append("Project name: ").append(p.getName())
                        .append(System.lineSeparator())
                        .append("\t").append("Project Description: ").append(p.getDescription())
                        .append(System.lineSeparator())
                        .append("\t").append("Project Start Date: ").append(p.getStartDate())
                        .append(System.lineSeparator())
                        .append("\t").append("Project End Date: ").append(p.getEndDate())
                        .append(System.lineSeparator())
                );

        System.out.println(sb);
    }

    //10.	Increase Salaries
    private void increaseSalaries() {
        this.entityManager.getTransaction().begin();

        this.entityManager.createQuery("" +
                "SELECT e FROM Employee AS e " +
                "WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')" +
                "ORDER BY e.id", Employee.class)
                .getResultList()
                .forEach(e -> {
                    e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));

                    System.out.println(String.format("%s %s ($%.2f)"
                            , e.getFirstName()
                            , e.getLastName()
                            , e.getSalary()));
                });

        this.entityManager.getTransaction().commit();
    }

    //11.	Remove Towns
    private void removeTowns() {
        System.out.println("Enter town: ");
        String townToRemove = this.scanner.nextLine();

        try {

            Town town = this.entityManager.createQuery("" +
                    "SELECT t FROM Town AS t WHERE t.name = :town", Town.class)
                    .setParameter("town", townToRemove)
                    .getSingleResult();

            List<Address> addresses = this.entityManager.createQuery("" +
                    "SELECT a FROM Address AS a " +
                    "WHERE a.town.name = :town", Address.class)
                    .setParameter("town", townToRemove)
                    .getResultList();

            System.out.println(String.format("%d address%s in %s deleted"
                    , addresses.size()
                    , addresses.size() != 1 ? "es" : ""
                    , town.getName()));

            this.entityManager.getTransaction().begin();

            for (Address address : addresses) {
                for (Employee employee : address.getEmployees()) {
                    employee.setAddress(null);
                }

                address.setTown(null);
                this.entityManager.remove(address);
            }

            this.entityManager.remove(town);

            this.entityManager.getTransaction().commit();

        } catch (NoResultException | IllegalArgumentException ex) {
            System.out.println("You have entered invalid town. Start over!");
            this.entityManager.getTransaction().rollback();
        }
    }

    //12.	Find Employees by First Name
    private void findEmployeesByFirstName() {
        System.out.println("Enter pattern: ");
        String pattern = this.scanner.nextLine();

        try {
            this.entityManager.createQuery("" +
                    "SELECT e FROM Employee AS e " +
                    "WHERE e.firstName LIKE :pattern", Employee.class)
                    .setParameter("pattern", pattern + "%")
                    .getResultList()
                    .forEach(e -> System.out.println(String.format("%s %s - %s - ($%.2f)"
                            , e.getFirstName()
                            , e.getLastName()
                            , e.getJobTitle()
                            , e.getSalary())));
        } catch (NoResultException ex) {
            System.out.println("No employees with entered pattern. Start over!");
        }

    }

    //13.	Employees Maximum Salaries
    private void employeesMaximumSalaries() {
        this.entityManager.createQuery("" +
                "SELECT e FROM Employee AS e " +
                "WHERE e.salary NOT BETWEEN 30000 AND 70000" +
                "GROUP BY e.department.name " +
                "ORDER BY e.salary DESC", Employee.class)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(e2 -> e2.getDepartment().getId()))
                .forEach(e -> System.out.println(String.format("%s - %.2f"
                        , e.getDepartment().getName()
                        , e.getSalary())));
    }
}
