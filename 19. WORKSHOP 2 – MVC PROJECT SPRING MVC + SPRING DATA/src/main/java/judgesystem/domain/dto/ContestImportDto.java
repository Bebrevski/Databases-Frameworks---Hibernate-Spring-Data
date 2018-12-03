package judgesystem.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContestImportDto {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    @SerializedName(value = "category")
    private CategoryImportDto categoryImportDto;
    @Expose
    @SerializedName(value = "allowedStrategies")
    private StrategyImportDto[] strategyImportDtos;

    public ContestImportDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryImportDto getCategoryImportDto() {
        return this.categoryImportDto;
    }

    public void setCategoryImportDto(CategoryImportDto categoryImportDto) {
        this.categoryImportDto = categoryImportDto;
    }

    public StrategyImportDto[] getStrategyImportDtos() {
        return this.strategyImportDtos;
    }

    public void setStrategyImportDtos(StrategyImportDto[] strategyImportDtos) {
        this.strategyImportDtos = strategyImportDtos;
    }
}
