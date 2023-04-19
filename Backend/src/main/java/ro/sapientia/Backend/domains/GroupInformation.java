package ro.sapientia.Backend.domains;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="group_information")
public class GroupInformation  {
    @Id
    @Column(name = "id")
    private Long groupInformationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @ManyToOne()
    @JoinColumn(name = "inviting_user_id")
    private UserEntity invitingUser;

    public GroupInformation() {
    }

    public Long getId() {
        return groupInformationId;
    }

    public void setId(Long id) {
        this.groupInformationId = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
        user.addGroupInformation(this);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        group.addInformation(this);
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public UserEntity getAdderUserId() {
        return invitingUser;
    }

    public void setAdderUserId(UserEntity adderUser) {
        this.invitingUser = adderUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupInformation that = (GroupInformation) o;
        return groupInformationId.equals(that.groupInformationId) && user.equals(that.user) && group.equals(that.group) && joiningDate.equals(that.joiningDate) && invitingUser.equals(that.invitingUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupInformationId, user, group, joiningDate, invitingUser);
    }
}
