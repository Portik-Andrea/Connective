package ro.sapientia.Backend.domains;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="group_information")
public class GroupInformation  {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "group_inf_id_gen", sequenceName = "group_inf_id_seq", initialValue = 1)
    @GeneratedValue(generator = "group_inf_id_gen")
    private Long groupInformationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "joining_date")
    private Date joiningDate;

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
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        group.addMember(this.user);
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public UserEntity getInvitingUser() {
        return invitingUser;
    }

    public void setInvitingUser(UserEntity invitingUser) {
        this.invitingUser = invitingUser;
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
