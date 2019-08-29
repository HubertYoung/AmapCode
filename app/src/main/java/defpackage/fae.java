package defpackage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build.VERSION;

/* renamed from: fae reason: default package */
/* compiled from: NotifyAdapterUtil */
public final class fae {
    private static int a = 20000000;
    private static NotificationManager b;

    /* JADX WARNING: type inference failed for: r6v9, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r6v20 */
    /* JADX WARNING: type inference failed for: r6v23 */
    /* JADX WARNING: type inference failed for: r6v24 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v9, types: [boolean, int]
      assigns: []
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], boolean, ?[int, byte, short, char], int]
      mth insns count: 301
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0312  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0360  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0378  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0387  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x03c4  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r18, java.util.List<android.graphics.Bitmap> r19, defpackage.ezp r20, long r21, int r23) {
        /*
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            java.lang.String r5 = "NotifyManager"
            java.lang.String r6 = "pushNotification"
            defpackage.fat.d(r5, r6)
            b(r18)
            fac r5 = defpackage.faf.a(r18)
            int r5 = r5.c()
            java.lang.String r6 = r2.k
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            r7 = 1
            if (r6 != 0) goto L_0x0032
            if (r1 == 0) goto L_0x0032
            int r6 = r19.size()
            if (r6 <= r7) goto L_0x0032
            java.lang.Object r6 = r1.get(r7)
            if (r6 == 0) goto L_0x0032
            r5 = 1
        L_0x0032:
            r11 = 0
            r12 = 2
            if (r5 != r12) goto L_0x01c4
            java.lang.String r5 = r18.getPackageName()
            java.lang.String r13 = r2.h
            java.lang.String r14 = r2.i
            android.content.pm.ApplicationInfo r15 = r18.getApplicationInfo()
            int r15 = r15.icon
            boolean r9 = r2.p
            java.lang.String r6 = "audio"
            java.lang.Object r6 = r0.getSystemService(r6)
            android.media.AudioManager r6 = (android.media.AudioManager) r6
            fac r7 = defpackage.faf.a(r18)
            int r7 = r7.a()
            if (r1 == 0) goto L_0x0084
            boolean r16 = r19.isEmpty()
            if (r16 != 0) goto L_0x0084
            java.lang.Object r11 = r1.get(r11)
            android.graphics.Bitmap r11 = (android.graphics.Bitmap) r11
            if (r11 == 0) goto L_0x0082
            if (r7 <= 0) goto L_0x0082
            android.content.res.Resources r10 = r18.getResources()
            android.graphics.Bitmap r10 = android.graphics.BitmapFactory.decodeResource(r10, r7)
            if (r10 == 0) goto L_0x0082
            int r12 = r10.getWidth()
            int r8 = r10.getHeight()
            r10.recycle()
            android.graphics.Bitmap r8 = defpackage.fah.a(r11, r12, r8)
            goto L_0x0085
        L_0x0082:
            r8 = r11
            goto L_0x0085
        L_0x0084:
            r8 = 0
        L_0x0085:
            int r10 = android.os.Build.VERSION.SDK_INT
            r11 = 26
            if (r10 < r11) goto L_0x00a7
            android.app.Notification$Builder r10 = new android.app.Notification$Builder
            java.lang.String r11 = "vivo_push_channel"
            r10.<init>(r0, r11)
            if (r7 <= 0) goto L_0x00a1
            android.os.Bundle r11 = new android.os.Bundle
            r11.<init>()
            java.lang.String r12 = "vivo.summaryIconRes"
            r11.putInt(r12, r7)
            r10.setExtras(r11)
        L_0x00a1:
            if (r8 == 0) goto L_0x00c3
            r10.setLargeIcon(r8)
            goto L_0x00c3
        L_0x00a7:
            android.app.Notification$Builder r10 = new android.app.Notification$Builder
            r10.<init>(r0)
            if (r8 == 0) goto L_0x00b2
            r10.setLargeIcon(r8)
            goto L_0x00c3
        L_0x00b2:
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 22
            if (r7 > r8) goto L_0x00c3
            android.content.res.Resources r7 = r18.getResources()
            android.graphics.Bitmap r7 = android.graphics.BitmapFactory.decodeResource(r7, r15)
            r10.setLargeIcon(r7)
        L_0x00c3:
            fac r7 = defpackage.faf.a(r18)
            int r7 = r7.b()
            if (r7 > 0) goto L_0x00ce
            goto L_0x00cf
        L_0x00ce:
            r15 = r7
        L_0x00cf:
            r10.setSmallIcon(r15)
            r10.setContentTitle(r13)
            r7 = 2
            r10.setPriority(r7)
            r10.setContentText(r14)
            if (r9 == 0) goto L_0x00e3
            long r7 = java.lang.System.currentTimeMillis()
            goto L_0x00e5
        L_0x00e3:
            r7 = 0
        L_0x00e5:
            r10.setWhen(r7)
            r10.setShowWhen(r9)
            r10.setTicker(r13)
            int r6 = r6.getRingerMode()
            int r7 = r2.j
            switch(r7) {
                case 2: goto L_0x012a;
                case 3: goto L_0x011a;
                case 4: goto L_0x00f9;
                default: goto L_0x00f7;
            }
        L_0x00f7:
            r6 = 1
            goto L_0x0131
        L_0x00f9:
            r7 = 2
            if (r6 != r7) goto L_0x010a
            r6 = 3
            r10.setDefaults(r6)
            r8 = 4
            long[] r6 = new long[r8]
            r6 = {0, 100, 200, 300} // fill-array
            r10.setVibrate(r6)
            goto L_0x00f7
        L_0x010a:
            r8 = 4
            r9 = 1
            if (r6 != r9) goto L_0x00f7
            r10.setDefaults(r7)
            long[] r6 = new long[r8]
            r6 = {0, 100, 200, 300} // fill-array
            r10.setVibrate(r6)
            goto L_0x00f7
        L_0x011a:
            r7 = 2
            r8 = 4
            if (r6 != r7) goto L_0x00f7
            r10.setDefaults(r7)
            long[] r6 = new long[r8]
            r6 = {0, 100, 200, 300} // fill-array
            r10.setVibrate(r6)
            goto L_0x00f7
        L_0x012a:
            r7 = 2
            if (r6 != r7) goto L_0x00f7
            r6 = 1
            r10.setDefaults(r6)
        L_0x0131:
            if (r1 == 0) goto L_0x0143
            int r7 = r19.size()
            if (r7 <= r6) goto L_0x0143
            java.lang.Object r1 = r1.get(r6)
            r9 = r1
            android.graphics.Bitmap r9 = (android.graphics.Bitmap) r9
            r1 = r23
            goto L_0x0146
        L_0x0143:
            r1 = r23
            r9 = 0
        L_0x0146:
            if (r1 == r6) goto L_0x015b
            if (r9 == 0) goto L_0x015b
            android.app.Notification$BigPictureStyle r1 = new android.app.Notification$BigPictureStyle
            r1.<init>()
            r1.setBigContentTitle(r13)
            r1.setSummaryText(r14)
            r1.bigPicture(r9)
            r10.setStyle(r1)
        L_0x015b:
            r10.setAutoCancel(r6)
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r6 = "com.vivo.pushservice.action.RECEIVE"
            r1.<init>(r6)
            java.lang.String r6 = r18.getPackageName()
            r1.setPackage(r6)
            java.lang.String r6 = r18.getPackageName()
            java.lang.String r7 = "com.vivo.push.sdk.service.CommandService"
            r1.setClassName(r6, r7)
            java.lang.String r6 = "command_type"
            java.lang.String r7 = "reflect_receiver"
            r1.putExtra(r6, r7)
            exp r6 = new exp
            r6.<init>(r5, r3, r2)
            r6.a(r1)
            long r5 = android.os.SystemClock.uptimeMillis()
            int r2 = (int) r5
            r5 = 268435456(0x10000000, float:2.5243549E-29)
            android.app.PendingIntent r0 = android.app.PendingIntent.getService(r0, r2, r1, r5)
            r10.setContentIntent(r0)
            android.app.Notification r0 = r10.build()
            ezv r1 = defpackage.ezv.a()
            int r1 = r1.i
            android.app.NotificationManager r2 = b
            if (r2 == 0) goto L_0x01c3
            if (r1 != 0) goto L_0x01aa
            android.app.NotificationManager r1 = b
            int r2 = a
            r1.notify(r2, r0)
            return
        L_0x01aa:
            r2 = 1
            if (r1 != r2) goto L_0x01b4
            android.app.NotificationManager r1 = b
            int r2 = (int) r3
            r1.notify(r2, r0)
            return
        L_0x01b4:
            java.lang.String r0 = "NotifyManager"
            java.lang.String r2 = "unknow notify style "
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r1 = r2.concat(r1)
            defpackage.fat.a(r0, r1)
        L_0x01c3:
            return
        L_0x01c4:
            r6 = 1
            if (r5 != r6) goto L_0x03ed
            android.content.res.Resources r5 = r18.getResources()
            java.lang.String r6 = r18.getPackageName()
            java.lang.String r7 = r2.h
            fac r8 = defpackage.faf.a(r18)
            int r8 = r8.a()
            android.content.pm.ApplicationInfo r9 = r18.getApplicationInfo()
            int r9 = r9.icon
            int r10 = android.os.Build.VERSION.SDK_INT
            r12 = 26
            if (r10 < r12) goto L_0x0200
            android.app.Notification$Builder r10 = new android.app.Notification$Builder
            java.lang.String r12 = "vivo_push_channel"
            r10.<init>(r0, r12)
            if (r8 <= 0) goto L_0x01fb
            android.os.Bundle r12 = new android.os.Bundle
            r12.<init>()
            java.lang.String r13 = "vivo.summaryIconRes"
            r12.putInt(r13, r8)
            r10.setExtras(r12)
        L_0x01fb:
            android.app.Notification r10 = r10.build()
            goto L_0x0205
        L_0x0200:
            android.app.Notification r10 = new android.app.Notification
            r10.<init>()
        L_0x0205:
            r12 = 2
            r10.priority = r12
            r12 = 16
            r10.flags = r12
            r10.tickerText = r7
            fac r13 = defpackage.faf.a(r18)
            int r13 = r13.b()
            if (r13 > 0) goto L_0x0219
            r13 = r9
        L_0x0219:
            r10.icon = r13
            android.widget.RemoteViews r13 = new android.widget.RemoteViews
            fad r14 = defpackage.faf.b(r18)
            int r14 = r14.a()
            r13.<init>(r6, r14)
            java.lang.String r14 = "notify_title"
            java.lang.String r15 = "id"
            int r14 = r5.getIdentifier(r14, r15, r6)
            r13.setTextViewText(r14, r7)
            java.lang.String r7 = "notify_title"
            java.lang.String r14 = "id"
            int r7 = r5.getIdentifier(r7, r14, r6)
            fad r14 = defpackage.faf.b(r18)
            int r14 = r14.b()
            r13.setTextColor(r7, r14)
            java.lang.String r7 = "notify_msg"
            java.lang.String r14 = "id"
            int r7 = r5.getIdentifier(r7, r14, r6)
            java.lang.String r14 = r2.i
            r13.setTextViewText(r7, r14)
            boolean r7 = r2.p
            r14 = 8
            if (r7 == 0) goto L_0x0282
            java.text.SimpleDateFormat r7 = new java.text.SimpleDateFormat
            java.lang.String r15 = "HH:mm"
            java.util.Locale r12 = java.util.Locale.CHINA
            r7.<init>(r15, r12)
            java.util.Date r12 = new java.util.Date
            r12.<init>()
            java.lang.String r7 = r7.format(r12)
            java.lang.String r12 = "notify_when"
            java.lang.String r15 = "id"
            int r12 = r5.getIdentifier(r12, r15, r6)
            r13.setTextViewText(r12, r7)
            java.lang.String r7 = "notify_when"
            java.lang.String r12 = "id"
            int r7 = r5.getIdentifier(r7, r12, r6)
            r13.setViewVisibility(r7, r11)
            goto L_0x028d
        L_0x0282:
            java.lang.String r7 = "notify_when"
            java.lang.String r12 = "id"
            int r7 = r5.getIdentifier(r7, r12, r6)
            r13.setViewVisibility(r7, r14)
        L_0x028d:
            fad r7 = defpackage.faf.b(r18)
            int r7 = r7.c()
            r13.setViewVisibility(r7, r11)
            if (r1 == 0) goto L_0x02ac
            boolean r12 = r19.isEmpty()
            if (r12 != 0) goto L_0x02ac
            java.lang.Object r12 = r1.get(r11)
            android.graphics.Bitmap r12 = (android.graphics.Bitmap) r12
            if (r12 == 0) goto L_0x02ac
            r13.setImageViewBitmap(r7, r12)
            goto L_0x02b2
        L_0x02ac:
            if (r8 > 0) goto L_0x02af
            r8 = r9
        L_0x02af:
            r13.setImageViewResource(r7, r8)
        L_0x02b2:
            if (r1 == 0) goto L_0x02c3
            int r7 = r19.size()
            r8 = 1
            if (r7 <= r8) goto L_0x02c3
            java.lang.Object r1 = r1.get(r8)
            r9 = r1
            android.graphics.Bitmap r9 = (android.graphics.Bitmap) r9
            goto L_0x02c4
        L_0x02c3:
            r9 = 0
        L_0x02c4:
            if (r9 == 0) goto L_0x0312
            java.lang.String r1 = r2.k
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x02fb
            java.lang.String r1 = "notify_content"
            java.lang.String r7 = "id"
            int r1 = r5.getIdentifier(r1, r7, r6)
            r13.setViewVisibility(r1, r14)
            java.lang.String r1 = "notify_cover"
            java.lang.String r7 = "id"
            int r1 = r5.getIdentifier(r1, r7, r6)
            r13.setViewVisibility(r1, r14)
            java.lang.String r1 = "notify_pure_cover"
            java.lang.String r7 = "id"
            int r1 = r5.getIdentifier(r1, r7, r6)
            r13.setViewVisibility(r1, r11)
            java.lang.String r1 = "notify_pure_cover"
            java.lang.String r7 = "id"
            int r1 = r5.getIdentifier(r1, r7, r6)
            r13.setImageViewBitmap(r1, r9)
            goto L_0x031d
        L_0x02fb:
            java.lang.String r1 = "notify_cover"
            java.lang.String r7 = "id"
            int r1 = r5.getIdentifier(r1, r7, r6)
            r13.setViewVisibility(r1, r11)
            java.lang.String r1 = "notify_cover"
            java.lang.String r7 = "id"
            int r1 = r5.getIdentifier(r1, r7, r6)
            r13.setImageViewBitmap(r1, r9)
            goto L_0x031d
        L_0x0312:
            java.lang.String r1 = "notify_cover"
            java.lang.String r7 = "id"
            int r1 = r5.getIdentifier(r1, r7, r6)
            r13.setViewVisibility(r1, r14)
        L_0x031d:
            r10.contentView = r13
            int r1 = android.os.Build.VERSION.SDK_INT
            r5 = 16
            if (r1 < r5) goto L_0x032f
            java.lang.String r1 = r2.k
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x032f
            r10.bigContentView = r13
        L_0x032f:
            java.lang.String r1 = "audio"
            java.lang.Object r1 = r0.getSystemService(r1)
            android.media.AudioManager r1 = (android.media.AudioManager) r1
            int r5 = r1.getRingerMode()
            int r1 = r1.getVibrateSetting(r11)
            java.lang.String r7 = "NotifyManager"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "ringMode="
            r8.<init>(r9)
            r8.append(r5)
            java.lang.String r9 = " callVibrateSetting="
            r8.append(r9)
            r8.append(r1)
            java.lang.String r8 = r8.toString()
            defpackage.fat.d(r7, r8)
            int r7 = r2.j
            switch(r7) {
                case 2: goto L_0x0387;
                case 3: goto L_0x0378;
                case 4: goto L_0x0360;
                default: goto L_0x035f;
            }
        L_0x035f:
            goto L_0x038d
        L_0x0360:
            r7 = 2
            if (r5 != r7) goto L_0x0367
            r8 = 1
            r10.defaults = r8
            goto L_0x0368
        L_0x0367:
            r8 = 1
        L_0x0368:
            if (r1 != r8) goto L_0x038d
            int r1 = r10.defaults
            r1 = r1 | r7
            r10.defaults = r1
            r5 = 4
            long[] r1 = new long[r5]
            r1 = {0, 100, 200, 300} // fill-array
            r10.vibrate = r1
            goto L_0x038d
        L_0x0378:
            r5 = 4
            r7 = 2
            r8 = 1
            if (r1 != r8) goto L_0x038d
            r10.defaults = r7
            long[] r1 = new long[r5]
            r1 = {0, 100, 200, 300} // fill-array
            r10.vibrate = r1
            goto L_0x038d
        L_0x0387:
            r7 = 2
            r8 = 1
            if (r5 != r7) goto L_0x038d
            r10.defaults = r8
        L_0x038d:
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r5 = "com.vivo.pushservice.action.RECEIVE"
            r1.<init>(r5)
            java.lang.String r5 = r18.getPackageName()
            r1.setPackage(r5)
            java.lang.String r5 = r18.getPackageName()
            java.lang.String r7 = "com.vivo.push.sdk.service.CommandService"
            r1.setClassName(r5, r7)
            java.lang.String r5 = "command_type"
            java.lang.String r7 = "reflect_receiver"
            r1.putExtra(r5, r7)
            exp r5 = new exp
            r5.<init>(r6, r3, r2)
            r5.a(r1)
            long r5 = android.os.SystemClock.uptimeMillis()
            int r2 = (int) r5
            r5 = 268435456(0x10000000, float:2.5243549E-29)
            android.app.PendingIntent r0 = android.app.PendingIntent.getService(r0, r2, r1, r5)
            r10.contentIntent = r0
            android.app.NotificationManager r0 = b
            if (r0 == 0) goto L_0x03ed
            ezv r0 = defpackage.ezv.a()
            int r0 = r0.i
            if (r0 != 0) goto L_0x03d4
            android.app.NotificationManager r0 = b
            int r1 = a
            r0.notify(r1, r10)
            return
        L_0x03d4:
            r1 = 1
            if (r0 != r1) goto L_0x03de
            android.app.NotificationManager r0 = b
            int r1 = (int) r3
            r0.notify(r1, r10)
            return
        L_0x03de:
            java.lang.String r1 = "NotifyManager"
            java.lang.String r2 = "unknow notify style "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r2.concat(r0)
            defpackage.fat.a(r1, r0)
        L_0x03ed:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fae.a(android.content.Context, java.util.List, ezp, long, int):void");
    }

    private static synchronized void b(Context context) {
        synchronized (fae.class) {
            if (b == null) {
                b = (NotificationManager) context.getSystemService("notification");
            }
            if (VERSION.SDK_INT >= 26 && b != null) {
                NotificationChannel notificationChannel = b.getNotificationChannel("default");
                if (notificationChannel != null) {
                    CharSequence name = notificationChannel.getName();
                    if ("推送通知".equals(name) || "PUSH".equals(name)) {
                        b.deleteNotificationChannel("default");
                    }
                }
                NotificationChannel notificationChannel2 = new NotificationChannel("vivo_push_channel", context.getResources().getConfiguration().locale.getLanguage().endsWith("zh") ? "推送通知" : "PUSH", 4);
                notificationChannel2.setLightColor(-16711936);
                notificationChannel2.enableVibration(true);
                notificationChannel2.setLockscreenVisibility(1);
                b.createNotificationChannel(notificationChannel2);
            }
        }
    }

    public static boolean a(Context context, int i) {
        int i2 = ezv.a().i;
        if (i2 == 0) {
            long b2 = fba.b().b("com.vivo.push.notify_key", -1);
            if (b2 == ((long) i)) {
                fat.d("NotifyManager", "undo showed message ".concat(String.valueOf(i)));
                fat.a(context, "回收已展示的通知： ".concat(String.valueOf(i)));
                return b(context, a);
            }
            StringBuilder sb = new StringBuilder("current showing message id ");
            sb.append(b2);
            sb.append(" not match ");
            sb.append(i);
            fat.d("NotifyManager", sb.toString());
            StringBuilder sb2 = new StringBuilder("与已展示的通知");
            sb2.append(b2);
            sb2.append("与待回收的通知");
            sb2.append(i);
            sb2.append("不匹配");
            fat.a(context, sb2.toString());
            return false;
        } else if (i2 == 1) {
            return b(context, i);
        } else {
            fat.a((String) "NotifyManager", "unknow cancle notify style ".concat(String.valueOf(i2)));
            return false;
        }
    }

    private static boolean b(Context context, int i) {
        b(context);
        if (b == null) {
            return false;
        }
        b.cancel(i);
        return true;
    }

    public static void a(Context context) {
        b(context, a);
    }
}
