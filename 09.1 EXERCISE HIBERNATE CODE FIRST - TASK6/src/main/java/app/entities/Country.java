package app.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {
    private String id;
    private String name;
    private Set<Continent> continents;

    public Country() {
    }

    @Id
    @Column(name = "id", length = 3, nullable = false, unique = true)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(targetEntity = Continent.class)
    @JoinTable(
            name = "countries_continents",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id")
    )
    public Set<Continent> getContinents() {
        return this.continents;
    }

    public void setContinents(Set<Continent> continents) {
        this.continents = continents;
    }
}
