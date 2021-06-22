package database.prisonsmanagement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class AppHibernate {


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

        meniu.selectRegistrationVsLogin();

        //meniu.meniu(user, "1980507460028");



    }

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
            if (object instanceof InmatesEntity) {
                System.out.println("Insert prisonId: ");
                int prisonId = Utils.scannerOption();
                object = ((InmatesEntity) object).inmateRegistration(prisonId);
                ((InmatesEntity) object).setCnpInmate(Utils.scannerOptionString());
                session.save(object);
            } else if (object instanceof UsersEntity) {
                object = ((UsersEntity) object).userRegistration();
                ((UsersEntity) object).setCnp(Utils.scannerOptionString());
                session.save(object);
            } else if (object instanceof PrisonsEntity) {
                object = ((PrisonsEntity) object).prisonRegistration();
                session.save(object);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void update(Object object, String id) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            if (object instanceof InmatesEntity) {
                object = session.find(InmatesEntity.class, id);
                ((InmatesEntity) object).selectForUpate((InmatesEntity) object);
                session.update(object);
            } else if (object instanceof UsersEntity) {
                object = session.find(UsersEntity.class, id);
                ((UsersEntity) object).selectForUpate((UsersEntity) object);
                session.update(object);
            } else if (object instanceof PrisonsEntity) {
                object = session.find(PrisonsEntity.class, Integer.parseInt(id));
                ((PrisonsEntity) object).selectForUpate((PrisonsEntity) object);
                session.update(object);
            }
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
                    System.out.println("Login successfull");
                    isTrue = true;

                } else if (user.getAppEmail().equals(email) && !user.getAppPassword().equals(appPassword)) {
                    System.out.println("Login faild. Incorrect Password");
                } else if (!user.getAppEmail().equals(email) && user.getAppPassword().equals(appPassword)) {
                    System.out.println("Login faild. Incorrect Email");
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

    public List<InmatesEntity> seeAllInmates() {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM InmatesEntity");
            List<InmatesEntity> inmatesList = query.getResultList();
            return inmatesList;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<UsersEntity> seeAllUsers() {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity");
            List<UsersEntity> usersList = query.getResultList();
            return usersList;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void registrationMeniu(UsersEntity user){
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity");
            List<UsersEntity> userList = query.getResultList();
            if(userList.contains(user)){
                System.out.println("The record already exist");
            }else{
                insert(user);
            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

}
