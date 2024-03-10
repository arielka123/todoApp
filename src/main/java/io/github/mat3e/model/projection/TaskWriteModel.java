package io.github.mat3e.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskGroup;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

//DTO
public class TaskWriteModel {
    @NotBlank(message = "Task group's description must not be empty")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }


    public Task toTask(final TaskGroup group) {
        return new Task(description, deadline, group);
    }

    @Override
    public String toString() {
        return "GroupTaskWriteModel{" +
                "description='" + description + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
