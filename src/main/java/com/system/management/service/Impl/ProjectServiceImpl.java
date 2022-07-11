package com.system.management.service.Impl;

import com.system.management.model.entity.AccountEntity;
import com.system.management.model.entity.ProjectEntity;
import com.system.management.model.service.CreateNewProjectServiceModel;
import com.system.management.repository.AccountRepository;
import com.system.management.repository.ProjectRepository;
import com.system.management.service.ProjectService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(AccountRepository accountRepository, ProjectRepository projectRepository) {
        this.accountRepository = accountRepository;
        this.projectRepository = projectRepository;
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
}
