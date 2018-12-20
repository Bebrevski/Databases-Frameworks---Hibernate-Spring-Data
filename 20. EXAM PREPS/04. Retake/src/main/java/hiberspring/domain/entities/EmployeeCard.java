package hiberspring.domain.entities;

import hiberspring.domain.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "employee_cards")
public class EmployeeCard extends BaseEntity {
    private String number;

    public EmployeeCard() {
    }

    @Column(name = "number", nullable = false, unique = true)
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
