package ro.sapientia.Backend.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

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
    private UserEntity assignedToUser;

    @OneToOne
    private UserEntity creatorUser;

    @Column(name = "created_time")
    private Date createdTime;

    @OneToOne
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "deadline")
    private Date deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "progress")
    //@Positive(message = "Progress is positive integer")
    private Integer progress;

    public Task() {
    }

    public Task(String title, String description, UserEntity creatorUser,
                Date createdTime, Priority priority, Date deadline,
                Status status, Integer progress) {
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

    public UserEntity getAssignedToUser() {
        return assignedToUser;
    }

    public UserEntity deleteAssignedUser(){
        if (this.assignedToUser != null) {
            UserEntity oldAssignedToUser = this.assignedToUser;
            this.assignedToUser = null;
            oldAssignedToUser.removeTask(this);
            return oldAssignedToUser;
        }
        return null;
    }

    public void setAssignedToUser(UserEntity assignedToUser) {
        if(this.assignedToUser == null){
            this.assignedToUser = assignedToUser;
            assignedToUser.addTask(this);
        }
    }

    public UserEntity getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(UserEntity creatorUser) {
        this.creatorUser = creatorUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
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
