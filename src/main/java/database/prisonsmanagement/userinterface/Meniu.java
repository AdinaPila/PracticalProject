package database.prisonsmanagement.userinterface;

import database.prisonsmanagement.entitys.AppHibernate;
import database.prisonsmanagement.Utils;
import database.prisonsmanagement.entitys.InmatesEntity;
import database.prisonsmanagement.entitys.PrisonsEntity;
import database.prisonsmanagement.entitys.UsersEntity;

public class Meniu extends AppHibernate {


    public void selectRegistrationVsLogin() {
        System.out.println("Welcome to the Prisons Management Application\nWhat action would you like to perform?\n1.Registration\n2.Login");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                registrationMeniu(new UsersEntity());
                break;
            case 2:
                System.out.println("Insert email address and password");
                System.out.println("Email: ");
                String email = Utils.scannerOptionString();
                System.out.println("Password: ");
                String password = Utils.scannerOptionString();
                boolean verification = verifyCredentials(email, password);
                if (verification == true) {
                    meniu(findUsersByEmail(email), findUsersByEmail(email).getCnp());
                } else {
                    System.out.println("Invalid login details. Try again");
                    System.out.println("==================================");
                    selectRegistrationVsLogin();
                }
        }

    }

    public void meniu(Object object, String id) {
        UsersEntity user = findUsersByCnp(id);

        if (user.getAccessLevel() == 1) {

            System.out.println("Select the action from the below meniu:\n1.Insert inmate\n2.Update inmate\n3.Delete inmate\n4.Insert prison\n5.Update prison\n6.Delete prison");
            int option = Utils.scannerOption();
            while (option != 0) {
                switch (option) {
                    case 1:
                        insert(new InmatesEntity());
                        break;
                    case 2:
                        System.out.println("Insert CNP of the inmate that you want to be updated: ");
                        String cnpForUpdate = Utils.scannerOptionString();
                        InmatesEntity inmate = findInmateByCnp(cnpForUpdate);
                        update(inmate, cnpForUpdate);
                        break;
                    case 3:
                        System.out.println("Insert CNP of the inmate that you want to be deleted: ");
                        String cnpForDelete = Utils.scannerOptionString();
                        InmatesEntity inmateForDelete = findInmateByCnp(cnpForDelete);
                        delete(inmateForDelete);
                        break;
                    case 4:
                        insert(new PrisonsEntity());
                        break;
                    case 5:
                        System.out.println("Insert the ID of the prison that you want to be updated: ");
                        String idForUpdate = Utils.scannerOptionString();
                        PrisonsEntity prison = findById(Integer.parseInt(idForUpdate));
                        update(prison, id);
                        break;
                    case 6:
                        System.out.println("Insert the ID of the prison that you want to be deleted: ");
                        String idForDelete = Utils.scannerOptionString();
                        PrisonsEntity prisonForDelete = findById(Integer.parseInt(idForDelete));
                        update(prisonForDelete, idForDelete);
                        delete(prisonForDelete);
                        break;
                }
                System.out.println("Return to the principal meniu: ");
                meniu(object, id);
                option = Utils.scannerOption();
            }
        } else {
            System.out.println("Welcome to the Prisons Management Application");
            System.out.println("Select the action from the below meniu:\n1.See all the application users\n2.See all the registered inmates\3.See all the registered prisons");
            int option = Utils.scannerOption();
            while (option != 0) {
                switch (option) {
                    case 1:
                        for (UsersEntity item:seeAllUsers()) {
                            System.out.println(item);
                        }


                        break;
                    case 2:
                        for (InmatesEntity item:seeAllInmates()) {
                            System.out.println(item);
                        }

                        break;
                    case 3:
                        for (PrisonsEntity item:seeAllPrisons()) {
                            System.out.println(item);
                        }

                        break;
                }
                System.out.println("Return to the principal meniu: ");
                meniu(object, id);
                option = Utils.scannerOption();
            }

        }

    }


}
