package io.github.mat3e.model.event;

import io.github.mat3e.model.Task;

import java.time.Clock;

public class TaskUnDone extends TaskEvent {
    TaskUnDone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
