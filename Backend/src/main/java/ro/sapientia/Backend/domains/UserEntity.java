package ro.sapientia.Backend.domains;

import jakarta.persistence.*;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @OneToOne()
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_url",columnDefinition = "bytea")
    private byte[] imageUrl;

    //egy mentor tobb mentoralt vagy egy mentor egy mentoralt ??
    @OneToOne
    @JoinColumn(name = "mentor_id")
    private UserEntity mentor;

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

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, UserType type, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
        this.department = department;
    }

    public UserEntity(String firstName, String lastName, String email, UserType type, Department department, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
        this.department = department;
        this.password=password;
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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
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

    public byte[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public void setMentor(UserEntity mentor) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public void updateMentor(UserEntity mentor){
        if(this.type == UserType.MENTEE && mentor.getType() == UserType.MENTOR){
            this.mentor = mentor;
        }
    }

    public void updateDepartment(Department department){
        if(department.getDepartmentId() != null){
            this.department=department;
        }
    }


    @Override
    public String toString() {
        StringBuilder userToString= new StringBuilder( "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", department=" + department +
                ", location='" + location + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", imageUrl='" + imageUrl + '\''
                );
        if(this.type==UserType.MENTEE && mentor != null){
            String appendMentor =new String( ", mentor='"+ mentor.getFirstName() + " " + mentor.getLastName()+ '\'' +"}");
            userToString.append(appendMentor);
        }
        else{
            userToString.append(", mentor nincs}");
        }
        return userToString.toString();
    }
}
