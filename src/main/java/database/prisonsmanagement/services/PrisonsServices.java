package com.sda.alina.exercises.prisonsmanagement.services;

import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.PrisonsEntity;
import database.prisonsmanagement.utils.Utils;

public class PrisonsServices extends AppHibernate {

    public void insertPrison(PrisonsEntity prison){
        prison = prisonRegistration();
        insert(prison);
    }

    public void updatePrison(PrisonsEntity prison, String id){
        prison = findById(Integer.parseInt(id));
        selectForUpatePrison(prison);
        update(prison,id);
    }

    public void selectForUpatePrison(PrisonsEntity entity) {
        System.out.println("What element would you like to be updated?\n1.Prison Name\n2.Security Level\n3.Total Capacity");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                System.out.println("Insert the new name: ");
                entity.setPrisonName(Utils.scannerOptionString());
                break;
            case 2:
                System.out.println("Insert the new security level: ");
                entity.setSecurityLevel(Utils.scannerOption());
                break;
            case 3:
                System.out.println("Insert the new capacity: ");
                entity.setTotalCapacity(Utils.scannerOption());
                break;

        }

    }

    public PrisonsEntity prisonRegistration() {
        PrisonsEntity prison = new PrisonsEntity();
        System.out.println("Insert prison name: ");
        prison.setPrisonName(Utils.scannerOptionString());
        System.out.println("Insert security level: ");
        prison.setSecurityLevel(Utils.scannerOption());
        System.out.println("Total capacity: ");
        prison.setTotalCapacity(Utils.scannerOption());

        return prison;
    }
}
