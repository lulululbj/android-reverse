# Smali —— 类

上一篇学习了 Smali 的数学运算，条件判断和循环，接下来学习类的基本使用，包括接口，抽象类，内部类等等。

直接上代码吧，抽象类 `Car.java` :

```java
public abstract class Car {

    protected String brand;

    abstract void run();
}
```

接口 `IFly.java` ：

```java
public interface IFly {
    void fly();
}
```

类 `BMW.java` ：

```java
public class BMW extends Car implements IFly{

    private String brand = "BMW";

    @Override
    void run() {
        System.out.println(brand + " run!");
    }

    @Override
    public void fly() {
        System.out.println("I can fly!");
    }

    public static void main(String[] args){
        BMW bmw=new BMW();
        bmw.run();
        bmw.fly();
    }
}
```

`javac` 编译之后使用 `dx` 生成 dex 文件，和以往不同的是，这次是三个 class 文件生成一个 dex，具体命令如下：
```
dx --dex --output=BMWCar.dex Car.class IFly.class BMW.class
```
最后再使用 `baksmali` 生成 Smali 文件：
```
baksmali d BMWCar.dex
```
可以看到在当前目录 `out` 文件夹下生成了三个文件 `Car.smali`  `IFly.smaii` 和 `BMW.smali`。下面逐一进行分析。

## 抽象类

`Car.smali` :

```java
.class public abstract LCar; // 表明为抽象类
.super Ljava/lang/Object;
.source "Car.java"


# instance fields
.field protected brand:Ljava/lang/String; // proteced String brand


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method abstract run()V // abstract void run()
.end method
```

抽象类的表示没有什么特殊的地方，第一行中会使用 `abstract` 来声明。

## 接口

`IFly.smali` ：

```java
.class public interface abstract LIFly; // 表明是接口
.super Ljava/lang/Object;
.source "IFly.java"


# virtual methods
.method public abstract fly()V // abstract void fly()
.end method
```

接口使用 `interface` 声明。从上面的 smali 代码也可以看到接口中的方法默认是 `abstract` 修饰的。

## 实现类
`BMW.smali` :

```java
.class public LBMW;
.super LCar;    // 父类是 Car
.source "BMW.java"

# interfaces
.implements LIFly; // 实现了接口 IFly


# instance fields
.field private brand:Ljava/lang/String; // priva String brand


# direct methods
.method public constructor <init>()V
    .registers 2

    .prologue
    .line 1
    invoke-direct {p0}, LCar;-><init>()V

    .line 3
    const-string v0, "BMW"

    iput-object v0, p0, LBMW;->brand:Ljava/lang/String; // String brand = "BMW";

    return-void
.end method

.method public static main([Ljava/lang/String;)V // main 方法
    .registers 2

    .prologue
    .line 16
    new-instance v0, LBMW; // 新建 BMW 对象，并将其引用存入 v0

    invoke-direct {v0}, LBMW;-><init>()V // 执行对象的构造函数

    .line 17
    invoke-virtual {v0}, LBMW;->run()V // 执行 run() 方法

    .line 18
    invoke-virtual {v0}, LBMW;->fly()V // 执行 fly() 方法

    .line 19
    return-void
.end method


# virtual methods
.method public fly()V  // fly() 方法
    .registers 3

    .prologue
    .line 12
    // 下面三行字节码执行了 System.out.println("I can fly!");
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "I can fly!"

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 13
    return-void
.end method

.method run()V  // run() 方法
    .registers 4

    .prologue
    .line 7
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream; // 获取 Sysem.out 对象

    new-instance v1, Ljava/lang/StringBuilder; // 新建 StringBuilder 对象，并将其引用存到 v1

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V // 初始化

    iget-object v2, p0, LBMW;->brand:Ljava/lang/String; // 获取当前类的 brand 对象

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder; // append()

    move-result-object v1 // 将上一步中执行 append() 返回的对象赋给 v1，这里指的是 StringBuilder 对象

    const-string v2, " run!"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder; // append()

    move-result-object v1 // 这里的 v1 存储的仍然是 StringBuilder 对象的引用

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String; // toString()

    move-result-object v1 // 这里 v1 存储的是 StringB.toString() 执行的结果

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V // 打印语句

    .line 8
    return-void
.end method
```

`BMW` 类中出现了几个还没遇到过的指令，逐一分析一下：

1. `invoke-direct {p0}, LCar;-><init>()V`

`p0` 存储的是当前类的引用，这句话表示执行当前类中 Car 对象的 init() 方法。

通用表示：

`invoke-kind {vC, vD, vE, vF, vG}, meth@BBBB`

表示调用指定的方法，之后通常会跟一句 `move-result*` 来获取方法的返回值。例如：

```
invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

move-result-object v1
```

第一句中执行了 `StringBuilder.toString()` 方法，返回值是一个 `String` 对象，紧接着后面的 `move-result-object` 将返回的 `String` 对象引用存在了 `v1` 中。

`invoke-virtual` 指调用正常的虚方法（不是 private,static,final，也不是构造函数）。除此之外，还有一些方法调用指令，在下面的表格中列举出来：



|      指令       |                      说明                      |
| :-------------: | :---------------------------------------------: |
| invoke-virtual | 调用正常的虚方法 |
| invoke-super | 调用最近超类的虚方法 |
| invoke-direct | 调用 private 方法或构造函数 |
| invoke-static | 调用 static 方法 |
| invoke-interface | 调用 interface 方法 |

2. `iput-object v0, p0, LBMW;->brand:Ljava/lang/String;`

`v0` 中存储的是 `BMW` 字符串，赋给 `p0` 的 brand 字段。

通用用法：

```
iinstanceop vA, vB, field@CCCC
```

`vA` 可以是源寄存器，也可以是目的寄存器。当使用 `iput` 命令时， `vA` 就是源寄存器，使用 `iget` 命令时， `vA` 就是目的寄存器。

此命令还有一个同样用法的变种 `sput` 和 `sget`，从名字就可以看出来是对静态字段的操作，`i` 是对实例字段的操作。

3. `new-instance v1, Ljava/lang/StringBuilder;`

创建一个 StringBuilder 对象，并将其引用存在 `v1` 寄存器。

通用用法：

```
new-instance vAA, type@BBBB
```

注意这里的 `type` 不能是数组类型。数组使用的是 `new-array` 指令：

```
new-array vA, vB, type@CCCC
```

从 `run()` 方法的 smali 代码中我们也学习到了一个小知识点。我们都知道在 Java 中，使用 `=` 进行字符串拼接是很低效的，`run()` 方法中执行的是 `System.out.println(brand + " run!");`，然而虚拟机并没有傻傻的使用 `=` 去拼接，而是自动使用 `StringBuilder` 去拼接，提高运行效率。

## 内部类

内部类大家应该都不陌生，在 Android 开发中使用最多的要数匿名内部类了，除此之后，还有静态内部类，成员内部类等。下面的 `Outer.java` 中便使用了这三种内部类：

```java
public class Outer {

    // 成员内部类
    private class Inner {
        private void in() {
            System.out.println("I am inner class.");
        }
    }

    // 静态内部类
    private static class StaticInner {
        private void staticIn() {
            System.out.println("I am static inner class.");
        }
    }

    public static void main(String[] args) {

        Outer outer = new Outer();

        Inner inner = outer.new Inner();
        inner.in();

        StaticInner staticInner = new StaticInner();
        staticInner.staticIn();

        // 匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}
```

还记得内部类的使用方法吗？静态内部类可以直接使用 `new` 关键字进行实例化，如 `new StaticInner()`。而成员内部类则不行，必须通过外部类来初始化，如 `out.new Inner()`。`javac` 编译之后会生成如下四个 `class` 文件：

* `OUter.class`
* `Outer$Inner.clss`
* `Outer$StaticInner.class`
* `Outer$1.class`

同样，使用 `baksmali` 反汇编之后也会生成四个 `smali` 文件。

首先来看外部类 `Outer.smali`：

```java
.class public LOuter;
.super Ljava/lang/Object;
.source "Outer.java"


# annotations 系统自动添加的注解，表示内部类列表
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        LOuter$StaticInner;,
        LOuter$Inner;
    }
.end annotation


# direct methods
.method public constructor <init>()V  // 构造函数
    .registers 1

    .prologue
    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static main([Ljava/lang/String;)V // main() 方法
    .registers 4

    .prologue
    const/4 v2, 0x0

    .line 17
    new-instance v0, LOuter; // 创建外部类 Outer 对象，存入 v0

    invoke-direct {v0}, LOuter;-><init>()V // 执行 Outer 对象的构造函数

    .line 19
    new-instance v1, LOuter$Inner; // 创建内部类 Outer$Inner 对象，存入 v1

    // 获取外部类对象的引用
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    // 执行内部类 Out$Inner 的构造函数，注意这里执行的不是无参构造，后面具体说明
    invoke-direct {v1, v0, v2}, LOuter$Inner;-><init>(LOuter;LOuter$1;)V

    .line 20
    # invokes: LOuter$Inner;->in()V
    // 执行 Outer$Inner 的 access$100() 方法，这个方法是自动生成的，
    // 实际执行的就是 in() 方法
    invoke-static {v1}, LOuter$Inner;->access$100(LOuter$Inner;)V

    .line 22
    new-instance v0, LOuter$StaticInner; // 创建静态内部类 Out$StaticInner 对象

    // 执行静态内部类的构造函数，这里也是有参构造
    invoke-direct {v0, v2}, LOuter$StaticInner;-><init>(LOuter$1;)V

    .line 23
    # invokes: LOuter$StaticInner;->staticIn()V
    // 执行 Outer$StaticInner 的 access$300() 方法，这个方法是自动生成的，
    // 实际执行的就是 staticIn() 方法
    invoke-static {v0}, LOuter$StaticInner;->access$300(LOuter$StaticInner;)V

    .line 25
    new-instance v0, Ljava/lang/Thread; // 创建 Thread 对象

    new-instance v1, LOuter$1; // 创建 Out$1 对象

    invoke-direct {v1}, LOuter$1;-><init>()V // 执行 Out$1 对象的构造函数

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V // 执行 Thread 对象的构造函数

    .line 30
    invoke-virtual {v0}, Ljava/lang/Thread;->start()V // 执行 Thread.start() 方法

    .line 31
    return-void
.end method
```

看完外部类 `Outer` 的 smali 代码，我们发现每个内部类初始化时执行的都是有参构造，但是我们并没有显示的声明任何有参构造。我们从内部类的 smali 代码中找找答案。

### 成员内部类

`Out$Inner.smali`：

```java
.class LOuter$Inner;
.super Ljava/lang/Object;
.source "Outer.java"


# annotations
# EnclosingClass 注解，系统自动生成，value 值代表其作用范围
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LOuter;
.end annotation

# InnerClass 注解，系统自动生成，表示内部类
.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2 // private
    name = "Inner"
.end annotation


# instance fields
# synthetic 表示 this$0 是“合成” 的，并非源码中就有的
.field final synthetic this$0:LOuter;


# direct methods
# 有参构造
.method private constructor <init>(LOuter;)V
    .registers 2

    .prologue
    .line 3
    iput-object p1, p0, LOuter$Inner;->this$0:LOuter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

# 有参构造
.method synthetic constructor <init>(LOuter;LOuter$1;)V
    .registers 3

    .prologue
    .line 3
    invoke-direct {p0, p1}, LOuter$Inner;-><init>(LOuter;)V

    return-void
.end method

# 编译器生成的 access$100() 方法
.method static synthetic access$100(LOuter$Inner;)V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, LOuter$Inner;->in()V // 调用 in() 方法

    return-void
.end method

// in() 方法
.method private in()V
    .registers 3

    .prologue
    .line 5
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "I am inner class."

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 6
    return-void
.end method
```

重点看一下成员内部类的有参构造函数：

```java
.method private constructor <init>(LOuter;)V
    .registers 2

    .prologue
    .line 3
    iput-object p1, p0, LOuter$Inner;->this$0:LOuter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
```

有参构造函数的参数是 `LOuter;`，`p0` 存储的是当前类的引用，`p1` 存储的是构造函数的参数，即外部类的引用。这个构造函数一共执行了两步：

1. 把外部类的引用赋值给 `this$0`
2. 执行内部类的 `init()` 方法

由此可以，成员内部类是持有外部类的引用的，这也就解释了为什么成员内部类可以调用外部类的属性和方法。

再回头看一下 `Outer.smali` 中成员内部类 `Outer$Inner` 的初始化过程：

```
const/4 v2, 0x0
invoke-direct {v1, v0, v2}, LOuter$Inner;-><init>(LOuter;LOuter$1;)V
```

发现调用的并不是上面那个构造函数，而是一个两个参数的构造函数：

```java
.method synthetic constructor <init>(LOuter;LOuter$1;)V
    .registers 3

    .prologue
    .line 3
    invoke-direct {p0, p1}, LOuter$Inner;-><init>(LOuter;)V

    return-void
.end method
```

这个构造函数中还是调用了 `init(LOuter;)` 函数，并且也没有使用 `LOuter$1` 参数。这个 `LOuter$1` 参数是什么呢？看到最后就知道，这是匿名内部类的引用。但是在实际调用中传入的是 `0x0`, 关于传递这个参数的意义，不知道有没有读者知道，可以讨论一下。

紧接着编译器为成员内部类自动生成了一个静态方法：

```java
.method static synthetic access$100(LOuter$Inner;)V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, LOuter$Inner;->in()V // 调用 in() 方法

    return-void
.end method
```

方法参数是 `p0` 存储的当前类的引用，然后通过 `p0` 直接调用 `in()` 方法。同样，在外部类中调用成员内部类的 `in()` 方法，是通过 `invoke-static` 调用这个静态方法来执行的，如下所示：

```java
# invokes: LOuter$Inner;->in()V
invoke-static {v1}, LOuter$Inner;->access$100(LOuter$Inner;)V
```

### 静态内部类

`Outer$StaticInner.smali`：

```java
.class LOuter$StaticInner;
.super Ljava/lang/Object;
.source "Outer.java"


# annotations
# EnclosingClass 注解，系统自动生成，value 值代表其作用范围
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LOuter;
.end annotation

# InnerClass 注解，系统自动生成，表示内部类
.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa # private static
    name = "StaticInner"
.end annotation


# direct methods
# 无参构造
.method private constructor <init>()V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

# 有参构造
.method synthetic constructor <init>(LOuter$1;)V
    .registers 2

    .prologue
    .line 9
    invoke-direct {p0}, LOuter$StaticInner;-><init>()V

    return-void
.end method

# 编译器生成的 static 方法
.method static synthetic access$300(LOuter$StaticInner;)V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, LOuter$StaticInner;->staticIn()V

    return-void
.end method

// staticIn() 方法
.method private staticIn()V
    .registers 3

    .prologue
    .line 11
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "I am static inner class."

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 12
    return-void
.end method
```

结构与成员内部类基本相似，有两点区别。

第一， `accessFlags` 的不同。在系统生成的 `InnerClass` 注解中有 `accessFlags` 值。成员内部类 `Outer$Inner` 值为 `0x2`，表示 `private`，静态内部类 `Outer$StaticInner` 值为 `0xa`，表示 `private static`。关于 `accessFlags` 的表示方式，在我的另一篇文章 [Class 文件格式详解](https://juejin.im/post/5c0932cee51d45090a1da07e) 中有具体介绍。

第二，静态内部类不持有外部类的引用。`Outer$StaticInner.smali` 中并没有定义 `this$0` 字段，有参构造中的参数也不包含外部类引用。所以，这也验证了静态内部类只能调用外部类的静态属性和静态方法。

外部类对静态内部类方法的调用也是自动生成了一个静态方法，再通过这个静态方法来调用，如下所示：

```
# invokes: LOuter$StaticInner;->staticIn()V
invoke-static {v0}, LOuter$StaticInner;->access$300(LOuter$StaticInner;)V
```

### 匿名内部类

`Outer$1.smali`：

```java
.class final LOuter$1; // 匿名内部类是 final 的
.super Ljava/lang/Object;
.source "Outer.java"

# interfaces
.implements Ljava/lang/Runnable; // 实现了 Runnable 接口


# annotations
# 作用域在 main() 方法中
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = LOuter;->main([Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = null
.end annotation


# direct methods
# 无参构造
.method constructor <init>()V
    .registers 1

    .prologue
    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
# run() 方法
.method public run()V
    .registers 3

    .prologue
    .line 28
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 29
    return-void
.end method
```

看起来并没有什么特别的地方，`run()` 方法中执行的 `System.out.println(Thread.currentThread().getName());` 也很容易看懂。只有一个无参构造。对，只有一个无参构造，如果你的 Java 基础学的还可以的话，应该记得匿名内部类会持有外部类的引用，可以这里为什么只有一个无参构造呢？别忘了，这里是 `main()` 方法，是 `static` 方法。静态方法中只能引用类中的静态属性。如果换成一个普通方法，生成的 smali 代码中肯定会有 `this$0` 字段和有参构造。在 `Outer.java` 中添加如下代码：

```java
    public void test(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
```

生成的 smali 代码中多了一个文件 `Outer$2.smali`：

```java
.class LOuter$2;
.super Ljava/lang/Object;
.source "Outer.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = LOuter;->test()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:LOuter; // 外部类引用


# direct methods
# 有参构造
.method constructor <init>(LOuter;)V
    .registers 2

    .prologue
    .line 34
    iput-object p1, p0, LOuter$2;->this$0:LOuter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 3

    .prologue
    .line 37
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 38
    return-void
.end method
```

注意看两处添加注释的地方，这就验证了匿名内部类持有外部类的引用的说法。

关于 Smali 的学习就到这里了，在这个过程中，也验证了一些我们曾经熟知的知识点，加深了我们对 Java 的理解。当然在 Android 逆向过程中，我们碰到的要比这里说的复杂的多，这就需要我们积累足够的经验了。下一篇，正式进入 Android 领域了，介绍一些常用的 Android 逆向工具。
