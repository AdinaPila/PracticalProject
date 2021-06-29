package com.sda.alina.exercises.prisonsmanagement.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "prisons")
public class PrisonsEntity extends AppHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prisonId;

    @NotNull
    private String prisonName;

    @NotNull
    private Integer totalCapacity;

    @NotNull
    private Integer securityLevel;

    @OneToMany( fetch = FetchType.EAGER, mappedBy = "prisonsEntity")
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

    @Override
    public String toString() {
        return "PrisonsEntity{" +
                "prisonId=" + prisonId +
                ", prisonName='" + prisonName + '\'' +
                ", totalCapacity=" + totalCapacity +
                ", securityLevel=" + securityLevel +
                ", inmatesList=" + inmatesList +
                '}';
    }
}
