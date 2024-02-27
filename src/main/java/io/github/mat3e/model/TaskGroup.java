package io.github.mat3e.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.aop.target.LazyInitTargetSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task_group")
public class TaskGroup extends BaseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //lazy loading when it is needed and cascade all when group is removed - remove all tasks
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    public TaskGroup() {
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
