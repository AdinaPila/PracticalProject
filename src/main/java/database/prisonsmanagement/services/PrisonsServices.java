package database.prisonsmanagement.services;

import database.prisonsmanagement.Utils;
import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.PrisonsEntity;

public class PrisonsServices extends AppHibernate {

    public void insertPrison(PrisonsEntity prison){
        prison = prison.prisonRegistration();
        insert(prison);
    }

    public void updatePrison(PrisonsEntity prison, String id){
        prison = findById(Integer.parseInt(id));
        selectForUpatePrison(prison);
        update(prison,id);
    }

    public void selectForUpatePrison(Object entity) {
        System.out.println("What element would you like to be updated?\n1.Prison Name\n2.Security Level\n3.Total Capacity");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                System.out.println("Insert the new name: ");
                ((PrisonsEntity) entity).setPrisonName(Utils.scannerOptionString());
                break;
            case 2:
                System.out.println("Insert the new security level: ");
                ((PrisonsEntity) entity).setSecurityLevel(Utils.scannerOption());
                break;
            case 3:
                System.out.println("Insert the new capacity: ");
                ((PrisonsEntity) entity).setTotalCapacity(Utils.scannerOption());
                break;

        }

    }
}
