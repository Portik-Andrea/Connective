package ro.sapientia.Backend.domains;

import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Optional;
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

    @OneToOne()
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

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<GroupInformation> information = new HashSet<>();

    @Column
    @ToString.Exclude
    @NotEmpty(message = "password is mandatory")
    private String password;

    @ToString.Exclude
    private String token;

    public User() {
    }

    public User(String firstName, String lastName, String email, Integer type, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
        this.department = department;

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

    public void addGroup(Group group){
        groups.add(group);
    }

    public void addGroupInformation(GroupInformation groupInformation){
        boolean result = information.add(groupInformation);
    }

    public void updateMentor(User mentor){
        if(this.type == 2 && mentor.getType() == 1){
            this.mentor = mentor;
        }
    }

    @Override
    public String toString() {
        StringBuilder userToString= new StringBuilder( "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", type=" + type +
                ", department=" + department +
                ", location='" + location + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", imageUrl='" + imageUrl + '\''
                );
        if(this.type==2 && mentor != null){
            String appendMentor =new String( ", mentor='"+ mentor.getFirstName() + " " + mentor.getLastName()+ '\'' +"}");
            userToString.append(appendMentor);
        }
        else{
            userToString.append(", mentor nincs}");
        }
        return userToString.toString();
    }
}
