package io.github.mat3e.logic;

import io.github.mat3e.TaskConfigurationProperties;
import io.github.mat3e.model.ProjectRepository;
import io.github.mat3e.model.TaskGroupRepository;
import io.github.mat3e.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class LogicConfiguration {

    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config
    ) {
        return new ProjectService(repository, taskGroupRepository, config);
    }

    @Bean
    @RequestScope
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            final TaskRepository taskRepository) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }
}