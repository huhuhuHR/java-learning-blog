# Java8
### 为什么要学习java8？
* 改善代码可读性
* 增加代码灵活性
* 利于重构
### 关于stream().foreach(),foreach
```
    public int outside = 0;
    
    private void forCounter(List<Integer> integers) {
        for(int ii = 0; ii < integers.size(); ii++) {
            Integer next = integers.get(ii);
            outside = next*next;
        }
    }
    
    private void forEach(List<Integer> integers) {
        for(Integer next : integers) {
            outside = next * next;
        }
    }
    
    private void iteratorForEach(List<Integer> integers) {
        integers.forEach((ii) -> {
            outside = ii*ii;
        });
    }
    private void iteratorStream(List<Integer> integers) {
        integers.stream().forEach((ii) -> {
            outside = ii*ii;
        });
    }
```
### Here are my timings: milliseconds / function / number of entries in list. Each run is 10^6 loops.
    
                               1    10    100    1000    10000
             for with index   39   112    920    8577    89212
           iterator.forEach   27   116    959    8832    88958
                   for:each   53   171   1262   11164   111005
    iterable.stream.forEach  255   324   1030    8519    88419
### 结论四种循环方式：数据量越大，stream优势越大。
### 关于Stream
* java8中的Stream是对集合Collection对象功能的增强
* 专注对集合对象进行各种非常便利，高效的聚合操作，大批量数据操作。
* Stream api 借助于同样新出现Lambda表达式，极大的提高变成效率和程序可读性。
* Stream不是集合元素，它不是数据结构，不保存数据，他是有关算法和计算的，它更像一个高级版本的Iterator.
* 单向的，只能遍历一次，好似流水
* Stream另一大特点，数据源本身可以是无限的
#### 流分两种：
	Intermediate 中间流
	Terminal 终端流
#### 生成stream方式
* Collection.stream();
* Collection.parallelStream();
* Arrays.stream(T array) or Stream.of()
* java.io.BufferedReader.lines();
* java.util.stream.IntStream.range();
* java.nio.file.File.walk();
* java.util.Spliterator;
* Random.ints();
* BitSet.stream();
* Pattern.splitAsStream(java.lang.CharSequence);
* JarFile.stream();
#### 例子
```
    // Individual values
    Stream stream = Stream.of("a", "b", "c");
    // Arrays
    String[] strArray = new String[] { "1", "2", "3" };
    Stream arrayStream = Stream.of(strArray);
    Stream arrayStreamCopy = Arrays.stream(strArray);
    // Collections
    List<String> list = Arrays.asList(strArray);
    Stream lastestStream = list.stream();
```
#### Api函数：
##### Intermediate:生成流
    map(mapToInt,flatMap等) filter,distinct,sorted,peek,limit,skip,parallel,sequential,unordered
##### Terminal:一次性
```
    forEach,forEachOrdered,toArray,reduce,collect,min,max,count,anyMatch,allMatch,
    findFirst,findAny,iterator
    short-circuiting:
    anyMatch,allMatch,noneMatch,findFirst,findAny,limit

    List<String> output = wordList.stream().map(String::toUpperCase).collect(Collectors.toList());
```
##### 流处理：流是一系列数据项，一次生成一项。程序可以从输入流中一个个读取项，
##### 然后以同样的方式写入输出流。一个程序的输出流很可能是另一个程序的输出流。
```    
    File[] hiddenFiles = new File(".").listFiles(new FileFilter(){
        public boolean accept(File file){
            return file.isHidden;
        }
    })
    java 8
    File[] hiddenFiles = new File(".").listFiles(File::isHidden);
    Screening of apple
    //筛选红色苹果
    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> reuslt = new ArrayList<>();
        for(Apple apple : inventory){
            if("Green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }
    // 筛选红色苹果
    public static List<Apple> filterRedApple(List<Apple> inventory){
        List<Apple> result = new ArrrayList<>();
        for(Apple apple : inventory){
            "Green".equals(apple.getColor()) ? result.add(apple) : continue;
        }
        return result;
    }
    // evolution
    public static isGreenApple(Apple apple){
        return "Green".equals(apple.getColor());
    }
    public interfase Predicate<T>{
        boolean test(T t);
    }
    // Predicate<Apple> 谓词
    public static List<Apple> filterApples(List<Apple> inventory,Predicate<Apple> p){
        List<Apple> reuslt = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
    
    List<Apple> inventoryResult = apples.filterApples(inventory,Apple::isGreenApple);
```
#### 什么是谓词：
```
    前面的代码传递了方法Apple::isGreenApple
    它接受参数apple并返回一个boolean给filterApples，
    后者希望接受一个Predicate<Apple>参数。
    谓词接受一个类似函数的东西，它接受一个参数值，并返回true/false
    //例子晒出货币较高的交易，然后按照货币分组
    @Data
    public calss Transaction{
        Private Currency currency;
        private String price;
        private String name;
    }
    List<Transaction> transactions;假设的数据源头
    // 层级 Map {
                    {
                        name,list{
                            lsit1,
                            list2,
                            ...
                        }
                    },
                    ...
                }
    Map<String,List<Transaction>> result = new HashMap<>();
    for(Transaction transaction : transactions){
    //筛选 1000以上的数据
        if(transaction.getPrice() > 1000){
        // 取key
            String categoryName = transaction.getName();
            // 通过key 去 list
            List <Transaction> currentTranSaction = result.get(categoryName);
            // list为null
            if(currentTranSaction == null){
                //新增一个分类	
                currentTranSaction = new ArrayList<>();
                result.put(name,currentTranSaction);
            }
            // list 不为null时候，直接向分类引用添加元素
            currentTranSaction.add(transaction);
        }
    }
    // java 8 简化写法
    Map<String,List<Transaction>> transactionsByCurrencies
        =   transactions.stream
            .filter((Transaction t) -> t.getPrice() > 1000)
            .collect(groupingBy(Transaction::getCurrency));
    
    //for-each 第一种是外部迭代  stream api 是内部迭代
```
#### java stream 可以解决两个问题：
* 集合处理时的套路；难以利用多核
* 筛选一个Collection最快的方法常是将其转换成stream，进行并行处理，然后再转换回List。
* Optional<T> 类，这是一个可以为null的容器对象。如果值存在则isPresent()返回true，调用get回返回该对象
##### 例子：
```
    Optional<String> name = Optional.of("Sanaulla");
    // of
    Optional<String> someNull = Optional.of(null);// nullpointException
    
    // ofNullable 
    Optional empty = Optional.ofNullable(null);//可以接收null
    
    // orElse 有值则将其返回，否则返回指定的其他值
    // orElseGet 类似orElse
    // orElseThrow 如果有值将其返回没有抛异常
    demo：
    public class OptionalDemo {
      public static void main(String[] args) {
        //创建Optional实例，也可以通过方法返回值得到。
        Optional<String> name = Optional.of("Sanaulla");
 
    //创建没有值的Optional实例，例如值为'null'
    Optional empty = Optional.ofNullable(null);
 
    //isPresent方法用来检查Optional实例是否有值。
    if (name.isPresent()) {
      //调用get()返回Optional值。
      System.out.println(name.get());
    }
 
    try {
      //在Optional实例上调用get()抛出NoSuchElementException。
      System.out.println(empty.get());
    } catch (NoSuchElementException ex) {
      System.out.println(ex.getMessage());
    }
 
    //ifPresent方法接受lambda表达式参数。
    //如果Optional值不为空，lambda表达式会处理并在其上执行操作。
    name.ifPresent((value) -> {
      System.out.println("The length of the value is: " + value.length());
    });
 
    //如果有值orElse方法会返回Optional实例，否则返回传入的错误信息。
    System.out.println(empty.orElse("There is no value present!"));
    System.out.println(name.orElse("There is some value!"));
 
    //orElseGet与orElse类似，区别在于传入的默认值。
    //orElseGet接受lambda表达式生成默认值。
    System.out.println(empty.orElseGet(() -> "Default Value"));
    System.out.println(name.orElseGet(() -> "Default Value"));
 
    try {
      //orElseThrow与orElse方法类似，区别在于返回值。
      //orElseThrow抛出由传入的lambda表达式/方法生成异常。
      empty.orElseThrow(ValueAbsentException::new);
    } catch (Throwable ex) {
      System.out.println(ex.getMessage());
    }
 
    //map方法通过传入的lambda表达式修改Optonal实例默认值。 
    //lambda表达式返回值会包装为Optional实例。
    Optional<String> upperName = name.map((value) -> value.toUpperCase());
    System.out.println(upperName.orElse("No value found"));
 
    //flatMap与map（Funtion）非常相似，区别在于lambda表达式的返回值。
    //map方法的lambda表达式返回值可以是任何类型，但是返回值会包装成Optional实例。
    //但是flatMap方法的lambda返回值总是Optional类型。
    upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
    System.out.println(upperName.orElse("No value found"));
 
    //filter方法检查Optiona值是否满足给定条件。
    //如果满足返回Optional实例值，否则返回空Optional。
    Optional<String> longName = name.filter((value) -> value.length() > 6);
    System.out.println(longName.orElse("The name is less than 6 characters"));
 
    //另一个示例，Optional值不满足给定条件。
    Optional<String> anotherName = Optional.of("Sana");
    Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
    System.out.println(shortName.orElse("The name is less than 6 characters"));
 
  }
}
```
#### 行为参数化的好处：帮助你处理频繁变更的需求的一种软件开发模式。
```
    例子：
    // 只要红色
    public static List<Apple> filterApplesByColor(List<Apple> inventory) {
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if ("red".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
    
    // 再要一个绿色
    public static List<Apple> filterApplesByColor(List<Apple> inventory){
        List<Apple> result = new ArrayList<Apple>();
        for(Apple apple : inventory){
            if("green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }
    
    // 适合所有颜色
    public static List<Apple> filterApplesByColor(List<Apple> inventory,String color){
        List<Apple> result = new ArrayList<Apple>();
        for(Apple apple : inventory){
            if(apple.getColor().equals(color)){
                result.add(apple);
            }
        }
        return result;
    }
    
    // 筛选其它属性－入重量
    public static List<Apple> filterApplesByWeight(List<Apple> inventory,int weigth){
        List<Apple> result = new ArrayList<Apple>();
        for(Apple apple : inventory){
            if(apple.getWeight() > weight)  {
                result.add(apple);
            }
        }
        return result;
    }
    // 写出所有属性
    public static List<Apple> filterApplesByAll(List<Apple> inventory){
        List<Apple> result = new ArrayList<Apple>();
        for(Apple apple : inventory){
            if(apple.getWeight() > 150 || "green".equals(apple.getColor()))  {
                result.add(apple)
            }
        }
        retrun result;
    }
    // 行为参数化
    public interfase ApplePredicate{
        boolean test(Apple apple);
    }
    
    public class AppleHeavyWeightPredicate implements ApplePredicate {
        pirvate int weight;
        public AppleHeavyWeightPredicate(int weight){
            this.weight = weight;
        }
        public boolean test(Apple apple){
            return apple.getWeight() > getWeight();
        }
    }
    
    public class AppleColorPredicate implements ApplePredicate {
        private String color;
        public AppleColorPredicate(String color){
            this.color = color;
        }
        public boolean test(Apple apple){
            return getColor().equals(apple.getColor());
        }
    }
    
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
    List<Apple> inventorys = Arrays.asList(new Apple(80, "green"),
                                           new Apple(155, "green")
                                           new Apple(120, "red"));
    List<Apple> appFilterleWeight = filterApples(inventorys, new AppleColorPredicate("red"));
    
    List<Apple> appleFilterColor = filterApples(inventorys, new AppleHeavyWeightPredicate(150));
    
    // 匿名内部类
    List<Apple> redApples = filterApples(inventorys, new ApplePredicate(){
        public boolean test(Apple apple){
            return "red".equals(apple.getColor());
        }
    })

    解决：行为参数化
    用谓词筛选苹果
    public interface Predicate<T>{
        boolean test(T t);
    }
    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p){
        List<Apple> result = new ArrayList<Apple>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
    
    filterApples(apples,(Apple apple) -> "green".equals(apple.getColor()) && apple.getWeight() > 150);
```
#### lambda表达式：
* 它没有名称，但是它有参数列表，函数主体，返回类型，抛出异常列表。
* 源自：学术界描述计算的λ演算法。λ演算之通用在于，任何一个可计算函数都能用这种形式来表达和求值。
#### java.util.stream.Stream
* filter 接受lambda，从流中排除某些元素。
* map 将元素转换成其它形式或提取信息
* limit 截断流
* collect 将流转换成其它形式
#### 集合和流之间的差别：
* 集合中的每个元素都是存在于内存中的，元素都需要计算出来才能成为集合的一部分。
* 流按需生成，需求驱动制造，甚至是实时制造。
* 流是概念上固定的数据结构，你不能添加或者删除。
##### 中间流
    filter distinct skip limit map flatMap sorted 
##### 终端
    anyMatch 流noneMatch allMatch findAny findFirst forEach collect reduce count
##### 收集器
```
    //数据源
    transactions
    // 同类Currency，收集器
    Map<Currency, List<Transaction>> transactionByCurrentcies = new HashMap<>();
    //遍历数据源
    for(Transaction transaction : transactions){
        //获取当前交易的类型
        Currency currency = transaction.getCurrency();
        // 查询收集器有没有当前这种交易类型
        List<Transaction> transactionForCurrency = transactionByCurrentcies.get(currency);
        if(transactionForCurrency == null){
            // 无，创建一个
            transactionForCurrency = new ArrayList<>();
            // 把新建类型添加收集器
            transactionByCurrentcies.put(currency, transactionForCurrency);
        }
        // 为收集器某一类型的List添加元素
        transactionForCurrency.add(transaction);
    } 
    java8写法
    Map<Currency, List<Transaction>> transactionByCurrentcies = 
        transactions.stream().collect(groupingBy(Transaction::getCurrency));
```
# [java8一偏比较详细的文章](https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/)