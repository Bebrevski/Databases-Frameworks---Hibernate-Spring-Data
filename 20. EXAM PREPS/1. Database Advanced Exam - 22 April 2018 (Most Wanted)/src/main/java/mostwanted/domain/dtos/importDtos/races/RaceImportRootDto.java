package mostwanted.domain.dtos.importDtos.races;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "races")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RaceImportRootDto {
    @XmlElement(name = "race")
    private RaceImportDto[] racerImportDtos;

    public RaceImportRootDto() {
    }

    public RaceImportDto[] getRaceImportDtos() {
        return this.racerImportDtos;
    }

    public void setRaceImportDtos(RaceImportDto[] racerImportDtos) {
        this.racerImportDtos = racerImportDtos;
    }
}
