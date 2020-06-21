/*
 * @Datei           JobOrderNo.java
 * @Autor           Muhammednur Şehebi
 * @Matrikelnummer  170503112
 * @Date            6/20/2020
 */

package sample.model;

public class JobOrderNo {
    private String number = "";

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public JobOrderNo(String number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return this.getNumber();
    }
}
