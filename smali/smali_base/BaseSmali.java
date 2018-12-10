public class BaseSmali {

    private void test() {
        int a = 31;
        int b = 2;
    }


    private float add() {
        int a = 1;
        float b = 1.5f;
        return a + b;
    }

    private double sub() {
        int a = 1;
        double b = 2.5;
        return a - b;
    }

    private double mul() {
        float a = 1.5f;
        double b = 2;
        return a * b;
    }

    private int div() {
        int a = 3;
        int b = 2;
        int c = a / b;
        return c;
    }

    private boolean bool(boolean a, boolean b,boolean c) {
        return a && b || c;
    }

    private void other(int a) {
        int or = a | 1;
        int and = a & 1;
        int right = a >> 2;
        int left = a << 2;
        int mod = a % 2;
    }

    private void loop(){
        for (int i=0;i<10;i++){
            System.out.println(i);
        }
    }

}
