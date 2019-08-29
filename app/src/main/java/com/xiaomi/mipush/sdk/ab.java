package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ab {
    private static volatile ab a;
    private Context b;
    private List<t> c = new ArrayList();

    private ab(Context context) {
        this.b = context.getApplicationContext();
        if (this.b == null) {
            this.b = context;
        }
    }

    public static ab a(Context context) {
        if (a == null) {
            synchronized (ab.class) {
                try {
                    if (a == null) {
                        a = new ab(context);
                    }
                }
            }
        }
        return a;
    }

    public synchronized String a(ao aoVar) {
        try {
        }
        return this.b.getSharedPreferences("mipush_extra", 0).getString(aoVar.name(), "");
    }

    public synchronized void a(ao aoVar, String str) {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_extra", 0);
        sharedPreferences.edit().putString(aoVar.name(), str).commit();
    }

    public void a(String str) {
        synchronized (this.c) {
            t tVar = new t();
            tVar.a = 0;
            tVar.b = str;
            if (this.c.contains(tVar)) {
                this.c.remove(tVar);
            }
            this.c.add(tVar);
        }
    }

    public void b(String str) {
        synchronized (this.c) {
            t tVar = new t();
            tVar.b = str;
            if (this.c.contains(tVar)) {
                Iterator<t> it = this.c.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    t next = it.next();
                    if (tVar.equals(next)) {
                        tVar = next;
                        break;
                    }
                }
            }
            tVar.a++;
            this.c.remove(tVar);
            this.c.add(tVar);
        }
    }

    public int c(String str) {
        synchronized (this.c) {
            t tVar = new t();
            tVar.b = str;
            if (this.c.contains(tVar)) {
                for (t next : this.c) {
                    if (next.equals(tVar)) {
                        int i = next.a;
                        return i;
                    }
                }
            }
            return 0;
        }
    }

    public void d(String str) {
        synchronized (this.c) {
            t tVar = new t();
            tVar.b = str;
            if (this.c.contains(tVar)) {
                this.c.remove(tVar);
            }
        }
    }

    public boolean e(String str) {
        synchronized (this.c) {
            t tVar = new t();
            tVar.b = str;
            return this.c.contains(tVar);
        }
    }
}
