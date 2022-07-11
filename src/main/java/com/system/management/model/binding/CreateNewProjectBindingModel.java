package com.system.management.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateNewProjectBindingModel {

    @NotNull
    @NotBlank
    @Size(min = 3,max = 255)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 255)
    private String key;

    public CreateNewProjectBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public CreateNewProjectBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getKey() {
        return key;
    }

    public CreateNewProjectBindingModel setKey(String key) {
        this.key = key;
        return this;
    }
}
