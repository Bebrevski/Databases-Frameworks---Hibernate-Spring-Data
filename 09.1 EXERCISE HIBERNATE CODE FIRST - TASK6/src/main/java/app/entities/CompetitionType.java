package app.entities;

import javax.persistence.*;

@Entity
@Table(name = "competition_type")
public class CompetitionType {
    private int id;
    private String name;

    public CompetitionType(){}

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
