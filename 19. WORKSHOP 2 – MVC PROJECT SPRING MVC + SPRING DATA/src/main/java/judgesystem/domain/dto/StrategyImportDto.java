package judgesystem.domain.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class StrategyImportDto {
    @Expose
    private Long id;
    @Expose
    private String name;

    public StrategyImportDto() {
    }

    @NotNull
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
