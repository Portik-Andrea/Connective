package ro.sapientia.Backend.domains;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @SequenceGenerator(name = "task_id_gen", sequenceName = "task_id_seq", initialValue = 1)
    @GeneratedValue(generator = "task_id_gen")
    private Long taskId;

    @Column(name = "title")
    @NotEmpty(message = "Title is mandatory")
    private String title;

    @Column(name = "description")
    @NotEmpty(message = "Description is mandatory")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_user_id")
    private User assignedToUser;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @NotEmpty(message = "Creator is mandatory")
    private User creatorUser;

    @Column(name = "created_time")
    @NotEmpty(message = "The created time is mandatory")
    private LocalDate createdTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "progress")
    @Positive(message = "Progress is positive integer")
    private Integer progress;

    public Task() {
    }

    public Task(String title, String description, User creatorUser, LocalDate createdTime, Priority priority, LocalDate deadline, Status status, Integer progress) {
        this.title = title;
        this.description = description;
        this.creatorUser = creatorUser;
        this.createdTime = createdTime;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.progress = progress;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(User assignedToUser) {
        if (this.assignedToUser != null) {
            User oldAssignedToUser = this.assignedToUser;
            oldAssignedToUser.removeTask(this);
        }
        this.assignedToUser = assignedToUser;
        assignedToUser.addTask(this);
    }

    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
