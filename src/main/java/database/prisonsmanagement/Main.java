package database.prisonsmanagement;

import database.prisonsmanagement.entities.UsersEntity;
import database.prisonsmanagement.userinterface.Menu;

public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        menu.selectRegistrationVsLogin();

//        AppHibernate hibernate = new AppHibernate();
           UsersEntity user = new UsersEntity();
//        PrisonsEntity prison = new PrisonsEntity();
//        PrisonsEntity prison1 = new PrisonsEntity();
//        PrisonsEntity prison2 = new PrisonsEntity();
//        InmatesEntity inmate = new InmatesEntity();
//        InmatesEntity inmate2 = new InmatesEntity();
//        InmatesServices inmatesServices = new InmatesServices();
//
//
//
//
//        UsersServices user1 = new UsersServices();
//
//        PrisonsServices prisonServices = new PrisonsServices();


       menu.menu(user,"1894623460073");

    }

}
