package ro.sapientia.Backend.domains;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

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
    private Long createdTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "deadline")
    private Long deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "progress")
    @Positive(message = "Progress is positive integer")
    private Integer progress;

    public Task() {
    }
}
