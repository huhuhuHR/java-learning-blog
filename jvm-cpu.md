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
- jps 显示指定系统内所有的HotSpot虚拟机进程
    * jps -q: 显示进程号
    * jps -m: main函数参数
    * jps -l:主类全名
    * jps -v:jvm启动参数

- jstat 收集HotSpot虚拟机各方面的运行数据 
    * jstat -参数 进程号 间隔时间 监控次数
    * 类加载统计jstat -class pid:Loaded Bytes  Unloaded  Bytes     Time
        * Loaded:加载class的数量
        * Bytes：所占用空间大小
        * Unloaded：未加载数量
        * Bytes:未加载占用空间
        * Time：时间 
    * 编译统计 jstat -compiler pid:Compiled Failed Invalid   Time   FailedType FailedMethod
        * Compiled：编译数量。
        * Failed：失败数量
        * Invalid：不可用数量
        * Time：时间
        * FailedType：失败类型
        * FailedMethod：失败的方法
    * 垃圾回收统计 jstat -gc pid: S0C|S1C|S0U|S1U|EC|EU|OC|OU|MC|MU|CCSC|CCSU|YGC|YGCT|FGC|FGCT|GCT
        * S0C：第一个幸存区的大小
        * S1C：第二个幸存区的大小
        * S0U：第一个幸存区的使用大小
        * S1U：第二个幸存区的使用大小
        * EC：伊甸园区的大小
        * EU：伊甸园区的使用大小
        * OC：老年代大小
        * OU：老年代使用大小
        * MC：方法区大小
        * MU：方法区使用大小
        * CCSC:压缩类空间大小
        * CCSU:压缩类空间使用大小
        * YGC：年轻代垃圾回收次数
        * YGCT：年轻代垃圾回收消耗时间
        * FGC：老年代垃圾回收次数
        * FGCT：老年代垃圾回收消耗时间
        * GCT：垃圾回收消耗总时间    
    * 堆内存统计 jstat -gccapacity pid:NGCMN|NGCMX|NGC|S0C|S1C|EC|OGCMN|OGCMX|OGC|OC|MCMN|MCMX|MC|CCSMN|CCSMX|CCSC|YGC|FGC 
        * NGCMN：新生代最小容量
        * NGCMX：新生代最大容量
        * NGC：当前新生代容量
        * S0C：第一个幸存区大小
        * S1C：第二个幸存区的大小
        * EC：伊甸园区的大小
        * OGCMN：老年代最小容量
        * OGCMX：老年代最大容量
        * OGC：当前老年代大小
        * OC:当前老年代大小
        * MCMN:最小元数据容量
        * MCMX：最大元数据容量
        * MC：当前元数据空间大小
        * CCSMN：最小压缩类空间大小
        * CCSMX：最大压缩类空间大小
        * CCSC：当前压缩类空间大小
        * YGC：年轻代gc次数
        * FGC：老年代GC次数
    * 新生代垃圾回收统计 jstat -gcnew pid:S0C|S1C|S0U|S1U|TT|MTT|DSS|EC|EU|YGC|YGCT 
        * S0C：第一个幸存区大小
        * S1C：第二个幸存区的大小
        * S0U：第一个幸存区的使用大小
        * S1U：第二个幸存区的使用大小
        * TT:对象在新生代存活的次数
        * MTT:对象在新生代存活的最大次数
        * DSS:期望的幸存区大小
        * EC：伊甸园区的大小
        * EU：伊甸园区的使用大小
        * YGC：年轻代垃圾回收次数
        * YGCT：年轻代垃圾回收消耗时间
    * 新生代内存统计 jstat -gcnewcapacity pid:NGCMN|NGCMX|NGC|S0CMX|S0C|S1CMX|S1C|ECMX|EC|YGC|FGC
        * NGCMN：新生代最小容量
        * NGCMX：新生代最大容量
        * NGC：当前新生代容量
        * S0CMX：最大幸存1区大小
        * S0C：当前幸存1区大小
        * S1CMX：最大幸存2区大小
        * S1C：当前幸存2区大小
        * ECMX：最大伊甸园区大小
        * EC：当前伊甸园区大小
        * YGC：年轻代垃圾回收次数
        * FGC：老年代回收次数  
    * 老年代垃圾回收统计 jstat -gcold pid:MC|MU|CCSC|CCSU|OC|OU|YGC|FGC|FGCT|GCT  
        * MC：方法区大小
        * MU：方法区使用大小
        * CCSC:压缩类空间大小
        * CCSU:压缩类空间使用大小
        * OC：老年代大小
        * OU：老年代使用大小
        * YGC：年轻代垃圾回收次数
        * FGC：老年代垃圾回收次数
        * FGCT：老年代垃圾回收消耗时间
        * GCT：垃圾回收消耗总时间
     * 老年代内存统计 jstat -gcoldcapacity pid:OGCMN|OGCMX|OGC|OC|YGC|FGC|FGCT|GCT 
        * OGCMN：老年代最小容量
        * OGCMX：老年代最大容量
        * OGC：当前老年代大小
        * OC：老年代大小
        * YGC：年轻代垃圾回收次数
        * FGC：老年代垃圾回收次数
        * FGCT：老年代垃圾回收消耗时间
        * GCT：垃圾回收消耗总时间
     * 元数据空间统计 jstat -gcmetacapacity pid:MCMN|MCMX|MC|CCSMN|CCSMX|CCSC|YGC|FGC|FGCT|GCT
        * MCMN: 最小元数据容量
        * MCMX：最大元数据容量
        * MC：当前元数据空间大小
        * CCSMN：最小压缩类空间大小
        * CCSMX：最大压缩类空间大小
        * CCSC：当前压缩类空间大小
        * YGC：年轻代垃圾回收次数
        * FGC：老年代垃圾回收次数
        * FGCT：老年代垃圾回收消耗时间
        * GCT：垃圾回收消耗总时间
     * 总结垃圾回收统计 jstat -gcutil pid:S0|S1|E|O|M|CCS|YGC|YGCT|FGC|FGCT|GCT
        * S0：幸存1区当前使用比例
        * S1：幸存2区当前使用比例
        * E：伊甸园区使用比例
        * O：老年代使用比例
        * M：元数据区使用比例
        * CCS：压缩使用比例
        * YGC：年轻代垃圾回收次数
        * FGC：老年代垃圾回收次数
        * FGCT：老年代垃圾回收消耗时间
        * GCT：垃圾回收消耗总时间
     * JVM编译方法统计 jstat -printcompilation pid:Compiled  Size  Type Method
        * Compiled：最近编译方法的数量
        * Size：最近编译方法的字节码数量
        * Type：最近编译方法的编译类型。
        * Method：方法名标识。
- jinfo 显示虚拟机配置信息
    * jinfo -sysprops PID  查看java系统参数
    * jinfo -flags PID 查看jvm的参数
    
- jmap 生成虚拟机的内存转储快照（heapdump文件）
    * jmap -heap pid >> xx.txt-打印heap空间的概要，这里可以粗略的检验heap空间的使用情况
    * jmap -heap:format=b pid 产生一个HeapDump文件，此为生成heapdump文件的重要参数
    * jmap -histo:live pid|sort -n|head 统计对象前10个，显示实例个数，占用字节数，对象名称
    
- jstack 显示虚拟机的线程快照
    * jstack pid | grep itd  -C5 --color  打印线程前后五行      
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