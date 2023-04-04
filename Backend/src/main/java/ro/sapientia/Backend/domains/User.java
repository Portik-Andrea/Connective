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
}
