package com.sijla.b;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.q.Qt;
import com.sijla.e.c;
import com.sijla.g.a.a;
import com.sijla.g.d;
import com.sijla.g.j;
import java.util.ArrayList;
import java.util.List;

public class b {
    private static String a = ":QS";
    private static String b = "";
    private static String c = "notset";
    /* access modifiers changed from: private */
    public static boolean d = true;
    /* access modifiers changed from: private */
    public static long e = 0;
    /* access modifiers changed from: private */
    public static long f = 0;
    /* access modifiers changed from: private */
    public static boolean g = false;
    /* access modifiers changed from: private */
    public static String h = "";
    private static boolean i = false;

    public static void a(Context context, String str) {
    }

    public static void a(String str, int i2) {
    }

    static /* synthetic */ long d() {
        long j = f;
        f = 1 + j;
        return j;
    }

    public static void a(String str) {
        b = str;
    }

    public static String a() {
        return b;
    }

    public static String b() {
        return c;
    }

    public static void a(boolean z) {
        d = z;
    }

    public static void b(String str) {
        a(str, 1);
    }

    public static void a(final Application application, final long j) {
        if (VERSION.SDK_INT >= 14) {
            e = 0;
            g = true;
            final String b2 = a.b((Context) application, application.getPackageName());
            application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                public final void onActivityCreated(Activity activity, Bundle bundle) {
                }

                public final void onActivityDestroyed(Activity activity) {
                }

                public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                public final void onActivityStarted(Activity activity) {
                }

                public final void onActivityStopped(Activity activity) {
                }

                public final void onActivityResumed(Activity activity) {
                    if (b.f > 0) {
                        b.f = 1;
                        return;
                    }
                    if (b.d() == 0 && 0 == b.e) {
                        b.e = System.currentTimeMillis();
                        com.sijla.f.b.a().a(application);
                        if (b.g) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(b2);
                            sb.append(" 冷启动");
                            b.b(sb.toString());
                            b.g = false;
                            b.a((Context) application, (String) "onActivityResumed");
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(b2);
                        sb2.append("到前台:");
                        sb2.append(d.a());
                        sb2.append(" proc:");
                        sb2.append(a.f(application));
                        sb2.append(" tid:");
                        sb2.append(Thread.currentThread().getId());
                        b.b(sb2.toString());
                        com.sijla.a.a.a(new Runnable() {
                            public void run() {
                                b.h = a.z(application);
                                com.sijla.f.a.a((Context) application, 0, Qt._dauCallBack);
                            }
                        });
                        b.b(application, 0);
                    }
                }

                public final void onActivityPaused(Activity activity) {
                    if (b.f >= 0) {
                        b.f = 0;
                        com.sijla.a.a.b(new Runnable() {
                            public void run() {
                                if (b.f == 0) {
                                    if (0 == b.e) {
                                        b.e = j;
                                        b.b((String) "QuestMobile SDK 可能未在Application中的onCreate方法启动");
                                    }
                                    b.g = false;
                                    final long currentTimeMillis = (System.currentTimeMillis() - b.e) / 1000;
                                    b.e = 0;
                                    com.sijla.a.a.a(new Runnable() {
                                        public void run() {
                                            com.sijla.f.a.a((Context) application, currentTimeMillis, Qt._dauCallBack);
                                        }
                                    });
                                    if (b.d) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(b2);
                                        sb.append("到后台:");
                                        sb.append(d.a());
                                        sb.append(" 使用时长 ");
                                        sb.append(currentTimeMillis);
                                        sb.append(" 秒 proc:");
                                        sb.append(a.f(application));
                                        sb.append(" tid:");
                                        sb.append(Thread.currentThread().getId());
                                        b.b(sb.toString());
                                    }
                                    b.b(application, currentTimeMillis);
                                }
                            }
                        }, 1000);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void b(Context context, long j) {
        boolean z = 0 == j;
        Intent intent = new Intent();
        intent.putExtra("dur", j);
        intent.setPackage(context.getPackageName());
        intent.setAction(z ? a.a(context) : a.b(context));
        context.sendBroadcast(intent);
    }

    public static void a(Context context) {
        if (com.sijla.g.b.a(b(context))) {
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            sb.append(context.getPackageName());
            sb.append(com.sijla.g.b.a(8));
            j.a(context, "insid", com.sijla.g.a.d.a(sb.toString()));
        }
    }

    public static String b(Context context) {
        try {
            return (String) j.b(context, "insid", "");
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static void c(Context context) {
        try {
            if (a.d(context, (String) "android.permission.READ_PHONE_STATE") && a.d(context, (String) "android.permission.WRITE_EXTERNAL_STORAGE") && ((Boolean) j.b(context, "isFirstRun", Boolean.TRUE)).booleanValue()) {
                j.a(context, "FirstRunTime", Long.valueOf(d.d()));
                j.a(context, "isFirstRun", Boolean.FALSE);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static List<com.sijla.e.b> d(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new com.sijla.e.d(context));
        arrayList.add(new c(context));
        return arrayList;
    }
}
