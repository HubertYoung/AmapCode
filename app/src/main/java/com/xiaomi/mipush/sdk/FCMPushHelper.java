package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushReceiver.BOUND_KEY;
import java.util.Map;

public class FCMPushHelper {
    public static void clearToken(Context context) {
        g.a(context, d.ASSEMBLE_PUSH_FCM);
    }

    public static void convertMessage(Intent intent) {
        g.a(intent);
    }

    public static boolean isFCMSwitchOpen(Context context) {
        return g.b(context, d.ASSEMBLE_PUSH_FCM) && MiPushClient.getOpenFCMPush();
    }

    public static void notifyFCMNotificationCome(Context context, Map<String, String> map) {
        String str = map.get(BOUND_KEY.pushMsgKey);
        if (!TextUtils.isEmpty(str)) {
            PushMessageReceiver b = g.b(context);
            if (b != null) {
                b.onReceiveMessage(context, g.a(str));
            }
        }
    }

    public static void notifyFCMPassThoughMessageCome(Context context, Map<String, String> map) {
        String str = map.get(BOUND_KEY.pushMsgKey);
        if (!TextUtils.isEmpty(str)) {
            PushMessageReceiver b = g.b(context);
            if (b != null) {
                b.onReceivePassThroughMessage(context, g.a(str));
            }
        }
    }

    public static void reportFCMMessageDelete() {
        MiTinyDataClient.upload(g.b(d.ASSEMBLE_PUSH_FCM), "fcm", 1, "some fcm messages was deleted ");
    }

    public static void uploadToken(Context context, String str) {
        g.a(context, d.ASSEMBLE_PUSH_FCM, str);
    }
}
