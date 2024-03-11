package io.github.mat3e.reports;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.mat3e.model.Task;
import io.github.mat3e.model.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
class ReportController {
    public static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final TaskRepository taskRepository;
    private final PersistedTaskEventRepository eventRepository;

    public ReportController(TaskRepository taskRepository, PersistedTaskEventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithChangesCount(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithChangesCount(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/deadline/{id}")
    ResponseEntity<TaskWithDeadlineReport> readTaskWithDeadline(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithDeadlineReport(task, eventRepository.findByTaskIdAndNameOrderByOccurrenceDesc(id, "TaskDone")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/deadline/done={done}")
    ResponseEntity<?> readTaskWithDeadlineDone(@PathVariable boolean done) {
        List<Task> tasks = taskRepository.findByDone(done);
        return ResponseEntity.ok(taskRepository.findByDone(done));
    }

    private static class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;


        TaskWithChangesCount(final Task task, final List<PersistedTaskEvent> events) {
            description = task.getDescription();
            done = task.isDone();
            changesCount = events.size();
        }
    }

    private static class TaskWithDeadlineReport {
        public static final Logger logger = LoggerFactory.getLogger(TaskWithDeadlineReport.class);
        public String description;
        public boolean done;
        public boolean afterDeadlineDone;
        public boolean beforeDeadlineDone;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        public LocalDateTime date;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        public LocalDateTime deadline;

        TaskWithDeadlineReport(final Task task, final List<PersistedTaskEvent> event) {
            description = task.getDescription();
            done = task.isDone();
            deadline = task.getDeadline();
            date = event.getFirst().occurrence;
            afterDeadlineDone = task.isDone() && date.isAfter(task.getDeadline());
            beforeDeadlineDone = task.isDone() && date.isBefore(task.getDeadline());
        }
    }
}
