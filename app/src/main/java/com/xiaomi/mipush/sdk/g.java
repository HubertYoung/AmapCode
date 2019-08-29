package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushReceiver.BOUND_KEY;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.push.service.an;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

public class g {
    public static MiPushMessage a(String str) {
        MiPushMessage miPushMessage = new MiPushMessage();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("messageId")) {
                    miPushMessage.setMessageId(jSONObject.getString("messageId"));
                }
                if (jSONObject.has("description")) {
                    miPushMessage.setDescription(jSONObject.getString("description"));
                }
                if (jSONObject.has("title")) {
                    miPushMessage.setTitle(jSONObject.getString("title"));
                }
                if (jSONObject.has("content")) {
                    miPushMessage.setContent(jSONObject.getString("content"));
                }
                if (jSONObject.has("passThrough")) {
                    miPushMessage.setPassThrough(jSONObject.getInt("passThrough"));
                }
                if (jSONObject.has("notifyType")) {
                    miPushMessage.setNotifyType(jSONObject.getInt("notifyType"));
                }
                if (jSONObject.has("messageType")) {
                    miPushMessage.setMessageType(jSONObject.getInt("messageType"));
                }
                if (jSONObject.has("alias")) {
                    miPushMessage.setAlias(jSONObject.getString("alias"));
                }
                if (jSONObject.has("topic")) {
                    miPushMessage.setTopic(jSONObject.getString("topic"));
                }
                if (jSONObject.has("user_account")) {
                    miPushMessage.setUserAccount(jSONObject.getString("user_account"));
                }
                if (jSONObject.has(KEY_TYPE.PUSH_KEY_NOTIFY_ID)) {
                    miPushMessage.setNotifyId(jSONObject.getInt(KEY_TYPE.PUSH_KEY_NOTIFY_ID));
                }
                if (jSONObject.has("category")) {
                    miPushMessage.setCategory(jSONObject.getString("category"));
                }
                if (jSONObject.has("isNotified")) {
                    miPushMessage.setNotified(jSONObject.getBoolean("isNotified"));
                }
                if (jSONObject.has("extra")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("extra");
                    Iterator<String> keys = jSONObject2.keys();
                    HashMap hashMap = new HashMap();
                    while (keys != null && keys.hasNext()) {
                        String next = keys.next();
                        hashMap.put(next, jSONObject2.getString(next));
                    }
                    if (hashMap.size() > 0) {
                        miPushMessage.setExtra(hashMap);
                        return miPushMessage;
                    }
                }
            } catch (Exception e) {
                b.d(e.toString());
            }
        }
        return miPushMessage;
    }

    protected static synchronized String a(Context context, String str) {
        String string;
        synchronized (g.class) {
            string = context.getSharedPreferences("mipush_extra", 0).getString(str, "");
        }
        return string;
    }

    public static String a(d dVar) {
        switch (h.a[dVar.ordinal()]) {
            case 1:
                return "hms_push_token";
            case 2:
                return "fcm_push_token";
            case 3:
                return "cos_push_token";
            default:
                return null;
        }
    }

    public static void a(Context context, d dVar) {
        String a = a(dVar);
        if (!TextUtils.isEmpty(a)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
            if (VERSION.SDK_INT >= 9) {
                sharedPreferences.edit().putString(a, "").apply();
            } else {
                sharedPreferences.edit().putString(a, "").commit();
            }
        }
    }

    public static void a(Context context, d dVar, String str) {
        if (!TextUtils.isEmpty(str)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
            String a = a(dVar);
            if (TextUtils.isEmpty(a)) {
                b.a((String) "can not find the key of token used in sp file");
                return;
            }
            String string = sharedPreferences.getString(a, "");
            if (TextUtils.isEmpty(string) || !str.equals(string)) {
                b.a((String) "send token upload");
                a(context, a, str);
                ao c = i.c(dVar);
                if (c != null) {
                    aj.a(context).a((String) null, c, dVar);
                }
            } else {
                b.a((String) "do not need to send token");
            }
        }
    }

    private static synchronized void a(Context context, String str, String str2) {
        synchronized (g.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putString(str, str2).commit();
        }
    }

    public static void a(Intent intent) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey(BOUND_KEY.pushMsgKey)) {
                intent.putExtra(PushMessageHelper.KEY_MESSAGE, a(extras.getString(BOUND_KEY.pushMsgKey)));
            }
        }
    }

    public static void a(String str, int i) {
        MiTinyDataClient.upload("hms_push_error", str, 1, "error code = ".concat(String.valueOf(i)));
    }

    public static boolean a(Context context) {
        if (context == null) {
            return false;
        }
        return d.c(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0041 A[Catch:{ Exception -> 0x0051 }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0050 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static com.xiaomi.mipush.sdk.PushMessageReceiver b(android.content.Context r5) {
        /*
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "com.xiaomi.mipush.RECEIVE_MESSAGE"
            r0.<init>(r1)
            java.lang.String r1 = r5.getPackageName()
            r0.setPackage(r1)
            android.content.pm.PackageManager r1 = r5.getPackageManager()
            r2 = 32
            r3 = 0
            java.util.List r0 = r1.queryBroadcastReceivers(r0, r2)     // Catch:{ Exception -> 0x0051 }
            if (r0 == 0) goto L_0x003e
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x0051 }
        L_0x001f:
            boolean r1 = r0.hasNext()     // Catch:{ Exception -> 0x0051 }
            if (r1 == 0) goto L_0x003e
            java.lang.Object r1 = r0.next()     // Catch:{ Exception -> 0x0051 }
            android.content.pm.ResolveInfo r1 = (android.content.pm.ResolveInfo) r1     // Catch:{ Exception -> 0x0051 }
            android.content.pm.ActivityInfo r2 = r1.activityInfo     // Catch:{ Exception -> 0x0051 }
            if (r2 == 0) goto L_0x001f
            android.content.pm.ActivityInfo r2 = r1.activityInfo     // Catch:{ Exception -> 0x0051 }
            java.lang.String r2 = r2.packageName     // Catch:{ Exception -> 0x0051 }
            java.lang.String r4 = r5.getPackageName()     // Catch:{ Exception -> 0x0051 }
            boolean r2 = r2.equals(r4)     // Catch:{ Exception -> 0x0051 }
            if (r2 == 0) goto L_0x001f
            goto L_0x003f
        L_0x003e:
            r1 = r3
        L_0x003f:
            if (r1 == 0) goto L_0x0050
            android.content.pm.ActivityInfo r5 = r1.activityInfo     // Catch:{ Exception -> 0x0051 }
            java.lang.String r5 = r5.name     // Catch:{ Exception -> 0x0051 }
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch:{ Exception -> 0x0051 }
            java.lang.Object r5 = r5.newInstance()     // Catch:{ Exception -> 0x0051 }
            com.xiaomi.mipush.sdk.PushMessageReceiver r5 = (com.xiaomi.mipush.sdk.PushMessageReceiver) r5     // Catch:{ Exception -> 0x0051 }
            return r5
        L_0x0050:
            return r3
        L_0x0051:
            r5 = move-exception
            java.lang.String r5 = r5.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r5)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.g.b(android.content.Context):com.xiaomi.mipush.sdk.PushMessageReceiver");
    }

    public static String b(d dVar) {
        switch (h.a[dVar.ordinal()]) {
            case 1:
                return "hms_push_error";
            case 2:
                return "fcm_push_error";
            case 3:
                return "cos_push_error";
            default:
                return null;
        }
    }

    public static boolean b(Context context, d dVar) {
        if (i.b(dVar) != null) {
            return an.a(context).a(i.b(dVar).a(), false);
        }
        return false;
    }

    public static HashMap<String, String> c(Context context, d dVar) {
        ApplicationInfo applicationInfo;
        StringBuilder sb;
        ac acVar;
        HashMap<String, String> hashMap = new HashMap<>();
        String a = a(dVar);
        if (TextUtils.isEmpty(a)) {
            return hashMap;
        }
        String str = null;
        switch (h.a[dVar.ordinal()]) {
            case 1:
                try {
                    applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                } catch (Exception e) {
                    b.d(e.toString());
                    applicationInfo = null;
                }
                int i = -1;
                if (applicationInfo != null) {
                    i = applicationInfo.metaData.getInt(Constants.HUAWEI_HMS_CLIENT_APPID);
                }
                sb = new StringBuilder("brand:");
                sb.append(k.a(context).name());
                sb.append("~token:");
                sb.append(a(context, a));
                sb.append("~package_name:");
                sb.append(context.getPackageName());
                sb.append("~app_id:");
                sb.append(i);
                break;
            case 2:
                sb = new StringBuilder("brand:");
                acVar = ac.FCM;
                break;
            case 3:
                sb = new StringBuilder("brand:");
                acVar = ac.OPPO;
                break;
            default:
                hashMap.put(Constants.ASSEMBLE_PUSH_REG_INFO, str);
                return hashMap;
        }
        sb.append(acVar.name());
        sb.append("~token:");
        sb.append(a(context, a));
        sb.append("~package_name:");
        sb.append(context.getPackageName());
        str = sb.toString();
        hashMap.put(Constants.ASSEMBLE_PUSH_REG_INFO, str);
        return hashMap;
    }

    public static void c(Context context) {
        e.a(context).register();
    }

    public static void d(Context context) {
        e.a(context).unregister();
    }
}
