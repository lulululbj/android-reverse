.class public LBMW;
.super LCar;
.source "BMW.java"

# interfaces
.implements LIFly;


# instance fields
.field private brand:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .registers 2

    .prologue
    .line 1
    invoke-direct {p0}, LCar;-><init>()V

    .line 3
    const-string v0, "BMW"

    iput-object v0, p0, LBMW;->brand:Ljava/lang/String;

    return-void
.end method

.method public static main([Ljava/lang/String;)V
    .registers 2

    .prologue
    .line 16
    new-instance v0, LBMW;

    invoke-direct {v0}, LBMW;-><init>()V

    .line 17
    invoke-virtual {v0}, LBMW;->run()V

    .line 18
    invoke-virtual {v0}, LBMW;->fly()V

    .line 19
    return-void
.end method


# virtual methods
.method public fly()V
    .registers 3

    .prologue
    .line 12
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v1, "I can fly!"

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 13
    return-void
.end method

.method run()V
    .registers 4

    .prologue
    .line 7
    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v2, p0, LBMW;->brand:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " run!"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 8
    return-void
.end method
