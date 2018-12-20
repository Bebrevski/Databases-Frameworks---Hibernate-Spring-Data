package alararestaurant.domain.dtos.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orders")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderImportXmlRootDto {
    @XmlElement(name = "order")
    private OrderImportXmlDto[] orderImportXmlDtos;

    public OrderImportXmlRootDto() {
    }

    public OrderImportXmlDto[] getOrderImportXmlDtos() {
        return this.orderImportXmlDtos;
    }

    public void setOrderImportXmlDtos(OrderImportXmlDto[] orderImportXmlDtos) {
        this.orderImportXmlDtos = orderImportXmlDtos;
    }
}
