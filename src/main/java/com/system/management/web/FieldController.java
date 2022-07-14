package com.system.management.web;

import com.system.management.model.dto.BoardInformationDTO;
import com.system.management.model.dto.ProjectInformationDTO;
import com.system.management.model.dto.UserInformationDTO;
import com.system.management.model.entity.enums.PriorityEnum;
import com.system.management.model.entity.enums.StatusEnum;
import com.system.management.model.entity.enums.TypeEnum;
import com.system.management.model.user.CurrentUser;
import com.system.management.service.BoardService;
import com.system.management.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/field")
public class FieldController {

    private final BoardService boardService;
    private final CurrentUser currentUser;
    private final ProjectService projectService;

    public FieldController(BoardService boardService, CurrentUser currentUser, ProjectService projectService) {
        this.boardService = boardService;
        this.currentUser = currentUser;
        this.projectService = projectService;
    }


    // Boards
    //    RETURN ALL BOARDs
    @PreAuthorize("isLogged()")
    @GetMapping("/boards")
    public ResponseEntity<Object> getAllBoards() {

        List<BoardInformationDTO> allBoards = this.boardService
                .getAllBoards();


        return ResponseEntity.ok().body(allBoards);
    }

    //    Projects
    //    GET ALL PROJECTS FOR Account owner FIELD IN TASK
    @PreAuthorize("isLogged()")
    @GetMapping("/projects")
    public ResponseEntity<Object> getAllProjectsByAccountOwn() {

        System.out.println(currentUser.getEmail() + " " + currentUser.getId());
        List<ProjectInformationDTO> projectsByAccountId = this.projectService
                .getProjectsByAccountId(currentUser.getId());

        return ResponseEntity.ok().body(projectsByAccountId);
    }

    //    Assigned to
    //    Return Board member by Board id
    @PreAuthorize("isLogged()")
    @GetMapping("/board/members/{id}")
    public ResponseEntity<Object> getAllBoardMembers(@PathVariable Integer id) {
        List<UserInformationDTO> allBoardMembers = null;

        if (id != null && id > 0) {
           try {
               allBoardMembers = boardService
                       .getAllBoardMembers(id);
           } catch (NullPointerException ignored){
           }
        }

        return ResponseEntity.ok().body(allBoardMembers);
    }

    //    Return enums
    //    Priority,Status,
//    @PreAuthorize("isLogged()")
    @GetMapping("/enums")
    public ResponseEntity<Object> getAllEnums() {

        List<Object> listEnums = new ArrayList<>();

        List<PriorityEnum> priorityEnums = Arrays.stream(PriorityEnum.values()).toList();
        List<StatusEnum> statusEnums = Arrays.stream(StatusEnum.values()).toList();
        List<TypeEnum> typeEnums = Arrays.stream(TypeEnum.values()).toList();

        listEnums.add(priorityEnums);
        listEnums.add(statusEnums);
        listEnums.add(typeEnums);

        Map<String, List<String>> mapFields = new HashMap<>();

        //       [PriorityEnums,StatusEnums, etc..]  ENUMS -> List<String> -> Map<K,List<String>>
        listEnums
                .forEach(o -> {
                    if (priorityEnums.equals(o)) {
                        if (!mapFields.containsKey("priority")) {
                            mapFields.put("priority", new ArrayList<>());
                        }
                        for (PriorityEnum priorityEnum : (List<PriorityEnum>) o) {
                            mapFields.get("priority").add(priorityEnum.name());
                        }
                    } else if (statusEnums.equals(o)) {
                        if (!mapFields.containsKey("status")) {
                            mapFields.put("status", new ArrayList<>());
                        }
                        for (StatusEnum statusEnum : (List<StatusEnum>) o) {
                            mapFields.get("status").add(statusEnum.name());
                        }
                    } else if (typeEnums.equals(o)) {
                        if (!mapFields.containsKey("type")) {
                            mapFields.put("type", new ArrayList<>());
                        }
                        for ( TypeEnum typeEnum : (List<TypeEnum>) o) {
                         mapFields.get("type").add(typeEnum.name());
                        }
                    }
                });

        return ResponseEntity.ok().body(mapFields);
    }
}
