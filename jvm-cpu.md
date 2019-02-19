# java进程cpu使用率高原因排查
> 在生产java应用,CPU使用率一直很高,经常达到100%。有可能是开发代码逻辑有问题，甚至写了死循环.
## java工具
- jps 可以列出正在运行的虚拟机进程，并显示虚拟机执行主类（Main Class,main（）函数所在的类）名称以及这些进程的本地虚拟机唯一ID（Local Virtual Machine Identifier,LVMID）
    * -q  id
    * -m  main函数参数
    * -l 主类全名
    * -v jvm启动参数
## 排查思路
- 1 jps获取java进程的pid
- 2 jstack pid >>java.txt 导出CPU占用高进程的线程栈
- 3 top -H -p pid 查看对应的进程的哪个线程占用的CPU较高
- 4 echo "obase=16;PID"|bc  将线程的PID转换为16进制
- 5 在第二步导出的java.txt中查找转换为16进制的线程PID。找到对应的线程栈
- 6 分析负载高的线程栈都是什么业务操作。优化程序并处理问题

## linux辅助排查工具
- 根据端口查进程：
    * lsof -i:port 
    * netstat -nap | grep port
- 根据进程号查端口：
    * lsof -i|grep pid 
    * netstat -nap | grep pid
- 根据进程名查找pid、port：
    * ps -ef |grep tomcat
    * ps -ef |grep port(根据port查找相关进程)
    * ps -ef |grep pid(根据pid查找相关进程)
    
## 线上服务CPU100%问题快速定位实战
- top -c       P(排序) M(内存排序)  10765 找cpu最耗的PID
- top -Hp 10765   找最耗CPU的线程  10804
- printf "%x\n" 10804 或者  echo "obase=16; pid"| bc 
- jstack 10765 | grep '0x2a34' -C5 --color 
> pid ->线程 ->通过线程pid16进致输出堆栈信息