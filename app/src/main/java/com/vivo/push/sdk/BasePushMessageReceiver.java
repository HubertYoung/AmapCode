package com.vivo.push.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import java.util.List;

public abstract class BasePushMessageReceiver extends BroadcastReceiver implements ezy {
    public final void onReceive(Context context, Intent intent) {
        Context applicationContext = context.getApplicationContext();
        ezv.a().a(applicationContext);
        try {
            int intExtra = intent.getIntExtra("method", -1);
            String stringExtra = intent.getStringExtra("req_id");
            StringBuilder sb = new StringBuilder("PushMessageReceiver ");
            sb.append(applicationContext.getPackageName());
            sb.append(" ; type = ");
            sb.append(intExtra);
            sb.append(" ; requestId = ");
            sb.append(stringExtra);
            fat.d("PushMessageReceiver", sb.toString());
            try {
                ezv a = ezv.a();
                fbh a2 = a.h.a(intent);
                Context context2 = ezv.a().b;
                if (a2 == null) {
                    fat.a((String) "PushClientManager", (String) "sendCommand, null command!");
                    if (context2 != null) {
                        fat.c(context2, (String) "[执行指令失败]指令空！");
                    }
                    return;
                }
                eya b = a.h.b(a2);
                if (b == null) {
                    fat.a((String) "PushClientManager", "sendCommand, null command task! pushCommand = ".concat(String.valueOf(a2)));
                    if (context2 != null) {
                        StringBuilder sb2 = new StringBuilder("[执行指令失败]指令");
                        sb2.append(a2);
                        sb2.append("任务空！");
                        fat.c(context2, sb2.toString());
                    }
                    return;
                }
                if (context2 != null && !(a2 instanceof exn)) {
                    fat.a(context2, "[接收指令]".concat(String.valueOf(a2)));
                }
                b.a(this);
                fbf.a((fbe) b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            fat.b("PushMessageReceiver", "get method error", e2);
        }
    }

    public boolean a(Context context) {
        if (context == null) {
            fat.a((String) "PushMessageReceiver", (String) "isAllowNet sContext is null");
            return false;
        }
        String packageName = context.getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            fat.a((String) "PushMessageReceiver", (String) "isAllowNet pkgName is null");
            return false;
        }
        Intent intent = new Intent("com.vivo.pushservice.action.PUSH_SERVICE");
        intent.setPackage(packageName);
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 576);
        if (queryIntentServices != null && queryIntentServices.size() > 0) {
            return faw.a(context, packageName);
        }
        fat.a((String) "PushMessageReceiver", (String) "this is client sdk");
        return true;
    }
}
