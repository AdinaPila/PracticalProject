package database.prisonsmanagement.services;

import database.prisonsmanagement.UserRank;
import database.prisonsmanagement.Utils;
import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.UsersEntity;

import java.util.Scanner;

public class UsersServices extends AppHibernate {

    public void insertUser(UsersEntity object) {
        object = userRegistration();
        String userCnp = Utils.scannerOptionString();
        Utils.setCnp(object,userCnp);
        insert(object);
    }

    public void updateUser(UsersEntity user, String cnp){
        user = findUsersByCnp(cnp);
        selectForUpdateUser(user);
        update(user, cnp);
    }

    public void selectForUpdateUser(UsersEntity entity) {
        System.out.println("What element would you like to be updated?\n1.First name\n2.Last name\n3.CNP\n4.User Rank\n5.Email address\n6.Password");
        int option = Utils.scannerOption();
        Scanner scanner = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("Introduce the new first name: ");
                entity.setFirstName(scanner.nextLine());
                break;
            case 2:
                System.out.println("Introduce the new last name: ");
                entity.setLastName(scanner.nextLine());
                break;
            case 3:
                System.out.println("Introduce the new cnp: ");
                String userCnp = Utils.scannerOptionString();
                if (Utils.isCNPValid(userCnp)) {
                    entity.setCnp(userCnp);
                } else {

                    while (Utils.isCNPValid(userCnp) == false) {
                        System.out.println("CNP is not valid. Try again");
                        userCnp = Utils.scannerOptionString();
                    }
                    entity.setCnp(userCnp);
                }
                break;
            case 4:
                System.out.println("Introduce the new rank: ");
                entity.setUserRank(selectUserRankAndAccessLevel(entity));
                break;
            case 5:
                System.out.println("Introduce the new email address: ");
                entity.setAppEmail(scanner.next());
                break;
            case 6:
                System.out.println("Introduce the new password: ");
                entity.setAppPassword(scanner.next());
        }

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

        return user;
    }


    public String selectUserRankAndAccessLevel(UsersEntity user) {
        System.out.println("Insert user rank:\n 1.LIEUTENANT\n" +
                "2.CAPTAIN\n" +
                "3.MAJOR\n" +
                "4.COLONEL\n" +
                "5.BRIGADIER\n" +
                "6.GENERAL");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                 user.setUserRank(UserRank.LIEUTENANT.getRank());
                break;
            case 2:
                user.setUserRank(UserRank.CAPTAIN.getRank());
                break;
            case 3:
                user.setUserRank(UserRank.MAJOR.getRank());
                break;
            case 4:
                user.setUserRank(UserRank.COLONEL.getRank());
                break;
            case 5:
                user.setUserRank(UserRank.BRIGADIER.getRank());
                break;
            case 6:
                user.setUserRank(UserRank.GENERAL.getRank());
        }
        System.out.println("The user rank is: " + user.getUserRank());
        if (option > 3) {
            user.setAccessLevel(1);
        } else {
            user.setAccessLevel(2);
        }
        System.out.println("The user access level is: " + user.getAccessLevel());
        return user.getUserRank();
    }
}
