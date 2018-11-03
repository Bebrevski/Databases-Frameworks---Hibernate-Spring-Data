package app.entities;

import app.db.annotations.Column;
import app.db.annotations.Entity;
import app.db.annotations.PrimaryKey;

@Entity(name = "employees")
public class Employee {

    @PrimaryKey(name = "employee_id")
    private long employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "ucn")
    private String ucn;

    public Employee() {
    }

    public Employee(String firstName, String lastName, int age) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
    }

    public Employee(String firstName, String lastName, int age, String ucn) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
        this.setUcn(ucn);
    }

    public long getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUcn() {
        return this.ucn;
    }

    public void setUcn(String ucn) {
        this.ucn = ucn;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %d"
                , this.employeeId
                , this.firstName
                , this.lastName
                , this.age);
    }
}
