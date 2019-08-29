package com.alibaba.baichuan.android.auth;

import java.util.HashMap;

public enum a {
    HINTLIST_NULL(1, "授权列表为null（请使用want组件调用授权api）");
    
    public static HashMap d;
    public String b;
    public String c;

    static {
        d = new b();
    }

    private a(int i, String str) {
        this.b = String.valueOf(i);
        this.c = str;
    }
}
