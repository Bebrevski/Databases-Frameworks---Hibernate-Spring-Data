package mostwanted.domain.dtos.races;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "entry")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class EntryImportDto {
    @XmlAttribute(name = "id")
    private Long id;

    public EntryImportDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
