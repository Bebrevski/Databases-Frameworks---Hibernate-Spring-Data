package mostwanted.domain.entities;

import mostwanted.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "racers")
public class Racer extends BaseEntity {
    private String name;
    private Integer age;
    private BigDecimal bounty;
    private Town homeTown;
    private Set<Car> cars;

    public Racer() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age")
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "bounty")
    public BigDecimal getBounty() {
        return this.bounty;
    }

    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }

    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    public Town getHomeTown() {
        return this.homeTown;
    }

    public void setHomeTown(Town homeTown) {
        this.homeTown = homeTown;
    }

    @OneToMany(targetEntity = Car.class, mappedBy = "racer")
    public Set<Car> getCars() {
        return this.cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}