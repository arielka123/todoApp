package io.github.mat3e.model.event;

import io.github.mat3e.model.Task;

import java.time.Clock;
import java.time.Instant;

public abstract class TaskEvent {
    public static TaskEvent changed(Task source){
        return source.isDone() ? new TaskDone(source) : new TaskUnDone(source);
    }

    private int taskId;
    private Instant occurrence;

    TaskEvent(final int taskId, Clock clock) {
        this.taskId = taskId;
        this.occurrence = Instant.now(clock);
    }

    public int taskId() {
        return taskId;
    }

    public Instant occurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +"{" +
                "taskId=" + taskId +
                ", occurrence=" + occurrence +
                '}';
    }
}
