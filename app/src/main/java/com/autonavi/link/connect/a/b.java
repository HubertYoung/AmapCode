package com.autonavi.link.connect.a;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: WifiConnectionIdHolder */
public class b {
    private static volatile b c;
    private long a = System.currentTimeMillis();
    private final Map<String, a> b = new HashMap();

    /* compiled from: WifiConnectionIdHolder */
    static class a {
        String a;
        String b;
        String c;
        long d;

        private a() {
        }
    }

    private b() {
    }

    public static b a() {
        if (c == null) {
            synchronized (b.class) {
                try {
                    if (c == null) {
                        c = new b();
                    }
                }
            }
        }
        return c;
    }

    public void a(String str, String str2, String str3) {
        if (str != null && str2 != null && str3 != null) {
            a aVar = new a();
            aVar.a = str;
            aVar.b = str2;
            aVar.c = str3;
            aVar.d = System.currentTimeMillis();
            synchronized (this.b) {
                this.b.put(str, aVar);
            }
        }
    }

    public String a(String str) {
        if (str == null) {
            return "";
        }
        int indexOf = str.indexOf(":");
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        synchronized (this.b) {
            try {
                b();
                a aVar = this.b.get(str);
                if (aVar == null) {
                    return "";
                }
                String str2 = aVar.c;
                return str2;
            }
        }
    }

    private synchronized void b() {
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.b) {
            if (this.b.size() > 1000 || currentTimeMillis - this.a > 3600000) {
                this.a = currentTimeMillis;
                Iterator<Entry<String, a>> it = this.b.entrySet().iterator();
                while (it.hasNext()) {
                    if (currentTimeMillis - ((a) it.next().getValue()).d > 86400000) {
                        it.remove();
                    }
                }
            }
        }
    }
}
