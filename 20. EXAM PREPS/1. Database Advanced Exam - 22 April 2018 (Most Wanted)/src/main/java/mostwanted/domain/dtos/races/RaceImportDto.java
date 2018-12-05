package mostwanted.domain.dtos.races;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "race")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RaceImportDto {
    @XmlElement(name = "laps")
    private Integer laps;
    @XmlElement(name = "district-name")
    private String districtName;
    @XmlElement(name = "entries")
    private EntryImportRootDto entryImportRootDto;

    public RaceImportDto() {
    }

    @NotNull
    public Integer getLaps() {
        return this.laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }

    @NotNull
    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public EntryImportRootDto getEntryImportRootDto() {
        return this.entryImportRootDto;
    }

    public void setEntryImportRootDto(EntryImportRootDto entryImportRootDto) {
        this.entryImportRootDto = entryImportRootDto;
    }
}
