.class LOuter$StaticInner;
.super Ljava/lang/Object;
.source "Outer.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LOuter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "StaticInner"
.end annotation


# direct methods
.method private constructor <init>()V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(LOuter$1;)V
    .registers 2

    .prologue
    .line 9
    invoke-direct {p0}, LOuter$StaticInner;-><init>()V

    return-void
.end method

.method static synthetic access$300(LOuter$StaticInner;)V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, LOuter$StaticInner;->staticIn()V

    return-void
.end method

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
