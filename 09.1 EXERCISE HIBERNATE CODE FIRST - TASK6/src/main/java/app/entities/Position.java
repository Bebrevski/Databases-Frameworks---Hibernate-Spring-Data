package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positions")
public class Position {
    private String id;
    private String positionDescription;

    public Position(){}

    @Id
    @Column(name = "id", length = 2, nullable = false, unique = true)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "position_description")
    public String getPositionDescription() {
        return this.positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }
}
