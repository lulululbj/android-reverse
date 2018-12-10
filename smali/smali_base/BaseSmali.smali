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
    .registers 3

    .prologue
    .line 10
    const/4 v0, 0x1

    .line 11
    const/high16 v1, 0x3fc00000    # 1.5f

    .line 12
    int-to-float v0, v0

    add-float/2addr v0, v1

    return v0
.end method

.method private bool(ZZZ)Z
    .registers 5

    .prologue
    .line 35
    if-eqz p1, :cond_4

    if-nez p2, :cond_6

    :cond_4
    if-eqz p3, :cond_8

    :cond_6
    const/4 v0, 0x1

    :goto_7
    return v0

    :cond_8
    const/4 v0, 0x0

    goto :goto_7
.end method

.method private div()I
    .registers 2

    .prologue
    .line 28
    .line 30
    const/4 v0, 0x1

    .line 31
    return v0
.end method

.method private loop()V
    .registers 3

    .prologue
    .line 47
    const/4 v0, 0x0

    :goto_1
    const/16 v1, 0xa

    if-ge v0, v1, :cond_d

    .line 48
    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;

    invoke-virtual {v1, v0}, Ljava/io/PrintStream;->println(I)V

    .line 47
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    .line 50
    :cond_d
    return-void
.end method

.method private mul()D
    .registers 5

    .prologue
    .line 22
    const/high16 v0, 0x3fc00000    # 1.5f

    .line 23
    const-wide/high16 v2, 0x4000000000000000L    # 2.0

    .line 24
    float-to-double v0, v0

    mul-double/2addr v0, v2

    return-wide v0
.end method

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

.method private sub()D
    .registers 5

    .prologue
    .line 16
    const/4 v0, 0x1

    .line 17
    const-wide/high16 v2, 0x4004000000000000L    # 2.5

    .line 18
    int-to-double v0, v0

    sub-double/2addr v0, v2

    return-wide v0
.end method

.method private test()V
    .registers 1

    .prologue
    .line 4
    .line 6
    return-void
.end method
