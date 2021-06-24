package database.prisonsmanagement;

import database.prisonsmanagement.entities.AppHibernate;
import database.prisonsmanagement.entities.InmatesEntity;
import database.prisonsmanagement.entities.PrisonsEntity;
import database.prisonsmanagement.entities.UsersEntity;
import database.prisonsmanagement.services.PrisonsServices;
import database.prisonsmanagement.services.UsersServices;
import database.prisonsmanagement.userinterface.Meniu;

public class Main {
    public static void main(String[] args) {
        AppHibernate hibernate = new AppHibernate();
        UsersEntity user = new UsersEntity();

        PrisonsEntity prison = new PrisonsEntity();
        PrisonsEntity prison1 = new PrisonsEntity();
        PrisonsEntity prison2 = new PrisonsEntity();

        InmatesEntity inmate = new InmatesEntity();
        InmatesEntity inmate2 = new InmatesEntity();
        // hibernate.update(inmate2,"1730506460078");

        // hibernate.update(prison1, "1");
        Meniu meniu = new Meniu();

     //   inmate.updateInmate(inmate,"1897695254522");
     //   prison.updatePrison(prison, "2");

        UsersServices user1 = new UsersServices();
//        user1.updateUser(user, "1980507460028");
        PrisonsServices prisonServices = new PrisonsServices();
      //  prisonServices.insertPrison(prison);
       // inmate.insertInmate(inmate);
       // user.insertUser(user);
       // prison.insertPrison(prison);
       // meniu.selectRegistrationVsLogin();

        //meniu.meniu(user, "1980507460028");
        prisonServices.updatePrison(prison,"2");



    }

}
