package app.entities;

import javax.persistence.*;

@Entity
@Table(name = "continents")
public class Continent {
    private int id;
    private String name;

    public Continent(){}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
