## 常用排查语句
- 根据端口查进程
    * lsof -i:port 
    * netstat -nap | grep port
- 根据进程号查端口
    * lsof -i|grep pid 
    * netstat -nap | grep pid
- 根据进程名查找pid、port
    * ps -ef |grep tomcat
    * ps -ef |grep port(根据port查找相关进程)
    * ps -ef |grep pid(根据pid查找相关进程)
- linux 下 取进程占用 cpu 最高的前10个进程"
    * ps aux|head -1;ps aux|grep -v PID|sort -rn -k +3|head
- linux 下 取进程占用内存(MEM)最高的前10个进程
    * ps aux|head -1;ps aux|grep -v PID|sort -rn -k +4|head
- 打开句柄数排序最高的前10个进程
    * lsof -n|awk '{print $2}'|sort|uniq -c |sort -nr|head
- 线程打开句柄数
    * ps -LF PID|wc -l
- 线程打开句柄详情
    * ps -LF PID
- top输出某个特定进程<pid>并检查该进程内运行的线程状况
    * top -H -p PID
- jstack统计线程数
    * jstack -l PID | grep 'java.lang.Thread.State' | wc -l
- 转换PID/tid为十六进制
    * printf  "%x"  PID/tid
- ps查看相关进程
    * jps -mlv
- top查找最消耗cpu或者mem的进程
    * top -c P(排序) M(内存排序)
- 统计进程使用的句柄数
    * lsof -p PID|wc -l
## java工具
- jps 可以列出正在运行的虚拟机进程，并显示虚拟机执行主类（Main Class,main（）函数所在的类）名称以及这些进程的本地虚拟机唯一ID（Local Virtual Machine Identifier,LVMID）
    * -q  id
    * -m  main函数参数
    * -l 主类全名
    * -v jvm启动参数

## 线上服务CPU100%问题快速定位实战
- top -c       P(排序) M(内存排序)  PID:10765 找cpu最耗的PID
- top -Hp 10765   找最耗CPU的线程  tid:10804
- printf "%x\n" 10804
- jstack 10765 | grep '0x2a34' -C5 --color  打印线程前后五行
> pid ->线程 ->通过线程pid16进致输出堆栈信息
## 实战
### cpu 100%
#### 启动有会导致cpu爆高的代码
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
    nohup java -classpath java-exception-code-1.0.jar cpu.CpuHighDemo &
#### 第一步:找到cpu较高的进程
    linux 下 取进程占用 cpu 最高的前10个进程
    ps aux|head -1;ps aux|grep -v PID|sort -rn -k +3|head
    查看最消耗资源的PID-P(排序) M(内存排序)
    top -c
#### 第二步:找到进程中cou最高的线程
    PID 进程id tid线程id
    查看PD具体进程相关信息
    ps aux | grep PID
    查看PID的线程列表
    ps -mp pid -o THREAD,tid,time
    top输出某个特定进程<pid>并检查该进程内运行的线程状况
    top -H -p PID
    线程ID转换为16进制格式
    printf "%x\n" tid
#### 第三步:查看线程详情
    最后打印线程的堆栈信息
    jstack pid | grep tid -A 30(-A后 -B前 -C前后)
    jstack pid | more
#### outofmemory
##### HeapOutOfMemory
    public class HeapOutOfMemoryDemo {
        public static void main(String[] args) throws Exception {
            ArrayList<String> arrayList = new ArrayList<>(100000000);
            for (int i = 0; i <= 100000000; ++i) {
                arrayList.add(Integer.toString(i));
                if (i % 10000 == 0) {
                    System.out.println(i);
                }
            }
        }
    }
    nohup java -Xms128m -Xmx128m -classpath  java-exception-code-1.0.jar mem.HeapOutOfMemoryDemo & 
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    	at java.util.ArrayList.<init>(ArrayList.java:153)
    	at mem.HeapOutOfMemoryDemo.main(HeapOutOfMemoryDemo.java:7
##### Young OutOfMemory
    设置XX：MaxTenuringThreshold为一个很大的值
    使对象无法及时的移动到年老代中，导致年轻代内存溢出
##### MethodArea OutOfMemory
    在经常动态生成大量Class的应用中，需要特别注意类的回收状况。这类场景除了上面提到的程序使用了CGLib字节码增强和动态语言
    之外，常见的还有：大量JSP或动态产生JSP文件的应用（JSP第一次运行时需要编译为Java类）、基于OSGi的应用（即使是同一个类文
    件，被不同的加载器加载也会视为不同的类）等。
##### ConstantPool OutOfMemory
    一般来说是不可能的，只有项目启动方法区内存很小或者项目中的静态变量极其多时才会发生
##### DirectMemory OutOfMemory
    public class DirectMemoryDemo {
        public static void main(String[] args) throws Exception{
            List<ByteBuffer> buffers = new ArrayList<>();
            while(true){
                ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 1024);
                buffers.add(buffer);
            }
        }
    }
    nohup java -Xms128m -Xmx128m -classpath  java-exception-code-1.0.jar mem.DirectMemoryDemo & 
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    	at java.nio.HeapByteBuffer.<init>(HeapByteBuffer.java:57)
    	at java.nio.ByteBuffer.allocate(ByteBuffer.java:335)
    	at mem.DirectMemoryDemo.main(DirectMemoryDemo.java:11)
##### Stack OutOfMemory Stack OverFlow
    public class StackOverFlowDemo {
        public static void main(String[] args) throws Exception{
            new StackOverFlowDemo().digui();
        }
     
        public void digui(){
            long time = System.currentTimeMillis();
            digui();
        }
    }
    nohup java -Xms128m -Xmx128m -classpath  java-exception-code-1.0.jar mem.StackOverFlowDemo & 
    Exception in thread "main" java.lang.StackOverflowError
    	at mem.StackOverFlowDemo.digui(StackOverFlowDemo.java:10)
    	at mem.StackOverFlowDemo.digui(StackOverFlowDemo.java:10)
    	at mem.StackOverFlowDemo.digui(StackOverFlowDemo.java:10)
    	at mem.StackOverFlowDemo.digui(StackOverFlowDemo.java:10)
    	at mem.StackOverFlowDemo.digui(StackOverFlowDemo.java:10)
    	at mem.StackOverFlowDemo.digui(StackOverFlowDemo.java:10)