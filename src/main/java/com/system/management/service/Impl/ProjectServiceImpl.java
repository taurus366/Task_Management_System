package com.system.management.service.Impl;

import com.system.management.exception.NotOwnerException;
import com.system.management.model.dto.ProjectInformationDTO;
import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.BoardEntity;
import com.system.management.model.entity.ProjectEntity;
import com.system.management.model.entity.TaskEntity;
import com.system.management.model.service.CreateNewProjectServiceModel;
import com.system.management.model.user.CurrentUser;
import com.system.management.repository.AccountRepository;
import com.system.management.repository.BoardRepository;
import com.system.management.repository.ProjectRepository;
import com.system.management.service.ProjectService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;
    private final BoardRepository boardRepository;
    private final CurrentUser currentUser;

    public ProjectServiceImpl(AccountRepository accountRepository, ProjectRepository projectRepository, BoardRepository boardRepository, CurrentUser currentUser) {
        this.accountRepository = accountRepository;
        this.projectRepository = projectRepository;
        this.boardRepository = boardRepository;
        this.currentUser = currentUser;
    }

    @Override
    public void createNewProject(String email, CreateNewProjectServiceModel createNewProjectServiceModel) {

        AccountEntity account = this.accountRepository
                .getAccountEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("We couldn't find the account email"));

        ProjectEntity project = new ProjectEntity();
        project
                .setKey(createNewProjectServiceModel.getKey())
                .setTitle(createNewProjectServiceModel.getTitle())
                .setOwner(account)
                .setCreatedAt(Instant.now())
                .setUpdateAt(Instant.now());

        projectRepository
                .save(project);
    }

    @Override
    public List<ProjectInformationDTO> getProjectsByAccountId(Integer ownerId) {

        return this.projectRepository
                .getAllProjectsByOwnerId(ownerId)
                .stream()
                .map(this::asDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Integer projectId, Integer ownerId) {

        ProjectEntity projectListBoardGraph = projectRepository
                .getProjectListBoardGraph(projectId);

        if (projectListBoardGraph.getOwner().getId() != ownerId){
            throw new NotOwnerException("");
        }

        List<TaskEntity> taskList = projectListBoardGraph
                .getTaskList();

        for (TaskEntity task : taskList) {
            BoardEntity boardByIdTasksGraph = boardRepository
                    .getBoardByIdTasksGraph(task.getBoard().getId());
            Integer boardId = boardByIdTasksGraph.getId();
            List<TaskEntity> taskList1 = boardByIdTasksGraph.getTaskList();
            boolean canDelete = true;
            for (TaskEntity task2 : taskList1) {
                ProjectEntity project = task2.getProject();
                if (!Objects.equals(project.getId(), projectId)) {
                    canDelete = false;
                    break;
                }
            }
            if (canDelete) {

                List<AccountEntity> allByBoardLists2 = accountRepository.findAllByBoardLists(boardByIdTasksGraph);
                for (AccountEntity allByBoardList : allByBoardLists2) {
                    List<BoardEntity> boardLists = allByBoardList.getBoardLists()
                            .stream()
                            .filter(board -> board.getId() != boardByIdTasksGraph.getId())
                            .collect(Collectors.toList());

                    allByBoardList.setBoardLists(boardLists);
                    accountRepository
                            .save(allByBoardList);

                }

                boardRepository
                        .deleteById(boardId);
            }

        }
        projectRepository
                .deleteById(projectId);

    }

    @Override
    public List<ProjectInformationDTO> getAllOwnProjects(Integer ownerId) {

        Map<ProjectEntity, Map<String, Integer>> map = new HashMap<>();

        AccountEntity accountEntity = accountRepository.getById(ownerId);

        List<ProjectEntity> allByOwner = projectRepository.findAllByOwner(accountEntity);

        allByOwner
                .forEach(project -> {
                    int taskSize = project.getTaskList().size();

                    map.put(project,new HashMap<>());
                    map.get(project).put("taskSize",taskSize);
                });

        return map
                .entrySet()
                .stream()
                .map(this::asDTOs)
                .collect(Collectors.toList());
    }

    private ProjectInformationDTO asDTOs(Map.Entry<ProjectEntity, Map<String, Integer>> project){
        return new ProjectInformationDTO()
                .setKey(project.getKey().getKey())
                .setId(project.getKey().getId())
                .setTitle(project.getKey().getTitle())
                .setTaskSize(project.getValue().get("taskSize"))
                .setOwner(project.getKey().getOwner().getId() == currentUser.getId());
    }
    private ProjectInformationDTO asDTO(ProjectEntity project) {

        return new ProjectInformationDTO()
                .setId(project.getId())
                .setTitle(project.getTitle())
                .setKey(project.getKey());
    }
}
