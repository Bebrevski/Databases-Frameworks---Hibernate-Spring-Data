package mostwanted.domain.entities;

import mostwanted.domain.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "towns")
public class Town extends BaseEntity {
    private String name;
    private List<Racer> racers; //Added for exporting

    public Town() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = Racer.class, mappedBy = "homeTown")
    public List<Racer> getRacers() {
        return this.racers;
    }

    public void setRacers(List<Racer> racers) {
        this.racers = racers;
    }
}
