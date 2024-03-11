package io.github.mat3e.model.repository;

import io.github.mat3e.model.TaskGroup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TaskGroupRepository {
    List<TaskGroup> findAll();

    Optional<TaskGroup> findById(Integer id);

    TaskGroup save(TaskGroup entity);

    boolean existsByDoneIsFalseAndAndProject_Id(Integer projectId);

    boolean existsByDescription(String description);
}
