package database.prisonsmanagement;

import database.prisonsmanagement.entitys.AppHibernate;
import database.prisonsmanagement.entitys.InmatesEntity;
import database.prisonsmanagement.entitys.PrisonsEntity;
import database.prisonsmanagement.entitys.UsersEntity;
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

       // inmate.insertInmate(inmate);
       // user.insertUser(user);
        prison.insertPrison(prison);
       // meniu.selectRegistrationVsLogin();

        //meniu.meniu(user, "1980507460028");



    }

}