package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import com.alibaba.wireless.security.framework.ISGPluginManager;
import com.alibaba.wireless.security.framework.d;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent.IInitFinishListener;
import java.util.HashSet;
import java.util.Set;

public class b {
    boolean a = false;
    private Set<IInitFinishListener> b = new HashSet();
    private Object c = new Object();
    private String d = null;
    private ISGPluginManager e = null;

    public b() {
    }

    public b(String str) {
        this.d = str;
    }

    /* access modifiers changed from: private */
    public void b() {
        synchronized (this.c) {
            for (IInitFinishListener onSuccess : this.b) {
                onSuccess.onSuccess();
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        synchronized (this.c) {
            for (IInitFinishListener onError : this.b) {
                onError.onError();
            }
        }
    }

    public synchronized int a(Context context, String str, boolean z, boolean z2) throws SecException {
        try {
            if (!this.a) {
                if (context == null) {
                    throw new SecException(101);
                }
                d dVar = new d();
                dVar.a(context, this.d, str, z, new Object[0]);
                dVar.getPluginInfo(dVar.getMainPluginName());
                this.e = dVar;
                this.a = true;
            }
            return this.a ? 0 : 1;
        }
    }

    public ISGPluginManager a() {
        return this.e;
    }

    public void a(IInitFinishListener iInitFinishListener) throws SecException {
        if (iInitFinishListener != null) {
            synchronized (this.c) {
                this.b.add(iInitFinishListener);
            }
        }
    }

    public boolean a(Context context) throws SecException {
        return true;
    }

    public void b(Context context, String str, boolean z, boolean z2) throws SecException {
        if (context == null) {
            throw new SecException(101);
        }
        final Context context2 = context;
        final String str2 = str;
        final boolean z3 = z;
        final boolean z4 = z2;
        AnonymousClass1 r1 = new Runnable() {
            public void run() {
                try {
                    b.this.a(context2, str2, z3, z4);
                    b.this.b();
                } catch (SecException e2) {
                    e2.printStackTrace();
                    b.this.c();
                }
            }
        };
        new Thread(r1).start();
    }
}
