package database.prisonsmanagement.entitys;

import database.prisonsmanagement.Utils;
import org.hibernate.Session;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inmates")
public class InmatesEntity {

    private String firstNamePrison;

    private String lastNamePrison;


    @Id
    @Column(name = "cnpInmate", unique = true, nullable = false, columnDefinition = "VARCHAR(13)")
    private String cnpInmate;

    private LocalDate checkInPrison;

    private LocalDate checkOutPrison;

    @ManyToOne
    @JoinColumn(name = "prisonId")
    private PrisonsEntity prisonsEntity;


    public String getFirstNamePrison() {
        return firstNamePrison;
    }

    public void setFirstNamePrison(String firstNamePrison) {
        this.firstNamePrison = firstNamePrison;
    }

    public String getLastNamePrison() {
        return lastNamePrison;
    }

    public void setLastNamePrison(String lastNamePrison) {
        this.lastNamePrison = lastNamePrison;
    }

    public String getCnpInmate() {
        return cnpInmate;
    }

    public void setCnpInmate(String cnpInmate) {
        this.cnpInmate = cnpInmate;
    }

    public LocalDate getCheckInPrison() {
        return checkInPrison;
    }

    public void setCheckInPrison(LocalDate checkInPrison) {
        this.checkInPrison = checkInPrison;
    }

    public LocalDate getCheckOutPrison() {
        return checkOutPrison;
    }

    public void setCheckOutPrison(LocalDate checkOutPrison) {
        this.checkOutPrison = checkOutPrison;
    }

    public PrisonsEntity getPrisonsEntity() {
        return prisonsEntity;
    }

    public void setPrisonsEntity(PrisonsEntity prisonsEntity) {
        this.prisonsEntity = prisonsEntity;
    }


    void selectForUpateInmate(Object entity) {
        System.out.println("What element would you like to be updated?\n1.First name\n2.Last name\n3.CNP\n4.CheckIn Date\n5.Check Out Date");
        int option = Utils.scannerOption();
        switch (option) {
            case 1:
                System.out.println("Introduce the new first name: ");
                ((InmatesEntity) entity).setFirstNamePrison(Utils.scannerOptionString());
                break;
            case 2:
                System.out.println("Introduce the new last name: ");
                ((InmatesEntity) entity).setLastNamePrison(Utils.scannerOptionString());
                break;
            case 3:
                System.out.println("Introduce the new cnp: ");
                String inmateCnp = Utils.scannerOptionString();
                if (Utils.isCNPValid(inmateCnp)) {
                    ((InmatesEntity) entity).setCnpInmate(inmateCnp);
                } else {

                    while (Utils.isCNPValid(inmateCnp) == false) {
                        System.out.println("CNP is not valid. Try again");
                        inmateCnp = Utils.scannerOptionString();
                    }
                    ((InmatesEntity) entity).setCnpInmate(Utils.scannerOptionString());
                }

                break;
            case 4:
                System.out.println("Insert date in format yyyy mm dd");
                ((InmatesEntity) entity).setCheckInPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
                break;
            case 5:
                System.out.println("Insert date in format yyyy mm dd");
                ((InmatesEntity) entity).setCheckOutPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
                break;
            case 6:
                System.out.println("Insert prisonId: ");
                Integer prisonId = Utils.scannerOption();
                AppHibernate hibernate = new AppHibernate();
                hibernate.findById(prisonId);
        }
    }

    public InmatesEntity inmateRegistration(Integer prisonId) {
        InmatesEntity inmate = new InmatesEntity();
        PrisonsEntity prisonsEntity = null;
        AppHibernate hibernate = new AppHibernate();
        System.out.println("Insert inmate first name: ");
        inmate.setFirstNamePrison(Utils.scannerOptionString());
        System.out.println("Insert inmate last name: ");
        inmate.setLastNamePrison(Utils.scannerOptionString());
        System.out.println("Insert checkIn date - use format yyyy mm dd: ");
        inmate.setCheckInPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
        System.out.println("Insert checkOut date - use format yyyy mm dd:  ");
        inmate.setCheckOutPrison(LocalDate.of(Utils.scannerOption(), Utils.scannerOption(), Utils.scannerOption()));
        System.out.println("Insert prison id: ");
        try {
            Session session = hibernate.getSessionFactory().openSession();
            prisonsEntity = session.find(PrisonsEntity.class, prisonId);
        } catch (Exception e) {
            System.out.println(e);
        }
        inmate.setPrisonsEntity(prisonsEntity);
        System.out.println("Insert inmate CNP: ");
        inmate.setCnpInmate(null);

        return inmate;
    }

    @Override
    public String toString() {
        return "InmatesEntity{" +
                "firstNamePrison='" + firstNamePrison + '\'' +
                ", lastNamePrison='" + lastNamePrison + '\'' +
                ", cnpInmate='" + cnpInmate + '\'' +
                ", checkInPrison=" + checkInPrison +
                ", checkOutPrison=" + checkOutPrison +
                ", prisonsEntity=" + prisonsEntity +
                '}';
    }
}
