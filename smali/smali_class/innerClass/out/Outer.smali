.class public LOuter;
.super Ljava/lang/Object;
.source "Outer.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        LOuter$StaticInner;,
        LOuter$Inner;
    }
.end annotation


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static main([Ljava/lang/String;)V
    .registers 4

    .prologue
    const/4 v2, 0x0

    .line 17
    new-instance v0, LOuter;

    invoke-direct {v0}, LOuter;-><init>()V

    .line 19
    new-instance v1, LOuter$Inner;

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    invoke-direct {v1, v0, v2}, LOuter$Inner;-><init>(LOuter;LOuter$1;)V

    .line 20
    # invokes: LOuter$Inner;->in()V
    invoke-static {v1}, LOuter$Inner;->access$100(LOuter$Inner;)V

    .line 22
    new-instance v0, LOuter$StaticInner;

    invoke-direct {v0, v2}, LOuter$StaticInner;-><init>(LOuter$1;)V

    .line 23
    # invokes: LOuter$StaticInner;->staticIn()V
    invoke-static {v0}, LOuter$StaticInner;->access$300(LOuter$StaticInner;)V

    .line 25
    new-instance v0, Ljava/lang/Thread;

    new-instance v1, LOuter$1;

    invoke-direct {v1}, LOuter$1;-><init>()V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 30
    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 31
    return-void
.end method


# virtual methods
.method public test()V
    .registers 3

    .prologue
    .line 34
    new-instance v0, Ljava/lang/Thread;

    new-instance v1, LOuter$2;

    invoke-direct {v1, p0}, LOuter$2;-><init>(LOuter;)V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 39
    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 40
    return-void
.end method
