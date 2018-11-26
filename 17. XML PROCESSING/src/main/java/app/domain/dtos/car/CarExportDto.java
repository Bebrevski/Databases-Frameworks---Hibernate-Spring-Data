package app.domain.dtos.car;

import app.domain.dtos.part.PartExportRootDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportDto {
    @XmlAttribute(name = "make")
    private String make;
    @XmlAttribute(name = "model")
    private String model;
    @XmlAttribute(name = "travelled-distance")
    private Double travellerDistance;
    @XmlElement(name = "parts")
    private PartExportRootDto partExportRootDto;

    public CarExportDto() {
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getTravellerDistance() {
        return this.travellerDistance;
    }

    public void setTravellerDistance(Double travellerDistance) {
        this.travellerDistance = travellerDistance;
    }

    public PartExportRootDto getPartExportRootDto() {
        return this.partExportRootDto;
    }

    public void setPartExportRootDto(PartExportRootDto partExportRootDto) {
        this.partExportRootDto = partExportRootDto;
    }
}
