package org.softuni.ruk.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;

public class ClientImportJsonDto {
    @Expose
    @SerializedName(value = "first_name")
    private String firstName;
    @Expose
    @SerializedName(value = "last_name")
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    @SerializedName(value = "appointed_employee")
    private String appointedEmployee;

    public ClientImportJsonDto() {
    }

    @NotNull
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAppointedEmployee() {
        return this.appointedEmployee;
    }

    public void setAppointedEmployee(String appointedEmployee) {
        this.appointedEmployee = appointedEmployee;
    }
}
