package database.prisonsmanagement.services;

import database.prisonsmanagement.utils.Utils;
import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.InmatesEntity;
import database.prisonsmanagement.entities.PrisonsEntity;
import org.hibernate.Session;

import java.time.LocalDate;

public class InmatesServices extends AppHibernate {

    public void insertInmate(InmatesEntity object) {
        System.out.println("Insert prisonId: ");
        int prisonId = Utils.scannerOption();
        object = inmateRegistration(prisonId);
        String cnp = Utils.scannerOptionString();
        Utils.setCnp(object,cnp);
        object.setCnpInmate(cnp);
        insert(object);

    }

    public void updateInmate(InmatesEntity inmate, String cnp){
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
                if(Utils.isNameValid(firstName)){
                    entity.setFirstNamePrison(firstName);
                }else{
                    while (Utils.isNameValid(firstName) == false){
                        System.out.println("The name is not valid. Try again");
                        firstName = Utils.scannerOptionString();
                    }
                }

                break;
            case 2:
                System.out.println("Introduce the new last name: ");
                String lastName = Utils.scannerOptionString();
                if(Utils.isNameValid(lastName)){
                    entity.setFirstNamePrison(lastName);
                }else{
                    while (Utils.isNameValid(lastName) == false){
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
        if(Utils.isNameValid(firstName)){
            inmate.setFirstNamePrison(firstName);
        }else{
            while (Utils.isNameValid(firstName) == false){
                System.out.println("The name is not valid. Try again");
                firstName = Utils.scannerOptionString();
            }
        }
        System.out.println("Insert inmate last name: ");
        String lastName = Utils.scannerOptionString();
        if(Utils.isNameValid(lastName)){
            inmate.setLastNamePrison(lastName);
        }else{
            while (Utils.isNameValid(lastName) == false){
                System.out.println("The name is not valid. Try again");
                lastName = Utils.scannerOptionString();
            }
        }
        System.out.println("Insert checkIn date - use format yyyy mm dd: ");
        inmate.setCheckInPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
        System.out.println("Insert checkOut date - use format yyyy mm dd:  ");
        inmate.setCheckOutPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
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
