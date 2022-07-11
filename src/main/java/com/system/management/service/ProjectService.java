package com.system.management.service;


import com.system.management.model.service.CreateNewProjectServiceModel;

public interface ProjectService {

    void createNewProject(String email, CreateNewProjectServiceModel createNewProjectServiceModel);
}
