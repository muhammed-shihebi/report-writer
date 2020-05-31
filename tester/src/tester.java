public class tester {
    public class p1{
        int s ;
        int l;

        public p1(int s, int l) {
            this.s = s;
            this.l = l;
        }

        public p1(int s) {
            this.s = s;
        }

        public int getS() {
            return s;
        }

        public void setS(int s) {
            this.s = s;
        }

        public int getL() {
            return l;
        }

        public void setL(int l) {
            this.l = l;
        }
    }



    public static int test(Object o){
        System.out.println(o.getClass().getTypeName());
        return 1;
    }

    public static void main(String[] args) {
        String nur = "hello";
        test(nur);
    }
}
