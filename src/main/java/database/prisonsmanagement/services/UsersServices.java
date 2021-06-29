package com.sda.alina.exercises.prisonsmanagement.services;

import database.prisonsmanagement.UserRank;
import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.UsersEntity;
import database.prisonsmanagement.utils.Utils;

import java.util.Scanner;

public class UsersServices extends AppHibernate {

    public void insertUser(UsersEntity object) {
        object = userRegistration();
        String userCnp = Utils.scannerOptionString();
        Utils.setCnp(object, userCnp);
        insert(object);
    }

    public void updateUser(UsersEntity user, String cnp) {
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
                String firstName = Utils.scannerOptionString();
                if (Utils.isNameValid(firstName)) {
                    entity.setFirstName(firstName);
                } else {
                    System.out.println("The name is not valid. Try again");
                    firstName = Utils.scannerOptionString();
                    Utils.isNameValid(firstName);
                }

                break;
            case 2:
                System.out.println("Introduce the new last name: ");
                String lastName = Utils.scannerOptionString();
                if (Utils.isNameValid(lastName)) {
                    entity.setLastName(lastName);
                } else {
                    System.out.println("The name is not valid. Try again");
                    lastName = Utils.scannerOptionString();
                    Utils.isNameValid(lastName);
                }

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
                        Utils.isCNPValid(userCnp);
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
                String email = Utils.scannerOptionString();
                if(Utils.isEmailValid(email)){
                    entity.setAppEmail(email);
                }else {
                    while (Utils.isEmailValid(email) == false){
                        System.out.println("Email is not valid. Try again");
                        email = Utils.scannerOptionString();
                        Utils.isEmailValid(email);
                    }

                }

                break;
            case 6:
                System.out.println("Introduce the new password: ");
                entity.setAppPassword(scanner.next());
        }

    }

    public UsersEntity userRegistration() {
        UsersEntity user = new UsersEntity();
        System.out.println("Insert user first name: ");
        String firstName = Utils.scannerOptionString();
        if (Utils.isNameValid(firstName)) {
            user.setFirstName(firstName);
        } else {
            while (Utils.isNameValid(firstName) == false) {
                System.out.println("The name is not valid. Try again");
                firstName = Utils.scannerOptionString();
                Utils.isNameValid(firstName);
            }

        }

        System.out.println("Insert user last name: ");
        String lastName = Utils.scannerOptionString();
        if (Utils.isNameValid(lastName)) {
            user.setLastName(lastName);
        } else {
            while (Utils.isNameValid(lastName) == false) {
                System.out.println("The name is not valid. Try again");
                lastName = Utils.scannerOptionString();
                Utils.isNameValid(lastName);
            }

        }

        selectUserRankAndAccessLevel(user);
        System.out.println("Insert the login email address: ");
        String email = Utils.scannerOptionString();
        if(Utils.isEmailValid(email)){
            user.setAppEmail(email);
        }else {
            while (Utils.isEmailValid(email) == false){
                System.out.println("Email is not valid. Try again");
                email = Utils.scannerOptionString();
                Utils.isEmailValid(email);
            }

        }

        System.out.println("Insert the password: ");
        user.setAppPassword(Utils.scannerOptionString());

        return user;
    }


    public String selectUserRankAndAccessLevel(UsersEntity user) {
        System.out.println("Insert user rank:\n1.LIEUTENANT\n" +
                "2.CAPTAIN\n" +
                "3.MAJOR\n" +
                "4.COLONEL\n" +
                "5.BRIGADIER\n" +
                "6.GENERAL");
        int option = Utils.scannerOption();
        while (option < 1 || option > 6) {
            System.out.println("Your option is invalid. Try again");
            option = Utils.scannerOption();
        }
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
