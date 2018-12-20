package hiberspring.domain.dtos.employee;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "employee")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EmployeeImportDto {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute(name = "position")
    private String position;
    @XmlElement(name = "card")
    private String card;
    @XmlElement(name = "branch")
    private String branch;

    public EmployeeImportDto() {
    }

    @NotNull
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @NotNull
    public String getCard() {
        return this.card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @NotNull
    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
