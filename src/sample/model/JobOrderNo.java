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
