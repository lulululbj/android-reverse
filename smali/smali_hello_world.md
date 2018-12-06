# Smali 文件解析——Hello World

通过上一篇文章的学习，我们都知道了 Java 代码是如何执行的。Java 编译器将 `.java` 源文件编译为 `.class` 字节码文件，`JVM`（Java虚拟机）将字节码解释为机器代码最终在目标机器上执行。而在 Android 中，代码是如何执行的呢 ？首先看下面这张图：

![JVM VS DVM](http://upload-images.jianshu.io/upload_images/2466095-37c05f531dbb640d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这里的 `DVM` 指的是 `DalviK VM ` 。在 Android 中，Java 类被打包生成固定格式的 `DEX` 字节码文件，`DEX` 字节码经过 `Dalvik` 或者 `ART` 转换为原生机器码，进而执行。`DEX` 字节码是独立于设备架构的。

Dalvik 是一个基于 JIT（即时）的编译引擎。使用 Dalvik 是有缺点的，因此从 Android4.4（kitkat）开始引入了 ART 作为运行时，从 Android5.0（Lollopop）开始就完全替代了 Dalvik。Android7.0 增加了一个即时型编译器，给 Android 运行时（ART）提供了代码分析，提升了 Android app运行时的表现。关于 Dalvik 和 Art 的具体分析，可以阅读我之前的一篇译文 [走近 Android 运行时： DVM VS ART](https://www.jianshu.com/p/a8fbe290bb4b)  。

上图中还可以看到，JVM 的执行是 `Stack-based` , 基于栈帧的，而 Dalvik 虚拟机是 `Register-based`，基于寄存器的。这点需要记住，在后面的 Smali 语法分析中很重要。说到 Smali，那么什么是 Smali呢？用过 `apktool` 的朋友肯定都不陌生，`apktool d xxx.apk` 反编译 apk 之后，生成的文件夹之中会有 smali 文件夹，里面就包含了该 apk 的所有代码，均以 `.smali`  文件形式保存。关于 Smali ，在 Android 官网中并无相关介绍，它应该出自 `JesusFreke` 的开源项目 [smali](https://github.com/JesusFreke/smali)，在 README 中是这样介绍的：

```
smali/baksmali is an assembler/disassembler for the dex format used by dalvik, Android's Java VM implementation.
The syntax is loosely based on Jasmin's/dedexer's syntax, and supports the full functionality of the dex format (annotations, debug info, line info, etc.)
```

大致翻译一下， `smali/baksmali` 是针对 `dalvik` 使用的 `dex` 格式的汇编/反汇编器。它的语法基于 `Jasmin's/dedexer`，支持 `dex` 格式的所有功能（注释，调试信息，行信息等等）。因此我们可以认为 smali 和 Dalvik 字节码文件是等价的。事实上，`Apktool` 也正是调用这个工程生成的 jar 包来进行反编译生成 smali 代码的。对生成的 smali 代码进行修改之后再重打包，就可以修改 apk 中的逻辑了。因此，能阅读 smali 代码对我们进行 android 逆向十分重要。



## Smali 文件生成

下面仍然以之前的 `Hello.java` 为例：

```java
public class Hello {

    private static String HELLO_WORLD = "Hello World!";

    public static void main(String[] args) {
        System.out.println(HELLO_WORLD);
    }
}
```

`javac` 生成 `Hello.class` 文件，然后通过 Sdk 自带的 `dx` 工具生成 `Hello.dex` 文件，命令如下：

```
dx --dex --output=Hello.dex  Hello.class
```

`dx` 工具位于 Sdk 的 `build-tools` 目录下，可添加至环境变量方便调用。`dx` 也支持多 Class 文件生成 dex。

`dex` 转 `smali` 使用的工具是 `baksmali.jar` ，最新版本是 `2.2.5` ，[点击下载](https://bitbucket.org/JesusFreke/smali/downloads/)，使用命令如下：

```
java -jar baksmali-2.2.5.jar d hello.dex
```

执行完成后，会在当前目录生成 `out` 文件夹，文件夹内包含生成的 `smali` 文件。

## Smali 详细解析

我们首先看一下生成的 `Hello.smali` 文件内容：

```
.class public LHello;
.super Ljava/lang/Object;
.source "Hello.java"


# static fields
.field private static HELLO_WORLD:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 3
    const-string v0, "Hello World!"

    sput-object v0, LHello;->HELLO_WORLD:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static main([Ljava/lang/String;)V
    .registers 3

    .prologue
    .line 6
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    sget-object v1, LHello;->HELLO_WORLD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 7
    return-void
.end method
```

### 文件头

首先看一下文件头部分：

```
.class public LHello;       // 类名
.super Ljava/lang/Object;   // 父类名
.source "Hello.java"        // 源文件名称
```

`.class` 后面是 访问修饰符和当前类，这里类名用 `LHello` 表示。那么这个 `L` 代表什么呢？其实之前的 Class 文件中也出现过这种表示方法，JVM 的字节码指令和 Dalvik 的字节码指令有很多地方都是类似的。Java 中分为基本类型和引用类型，DalviK 对这两种类型分别有不同的描述方法。对于基本类型和 Void 类型，都是用一个大写字母表示。对于引用类型，使用字母 `L` 加上对象类型的全限定名来表示。具体规则如下表所示：

| Java 类型 | 类型描述符 |
| --------- | ---------- |
| char      | C          |
| byte      | B          |
| short     | S          |
| int       | I          |
| long      | J          |
| float     | F          |
| double    | D          |
| boolean   | Z          |
| void      | V          |
| 对象      | L          |
| 数组      | [          |

基本类型的表示很简单，int 用 `I` 表示即可。对象的表示，如上图中父类 Object 的表示方法 `Ljava/lang/Object;`，再比如 String 类型，就用 `Ljava/lang/String` 表示。

对于数组，DalviK 有特殊的表示方法 `[` 后面跟上数组元素的类型。`int[]` 的表示方式就是 `[I`, `String[]` 的表示方法是 `[Ljava/lang/String;`。二维数组用 `[[` 表示，`[[Ljava/lang/String` 就是指 `String[][]`，以此类推。

##  字段表示

```java
# static fields
.field private static HELLO_WORLD:Ljava/lang/String;
```

smali 中的字段以 `.field` 开头，并有 `# static field(静态字段)` 或者 `# instance field(实例字段)` 的注释。`.field` 之后分别是 访问修饰符，字段名称，冒号以及字段类型描述符。这句 smali 就声明了一个 `String` 类型名称为 `HELLO_WORLD` 的私有静态字段。

## 方法表示

smali 中的方法以 `.method` 开头。`Hello.smali` 中包含了三个方法，`clinit` , `init` 和 `main`  方法。`main` 方法是我们自己编写的，而 `clinit` 和 `init` 方法则是 javac 编译时生成的。下面进行逐一分析:


### clinit

```java
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 3
    const-string v0, "Hello World!"

    sput-object v0, LHello;->HELLO_WORLD:Ljava/lang/String;

    return-void
.end method
```

`clinit` 方法会进行静态变量的初始化，静态代码块的执行等操作，该方法在类被加载的时候调用。逐行分析该方法的执行逻辑：

* `.registers 1 :` 该方法需要使用的寄存器数量。之前已经提到，DalviK VM 是基于寄存器的，字节码可以使用的虚拟寄存器个数可达 65536 个，每个寄存器 32 位，64 位的数据使用相邻两个寄存器表示。最终，所有的虚拟寄存器都会被映射到真实的物理寄存器上。一般情况下，我们使用字母 `v` 表示局部变量使用的寄存器，使用字母 `p` 表示参数所使用的寄存器，且局部变量使用的寄存器排列在前，参数使用的寄存器排列在后。这里就表示 `clinit` 方法仅使用了一个寄存器。

* `.prologue :` 表示逻辑代码的开始处

* `.line 3 :` 表示 java 源文件中的行数

* `const-string v0, "Hello World!"` : 将字符串 ` Hello World！` 的引用移到寄存器 `v0` 中。

* `sput-object v0, LHello;->HELLO_WORLD:Ljava/lang/String;` : 前缀 `s` 的 `sput` 和 `sget` 指令用于静态字段的读写操作。将寄存器 `v0` 存储的字符串引用赋值给 `HELLO_WORLD` 字段，结合上一句字节码，这里完成了静态变量 `HELLO_WORLD` 的赋值工作，也验证了 `clinit` 方法的确进行了静态变量的初始化。

* `return-void` : 表示该方法无返回值

* `.end method` : 表示方法执行结束

到这里，`clinit` 方法就执行结束了。下面分析 `init` 方法。

### init

```java
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
```

其余各项与 `clinit` 方法相同，我们直接看执行的代码逻辑：

```
invoke-direct {p0}, Ljava/lang/Object;-><init>()V
```

invoke-direct 用于调用非 static 直接方法（也就是说，本质上不可覆盖的实例方法，即 private 实例方法或构造函数）。显然，这里调用的是默认构造函数。

### main

```java
.method public static main([Ljava/lang/String;)V
    .registers 3

    .prologue
    .line 6
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    sget-object v1, LHello;->HELLO_WORLD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 7
    return-void
.end method
```

最后是 `main` 方法，从上述 smali 代码我们可以看到 `main` 方法使用了 3 个寄存器，无返回值（那是肯定的），执行的具体代码是下面三行：

```java
sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

sget-object v1, LHello;->HELLO_WORLD:Ljava/lang/String;

invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V
```

`sget` 的用法在 `clinit` 方法中解释过，表示静态字段的读取。第一句代码，获取类 `System` 的静态字段 `out`，其类型是 `Ljava/io/PrintStream`，并将其引用赋给寄存器 `v0`。第二句代码获取在 `clinit`
 方法中已经初始化的静态字段 `HELLO_WORLD`，并将其引用赋给寄存器 `v1`。第三句中使用了 `invoke-virtual` 指令，`invoke-virtual` 调用正常的虚方法（该方法不是 private、static 或 final，也不是构造函数），之后通常会跟上 `{}`，`{}`之中的第一个寄存器通常是指向当前实例对象，如 `v0` 就是指向 `System.out` 对象，后面的内容才是该方法真正的参数，如 `v1`。`{},` 之后就是要执行的方法的描述，如 `Ljava/io/PrintStream;->println(Ljava/lang/String;)V` ,指的就是 `PrintStream` 对象的 `println` 方法。综上，这三句字节码执行的就是 `System.out.println(HELLO_WORLD);`。

 到这里，`Hello.smali` 文件就解析完了。当然，我们在反编译过程中遇到的任何一个 smali 文件肯定都要比这个复杂的多。Android 官网也对 Dalvik 字节码的指令集进行了归纳，地址是 [https://source.android.google.cn/devices/tech/dalvik/dalvik-bytecode](https://source.android.google.cn/devices/tech/dalvik/dalvik-bytecode)。在阅读过程中遇到不熟悉的指令，都可以在这个页面进行查找。

 最后再介绍一个 `java` 转 `smali` 的快捷方式，在 `IDEA` 或者 `Android Studo` 中安装插件 `java2smali`，在 `Build` 菜单栏下会出现 `Compile to smali`选项，可以迅速将 java 代码转化成 smali 代码。在我们学习 smali 的过程中，碰到不确定的内容，可以先写好 java 代码，再转成 smali 代码进行对照学习。

最后贴一个完整的带注释的 `Hello.smali` 文件：

```
.class public LHello; 		// 类名
.super Ljava/lang/Object;   // 父类名
.source "Hello.java"		// 源文件名称


# static fields // 表示静态字段 private static String HELLO_WORLD
.field private static HELLO_WORLD:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V // clinit 方法
    .registers 1 // 使用一个寄存器 v0

    .prologue // 方法开始
    .line 3 // 源代码行数
    const-string v0, "Hello World!" // 将 "Hello World!"放入寄存器 v0

    // 静态字段赋值，将寄存器v0存储的值赋给 HELLO_WORLD 
    sput-object v0, LHello;->HELLO_WORLD:Ljava/lang/String; 

    return-void // 无返回值
.end method // 方法结束

.method public constructor <init>()V // init 方法
    .registers 1 // 使用一个寄存器

    .prologue // 方法开始
    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V // 调用构造方法

    return-void // 无返回值
.end method // 方法结束

.method public static main([Ljava/lang/String;)V // main 方法
    .registers 3 // 使用 3 个寄存器

    .prologue // 方法开始
    .line 6
    // 获取静态对象，System.out，其类型为 java.io.PrintStream，赋给 v0
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;
	// 获取静态对象， HELLO_WORLD,其类型为 java.lang.String，赋给 v1
    sget-object v1, LHello;->HELLO_WORLD:Ljava/lang/String;
	// 执行 v0 所存储的对象的 println() 方法，v1存储的是方法的参数
    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 7
    return-void // 无返回值
.end method // 方法结束

```



 下一篇简单学习一些常见语法的 smali 学习，比如数学运算，if-else,循环，接口，内部类等等。



