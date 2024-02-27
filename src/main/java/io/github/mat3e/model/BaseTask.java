package io.github.mat3e.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
abstract class BaseTask {
    @NotBlank(message = "Task group's description must not be empty")
    private String description;
    private boolean done;

    String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
