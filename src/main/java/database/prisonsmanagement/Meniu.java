//package database.prisonsmanagement;
//
//import org.hibernate.Session;
//import org.hibernate.query.Query;
//
//import java.util.List;
//
//public class Meniu {
//
//    private UsersEntity user;
//    private PrisonsEntity prison;
//    private InmatesEntity inmate;
//    private AppHibernate hibernate = new AppHibernate();
//
//    public Meniu(UsersEntity user, PrisonsEntity prison, InmatesEntity inmate) {
//        this.user = user;
//        this.prison = prison;
//        this.inmate = inmate;
//    }
//
//    public Meniu() {
//    }
//
//    public void meniu(UsersEntity user, Integer prisonId){
//        InmatesEntity inmate = null;
//        PrisonsEntity prison = null;
//        if(user.getAccessLevel() > 3){
//            System.out.println("Welcome to the Prisons Management Application");
//            System.out.println("Select the action from the below meniu:\n1.Insert inmate\n2.Update inmate\n3.Delete inmate\n4.Insert prison\n5.Update prison\n6.Delete prison");
//            int option = Utils.scannerOption();
//            while (option != 0){
//                switch (option){
//                    case 1:
//                    hibernate.insert(inmate.inmateRegistration(prisonId));
//                    break;
//                    case 2: hibernate.update(inmate);
//                    break;
//                    case 3: hibernate.delete(inmate);
//                    break;
//                    case 4: hibernate.insert(prison);
//                    break;
//                    case 5:hibernate.update(prison);
//                    break;
//                    case 6: hibernate.delete(prison);
//                    break;
//                }
//                option = Utils.scannerOption();
//            }
//        }else {
//            System.out.println("Welcome to the Prisons Management Application");
//            System.out.println("Select the action from the below meniu:\n1.See all the application users\n2.See all the registered inmates\3.See all the registered prisons");
//            int option = Utils.scannerOption();
//            while (option != 0){
//                switch (option){
//                    case 1:
//                        user.seeAllUsers();
//                        break;
//                    case 2: user.seeAllInmates();
//                        break;
//                    case 3: user.seeAllPrisons();
//                        break;
//                }
//                option = Utils.scannerOption();
//            }
//
//        }
//
//    }
//
//    public void registrationMeniu(UsersEntity user){
//        try {
//            Session session = hibernate.getSessionFactory().openSession();
//            Query query = session.createQuery("FROM UsersEntity");
//            List<UsersEntity> userList = query.getResultList();
//            if(userList.contains(user)){
//                System.out.println("The record already exist");
//            }else{
//               hibernate.insert(user);
//            }
//
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//    }
//}
