package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.xmpush.thrift.aa;
import com.xiaomi.xmpush.thrift.af;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.an;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.t;
import com.xiaomi.xmpush.thrift.u;
import com.xiaomi.xmpush.thrift.w;
import com.xiaomi.xmpush.thrift.z;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TimeZone;
import org.apache.thrift.a;
import org.apache.thrift.f;

public class ah {
    private static ah a;
    private static Queue<String> c;
    private static Object d = new Object();
    private Context b;

    private ah(Context context) {
        this.b = context.getApplicationContext();
        if (this.b == null) {
            this.b = context;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x0137  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.Intent a(android.content.Context r4, java.lang.String r5, java.util.Map<java.lang.String, java.lang.String> r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x015f
            java.lang.String r1 = "notify_effect"
            boolean r1 = r6.containsKey(r1)
            if (r1 != 0) goto L_0x000c
            return r0
        L_0x000c:
            java.lang.String r1 = "notify_effect"
            java.lang.Object r1 = r6.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = com.xiaomi.push.service.at.a
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x003f
            android.content.pm.PackageManager r6 = r4.getPackageManager()     // Catch:{ Exception -> 0x0027 }
            android.content.Intent r5 = r6.getLaunchIntentForPackage(r5)     // Catch:{ Exception -> 0x0027 }
            r6 = r5
            goto L_0x0135
        L_0x0027:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r1 = "Cause: "
            r6.<init>(r1)
            java.lang.String r5 = r5.getMessage()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r5)
            goto L_0x0134
        L_0x003f:
            java.lang.String r2 = com.xiaomi.push.service.at.b
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00c2
            java.lang.String r1 = "intent_uri"
            boolean r1 = r6.containsKey(r1)
            if (r1 == 0) goto L_0x0074
            java.lang.String r1 = "intent_uri"
            java.lang.Object r6 = r6.get(r1)
            java.lang.String r6 = (java.lang.String) r6
            if (r6 == 0) goto L_0x0134
            r1 = 1
            android.content.Intent r6 = android.content.Intent.parseUri(r6, r1)     // Catch:{ URISyntaxException -> 0x0065 }
            r6.setPackage(r5)     // Catch:{ URISyntaxException -> 0x0063 }
            goto L_0x0135
        L_0x0063:
            r5 = move-exception
            goto L_0x0067
        L_0x0065:
            r5 = move-exception
            r6 = r0
        L_0x0067:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Cause: "
            r1.<init>(r2)
            java.lang.String r5 = r5.getMessage()
            goto L_0x0129
        L_0x0074:
            java.lang.String r1 = "class_name"
            boolean r1 = r6.containsKey(r1)
            if (r1 == 0) goto L_0x0134
            java.lang.String r1 = "class_name"
            java.lang.Object r1 = r6.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            android.content.Intent r2 = new android.content.Intent
            r2.<init>()
            android.content.ComponentName r3 = new android.content.ComponentName
            r3.<init>(r5, r1)
            r2.setComponent(r3)
            java.lang.String r5 = "intent_flag"
            boolean r5 = r6.containsKey(r5)     // Catch:{ NumberFormatException -> 0x00a9 }
            if (r5 == 0) goto L_0x00bf
            java.lang.String r5 = "intent_flag"
            java.lang.Object r5 = r6.get(r5)     // Catch:{ NumberFormatException -> 0x00a9 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ NumberFormatException -> 0x00a9 }
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x00a9 }
            r2.setFlags(r5)     // Catch:{ NumberFormatException -> 0x00a9 }
            goto L_0x00bf
        L_0x00a9:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r1 = "Cause by intent_flag: "
            r6.<init>(r1)
            java.lang.String r5 = r5.getMessage()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r5)
        L_0x00bf:
            r6 = r2
            goto L_0x0135
        L_0x00c2:
            java.lang.String r5 = com.xiaomi.push.service.at.c
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0134
            java.lang.String r5 = "web_uri"
            java.lang.Object r5 = r6.get(r5)
            java.lang.String r5 = (java.lang.String) r5
            if (r5 == 0) goto L_0x0134
            java.lang.String r5 = r5.trim()
            java.lang.String r6 = "http://"
            boolean r6 = r5.startsWith(r6)
            if (r6 != 0) goto L_0x00f2
            java.lang.String r6 = "https://"
            boolean r6 = r5.startsWith(r6)
            if (r6 != 0) goto L_0x00f2
            java.lang.String r6 = "http://"
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r5 = r6.concat(r5)
        L_0x00f2:
            java.net.URL r6 = new java.net.URL     // Catch:{ MalformedURLException -> 0x011c }
            r6.<init>(r5)     // Catch:{ MalformedURLException -> 0x011c }
            java.lang.String r6 = r6.getProtocol()     // Catch:{ MalformedURLException -> 0x011c }
            java.lang.String r1 = "http"
            boolean r1 = r1.equals(r6)     // Catch:{ MalformedURLException -> 0x011c }
            if (r1 != 0) goto L_0x010b
            java.lang.String r1 = "https"
            boolean r6 = r1.equals(r6)     // Catch:{ MalformedURLException -> 0x011c }
            if (r6 == 0) goto L_0x0134
        L_0x010b:
            android.content.Intent r6 = new android.content.Intent     // Catch:{ MalformedURLException -> 0x011c }
            java.lang.String r1 = "android.intent.action.VIEW"
            r6.<init>(r1)     // Catch:{ MalformedURLException -> 0x011c }
            android.net.Uri r5 = android.net.Uri.parse(r5)     // Catch:{ MalformedURLException -> 0x011a }
            r6.setData(r5)     // Catch:{ MalformedURLException -> 0x011a }
            goto L_0x0135
        L_0x011a:
            r5 = move-exception
            goto L_0x011e
        L_0x011c:
            r5 = move-exception
            r6 = r0
        L_0x011e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Cause: "
            r1.<init>(r2)
            java.lang.String r5 = r5.getMessage()
        L_0x0129:
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r5)
            goto L_0x0135
        L_0x0134:
            r6 = r0
        L_0x0135:
            if (r6 == 0) goto L_0x015f
            r5 = 268435456(0x10000000, float:2.5243549E-29)
            r6.addFlags(r5)
            android.content.pm.PackageManager r4 = r4.getPackageManager()     // Catch:{ Exception -> 0x0149 }
            r5 = 65536(0x10000, float:9.18355E-41)
            android.content.pm.ResolveInfo r4 = r4.resolveActivity(r6, r5)     // Catch:{ Exception -> 0x0149 }
            if (r4 == 0) goto L_0x015f
            return r6
        L_0x0149:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Cause: "
            r5.<init>(r6)
            java.lang.String r4 = r4.getMessage()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r4)
        L_0x015f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.ah.a(android.content.Context, java.lang.String, java.util.Map):android.content.Intent");
    }

    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [com.xiaomi.mipush.sdk.PushMessageHandler$a] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r8v4, types: [com.xiaomi.mipush.sdk.MiPushMessage, java.io.Serializable] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r7v34, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r4v5, types: [java.util.List, java.util.ArrayList] */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r7v35, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r4v7, types: [java.util.List, java.util.ArrayList] */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r7v36, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r4v9, types: [java.util.List, java.util.ArrayList] */
    /* JADX WARNING: type inference failed for: r4v23 */
    /* JADX WARNING: type inference failed for: r4v24 */
    /* JADX WARNING: type inference failed for: r4v25 */
    /* JADX WARNING: type inference failed for: r4v26 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.util.ArrayList, ?[OBJECT, ARRAY]]
      uses: [?[OBJECT, ARRAY], java.util.List, com.xiaomi.mipush.sdk.PushMessageHandler$a]
      mth insns count: 694
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
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.xiaomi.mipush.sdk.PushMessageHandler.a a(com.xiaomi.xmpush.thrift.af r18, boolean r19, byte[] r20) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = 0
            android.content.Context r5 = r1.b     // Catch:{ m -> 0x078d, f -> 0x0782 }
            org.apache.thrift.a r5 = com.xiaomi.mipush.sdk.ae.a(r5, r2)     // Catch:{ m -> 0x078d, f -> 0x0782 }
            if (r5 != 0) goto L_0x0023
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ m -> 0x078d, f -> 0x0782 }
            java.lang.String r5 = "receiving an un-recognized message. "
            r3.<init>(r5)     // Catch:{ m -> 0x078d, f -> 0x0782 }
            com.xiaomi.xmpush.thrift.a r5 = r2.a     // Catch:{ m -> 0x078d, f -> 0x0782 }
            r3.append(r5)     // Catch:{ m -> 0x078d, f -> 0x0782 }
            java.lang.String r3 = r3.toString()     // Catch:{ m -> 0x078d, f -> 0x0782 }
            com.xiaomi.channel.commonutils.logger.b.d(r3)     // Catch:{ m -> 0x078d, f -> 0x0782 }
            return r4
        L_0x0023:
            com.xiaomi.xmpush.thrift.a r6 = r18.a()
            java.lang.String r7 = "processing a message, action="
            java.lang.String r8 = java.lang.String.valueOf(r6)
            java.lang.String r7 = r7.concat(r8)
            com.xiaomi.channel.commonutils.logger.b.a(r7)
            int[] r7 = com.xiaomi.mipush.sdk.ai.a
            int r6 = r6.ordinal()
            r6 = r7[r6]
            r7 = 1
            r8 = 0
            r10 = 0
            switch(r6) {
                case 1: goto L_0x05fd;
                case 2: goto L_0x0599;
                case 3: goto L_0x057f;
                case 4: goto L_0x0548;
                case 5: goto L_0x0511;
                case 6: goto L_0x0428;
                case 7: goto L_0x0044;
                default: goto L_0x0043;
            }
        L_0x0043:
            return r4
        L_0x0044:
            boolean r2 = r5 instanceof com.xiaomi.xmpush.thrift.aa
            if (r2 == 0) goto L_0x01c4
            com.xiaomi.xmpush.thrift.aa r5 = (com.xiaomi.xmpush.thrift.aa) r5
            java.lang.String r2 = r5.c()
            com.xiaomi.xmpush.thrift.r r3 = com.xiaomi.xmpush.thrift.r.DisablePushMessage
            java.lang.String r3 = r3.W
            java.lang.String r6 = r5.e
            boolean r3 = r3.equalsIgnoreCase(r6)
            r6 = 10
            if (r3 == 0) goto L_0x0112
            long r10 = r5.g
            int r3 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r3 != 0) goto L_0x00b7
            java.lang.Class<com.xiaomi.mipush.sdk.ab> r3 = com.xiaomi.mipush.sdk.ab.class
            monitor-enter(r3)
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x00b3 }
            boolean r5 = r5.e(r2)     // Catch:{ all -> 0x00b3 }
            if (r5 == 0) goto L_0x00b1
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x00b3 }
            r5.d(r2)     // Catch:{ all -> 0x00b3 }
            java.lang.String r2 = "syncing"
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.ao r6 = com.xiaomi.mipush.sdk.ao.DISABLE_PUSH     // Catch:{ all -> 0x00b3 }
            java.lang.String r5 = r5.a(r6)     // Catch:{ all -> 0x00b3 }
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x00b3 }
            if (r2 == 0) goto L_0x00b1
            android.content.Context r2 = r1.b     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.ab r2 = com.xiaomi.mipush.sdk.ab.a(r2)     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.ao r5 = com.xiaomi.mipush.sdk.ao.DISABLE_PUSH     // Catch:{ all -> 0x00b3 }
            java.lang.String r6 = "synced"
            r2.a(r5, r6)     // Catch:{ all -> 0x00b3 }
            android.content.Context r2 = r1.b     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.MiPushClient.clearNotification(r2)     // Catch:{ all -> 0x00b3 }
            android.content.Context r2 = r1.b     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.MiPushClient.clearLocalNotificationType(r2)     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.PushMessageHandler.a()     // Catch:{ all -> 0x00b3 }
            android.content.Context r2 = r1.b     // Catch:{ all -> 0x00b3 }
            com.xiaomi.mipush.sdk.aj r2 = com.xiaomi.mipush.sdk.aj.a(r2)     // Catch:{ all -> 0x00b3 }
            r2.b()     // Catch:{ all -> 0x00b3 }
        L_0x00b1:
            monitor-exit(r3)     // Catch:{ all -> 0x00b3 }
            return r4
        L_0x00b3:
            r0 = move-exception
            r2 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x00b3 }
            throw r2
        L_0x00b7:
            java.lang.String r3 = "syncing"
            android.content.Context r5 = r1.b
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)
            com.xiaomi.mipush.sdk.ao r8 = com.xiaomi.mipush.sdk.ao.DISABLE_PUSH
            java.lang.String r5 = r5.a(r8)
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0108
            java.lang.Class<com.xiaomi.mipush.sdk.ab> r3 = com.xiaomi.mipush.sdk.ab.class
            monitor-enter(r3)
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x0104 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x0104 }
            boolean r5 = r5.e(r2)     // Catch:{ all -> 0x0104 }
            if (r5 == 0) goto L_0x0102
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x0104 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x0104 }
            int r5 = r5.c(r2)     // Catch:{ all -> 0x0104 }
            if (r5 >= r6) goto L_0x00f9
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x0104 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x0104 }
            r5.b(r2)     // Catch:{ all -> 0x0104 }
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x0104 }
            com.xiaomi.mipush.sdk.aj r5 = com.xiaomi.mipush.sdk.aj.a(r5)     // Catch:{ all -> 0x0104 }
            r5.a(r7, r2)     // Catch:{ all -> 0x0104 }
            goto L_0x0102
        L_0x00f9:
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x0104 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x0104 }
            r5.d(r2)     // Catch:{ all -> 0x0104 }
        L_0x0102:
            monitor-exit(r3)     // Catch:{ all -> 0x0104 }
            return r4
        L_0x0104:
            r0 = move-exception
            r2 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x0104 }
            throw r2
        L_0x0108:
            android.content.Context r3 = r1.b
            com.xiaomi.mipush.sdk.ab r3 = com.xiaomi.mipush.sdk.ab.a(r3)
            r3.d(r2)
            return r4
        L_0x0112:
            com.xiaomi.xmpush.thrift.r r3 = com.xiaomi.xmpush.thrift.r.EnablePushMessage
            java.lang.String r3 = r3.W
            java.lang.String r7 = r5.e
            boolean r3 = r3.equalsIgnoreCase(r7)
            if (r3 == 0) goto L_0x01b4
            long r11 = r5.g
            int r3 = (r11 > r8 ? 1 : (r11 == r8 ? 0 : -1))
            if (r3 != 0) goto L_0x0163
            java.lang.Class<com.xiaomi.mipush.sdk.ab> r3 = com.xiaomi.mipush.sdk.ab.class
            monitor-enter(r3)
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x015f }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x015f }
            boolean r5 = r5.e(r2)     // Catch:{ all -> 0x015f }
            if (r5 == 0) goto L_0x015d
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x015f }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x015f }
            r5.d(r2)     // Catch:{ all -> 0x015f }
            java.lang.String r2 = "syncing"
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x015f }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x015f }
            com.xiaomi.mipush.sdk.ao r6 = com.xiaomi.mipush.sdk.ao.ENABLE_PUSH     // Catch:{ all -> 0x015f }
            java.lang.String r5 = r5.a(r6)     // Catch:{ all -> 0x015f }
            boolean r2 = r2.equals(r5)     // Catch:{ all -> 0x015f }
            if (r2 == 0) goto L_0x015d
            android.content.Context r2 = r1.b     // Catch:{ all -> 0x015f }
            com.xiaomi.mipush.sdk.ab r2 = com.xiaomi.mipush.sdk.ab.a(r2)     // Catch:{ all -> 0x015f }
            com.xiaomi.mipush.sdk.ao r5 = com.xiaomi.mipush.sdk.ao.ENABLE_PUSH     // Catch:{ all -> 0x015f }
            java.lang.String r6 = "synced"
            r2.a(r5, r6)     // Catch:{ all -> 0x015f }
        L_0x015d:
            monitor-exit(r3)     // Catch:{ all -> 0x015f }
            return r4
        L_0x015f:
            r0 = move-exception
            r2 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x015f }
            throw r2
        L_0x0163:
            java.lang.String r3 = "syncing"
            android.content.Context r5 = r1.b
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)
            com.xiaomi.mipush.sdk.ao r7 = com.xiaomi.mipush.sdk.ao.ENABLE_PUSH
            java.lang.String r5 = r5.a(r7)
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0108
            java.lang.Class<com.xiaomi.mipush.sdk.ab> r3 = com.xiaomi.mipush.sdk.ab.class
            monitor-enter(r3)
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x01b0 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x01b0 }
            boolean r5 = r5.e(r2)     // Catch:{ all -> 0x01b0 }
            if (r5 == 0) goto L_0x01ae
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x01b0 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x01b0 }
            int r5 = r5.c(r2)     // Catch:{ all -> 0x01b0 }
            if (r5 >= r6) goto L_0x01a5
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x01b0 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x01b0 }
            r5.b(r2)     // Catch:{ all -> 0x01b0 }
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x01b0 }
            com.xiaomi.mipush.sdk.aj r5 = com.xiaomi.mipush.sdk.aj.a(r5)     // Catch:{ all -> 0x01b0 }
            r5.a(r10, r2)     // Catch:{ all -> 0x01b0 }
            goto L_0x01ae
        L_0x01a5:
            android.content.Context r5 = r1.b     // Catch:{ all -> 0x01b0 }
            com.xiaomi.mipush.sdk.ab r5 = com.xiaomi.mipush.sdk.ab.a(r5)     // Catch:{ all -> 0x01b0 }
            r5.d(r2)     // Catch:{ all -> 0x01b0 }
        L_0x01ae:
            monitor-exit(r3)     // Catch:{ all -> 0x01b0 }
            return r4
        L_0x01b0:
            r0 = move-exception
            r2 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x01b0 }
            throw r2
        L_0x01b4:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.ThirdPartyRegUpdate
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0781
            r1.a(r5)
            return r4
        L_0x01c4:
            boolean r2 = r5 instanceof com.xiaomi.xmpush.thrift.ai
            if (r2 == 0) goto L_0x0781
            com.xiaomi.xmpush.thrift.ai r5 = (com.xiaomi.xmpush.thrift.ai) r5
            java.lang.String r2 = "registration id expired"
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0249
            android.content.Context r2 = r1.b
            java.util.List r2 = com.xiaomi.mipush.sdk.MiPushClient.getAllAlias(r2)
            android.content.Context r3 = r1.b
            java.util.List r3 = com.xiaomi.mipush.sdk.MiPushClient.getAllTopic(r3)
            android.content.Context r5 = r1.b
            java.util.List r5 = com.xiaomi.mipush.sdk.MiPushClient.getAllUserAccount(r5)
            android.content.Context r6 = r1.b
            java.lang.String r6 = com.xiaomi.mipush.sdk.MiPushClient.getAcceptTime(r6)
            android.content.Context r8 = r1.b
            com.xiaomi.xmpush.thrift.w r9 = com.xiaomi.xmpush.thrift.w.RegIdExpired
            com.xiaomi.mipush.sdk.MiPushClient.reInitialize(r8, r9)
            java.util.Iterator r2 = r2.iterator()
        L_0x01f7:
            boolean r8 = r2.hasNext()
            if (r8 == 0) goto L_0x0209
            java.lang.Object r8 = r2.next()
            java.lang.String r8 = (java.lang.String) r8
            android.content.Context r9 = r1.b
            com.xiaomi.mipush.sdk.MiPushClient.setAlias(r9, r8, r4)
            goto L_0x01f7
        L_0x0209:
            java.util.Iterator r2 = r3.iterator()
        L_0x020d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x021f
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            android.content.Context r8 = r1.b
            com.xiaomi.mipush.sdk.MiPushClient.subscribe(r8, r3, r4)
            goto L_0x020d
        L_0x021f:
            java.util.Iterator r2 = r5.iterator()
        L_0x0223:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0235
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            android.content.Context r5 = r1.b
            com.xiaomi.mipush.sdk.MiPushClient.setUserAccount(r5, r3, r4)
            goto L_0x0223
        L_0x0235:
            java.lang.String r2 = ","
            java.lang.String[] r2 = r6.split(r2)
            int r3 = r2.length
            r5 = 2
            if (r3 != r5) goto L_0x0781
            android.content.Context r3 = r1.b
            r5 = r2[r10]
            r2 = r2[r7]
            com.xiaomi.mipush.sdk.MiPushClient.addAcceptTime(r3, r5, r2)
            return r4
        L_0x0249:
            java.lang.String r2 = "client_info_update_ok"
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x027b
            java.util.Map r2 = r5.i()
            if (r2 == 0) goto L_0x0781
            java.util.Map r2 = r5.i()
            java.lang.String r3 = "app_version"
            boolean r2 = r2.containsKey(r3)
            if (r2 == 0) goto L_0x0781
            java.util.Map r2 = r5.i()
            java.lang.String r3 = "app_version"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            android.content.Context r3 = r1.b
            com.xiaomi.mipush.sdk.c r3 = com.xiaomi.mipush.sdk.c.a(r3)
            r3.a(r2)
            return r4
        L_0x027b:
            java.lang.String r2 = "awake_app"
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x02af
            java.util.Map r2 = r5.i()
            if (r2 == 0) goto L_0x0781
            java.util.Map r2 = r5.i()
            java.lang.String r3 = "packages"
            boolean r2 = r2.containsKey(r3)
            if (r2 == 0) goto L_0x0781
            java.util.Map r2 = r5.i()
            java.lang.String r3 = "packages"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r3 = ","
            java.lang.String[] r2 = r2.split(r3)
            android.content.Context r3 = r1.b
            com.xiaomi.mipush.sdk.MiPushClient.awakeApps(r3, r2)
            return r4
        L_0x02af:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.NormalClientConfigUpdate
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x02dc
            com.xiaomi.xmpush.thrift.ah r2 = new com.xiaomi.xmpush.thrift.ah
            r2.<init>()
            byte[] r3 = r5.m()     // Catch:{ f -> 0x02d6 }
            com.xiaomi.xmpush.thrift.au.a(r2, r3)     // Catch:{ f -> 0x02d6 }
            android.content.Context r3 = r1.b     // Catch:{ f -> 0x02d6 }
            com.xiaomi.push.service.an r3 = com.xiaomi.push.service.an.a(r3)     // Catch:{ f -> 0x02d6 }
            com.xiaomi.push.service.ao.a(r3, r2)     // Catch:{ f -> 0x02d6 }
            android.content.Context r2 = r1.b     // Catch:{ f -> 0x02d6 }
            com.xiaomi.mipush.sdk.g.c(r2)     // Catch:{ f -> 0x02d6 }
            return r4
        L_0x02d6:
            r0 = move-exception
            r2 = r0
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            return r4
        L_0x02dc:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.CustomClientConfigUpdate
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0303
            com.xiaomi.xmpush.thrift.ag r2 = new com.xiaomi.xmpush.thrift.ag
            r2.<init>()
            byte[] r3 = r5.m()     // Catch:{ f -> 0x02d6 }
            com.xiaomi.xmpush.thrift.au.a(r2, r3)     // Catch:{ f -> 0x02d6 }
            android.content.Context r3 = r1.b     // Catch:{ f -> 0x02d6 }
            com.xiaomi.push.service.an r3 = com.xiaomi.push.service.an.a(r3)     // Catch:{ f -> 0x02d6 }
            com.xiaomi.push.service.ao.a(r3, r2)     // Catch:{ f -> 0x02d6 }
            android.content.Context r2 = r1.b     // Catch:{ f -> 0x02d6 }
            com.xiaomi.mipush.sdk.g.c(r2)     // Catch:{ f -> 0x02d6 }
            return r4
        L_0x0303:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.SyncInfoResult
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0315
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.aq.a(r2, r5)
            return r4
        L_0x0315:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.ForceSync
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x032c
            java.lang.String r2 = "receive force sync notification"
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.aq.a(r2, r10)
            return r4
        L_0x032c:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.GeoRegsiter
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0342
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.p r2 = com.xiaomi.mipush.sdk.p.a(r2)
            r2.a(r5)
            return r4
        L_0x0342:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.GeoUnregsiter
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0358
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.p r2 = com.xiaomi.mipush.sdk.p.a(r2)
            r2.b(r5)
            return r4
        L_0x0358:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.GeoSync
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x036e
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.p r2 = com.xiaomi.mipush.sdk.p.a(r2)
            r2.c(r5)
            return r4
        L_0x036e:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.CancelPushMessage
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x03ec
            java.util.Map r2 = r5.i()
            if (r2 == 0) goto L_0x0781
            java.util.Map r2 = r5.i()
            java.lang.String r3 = com.xiaomi.push.service.at.I
            boolean r2 = r2.containsKey(r3)
            r3 = -2
            if (r2 == 0) goto L_0x03a9
            java.util.Map r2 = r5.i()
            java.lang.String r6 = com.xiaomi.push.service.at.I
            java.lang.Object r2 = r2.get(r6)
            java.lang.String r2 = (java.lang.String) r2
            boolean r6 = android.text.TextUtils.isEmpty(r2)
            if (r6 != 0) goto L_0x03a9
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x03a5 }
            r3 = r2
            goto L_0x03a9
        L_0x03a5:
            r0 = move-exception
            r0.printStackTrace()
        L_0x03a9:
            r2 = -1
            if (r3 < r2) goto L_0x03b2
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.MiPushClient.clearNotification(r2, r3)
            return r4
        L_0x03b2:
            java.lang.String r2 = ""
            java.lang.String r3 = ""
            java.util.Map r6 = r5.i()
            java.lang.String r7 = com.xiaomi.push.service.at.G
            boolean r6 = r6.containsKey(r7)
            if (r6 == 0) goto L_0x03ce
            java.util.Map r2 = r5.i()
            java.lang.String r6 = com.xiaomi.push.service.at.G
            java.lang.Object r2 = r2.get(r6)
            java.lang.String r2 = (java.lang.String) r2
        L_0x03ce:
            java.util.Map r6 = r5.i()
            java.lang.String r7 = com.xiaomi.push.service.at.H
            boolean r6 = r6.containsKey(r7)
            if (r6 == 0) goto L_0x03e6
            java.util.Map r3 = r5.i()
            java.lang.String r5 = com.xiaomi.push.service.at.H
            java.lang.Object r3 = r3.get(r5)
            java.lang.String r3 = (java.lang.String) r3
        L_0x03e6:
            android.content.Context r5 = r1.b
            com.xiaomi.mipush.sdk.MiPushClient.clearNotification(r5, r2, r3)
            return r4
        L_0x03ec:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.HybridRegisterResult
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x040a
            com.xiaomi.xmpush.thrift.ak r2 = new com.xiaomi.xmpush.thrift.ak     // Catch:{ f -> 0x02d6 }
            r2.<init>()     // Catch:{ f -> 0x02d6 }
            byte[] r3 = r5.m()     // Catch:{ f -> 0x02d6 }
            com.xiaomi.xmpush.thrift.au.a(r2, r3)     // Catch:{ f -> 0x02d6 }
            android.content.Context r3 = r1.b     // Catch:{ f -> 0x02d6 }
            com.xiaomi.mipush.sdk.MiPushClient4Hybrid.onReceiveRegisterResult(r3, r2)     // Catch:{ f -> 0x02d6 }
            return r4
        L_0x040a:
            com.xiaomi.xmpush.thrift.r r2 = com.xiaomi.xmpush.thrift.r.HybridUnregisterResult
            java.lang.String r2 = r2.W
            java.lang.String r3 = r5.e
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0781
            com.xiaomi.xmpush.thrift.ar r2 = new com.xiaomi.xmpush.thrift.ar     // Catch:{ f -> 0x02d6 }
            r2.<init>()     // Catch:{ f -> 0x02d6 }
            byte[] r3 = r5.m()     // Catch:{ f -> 0x02d6 }
            com.xiaomi.xmpush.thrift.au.a(r2, r3)     // Catch:{ f -> 0x02d6 }
            android.content.Context r3 = r1.b     // Catch:{ f -> 0x02d6 }
            com.xiaomi.mipush.sdk.MiPushClient4Hybrid.onReceiveUnregisterResult(r3, r2)     // Catch:{ f -> 0x02d6 }
            return r4
        L_0x0428:
            com.xiaomi.xmpush.thrift.ae r5 = (com.xiaomi.xmpush.thrift.ae) r5
            java.lang.String r11 = r5.e()
            java.util.List r2 = r5.k()
            long r3 = r5.g
            int r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r3 != 0) goto L_0x0503
            java.lang.String r3 = "accept-time"
            boolean r3 = android.text.TextUtils.equals(r11, r3)
            if (r3 == 0) goto L_0x0494
            if (r2 == 0) goto L_0x0494
            int r3 = r2.size()
            if (r3 <= r7) goto L_0x0494
            android.content.Context r3 = r1.b
            java.lang.Object r4 = r2.get(r10)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r6 = r2.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            com.xiaomi.mipush.sdk.MiPushClient.addAcceptTime(r3, r4, r6)
            java.lang.String r3 = "00:00"
            java.lang.Object r4 = r2.get(r10)
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x047b
            java.lang.String r3 = "00:00"
            java.lang.Object r4 = r2.get(r7)
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x047b
            android.content.Context r3 = r1.b
            com.xiaomi.mipush.sdk.c r3 = com.xiaomi.mipush.sdk.c.a(r3)
            r3.a(r7)
            goto L_0x0484
        L_0x047b:
            android.content.Context r3 = r1.b
            com.xiaomi.mipush.sdk.c r3 = com.xiaomi.mipush.sdk.c.a(r3)
            r3.a(r10)
        L_0x0484:
            java.lang.String r3 = "GMT+08"
            java.util.TimeZone r3 = java.util.TimeZone.getTimeZone(r3)
            java.util.TimeZone r4 = java.util.TimeZone.getDefault()
            java.util.List r2 = r1.a(r3, r4, r2)
            goto L_0x0503
        L_0x0494:
            java.lang.String r3 = "set-alias"
            boolean r3 = android.text.TextUtils.equals(r11, r3)
            if (r3 == 0) goto L_0x04b0
            if (r2 == 0) goto L_0x04b0
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x04b0
            android.content.Context r3 = r1.b
            java.lang.Object r4 = r2.get(r10)
            java.lang.String r4 = (java.lang.String) r4
            com.xiaomi.mipush.sdk.MiPushClient.addAlias(r3, r4)
            goto L_0x0503
        L_0x04b0:
            java.lang.String r3 = "unset-alias"
            boolean r3 = android.text.TextUtils.equals(r11, r3)
            if (r3 == 0) goto L_0x04cc
            if (r2 == 0) goto L_0x04cc
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x04cc
            android.content.Context r3 = r1.b
            java.lang.Object r4 = r2.get(r10)
            java.lang.String r4 = (java.lang.String) r4
            com.xiaomi.mipush.sdk.MiPushClient.removeAlias(r3, r4)
            goto L_0x0503
        L_0x04cc:
            java.lang.String r3 = "set-account"
            boolean r3 = android.text.TextUtils.equals(r11, r3)
            if (r3 == 0) goto L_0x04e8
            if (r2 == 0) goto L_0x04e8
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x04e8
            android.content.Context r3 = r1.b
            java.lang.Object r4 = r2.get(r10)
            java.lang.String r4 = (java.lang.String) r4
            com.xiaomi.mipush.sdk.MiPushClient.addAccount(r3, r4)
            goto L_0x0503
        L_0x04e8:
            java.lang.String r3 = "unset-account"
            boolean r3 = android.text.TextUtils.equals(r11, r3)
            if (r3 == 0) goto L_0x0503
            if (r2 == 0) goto L_0x0503
            int r3 = r2.size()
            if (r3 <= 0) goto L_0x0503
            android.content.Context r3 = r1.b
            java.lang.Object r4 = r2.get(r10)
            java.lang.String r4 = (java.lang.String) r4
            com.xiaomi.mipush.sdk.MiPushClient.removeAccount(r3, r4)
        L_0x0503:
            r12 = r2
            long r13 = r5.g
            java.lang.String r15 = r5.h
            java.lang.String r16 = r5.m()
            com.xiaomi.mipush.sdk.MiPushCommandMessage r2 = com.xiaomi.mipush.sdk.PushMessageHelper.generateCommandMessage(r11, r12, r13, r15, r16)
            return r2
        L_0x0511:
            com.xiaomi.xmpush.thrift.at r5 = (com.xiaomi.xmpush.thrift.at) r5
            long r2 = r5.f
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x0522
            android.content.Context r2 = r1.b
            java.lang.String r3 = r5.h()
            com.xiaomi.mipush.sdk.MiPushClient.removeTopic(r2, r3)
        L_0x0522:
            java.lang.String r2 = r5.h()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0538
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.lang.String r2 = r5.h()
            r4.add(r2)
        L_0x0538:
            r7 = r4
            java.lang.String r6 = "unsubscibe-topic"
            long r8 = r5.f
            java.lang.String r10 = r5.g
            java.lang.String r11 = r5.k()
            com.xiaomi.mipush.sdk.MiPushCommandMessage r2 = com.xiaomi.mipush.sdk.PushMessageHelper.generateCommandMessage(r6, r7, r8, r10, r11)
            return r2
        L_0x0548:
            com.xiaomi.xmpush.thrift.ap r5 = (com.xiaomi.xmpush.thrift.ap) r5
            long r2 = r5.f
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x0559
            android.content.Context r2 = r1.b
            java.lang.String r3 = r5.h()
            com.xiaomi.mipush.sdk.MiPushClient.addTopic(r2, r3)
        L_0x0559:
            java.lang.String r2 = r5.h()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x056f
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.lang.String r2 = r5.h()
            r4.add(r2)
        L_0x056f:
            r7 = r4
            java.lang.String r6 = "subscribe-topic"
            long r8 = r5.f
            java.lang.String r10 = r5.g
            java.lang.String r11 = r5.k()
            com.xiaomi.mipush.sdk.MiPushCommandMessage r2 = com.xiaomi.mipush.sdk.PushMessageHelper.generateCommandMessage(r6, r7, r8, r10, r11)
            return r2
        L_0x057f:
            com.xiaomi.xmpush.thrift.ar r5 = (com.xiaomi.xmpush.thrift.ar) r5
            long r2 = r5.f
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x0595
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.c r2 = com.xiaomi.mipush.sdk.c.a(r2)
            r2.h()
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.MiPushClient.clearExtras(r2)
        L_0x0595:
            com.xiaomi.mipush.sdk.PushMessageHandler.a()
            return r4
        L_0x0599:
            com.xiaomi.xmpush.thrift.ak r5 = (com.xiaomi.xmpush.thrift.ak) r5
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.c r2 = com.xiaomi.mipush.sdk.c.a(r2)
            java.lang.String r2 = r2.a
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x05f7
            java.lang.String r3 = r5.c()
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 != 0) goto L_0x05b4
            goto L_0x05f7
        L_0x05b4:
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.c r2 = com.xiaomi.mipush.sdk.c.a(r2)
            r2.a = r4
            long r2 = r5.f
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x05cf
            android.content.Context r2 = r1.b
            com.xiaomi.mipush.sdk.c r2 = com.xiaomi.mipush.sdk.c.a(r2)
            java.lang.String r3 = r5.h
            java.lang.String r6 = r5.i
            r2.b(r3, r6)
        L_0x05cf:
            java.lang.String r2 = r5.h
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x05e1
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.lang.String r2 = r5.h
            r4.add(r2)
        L_0x05e1:
            r7 = r4
            java.lang.String r6 = "register"
            long r8 = r5.f
            java.lang.String r10 = r5.g
            r11 = 0
            com.xiaomi.mipush.sdk.MiPushCommandMessage r2 = com.xiaomi.mipush.sdk.PushMessageHelper.generateCommandMessage(r6, r7, r8, r10, r11)
            android.content.Context r3 = r1.b
            com.xiaomi.mipush.sdk.aj r3 = com.xiaomi.mipush.sdk.aj.a(r3)
            r3.e()
            return r2
        L_0x05f7:
            java.lang.String r2 = "bad Registration result:"
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            return r4
        L_0x05fd:
            android.content.Context r6 = r1.b
            com.xiaomi.mipush.sdk.c r6 = com.xiaomi.mipush.sdk.c.a(r6)
            boolean r6 = r6.k()
            if (r6 == 0) goto L_0x0611
            if (r3 != 0) goto L_0x0611
            java.lang.String r2 = "receive a message in pause state. drop it"
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            return r4
        L_0x0611:
            com.xiaomi.xmpush.thrift.an r5 = (com.xiaomi.xmpush.thrift.an) r5
            com.xiaomi.xmpush.thrift.t r6 = r5.l()
            if (r6 != 0) goto L_0x061f
            java.lang.String r2 = "receive an empty message without push content, drop it"
            com.xiaomi.channel.commonutils.logger.b.d(r2)
            return r4
        L_0x061f:
            if (r3 == 0) goto L_0x064c
            boolean r7 = com.xiaomi.push.service.ah.b(r18)
            if (r7 == 0) goto L_0x063b
            android.content.Context r7 = r1.b
            java.lang.String r10 = r6.b()
            com.xiaomi.xmpush.thrift.u r11 = r18.m()
            java.lang.String r12 = r2.f
            java.lang.String r13 = r6.d()
            com.xiaomi.mipush.sdk.MiPushClient.reportIgnoreRegMessageClicked(r7, r10, r11, r12, r13)
            goto L_0x064c
        L_0x063b:
            android.content.Context r7 = r1.b
            java.lang.String r10 = r6.b()
            com.xiaomi.xmpush.thrift.u r11 = r18.m()
            java.lang.String r12 = r6.d()
            com.xiaomi.mipush.sdk.MiPushClient.reportMessageClicked(r7, r10, r11, r12)
        L_0x064c:
            if (r3 != 0) goto L_0x0691
            java.lang.String r7 = r5.j()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x0670
            android.content.Context r7 = r1.b
            java.lang.String r10 = r5.j()
            long r10 = com.xiaomi.mipush.sdk.MiPushClient.aliasSetTime(r7, r10)
            int r7 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r7 >= 0) goto L_0x0670
            android.content.Context r7 = r1.b
            java.lang.String r8 = r5.j()
            com.xiaomi.mipush.sdk.MiPushClient.addAlias(r7, r8)
            goto L_0x0691
        L_0x0670:
            java.lang.String r7 = r5.h()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x0691
            android.content.Context r7 = r1.b
            java.lang.String r10 = r5.h()
            long r10 = com.xiaomi.mipush.sdk.MiPushClient.topicSubscribedTime(r7, r10)
            int r7 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r7 >= 0) goto L_0x0691
            android.content.Context r7 = r1.b
            java.lang.String r8 = r5.h()
            com.xiaomi.mipush.sdk.MiPushClient.addTopic(r7, r8)
        L_0x0691:
            com.xiaomi.xmpush.thrift.u r7 = r2.h
            if (r7 == 0) goto L_0x06aa
            com.xiaomi.xmpush.thrift.u r7 = r2.h
            java.util.Map r7 = r7.s()
            if (r7 == 0) goto L_0x06aa
            com.xiaomi.xmpush.thrift.u r7 = r2.h
            java.util.Map<java.lang.String, java.lang.String> r7 = r7.j
            java.lang.String r8 = "jobkey"
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            goto L_0x06ab
        L_0x06aa:
            r7 = r4
        L_0x06ab:
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 == 0) goto L_0x06b5
            java.lang.String r7 = r6.b()
        L_0x06b5:
            if (r3 != 0) goto L_0x06ce
            android.content.Context r8 = r1.b
            boolean r8 = b(r8, r7)
            if (r8 == 0) goto L_0x06ce
            java.lang.String r6 = "drop a duplicate message, key="
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r6 = r6.concat(r7)
            com.xiaomi.channel.commonutils.logger.b.a(r6)
            goto L_0x0776
        L_0x06ce:
            com.xiaomi.xmpush.thrift.u r8 = r18.m()
            com.xiaomi.mipush.sdk.MiPushMessage r8 = com.xiaomi.mipush.sdk.PushMessageHelper.generateMessage(r5, r8, r3)
            int r9 = r8.getPassThrough()
            if (r9 != 0) goto L_0x06f0
            if (r3 != 0) goto L_0x06f0
            java.util.Map r9 = r8.getExtra()
            boolean r9 = com.xiaomi.push.service.ah.a(r9)
            if (r9 == 0) goto L_0x06f0
            android.content.Context r3 = r1.b
            r5 = r20
            com.xiaomi.push.service.ah.a(r3, r2, r5)
            return r4
        L_0x06f0:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "receive a message, msgid="
            r9.<init>(r10)
            java.lang.String r10 = r6.b()
            r9.append(r10)
            java.lang.String r10 = ", jobkey="
            r9.append(r10)
            r9.append(r7)
            java.lang.String r7 = r9.toString()
            com.xiaomi.channel.commonutils.logger.b.a(r7)
            if (r3 == 0) goto L_0x0775
            java.util.Map r7 = r8.getExtra()
            if (r7 == 0) goto L_0x0775
            java.util.Map r7 = r8.getExtra()
            java.lang.String r9 = "notify_effect"
            boolean r7 = r7.containsKey(r9)
            if (r7 == 0) goto L_0x0775
            java.util.Map r3 = r8.getExtra()
            java.lang.String r5 = "notify_effect"
            java.lang.Object r5 = r3.get(r5)
            java.lang.String r5 = (java.lang.String) r5
            boolean r7 = com.xiaomi.push.service.ah.b(r18)
            if (r7 == 0) goto L_0x0758
            android.content.Context r5 = r1.b
            java.lang.String r2 = r2.f
            android.content.Intent r2 = a(r5, r2, r3)
            if (r2 != 0) goto L_0x0743
            java.lang.String r2 = "Getting Intent fail from ignore reg message. "
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            return r4
        L_0x0743:
            java.lang.String r3 = r6.f()
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 != 0) goto L_0x0752
            java.lang.String r5 = "payload"
            r2.putExtra(r5, r3)
        L_0x0752:
            android.content.Context r3 = r1.b
            r3.startActivity(r2)
            return r4
        L_0x0758:
            android.content.Context r2 = r1.b
            android.content.Context r6 = r1.b
            java.lang.String r6 = r6.getPackageName()
            android.content.Intent r2 = a(r2, r6, r3)
            if (r2 == 0) goto L_0x0774
            java.lang.String r3 = com.xiaomi.push.service.at.c
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x0752
            java.lang.String r3 = "key_message"
            r2.putExtra(r3, r8)
            goto L_0x0752
        L_0x0774:
            return r4
        L_0x0775:
            r4 = r8
        L_0x0776:
            com.xiaomi.xmpush.thrift.u r6 = r18.m()
            if (r6 != 0) goto L_0x0781
            if (r3 != 0) goto L_0x0781
            r1.a(r5, r2)
        L_0x0781:
            return r4
        L_0x0782:
            r0 = move-exception
            r2 = r0
            com.xiaomi.channel.commonutils.logger.b.a(r2)
            java.lang.String r2 = "receive a message which action string is not valid. is the reg expired?"
            com.xiaomi.channel.commonutils.logger.b.d(r2)
            return r4
        L_0x078d:
            r0 = move-exception
            r3 = r0
            com.xiaomi.channel.commonutils.logger.b.a(r3)
            r17.a(r18)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.ah.a(com.xiaomi.xmpush.thrift.af, boolean, byte[]):com.xiaomi.mipush.sdk.PushMessageHandler$a");
    }

    private a a(af afVar, byte[] bArr) {
        String str;
        String str2 = null;
        try {
            a a2 = ae.a(this.b, afVar);
            if (a2 == null) {
                StringBuilder sb = new StringBuilder("message arrived: receiving an un-recognized message. ");
                sb.append(afVar.a);
                b.d(sb.toString());
                return null;
            }
            com.xiaomi.xmpush.thrift.a a3 = afVar.a();
            b.a("message arrived: processing an arrived message, action=".concat(String.valueOf(a3)));
            if (ai.a[a3.ordinal()] != 1) {
                return null;
            }
            an anVar = (an) a2;
            t l = anVar.l();
            if (l == null) {
                str = "message arrived: receive an empty message without push content, drop it";
                b.d(str);
                return null;
            }
            if (!(afVar.h == null || afVar.h.s() == null)) {
                str2 = afVar.h.j.get("jobkey");
            }
            MiPushMessage generateMessage = PushMessageHelper.generateMessage(anVar, afVar.m(), false);
            generateMessage.setArrivedMessage(true);
            StringBuilder sb2 = new StringBuilder("message arrived: receive a message, msgid=");
            sb2.append(l.b());
            sb2.append(", jobkey=");
            sb2.append(str2);
            b.a(sb2.toString());
            return generateMessage;
        } catch (m e) {
            b.a((Throwable) e);
            str = "message arrived: receive a message but decrypt failed. report when click.";
        } catch (f e2) {
            b.a((Throwable) e2);
            str = "message arrived: receive a message which action string is not valid. is the reg expired?";
        }
    }

    public static ah a(Context context) {
        if (a == null) {
            a = new ah(context);
        }
        return a;
    }

    private void a() {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_extra", 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - sharedPreferences.getLong(Constants.SP_KEY_LAST_REINITIALIZE, 0)) > TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL) {
            MiPushClient.reInitialize(this.b, w.PackageUnregistered);
            sharedPreferences.edit().putLong(Constants.SP_KEY_LAST_REINITIALIZE, currentTimeMillis).commit();
        }
    }

    public static void a(Context context, String str) {
        synchronized (d) {
            c.remove(str);
            c.a(context);
            SharedPreferences b2 = c.b(context);
            String a2 = d.a((Collection<?>) c, (String) ",");
            Editor edit = b2.edit();
            edit.putString("pref_msg_ids", a2);
            ap.a(edit);
        }
    }

    private void a(aa aaVar) {
        long j;
        d dVar;
        String c2 = aaVar.c();
        Map<String, String> j2 = aaVar.j();
        if (j2 != null) {
            String str = j2.get(Constants.ASSEMBLE_PUSH_REG_INFO);
            if (!TextUtils.isEmpty(str)) {
                StringBuilder sb = new StringBuilder("brand:");
                sb.append(ac.FCM.name());
                if (str.contains(sb.toString())) {
                    b.c("receiver fcm token sync ack");
                    j = aaVar.g;
                    dVar = d.ASSEMBLE_PUSH_FCM;
                    a(c2, j, dVar);
                }
                StringBuilder sb2 = new StringBuilder("brand:");
                sb2.append(ac.HUAWEI.name());
                if (!str.contains(sb2.toString())) {
                    StringBuilder sb3 = new StringBuilder("brand:");
                    sb3.append(ac.OPPO.name());
                    if (str.contains(sb3.toString())) {
                        b.c("receiver COS token sync ack");
                        a(c2, aaVar.g, d.ASSEMBLE_PUSH_COS);
                    }
                }
            }
            return;
        }
        b.c("receiver hw token sync ack");
        j = aaVar.g;
        dVar = d.ASSEMBLE_PUSH_HUAWEI;
        a(c2, j, dVar);
    }

    private void a(af afVar) {
        b.a((String) "receive a message but decrypt failed. report now.");
        ai aiVar = new ai(afVar.m().a, false);
        aiVar.c(r.DecryptMessageFail.W);
        aiVar.b(afVar.h());
        aiVar.d(afVar.f);
        aiVar.h = new HashMap();
        aiVar.h.put("regid", MiPushClient.getRegId(this.b));
        aj.a(this.b).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, false, (u) null);
    }

    private void a(an anVar, af afVar) {
        u m = afVar.m();
        z zVar = new z();
        zVar.b(anVar.e());
        zVar.a(anVar.c());
        zVar.a(anVar.l().h());
        if (!TextUtils.isEmpty(anVar.h())) {
            zVar.c(anVar.h());
        }
        if (!TextUtils.isEmpty(anVar.j())) {
            zVar.d(anVar.j());
        }
        zVar.a(au.a(this.b, afVar));
        aj.a(this.b).a(zVar, com.xiaomi.xmpush.thrift.a.AckMessage, m);
    }

    private void a(String str, long j, d dVar) {
        ao c2 = i.c(dVar);
        if (c2 != null) {
            if (j == 0) {
                synchronized (ab.class) {
                    if (ab.a(this.b).e(str)) {
                        ab.a(this.b).d(str);
                        if ("syncing".equals(ab.a(this.b).a(c2))) {
                            ab.a(this.b).a(c2, "synced");
                        }
                    }
                }
            } else if ("syncing".equals(ab.a(this.b).a(c2))) {
                synchronized (ab.class) {
                    if (ab.a(this.b).e(str)) {
                        if (ab.a(this.b).c(str) < 10) {
                            ab.a(this.b).b(str);
                            aj.a(this.b).a(str, c2, dVar);
                        } else {
                            ab.a(this.b).d(str);
                        }
                    }
                }
            } else {
                ab.a(this.b).d(str);
            }
        }
    }

    private void b(af afVar) {
        u m = afVar.m();
        z zVar = new z();
        zVar.b(afVar.h());
        zVar.a(m.b());
        zVar.a(m.d());
        if (!TextUtils.isEmpty(m.f())) {
            zVar.c(m.f());
        }
        zVar.a(au.a(this.b, afVar));
        aj.a(this.b).a(zVar, com.xiaomi.xmpush.thrift.a.AckMessage, false, afVar.m());
    }

    private static boolean b(Context context, String str) {
        synchronized (d) {
            c.a(context);
            SharedPreferences b2 = c.b(context);
            if (c == null) {
                String[] split = b2.getString("pref_msg_ids", "").split(",");
                c = new LinkedList();
                for (String add : split) {
                    c.add(add);
                }
            }
            if (c.contains(str)) {
                return true;
            }
            c.add(str);
            if (c.size() > 25) {
                c.poll();
            }
            String a2 = d.a((Collection<?>) c, (String) ",");
            Editor edit = b2.edit();
            edit.putString("pref_msg_ids", a2);
            ap.a(edit);
            return false;
        }
    }

    private boolean c(af afVar) {
        return TextUtils.equals(Constants.HYBRID_PACKAGE_NAME, afVar.j()) && afVar.m() != null && afVar.m().s() != null && TextUtils.equals("1", afVar.m().s().get(Constants.EXTRA_KEY_HYBRID_PASS_THROUGH));
    }

    public a a(Intent intent) {
        String str;
        String str2;
        String action = intent.getAction();
        b.a("receive an intent from server, action=".concat(String.valueOf(action)));
        String stringExtra = intent.getStringExtra("mrt");
        if (stringExtra == null) {
            stringExtra = Long.toString(System.currentTimeMillis());
        }
        if ("com.xiaomi.mipush.RECEIVE_MESSAGE".equals(action)) {
            byte[] byteArrayExtra = intent.getByteArrayExtra("mipush_payload");
            boolean booleanExtra = intent.getBooleanExtra("mipush_notified", false);
            if (byteArrayExtra == null) {
                str2 = "receiving an empty message, drop";
            } else {
                af afVar = new af();
                try {
                    au.a(afVar, byteArrayExtra);
                    c a2 = c.a(this.b);
                    u m = afVar.m();
                    if (afVar.a() == com.xiaomi.xmpush.thrift.a.SendMessage && m != null && !a2.k() && !booleanExtra) {
                        m.a("mrt", stringExtra);
                        m.a("mat", Long.toString(System.currentTimeMillis()));
                        if (!c(afVar)) {
                            b(afVar);
                        } else {
                            b.b("this is a mina's pass through message, ack later");
                            m.a(Constants.EXTRA_KEY_MINA_MESSAGE_TS, String.valueOf(m.d()));
                            m.a(Constants.EXTRA_KEY_MINA_DEVICE_STATUS, String.valueOf(au.a(this.b, afVar)));
                            m.a(Constants.EXTRA_KEY_MINA_APPID, afVar.h());
                        }
                    }
                    if (afVar.a() == com.xiaomi.xmpush.thrift.a.SendMessage && !afVar.c()) {
                        if (!com.xiaomi.push.service.ah.b(afVar)) {
                            Object[] objArr = new Object[2];
                            objArr[0] = afVar.j();
                            objArr[1] = m != null ? m.b() : "";
                            b.a(String.format("drop an un-encrypted messages. %1$s, %2$s", objArr));
                            return null;
                        } else if (!booleanExtra || m.s() == null || !m.s().containsKey("notify_effect")) {
                            b.a(String.format("drop an un-encrypted messages. %1$s, %2$s", new Object[]{afVar.j(), m.b()}));
                            return null;
                        }
                    }
                    if (a2.i() || afVar.a == com.xiaomi.xmpush.thrift.a.Registration) {
                        if (!a2.i() || !a2.m()) {
                            return a(afVar, booleanExtra, byteArrayExtra);
                        }
                        if (afVar.a == com.xiaomi.xmpush.thrift.a.UnRegistration) {
                            a2.h();
                            MiPushClient.clearExtras(this.b);
                            PushMessageHandler.a();
                            return null;
                        }
                        MiPushClient.unregisterPush(this.b);
                        return null;
                    } else if (com.xiaomi.push.service.ah.b(afVar)) {
                        return a(afVar, booleanExtra, byteArrayExtra);
                    } else {
                        b.d("receive message without registration. need re-register!");
                        a();
                        return null;
                    }
                } catch (Exception | f e) {
                    b.a(e);
                    return null;
                }
            }
        } else if ("com.xiaomi.mipush.ERROR".equals(action)) {
            MiPushCommandMessage miPushCommandMessage = new MiPushCommandMessage();
            af afVar2 = new af();
            try {
                byte[] byteArrayExtra2 = intent.getByteArrayExtra("mipush_payload");
                if (byteArrayExtra2 != null) {
                    au.a(afVar2, byteArrayExtra2);
                }
            } catch (f unused) {
            }
            miPushCommandMessage.setCommand(String.valueOf(afVar2.a()));
            miPushCommandMessage.setResultCode((long) intent.getIntExtra("mipush_error_code", 0));
            miPushCommandMessage.setReason(intent.getStringExtra("mipush_error_msg"));
            StringBuilder sb = new StringBuilder("receive a error message. code = ");
            sb.append(intent.getIntExtra("mipush_error_code", 0));
            sb.append(", msg= ");
            sb.append(intent.getStringExtra("mipush_error_msg"));
            b.d(sb.toString());
            return miPushCommandMessage;
        } else if (!"com.xiaomi.mipush.MESSAGE_ARRIVED".equals(action)) {
            return null;
        } else {
            byte[] byteArrayExtra3 = intent.getByteArrayExtra("mipush_payload");
            if (byteArrayExtra3 == null) {
                str2 = "message arrived: receiving an empty message, drop";
            } else {
                af afVar3 = new af();
                au.a(afVar3, byteArrayExtra3);
                c a3 = c.a(this.b);
                if (com.xiaomi.push.service.ah.b(afVar3)) {
                    str = "message arrived: receive ignore reg message, ignore!";
                } else if (!a3.i()) {
                    str = "message arrived: receive message without registration. need unregister or re-register!";
                } else if (!a3.i() || !a3.m()) {
                    return a(afVar3, byteArrayExtra3);
                } else {
                    str = "message arrived: app info is invalidated";
                }
                b.d(str);
                return null;
            }
        }
        b.d(str2);
        return null;
    }

    public List<String> a(TimeZone timeZone, TimeZone timeZone2, List<String> list) {
        List<String> list2 = list;
        if (timeZone.equals(timeZone2)) {
            return list2;
        }
        long rawOffset = (long) (((timeZone.getRawOffset() - timeZone2.getRawOffset()) / 1000) / 60);
        long parseLong = Long.parseLong(list2.get(0).split(":")[0]);
        long parseLong2 = Long.parseLong(list2.get(0).split(":")[1]);
        long j = ((((parseLong * 60) + parseLong2) - rawOffset) + 1440) % 1440;
        long parseLong3 = ((((Long.parseLong(list2.get(1).split(":")[0]) * 60) + Long.parseLong(list2.get(1).split(":")[1])) - rawOffset) + 1440) % 1440;
        ArrayList arrayList = new ArrayList();
        arrayList.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(j / 60), Long.valueOf(j % 60)}));
        arrayList.add(String.format("%1$02d:%2$02d", new Object[]{Long.valueOf(parseLong3 / 60), Long.valueOf(parseLong3 % 60)}));
        return arrayList;
    }
}
