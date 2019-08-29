package com.xiaomi.push.mpcd;

public class c {
    private static volatile c b;
    private b a;

    public static c a() {
        if (b == null) {
            synchronized (c.class) {
                try {
                    if (b == null) {
                        b = new c();
                    }
                }
            }
        }
        return b;
    }

    public void a(b bVar) {
        this.a = bVar;
    }

    public b b() {
        return this.a;
    }
}
