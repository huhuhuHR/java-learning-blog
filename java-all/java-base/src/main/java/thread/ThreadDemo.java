package thread;

public class ThreadDemo {
    public static void main(String[] args) {
        System.out.println("-----多线程创建开始-----");
        // 1.创建一个线程
        CreateThread createThread = new CreateThread();
        // 2.开始执行线程 注意 开启线程不是调用run方法，而是start方法
        System.out.println("-----多线程创建启动-----");
        createThread.start();
        System.out.println("-----多线程创建结束-----");
    }
}
