package database.prisonsmanagement.services;

import database.prisonsmanagement.Utils;
import database.prisonsmanagement.entities.AppHibernate;

import java.util.Scanner;

public class UsersServices extends AppHibernate {

    public void insertUser(database.prisonsmanagement.entities.UsersEntity object) {
        object = object.userRegistration();
        String userCnp = Utils.scannerOptionString();
        Utils.setCnp(object,userCnp);
        insert(object);
    }

    public void updateUser(database.prisonsmanagement.entities.UsersEntity user, String cnp){
        user = findUsersByCnp(cnp);
        selectForUpdateUser(user);
        update(user, cnp);
    }

    public void selectForUpdateUser(Object entity) {
        System.out.println("What element would you like to be updated?\n1.First name\n2.Last name\n3.CNP\n4.User Rank\n5.Email address\n6.Password");
        int option = Utils.scannerOption();
        Scanner scanner = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("Introduce the new first name: ");
                ((database.prisonsmanagement.entities.UsersEntity) entity).setFirstName(scanner.nextLine());
                break;
            case 2:
                System.out.println("Introduce the new last name: ");
                ((database.prisonsmanagement.entities.UsersEntity) entity).setLastName(scanner.nextLine());
                break;
            case 3:
                System.out.println("Introduce the new cnp: ");
                String userCnp = Utils.scannerOptionString();
                if (Utils.isCNPValid(userCnp)) {
                    ((database.prisonsmanagement.entities.UsersEntity) entity).setCnp(userCnp);
                } else {

                    while (Utils.isCNPValid(userCnp) == false) {
                        System.out.println("CNP is not valid. Try again");
                        userCnp = Utils.scannerOptionString();
                    }
                    ((database.prisonsmanagement.entities.UsersEntity) entity).setCnp(userCnp);
                }
                break;
            case 4:
                System.out.println("Introduce the new rank: ");
                ((database.prisonsmanagement.entities.UsersEntity) entity).setUserRank(((database.prisonsmanagement.entities.UsersEntity) entity).selectUserRankAndAccessLevel(entity));
                break;
            case 5:
                System.out.println("Introduce the new email address: ");
                ((database.prisonsmanagement.entities.UsersEntity) entity).setAppEmail(scanner.next());
                break;
            case 6:
                System.out.println("Introduce the new password: ");
                ((database.prisonsmanagement.entities.UsersEntity) entity).setAppPassword(scanner.next());
        }

    }


}
