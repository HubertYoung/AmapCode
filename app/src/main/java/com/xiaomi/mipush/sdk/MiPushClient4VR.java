package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.xmpush.thrift.a;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;

public class MiPushClient4VR {
    public static void uploadData(Context context, String str) {
        ai aiVar = new ai();
        aiVar.c(r.VRUpload.W);
        aiVar.b(c.a(context).c());
        aiVar.d(context.getPackageName());
        aiVar.a("data", str);
        aiVar.a(MiPushClient.generatePacketID());
        aj.a(context).a(aiVar, a.Notification, (u) null);
    }
}
