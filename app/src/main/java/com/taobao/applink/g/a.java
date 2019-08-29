package com.taobao.applink.g;

import com.taobao.applink.h.d;

public class a {
    private static d a;

    public static synchronized d a() {
        d dVar;
        synchronized (a.class) {
            try {
                dVar = a;
            }
        }
        return dVar;
    }

    public static synchronized void a(d dVar) {
        synchronized (a.class) {
            a = dVar;
        }
    }
}
