package com.sijla.b;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.sijla.b.e.b;
import com.sijla.g.k;
import com.sijla.lj.L;
import com.sijla.lj.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class d implements b {
    public static List<com.sijla.lj.d> b = new ArrayList();
    /* access modifiers changed from: private */
    public static Intent e;
    /* access modifiers changed from: private */
    public static long f;
    /* access modifiers changed from: private */
    public static boolean n;
    boolean a;
    /* access modifiers changed from: private */
    public Application c;
    private HashMap<String, String> d = new HashMap<>();
    private ArrayList<Object> g = new ArrayList<>();
    private a h;
    private L i;
    private String j = "";
    private String k = "";
    private BroadcastReceiver l;
    private e m;
    /* access modifiers changed from: private */
    public List<com.sijla.e.b> o = new ArrayList();
    /* access modifiers changed from: private */
    public boolean p;
    private StringBuilder q = new StringBuilder();

    public class a extends Handler {
        public a() {
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    k.a(d.this.c, message.getData().getString("data"));
                    return;
                case 1:
                    Bundle data = message.getData();
                    d.this.a(data.getString("data"), ((Object[]) data.getSerializable("args"))[0]);
                    return;
                case 2:
                    d.this.a(message.getData().getString("data"), new Object[0]);
                    return;
                case 3:
                    d.this.a(message.getData().getString("data"), (Object[]) message.getData().getSerializable("args"));
                    break;
            }
        }
    }

    public void b() {
    }

    public void d() {
    }

    public d(Application application) {
        this.c = application;
    }

    public Intent a(IntentFilter intentFilter) {
        if (this.l != null) {
            try {
                this.c.unregisterReceiver(this.l);
            } catch (Throwable unused) {
            }
        }
        this.l = new BroadcastReceiver() {
            public void onReceive(final Context context, Intent intent) {
                d.e = intent;
                if (System.currentTimeMillis() - d.f > 1000) {
                    d.f = System.currentTimeMillis();
                    com.sijla.a.a.b(new Runnable() {
                        public void run() {
                            if (d.e != null) {
                                String action = d.e.getAction();
                                String substring = d.e.getDataString().substring(8);
                                com.sijla.a.a.a(new com.sijla.e.a(context, d.e));
                                d.this.a((String) "onReceive", substring, action);
                            }
                        }
                    }, 2000);
                }
            }
        };
        return this.c.registerReceiver(this.l, intentFilter);
    }

    public void a() {
        try {
            this.h = new a();
        } catch (Throwable unused) {
        }
        this.m = new e(this.c);
        this.m.a((b) this);
        com.sijla.a.a.a(new Runnable() {
            public void run() {
                try {
                    d.n = com.sijla.g.a.a.e(d.this.c);
                    com.sijla.c.a.a(d.this.c);
                    d.this.a(d.this.c);
                    d.this.b();
                    d.this.c();
                    b.c((Context) d.this.c);
                    d.this.o = b.d(d.this.c);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    public void a(final Application application) {
        if (VERSION.SDK_INT < 14) {
            final String packageName = application.getPackageName();
            final String f2 = com.sijla.g.a.a.f(application);
            this.a = true;
            com.sijla.a.a.a(new Runnable() {
                public void run() {
                    long j = 0;
                    while (d.this.a) {
                        try {
                            Thread.sleep(2000);
                            if (!com.sijla.g.a.a.d(application).equals(packageName) || !d.n) {
                                if (d.this.p && j > 0) {
                                    d.this.p = false;
                                    d.this.a((System.currentTimeMillis() - j) / 1000);
                                    j = 0;
                                }
                            } else if (!d.this.p && j == 0) {
                                d.this.p = true;
                                long currentTimeMillis = System.currentTimeMillis();
                                try {
                                    d.this.i();
                                    j = currentTimeMillis;
                                } catch (Throwable th) {
                                    th = th;
                                    j = currentTimeMillis;
                                    th.printStackTrace();
                                }
                            }
                            d.this.a((String) "ticker", Long.valueOf(2000));
                        } catch (Throwable th2) {
                            th = th2;
                            th.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void m() {
        this.a = false;
    }

    public void c() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme("package");
        intentFilter.setPriority(SecExceptionCode.SEC_ERROR_UMID_UNKNOWN_ERR);
        a(intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(com.sijla.g.a.a.b((Context) this.c));
        intentFilter2.addAction(com.sijla.g.a.a.a((Context) this.c));
        intentFilter2.addAction(com.sijla.g.a.a.c(this.c));
        this.c.registerReceiver(new BroadcastReceiver() {
            public void onReceive(final Context context, final Intent intent) {
                try {
                    final String action = intent.getAction();
                    String str = intent.getPackage();
                    if (!(action == null || str == null || !str.equals(context.getPackageName()))) {
                        com.sijla.a.a.a(new Runnable() {
                            public void run() {
                                if (action.equals(com.sijla.g.a.a.b(context))) {
                                    d.this.a(intent.getLongExtra("dur", 0));
                                } else if (action.equals(com.sijla.g.a.a.a(context))) {
                                    d.this.i();
                                } else {
                                    if (action.equals(com.sijla.g.a.a.c(context))) {
                                        com.sijla.g.b.a.a().a(intent.getBooleanExtra(com.sijla.g.a.a.c(context), true));
                                    }
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, intentFilter2);
    }

    public Object a(String str, Object... objArr) {
        if (!(this.i == null || this.i.a() == 0)) {
            try {
                this.i.a(0);
                if (this.i.b(str) != 0 && this.i.d(-1)) {
                    int length = objArr != null ? objArr.length : 0;
                    for (int i2 = 0; i2 < length; i2++) {
                        this.i.b(objArr[i2]);
                    }
                    if (this.i.a(length, 1, 0) == 0) {
                        return null;
                    }
                }
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(Token.SEPARATOR);
                sb.append(e2.getMessage());
                a(sb.toString());
            }
        }
        return null;
    }

    public void a(String str) {
        if (str != null && this.h != null) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("data", str);
            message.setData(bundle);
            message.what = 0;
            this.h.sendMessage(message);
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, Object obj) {
        if (this.i != null) {
            try {
                this.i.b(obj);
                this.i.c(str);
            } catch (c e2) {
                a(e2.getMessage());
            }
        }
    }

    public void e() {
        a((String) "onPowerConnected", new Object[0]);
        if (this.o != null) {
            for (final com.sijla.e.b next : this.o) {
                com.sijla.a.a.a((Runnable) new Runnable() {
                    public void run() {
                        try {
                            next.e();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }
    }

    public void f() {
        a((String) "onPowerDisConnected", new Object[0]);
        if (this.o != null) {
            for (final com.sijla.e.b next : this.o) {
                com.sijla.a.a.a((Runnable) new Runnable() {
                    public void run() {
                        try {
                            next.g();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }
    }

    public void g() {
        n = true;
        a(this.c);
        a((String) "onKeyGuardGone", new Object[0]);
        if (this.o != null) {
            for (final com.sijla.e.b next : this.o) {
                com.sijla.a.a.a((Runnable) new Runnable() {
                    public void run() {
                        try {
                            next.g();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }
    }

    public void h() {
        n = false;
        m();
        a((String) "onScreenOff", new Object[0]);
        d();
        if (this.o != null) {
            for (final com.sijla.e.b next : this.o) {
                com.sijla.a.a.a((Runnable) new Runnable() {
                    public void run() {
                        try {
                            next.h();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }
    }

    public void a(final Intent intent) {
        a((String) "chargeSate", new Object[0]);
        if (this.o != null) {
            for (final com.sijla.e.b next : this.o) {
                com.sijla.a.a.a((Runnable) new Runnable() {
                    public void run() {
                        try {
                            next.a(intent);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }
    }

    public void i() {
        a((String) Nebula.appResume, new Object[0]);
        if (this.o != null) {
            for (final com.sijla.e.b next : this.o) {
                com.sijla.a.a.a((Runnable) new Runnable() {
                    public void run() {
                        try {
                            next.a();
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }
    }

    public void a(final long j2) {
        a((String) "appPause", Long.valueOf(j2));
        if (this.o != null) {
            for (final com.sijla.e.b next : this.o) {
                com.sijla.a.a.a((Runnable) new Runnable() {
                    public void run() {
                        try {
                            next.a(j2);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                }, 1000);
            }
        }
    }
}
