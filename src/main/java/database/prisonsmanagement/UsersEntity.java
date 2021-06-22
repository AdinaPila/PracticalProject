package database.prisonsmanagement;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Entity
@Table(name = "users")
public class UsersEntity {

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

    public UsersEntity userRegistration() {
        UsersEntity user = new UsersEntity();
        System.out.println("Insert user first name: ");
        user.setFirstName(Utils.scannerOptionString());
        System.out.println("Insert user last name: ");
        user.setLastName(Utils.scannerOptionString());
        selectUserRankAndAccessLevel(user);
        System.out.println("Insert the login email address: ");
        user.setAppEmail(Utils.scannerOptionString());
        System.out.println("Insert the password: ");
        user.setAppPassword(Utils.scannerOptionString());
        System.out.println("Insert user CNP: ");
        user.setCnp(null);
        return user;
    }

    public String selectUserRankAndAccessLevel(Object user) {
        System.out.println("Insert user rank:\n 1.LIEUTENANT\n" +
                "2.CAPTAIN\n" +
                "3.MAJOR\n" +
                "4.COLONEL\n" +
                "5.BRIGADIER\n" +
                "6.GENERAL");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                ((UsersEntity) user).setUserRank(UserRank.LIEUTENANT.getRank());
                break;
            case 2:
                ((UsersEntity) user).setUserRank(UserRank.CAPTAIN.getRank());
                break;
            case 3:
                ((UsersEntity) user).setUserRank(UserRank.MAJOR.getRank());
                break;
            case 4:
                ((UsersEntity) user).setUserRank(UserRank.COLONEL.getRank());
                break;
            case 5:
                ((UsersEntity) user).setUserRank(UserRank.BRIGADIER.getRank());
                break;
            case 6:
                ((UsersEntity) user).setUserRank(UserRank.GENERAL.getRank());
        }
        System.out.println("The user rank is: " + ((UsersEntity) user).getUserRank());
        if (option > 3) {
            ((UsersEntity) user).setAccessLevel(1);
        } else {
            ((UsersEntity) user).setAccessLevel(2);
        }
        System.out.println("The user acces level is: " + ((UsersEntity) user).getAccessLevel());
        return ((UsersEntity) user).getUserRank();
    }


    void selectForUpate(Object entity) {
        System.out.println("What element would you linke to be updated?\n1.First name\n2.Last name\n3.CNP\n4.User Rank\n5.Email address\n6.Password");
        int option = Utils.scannerOption();
        Scanner scanner = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("Introduce the new first name: ");
                ((UsersEntity) entity).setFirstName(scanner.nextLine());
                break;
            case 2:
                System.out.println("Introduce the new last name: ");
                ((UsersEntity) entity).setLastName(scanner.nextLine());
                break;
            case 3:
                System.out.println("Introduce the new cnp: ");
                ((UsersEntity) entity).setCnp(scanner.nextLine());
                break;
            case 4:
                System.out.println("Introduce the new rank: ");
                ((UsersEntity) entity).setUserRank(selectUserRankAndAccessLevel((UsersEntity) entity));
                break;
            case 5:
                System.out.println("Introduce the new email address: ");
                ((UsersEntity) entity).setAppEmail(scanner.next());
                break;
            case 6:
                System.out.println("Introduce the new password: ");
                ((UsersEntity) entity).setAppPassword(scanner.next());
        }

    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersEntity)) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(getFirstName(), that.getFirstName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getCnp(), that.getCnp()) && Objects.equals(getUserRank(), that.getUserRank()) && Objects.equals(getAccessLevel(), that.getAccessLevel()) && Objects.equals(getAppEmail(), that.getAppEmail()) && Objects.equals(getAppPassword(), that.getAppPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getCnp(), getUserRank(), getAccessLevel(), getAppEmail(), getAppPassword());
    }
}
