package com.system.management.service;

import com.system.management.model.dto.TaskInformationDTO;
import com.system.management.model.service.CreateNewTaskServiceModel;

import java.util.List;

public interface TaskService {

    void createNewTask(CreateNewTaskServiceModel createNewTaskServiceModel , Integer reportedById);
    List<TaskInformationDTO> getAllOwnTasks(Integer accountId);
    List<TaskInformationDTO> searchTasks(String title, String type, String assignedTo);
}
