package com.alibaba.baichuan.android.trade.utils;

import android.app.AlertDialog.Builder;

final class b implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;

    b(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public final void run() {
        Builder builder = new Builder(a.a());
        StringBuilder sb = new StringBuilder("class = ");
        sb.append(this.a);
        sb.append("\nmethod = ");
        sb.append(this.b);
        sb.append("\nerrMsg = ");
        sb.append(this.c);
        builder.setMessage(sb.toString());
        builder.setTitle("执行过程参数发生错误");
        builder.show();
    }
}
