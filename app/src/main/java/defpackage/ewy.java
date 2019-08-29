package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.List;

/* renamed from: ewy reason: default package */
/* compiled from: CommandBridge */
public final class ewy {
    public static void a(Context context, String str, fbh fbh) {
        boolean z;
        boolean a = fbh.a();
        exa a2 = exa.a(context, a ? "com.vivo.vms.upstageservice" : "com.vivo.vms.aidlservice");
        a2.b = faw.b(a2.c);
        if (TextUtils.isEmpty(a2.b)) {
            fat.c(a2.c, (String) "push pkgname is null");
            z = false;
        } else {
            a2.a = fbd.a(a2.c, a2.b) >= 1260;
            z = a2.a;
        }
        if (TextUtils.isEmpty(fbh.g)) {
            fbh.g = context.getPackageName();
        }
        if (z && !"com.vivo.pushservice".equals(context.getPackageName())) {
            ewx ewx = new ewx(fbh.g, str, new Bundle());
            fbh.c(ewx);
            if (!a2.a(ewx.a)) {
                fat.b((String) "CommandBridge", (String) "send command error by aidl");
                fat.c(context, (String) "send command error by aidl");
            } else {
                return;
            }
        }
        Intent intent = new Intent("com.vivo.pushservice.action.METHOD");
        intent.setPackage(str);
        intent.setClassName(str, a ? "com.vivo.push.sdk.service.UpstageService" : "com.vivo.push.sdk.service.PushService");
        ewx a3 = ewx.a(intent);
        if (a3 == null) {
            fat.b((String) "PushCommand", (String) "bundleWapper is null");
        } else {
            fbh.c(a3);
            Bundle bundle = a3.a;
            if (bundle != null) {
                intent.putExtras(bundle);
            }
        }
        try {
            a(context, intent, false);
        } catch (Exception e) {
            fat.a("CommandBridge", "CommandBridge startService exception: ", e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0093, code lost:
        if (r8 == false) goto L_0x0097;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r17, android.content.Intent r18, boolean r19) throws java.lang.Exception {
        /*
            r1 = r17
            r2 = r18
            if (r1 != 0) goto L_0x0015
            java.lang.String r1 = "CommandBridge"
            java.lang.String r2 = "enter startLinkProxyActivityOrService context is null"
            defpackage.fat.d(r1, r2)
            java.lang.Exception r1 = new java.lang.Exception
            java.lang.String r2 = "context is null"
            r1.<init>(r2)
            throw r1
        L_0x0015:
            java.lang.String r3 = r18.getPackage()
            java.lang.String r4 = r17.getPackageName()
            boolean r4 = r4.equals(r3)
            r5 = 0
            r6 = 1
            if (r4 != 0) goto L_0x0097
            ezl r4 = defpackage.ezl.a()
            ezn r4 = r4.a(r1)
            if (r4 == 0) goto L_0x003b
            java.lang.String r4 = r4.d()
            java.lang.String r7 = "CommandBridge"
            java.lang.String r8 = "get app suit Tag success"
            defpackage.fat.d(r7, r8)
            goto L_0x0043
        L_0x003b:
            java.lang.String r4 = "CommandBridge"
            java.lang.String r7 = "get app suit Tag is null"
            defpackage.fat.d(r4, r7)
            r4 = r5
        L_0x0043:
            java.lang.String r7 = "1"
            boolean r7 = r7.equals(r4)
            r8 = 0
            if (r7 != 0) goto L_0x0069
            java.lang.String r7 = "0"
            boolean r4 = r7.equals(r4)
            if (r4 == 0) goto L_0x0056
        L_0x0054:
            r4 = 1
            goto L_0x006a
        L_0x0056:
            boolean r4 = defpackage.fao.b()
            if (r4 == 0) goto L_0x0062
            boolean r4 = defpackage.fbd.c(r1, r3)
            if (r4 != 0) goto L_0x0054
        L_0x0062:
            boolean r4 = defpackage.fao.c()
            if (r4 == 0) goto L_0x0069
            goto L_0x0054
        L_0x0069:
            r4 = 0
        L_0x006a:
            if (r4 != 0) goto L_0x0096
            long r9 = defpackage.fbd.a(r1, r3)
            r11 = 100
            int r7 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r7 < 0) goto L_0x007e
            r13 = 200(0xc8, double:9.9E-322)
            int r7 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
            if (r7 >= 0) goto L_0x007e
            long r9 = r9 - r11
            goto L_0x008c
        L_0x007e:
            r11 = 1000(0x3e8, double:4.94E-321)
            long r13 = r9 / r11
            r15 = 2
            long r13 = r13 % r15
            r15 = 1
            int r7 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r7 != 0) goto L_0x008c
            long r9 = r9 - r11
        L_0x008c:
            r11 = 50
            int r7 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r7 < 0) goto L_0x0093
            r8 = 1
        L_0x0093:
            if (r8 != 0) goto L_0x0096
            goto L_0x0097
        L_0x0096:
            r6 = r4
        L_0x0097:
            if (r6 == 0) goto L_0x00ac
            r17.startService(r18)     // Catch:{ Exception -> 0x009d }
            return
        L_0x009d:
            r0 = move-exception
            java.lang.String r3 = "CommandBridge"
            java.lang.String r4 = "start service error"
            defpackage.fat.a(r3, r4, r0)
            r2.setComponent(r5)
            r17.sendBroadcast(r18)
            return
        L_0x00ac:
            android.content.Intent r4 = new android.content.Intent     // Catch:{ Exception -> 0x00db }
            r4.<init>()     // Catch:{ Exception -> 0x00db }
            if (r19 == 0) goto L_0x00bf
            android.content.ComponentName r6 = new android.content.ComponentName     // Catch:{ Exception -> 0x00db }
            java.lang.Class<com.vivo.push.sdk.LinkProxyClientActivity> r7 = com.vivo.push.sdk.LinkProxyClientActivity.class
            java.lang.String r7 = r7.getName()     // Catch:{ Exception -> 0x00db }
            r6.<init>(r3, r7)     // Catch:{ Exception -> 0x00db }
            goto L_0x00ca
        L_0x00bf:
            android.content.ComponentName r6 = new android.content.ComponentName     // Catch:{ Exception -> 0x00db }
            java.lang.Class<com.vivo.push.sdk.service.LinkProxyActivity> r7 = com.vivo.push.sdk.service.LinkProxyActivity.class
            java.lang.String r7 = r7.getName()     // Catch:{ Exception -> 0x00db }
            r6.<init>(r3, r7)     // Catch:{ Exception -> 0x00db }
        L_0x00ca:
            r4.setComponent(r6)     // Catch:{ Exception -> 0x00db }
            r3 = 402653184(0x18000000, float:1.6543612E-24)
            r4.addFlags(r3)     // Catch:{ Exception -> 0x00db }
            java.lang.String r3 = "previous_intent"
            r4.putExtra(r3, r2)     // Catch:{ Exception -> 0x00db }
            r1.startActivity(r4)     // Catch:{ Exception -> 0x00db }
            return
        L_0x00db:
            java.lang.String r3 = "CommandBridge"
            java.lang.String r4 = "start activity error"
            defpackage.fat.d(r3, r4)
            r17.startService(r18)     // Catch:{ Exception -> 0x00e6 }
            return
        L_0x00e6:
            r0 = move-exception
            java.lang.String r3 = "CommandBridge"
            java.lang.String r4 = "start service error"
            defpackage.fat.a(r3, r4, r0)
            r2.setComponent(r5)
            r17.sendBroadcast(r18)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ewy.a(android.content.Context, android.content.Intent, boolean):void");
    }

    private static boolean a(Context context, String str, String str2) {
        Intent intent = new Intent(str);
        intent.setPackage(str2);
        try {
            List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 576);
            if (queryBroadcastReceivers != null) {
                if (queryBroadcastReceivers.size() > 0) {
                    return true;
                }
            }
            StringBuilder sb = new StringBuilder("action check error：action>>");
            sb.append(str);
            sb.append(";pkgname>>");
            sb.append(str2);
            fat.b((String) "CommandBridge", sb.toString());
            return false;
        } catch (Exception unused) {
            fat.b((String) "CommandBridge", (String) "queryBroadcastReceivers error");
            return false;
        }
    }

    public static void a(Context context, fbh fbh, String str) {
        try {
            boolean a = faw.a(context, str, "com.vivo.pushclient.action.RECEIVE");
            String str2 = a ? "com.vivo.pushclient.action.RECEIVE" : "com.vivo.pushservice.action.RECEIVE";
            if (TextUtils.isEmpty(str)) {
                fat.c(context, (String) "消息接受者包名为空！");
                throw new Exception("消息接受者包名为空！");
            } else if (!a(context, str2, str)) {
                throw new Exception("校验action异常");
            } else {
                if (TextUtils.isEmpty(fbh.g)) {
                    fbh.g = context.getPackageName();
                }
                Intent intent = new Intent();
                intent.setFlags(1048576);
                if (!TextUtils.isEmpty(str2)) {
                    intent.setAction(str2);
                }
                intent.setPackage(str);
                intent.setClassName(str, a ? "com.vivo.push.sdk.service.CommandClientService" : "com.vivo.push.sdk.service.CommandService");
                fbh.a(intent);
                intent.putExtra("command_type", "reflect_receiver");
                a(context, intent, a);
            }
        } catch (Exception e) {
            fat.a("CommandBridge", "CommandBridge sendCommandToClient exception", e);
        }
    }
}
