package sysn;

public class InstructionReOrder {
    static Integer a = 0;
    static Integer b = 0;
    static Integer x = 0;
    static Integer y = 0;

    private void showReOrder() throws InterruptedException {

        for(int i=0;i<Integer.MAX_VALUE;i++){
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 有可能发生重排，即 先执行 x = b,再执行 a = 1
                    a = 1;
                    x = b;

                }
            });

            Thread t2= new Thread(new Runnable() {
                @Override
                public void run() {
                    // 有可能发生重排，即先执行 y = a,再执行 b = 1;
                    b = 1;
                    y = a;
                }
            });

            t2.start();
            t1.start();
            t1.join();
            t2.join();
            /**
             * 如果没有指令重排，输出的可以结果为:(0,1)(1,1)(1,0)
             * 但实际上有可能会输出(0,0)
             */
            System.out.println("第 "+ i + "次，x="+x +", y="+y);
            if(x == 0 && y == 0){
                break;
            }
            a = b = 0;
            x = y = 0;
        }
    }

    public static void main(String[] args) {
        InstructionReOrder reOrder = new InstructionReOrder();
        try {
            reOrder.showReOrder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}