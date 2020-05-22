package sample.model;

    public class SurfaceCondition {
    private String condition = "";
    private int id = 0;

    public SurfaceCondition(int id, String condition) {
        this.condition = condition;
        this.id = id;
    }

    public SurfaceCondition(String condition) {
        this.condition = condition;
    }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString(){
        return this.getCondition();
    }
}
