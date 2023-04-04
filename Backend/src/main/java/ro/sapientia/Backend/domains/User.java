package ro.sapientia.Backend.domains;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", initialValue = 1)
    @GeneratedValue(generator = "user_id_gen")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First name is mandatory")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name is mandatory")
    private String lastName;

    @Column(unique = true)
    @NotEmpty(message = "email is mandatory")
    private String email;

    @Column(name = "password_key")
    private String passwordKey;

    @Column(name = "type")
    private Integer type;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_url")
    private String imageUrl;

    //egy mentor tobb mentoralt vagy egy mentor egy mentoralt ??
    @OneToOne
    @JoinColumn(name = "mentor_id")
    private User mentor;

    //private String token;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<GroupInformation> information = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getMentor() {
        return mentor;
    }

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<GroupInformation> getInformation() {
        return information;
    }

    public void setInformation(Set<GroupInformation> information) {
        this.information = information;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeTask(Task task){
        tasks.remove(task);
    }
}
