package com.system.management.web;

import com.system.management.model.binding.CreateNewTaskBindingModel;
import com.system.management.model.dto.TaskInformationDTO;
import com.system.management.model.service.CreateNewTaskServiceModel;
import com.system.management.model.user.CurrentUser;
import com.system.management.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/task")
public class TaskController {

        private final TaskService taskService;
        private final ModelMapper modelMapper;
        private final CurrentUser currentUser;

    public TaskController(TaskService taskService, ModelMapper modelMapper, CurrentUser currentUser) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @GetMapping("/create")
    public ResponseEntity<Object> getAllNecessaryFields() {
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postNewTask(@Valid @RequestBody CreateNewTaskBindingModel createNewTaskBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            redirectAttributes
                    .addFlashAttribute("createNewTaskBindingModel",createNewTaskBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult",bindingResult);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
        }


        try {
            taskService
                    .createNewTask(modelMapper.map(createNewTaskBindingModel, CreateNewTaskServiceModel.class), currentUser.getId());
        } catch (Exception ex) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Object> deleteTask(@PathVariable Integer id) {
//
//
//return null;
//    }

    @PreAuthorize("isLogged()")
    @GetMapping("/own/task")
    public ResponseEntity<Object> getAllOwnTasks() {
        List<TaskInformationDTO> allOwnTasks = taskService
                .getAllOwnTasks(currentUser.getId());

        return ResponseEntity.ok().body(allOwnTasks);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchForTasks(
            @RequestParam(name = "title",defaultValue = "null", required = false) String title,
            @RequestParam(name = "type",defaultValue = "null", required = false) String type,
            @RequestParam(name = "assignedTo",defaultValue = "null", required = false) String assignedTo
    ) {

        List<TaskInformationDTO> taskInformationDTOS = null;
        try {
            taskInformationDTOS = taskService
                    .searchTasks(title, type, assignedTo);
        } catch (Exception ignored){

        }


        return ResponseEntity.ok().body(taskInformationDTOS);
    }


}
