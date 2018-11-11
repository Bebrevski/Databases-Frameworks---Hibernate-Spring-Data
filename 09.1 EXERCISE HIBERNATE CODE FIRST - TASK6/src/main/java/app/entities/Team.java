package app.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "teams")
public class Team {
    private int id;
    private String name;
    private byte[] logo;
    private String initials;
    private Color primaryColor;
    private Color secondaryColor;
    private Town town;
    private BigDecimal budget;

    public Team() {
    }

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

    @Lob
    @Column(name = "logo", length = 50000)
    public byte[] getLogo() {
        return this.logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    @Column(name = "initials", length = 3)
    public String getInitials() {
        return this.initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    @ManyToOne
    @JoinColumn(name = "primary_kit_color_id")
    public Color getPrimaryColor() {
        return this.primaryColor;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    @ManyToOne
    @JoinColumn(name = "secondary_kit_color_id")
    public Color getSecondaryColor() {
        return this.secondaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(name = "town_id")
    public Town getTown() {
        return this.town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Column(name = "budget")
    public BigDecimal getBudget() {
        return this.budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
