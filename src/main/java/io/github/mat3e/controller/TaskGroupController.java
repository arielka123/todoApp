package io.github.mat3e.controller;

import io.github.mat3e.logic.TaskGroupService;
import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import io.github.mat3e.model.projection.GroupReadModel;
import io.github.mat3e.model.projection.GroupWriteModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/groups")
class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskRepository repository;
    private final TaskGroupService taskGroupService;

    TaskGroupController(final TaskRepository repository, TaskGroupService taskGroupService) {
        this.repository = repository;
        this.taskGroupService = taskGroupService;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        logger.warn(toCreate.tasks().toString());

        GroupReadModel result = taskGroupService.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.warn("Exposing all the groups");
        return ResponseEntity.ok(taskGroupService.readAll());
//        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }


    @Transactional
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class) /// zmienia kod błedu z 500 na 404
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?>handleStateException(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
