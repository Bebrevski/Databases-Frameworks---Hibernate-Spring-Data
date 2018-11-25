package app.domain.entities;

import app.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "categories")
public class Category extends BaseEntity {
    private String name;
    private List<Product> products;

    public Category(){}

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(targetEntity = Product.class, mappedBy = "categories")
    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
