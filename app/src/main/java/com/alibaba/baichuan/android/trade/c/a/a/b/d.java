package com.alibaba.baichuan.android.trade.c.a.a.b;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class d extends g implements Serializable {
    public String f;
    public int[] g;
    public String[] h;
    public String i;
    public b j;
    public Map k = new HashMap();
    public boolean l;

    public a a() {
        if (TextUtils.isEmpty(this.f)) {
            return new a(this);
        }
        return null;
    }
}
