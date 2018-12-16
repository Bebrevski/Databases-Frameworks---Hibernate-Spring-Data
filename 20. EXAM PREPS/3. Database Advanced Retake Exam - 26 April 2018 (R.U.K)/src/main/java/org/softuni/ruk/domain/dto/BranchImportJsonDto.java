package org.softuni.ruk.domain.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class BranchImportJsonDto {
    @Expose
    private String name;

    public BranchImportJsonDto() {
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
