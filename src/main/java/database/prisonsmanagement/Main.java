package database.prisonsmanagement;

import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.InmatesEntity;
import database.prisonsmanagement.entities.PrisonsEntity;
import database.prisonsmanagement.entities.UsersEntity;
import database.prisonsmanagement.services.InmatesServices;
import database.prisonsmanagement.services.PrisonsServices;
import database.prisonsmanagement.services.UsersServices;
import database.prisonsmanagement.userinterface.Meniu;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        AppHibernate hibernate = new AppHibernate();
        UsersEntity user = new UsersEntity();

        PrisonsEntity prison = new PrisonsEntity();
        PrisonsEntity prison1 = new PrisonsEntity();
        PrisonsEntity prison2 = new PrisonsEntity();

        InmatesEntity inmate = new InmatesEntity();
        InmatesEntity inmate2 = new InmatesEntity();

        InmatesServices inmatesServices = new InmatesServices();

        Meniu meniu = new Meniu();


        UsersServices user1 = new UsersServices();

        PrisonsServices prisonServices = new PrisonsServices();
        
        meniu.selectRegistrationVsLogin();

    }

}
