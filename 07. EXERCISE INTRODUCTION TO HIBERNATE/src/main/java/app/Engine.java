package app;

import app.entities.Address;
import app.entities.Employee;
import app.entities.Town;
import app.interfaces.Runnable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    break;
                default:
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
        
    }
}
