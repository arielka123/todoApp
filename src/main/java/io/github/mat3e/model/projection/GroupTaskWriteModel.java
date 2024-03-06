package io.github.mat3e.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
//DTO
public class GroupTaskWriteModel {
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
        return new Task(description,deadline, group);
    }

    @Override
    public String toString() {
        return "GroupTaskWriteModel{" +
                "description='" + description + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
