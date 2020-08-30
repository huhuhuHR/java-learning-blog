package template;

public class SingleE {
    private static volatile SingleE instance = null;

    private SingleE() {
    }

    public static synchronized SingleE getInstance() {
        if (instance == null) {
            synchronized (SingleE.class) {
                if (instance == null) {
                    instance = new SingleE();
                }
            }
        }
        return instance;
    }
}
