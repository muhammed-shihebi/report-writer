package sample.model;

public class StageOfExamination {
    private String stage = "";
    private int id = 0;

    public StageOfExamination(int id ,String stage) {
        this.stage = stage;
        this.id = id;
    }

    public StageOfExamination(String stage) {
        this.stage = stage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public String toString(){
        return this.getStage();
    }

}
