package app.entities;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {
    private int id;
    private String name;
    private int squadNumber;
    private Team team;
    private Position position;
    private boolean isInjured;

    public Player(){}

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

    @Column(name = "squad_number")
    public int getSquadNumber() {
        return this.squadNumber;
    }

    public void setSquadNumber(int squadNumber) {
        this.squadNumber = squadNumber;
    }

    @ManyToOne(targetEntity = Team.class)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @ManyToOne(targetEntity = Position.class)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Column(name = "is_injured")
    public boolean isInjured() {
        return this.isInjured;
    }

    public void setInjured(boolean injured) {
        isInjured = injured;
    }
}
