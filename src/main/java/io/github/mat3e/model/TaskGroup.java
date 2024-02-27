package io.github.mat3e.model;

import jakarta.persistence.*;

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

    @ManyToOne //wiele tasków w jednej grupie
    @JoinColumn(name = "project_id")
    private Project project;

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

    Project getProject() {
        return project;
    }

    void setProject(Project project) {
        this.project = project;
    }
}
