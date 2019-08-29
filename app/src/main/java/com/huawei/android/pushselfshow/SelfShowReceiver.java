package com.huawei.android.pushselfshow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushReceiver.ACTION;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.c.d;
import com.huawei.android.pushselfshow.permission.RequestPermissionsActivity;
import com.huawei.android.pushselfshow.utils.b.b;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;

public class SelfShowReceiver {

    static class a extends Thread {
        Context a;
        String b;

        public a(Context context, String str) {
            this.a = context;
            this.b = str;
        }

        public void run() {
            ArrayList a2 = com.huawei.android.pushselfshow.utils.a.a.a(this.a, this.b);
            int size = a2.size();
            c.e("PushSelfShowLog", "receive package add ,arrSize ".concat(String.valueOf(size)));
            for (int i = 0; i < size; i++) {
                com.huawei.android.pushselfshow.utils.a.a(this.a, "16", (String) a2.get(i), "app");
            }
            if (size > 0) {
                com.huawei.android.pushselfshow.utils.a.a.b(this.a, this.b);
            }
            com.huawei.android.pushselfshow.utils.a.b(new File(b.a(this.a)));
        }
    }

    public void a(Context context, Intent intent, com.huawei.android.pushselfshow.b.a aVar) {
        StringBuilder sb = new StringBuilder("receive a selfshow message ,the type is");
        sb.append(aVar.p);
        c.a("PushSelfShowLog", sb.toString());
        if (!com.huawei.android.pushselfshow.a.a.a(aVar.p)) {
            com.huawei.android.pushselfshow.utils.a.a(context, (String) "3", aVar);
            return;
        }
        long b = com.huawei.android.pushselfshow.utils.a.b(aVar.l);
        if (b == 0) {
            new d(context, aVar).start();
            return;
        }
        c.a("PushSelfShowLog", "waiting ……");
        intent.setPackage(context.getPackageName());
        com.huawei.android.pushselfshow.utils.a.a(context, intent, b);
    }

    public void a(Context context, Intent intent, String str, com.huawei.android.pushselfshow.b.a aVar, int i) {
        c.a("PushSelfShowLog", "receive a selfshow userhandle message");
        if (!"-1".equals(str)) {
            com.huawei.android.pushselfshow.c.b.a(context, intent);
        } else {
            com.huawei.android.pushselfshow.c.b.a(context, i);
        }
        if ("1".equals(str)) {
            new com.huawei.android.pushselfshow.a.a(context, aVar).a();
            if (aVar.o != null) {
                try {
                    JSONArray jSONArray = new JSONArray(aVar.o);
                    Intent intent2 = new Intent(ACTION.ACTION_NOTIFICATION_MSG_CLICK);
                    intent2.putExtra("click", jSONArray.toString()).setPackage(aVar.n).setFlags(32);
                    context.sendBroadcast(intent2);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("message.extras is not a json format,err info ");
                    sb.append(e.toString());
                    c.d("PushSelfShowLog", sb.toString());
                }
            }
        }
        if (!TextUtils.isEmpty(aVar.a)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(aVar.n);
            sb2.append(aVar.a);
            String sb3 = sb2.toString();
            c.a("PushSelfShowLog", "groupMap key is ".concat(String.valueOf(sb3)));
            if (com.huawei.android.pushselfshow.c.b.a.containsKey(sb3)) {
                com.huawei.android.pushselfshow.c.b.a.remove(sb3);
                StringBuilder sb4 = new StringBuilder("after remove, groupMap.size is:");
                sb4.append(com.huawei.android.pushselfshow.c.b.a.get(sb3));
                c.a("PushSelfShowLog", sb4.toString());
            }
        }
        com.huawei.android.pushselfshow.utils.a.a(context, str, aVar);
    }

    public void onReceive(Context context, Intent intent) {
        int i;
        if (context == null || intent == null) {
            c.a("PushSelfShowLog", "enter SelfShowReceiver receiver, context or intent is null");
            return;
        }
        try {
            c.a(context);
            String action = intent.getAction();
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                Uri data = intent.getData();
                if (data != null) {
                    String schemeSpecificPart = data.getSchemeSpecificPart();
                    c.e("PushSelfShowLog", "receive package add ,the pkgName is ".concat(String.valueOf(schemeSpecificPart)));
                    if (!TextUtils.isEmpty(schemeSpecificPart)) {
                        new a(context, schemeSpecificPart).start();
                    }
                }
                return;
            }
            if ("com.huawei.intent.action.PUSH".equals(action)) {
                if (RequestPermissionsActivity.a(context)) {
                    c.b("PushSelfShowLog", "needStartPermissionActivity");
                    RequestPermissionsActivity.a(context, intent);
                    return;
                }
                String str = null;
                if (intent.hasExtra("selfshow_info")) {
                    byte[] byteArrayExtra = intent.getByteArrayExtra("selfshow_info");
                    if (intent.hasExtra("selfshow_token")) {
                        byte[] byteArrayExtra2 = intent.getByteArrayExtra("selfshow_token");
                        if (intent.hasExtra("selfshow_event_id")) {
                            str = intent.getStringExtra("selfshow_event_id");
                        }
                        String str2 = str;
                        if (intent.hasExtra("selfshow_notify_id")) {
                            int intExtra = intent.getIntExtra("selfshow_notify_id", 0);
                            c.b("PushSelfShowLog", "get notifyId:".concat(String.valueOf(intExtra)));
                            i = intExtra;
                        } else {
                            i = 0;
                        }
                        com.huawei.android.pushselfshow.b.a aVar = new com.huawei.android.pushselfshow.b.a(byteArrayExtra, byteArrayExtra2);
                        if (!aVar.b()) {
                            c.a("PushSelfShowLog", "parseMessage failed");
                            return;
                        }
                        StringBuilder sb = new StringBuilder(" onReceive the msg id = ");
                        sb.append(aVar.m);
                        sb.append(",and cmd is");
                        sb.append(aVar.p);
                        sb.append(",and the eventId is ");
                        sb.append(str2);
                        c.a("PushSelfShowLog", sb.toString());
                        if (str2 == null) {
                            a(context, intent, aVar);
                        } else {
                            a(context, intent, str2, aVar, i);
                        }
                        com.huawei.android.pushselfshow.utils.a.b(new File(b.a(context)));
                    }
                }
            }
        } catch (Exception e) {
            c.a((String) "PushSelfShowLog", e.toString(), (Throwable) e);
        }
    }
}
