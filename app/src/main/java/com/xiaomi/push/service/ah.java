package com.xiaomi.push.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.xiaomi.channel.commonutils.android.a.C0073a;
import com.xiaomi.channel.commonutils.android.f;
import com.xiaomi.channel.commonutils.misc.h;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.u;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ah {
    public static long a;
    private static final LinkedList<Pair<Integer, af>> b = new LinkedList<>();

    public static class a {
        Notification a;
        long b = 0;
    }

    public static class b {
        public String a;
        public long b = 0;
    }

    private static int a(Context context, String str, String str2) {
        if (str.equals(context.getPackageName())) {
            return context.getResources().getIdentifier(str2, ResUtils.DRAWABLE, str);
        }
        return 0;
    }

    private static Notification a(Notification notification) {
        Object a2 = com.xiaomi.channel.commonutils.reflect.a.a((Object) notification, (String) "extraNotification");
        if (a2 != null) {
            com.xiaomi.channel.commonutils.reflect.a.a(a2, (String) "setCustomizedIcon", Boolean.TRUE);
        }
        return notification;
    }

    private static Notification a(Notification notification, String str) {
        try {
            Field declaredField = Notification.class.getDeclaredField("extraNotification");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(notification);
            Method declaredMethod = obj.getClass().getDeclaredMethod("setTargetPkg", new Class[]{CharSequence.class});
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, new Object[]{str});
            return notification;
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
            return notification;
        }
    }

    private static PendingIntent a(Context context, af afVar, u uVar, byte[] bArr) {
        if (uVar != null && !TextUtils.isEmpty(uVar.g)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(uVar.g));
            intent.addFlags(268435456);
            return PendingIntent.getActivity(context, 0, intent, 134217728);
        } else if (b(afVar)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.xiaomi.xmsf", "com.xiaomi.mipush.sdk.PushMessageHandler"));
            intent2.putExtra("mipush_payload", bArr);
            intent2.putExtra("mipush_notified", true);
            intent2.addCategory(String.valueOf(uVar.q()));
            return PendingIntent.getService(context, 0, intent2, 134217728);
        } else {
            Intent intent3 = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
            intent3.setComponent(new ComponentName(afVar.f, "com.xiaomi.mipush.sdk.PushMessageHandler"));
            intent3.putExtra("mipush_payload", bArr);
            intent3.putExtra("mipush_notified", true);
            intent3.addCategory(String.valueOf(uVar.q()));
            return PendingIntent.getService(context, 0, intent3, 134217728);
        }
    }

    private static Bitmap a(Context context, int i) {
        return a(context.getResources().getDrawable(i));
    }

    public static Bitmap a(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int i = 1;
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 1;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicHeight > 0) {
            i = intrinsicHeight;
        }
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, i, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x015a  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x017b A[Catch:{ Exception -> 0x01b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01c3 A[SYNTHETIC, Splitter:B:75:0x01c3] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0204  */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.xiaomi.push.service.ah.a a(android.content.Context r18, com.xiaomi.xmpush.thrift.af r19, byte[] r20, android.widget.RemoteViews r21, android.app.PendingIntent r22) {
        /*
            r1 = r18
            r2 = r21
            com.xiaomi.push.service.ah$a r3 = new com.xiaomi.push.service.ah$a
            r3.<init>()
            com.xiaomi.xmpush.thrift.u r4 = r19.m()
            java.lang.String r5 = a(r19)
            java.util.Map r6 = r4.s()
            android.app.Notification$Builder r7 = new android.app.Notification$Builder
            r7.<init>(r1)
            int r8 = android.os.Build.VERSION.SDK_INT
            r9 = 26
            if (r8 < r9) goto L_0x0025
            java.lang.String r8 = "default"
            r7.setChannelId(r8)
        L_0x0025:
            java.lang.String[] r8 = a(r1, r4)
            r10 = 0
            r11 = r8[r10]
            r7.setContentTitle(r11)
            r11 = 1
            r12 = r8[r11]
            r7.setContentText(r12)
            if (r2 == 0) goto L_0x003b
            r7.setContent(r2)
            goto L_0x004f
        L_0x003b:
            int r2 = android.os.Build.VERSION.SDK_INT
            r12 = 16
            if (r2 < r12) goto L_0x004f
            android.app.Notification$BigTextStyle r2 = new android.app.Notification$BigTextStyle
            r2.<init>()
            r8 = r8[r11]
            android.app.Notification$BigTextStyle r2 = r2.bigText(r8)
            r7.setStyle(r2)
        L_0x004f:
            long r12 = java.lang.System.currentTimeMillis()
            r7.setWhen(r12)
            r2 = 0
            if (r6 != 0) goto L_0x005b
            r8 = r2
            goto L_0x0063
        L_0x005b:
            java.lang.String r8 = "notification_show_when"
            java.lang.Object r8 = r6.get(r8)
            java.lang.String r8 = (java.lang.String) r8
        L_0x0063:
            boolean r12 = android.text.TextUtils.isEmpty(r8)
            r13 = 24
            if (r12 == 0) goto L_0x0075
            int r8 = android.os.Build.VERSION.SDK_INT
            if (r8 < r13) goto L_0x0072
            r7.setShowWhen(r11)
        L_0x0072:
            r8 = r22
            goto L_0x007d
        L_0x0075:
            boolean r8 = java.lang.Boolean.parseBoolean(r8)
            r7.setShowWhen(r8)
            goto L_0x0072
        L_0x007d:
            r7.setContentIntent(r8)
            java.lang.String r8 = "mipush_notification"
            int r8 = a(r1, r5, r8)
            java.lang.String r12 = "mipush_small_notification"
            int r12 = a(r1, r5, r12)
            if (r8 <= 0) goto L_0x009b
            if (r12 <= 0) goto L_0x009b
            android.graphics.Bitmap r8 = a(r1, r8)
            r7.setLargeIcon(r8)
            r7.setSmallIcon(r12)
            goto L_0x00a2
        L_0x009b:
            int r8 = f(r1, r5)
            r7.setSmallIcon(r8)
        L_0x00a2:
            if (r6 != 0) goto L_0x00a6
            r8 = r2
            goto L_0x00ae
        L_0x00a6:
            java.lang.String r8 = "__dynamic_icon_uri"
            java.lang.Object r8 = r6.get(r8)
            java.lang.String r8 = (java.lang.String) r8
        L_0x00ae:
            if (r6 == 0) goto L_0x00c0
            java.lang.String r12 = "__adiom"
            java.lang.Object r12 = r6.get(r12)
            java.lang.String r12 = (java.lang.String) r12
            boolean r12 = java.lang.Boolean.parseBoolean(r12)
            if (r12 == 0) goto L_0x00c0
            r12 = 1
            goto L_0x00c1
        L_0x00c0:
            r12 = 0
        L_0x00c1:
            if (r12 != 0) goto L_0x00cc
            boolean r12 = com.xiaomi.channel.commonutils.android.f.a()
            if (r12 != 0) goto L_0x00ca
            goto L_0x00cc
        L_0x00ca:
            r12 = 0
            goto L_0x00cd
        L_0x00cc:
            r12 = 1
        L_0x00cd:
            if (r8 == 0) goto L_0x00f1
            if (r12 == 0) goto L_0x00f1
            java.lang.String r12 = "http"
            boolean r12 = r8.startsWith(r12)
            if (r12 == 0) goto L_0x00e6
            com.xiaomi.push.service.am$b r8 = com.xiaomi.push.service.am.a(r1, r8)
            if (r8 == 0) goto L_0x00ea
            android.graphics.Bitmap r2 = r8.a
            long r14 = r8.b
            r3.b = r14
            goto L_0x00ea
        L_0x00e6:
            android.graphics.Bitmap r2 = com.xiaomi.push.service.am.b(r1, r8)
        L_0x00ea:
            if (r2 == 0) goto L_0x00f1
            r7.setLargeIcon(r2)
            r2 = 1
            goto L_0x00f2
        L_0x00f1:
            r2 = 0
        L_0x00f2:
            if (r6 == 0) goto L_0x0122
            int r8 = android.os.Build.VERSION.SDK_INT
            if (r8 < r13) goto L_0x0122
            java.lang.String r8 = "notification_group"
            java.lang.Object r8 = r6.get(r8)
            java.lang.String r8 = (java.lang.String) r8
            java.lang.String r12 = "notification_is_summary"
            java.lang.Object r12 = r6.get(r12)
            java.lang.String r12 = (java.lang.String) r12
            boolean r12 = java.lang.Boolean.parseBoolean(r12)
            java.lang.String r13 = "setGroup"
            java.lang.Object[] r14 = new java.lang.Object[r11]
            r14[r10] = r8
            com.xiaomi.channel.commonutils.reflect.a.a(r7, r13, r14)
            java.lang.String r8 = "setGroupSummary"
            java.lang.Object[] r13 = new java.lang.Object[r11]
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)
            r13[r10] = r12
            com.xiaomi.channel.commonutils.reflect.a.a(r7, r8, r13)
        L_0x0122:
            if (r6 == 0) goto L_0x01de
            int r8 = android.os.Build.VERSION.SDK_INT
            if (r8 < r9) goto L_0x01de
            int r8 = b(r6)
            if (r8 <= 0) goto L_0x013e
            java.lang.String r12 = "setTimeoutAfter"
            java.lang.Object[] r13 = new java.lang.Object[r11]
            int r8 = r8 * 1000
            long r14 = (long) r8
            java.lang.Long r8 = java.lang.Long.valueOf(r14)
            r13[r10] = r8
            com.xiaomi.channel.commonutils.reflect.a.a(r7, r12, r13)
        L_0x013e:
            java.lang.String r8 = "channel_id"
            java.lang.Object r8 = r6.get(r8)
            java.lang.String r8 = (java.lang.String) r8
            boolean r12 = android.text.TextUtils.isEmpty(r8)
            if (r12 == 0) goto L_0x0154
            android.content.pm.ApplicationInfo r12 = r18.getApplicationInfo()
            int r12 = r12.targetSdkVersion
            if (r12 < r9) goto L_0x01b5
        L_0x0154:
            boolean r9 = android.text.TextUtils.isEmpty(r8)
            if (r9 == 0) goto L_0x015c
            java.lang.String r8 = "default_channel_mi_push"
        L_0x015c:
            java.lang.String r9 = "setChannelId"
            java.lang.Object[] r12 = new java.lang.Object[r11]
            r12[r10] = r8
            com.xiaomi.channel.commonutils.reflect.a.a(r7, r9, r12)
            java.lang.String r9 = "notification"
            java.lang.String r12 = "notification"
            java.lang.Object r12 = r1.getSystemService(r12)
            android.app.NotificationManager r12 = (android.app.NotificationManager) r12
            java.lang.String r13 = "getNotificationChannel"
            java.lang.Object[] r14 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x01b0 }
            r14[r10] = r8     // Catch:{ Exception -> 0x01b0 }
            java.lang.Object r13 = com.xiaomi.channel.commonutils.reflect.a.a(r12, r13, r14)     // Catch:{ Exception -> 0x01b0 }
            if (r13 != 0) goto L_0x01b5
            java.lang.String r13 = "android.app.NotificationChannel"
            java.lang.Class r13 = java.lang.Class.forName(r13)     // Catch:{ Exception -> 0x01b0 }
            r14 = 3
            java.lang.Class[] r15 = new java.lang.Class[r14]     // Catch:{ Exception -> 0x01b0 }
            java.lang.Class<java.lang.String> r16 = java.lang.String.class
            r15[r10] = r16     // Catch:{ Exception -> 0x01b0 }
            java.lang.Class<java.lang.CharSequence> r16 = java.lang.CharSequence.class
            r15[r11] = r16     // Catch:{ Exception -> 0x01b0 }
            java.lang.Class r16 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x01b0 }
            r17 = 2
            r15[r17] = r16     // Catch:{ Exception -> 0x01b0 }
            java.lang.reflect.Constructor r13 = r13.getConstructor(r15)     // Catch:{ Exception -> 0x01b0 }
            java.lang.Object[] r15 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x01b0 }
            r15[r10] = r8     // Catch:{ Exception -> 0x01b0 }
            r15[r11] = r9     // Catch:{ Exception -> 0x01b0 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r14)     // Catch:{ Exception -> 0x01b0 }
            r15[r17] = r8     // Catch:{ Exception -> 0x01b0 }
            java.lang.Object r8 = r13.newInstance(r15)     // Catch:{ Exception -> 0x01b0 }
            java.lang.String r9 = "createNotificationChannel"
            java.lang.Object[] r13 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x01b0 }
            r13[r10] = r8     // Catch:{ Exception -> 0x01b0 }
            com.xiaomi.channel.commonutils.reflect.a.a(r12, r9, r13)     // Catch:{ Exception -> 0x01b0 }
            goto L_0x01b5
        L_0x01b0:
            r0 = move-exception
            r8 = r0
            com.xiaomi.channel.commonutils.logger.b.a(r8)
        L_0x01b5:
            java.lang.String r8 = "background_color"
            java.lang.Object r8 = r6.get(r8)
            java.lang.String r8 = (java.lang.String) r8
            boolean r9 = android.text.TextUtils.isEmpty(r8)
            if (r9 != 0) goto L_0x01de
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ Exception -> 0x01d9 }
            r7.setOngoing(r11)     // Catch:{ Exception -> 0x01d9 }
            r7.setColor(r8)     // Catch:{ Exception -> 0x01d9 }
            java.lang.String r8 = "setColorized"
            java.lang.Object[] r9 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x01d9 }
            java.lang.Boolean r12 = java.lang.Boolean.TRUE     // Catch:{ Exception -> 0x01d9 }
            r9[r10] = r12     // Catch:{ Exception -> 0x01d9 }
            com.xiaomi.channel.commonutils.reflect.a.a(r7, r8, r9)     // Catch:{ Exception -> 0x01d9 }
            goto L_0x01de
        L_0x01d9:
            r0 = move-exception
            r8 = r0
            com.xiaomi.channel.commonutils.logger.b.a(r8)
        L_0x01de:
            r7.setAutoCancel(r11)
            long r8 = java.lang.System.currentTimeMillis()
            if (r6 == 0) goto L_0x01fa
            java.lang.String r10 = "ticker"
            boolean r10 = r6.containsKey(r10)
            if (r10 == 0) goto L_0x01fa
            java.lang.String r10 = "ticker"
            java.lang.Object r10 = r6.get(r10)
            java.lang.CharSequence r10 = (java.lang.CharSequence) r10
            r7.setTicker(r10)
        L_0x01fa:
            long r12 = a
            long r12 = r8 - r12
            r14 = 10000(0x2710, double:4.9407E-320)
            int r10 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r10 <= 0) goto L_0x0244
            a = r8
            int r4 = r4.f
            boolean r8 = e(r1, r5)
            if (r8 == 0) goto L_0x0212
            int r4 = c(r1, r5)
        L_0x0212:
            r7.setDefaults(r4)
            if (r6 == 0) goto L_0x0244
            r1 = r4 & 1
            if (r1 == 0) goto L_0x0244
            java.lang.String r1 = "sound_uri"
            java.lang.Object r1 = r6.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            boolean r6 = android.text.TextUtils.isEmpty(r1)
            if (r6 != 0) goto L_0x0244
            java.lang.String r6 = "android.resource://"
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r6.concat(r5)
            boolean r5 = r1.startsWith(r5)
            if (r5 == 0) goto L_0x0244
            r4 = r4 ^ r11
            r7.setDefaults(r4)
            android.net.Uri r1 = android.net.Uri.parse(r1)
            r7.setSound(r1)
        L_0x0244:
            android.app.Notification r1 = r7.getNotification()
            if (r2 == 0) goto L_0x0253
            boolean r2 = com.xiaomi.channel.commonutils.android.f.a()
            if (r2 == 0) goto L_0x0253
            a(r1)
        L_0x0253:
            r3.a = r1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ah.a(android.content.Context, com.xiaomi.xmpush.thrift.af, byte[], android.widget.RemoteViews, android.app.PendingIntent):com.xiaomi.push.service.ah$a");
    }

    public static b a(Context context, af afVar, byte[] bArr) {
        Notification notification;
        String str;
        Context context2 = context;
        af afVar2 = afVar;
        byte[] bArr2 = bArr;
        b bVar = new b();
        if (com.xiaomi.channel.commonutils.android.a.c(context2, a(afVar)) == C0073a.NOT_ALLOWED) {
            StringBuilder sb = new StringBuilder("Do not notify because user block ");
            sb.append(a(afVar));
            sb.append("‘s notification");
            str = sb.toString();
        } else if (bi.a(context, afVar)) {
            String a2 = bi.a(afVar);
            StringBuilder sb2 = new StringBuilder("Do not notify because user block ");
            sb2.append(a2);
            sb2.append("‘s notification");
            str = sb2.toString();
        } else {
            NotificationManager notificationManager = (NotificationManager) context2.getSystemService("notification");
            u m = afVar.m();
            RemoteViews b2 = b(context, afVar, bArr);
            PendingIntent a3 = a(context2, afVar2, m, bArr2);
            if (a3 == null) {
                str = "The click PendingIntent is null. ";
            } else {
                String str2 = null;
                if (VERSION.SDK_INT >= 11) {
                    a a4 = a(context2, afVar2, bArr2, b2, a3);
                    bVar.b = a4.b;
                    bVar.a = a(afVar);
                    notification = a4.a;
                } else {
                    notification = new Notification(f(context2, a(afVar)), null, System.currentTimeMillis());
                    String[] a5 = a(context2, m);
                    try {
                        notification.getClass().getMethod("setLatestEventInfo", new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class}).invoke(notification, new Object[]{context2, a5[0], a5[1], a3});
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                    }
                    Map<String, String> s = m.s();
                    if (s != null && s.containsKey("ticker")) {
                        notification.tickerText = s.get("ticker");
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - a > 10000) {
                        a = currentTimeMillis;
                        int i = m.f;
                        if (e(context2, a(afVar))) {
                            i = c(context2, a(afVar));
                        }
                        notification.defaults = i;
                        if (!(s == null || (i & 1) == 0)) {
                            String str3 = s.get("sound_uri");
                            if (!TextUtils.isEmpty(str3)) {
                                StringBuilder sb3 = new StringBuilder("android.resource://");
                                sb3.append(a(afVar));
                                if (str3.startsWith(sb3.toString())) {
                                    notification.defaults = i ^ 1;
                                    notification.sound = Uri.parse(str3);
                                }
                            }
                        }
                    }
                    notification.flags |= 16;
                    if (b2 != null) {
                        notification.contentView = b2;
                    }
                }
                if (f.a() && VERSION.SDK_INT >= 19) {
                    if (!TextUtils.isEmpty(m.b())) {
                        notification.extras.putString("message_id", m.b());
                    }
                    String str4 = m.u() == null ? null : m.u().get("score_info");
                    if (!TextUtils.isEmpty(str4)) {
                        notification.extras.putString("score_info", str4);
                    }
                }
                if (m.s() != null) {
                    str2 = m.s().get("message_count");
                }
                if (f.a() && str2 != null) {
                    try {
                        a(notification, Integer.parseInt(str2));
                    } catch (NumberFormatException e2) {
                        com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
                    }
                }
                if ("com.xiaomi.xmsf".equals(context.getPackageName())) {
                    a(notification, a(afVar));
                }
                if ("com.xiaomi.xmsf".equals(a(afVar))) {
                    bi.a(context2, afVar2, notification);
                }
                int hashCode = ((a(afVar).hashCode() / 10) * 10) + m.q();
                notificationManager.notify(hashCode, notification);
                if (VERSION.SDK_INT < 26) {
                    h a6 = h.a(context);
                    a6.a(hashCode);
                    int b3 = b(m.s());
                    if (b3 > 0) {
                        a6.b(new ai(hashCode, notificationManager), b3);
                    }
                }
                Pair pair = new Pair(Integer.valueOf(hashCode), afVar2);
                synchronized (b) {
                    try {
                        b.add(pair);
                        if (b.size() > 100) {
                            b.remove();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return bVar;
            }
        }
        com.xiaomi.channel.commonutils.logger.b.a(str);
        return bVar;
    }

    static String a(af afVar) {
        if ("com.xiaomi.xmsf".equals(afVar.f)) {
            u m = afVar.m();
            if (!(m == null || m.s() == null)) {
                String str = m.s().get("miui_package_name");
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
            }
        }
        return afVar.f;
    }

    private static void a(Notification notification, int i) {
        Object a2 = com.xiaomi.channel.commonutils.reflect.a.a((Object) notification, (String) "extraNotification");
        if (a2 != null) {
            com.xiaomi.channel.commonutils.reflect.a.a(a2, (String) "setMessageCount", Integer.valueOf(i));
        }
    }

    public static void a(Context context, String str, int i) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int hashCode = ((str.hashCode() / 10) * 10) + i;
        LinkedList linkedList = new LinkedList();
        if (i >= 0) {
            notificationManager.cancel(hashCode);
        }
        synchronized (b) {
            Iterator it = b.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                af afVar = (af) pair.second;
                if (afVar != null) {
                    String a2 = a(afVar);
                    if (i >= 0) {
                        if (hashCode == ((Integer) pair.first).intValue()) {
                            if (!TextUtils.equals(a2, str)) {
                            }
                        }
                    } else if (i == -1 && TextUtils.equals(a2, str)) {
                        notificationManager.cancel(((Integer) pair.first).intValue());
                    }
                    linkedList.add(pair);
                }
            }
            if (b != null) {
                b.removeAll(linkedList);
                a(context, linkedList);
            }
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            LinkedList linkedList = new LinkedList();
            synchronized (b) {
                Iterator it = b.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    af afVar = (af) pair.second;
                    if (afVar != null) {
                        String a2 = a(afVar);
                        u m = afVar.m();
                        if (m != null && TextUtils.equals(a2, str)) {
                            String h = m.h();
                            String j = m.j();
                            if (!TextUtils.isEmpty(h) && !TextUtils.isEmpty(j) && a(str2, h) && a(str3, j)) {
                                notificationManager.cancel(((Integer) pair.first).intValue());
                                linkedList.add(pair);
                            }
                        }
                    }
                }
                if (b != null) {
                    b.removeAll(linkedList);
                    a(context, linkedList);
                }
            }
        }
    }

    public static void a(Context context, LinkedList<? extends Object> linkedList) {
        if (linkedList != null && linkedList.size() > 0) {
            bf.a(context, "category_clear_notification", "clear_notification", (long) linkedList.size(), "");
        }
    }

    public static boolean a(Context context, String str) {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo next : runningAppProcesses) {
                if (next.importance == 100 && Arrays.asList(next.pkgList).contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean a(String str, String str2) {
        return TextUtils.isEmpty(str) || str2.contains(str);
    }

    public static boolean a(Map<String, String> map) {
        if (map == null || !map.containsKey("notify_foreground")) {
            return true;
        }
        return "1".equals(map.get("notify_foreground"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0070, code lost:
        if (android.text.TextUtils.isEmpty(r3) == false) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x004e, code lost:
        if (android.text.TextUtils.isEmpty(r3) == false) goto L_0x0072;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String[] a(android.content.Context r3, com.xiaomi.xmpush.thrift.u r4) {
        /*
            java.lang.String r0 = r4.h()
            java.lang.String r1 = r4.j()
            java.util.Map r4 = r4.s()
            if (r4 == 0) goto L_0x0073
            android.content.res.Resources r2 = r3.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.content.res.Resources r3 = r3.getResources()
            android.util.DisplayMetrics r3 = r3.getDisplayMetrics()
            float r3 = r3.density
            float r2 = (float) r2
            float r2 = r2 / r3
            r3 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r3
            java.lang.Float r3 = java.lang.Float.valueOf(r2)
            int r3 = r3.intValue()
            r2 = 320(0x140, float:4.48E-43)
            if (r3 > r2) goto L_0x0051
            java.lang.String r3 = "title_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x0042
            r0 = r3
        L_0x0042:
            java.lang.String r3 = "description_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x0073
            goto L_0x0072
        L_0x0051:
            r2 = 360(0x168, float:5.04E-43)
            if (r3 <= r2) goto L_0x0073
            java.lang.String r3 = "title_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x0064
            r0 = r3
        L_0x0064:
            java.lang.String r3 = "description_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x0073
        L_0x0072:
            r1 = r3
        L_0x0073:
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]
            r4 = 0
            r3[r4] = r0
            r4 = 1
            r3[r4] = r1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ah.a(android.content.Context, com.xiaomi.xmpush.thrift.u):java.lang.String[]");
    }

    private static int b(Map<String, String> map) {
        String str = map == null ? null : map.get("timeout");
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    private static RemoteViews b(Context context, af afVar, byte[] bArr) {
        u m = afVar.m();
        String a2 = a(afVar);
        Map<String, String> s = m.s();
        if (s == null) {
            return null;
        }
        String str = s.get("layout_name");
        String str2 = s.get("layout_value");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(a2);
            int identifier = resourcesForApplication.getIdentifier(str, ResUtils.LAYOUT, a2);
            if (identifier == 0) {
                return null;
            }
            RemoteViews remoteViews = new RemoteViews(a2, identifier);
            try {
                JSONObject jSONObject = new JSONObject(str2);
                if (jSONObject.has("text")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("text");
                    Iterator<String> keys = jSONObject2.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        String string = jSONObject2.getString(next);
                        int identifier2 = resourcesForApplication.getIdentifier(next, "id", a2);
                        if (identifier2 > 0) {
                            remoteViews.setTextViewText(identifier2, string);
                        }
                    }
                }
                if (jSONObject.has("image")) {
                    JSONObject jSONObject3 = jSONObject.getJSONObject("image");
                    Iterator<String> keys2 = jSONObject3.keys();
                    while (keys2.hasNext()) {
                        String next2 = keys2.next();
                        String string2 = jSONObject3.getString(next2);
                        int identifier3 = resourcesForApplication.getIdentifier(next2, "id", a2);
                        int identifier4 = resourcesForApplication.getIdentifier(string2, ResUtils.DRAWABLE, a2);
                        if (identifier3 > 0) {
                            remoteViews.setImageViewResource(identifier3, identifier4);
                        }
                    }
                }
                if (jSONObject.has("time")) {
                    JSONObject jSONObject4 = jSONObject.getJSONObject("time");
                    Iterator<String> keys3 = jSONObject4.keys();
                    while (keys3.hasNext()) {
                        String next3 = keys3.next();
                        String string3 = jSONObject4.getString(next3);
                        if (string3.length() == 0) {
                            string3 = "yy-MM-dd hh:mm";
                        }
                        int identifier5 = resourcesForApplication.getIdentifier(next3, "id", a2);
                        if (identifier5 > 0) {
                            remoteViews.setTextViewText(identifier5, new SimpleDateFormat(string3).format(new Date(System.currentTimeMillis())));
                        }
                    }
                }
                return remoteViews;
            } catch (JSONException e) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                return null;
            }
        } catch (NameNotFoundException e2) {
            com.xiaomi.channel.commonutils.logger.b.a((Throwable) e2);
            return null;
        }
    }

    public static void b(Context context, String str) {
        a(context, str, -1);
    }

    static void b(Context context, String str, int i) {
        context.getSharedPreferences("pref_notify_type", 0).edit().putInt(str, i).commit();
    }

    public static boolean b(af afVar) {
        u m = afVar.m();
        return m != null && m.w();
    }

    static int c(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).getInt(str, Integer.MAX_VALUE);
    }

    static void d(Context context, String str) {
        context.getSharedPreferences("pref_notify_type", 0).edit().remove(str).commit();
    }

    static boolean e(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).contains(str);
    }

    private static int f(Context context, String str) {
        int a2 = a(context, str, (String) "mipush_notification");
        int a3 = a(context, str, (String) "mipush_small_notification");
        if (a2 <= 0) {
            a2 = a3 > 0 ? a3 : context.getApplicationInfo().icon;
        }
        return (a2 != 0 || VERSION.SDK_INT < 9) ? a2 : context.getApplicationInfo().logo;
    }
}
