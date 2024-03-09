package io.github.mat3e.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "task_group")
public class TaskGroup extends BaseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //lazy loading when it is needed and cascade all when group is removed - remove all tasks
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    @JsonIgnore
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

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public boolean isDone() {
        return super.isDone();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public void setDone(boolean done) {
        super.setDone(done);
    }
}
