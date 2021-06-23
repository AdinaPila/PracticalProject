package database.prisonsmanagement.entitys;

import database.prisonsmanagement.Utils;
import database.prisonsmanagement.entitys.InmatesEntity;
import database.prisonsmanagement.entitys.PrisonsEntity;
import database.prisonsmanagement.entitys.UsersEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

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

    public void setCnp(Object object) {
        String cnpInmate = Utils.scannerOptionString();
        if (Utils.isCNPValid(cnpInmate)) {
            ((InmatesEntity) object).setCnpInmate(Utils.scannerOptionString());
        } else {
            while (Utils.isCNPValid(cnpInmate) == false) {
                System.out.println("CNP is not valid. Try again");
                cnpInmate = Utils.scannerOptionString();
            }
        }
    }

    public void insert(Object object) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            if (object instanceof InmatesEntity) {
                System.out.println("Insert prisonId: ");
                int prisonId = Utils.scannerOption();
                object = ((InmatesEntity) object).inmateRegistration(prisonId);
                setCnp(object);
                ((InmatesEntity) object).setCnpInmate(Utils.scannerOptionString());
                session.save(object);
            } else if (object instanceof UsersEntity) {
                object = ((UsersEntity) object).userRegistration();
                String userCnp = Utils.scannerOptionString();
                setCnp(object);
                session.save(object);
            } else if (object instanceof PrisonsEntity) {
                object = ((PrisonsEntity) object).prisonRegistration();
                session.save(object);
            }
            transaction.commit();
            session.close();
        } catch (
                Exception e) {
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
                ((InmatesEntity) object).selectForUpateInmate((InmatesEntity) object);
                session.update(object);
            } else if (object instanceof UsersEntity) {
                object = session.find(UsersEntity.class, id);
                ((UsersEntity) object).selectForUpdateUser((UsersEntity) object);
                session.update(object);
            } else if (object instanceof PrisonsEntity) {
                object = session.find(PrisonsEntity.class, Integer.parseInt(id));
                ((PrisonsEntity) object).selectForUpatePrison((PrisonsEntity) object);
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

    public void registrationMeniu(UsersEntity user) {
        try {
            Session session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM UsersEntity");
            List<UsersEntity> userList = query.getResultList();
            if (userList.contains(user)) {
                System.out.println("The record already exist");
            } else {
                insert(user);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void list(List<Object> object) {
        if (object instanceof UsersEntity) {
            for (UsersEntity item : seeAllUsers()) {
                System.out.println(item.getFirstName() +
                        "\n" + item.getLastName() +
                        "\n" + item.getCnp() +
                        "\n" + item.getUserRank() +
                        "\n" + item.getAccessLevel() +
                        "\n" + item.getAppEmail() +
                        "\n" + item.getAppPassword());
            }
        } else if (object instanceof InmatesEntity) {
            for (InmatesEntity item : seeAllInmates()) {
                System.out.println(item.getFirstNamePrison() +
                        "\n" + item.getLastNamePrison() +
                        "\n" + item.getCnpInmate() +
                        "\n" + item.getPrisonsEntity().getPrisonId() +
                        "\n" + item.getCheckInPrison() +
                        "\n" + item.getCheckOutPrison()
                );
            }
        } else if (object instanceof PrisonsEntity) {
            for (PrisonsEntity item : seeAllPrisons()) {
                System.out.println(item.getPrisonId() +
                        "\n" + item.getPrisonName() +
                        "\n" + item.getSecurityLevel() +
                        "\n" + item.getTotalCapacity());
            }
        }
    }

}
