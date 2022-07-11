package com.system.management.model.binding;

import com.system.management.model.object.Members;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateNewBoardBindingModel {

    @NotNull
    @NotBlank
    @Size(min = 3,max = 255)
    private String name;


   private List<Members> members;

    public CreateNewBoardBindingModel() {
    }

    public String getName() {
        return name;
    }

    public CreateNewBoardBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<Members> getMembers() {
        return members;
    }

    public CreateNewBoardBindingModel setMembers(List<Members> members) {
        this.members = members;
        return this;
    }
}
