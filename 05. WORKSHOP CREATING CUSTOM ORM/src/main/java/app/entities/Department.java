package app.entities;

import app.db.annotations.Column;
import app.db.annotations.Entity;
import app.db.annotations.PrimaryKey;

@Entity(name = "departments")
public class Department {

    @PrimaryKey(name = "department_id")
    private long department_id;

    @Column(name = "name")
    private String name;

    @Column(name = "company_name")
    private String company;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, String company) {
        this.name = name;
        this.company = company;
    }

    public long getDepartment_id() {
        return this.department_id;
    }

    public void setDepartment_id(long department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%d | %s"
                , this.getDepartment_id()
                , this.getName());
    }
}
