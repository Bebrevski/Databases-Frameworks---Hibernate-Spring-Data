package hiberspring.domain.entities;

import hiberspring.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "branches")
public class Branch extends BaseEntity {
    private String name;
    private Town town;
    private List<Product> products;

    public Branch() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "town", referencedColumnName = "id", nullable = false)
    public Town getTown() {
        return this.town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @OneToMany(targetEntity = Product.class, mappedBy = "branch")
    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
