package app;

import app.gringottsDatabase.WizardDeposit;
import app.salesDatabase.Customer;
import app.salesDatabase.Product;
import app.salesDatabase.Sale;
import app.salesDatabase.StoreLocation;
import app.universitySystemDatabase.Course;
import app.universitySystemDatabase.Student;
import app.universitySystemDatabase.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    private EntityManager manager;
    private Scanner scanner;

    public Engine(EntityManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }


    public void run() {
        // this.createGringottsDatabse(); // Task 1

        // createSalesDatabаse();         // Task 2

        // universitySystem();            // Task 3

        // hospitalDatabase();            // Task 4
    }

    //1.	Gringotts Database
    private void createGringottsDatabse() {
        WizardDeposit deposit = new WizardDeposit();

        deposit.setAge(29);
        deposit.setFirstName("Bai Pesho");
        deposit.setLastName("Motokarista");
        deposit.setDepositStartDate(new Date());

        this.manager.getTransaction().begin();

        this.manager.persist(deposit);

        this.manager.getTransaction().commit();
    }

    //2.	Sales Database
    private void createSalesDatabаse() {
        Customer customer = new Customer();
        customer.setName("Pesho");
        customer.setEmail("peshaka@abv.bg");
        customer.setCreditCardNumber("123456789");

        Product product = new Product();
        product.setName("BTR");
        product.setPrice(BigDecimal.valueOf(10000));
        product.setQuantity(1D);

        StoreLocation storeLocation = new StoreLocation();
        storeLocation.setLocationName("BG");

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setProduct(product);
        sale.setStoreLocation(storeLocation);
        sale.setDate(new Date());

        this.manager.getTransaction().begin();

        this.manager.persist(customer);
        this.manager.persist(product);
        this.manager.persist(storeLocation);
        this.manager.persist(sale);

        this.manager.getTransaction().commit();

        CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();

        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);

        query.from(Customer.class);

        this.manager.createQuery(query)
                .getResultList()
                .forEach(c -> System.out.println(String.format("Name: %s\nEmail: %s\nSale Location:%s"
                        , c.getName()
                        , c.getEmail()
                        , c.getSales().stream()
                                .map(s -> s.getStoreLocation().getLocationName())
                                .collect(Collectors.joining(", ")))));
    }

    //3.	University System
    private void universitySystem() {
        Student student = new Student();
        student.setFirstName("Pesho");
        student.setLastName("Peshov");
        student.setPhoneNumber("0777888999");
        student.setCourses(new HashSet<>());

        Teacher teacher = new Teacher();
        teacher.setFirstName("Gosho");
        teacher.setLastName("Goshov");
        teacher.setPhoneNumber("0666777888");
        teacher.setEmail("some@mail.com");
        teacher.setCourses(new HashSet<>());

        Course course = new Course();
        course.setName("Java");
        course.setDescription("Databases");
        course.setCredits(9);
        course.setStartDate(new Date());
        course.setTeacher(teacher);
        course.setStudents(new HashSet<>());

        course.getStudents().add(student);
        student.getCourses().add(course);
        teacher.getCourses().add(course);

        this.manager.getTransaction().begin();

        this.manager.persist(student);
        this.manager.persist(teacher);
        this.manager.persist(course);

        this.manager.getTransaction().commit();
    }

    //4.	Hospital Database
    private void hospitalDatabase(){

    }
}
