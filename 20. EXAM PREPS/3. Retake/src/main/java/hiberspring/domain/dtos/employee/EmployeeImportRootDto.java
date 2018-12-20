package hiberspring.domain.dtos.employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employees")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EmployeeImportRootDto {
    @XmlElement(name = "employee")
    private EmployeeImportDto[] employeeImportDtos;

    public EmployeeImportRootDto() {
    }

    public EmployeeImportDto[] getEmployeeImportDtos() {
        return this.employeeImportDtos;
    }

    public void setEmployeeImportDtos(EmployeeImportDto[] employeeImportDtos) {
        this.employeeImportDtos = employeeImportDtos;
    }
}
