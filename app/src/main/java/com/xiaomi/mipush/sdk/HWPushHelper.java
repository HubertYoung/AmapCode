package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushReceiver.BOUND_KEY;
import com.xiaomi.channel.commonutils.logger.b;
import org.json.JSONArray;
import org.json.JSONObject;

public class HWPushHelper {
    private static boolean a = false;

    public static void convertMessage(Intent intent) {
        g.a(intent);
    }

    public static boolean hasNetwork(Context context) {
        return g.a(context);
    }

    public static boolean isHmsTokenSynced(Context context) {
        String a2 = g.a(d.ASSEMBLE_PUSH_HUAWEI);
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        String a3 = g.a(context, a2);
        String a4 = ab.a(context).a(ao.UPLOAD_HUAWEI_TOKEN);
        return !TextUtils.isEmpty(a3) && !TextUtils.isEmpty(a4) && "synced".equals(a4);
    }

    public static boolean isUserOpenHmsPush() {
        return MiPushClient.getOpenHmsPush();
    }

    public static boolean needConnect() {
        return a;
    }

    public static void notifyHmsNotificationMessageClicked(Context context, String str) {
        String str2;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                if (jSONArray.length() > 0) {
                    int i = 0;
                    while (true) {
                        if (i >= jSONArray.length()) {
                            break;
                        }
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        if (jSONObject.has(BOUND_KEY.pushMsgKey)) {
                            str2 = jSONObject.getString(BOUND_KEY.pushMsgKey);
                            break;
                        }
                        i++;
                    }
                }
            } catch (Exception e) {
                b.d(e.toString());
            }
        }
        str2 = "";
        PushMessageReceiver b = g.b(context);
        if (b != null) {
            b.onNotificationMessageClicked(context, g.a(str2));
        }
    }

    public static void notifyHmsPassThoughMessageArrived(Context context, String str) {
        String str2 = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("content")) {
                    str2 = jSONObject.getString("content");
                }
            }
        } catch (Exception e) {
            b.d(e.toString());
        }
        PushMessageReceiver b = g.b(context);
        if (b != null) {
            b.onReceivePassThroughMessage(context, g.a(str2));
        }
    }

    public static void registerHuaWeiAssemblePush(Context context) {
        AbstractPushManager c = e.a(context).c(d.ASSEMBLE_PUSH_HUAWEI);
        if (c != null) {
            c.register();
        }
    }

    public static void reportError(String str, int i) {
        g.a(str, i);
    }

    public static synchronized void setConnectTime(Context context) {
        synchronized (HWPushHelper.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_connect_time", System.currentTimeMillis()).commit();
        }
    }

    public static synchronized void setGetTokenTime(Context context) {
        synchronized (HWPushHelper.class) {
            context.getSharedPreferences("mipush_extra", 0).edit().putLong("last_get_token_time", System.currentTimeMillis()).commit();
        }
    }

    public static void setNeedConnect(boolean z) {
        a = z;
    }

    public static synchronized boolean shouldGetToken(Context context) {
        synchronized (HWPushHelper.class) {
            return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_get_token_time", -1)) > 172800000;
        }
    }

    public static synchronized boolean shouldTryConnect(Context context) {
        synchronized (HWPushHelper.class) {
            return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_connect_time", -1)) > 5000;
        }
    }

    public static void uploadToken(Context context, String str) {
        g.a(context, d.ASSEMBLE_PUSH_HUAWEI, str);
    }
}
