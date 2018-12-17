public class BMW extends Car implements IFly{

    private String brand = "BMW";

    @Override
    void run() {
        System.out.println(brand + " run!");
    }

    @Override
    public void fly() {
        System.out.println("I can fly!");
    }

    public static void main(String[] args){
        BMW bmw=new BMW();
        bmw.run();
        bmw.fly();
    }
}
