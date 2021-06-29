package database.prisonsmanagement.services;

import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.InmatesEntity;
import database.prisonsmanagement.entities.PrisonsEntity;
import database.prisonsmanagement.utils.Utils;
import org.hibernate.Session;

import java.time.LocalDate;

public class InmatesServices extends AppHibernate {

    public void insertInmate(InmatesEntity object) {
        System.out.println("Insert prisonId: ");
        int prisonId = Utils.scannerOption();
        object = inmateRegistration(prisonId);
        String cnp = Utils.scannerOptionString();
        Utils.setCnp(object, cnp);
        object.setCnpInmate(cnp);
        insert(object);

    }

    public void updateInmate(InmatesEntity inmate, String cnp) {
        inmate = findInmateByCnp(cnp);
        selectForUpdateInmate(inmate);
        update(inmate, cnp);
    }

    void selectForUpdateInmate(InmatesEntity entity) {
        System.out.println("What element would you like to be updated?\n1.First name\n2.Last name\n3.CNP\n4.CheckIn Date\n5.Check Out Date");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                System.out.println("Introduce the new first name: ");
                String firstName = Utils.scannerOptionString();
                if (Utils.isNameValid(firstName)) {
                    entity.setFirstNamePrison(firstName);
                } else {
                    while (Utils.isNameValid(firstName) == false) {
                        System.out.println("The name is not valid. Try again");
                        firstName = Utils.scannerOptionString();
                    }
                }

                break;
            case 2:
                System.out.println("Introduce the new last name: ");
                String lastName = Utils.scannerOptionString();
                if (Utils.isNameValid(lastName)) {
                    entity.setFirstNamePrison(lastName);
                } else {
                    while (Utils.isNameValid(lastName) == false) {
                        System.out.println("The name is not valid. Try again");
                        lastName = Utils.scannerOptionString();
                    }
                }
                entity.setLastNamePrison(Utils.scannerOptionString());
                break;
            case 3:
                System.out.println("Introduce the new cnp: ");
                String inmateCnp = Utils.scannerOptionString();
                if (Utils.isCNPValid(inmateCnp)) {
                    entity.setCnpInmate(inmateCnp);
                } else {

                    while (Utils.isCNPValid(inmateCnp) == false) {
                        System.out.println("CNP is not valid. Try again");
                        inmateCnp = Utils.scannerOptionString();
                    }
                    entity.setCnpInmate(Utils.scannerOptionString());
                }

                break;
            case 4:
                System.out.println("Insert date in format yyyy mm dd");
                entity.setCheckInPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
                break;
            case 5:
                System.out.println("Insert date in format yyyy mm dd");
                entity.setCheckOutPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
                break;
            case 6:
                System.out.println("Insert prisonId: ");
                Integer prisonId = Utils.scannerOption();
                AppHibernate hibernate = new AppHibernate();
                hibernate.findById(prisonId);
        }
    }

    public InmatesEntity inmateRegistration(Integer prisonId) {
        InmatesEntity inmate = new InmatesEntity();
        database.prisonsmanagement.entities.PrisonsEntity prisonsEntity = null;
        AppHibernate hibernate = new AppHibernate();
        System.out.println("Insert inmate first name: ");
        String firstName = Utils.scannerOptionString();
        if (Utils.isNameValid(firstName)) {
            inmate.setFirstNamePrison(firstName);
        } else {
            while (Utils.isNameValid(firstName) == false) {
                System.out.println("The name is not valid. Try again");
                firstName = Utils.scannerOptionString();
            }
        }
        System.out.println("Insert inmate last name: ");
        String lastName = Utils.scannerOptionString();
        if (Utils.isNameValid(lastName)) {
            inmate.setLastNamePrison(lastName);
        } else {
            while (Utils.isNameValid(lastName) == false) {
                System.out.println("The name is not valid. Try again");
                lastName = Utils.scannerOptionString();
            }
        }
        System.out.println("Insert checkIn date - use format yyyy mm dd: ");
        int year = Utils.scannerOption();
        while ((year < 1900 || year > 2050) == true) {
            System.out.println("Year is not valid");
            year = Utils.scannerOption();
        }
        int month = Utils.scannerOption();
        while ((month < 1 || month > 12) == true) {
            System.out.println("Month is not valid");
            month = Utils.scannerOption();
        }
        int day = Utils.scannerOption();
        while ((day < 1 || day > 31) == true) {
            System.out.println("Day is not valid");
            day = Utils.scannerOption();
        }
        LocalDate checkInDate = LocalDate.of(year, month, day);
        inmate.setCheckInPrison(checkInDate);
        System.out.println("Insert checkOut date - use format yyyy mm dd:  ");
        int year1 = Utils.scannerOption();
        while ((year1 < 1900 || year1 > 2050 || year1 < year) == true) {
            System.out.println("Year is not valid");
            year1 = Utils.scannerOption();
        }
        int month1 = Utils.scannerOption();
        while ((month1 < 1 || month1 > 12) == true) {
            System.out.println("Month is not valid");
            month1 = Utils.scannerOption();
        }
        int day1 = Utils.scannerOption();
        while ((day1 < 1 || day1 > 31) == true) {
            System.out.println("Day is not valid");
            day1 = Utils.scannerOption();
        }

        LocalDate checkOutDate = LocalDate.of(year1,month1,day1);
        inmate.setCheckOutPrison(checkOutDate);
        try {
            Session session = hibernate.getSessionFactory().openSession();
            prisonsEntity = session.find(PrisonsEntity.class, prisonId);
        } catch (Exception e) {
            System.out.println(e);
        }
        inmate.setPrisonsEntity(prisonsEntity);
        System.out.println("Insert inmate CNP: ");

        return inmate;
    }
}
