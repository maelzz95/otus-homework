package org.otus.solodov;

public class Task {
    public enum TaskStatus {
        OPEN,
        IN_WORK,
        CLOSED
    }

    private long id;
    private String name;
    private TaskStatus status;

    public Task(long id, String name, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Task(id = %s, name = %s, status = %s)", this.id, this.name, this.status.toString());
    }
}
