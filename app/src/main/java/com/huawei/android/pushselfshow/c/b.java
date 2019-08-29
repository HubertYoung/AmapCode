package com.huawei.android.pushselfshow.c;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a;
import java.util.HashMap;

public class b {
    public static HashMap a = new HashMap();
    private static int b;

    @SuppressLint({"InlinedApi"})
    private static float a(Context context) {
        float a2 = (float) a.a(context, 48.0f);
        try {
            float dimension = context.getResources().getDimension(17104901);
            if (dimension > 0.0f && a2 > dimension) {
                a2 = dimension;
            }
        } catch (Exception e) {
            c.c("PushSelfShowLog", e.toString());
        }
        c.a("PushSelfShowLog", "getRescaleBitmapSize:".concat(String.valueOf(a2)));
        return a2;
    }

    public static int a(Context context, String str, String str2, Object obj) {
        String str3;
        String str4;
        Throwable th;
        Class cls;
        int i;
        String str5;
        String str6;
        Object[] objArr;
        String str7 = str;
        String str8 = str2;
        Object obj2 = obj;
        int i2 = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPackageName());
            sb.append(".R");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder("try to refrect ");
            sb3.append(sb2);
            sb3.append(" typeName is ");
            sb3.append(str8);
            c.a("PushSelfShowLog", sb3.toString());
            Class[] classes = Class.forName(sb2).getClasses();
            StringBuilder sb4 = new StringBuilder("sonClassArr length ");
            sb4.append(classes.length);
            c.a("PushSelfShowLog", sb4.toString());
            int i3 = 0;
            while (true) {
                if (i3 >= classes.length) {
                    cls = null;
                    break;
                }
                cls = classes[i3];
                String substring = cls.getName().substring(sb2.length() + 1);
                StringBuilder sb5 = new StringBuilder();
                sb5.append(substring);
                sb5.append(" sonClass.getName() is");
                sb5.append(cls.getName());
                c.a((String) "PushSelfShowLog", (String) "sonTypeClass,query sonclass is  %s", sb5.toString());
                if (str8.equals(substring)) {
                    break;
                }
                i3++;
            }
            if (cls != null) {
                i = cls.getField(str7).getInt(obj2);
                str5 = "PushSelfShowLog";
                str6 = " refect res id is %s";
                try {
                    objArr = new Object[]{String.valueOf(i)};
                } catch (ClassNotFoundException e) {
                    e = e;
                    i2 = i;
                } catch (NoSuchFieldException e2) {
                    e = e2;
                    i2 = i;
                    th = e;
                    str4 = "PushSelfShowLog";
                    str3 = "NoSuchFieldException failed,";
                    c.c(str4, str3, th);
                    return i2;
                } catch (IllegalAccessException e3) {
                    e = e3;
                    i2 = i;
                    th = e;
                    str4 = "PushSelfShowLog";
                    str3 = "IllegalAccessException failed,";
                    c.c(str4, str3, th);
                    return i2;
                } catch (IllegalArgumentException e4) {
                    e = e4;
                    i2 = i;
                    th = e;
                    str4 = "PushSelfShowLog";
                    str3 = "IllegalArgumentException failed,";
                    c.c(str4, str3, th);
                    return i2;
                } catch (IndexOutOfBoundsException e5) {
                    e = e5;
                    i2 = i;
                    th = e;
                    str4 = "PushSelfShowLog";
                    str3 = "IndexOutOfBoundsException failed,";
                    c.c(str4, str3, th);
                    return i2;
                } catch (Exception e6) {
                    e = e6;
                    i2 = i;
                    th = e;
                    str4 = "PushSelfShowLog";
                    str3 = "  failed,";
                    c.c(str4, str3, th);
                    return i2;
                }
            } else {
                c.a("PushSelfShowLog", "sonTypeClass is null");
                StringBuilder sb6 = new StringBuilder();
                sb6.append(context.getPackageName());
                sb6.append(".R$");
                sb6.append(str8);
                String sb7 = sb6.toString();
                StringBuilder sb8 = new StringBuilder("try to refrect 2 ");
                sb8.append(sb7);
                sb8.append(" typeName is ");
                sb8.append(str8);
                c.a("PushSelfShowLog", sb8.toString());
                i = Class.forName(sb7).getField(str7).getInt(obj2);
                str5 = "PushSelfShowLog";
                str6 = " refect res id 2 is %s";
                objArr = new Object[]{String.valueOf(i)};
            }
            c.a(str5, str6, objArr);
            return i;
        } catch (ClassNotFoundException e7) {
            e = e7;
            th = e;
            str4 = "PushSelfShowLog";
            str3 = "ClassNotFound failed,";
            c.c(str4, str3, th);
            return i2;
        } catch (NoSuchFieldException e8) {
            e = e8;
            th = e;
            str4 = "PushSelfShowLog";
            str3 = "NoSuchFieldException failed,";
            c.c(str4, str3, th);
            return i2;
        } catch (IllegalAccessException e9) {
            e = e9;
            th = e;
            str4 = "PushSelfShowLog";
            str3 = "IllegalAccessException failed,";
            c.c(str4, str3, th);
            return i2;
        } catch (IllegalArgumentException e10) {
            e = e10;
            th = e;
            str4 = "PushSelfShowLog";
            str3 = "IllegalArgumentException failed,";
            c.c(str4, str3, th);
            return i2;
        } catch (IndexOutOfBoundsException e11) {
            e = e11;
            th = e;
            str4 = "PushSelfShowLog";
            str3 = "IndexOutOfBoundsException failed,";
            c.c(str4, str3, th);
            return i2;
        } catch (Exception e12) {
            e = e12;
            th = e;
            str4 = "PushSelfShowLog";
            str3 = "  failed,";
            c.c(str4, str3, th);
            return i2;
        }
    }

    public static Notification a(Context context, com.huawei.android.pushselfshow.b.a aVar, int i, int i2, int i3) {
        Builder builder = new Builder(context);
        if (VERSION.SDK_INT >= 26) {
            builder.setChannelId("default");
        }
        Notification build = builder.build();
        build.icon = b(context, aVar);
        int i4 = context.getApplicationInfo().labelRes;
        build.tickerText = aVar.q;
        build.when = System.currentTimeMillis();
        build.flags |= 16;
        build.defaults |= 1;
        PendingIntent a2 = a(context, aVar, i, i2);
        build.contentIntent = a2;
        build.deleteIntent = b(context, aVar, i, i3);
        if (VERSION.SDK_INT <= 22) {
            build.setLatestEventInfo(context, (aVar.s == null || "".equals(aVar.s)) ? context.getResources().getString(i4) : aVar.s, aVar.q, a2);
        }
        int identifier = context.getResources().getIdentifier(H5Param.MENU_ICON, "id", "android");
        Bitmap d = d(context, aVar);
        if (!(identifier == 0 || build.contentView == null || d == null)) {
            build.contentView.setImageViewBitmap(identifier, d);
        }
        return c.a(context, build, i, aVar, d);
    }

    private static PendingIntent a(Context context, com.huawei.android.pushselfshow.b.a aVar, int i, int i2) {
        Intent intent = new Intent("com.huawei.intent.action.PUSH");
        intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "1").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(268435456);
        return PendingIntent.getBroadcast(context, i2, intent, 134217728);
    }

    public static void a(Context context, int i) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (notificationManager != null) {
                notificationManager.cancel(i);
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("removeNotifiCationById err:");
            sb.append(e.toString());
            c.d("PushSelfShowLog", sb.toString());
        }
    }

    public static void a(Context context, Intent intent) {
        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            int i = 0;
            if (intent.hasExtra("selfshow_notify_id")) {
                i = intent.getIntExtra("selfshow_notify_id", 0) + 3;
            }
            StringBuilder sb = new StringBuilder("setDelayAlarm(cancel) alarmNotityId ");
            sb.append(i);
            sb.append(" and intent is ");
            sb.append(intent.toURI());
            c.a("PushSelfShowLog", sb.toString());
            Intent intent2 = new Intent("com.huawei.intent.action.PUSH");
            intent2.setPackage(context.getPackageName()).setFlags(32);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent2, 536870912);
            if (broadcast != null) {
                c.a("PushSelfShowLog", "  alarm cancel");
                alarmManager.cancel(broadcast);
                return;
            }
            c.a("PushSelfShowLog", "alarm not exist");
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("cancelAlarm err:");
            sb2.append(e.toString());
            c.d("PushSelfShowLog", sb2.toString());
        }
    }

    public static void a(Context context, Intent intent, long j, int i) {
        try {
            StringBuilder sb = new StringBuilder("enter setDelayAlarm(intent:");
            sb.append(intent.toURI());
            sb.append(" interval:");
            sb.append(j);
            sb.append("ms, context:");
            sb.append(context);
            c.a("PushSelfShowLog", sb.toString());
            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(0, System.currentTimeMillis() + j, PendingIntent.getBroadcast(context, i, intent, 134217728));
        } catch (Exception e) {
            c.a((String) "PushSelfShowLog", (String) "set DelayAlarm error", (Throwable) e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0141, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0161, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r8, com.huawei.android.pushselfshow.b.a r9) {
        /*
            java.lang.Class<com.huawei.android.pushselfshow.c.b> r0 = com.huawei.android.pushselfshow.c.b.class
            monitor-enter(r0)
            if (r8 == 0) goto L_0x0160
            if (r9 != 0) goto L_0x0009
            goto L_0x0160
        L_0x0009:
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0144 }
            java.lang.String r3 = " showNotification , the msg id = "
            r2.<init>(r3)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r3 = r9.m     // Catch:{ Exception -> 0x0144 }
            r2.append(r3)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0144 }
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ Exception -> 0x0144 }
            r1 = 2
            r2 = 180(0xb4, float:2.52E-43)
            com.huawei.android.pushselfshow.utils.a.a(r1, r2)     // Catch:{ Exception -> 0x0144 }
            int r1 = b     // Catch:{ Exception -> 0x0144 }
            if (r1 != 0) goto L_0x004a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0144 }
            r1.<init>()     // Catch:{ Exception -> 0x0144 }
            java.lang.String r2 = r8.getPackageName()     // Catch:{ Exception -> 0x0144 }
            r1.append(r2)     // Catch:{ Exception -> 0x0144 }
            java.util.Date r2 = new java.util.Date     // Catch:{ Exception -> 0x0144 }
            r2.<init>()     // Catch:{ Exception -> 0x0144 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0144 }
            r1.append(r2)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0144 }
            int r1 = r1.hashCode()     // Catch:{ Exception -> 0x0144 }
            b = r1     // Catch:{ Exception -> 0x0144 }
        L_0x004a:
            java.lang.String r1 = r9.a     // Catch:{ Exception -> 0x0144 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0144 }
            if (r1 == 0) goto L_0x006b
            int r1 = b     // Catch:{ Exception -> 0x0144 }
            int r1 = r1 + 1
            b = r1     // Catch:{ Exception -> 0x0144 }
            int r2 = b     // Catch:{ Exception -> 0x0144 }
            int r2 = r2 + 1
            b = r2     // Catch:{ Exception -> 0x0144 }
            int r3 = b     // Catch:{ Exception -> 0x0144 }
            int r3 = r3 + 1
            b = r3     // Catch:{ Exception -> 0x0144 }
            int r4 = b     // Catch:{ Exception -> 0x0144 }
        L_0x0066:
            int r4 = r4 + 1
            b = r4     // Catch:{ Exception -> 0x0144 }
            goto L_0x0091
        L_0x006b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0144 }
            r1.<init>()     // Catch:{ Exception -> 0x0144 }
            java.lang.String r2 = r9.n     // Catch:{ Exception -> 0x0144 }
            r1.append(r2)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r2 = r9.a     // Catch:{ Exception -> 0x0144 }
            r1.append(r2)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0144 }
            int r1 = r1.hashCode()     // Catch:{ Exception -> 0x0144 }
            int r2 = b     // Catch:{ Exception -> 0x0144 }
            int r2 = r2 + 1
            b = r2     // Catch:{ Exception -> 0x0144 }
            int r3 = b     // Catch:{ Exception -> 0x0144 }
            int r3 = r3 + 1
            b = r3     // Catch:{ Exception -> 0x0144 }
            int r4 = b     // Catch:{ Exception -> 0x0144 }
            goto L_0x0066
        L_0x0091:
            java.lang.String r5 = "PushSelfShowLog"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0144 }
            java.lang.String r7 = "notifyId:"
            r6.<init>(r7)     // Catch:{ Exception -> 0x0144 }
            r6.append(r1)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r7 = ",openNotifyId:"
            r6.append(r7)     // Catch:{ Exception -> 0x0144 }
            r6.append(r2)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r7 = ",delNotifyId:"
            r6.append(r7)     // Catch:{ Exception -> 0x0144 }
            r6.append(r3)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r7 = ",alarmNotifyId:"
            r6.append(r7)     // Catch:{ Exception -> 0x0144 }
            r6.append(r4)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0144 }
            com.huawei.android.pushagent.a.a.c.a(r5, r6)     // Catch:{ Exception -> 0x0144 }
            boolean r5 = com.huawei.android.pushselfshow.utils.a.b()     // Catch:{ Exception -> 0x0144 }
            if (r5 == 0) goto L_0x00c7
            android.app.Notification r2 = b(r8, r9, r1, r2, r3)     // Catch:{ Exception -> 0x0144 }
            goto L_0x00cb
        L_0x00c7:
            android.app.Notification r2 = a(r8, r9, r1, r2, r3)     // Catch:{ Exception -> 0x0144 }
        L_0x00cb:
            java.lang.String r3 = "notification"
            java.lang.Object r3 = r8.getSystemService(r3)     // Catch:{ Exception -> 0x0144 }
            android.app.NotificationManager r3 = (android.app.NotificationManager) r3     // Catch:{ Exception -> 0x0144 }
            if (r3 == 0) goto L_0x0140
            if (r2 == 0) goto L_0x0140
            r3.notify(r1, r2)     // Catch:{ Exception -> 0x0144 }
            int r2 = r9.f     // Catch:{ Exception -> 0x0144 }
            if (r2 <= 0) goto L_0x0139
            android.content.Intent r2 = new android.content.Intent     // Catch:{ Exception -> 0x0144 }
            java.lang.String r3 = "com.huawei.intent.action.PUSH"
            r2.<init>(r3)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r3 = "selfshow_info"
            byte[] r5 = r9.c()     // Catch:{ Exception -> 0x0144 }
            android.content.Intent r3 = r2.putExtra(r3, r5)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r5 = "selfshow_token"
            byte[] r6 = r9.d()     // Catch:{ Exception -> 0x0144 }
            android.content.Intent r3 = r3.putExtra(r5, r6)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r5 = "selfshow_event_id"
            java.lang.String r6 = "-1"
            android.content.Intent r3 = r3.putExtra(r5, r6)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r5 = "selfshow_notify_id"
            android.content.Intent r1 = r3.putExtra(r5, r1)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r3 = r8.getPackageName()     // Catch:{ Exception -> 0x0144 }
            android.content.Intent r1 = r1.setPackage(r3)     // Catch:{ Exception -> 0x0144 }
            r3 = 32
            r1.setFlags(r3)     // Catch:{ Exception -> 0x0144 }
            int r1 = r9.f     // Catch:{ Exception -> 0x0144 }
            long r5 = (long) r1     // Catch:{ Exception -> 0x0144 }
            a(r8, r2, r5, r4)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0144 }
            java.lang.String r5 = "setDelayAlarm alarmNotityId"
            r3.<init>(r5)     // Catch:{ Exception -> 0x0144 }
            r3.append(r4)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r4 = " and intent is "
            r3.append(r4)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r2 = r2.toURI()     // Catch:{ Exception -> 0x0144 }
            r3.append(r2)     // Catch:{ Exception -> 0x0144 }
            java.lang.String r2 = r3.toString()     // Catch:{ Exception -> 0x0144 }
            com.huawei.android.pushagent.a.a.c.a(r1, r2)     // Catch:{ Exception -> 0x0144 }
        L_0x0139:
            java.lang.String r1 = "0"
            com.huawei.android.pushselfshow.utils.a.a(r8, r1, r9)     // Catch:{ Exception -> 0x0144 }
            monitor-exit(r0)
            return
        L_0x0140:
            monitor-exit(r0)
            return
        L_0x0142:
            r8 = move-exception
            goto L_0x015e
        L_0x0144:
            r8 = move-exception
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0142 }
            java.lang.String r2 = "showNotification error "
            r1.<init>(r2)     // Catch:{ all -> 0x0142 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0142 }
            r1.append(r8)     // Catch:{ all -> 0x0142 }
            java.lang.String r8 = r1.toString()     // Catch:{ all -> 0x0142 }
            com.huawei.android.pushagent.a.a.c.d(r9, r8)     // Catch:{ all -> 0x0142 }
            monitor-exit(r0)
            return
        L_0x015e:
            monitor-exit(r0)
            throw r8
        L_0x0160:
            monitor-exit(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.c.b.a(android.content.Context, com.huawei.android.pushselfshow.b.a):void");
    }

    private static int b(Context context, com.huawei.android.pushselfshow.b.a aVar) {
        int i = 0;
        if (context == null || aVar == null) {
            c.b("PushSelfShowLog", "enter getSmallIconId, context or msg is null");
            return 0;
        }
        if (aVar.t != null && aVar.t.length() > 0) {
            i = a(context, aVar.t, (String) ResUtils.DRAWABLE, (Object) new drawable());
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPackageName());
            sb.append("  msg.statusIcon is ");
            sb.append(aVar.t);
            sb.append(",and icon is ");
            sb.append(i);
            c.a("PushSelfShowLog", sb.toString());
            if (i == 0) {
                i = context.getResources().getIdentifier(aVar.t, ResUtils.DRAWABLE, "android");
            }
            StringBuilder sb2 = new StringBuilder("msg.statusIcon is ");
            sb2.append(aVar.t);
            sb2.append(",and icon is ");
            sb2.append(i);
            c.a("PushSelfShowLog", sb2.toString());
        }
        if (i == 0) {
            i = context.getApplicationInfo().icon;
            if (i == 0) {
                i = context.getResources().getIdentifier("btn_star_big_on", ResUtils.DRAWABLE, "android");
                c.a("PushSelfShowLog", "icon is btn_star_big_on ");
                if (i == 0) {
                    i = 17301651;
                    c.a("PushSelfShowLog", "icon is sym_def_app_icon ");
                }
            }
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x017b  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x017d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.app.Notification b(android.content.Context r7, com.huawei.android.pushselfshow.b.a r8, int r9, int r10, int r11) {
        /*
            android.app.Notification$Builder r0 = new android.app.Notification$Builder
            r0.<init>(r7)
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 26
            if (r1 < r2) goto L_0x0010
            java.lang.String r1 = "default"
            r0.setChannelId(r1)
        L_0x0010:
            boolean r1 = com.huawei.android.pushselfshow.utils.a.e(r7)
            if (r1 == 0) goto L_0x003f
            java.lang.String r1 = "com.huawei.android.pushagent"
            java.lang.String r2 = r8.n
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x003f
            java.lang.String r1 = "PushSelfShowLog"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "get small icon from "
            r2.<init>(r3)
            java.lang.String r3 = r8.n
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.huawei.android.pushagent.a.a.c.b(r1, r2)
            android.graphics.drawable.Icon r1 = c(r7, r8)
            if (r1 == 0) goto L_0x003f
            r0.setSmallIcon(r1)
            goto L_0x0046
        L_0x003f:
            int r1 = b(r7, r8)
            r0.setSmallIcon(r1)
        L_0x0046:
            android.content.pm.ApplicationInfo r1 = r7.getApplicationInfo()
            int r1 = r1.labelRes
            java.lang.String r2 = r8.q
            r0.setTicker(r2)
            long r2 = java.lang.System.currentTimeMillis()
            r0.setWhen(r2)
            r2 = 1
            r0.setAutoCancel(r2)
            r0.setDefaults(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r8.n
            r3.append(r4)
            java.lang.String r4 = r8.a
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = r8.a
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x00ca
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.String r5 = "groupMap key is "
            java.lang.String r6 = java.lang.String.valueOf(r3)
            java.lang.String r5 = r5.concat(r6)
            com.huawei.android.pushagent.a.a.c.a(r4, r5)
            java.util.HashMap r4 = a
            boolean r4 = r4.containsKey(r3)
            if (r4 != 0) goto L_0x009b
            java.util.HashMap r4 = a
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
            r4.put(r3, r5)
            goto L_0x00ca
        L_0x009b:
            java.util.HashMap r4 = a
            java.util.HashMap r5 = a
            java.lang.Object r5 = r5.get(r3)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            int r5 = r5 + r2
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.put(r3, r5)
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "groupMap.size:"
            r5.<init>(r6)
            java.util.HashMap r6 = a
            java.lang.Object r6 = r6.get(r3)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.huawei.android.pushagent.a.a.c.a(r4, r5)
        L_0x00ca:
            java.lang.String r4 = r8.s
            if (r4 == 0) goto L_0x00dc
            java.lang.String r4 = ""
            java.lang.String r5 = r8.s
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00d9
            goto L_0x00dc
        L_0x00d9:
            java.lang.String r1 = r8.s
            goto L_0x00e4
        L_0x00dc:
            android.content.res.Resources r4 = r7.getResources()
            java.lang.String r1 = r4.getString(r1)
        L_0x00e4:
            r0.setContentTitle(r1)
            java.lang.String r1 = r8.a
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0122
            java.util.HashMap r1 = a
            java.lang.Object r1 = r1.get(r3)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            if (r1 != r2) goto L_0x00fe
            goto L_0x0122
        L_0x00fe:
            java.util.HashMap r1 = a
            java.lang.Object r1 = r1.get(r3)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            android.content.res.Resources r3 = r7.getResources()
            java.lang.String r4 = "hwpush_message_hint"
            int r4 = com.huawei.android.pushselfshow.utils.d.b(r7, r4)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r5 = 0
            java.lang.Integer r6 = java.lang.Integer.valueOf(r1)
            r2[r5] = r6
            java.lang.String r1 = r3.getQuantityString(r4, r1, r2)
            goto L_0x0124
        L_0x0122:
            java.lang.String r1 = r8.q
        L_0x0124:
            r0.setContentText(r1)
            android.app.PendingIntent r10 = a(r7, r8, r9, r10)
            r0.setContentIntent(r10)
            android.app.PendingIntent r10 = b(r7, r8, r9, r11)
            r0.setDeleteIntent(r10)
            android.graphics.Bitmap r10 = d(r7, r8)
            if (r10 == 0) goto L_0x013e
            r0.setLargeIcon(r10)
        L_0x013e:
            java.lang.String r11 = "com.huawei.android.pushagent"
            java.lang.String r1 = r7.getPackageName()
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x0175
            android.os.Bundle r11 = new android.os.Bundle
            r11.<init>()
            java.lang.String r1 = r8.n
            java.lang.String r2 = "app"
            java.lang.String r3 = r8.p
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L_0x0165
            java.lang.String r2 = "cosa"
            java.lang.String r3 = r8.p
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0167
        L_0x0165:
            java.lang.String r1 = r8.A
        L_0x0167:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x0175
            java.lang.String r2 = "hw_origin_sender_package_name"
            r11.putString(r2, r1)
            r0.setExtras(r11)
        L_0x0175:
            android.app.Notification$Builder r7 = com.huawei.android.pushselfshow.c.c.a(r7, r0, r9, r8, r10)
            if (r7 != 0) goto L_0x017d
            r7 = 0
            return r7
        L_0x017d:
            android.app.Notification r7 = r0.getNotification()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.c.b.b(android.content.Context, com.huawei.android.pushselfshow.b.a, int, int, int):android.app.Notification");
    }

    private static PendingIntent b(Context context, com.huawei.android.pushselfshow.b.a aVar, int i, int i2) {
        Intent intent = new Intent("com.huawei.intent.action.PUSH");
        intent.putExtra("selfshow_info", aVar.c()).putExtra("selfshow_token", aVar.d()).putExtra("selfshow_event_id", "2").putExtra("selfshow_notify_id", i).setPackage(context.getPackageName()).setFlags(268435456);
        return PendingIntent.getBroadcast(context, i2, intent, 134217728);
    }

    private static Icon c(Context context, com.huawei.android.pushselfshow.b.a aVar) {
        try {
            return Icon.createWithBitmap(((BitmapDrawable) context.getPackageManager().getApplicationIcon(aVar.n)).getBitmap());
        } catch (NameNotFoundException e) {
            c.d("PushSelfShowLog", e.toString());
            return null;
        }
    }

    private static Bitmap d(Context context, com.huawei.android.pushselfshow.b.a aVar) {
        String str;
        String str2;
        StringBuilder sb;
        Bitmap bitmap;
        Bitmap bitmap2 = null;
        if (context == null || aVar == null) {
            return null;
        }
        try {
            if (aVar.r != null && aVar.r.length() > 0) {
                com.huawei.android.pushselfshow.utils.c.a aVar2 = new com.huawei.android.pushselfshow.utils.c.a();
                int i = 0;
                String str3 = aVar.r;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(aVar.a());
                if (!str3.equals(sb2.toString())) {
                    i = a(context, aVar.r, (String) ResUtils.DRAWABLE, (Object) new drawable());
                    if (i == 0) {
                        i = context.getResources().getIdentifier(aVar.r, ResUtils.DRAWABLE, "android");
                    }
                    StringBuilder sb3 = new StringBuilder("msg.notifyIcon is ");
                    sb3.append(aVar.r);
                    sb3.append(",and defaultIcon is ");
                    sb3.append(i);
                    c.a("PushSelfShowLog", sb3.toString());
                }
                if (i != 0) {
                    bitmap2 = BitmapFactory.decodeResource(context.getResources(), i);
                } else {
                    Bitmap a2 = aVar2.a(context, aVar.r);
                    c.a("PushSelfShowLog", "get bitmap from new downloaded ");
                    if (a2 != null) {
                        StringBuilder sb4 = new StringBuilder("height:");
                        sb4.append(a2.getHeight());
                        sb4.append(",width:");
                        sb4.append(a2.getWidth());
                        c.a("PushSelfShowLog", sb4.toString());
                        float a3 = a(context);
                        a2 = aVar2.a(context, a2, a3, a3);
                    }
                    if (a2 != null) {
                        bitmap2 = a2;
                    }
                }
            }
            if (bitmap2 != null || "com.huawei.android.pushagent".equals(aVar.n)) {
                bitmap = bitmap2;
            } else {
                StringBuilder sb5 = new StringBuilder("get left bitmap from ");
                sb5.append(aVar.n);
                c.b("PushSelfShowLog", sb5.toString());
                bitmap = ((BitmapDrawable) context.getPackageManager().getApplicationIcon(aVar.n)).getBitmap();
            }
            return bitmap;
        } catch (NameNotFoundException e) {
            str = "PushSelfShowLog";
            sb = new StringBuilder();
            str2 = e.toString();
            r6 = e;
            sb.append(str2);
            c.c(str, sb.toString(), r6);
            return null;
        } catch (Exception e2) {
            str = "PushSelfShowLog";
            sb = new StringBuilder();
            str2 = e2.toString();
            r6 = e2;
            sb.append(str2);
            c.c(str, sb.toString(), r6);
            return null;
        }
    }
}
