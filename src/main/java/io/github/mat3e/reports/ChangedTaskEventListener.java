package io.github.mat3e.reports;

import io.github.mat3e.model.event.TaskDone;
import io.github.mat3e.model.event.TaskEvent;
import io.github.mat3e.model.event.TaskUnDone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
class ChangedTaskEventListener {
    public static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void on(TaskDone event) {
        onChanged(event);
    }

    @Async
    @EventListener
    public void on(TaskUnDone event) {
        onChanged(event);
    }

    private void onChanged(final TaskEvent event) {
        logger.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }

}
