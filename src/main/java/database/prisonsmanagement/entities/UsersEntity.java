package database.prisonsmanagement.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UsersEntity extends AppHibernate {

    private String firstName;

    private String lastName;

    @Id
    @Column(name = "cnp", unique = true, nullable = false, columnDefinition = "VARCHAR(13)")
    private String cnp;

    private String userRank;

    private Integer accessLevel;

    private String appEmail;

    private String appPassword;

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

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getAppEmail() {
        return appEmail;
    }

    public void setAppEmail(String appEmail) {
        this.appEmail = appEmail;
    }

    public String getAppPassword() {
        return appPassword;
    }

    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cnp='" + cnp + '\'' +
                ", userRank='" + userRank + '\'' +
                ", accessLevel=" + accessLevel +
                ", appEmail='" + appEmail + '\'' +
                ", appPassword='" + appPassword + '\'' +
                '}';
    }
}
