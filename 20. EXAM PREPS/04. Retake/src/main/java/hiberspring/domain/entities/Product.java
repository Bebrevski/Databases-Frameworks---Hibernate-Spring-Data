package hiberspring.domain.entities;

import hiberspring.domain.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "products")
public class Product extends BaseEntity {
    private String name;
    private Integer clients;
    private Branch branch;

    public Product() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "clients", nullable = false)
    public Integer getClients() {
        return this.clients;
    }

    public void setClients(Integer clients) {
        this.clients = clients;
    }

    @ManyToOne(targetEntity = Branch.class)
    @JoinColumn(name = "branch", referencedColumnName = "id", nullable = false)
    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
