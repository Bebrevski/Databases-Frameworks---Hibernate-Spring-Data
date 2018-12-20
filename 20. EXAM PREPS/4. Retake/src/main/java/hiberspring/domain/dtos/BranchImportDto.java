package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class BranchImportDto {
    @Expose
    private String name;
    @Expose
    private String town;

    public BranchImportDto() {
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getTown() {
        return this.town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}