package template;

public class SingleB {
    private static SingleB instance = new SingleB();

    private SingleB() {
    }

    public static SingleB getInstance() {
        return instance;
    }
}
