package io.github.mat3e.controller;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> addNewTask(@RequestBody @Valid Task toCreate) {

        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(toCreate);
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
        //aby było widać metadane z HATEOSA przy tych wywyłaniach z !
    ResponseEntity<List<Task>> readAllTask() {
        logger.warn("Exposing all the task");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/tasks")
        /// w ten sposób metodawołana jest nie z repo a z kontrolera
    ResponseEntity<List<Task>> readAllTask(Pageable pageable) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(pageable).getContent());

//        return ResponseEntity.ok(repository.findAll(pageable).getContent());
// wiecej info w jsonie np offset i max pages wiec moze lepiej zostawic
//        ResponseEntity<?> readAllTask(Pageable pageable)
    }


    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        Optional<Task> opt = repository.findById(id);

        return opt.map(task -> ResponseEntity.ok(task))
                .orElse((ResponseEntity.notFound().build()));
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

}
