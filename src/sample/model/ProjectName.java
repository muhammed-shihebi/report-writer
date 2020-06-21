/*
 * @Datei           ProjectName.java
 * @Autor           Muhammednur Şehebi
 * @Matrikelnummer  170503112
 * @Date            6/20/2020
 */

package sample.model;

public class ProjectName {
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
