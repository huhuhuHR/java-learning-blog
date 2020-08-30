package template;

public class SingleD {
    private static SingleD instance = null;

    private SingleD() {
    }

    public static synchronized SingleD getInstance() {
        if (instance == null){
            synchronized (SingleD.class){
                if(instance == null){
                    instance = new SingleD();
                }
            }
        }
        return instance;
    }
}
