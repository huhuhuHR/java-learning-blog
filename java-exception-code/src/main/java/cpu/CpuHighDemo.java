package cpu;

public class CpuHighDemo {
    public static void main(String[] args) {
        System.out.println("start to run an empty loop!");
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            //do nothing
            System.out.println("running...");
        }
    }
}
