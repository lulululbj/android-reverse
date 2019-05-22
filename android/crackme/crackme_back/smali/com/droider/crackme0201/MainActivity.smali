.class public Lcom/droider/crackme0201/MainActivity;
.super Landroid/app/Activity;
.source "MainActivity.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field private btn_answer:Landroid/widget/Button;

.field private btn_register:Landroid/widget/Button;

.field private edit_sn:Landroid/widget/EditText;

.field private edit_userName:Landroid/widget/EditText;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 18
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method

.method private checkSN(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 10  # 使用 10 个寄存器
    .param p1, "userName"   # Ljava/lang/String; 参数寄存器 p1 保存的是用户名 userName
    .param p2, "sn"    # Ljava/lang/String; 参数寄存器 p2 保存的是注册码 sn

    .prologue
    const/4 v7, 0x0 # 将 0x0 存入寄存器 v7

    .line 45
    if-eqz p1, :cond_0  # 如果 p1，即 userName 等于 0，跳转到 cond_0

    :try_start_0
    invoke-virtual {p1}, Ljava/lang/String;->length()I # 调用 userName.length()

    move-result v8  # 将 userName.length() 的执行结果存入寄存器 v8

    if-nez v8, :cond_1 # 如果 v8 不等于 0，跳转到 cond_1

    .line 69
    :cond_0
    :goto_0
    return v7

    .line 47
    :cond_1
    if-eqz p2, :cond_0  # 如果 p2，即注册码 sn 等于 0，跳转到 cond_0

    invoke-virtual {p2}, Ljava/lang/String;->length()I  # 执行 sn.length()

    move-result v8  # 将 sn.length() 执行结果存入寄存器 v8

    const/16 v9, 0x10 # 将 0x10 存入寄存器 v9

    if-ne v8, v9, :cond_0   # 如果 sn.length != 0x10 ，跳转至 cond_0

    .line 49
    const-string v8, "MD5"  # 将字符串 "MD5" 存入寄存器 v8

    # 调用静态方法 MessageDigest.getInstance("MD5")
    invoke-static {v8}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v1   # 将上一步方法的返回结果赋给寄存器 v1，这里是 MessageDigest 对象

    .line 50
    .local v1, "digest":Ljava/security/MessageDigest;
    invoke-virtual {v1}, Ljava/security/MessageDigest;->reset()V # 调用 digest.reset() 方法

    .line 51
    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B   # 调用 userName.getByte() 方法

    move-result-object v8   # 上一步得到的字节数组存入 v8

    invoke-virtual {v1, v8}, Ljava/security/MessageDigest;->update([B)V # 调用 digest.update(byte[]) 方法

    .line 52
    invoke-virtual {v1}, Ljava/security/MessageDigest;->digest()[B  # 调用 digest.digest() 方法

    move-result-object v0   # 上一步的执行结果存入 v0，是一个 byte[] 对象

    .line 53
    .local v0, "bytes":[B
    const-string v8, "" # 将字符串 "" 存入 v8

    # 调用 MainActivity 中的 toHexString(byte[] b,String s) 方法
    invoke-static {v0, v8}, Lcom/droider/crackme0201/MainActivity;->toHexString([BLjava/lang/String;)Ljava/lang/String;

    move-result-object v3   # 上一步方法返回的字符串存入 v3

    .line 54
    .local v3, "hexstr":Ljava/lang/String;
    new-instance v5, Ljava/lang/StringBuilder;  # 新建 StringBuilder 对象

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V    # 执行 StringBuilder 的构造函数

    .line 55
    .local v5, "sb":Ljava/lang/StringBuilder;   # 声明变量 sb 指向刚才创建的 StringBuilder 实例
    const/4 v4, 0x0 # v4 = 0x0

    .local v4, "i":I    # i = 0x0
    :goto_1 # for 循环开始
    invoke-virtual {v3}, Ljava/lang/String;->length()I  # 获取 hexstr 字符串的长度

    move-result v8  # v8 = hexstr.length()

    if-lt v4, v8, :cond_2   # 如果 v4 小于 v8，即 i < hexstr.length(), 跳转到 cond_2

    .line 58
    # 这里已经跳出 for 循环
    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6   # v6 = sb.toString()

    .line 63
    .local v6, "userSN":Ljava/lang/String;  # userSN = sb.toString()

    const-string v8, "SN"
    invoke-static {v8,v6}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    # userSN.equalsIgnoreCase(sn)
    invoke-virtual {v6, p2}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v8  # v8 = userSN.equalsIgnoreCase(sn)

    if-eqz v8, :cond_0 # 如果 v8 等于 0，跳转到 cond_0，即 userSN != sn

    .line 69
    const/4 v7, 0x1

    goto :goto_0    # 跳转到 goto_0，结束 checkSN() 方法并返回 v7

    .line 56
    .end local v6    # "userSN":Ljava/lang/String;
    :cond_2
    invoke-virtual {v3, v4}, Ljava/lang/String;->charAt(I)C # 执行 hexstr.charAt(i)

    move-result v8  # v8 = hexstr.charAt(i)

    # 调用 sb.append(v8)
    invoke-virtual {v5, v8}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0

    .line 55
    add-int/lit8 v4, v4, 0x2    # v4 自增 0x2，即 i+=2

    goto :goto_1    # 跳转到 goto_1，形成 循环

    .line 65
    .end local v0    # "bytes":[B
    .end local v1    # "digest":Ljava/security/MessageDigest;
    .end local v3    # "hexstr":Ljava/lang/String;
    .end local v4    # "i":I
    .end local v5    # "sb":Ljava/lang/StringBuilder;
    :catch_0
    move-exception v2

    .line 66
    .local v2, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v2}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    goto :goto_0
.end method

.method private checkSNfalse(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 11
    .param p1, "userName"    # Ljava/lang/String;
    .param p2, "sn"    # Ljava/lang/String;

    .prologue
    const/4 v8, 0x1

    const/4 v7, 0x0

    .line 74
    if-eqz p1, :cond_0

    :try_start_0
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v9

    if-nez v9, :cond_1

    .line 97
    :cond_0
    :goto_0
    return v7

    .line 76
    :cond_1
    const-string v9, "MD5"

    invoke-static {v9}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v1

    .line 77
    .local v1, "digest":Ljava/security/MessageDigest;
    invoke-virtual {v1}, Ljava/security/MessageDigest;->reset()V

    .line 78
    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v9

    invoke-virtual {v1, v9}, Ljava/security/MessageDigest;->update([B)V

    .line 79
    invoke-virtual {v1}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v0

    .line 80
    .local v0, "bytes":[B
    const-string v9, ""

    invoke-static {v0, v9}, Lcom/droider/crackme0201/MainActivity;->toHexString([BLjava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 81
    .local v3, "hexstr":Ljava/lang/String;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    .line 82
    .local v5, "sb":Ljava/lang/StringBuilder;
    const/4 v4, 0x0

    .local v4, "i":I
    :goto_1
    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result v9

    if-lt v4, v9, :cond_2

    .line 85
    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 87
    .local v6, "userSN":Ljava/lang/String;
    const-string v9, "\u7adf\u7136\u4f5c\u5f0a\uff0c\u597d\u4e22\u4eba\u7684\u54e6"

    const/4 v10, 0x1

    invoke-static {p0, v9, v10}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v9

    invoke-virtual {v9}, Landroid/widget/Toast;->show()V

    .line 88
    iget-object v9, p0, Lcom/droider/crackme0201/MainActivity;->edit_sn:Landroid/widget/EditText;

    invoke-virtual {v9, v6}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 91
    invoke-virtual {v6, p2}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v9

    if-eqz v9, :cond_0

    move v7, v8

    .line 97
    goto :goto_0

    .line 83
    .end local v6    # "userSN":Ljava/lang/String;
    :cond_2
    invoke-virtual {v3, v4}, Ljava/lang/String;->charAt(I)C

    move-result v9

    invoke-virtual {v5, v9}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0

    .line 82
    add-int/lit8 v4, v4, 0x2

    goto :goto_1

    .line 93
    .end local v0    # "bytes":[B
    .end local v1    # "digest":Ljava/security/MessageDigest;
    .end local v3    # "hexstr":Ljava/lang/String;
    .end local v4    # "i":I
    .end local v5    # "sb":Ljava/lang/StringBuilder;
    :catch_0
    move-exception v2

    .line 94
    .local v2, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v2}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    goto :goto_0
.end method

.method private static toHexString([BLjava/lang/String;)Ljava/lang/String;
    .locals 7
    .param p0, "bytes"    # [B
    .param p1, "separator"    # Ljava/lang/String;

    .prologue
    .line 101
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    .line 102
    .local v2, "hexString":Ljava/lang/StringBuilder;
    array-length v4, p0

    const/4 v3, 0x0

    :goto_0
    if-lt v3, v4, :cond_0

    .line 109
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    return-object v3

    .line 102
    :cond_0
    aget-byte v0, p0, v3

    .line 103
    .local v0, "b":B
    and-int/lit16 v5, v0, 0xff

    invoke-static {v5}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v1

    .line 104
    .local v1, "hex":Ljava/lang/String;
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v5

    const/4 v6, 0x1

    if-ne v5, v6, :cond_1

    .line 105
    const/16 v5, 0x30

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 107
    :cond_1
    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 102
    add-int/lit8 v3, v3, 0x1

    goto :goto_0
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .param p1, "v"    # Landroid/view/View;

    .prologue
    const/4 v2, 0x0

    .line 114
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    packed-switch v0, :pswitch_data_0

    .line 136
    :goto_0
    return-void

    .line 116
    :pswitch_0
    iget-object v0, p0, Lcom/droider/crackme0201/MainActivity;->edit_userName:Landroid/widget/EditText;

    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v0

    invoke-interface {v0}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/droider/crackme0201/MainActivity;->edit_sn:Landroid/widget/EditText;

    .line 117
    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-interface {v1}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    .line 116
    invoke-direct {p0, v0, v1}, Lcom/droider/crackme0201/MainActivity;->checkSN(Ljava/lang/String;Ljava/lang/String;)Z

    move-result v0

    .line 117
    if-nez v0, :cond_0

    .line 119
    const v0, 0x7f05000b    # 无效用户名或注册码

    .line 118
    invoke-static {p0, v0, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v0

    .line 119
    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 122
    :cond_0
    const v0, 0x7f05000c

    .line 121
    invoke-static {p0, v0, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;II)Landroid/widget/Toast;

    move-result-object v0

    .line 122
    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 123
    iget-object v0, p0, Lcom/droider/crackme0201/MainActivity;->btn_register:Landroid/widget/Button;

    invoke-virtual {v0, v2}, Landroid/widget/Button;->setEnabled(Z)V

    .line 124
    const v0, 0x7f05000a

    invoke-virtual {p0, v0}, Lcom/droider/crackme0201/MainActivity;->setTitle(I)V

    goto :goto_0

    .line 128
    :pswitch_1
    iget-object v0, p0, Lcom/droider/crackme0201/MainActivity;->edit_userName:Landroid/widget/EditText;

    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v0

    invoke-interface {v0}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/droider/crackme0201/MainActivity;->edit_sn:Landroid/widget/EditText;

    .line 129
    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-interface {v1}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    .line 128
    invoke-direct {p0, v0, v1}, Lcom/droider/crackme0201/MainActivity;->checkSNfalse(Ljava/lang/String;Ljava/lang/String;)Z

    .line 130
    iget-object v0, p0, Lcom/droider/crackme0201/MainActivity;->btn_answer:Landroid/widget/Button;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setVisibility(I)V

    goto :goto_0

    .line 114
    nop

    :pswitch_data_0
    .packed-switch 0x7f080003
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 25
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 26
    const/high16 v0, 0x7f030000

    invoke-virtual {p0, v0}, Lcom/droider/crackme0201/MainActivity;->setContentView(I)V

    .line 27
    const v0, 0x7f050009

    invoke-virtual {p0, v0}, Lcom/droider/crackme0201/MainActivity;->setTitle(I)V

    .line 28
    const v0, 0x7f080001

    invoke-virtual {p0, v0}, Lcom/droider/crackme0201/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/droider/crackme0201/MainActivity;->edit_userName:Landroid/widget/EditText;

    .line 29
    const v0, 0x7f080002

    invoke-virtual {p0, v0}, Lcom/droider/crackme0201/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/droider/crackme0201/MainActivity;->edit_sn:Landroid/widget/EditText;

    .line 30
    const v0, 0x7f080004

    invoke-virtual {p0, v0}, Lcom/droider/crackme0201/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/droider/crackme0201/MainActivity;->btn_register:Landroid/widget/Button;

    .line 31
    const v0, 0x7f080003

    invoke-virtual {p0, v0}, Lcom/droider/crackme0201/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    iput-object v0, p0, Lcom/droider/crackme0201/MainActivity;->btn_answer:Landroid/widget/Button;

    .line 32
    iget-object v0, p0, Lcom/droider/crackme0201/MainActivity;->btn_answer:Landroid/widget/Button;

    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 33
    iget-object v0, p0, Lcom/droider/crackme0201/MainActivity;->btn_register:Landroid/widget/Button;

    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 35
    return-void
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 2
    .param p1, "menu"    # Landroid/view/Menu;

    .prologue
    .line 39
    invoke-virtual {p0}, Lcom/droider/crackme0201/MainActivity;->getMenuInflater()Landroid/view/MenuInflater;

    move-result-object v0

    const/high16 v1, 0x7f070000

    invoke-virtual {v0, v1, p1}, Landroid/view/MenuInflater;->inflate(ILandroid/view/Menu;)V

    .line 40
    const/4 v0, 0x1

    return v0
.end method
