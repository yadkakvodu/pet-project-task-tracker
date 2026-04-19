package model;

public class Task {

    public String title;
    public String description;
    public String priority;
    public int creator_id;
    public int assignee_id;
    public int project_id;

    public Task(String title, String description, String priority, int creator_id, int assignee_id, int project_id) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.creator_id = creator_id;
        this.assignee_id = assignee_id;
        this.project_id = project_id;
    }

    public Task(String title, String priority, int creator_id, int assignee_id, int project_id) {
        this.title = title;
        this.priority = priority;
        this.creator_id = creator_id;
        this.assignee_id = assignee_id;
        this.project_id = project_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public int getAssignee_id() {
        return assignee_id;
    }

    public int getProject_id() {
        return project_id;
    }
}
