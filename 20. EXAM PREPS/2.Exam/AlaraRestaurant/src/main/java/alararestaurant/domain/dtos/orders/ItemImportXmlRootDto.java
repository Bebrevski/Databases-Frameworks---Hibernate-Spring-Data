package alararestaurant.domain.dtos.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ItemImportXmlRootDto {
    @XmlElement(name = "item")
    private ItemImportXmlDto[] itemImportXmlDtos;

    public ItemImportXmlRootDto() {
    }

    public ItemImportXmlDto[] getItemImportXmlDtos() {
        return this.itemImportXmlDtos;
    }

    public void setItemImportXmlDtos(ItemImportXmlDto[] itemImportXmlDtos) {
        this.itemImportXmlDtos = itemImportXmlDtos;
    }
}
