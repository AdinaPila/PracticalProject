package database.prisonsmanagement.entities;

import database.prisonsmanagement.Utils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "prisons")
public class PrisonsEntity extends AppHibernate {

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



}
