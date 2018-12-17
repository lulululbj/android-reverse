.class LOuter$Inner;
.super Ljava/lang/Object;
.source "Outer.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = LOuter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "Inner"
.end annotation


# instance fields
.field final synthetic this$0:LOuter;


# direct methods
.method private constructor <init>(LOuter;)V
    .registers 2

    .prologue
    .line 3
    iput-object p1, p0, LOuter$Inner;->this$0:LOuter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(LOuter;LOuter$1;)V
    .registers 3

    .prologue
    .line 3
    invoke-direct {p0, p1}, LOuter$Inner;-><init>(LOuter;)V

    return-void
.end method

.method static synthetic access$100(LOuter$Inner;)V
    .registers 1

    .prologue
    .line 3
    invoke-direct {p0}, LOuter$Inner;->in()V

    return-void
.end method

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
