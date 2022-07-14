package com.system.management.web;

import com.system.management.exception.NotOwnerException;
import com.system.management.model.binding.CreateNewProjectBindingModel;
import com.system.management.model.dto.ProjectInformationDTO;
import com.system.management.model.service.CreateNewProjectServiceModel;
import com.system.management.model.user.CurrentUser;
import com.system.management.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public ProjectController(ProjectService projectService, ModelMapper modelMapper, CurrentUser currentUser) {
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @PreAuthorize("isLogged()")
    @PostMapping("/create")
    public ResponseEntity<Object> createProject(@Valid @RequestBody CreateNewProjectBindingModel createNewProjectBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes
                        .addFlashAttribute("createNewProjectBindingModel", createNewProjectBindingModel)
                        .addFlashAttribute("org.springframework.validation.BindingResult.createNewProjectBindingModel", bindingResult);

                return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
            }

            CreateNewProjectServiceModel mapped = modelMapper.map(createNewProjectBindingModel, CreateNewProjectServiceModel.class);


            this.projectService
                    .createNewProject(currentUser.getEmail(), mapped);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("isLogged()")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable Integer id) {

        try {
            projectService
                    .deleteProject(id, currentUser.getId());
        } catch (NotOwnerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isLogged()")
    @GetMapping("/own/project")
    public ResponseEntity<Object> getAllOwnProjects() {

        List<ProjectInformationDTO> allOwnProjects = projectService
                .getAllOwnProjects(currentUser.getId());
        return ResponseEntity.ok().body(allOwnProjects);
    }


}
