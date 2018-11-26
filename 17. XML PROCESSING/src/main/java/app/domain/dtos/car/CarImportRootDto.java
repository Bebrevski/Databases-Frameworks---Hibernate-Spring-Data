package app.domain.dtos.car;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportRootDto {
    @XmlElement(name = "car")
    private CarImportDto[] carImportDtos;

    public CarImportRootDto() {
    }

    public CarImportDto[] getCarImportDtos() {
        return this.carImportDtos;
    }

    public void setCarImportDtos(CarImportDto[] carImportDtos) {
        this.carImportDtos = carImportDtos;
    }
}
