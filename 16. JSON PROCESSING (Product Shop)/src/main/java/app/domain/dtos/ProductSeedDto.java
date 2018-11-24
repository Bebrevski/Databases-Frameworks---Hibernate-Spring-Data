package app.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductSeedDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductSeedDto() {
    }

    @NotNull(message = "Message can not be null!")
    @Size(min = 3, message = "Name must be at least 3 symbols long!")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Price can not be null!")
    @DecimalMin(value = "500", message = "Price can not be less than 500")
    @DecimalMax(value = "1000", message = "Price can not be more than 1000")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
