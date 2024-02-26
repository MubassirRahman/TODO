package com.example.todo;
import java.util.Date;

public class Task {
    private String taskName;
    private Date dueDate;
    private boolean completed;

    public Task(String taskName, Date dueDate, boolean completed) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return taskName + " \nDue: " + dueDate + "\nCompleted: " +getCompletionStatusText();
    }
    public String getCompletionStatusText() {
        return completed ? "Yes" : "No";
    }
}

