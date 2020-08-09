package mem;

public class StackOverFlowDemo {
    public static void main(String[] args) {
        new StackOverFlowDemo().digui();
    }

    public void digui() {
        long time = System.currentTimeMillis();
        digui();
    }
}
