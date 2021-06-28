package database.prisonsmanagement.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inmates")
public class InmatesEntity extends AppHibernate {

    private String firstNamePrison;

    private String lastNamePrison;


    @Id
    @Column(name = "cnpInmate", unique = true, nullable = false, columnDefinition = "VARCHAR(13)")
    private String cnpInmate;

    private LocalDate checkInPrison;

    private LocalDate checkOutPrison;

    @NotFound(action = NotFoundAction.IGNORE)
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

}
