package com.alibaba.baichuan.android.auth;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.AlibcContext.Environment;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AlibcAuthHint {
    public static final AlibcAuthHint WANT_ADD_CART = new AlibcAuthHint("1", "24", "添加商品到您的淘宝购物车");
    private static Map a;
    private static Map b;
    private static Map c;
    private String d;
    private String e;
    public String hintInfo;

    private AlibcAuthHint() {
    }

    private AlibcAuthHint(String str, String str2, String str3) {
        this.d = str;
        this.hintInfo = str3;
        this.e = str2;
        synchronized (this) {
            if (a == null) {
                a = new ConcurrentHashMap();
            }
        }
        a.put(getHintID(), this);
    }

    public static synchronized Set getApiAndHint(String str) {
        synchronized (AlibcAuthHint.class) {
            try {
                if (b == null) {
                    return null;
                }
                Set set = (Set) c.get(str);
                return set;
            }
        }
    }

    public static String getHintInfo(String str) {
        StringBuilder sb;
        if (TextUtils.isEmpty(str)) {
            sb = new StringBuilder("访问您淘宝账号信息的权限(");
        } else {
            String str2 = a.get(str) == null ? "" : ((AlibcAuthHint) a.get(str)).hintInfo;
            if (TextUtils.isEmpty(str2)) {
                str2 = b == null ? "" : (String) b.get(str);
            }
            if (!TextUtils.isEmpty(str2)) {
                return str2;
            }
            sb = new StringBuilder("访问您淘宝账号信息的权限(");
        }
        sb.append(str);
        sb.append(")");
        return sb.toString();
    }

    public static synchronized void putApiAndHint(String str, Set set) {
        synchronized (AlibcAuthHint.class) {
            if (c == null) {
                c = new HashMap();
            }
            c.put(str, set);
        }
    }

    public static synchronized void putExpandList(String str, String str2) {
        synchronized (AlibcAuthHint.class) {
            if (b == null) {
                b = new HashMap();
            }
            b.put(str, str2);
        }
    }

    public String getHintID() {
        return AlibcContext.environment == Environment.TEST ? this.e : this.d;
    }
}
