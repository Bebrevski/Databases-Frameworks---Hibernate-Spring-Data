package org.softuni.ruk.domain.entity;

import org.softuni.ruk.domain.entity.base.BaseEntity;

import javax.persistence.*;

@Entity(name = "clients")
public class Client extends BaseEntity {
    private String fullName;
    private Integer age;
    private BankAccount bankAccount;

    public Client() {
    }

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @OneToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
