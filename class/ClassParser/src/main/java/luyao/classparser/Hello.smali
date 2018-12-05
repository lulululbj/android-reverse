.class public Lluyao/classparser/Hello;
.super Ljava/lang/Object;
.source "Hello.java"


# static fields
.field private static HELLO_WORLD:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 5
    const-string v0, "Hello World!"

    sput-object v0, Lluyao/classparser/Hello;->HELLO_WORLD:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static main([Ljava/lang/String;)V
    .registers 3
    .param p0, "args"    # [Ljava/lang/String;

    .prologue
    .line 8
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    sget-object v1, Lluyao/classparser/Hello;->HELLO_WORLD:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 9
    return-void
.end method
