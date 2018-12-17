public class Outer {

    private class Inner {
        private void in() {
            System.out.println("I am inner class.");
        }
    }

    private static class StaticInner {
        private void staticIn() {
            System.out.println("I am static inner class.");
        }
    }

    public static void main(String[] args) {

        Outer outer = new Outer();

        Inner inner = outer.new Inner();
        inner.in();

        StaticInner staticInner = new StaticInner();
        staticInner.staticIn();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }

    public void test(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}
