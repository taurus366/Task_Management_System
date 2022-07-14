package com.system.management.service;


import com.system.management.model.dto.ProjectInformationDTO;
import com.system.management.model.service.CreateNewProjectServiceModel;

import java.util.List;

public interface ProjectService {

    void createNewProject(String email, CreateNewProjectServiceModel createNewProjectServiceModel);
    List<ProjectInformationDTO> getProjectsByAccountId(Integer ownerId);
    void deleteProject(Integer projectId,Integer ownerId);
    List<ProjectInformationDTO> getAllOwnProjects(Integer ownerId);
}
