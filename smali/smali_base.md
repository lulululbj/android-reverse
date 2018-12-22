# Smali —— 基本语法

通过上一篇 [Smali 语法解析——Hello World](https://juejin.im/post/5c093fd751882535422e4f05) 的学习，了解了 `Smali` 文件的基本格式。这一篇从最基本的数学运算，条件判断，循环等开始，更加详细的了解 Smali 语法。

## 数学运算

### 加法

先看源文件：

```java
public class BaseSmali {
    private float add() {
        int a = 1;
        float b = 1.5f;
        return  a + b;
    }
}

```

通过 `javac` `dx` 和 `baksmali` 工具生成对应的 smali 文件，具体方法在  [上一篇](https://juejin.im/post/5c093fd751882535422e4f05) 中有所介绍。我们看一下生成的 smali 文件：



```java
.class public LBaseSmali;
.super Ljava/lang/Object;
.source "BaseSmali.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private add()F
    .registers 3 // 使用 3 个寄存器

    .prologue
    .line 5
    const/4 v0, 0x1 // 将 0x1 放入 v0

    .line 6
    const/high16 v1, 0x3fc00000    # 1.5f 将 1.5f 放入 v1

    .line 7
    int-to-float v0, v0 // 将 v0 中的 int 值强转为 float 再存入 v0

    add-float/2addr v0, v1 // 将 v0 和 v1 中的值相加再存入 v0

    return v0 // 返回 v0 中的值
.end method
```



代码逻辑很简单，可以看到 `int` 值和 `float` 值相加的过程中会先将 `int` 值强转为 `float`，再进行加法。这里用到了数据定义，强转，加法三种 smali 语法。

#### 数据定义指令

Dalvik 虚拟机中每个寄存器都是 32 位的。`int` 等 4字节表示的数据类型一个寄存器就可以表示，而 `double` 等 64 位的数据类型则需要两个寄存器来表示。数据定义指令用到的基本字节码是 `const`，一般带 `-wide` 后缀表示的是 64 位数据，不带 `-wide` 后缀则是 32 位数据。上面的例子中定义了 两种基本数据类型。 `const/4 v0, 0x1`表示将数值 0x1 扩展为 32 位之后赋给寄存器 v0。`const/high16 v1, 0x3fc00000` ，表示将 `0x3fc00000` 右边零扩展至 32 位赋给寄存器 v1。`0x3fc00000` 是 `1.5f` 在内存中的表示，如果你了解 `float` 数值在内存中的表示方法的话，就会理解这里为什么要右边零扩展了。不理解的话可以阅读我的文章，[先挖一个坑吧，还没有写]() 。下面介绍一些常见的数据定义指令（来自[官网](https://source.android.google.cn/devices/tech/dalvik/dalvik-bytecode)）：

|                   语法                    |                          参数                          |                             说明                             |
| :---------------------------------------: | :----------------------------------------------------: | :----------------------------------------------------------: |
|              const/4 vA, #+B              |    `A:` 目标寄存器（8 位） `B:` 有符号整数（8 位）     |    将给定的字面值（符号扩展为 32 位）移到指定的寄存器中。    |
|           const/16 vAA, #+BBBB            |    `A:` 目标寄存器（8 位） `B:` 有符号整数（16 位）    |    将给定的字面值（符号扩展为 32 位）移到指定的寄存器中。    |
|           const vAA, #+BBBBBBBB           |      `A:` 目标寄存器（8 位） `B:` 任意 32 位常量       |              将给定的字面值移到指定的寄存器中。              |
|       const/high16 vAA, #+BBBB0000        |    `A:` 目标寄存器（8 位） `B:` 有符号整数（16 位）    |    将给定的字面值（右零扩展为 32 位）移到指定的寄存器中。    |
|         const-wide/16 vAA, #+BBBB         |    `A:` 目标寄存器（8 位） `B:` 有符号整数（16 位）    |   将给定的字面值（符号扩展为 64 位）移到指定的寄存器对中。   |
|       const-wide/32 vAA, #+BBBBBBBB       |    `A:` 目标寄存器（8 位） `B:` 有符号整数（32 位）    |   将给定的字面值（符号扩展为 64 位）移到指定的寄存器对中。   |
|    const-wide vAA, #+BBBBBBBBBBBBBBBB     | `A:` 目标寄存器（8 位） `B:` 任意双字宽度（64 位）常量 |             将给定的字面值移到指定的寄存器对中。             |
| const-wide/high16 vAA, #+BBBB000000000000 |    `A:` 目标寄存器（8 位） `B:` 有符号整数（16 位）    |   将给定的字面值（右零扩展为 64 位）移到指定的寄存器对中。   |
|       const-string vAA, string@BBBB       |        `A:` 目标寄存器（8 位） `B:` 字符串索引         |     将通过给定的索引获取的字符串引用移到指定的寄存器中。     |
|  const-string/jumbo vAA, string@BBBBBBBB  |        `A:` 目标寄存器（8 位） `B:` 字符串索引         |     将通过给定的索引获取的字符串引用移到指定的寄存器中。     |
|        const-class vAA, type@BBBB         |         `A:` 目标寄存器（8 位） `B:` 类型索引          | 将通过给定的索引获取的类引用移到指定的寄存器中。如果指定的类型是原始类型，则将存储对原始类型的退化类的引用。 |



#### 强转指令

强转的语法比较简单，直接看官网截图:

![](/img/int-to-float.png)

除了常见的基本类型之间的强制转换，还有 `neg` 求补，`not` 求反，也同样适用这一语法。

#### 加法指令

```
add-float/2addr v0, v1 // 将 v0 和 v1 中的值相加再存入 v0
```

加法指令还有一种三个参数的写法，如下所示：

```
add-float v0, v1, v2 // 将 v1 和 v2 中的值相加再存入 v0
```

这里的 `float` 可以替换为其他基本数据类型， `add` 也可以替换为其他数学运算操作。同样，还是用过官网截图来了解一下支持的运算语法：

![](/img/smali_math.png)

一个加法延伸出来不少知识，看到这里，不知道你有没有一个疑问，想想最初的 java 源代码：

```java
private float add() {
    int a = 1;
    float b = 1.5f;
    return  a + b;
}
```

代码中定义了两个变量 `a` 和 `b`，可是 smali 中的这两个变量呢？虚拟机中的编译器，不论是 JVM 还是 DVM，都会竭尽所能的在编译阶段对代码进行优化以提升运行速度。`a` 和 `b` 这两个变量在 `add()` 方法中并不是必须存在的，所以 DVM 不会浪费时间和空间再去申明这两个变量。如果变量 `b` 也是 `int` 类型的话，DVM 甚至连加法都会省略，直接返回 `a+b` 的数值，大家可以动手试一下。那么，如果在学习过程中想了解每一句代码的 smali 指令该怎么办呢？使用 `IDEA` 的 `java2smali` 插件，就不会存在这些优化了。



### 减法

源代码：

```java
    private double sub(){
        int a = 1;
        double b = 2.5;
        return a-b;
    }
```



Smali 代码：

```java
.method private sub()D
    .registers 5

    .prologue
    .line 11
    const/4 v0, 0x1

    .line 12
    const-wide/high16 v2, 0x4004000000000000L    # 2.5

    .line 13
    int-to-double v0, v0

    sub-double/2addr v0, v2

    return-wide v0
.end method
```

减法指令用 `sub` 表示。

另外这里要注意的是 `const-wide` 和 `return-wide`，添加了 `-wide` 后缀的操作符表示的是 `64` 位数据类型。上面例子中定义了 `double` 类型常量，返回值也是 `double` 类型。

### 乘法

源代码：

```java
    private double mul(){
        float a = 1.5f;
        double b = 2;
        return a * b;
    }
```



Smali 代码：

```
.method private mul()D
    .registers 5

    .prologue
    .line 17
    const/high16 v0, 0x3fc00000    # 1.5f

    .line 18
    const-wide/high16 v2, 0x4000000000000000L    # 2.0

    .line 19
    float-to-double v0, v0

    mul-double/2addr v0, v2

    return-wide v0
.end method
```

乘法指令用 `mul` 表示



### 除法

源代码：

```java
    private int div() {
        int a = 3;
        int b = 2;
        int c = a / b;
        return c;
    }
```

Smali 代码：

```java
.method private div()I
    .registers 2

    .prologue
    .line 23
    .line 25
    const/4 v0, 0x1

    .line 26
    return v0
.end method
```

显然，编译器对这段代码进行了优化，提前计算了 `3/2` ，在 `div()` 方法中直接返回结果。我们在通过 `java2smali` 插件看一下未经优化的 Smali 代码：

```java
.method private div()I
    .registers 4

    .prologue
    .line 28
    const/4 v0, 0x3

    .line 29
    .local v0, "a":I
    const/4 v1, 0x2

    .line 30
    .local v1, "b":I
    div-int v2, v0, v1

    .line 31
    .local v2, "c":I
    return v2
.end method
```

可以看到除法指令用 `div` 表示



### 布尔运算

源代码：

```java
    private boolean bool(boolean a, boolean b,boolean c) {
        return a && b || c;
    }
```

Smali 代码：

```java
.method private bool(ZZZ)Z
    .registers 5

    .prologue
    .line 35
    if-eqz p1, :cond_4 // 如果 p1 = 0, 跳至 cond_4 处

    if-nez p2, :cond_6 // 如果 p2 != 0，跳至 cond_6 处

    :cond_4
    if-eqz p3, :cond_8 // 如果 p3 = 0，跳至 cond_8 处

    :cond_6
    const/4 v0, 0x1 // 将 0x1 赋给 v0

    :goto_7
    return v0 // 返回 v0 的值

    :cond_8
    const/4 v0, 0x0 // 将 0x1 赋给 v0

    goto :goto_7 // 跳至 goto_7 处
.end method
```

布尔运算在 `smali` 中被转化为一系列的条件判断加指令跳转。上面例子中使用了两种跳转指令，`if` 判断之后的条件跳转和 `goto` 表示的无条件跳转，表示从当前地址跳转到指定的偏移处。条件判断指令在后面会具体罗列。

好像还没提到过参数寄存器，这里用到三个参数寄存器，`p1`  `p2`  `p3` ，再加上一个局部变量寄存器 `v0`，看起来只用了四个寄存器，但是 `.registers 5` 却告诉我们这个方法用了五个寄存器，往上翻翻之前的 Smali 代码，你会发现，都无缘无故 “消失” 了一个寄存器。其实那是 `p0` 寄存器，函数被调用时会传入一个隐式的对当前对象的引用，存储在 `p0` 寄存器当中。

### 其他运算

源代码：

```java
    private void other(int a) {
        int or = a | 1;
        int and = a & 1;
        int right = a >> 2;
        int left = a << 2;
        int mod = a % 2;
    }
```

Smali 代码：

```java
.method private other(I)V
    .registers 3

    .prologue
    .line 39
    or-int/lit8 v0, p1, 0x1

    .line 40
    and-int/lit8 v0, p1, 0x1

    .line 41
    shr-int/lit8 v0, p1, 0x2

    .line 42
    shl-int/lit8 v0, p1, 0x2

    .line 43
    rem-int/lit8 v0, p1, 0x2

    .line 44
    return-void
.end method
```

`or`  或 ，`and` 与 ， `shr` 右移 ， `shl` 左移 ， `rem` 取模



## 条件判断

条件判断在之前的布尔运算中已经演示过，这里罗列一些具体的判断指令：

|        指令         |            说明            |
| :-----------------: | :------------------------: |
| if-eq vA, vB, +CCCC | 如果 vA=vB，跳转指定偏移量 |
|        if-ne        |          vA != vB          |
|        if-lt        |          vA < vB           |
|        if-ge        |          vA >= vB          |
|        if-gt        |          vA > vB           |
|        if-le        |          vA <= vB          |
|  if-eqz vA, +BBBB   |           vA = 0           |
|       if-nez        |          vA != 0           |
|       if-ltz        |           vA < 0           |
|       if-gez        |          vA >= 0           |
|       if-gtz        |           vA > 0           |
|       if-lez        |          vA <= 0           |



## 循环

源代码：

```java
    private void loop(){
        for (int i=0;i<10;i++){
            System.out.println(i);
        }
    }
```



Smali 代码：

```java
.method private loop()V
    .registers 3

    .prologue
    .line 47
    const/4 v0, 0x0 // v0 = 0

    :goto_1
    const/16 v1, 0xa // v1 = 10

    if-ge v0, v1, :cond_d // 如果 v0 >= v1，跳至 cond_d 处

    .line 48
    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {v1, v0}, Ljava/io/PrintStream;->println(I)V

    .line 47
    add-int/lit8 v0, v0, 0x1 // v0++

    goto :goto_1 // 跳转至 goto_1 处

    .line 50
    :cond_d
    return-void
.end method
```

显然，循环也是通过条件判断和指令跳转来完成的。



本节中学习了 Smali 的数学运算，条件判断和循环的语法，也基本涵盖了大部分的 Smali 基本语法。下一篇学习 Smali 中类的用法。
