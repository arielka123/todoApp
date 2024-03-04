package io.github.mat3e.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime deadline;

    @Embedded
    private final Audit audit = new Audit();

    @ManyToOne //wiele tasków w jednej grupie
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

    public Task() {
    }

    public Task(String description, LocalDateTime deadline) {
        this(description,deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.deadline=deadline;
        super.setDescription(description);

        if(group!=null){
            this.group = group;
        }
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDeadLine() {
        return deadline;
    }

    void setDeadLine(LocalDateTime deadLine) {
        this.deadline = deadLine;
    }

    public TaskGroup getGroup() {
        return group;
    }

    public void setGroup(TaskGroup group) {
        this.group = group;
    }

    public void updateFrom(final Task source) {
        super.setDescription(source.getDescription());
        super.setDone(source.isDone());
        deadline = source.deadline;
        group = source.group;
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public boolean isDone() {
        return super.isDone();
    }
}
