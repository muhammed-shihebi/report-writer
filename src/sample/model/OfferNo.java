package sample.model;

public class OfferNo {
    private String number = "";

    public OfferNo(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return this.getNumber();
    }
}
