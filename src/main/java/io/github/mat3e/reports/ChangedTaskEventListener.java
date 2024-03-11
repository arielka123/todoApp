package io.github.mat3e.reports;

import io.github.mat3e.model.event.TaskDone;
import io.github.mat3e.model.event.TaskUnDone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ChangedTaskEventListener {
    public static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    @EventListener
    public void on(TaskDone event) {
        logger.info("Got " + event);
    }

    @EventListener
    public void on(TaskUnDone event) {
        logger.info("Got " + event);
    }

}
