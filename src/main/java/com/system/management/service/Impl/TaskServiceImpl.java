package com.system.management.service.Impl;

import com.system.management.model.dto.TaskInformationDTO;
import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.BoardEntity;
import com.system.management.model.entity.ProjectEntity;
import com.system.management.model.entity.TaskEntity;
import com.system.management.model.entity.enums.PriorityEnum;
import com.system.management.model.entity.enums.StatusEnum;
import com.system.management.model.entity.enums.TypeEnum;
import com.system.management.model.service.CreateNewTaskServiceModel;
import com.system.management.repository.AccountRepository;
import com.system.management.repository.BoardRepository;
import com.system.management.repository.ProjectRepository;
import com.system.management.repository.TaskRepository;
import com.system.management.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;
    private final BoardRepository boardRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AccountRepository accountRepository, ProjectRepository projectRepository, BoardRepository boardRepository) {
        this.taskRepository = taskRepository;
        this.accountRepository = accountRepository;
        this.projectRepository = projectRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public void createNewTask(CreateNewTaskServiceModel createNewTaskServiceModel, Integer reportedById) {

        ProjectEntity projectEntity = projectRepository
                .getById(createNewTaskServiceModel.getProject());

        BoardEntity boardEntity = boardRepository
                .getById(createNewTaskServiceModel.getBoard());

        AccountEntity assignedTo = null;

        if (createNewTaskServiceModel.getAssignedTo() > 0) {
            assignedTo = accountRepository
                    .getById(createNewTaskServiceModel.getAssignedTo());
        }

        AccountEntity reportedBy = accountRepository
                .getById(reportedById);


        TaskEntity taskEntity = new TaskEntity();
        taskEntity
                .setTitle(createNewTaskServiceModel.getTitle())
                .setProject(projectEntity)
                .setPriority(PriorityEnum.valueOf(createNewTaskServiceModel.getPriority().name()))
                .setBoard(boardEntity)
                .setAssignedTo(assignedTo)
                .setStatus(StatusEnum.valueOf(createNewTaskServiceModel.getStatus().name()))
                .setReportedBy(reportedBy)
                .setType(TypeEnum.valueOf(createNewTaskServiceModel.getType().name()))
                .setStoryPoints(createNewTaskServiceModel.getStoryPoints())
                .setDescription(createNewTaskServiceModel.getDescription())
                .setCreatedAt(Instant.now())
                .setUpdateAt(Instant.now());


        taskRepository
                .save(taskEntity);

    }

    @Override
    public List<TaskInformationDTO> getAllOwnTasks(Integer accountId) {

        Map<TaskEntity, Map<String, String>> map = new HashMap<>();

        AccountEntity accountEntity = accountRepository.getById(accountId);

        List<TaskEntity> allByReportedBy = taskRepository
                .findAllByReportedBy(accountEntity);


        allByReportedBy
                .forEach(taskEntity -> {
                    String taskTitle = taskEntity.getTitle();
                    String assignedTo = taskEntity.getAssignedTo() != null ? taskEntity.getAssignedTo().getFirstName() : "empty";
                    String projectName = taskEntity.getProject().getTitle();
                    String reportedBy = taskEntity.getReportedBy().getFirstName();
                    String status = taskEntity.getStatus().name();
                    String priority = taskEntity.getPriority().name();

                    map.put(taskEntity, new HashMap<>());
                    map.get(taskEntity).put("taskTitle", taskTitle);
                    map.get(taskEntity).put("assignedTo", assignedTo);
                    map.get(taskEntity).put("projectName", projectName);
                    map.get(taskEntity).put("reportedBy", reportedBy);
                    map.get(taskEntity).put("status", status);
                    map.get(taskEntity).put("priority", priority);
                });

        return map.entrySet()
                .stream()
                .map(this::asDtoTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskInformationDTO> searchTasks(String title, String type, String assignedTo) {
        TypeEnum typeEnum = type.equals("null") ? null : TypeEnum.valueOf(type.toUpperCase());


        List<TaskEntity> taskEntities = taskRepository
                .searchForTasks(title.equals("null") ? null : title, typeEnum, assignedTo.equals("null") ? null : assignedTo);
        System.out.println(taskEntities.size());

        Map<TaskEntity, Map<String, String>> map = new HashMap<>();
        taskEntities
                .forEach(taskEntity -> {
                    String taskTitle = taskEntity.getTitle();
                    String assignedTo2 = taskEntity.getAssignedTo() != null ? taskEntity.getAssignedTo().getFirstName() : "empty";
                    String projectName = taskEntity.getProject().getTitle();
                    String reportedBy = taskEntity.getReportedBy().getFirstName();
                    String status = taskEntity.getStatus().name();
                    String priority = taskEntity.getPriority().name();

                    map.put(taskEntity, new HashMap<>());
                    map.get(taskEntity).put("taskTitle", taskTitle);
                    map.get(taskEntity).put("assignedTo", assignedTo2);
                    map.get(taskEntity).put("projectName", projectName);
                    map.get(taskEntity).put("reportedBy", reportedBy);
                    map.get(taskEntity).put("status", status);
                    map.get(taskEntity).put("priority", priority);
                });

        return map.entrySet()
                .stream()
                .map(this::asDtoTask)
                .collect(Collectors.toList());
    }

    private TaskInformationDTO asDtoTask(Map.Entry<TaskEntity, Map<String, String>> task) {

        return new TaskInformationDTO()
                .setId(task.getKey().getId())
                .setAssignedTo(task.getValue().get("assignedTo"))
                .setTaskTitle(task.getValue().get("taskTitle"))
                .setProjectName(task.getValue().get("projectName"))
                .setPriority(task.getValue().get("priority"))
                .setReportedBy(task.getValue().get("reportedBy"))
                .setStatus(task.getValue().get("status"));
    }
}
