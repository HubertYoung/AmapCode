package com.autonavi.link.connect.a;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/* compiled from: SecurityManager */
public class a {
    private static a a;
    private Map<String, Long> b = new HashMap();
    private long c = System.currentTimeMillis();

    private a() {
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            if (a == null) {
                a = new a();
            }
            aVar = a;
        }
        return aVar;
    }

    public String b() {
        while (true) {
            String a2 = a(64);
            synchronized (this) {
                if (!this.b.containsKey(a2)) {
                    this.b.put(a2, Long.valueOf(System.currentTimeMillis()));
                    return a2;
                }
            }
        }
    }

    public synchronized boolean a(String str) {
        if (str != null) {
            if (!str.isEmpty()) {
                c();
                return this.b.containsKey(str);
            }
        }
        return false;
    }

    private synchronized void c() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.b.size() > 1000 || currentTimeMillis - this.c > 3600000) {
            this.c = currentTimeMillis;
            Iterator<Entry<String, Long>> it = this.b.entrySet().iterator();
            while (it.hasNext()) {
                if (currentTimeMillis - ((Long) it.next().getValue()).longValue() > 86400000) {
                    it.remove();
                }
            }
        }
    }

    private String a(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length())));
        }
        return stringBuffer.toString();
    }
}
