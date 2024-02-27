package io.github.mat3e.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    List<TaskGroup> findAll();

    Optional<TaskGroup> findById();

    Task save(Task entity);
}
