package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.hmt.analytics.service.KeepService;
import com.hmt.analytics.task.WaTask;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* renamed from: ewk reason: default package */
/* compiled from: HMTUtils */
public class ewk {
    private static final String a = "ewk";

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static void a(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            Intent intent = new Intent(context, KeepService.class);
            intent.putExtra("path", str);
            List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
            if (queryIntentServices == null || queryIntentServices.size() == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(ewc.b);
                sb.append("9");
                ewc.b = sb.toString();
                euw.a((String) "There is no Service 2 handle the ws task!");
                WaTask.a(context, str);
            } else if (queryIntentServices.size() != 1) {
                euw.a((String) "Find more than one KeepService");
            } else if (b(context, queryIntentServices.get(0).serviceInfo.name)) {
                euw.a((String) "KeepService has already Started");
            } else if (VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent);
            } else {
                context.startService(intent);
            }
        }
    }

    private static boolean b(Context context, String str) {
        List<RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningServices(30);
        boolean z = false;
        if (runningServices.size() <= 0) {
            return false;
        }
        int i = 0;
        while (true) {
            if (i >= runningServices.size()) {
                break;
            } else if (runningServices.get(i).service.getClassName().equals(str)) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        return z;
    }

    public static boolean a(Context context, JSONObject jSONObject) {
        String optString = jSONObject.optString("is_ip");
        String str = (String) ewp.b(context, "hmt_is_in_location", "-1");
        if (optString.equals("1")) {
            if (str.equals("1")) {
                return true;
            }
        } else if (optString.equals("0")) {
            return true;
        }
        return false;
    }
}
