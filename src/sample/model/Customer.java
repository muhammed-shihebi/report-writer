package sample.model;

import javafx.collections.ObservableList;

public class Customer {
    private int id = 0;
    private String name = "";
    private String testPlace = "";
    private ObservableList<OfferNo> offerNos;
    private ObservableList<JobOrderNo> jobOrderNos;
    private ObservableList<ProjectName> projectNames;


    public Customer(String name, String testPlace, ObservableList<JobOrderNo> jobOrderNos,
                    ObservableList<ProjectName> projectNames,ObservableList<OfferNo> offerNos){
        this.name = name;
        this.testPlace = testPlace;
        this.jobOrderNos = jobOrderNos;
        this.projectNames = projectNames;
        this.offerNos = offerNos;
    }
    public Customer(int id,String name, String testPlace){
        this.id = id;
        this.name = name;
        this.testPlace = testPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestPlace() {
        return testPlace;
    }

    public void setTestPlace(String testPlace) {
        this.testPlace = testPlace;
    }

    public ObservableList<OfferNo> getOfferNos() {
        return offerNos;
    }

    public void setOfferNos(ObservableList<OfferNo> offerNos) {
        this.offerNos = offerNos;
    }

    public ObservableList<JobOrderNo> getJobOrderNos() {
        return jobOrderNos;
    }

    public void setJobOrderNos(ObservableList<JobOrderNo> jobOrderNos) {
        this.jobOrderNos = jobOrderNos;
    }

    public ObservableList<ProjectName> getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(ObservableList<ProjectName> projectNames) {
        this.projectNames = projectNames;
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
