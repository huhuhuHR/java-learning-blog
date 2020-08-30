package template;

public class SingleA {
    private static SingleA instance = null;

    private SingleA() {
    }

    public static SingleA getInstance() {
        //多个线程同时调用，可能会创建多个对象
        if (instance == null) {
            instance = new SingleA();
        }
        return instance;
    }
}
