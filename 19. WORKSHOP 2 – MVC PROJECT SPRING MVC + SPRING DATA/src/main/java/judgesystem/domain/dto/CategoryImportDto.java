package judgesystem.domain.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryImportDto {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    @SerializedName(value = "category")
    private CategoryImportDto parentCategory;
    @Expose
    @SerializedName(value = "categories")
    private CategoryImportDto[] subcategories;

    public CategoryImportDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 4)
    @Pattern(regexp = "[A-Z][a-zA-Z]+")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryImportDto getParentCategory() {
        return this.parentCategory;
    }

    public void setParentCategory(CategoryImportDto parentCategory) {
        this.parentCategory = parentCategory;
    }

    public CategoryImportDto[] getSubcategories() {
        return this.subcategories;
    }

    public void setSubcategories(CategoryImportDto[] subcategories) {
        this.subcategories = subcategories;
    }
}
