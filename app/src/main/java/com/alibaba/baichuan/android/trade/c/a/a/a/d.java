package com.alibaba.baichuan.android.trade.c.a.a.a;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.c.a.a.b.g;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class d extends g implements Serializable {
    public String f;
    public int[] g;
    public String[] h;
    public boolean i = true;
    public LinkedHashMap j = new LinkedHashMap();

    public static class a implements Serializable {
        public String a;
        public String b;
        public Map c = new HashMap();

        public b a() {
            if ("updateParameter".equals(this.b)) {
                return new f(this);
            }
            return null;
        }
    }

    public a a() {
        if (TextUtils.isEmpty(this.f)) {
            return new a(this);
        }
        return null;
    }
}
