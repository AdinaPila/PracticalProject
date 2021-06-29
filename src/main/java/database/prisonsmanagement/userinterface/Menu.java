package database.prisonsmanagement.userinterface;

import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.InmatesEntity;
import database.prisonsmanagement.entities.PrisonsEntity;
import database.prisonsmanagement.entities.UsersEntity;
import database.prisonsmanagement.services.InmatesServices;
import database.prisonsmanagement.services.PrisonsServices;
import database.prisonsmanagement.services.UsersServices;
import database.prisonsmanagement.singleton.WriteInFile;
import database.prisonsmanagement.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Menu extends AppHibernate {
    private WriteInFile log = WriteInFile.getInstance();
    private UsersServices usersServices = new UsersServices();

    public void selectRegistrationVsLogin() {
        System.out.println("Welcome to the Prisons Management Application\nWhat action would you like to perform?\n1.Registration\n2.Login");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                UsersEntity usersEntity = usersServices.userRegistration();
                registrationMenu(usersEntity);
                String name = usersEntity.getFirstName() + " " + usersEntity.getLastName() + " - performed registration - " + LocalDateTime.now();
                log.writeLogs(name);
                break;
            case 2:
                System.out.println("Insert email address and password");
                System.out.println("Email: ");
                String email = Utils.scannerOptionString();
                System.out.println("Password: ");
                String password = Utils.scannerOptionString();
                boolean verification = verifyCredentials(email, password);
                if (verification == true) {
                    menu(findUsersByEmail(email), findUsersByEmail(email).getCnp());
                    log.writeLogs((findUsersByEmail(email).getFirstName() + " " + findUsersByEmail(email).getLastName() + " - login successful - " + LocalDateTime.now()));
                } else {
                    System.out.println("Invalid login details. Try again");
                    System.out.println("========================================");
                    selectRegistrationVsLogin();
                }
        }

    }

    public void menu(UsersEntity object, String id) {
        UsersEntity user = findUsersByCnp(id);
        InmatesServices inmate = new InmatesServices();
        PrisonsServices prisonForInsert = new PrisonsServices();

        if (user.getAccessLevel() == 1) {

            System.out.println(String.format("\nSelect the action from the below menu:\n\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s",
                    "1.Insert inmate","8.See the inmates that have the checkout date until the given date", "2.Update inmate",
                    "9.See all the inmates with the checkout date between 2 given dates", "3.Delete inmate","10.See all prisons with vacancy",
                    "4.Insert prison","11.See all the registered prisons", "5.Update prison","12.See the average of occupation of all prisons",
                    "6.Delete prison","13.See the average occupation of each prison", "7.See all the application users", "0.Exit"));
            int option = Utils.scannerOption();
            while (option != 0) {
                switch (option) {
                    case 1:
                        inmate.insertInmate(new InmatesEntity());
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - Inserted inmate - " + LocalDateTime.now());
                        break;
                    case 2:
                        System.out.println("Insert CNP of the inmate that you want to be updated: ");
                        String cnpForUpdate = Utils.scannerOptionString();
                        InmatesEntity inmateUpdate = findInmateByCnp(cnpForUpdate);
                        update(inmateUpdate, cnpForUpdate);
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - Update inmate - " + LocalDateTime.now());

                        break;
                    case 3:
                        System.out.println("Insert CNP of the inmate that you want to be deleted: ");
                        String cnpForDelete = Utils.scannerOptionString();
                        InmatesEntity inmateForDelete = findInmateByCnp(cnpForDelete);
                        delete(inmateForDelete);
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - Delete inmate - " + LocalDateTime.now());

                        break;
                    case 4:
                        prisonForInsert.insertPrison(new PrisonsEntity());
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - Inserted a new prison - " + LocalDateTime.now());

                        break;
                    case 5:
                        System.out.println("Insert the ID of the prison that you want to be updated: ");
                        String idForUpdate = Utils.scannerOptionString();
                        PrisonsEntity prison = findById(Integer.parseInt(idForUpdate));
                        update(prison, id);
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - Update a prison - " + LocalDateTime.now());

                        break;
                    case 6:
                        System.out.println("Insert the ID of the prison that you want to be deleted: ");
                        String idForDelete = Utils.scannerOptionString();
                        PrisonsEntity prisonForDelete = findById(Integer.parseInt(idForDelete));
                        update(prisonForDelete, idForDelete);
                        delete(prisonForDelete);
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - Delete a prison - " + LocalDateTime.now());

                        break;

                    case 7:
                        for (UsersEntity item : seeAllUsers()) {
                            System.out.println(item);
                        }
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - See all the application users - " + LocalDateTime.now());

                        break;
                    case 8:
                        System.out.println("Insert checkOutDate");
                        LocalDate date = LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption());
                        for (InmatesEntity item : seeAllInmates(date)) {
                            System.out.println(item.getFirstNamePrison() + " " + item.getLastNamePrison() + " - " + item.getCheckOutPrison());
                        }
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - See the inmates that have the checkout date until the given date - " + LocalDateTime.now());

                        break;
                    case 9:
                        System.out.println("Insert the start date: ");
                        LocalDate date1 = LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption());
                        System.out.println("Insert the end date: ");
                        LocalDate date2 = LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption());
                        for (InmatesEntity item : seeAllInmatesBetween(date1, date2)) {
                            System.out.println(item.getFirstNamePrison() + " " + item.getLastNamePrison() + " - " + "checkout date: " + item.getCheckOutPrison());
                        }
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - See all the inmates with the checkout date between 2 given dates - " + LocalDateTime.now());

                        break;

                    case 10:
                        for (PrisonsEntity item : seeAllPrisonsWithVacancy()) {
                            int vacancy = item.getTotalCapacity() - item.getInmatesList().size();
                            System.out.println(item.getPrisonName() + " - " + vacancy);
                        }
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - See all prisons with vacancy - " + LocalDateTime.now());

                        break;

                    case 11:
                        for (PrisonsEntity item : seeAllPrisons()) {
                            System.out.println(item.getPrisonId() + " " +item.getPrisonName() + " " + item.getSecurityLevel() + " " + item.getTotalCapacity());
                        }
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - See all the registered prisons - " + LocalDateTime.now());

                        break;
                    case 12:
                        System.out.println("The occupation average is " + seeTheAverageOfOccupation() + " percent");
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - See the average of occupation of all prisons - " + LocalDateTime.now());

                        break;
                    case 13:
                        inmate.seeTheAverageOfOccupationForEachPrison();
                        log.writeLogs(object.getFirstName() + " " + object.getLastName() + " - See the average occupation of each prison - " + LocalDateTime.now());

                        break;

                }
                System.out.println("Return to the principal menu: ");
                System.out.println(String.format("\nSelect the action from the below menu:\n\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s\n%-35s %s",
                        "1.Insert inmate","8.See the inmates that have the checkout date until the given date", "2.Update inmate",
                        "9.See all the inmates with the checkout date between 2 given dates", "3.Delete inmate","10.See all prisons with vacancy",
                        "4.Insert prison","11.See all the registered prisons", "5.Update prison","12.See the average of occupation of all prisons",
                        "6.Delete prison","13.See the average occupation of each prison", "7.See all the application users", "0.Exit"));
                option = Utils.scannerOption();
            }
        } else {

            System.out.println("Welcome to the Prisons Management Application");
            System.out.println("Select the action from the below menu:\n1.See all the application users\n2.See the inmates that have the checkout date until the given date" +
                    "\n3.See all the inmates with the checkout date between 2 given dates\n4.See all prisons with vacancy\n5.See all the registered prisons\n6.See the average" +
                    " of occupation of all prisons\n7.See the average occupation of each prison");
            int option = Utils.scannerOption();
            while (option != 0) {
                switch (option) {
                    case 1:
                        for (UsersEntity item : seeAllUsers()) {
                            System.out.println(item);
                        }
                        log.writeLogs(user.getFirstName() + " " + user.getLastName() + " - See all the application users - " + LocalDateTime.now());

                        break;
                    case 2:
                        System.out.println("Insert checkOutDate");
                        LocalDate date = LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption());
                        for (InmatesEntity item : seeAllInmates(date)) {
                            System.out.println(item);
                        }
                        log.writeLogs(user.getFirstName() + " " + user.getLastName() + " - See the inmates that have the checkout date until the given date - " + LocalDateTime.now());

                        break;
                    case 3:
                        System.out.println("Insert the start date: ");
                        LocalDate date1 = LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption());
                        System.out.println("Insert the end date: ");
                        LocalDate date2 = LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption());
                        for (InmatesEntity item : seeAllInmatesBetween(date1, date2)) {
                            System.out.println(item.getFirstNamePrison() + " " + item.getLastNamePrison() + " - " + "checkout date: " + item.getCheckOutPrison());
                        }
                        log.writeLogs(user.getFirstName() + " " + user.getLastName() + " - See all the inmates with the checkout date between 2 given dates - " + LocalDateTime.now());


                        break;

                    case 4:
                        for (PrisonsEntity item : seeAllPrisonsWithVacancy()) {
                            int vacancy = item.getTotalCapacity() - item.getInmatesList().size();
                            System.out.println(item.getPrisonName() + " - " + vacancy);
                        }
                        log.writeLogs(user.getFirstName() + " " + user.getLastName() + " - See all prisons with vacancy - " + LocalDateTime.now());

                        break;

                    case 5:
                        for (PrisonsEntity item : seeAllPrisons()) {
                            System.out.println(item);
                        }
                        log.writeLogs(user.getFirstName() + " " + user.getLastName() + " - See all the registered prisons - " + LocalDateTime.now());

                        break;
                    case 6:
                        System.out.println("The occupation average is " + seeTheAverageOfOccupation() + " percent");
                        log.writeLogs(user.getFirstName() + " " + user.getLastName() + " - See the average of occupation of all prisons - " + LocalDateTime.now());

                        break;
                    case 7:
                        inmate.seeTheAverageOfOccupationForEachPrison();
                        log.writeLogs(user.getFirstName() + " " + user.getLastName() + " - See the average occupation of each prison - " + LocalDateTime.now());

                        break;
                }
                System.out.println("Return to the principal menu: ");
                menu(object, id);
                option = Utils.scannerOption();
            }

        }

    }


}
