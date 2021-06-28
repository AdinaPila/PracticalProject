package database.prisonsmanagement.userinterface;

import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.services.UsersServices;
import database.prisonsmanagement.utils.Utils;
import database.prisonsmanagement.entities.InmatesEntity;
import database.prisonsmanagement.entities.PrisonsEntity;
import database.prisonsmanagement.entities.UsersEntity;
import database.prisonsmanagement.services.InmatesServices;
import database.prisonsmanagement.services.PrisonsServices;
import database.prisonsmanagement.singleton.WriteInFile;

import java.time.LocalDate;

public class Meniu extends AppHibernate {
    private WriteInFile log = WriteInFile.getInstance();
    private UsersServices usersServices = new UsersServices();

    public void selectRegistrationVsLogin() {
        System.out.println("Welcome to the Prisons Management Application\nWhat action would you like to perform?\n1.Registration\n2.Login");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                UsersEntity usersEntity = usersServices.userRegistration();
                registrationMeniu(usersEntity);
                String name = usersEntity.getFirstName() + " "+ usersEntity.getLastName() + " - performed registration";
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
                    meniu(findUsersByEmail(email), findUsersByEmail(email).getCnp());
                    log.writeLogs((findUsersByEmail(email).getFirstName() + " " + findUsersByEmail(email).getLastName() + " - login successfull"));
                } else {
                    System.out.println("Invalid login details. Try again");
                    System.out.println("==================================");
                    selectRegistrationVsLogin();
                }
        }

    }

    public void meniu(UsersEntity object, String id) {
        UsersEntity user = findUsersByCnp(id);
        InmatesServices inmate = new InmatesServices();
        PrisonsServices prisonForInsert = new PrisonsServices();

        if (user.getAccessLevel() == 1) {

            System.out.println("Select the action from the below meniu:\n1.Insert inmate\n2.Update inmate\n3.Delete inmate\n4.Insert prison\n5.Update prison\n6.Delete prison");
            int option = Utils.scannerOption();
            while (option != 0) {
                switch (option) {
                    case 1:
                        inmate.insertInmate(new InmatesEntity());
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - Inserted inmate");
                        break;
                    case 2:
                        System.out.println("Insert CNP of the inmate that you want to be updated: ");
                        String cnpForUpdate = Utils.scannerOptionString();
                        InmatesEntity inmateUpdate = findInmateByCnp(cnpForUpdate);
                        update(inmateUpdate, cnpForUpdate);
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - Update inmate");

                        break;
                    case 3:
                        System.out.println("Insert CNP of the inmate that you want to be deleted: ");
                        String cnpForDelete = Utils.scannerOptionString();
                        InmatesEntity inmateForDelete = findInmateByCnp(cnpForDelete);
                        delete(inmateForDelete);
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - Delete inmate");

                        break;
                    case 4:
                        prisonForInsert.insertPrison(new PrisonsEntity());
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - Inserted a new prison");

                        break;
                    case 5:
                        System.out.println("Insert the ID of the prison that you want to be updated: ");
                        String idForUpdate = Utils.scannerOptionString();
                        PrisonsEntity prison = findById(Integer.parseInt(idForUpdate));
                        update(prison, id);
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - Update a prison");

                        break;
                    case 6:
                        System.out.println("Insert the ID of the prison that you want to be deleted: ");
                        String idForDelete = Utils.scannerOptionString();
                        PrisonsEntity prisonForDelete = findById(Integer.parseInt(idForDelete));
                        update(prisonForDelete, idForDelete);
                        delete(prisonForDelete);
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - Delete a prison");

                        break;
                }
                System.out.println("Return to the principal meniu: ");
                meniu(object, id);
                option = Utils.scannerOption();
            }
        } else {
            System.out.println("Welcome to the Prisons Management Application");
            System.out.println("Select the action from the below meniu:\n1.See all the application users\n2.See the inmates that have the chekout date until the given date\n3.See all the inmates with the checkout date between 2 given dates\n4.See all prisons with vacancy\n5.See all the registered prisons\n6.See the average of ocupation of all prisons\n7.See the average ocupation of each prison");
            int option = Utils.scannerOption();
            while (option != 0) {
                switch (option) {
                    case 1:
                        for (UsersEntity item:seeAllUsers()) {
                            System.out.println(item);
                        }
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - See all the application users");

                        break;
                    case 2:
                        System.out.println("Insert checkOutDate");
                        LocalDate date = LocalDate.of(Utils.scannerOption(),Utils.scannerOption(),Utils.scannerOption());
                        for (InmatesEntity item:seeAllInmates(date)) {
                            System.out.println(item);
                        }
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - See the inmates that have the chekout date until the given date");

                        break;
                    case 3:
                        System.out.println("Insert the start date: ");
                        LocalDate date1 = LocalDate.of(Utils.scannerOption(),Utils.scannerOption(),Utils.scannerOption());
                        System.out.println("Insert the end date: ");
                        LocalDate date2 = LocalDate.of(Utils.scannerOption(),Utils.scannerOption(),Utils.scannerOption());
                        for (InmatesEntity item : seeAllInmatesBetween(date1,date2)){
                            System.out.println(item.getFirstNamePrison() + " "+ item.getLastNamePrison() + " - "+"checkout date: "+ item.getCheckOutPrison());
                        }
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - See all the inmates with the checkout date between 2 given dates");


                        break;

                    case 4:
                        for (PrisonsEntity item :seeAllPrisonsWithVacancy()) {
                            int vacancy = item.getTotalCapacity() - item.getInmatesList().size();
                            System.out.println(item.getPrisonName() + " - "+vacancy);
                        }
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - See all prisons with vacancy");

                        break;

                    case 5:
                        for (PrisonsEntity item:seeAllPrisons()) {
                            System.out.println(item);
                        }
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - See all the registered prisons");

                        break;
                    case 6:
                        System.out.println("The ocupation average is " + seeTheAverageOfOcupation() + " percent");
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - See the average of ocupation of all prisons");

                        break;
                    case 7:
                       inmate.seeTheAverageOfOcupationForEachPrison();
                        log.writeLogs(object.getFirstName() + " "+object.getLastName() + " - See the average ocupation of each prison");

                        break;
                }
                System.out.println("Return to the principal meniu: ");
                meniu(object, id);
                option = Utils.scannerOption();
            }

        }

    }


}
