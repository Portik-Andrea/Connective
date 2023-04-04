package ro.sapientia.Backend.domains;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(name = "group_id_gen", sequenceName = "group_id_seq", initialValue = 1)
    @GeneratedValue(generator = "group_id_gen")
    private Long id;

    @Column(name = "group_name")
    @NotEmpty(message = "Group name is mandatory")
    private String groupName;

    @ManyToMany()
    @JoinTable(name = "group_user",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "group")
    private Set<GroupInformation> information = new HashSet<>();

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Set<GroupInformation> getInformation() {
        return information;
    }

    public void setInformation(Set<GroupInformation> information) {
        this.information = information;
    }
}