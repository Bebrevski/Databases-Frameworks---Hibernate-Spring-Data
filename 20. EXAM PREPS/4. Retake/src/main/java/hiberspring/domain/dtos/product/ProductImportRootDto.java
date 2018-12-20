package hiberspring.domain.dtos.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductImportRootDto {
    @XmlElement(name = "product")
    private ProductImportDto[] productImportDtos;

    public ProductImportRootDto() {
    }

    public ProductImportDto[] getProductImportDtos() {
        return this.productImportDtos;
    }

    public void setProductImportDtos(ProductImportDto[] productImportDtos) {
        this.productImportDtos = productImportDtos;
    }
}
