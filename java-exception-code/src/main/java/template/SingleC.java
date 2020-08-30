package template;

public class SingleC {
    private static SingleC instance = null;

    private SingleC() {
    }

    public static synchronized SingleC getInstance() {
        //多个线程同时调用，可能会创建多个对象
        if (instance == null) {
            instance = new SingleC();
        }
        return instance;
    }
}
