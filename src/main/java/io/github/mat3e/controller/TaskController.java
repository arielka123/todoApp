package io.github.mat3e.controller;

import io.github.mat3e.logic.TaskService;
import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/tasks")
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;
    private final TaskService service;

    TaskController(final TaskRepository repository, TaskService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/test")
    void oldFashionedWay(HttpServletRequest req, HttpServletResponse resp) { //starsza, niskopoziomowa mechanika obsługi
        try {
            System.out.println(req.getParameter("foo"));
            resp.getWriter().println("test oldFashionedWay");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Task> addNewTask(@RequestBody @Valid Task toCreate) {

        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(toCreate);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"!sort", "!page", "!size"})
        //aby było widać metadane z HATEOSA przy tych wywyłaniach z !
    CompletableFuture<ResponseEntity<List<Task>>> readAllTask() {
        logger.warn("Exposing all the task");
//        return ResponseEntity.ok(repository.findAll());
        return service.findAllAsync().thenApply(ResponseEntity::ok);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Task>> readAllTask(Pageable pageable) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(pageable).getContent());

//        return ResponseEntity.ok(repository.findAll(pageable).getContent());
// wiecej info w jsonie np offset i max pages wiec moze lepiej zostawic
//        ResponseEntity<?> readAllTask(Pageable pageable)
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/today")
//    ResponseEntity<List<Task>> readAllTaskToday() {
//        logger.info("task to done today");
//        return ResponseEntity.ok(repository.findAllByDoneIsFalseAndDeadlineIsNullOrDeadlineBeforeOrderByDeadlineAsc(LocalDateTime.now()));
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        Optional<Task> opt = repository.findById(id);

        return opt.map(task -> ResponseEntity.ok(task))
                .orElse((ResponseEntity.notFound().build()));
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) { //domyslnie szukamy zrobionych tasków
        return ResponseEntity.ok(repository.findByDone(state));
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
//        toUpdate.setId(id);
//        repository.save(toUpdate);
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }
}
