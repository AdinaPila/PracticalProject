package database.prisonsmanagement.entities;

import database.prisonsmanagement.utils.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AppHibernate {

    public SessionFactory getSessionFactory() {

        try {
            Properties properties = new Properties();
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/prison_management?serverTimezone=UTC");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "Consulting1#");
            properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "update");

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(InmatesEntity.class);
            configuration.addAnnotatedClass(PrisonsEntity.class);
            configuration.addAnnotatedClass(UsersEntity.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    public void insert(Object object) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(object);

            transaction.commit();
            session.close();
        } catch (
                Exception e) {
            System.out.println(e);
        }

    }


    public void update(Object object, String id) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            session.update(object);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public void delete(Object object) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public boolean verifyCredentials(String email, String appPassword) {
        boolean isTrue = false;
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity WHERE appEmail = :appEmail").setParameter("appEmail", email);
            UsersEntity user = (UsersEntity) query.uniqueResult();
            if (user != null) {
                if (user.getAppEmail().equals(email) && user.getAppPassword().equals(appPassword)) {
                    System.out.println("Login successful");
                    isTrue = true;

                } else if (user.getAppEmail().equals(email) && !user.getAppPassword().equals(appPassword)) {
                    System.out.println("Login failed. Incorrect Password");
                } else if (!user.getAppEmail().equals(email) && user.getAppPassword().equals(appPassword)) {
                    System.out.println("Login failed. Incorrect Email");
                }
            } else {
                System.out.println("The introduced email address does not exist");
            }
            return isTrue;

        } catch (Exception e) {
            System.out.println(e);
        }
        return isTrue;
    }

    public UsersEntity findUsersByCnp(String cnp) {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity WHERE cnp = :cnp").setParameter("cnp", cnp);
            UsersEntity user = (UsersEntity) query.uniqueResult();
            return user;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public UsersEntity findUsersByEmail(String email) {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity WHERE appEmail = :appEmail").setParameter("appEmail", email);
            UsersEntity user = (UsersEntity) query.uniqueResult();
            return user;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public InmatesEntity findInmateByCnp(String cnp) {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM InmatesEntity WHERE cnpInmate = :cnpInmate").setParameter("cnpInmate", cnp);
            InmatesEntity inmate = (InmatesEntity) query.uniqueResult();
            return inmate;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public PrisonsEntity findById(Integer id) {
        PrisonsEntity prison = null;
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM PrisonsEntity WHERE prisonId = :prisonId").setParameter("prisonId", id);
            prison = (PrisonsEntity) query.uniqueResult();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return prison;
    }

    public List<PrisonsEntity> seeAllPrisons() {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM PrisonsEntity");
            List<PrisonsEntity> prisonsList = query.getResultList();
            return prisonsList;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<InmatesEntity> seeAllInmates(LocalDate checkOutDate) {
        try {
            Date date = Date.valueOf(checkOutDate);
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM InmatesEntity WHERE checkOutPrison < '"+date+"'");
            List<InmatesEntity> inmatesList = query.getResultList();
            session.close();
            return inmatesList;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }


    public List<InmatesEntity> seeAllInmatesBetween(LocalDate startDate, LocalDate endDate) {
       List inmatesList = new ArrayList<>();
        try {
            Date date = Date.valueOf(startDate);
            Date date1 = Date.valueOf(endDate);
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM InmatesEntity WHERE checkOutPrison BETWEEN '"+date+"' AND '"+date1+"'");

            inmatesList = query.getResultList();
            if(inmatesList.size() == 0){
                System.out.println("The list is empty");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return inmatesList;
    }

    public List<PrisonsEntity> seeAllPrisonsWithVacancy(){
        List<PrisonsEntity> prisonsWithVacancy = new ArrayList<>();
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM PrisonsEntity");
            List<PrisonsEntity> prisons = query.getResultList();
            for (PrisonsEntity item : prisons) {
                int vacancy = item.getTotalCapacity() - item.getInmatesList().size();
                if(vacancy > 0){
                    prisonsWithVacancy.add(item);
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }

        return prisonsWithVacancy;
    }

    public Float seeTheAverageOfOccupation(){
        float average = 0;
        long totalCapacity = 0;
        int totalInmates = 0;
        List<PrisonsEntity> prisons = seeAllPrisons();
        for (PrisonsEntity item:prisons) {
            totalInmates = totalInmates + item.getInmatesList().size();
        }
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("SELECT sum(totalCapacity) FROM PrisonsEntity");
            totalCapacity = (Long) query.uniqueResult();
            System.out.println("Total capacity " + totalCapacity);
            average = (totalCapacity/totalInmates)/100f;
        }catch (Exception e){
            System.out.println(e);
        }

        return average;
    }

    public void seeTheAverageOfOccupationForEachPrison(){
        float average = 0;
        int totalCapacity = 0;
        int totalInmates = 0;
        List<PrisonsEntity> prisons = new ArrayList<>();
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM PrisonsEntity");
            prisons = query.getResultList();
            System.out.println("\n");
            for (PrisonsEntity prison: prisons) {
                totalCapacity = prison.getTotalCapacity();
                totalInmates = prison.getInmatesList().size();
                average = (totalCapacity/totalInmates)/100f;
                System.out.println(prison.getPrisonName() + " has " + average + " percent occupation");
            }

        }catch (Exception e){
            System.out.println(e);
            System.out.println("EITHER THAT OR THE PRISON IS EMPTY\n");
        }

    }

    public Integer prisonVacancy(Integer prisonId){
        Integer vacancy = 0;
        try {
            //Session session = getSessionFactory().openSession();
            PrisonsEntity prisonsEntity = findById(prisonId);
            System.out.println(prisonsEntity.getPrisonName());
            Integer totalCapacity = prisonsEntity.getTotalCapacity();
            List<InmatesEntity> inmateList = new ArrayList<>();
            inmateList = prisonsEntity.getInmatesList();
            Integer numberOfInmates = inmateList.size();
            vacancy = totalCapacity - numberOfInmates;
          //  session.close();
           // return totalCapacity - numberOfInmates;
        }catch (Exception e) {
            System.out.println(e);
        }
        return vacancy;
    }

    public List<UsersEntity> seeAllUsers() {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity");
            List<UsersEntity> usersList = query.getResultList();
            session.close();
            return usersList;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void registrationMenu(UsersEntity user) {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity");
            List<UsersEntity> userList = query.getResultList();
            if (userList.contains(user)) {
                System.out.println("The record already exists");
            } else {
                System.out.println("Insert user CNP: ");
                String cnp = Utils.scannerOptionString();
                user.setCnp(cnp);
                insert(user);
            }
            session.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

//    public void list(List<Object> object) {
//        if (object instanceof List<UsersEntity>) {
//            for (UsersEntity item : seeAllUsers()) {
//                System.out.println(item.getFirstName() +
//                        "\n" + item.getLastName() +
//                        "\n" + item.getCnp() +
//                        "\n" + item.getUserRank() +
//                        "\n" + item.getAccessLevel() +
//                        "\n" + item.getAppEmail() +
//                        "\n" + item.getAppPassword());
//            }
//        } else if (object instanceof List<InmatesEntity>) {
//            for (InmatesEntity item : seeAllInmates()) {
//                System.out.println(item.getFirstNamePrison() +
//                        "\n" + item.getLastNamePrison() +
//                        "\n" + item.getCnpInmate() +
//                        "\n" + item.getPrisonsEntity().getPrisonId() +
//                        "\n" + item.getCheckInPrison() +
//                        "\n" + item.getCheckOutPrison()
//                );
//            }
//        } else if (object instanceof List<PrisonsEntity>) {
//            for (PrisonsEntity item : seeAllPrisons()) {
//                System.out.println(item.getPrisonId() +
//                        "\n" + item.getPrisonName() +
//                        "\n" + item.getSecurityLevel() +
//                        "\n" + item.getTotalCapacity());
//            }
//        }
//    }

}
