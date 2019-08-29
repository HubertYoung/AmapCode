package defpackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.util.Constants;
import java.util.HashSet;
import java.util.Set;

/* renamed from: dbs reason: default package */
/* compiled from: PushMessageBroadcaster */
public final class dbs {
    private static Set<String> a;

    static {
        HashSet hashSet = new HashSet();
        a = hashSet;
        hashSet.add("8");
        a.add("9");
        a.add("10");
        a.add("13");
    }

    public static boolean a(Context context, String str, String str2, String str3, String str4) {
        boolean z;
        if (context == null || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str3)) {
            return false;
        }
        String a2 = a(str4);
        if (TextUtils.isEmpty(a2)) {
            z = false;
        } else {
            z = a.contains(a2.trim());
        }
        if (!z) {
            return false;
        }
        Intent intent = new Intent("com.autonavi.minimap.action.RECV_MESSAGE");
        intent.addFlags(268435488);
        intent.putExtra("id", TextUtils.isEmpty(str2) ? "" : str2);
        intent.putExtra("task_id", TextUtils.isEmpty(str) ? "" : str);
        intent.putExtra(Constants.BODY, str3);
        intent.putExtra("category", a2);
        StringBuilder sb = new StringBuilder("\nMessageId/TaskId/Category/MessageBody = ");
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        sb.append(str2);
        sb.append("/");
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        sb.append(str);
        sb.append("/");
        sb.append(a2);
        sb.append("/");
        if (TextUtils.isEmpty(str3)) {
            str3 = "";
        }
        sb.append(str3);
        AMapLog.d("PushMessageBroadcaster", sb.toString());
        context.sendBroadcast(intent, "com.autonavi.minimap.permission.RECV_MESSAGE");
        return true;
    }

    private static String a(String str) {
        try {
            Uri parse = Uri.parse(str);
            if (parse != null) {
                if (!parse.isOpaque()) {
                    return parse.getQueryParameter("category");
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
