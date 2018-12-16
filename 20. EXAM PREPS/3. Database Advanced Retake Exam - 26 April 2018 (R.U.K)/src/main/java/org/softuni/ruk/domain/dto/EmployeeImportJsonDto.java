package org.softuni.ruk.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeImportJsonDto {
    @Expose
    @SerializedName(value = "full_name")
    private String fullName;
    @Expose
    @SerializedName(value = "salary")
    private BigDecimal salary;
    @Expose
    @SerializedName(value = "started_on")
    private String startedOn;
    @Expose
    @SerializedName(value = "branch_name")
    private String branchName;

    public EmployeeImportJsonDto() {
    }

    @NotNull
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getStartedOn() {
        return this.startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    @NotNull
    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
