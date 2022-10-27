package ch.zhaw.springboot.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

//Psycho Profile is carried out using the theory of team roles according to Belbin
@Entity
public class PsychoProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //Action-oriented roles
    private int implementerPercentage;
    private int completerPercentage;
    private int shaperPercentage;
    //Communication-oriented roles
    private int coordinatorPercentage;
    private int teamworkerPercentage;
    private int resourceInvestigatorPercentage;
    //Knowledge-oriented roles
    private int plantPercentage;
    private int monitorEvaluatorPercentage;
    private int specialistPercentage;
    
    @OneToOne(mappedBy="psychoProfile")
    private Person person;
    
    public PsychoProfile (Person person) {
        this.person = person;
        this.shaperPercentage = 0;
        this.coordinatorPercentage = 0;
        this.teamworkerPercentage = 0;
        this.resourceInvestigatorPercentage = 0;
        this.plantPercentage = 0;
        this.monitorEvaluatorPercentage = 0;
        this.specialistPercentage = 0;
    }    
    
    public PsychoProfile (Person person, int implementerPercentage, int completerPercentage, int shaperPercentage, int coordinatorPercentage, int teamworkerPercentage, int resourceInvestigatorPercentage, int plantPercentage, int monitorEvaluatorPercentage, int specialistPercentage) {
        this.person = person;
        this.shaperPercentage = shaperPercentage;
        this.coordinatorPercentage = coordinatorPercentage;
        this.teamworkerPercentage = teamworkerPercentage;
        this.resourceInvestigatorPercentage = resourceInvestigatorPercentage;
        this.plantPercentage = plantPercentage;
        this.monitorEvaluatorPercentage = monitorEvaluatorPercentage;
        this.specialistPercentage = specialistPercentage;
    }
    
    public long getId() {
        return this.id;
    }

    public int getImplementerPercentage() {
        return this.implementerPercentage;
    }

    public void setImplementerPercentage(int implementerPercentage) {
        this.implementerPercentage = implementerPercentage;
    }

    public int getCompleterPercentage() {
        return this.completerPercentage;
    }

    public void setCompleterPercentage(int completerPercentage) {
        this.completerPercentage = completerPercentage;
    }

    public int getShaperPercentage() {
        return this.shaperPercentage;
    }

    public void setShaperPercentage(int shaperPercentage) {
        this.shaperPercentage = shaperPercentage;
    }

    public int getCoordinatorPercentage() {
        return this.coordinatorPercentage;
    }

    public void setCoordinatorPercentage(int coordinatorPercentage) {
        this.coordinatorPercentage = coordinatorPercentage;
    }

    public int getTeamworkerPercentage() {
        return this.teamworkerPercentage;
    }

    public void setTeamworkerPercentage(int teamworkerPercentage) {
        this.teamworkerPercentage = teamworkerPercentage;
    }

    public int getResourceInvestigatorPercentage() {
        return this.resourceInvestigatorPercentage;
    }

    public void setResourceInvestigatorPercentage(int resourceInvestigatorPercentage) {
        this.resourceInvestigatorPercentage = resourceInvestigatorPercentage;
    }

    public int getPlantPercentage() {
        return this.plantPercentage;
    }

    public void setPlantPercentage(int plantPercentage) {
        this.plantPercentage = plantPercentage;
    }

    public int getMonitorEvaluatorPercentage() {
        return this.monitorEvaluatorPercentage;
    }

    public void setMonitorEvaluatorPercentage(int monitorEvaluatorPercentage) {
        this.monitorEvaluatorPercentage = monitorEvaluatorPercentage;
    }

    public int getSpecialistPercentage() {
        return this.specialistPercentage;
    }

    public void setSpecialistPercentage(int specialistPercentage) {
        this.specialistPercentage = specialistPercentage;
    }

    public Person getPerson() {
        return this.person;
    }
}
