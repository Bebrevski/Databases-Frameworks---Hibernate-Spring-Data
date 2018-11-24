package app.domain.entities;

import app.domain.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity(name = "products")
public class Product extends BaseEntity {
    private String name;
    private BigDecimal price;
    private User buyerId;
    private User sellerId;
    //private List<Category> categories;

    public Product() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    public User getBuyerId() {
        return this.buyerId;
    }

    public void setBuyerId(User buyerId) {
        this.buyerId = buyerId;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    public User getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(User sellerId) {
        this.sellerId = sellerId;
    }
}
