package database.prisonsmanagement.entitys;

import database.prisonsmanagement.Utils;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "prisons")
public class PrisonsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prisonId;

    private String prisonName;

    private Integer totalCapacity;

    private Integer securityLevel;

    @OneToMany(mappedBy = "prisonsEntity")
    private List<InmatesEntity> inmatesList;


    public void setPrisonId(Integer prisonId) {
        this.prisonId = prisonId;
    }

    public void setPrisonName(String prisonName) {
        this.prisonName = prisonName;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public void setSecurityLevel(Integer securityLevel) {
        this.securityLevel = securityLevel;
    }

    public void setInmatesList(List<InmatesEntity> inmatesList) {
        this.inmatesList = inmatesList;
    }

    public Integer getPrisonId() {
        return prisonId;
    }

    public String getPrisonName() {
        return prisonName;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public List<InmatesEntity> getInmatesList() {
        return inmatesList;
    }



    void selectForUpatePrison(Object entity) {
        System.out.println("What element would you like to be updated?\n1.Prison Name\n2.Security Level\n3.Total Capacity");
        int option = Utils.scannerOption();
        Scanner scanner = new Scanner(System.in);
        switch (option) {
            case 1:
                System.out.println("Insert the new name: ");
                ((PrisonsEntity) entity).setPrisonName(scanner.nextLine());
                break;
            case 2:
                System.out.println("Insert the new security level: ");
                ((PrisonsEntity) entity).setSecurityLevel(Utils.scannerOption());
                break;
            case 3:
                System.out.println("Insert the new capacity: ");
                ((PrisonsEntity) entity).setTotalCapacity(Utils.scannerOption());
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
