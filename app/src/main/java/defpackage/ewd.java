package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.hmt.analytics.objects.o;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: ewd reason: default package */
/* compiled from: WakeMonitorTask */
public class ewd {
    public ArrayList<String> a = new ArrayList<>();
    public String b;
    final String c = ewd.class.getSimpleName();
    o d;

    private ewd() {
    }

    public static ewd a() {
        return new ewd();
    }

    public static void a(Context context, String str, String str2, String str3, ArrayList<String> arrayList) {
        euw.a((String) "startMonitor");
        euw.a((String) "getExplicitIntent");
        Intent intent = new Intent();
        intent.setAction(str3);
        intent.addCategory("android.intent.category.DEFAULT");
        ArrayList arrayList2 = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 0);
            if (queryIntentServices != null) {
                for (ResolveInfo next : queryIntentServices) {
                    ComponentName componentName = new ComponentName(next.serviceInfo.packageName, next.serviceInfo.name);
                    Intent intent2 = new Intent(intent);
                    intent2.setComponent(componentName);
                    arrayList2.add(intent2);
                }
            }
        }
        if (arrayList2.size() == 0) {
            euw.a((String) "intent arraylist is empty");
            return;
        }
        ArrayList<Intent> a2 = a(arrayList2, arrayList);
        StringBuilder sb = new StringBuilder("targetIntentList.size():");
        sb.append(a2.size());
        euw.a(sb.toString());
        Iterator<Intent> it = a2.iterator();
        while (it.hasNext()) {
            Intent next2 = it.next();
            StringBuilder sb2 = new StringBuilder("startService");
            sb2.append(next2.getComponent().toString());
            euw.a(sb2.toString());
            next2.putExtra("HMT_SOURCE", str);
            next2.putExtra("HMT_ACTION", str2);
            if (VERSION.SDK_INT >= 26) {
                context.startForegroundService(next2);
            } else {
                context.startService(next2);
            }
        }
    }

    private static ArrayList<Intent> a(ArrayList<Intent> arrayList, ArrayList<String> arrayList2) {
        ArrayList<Intent> arrayList3 = new ArrayList<>();
        Iterator<Intent> it = arrayList.iterator();
        while (it.hasNext()) {
            Intent next = it.next();
            if (next.getComponent() != null && !TextUtils.isEmpty(next.getComponent().getPackageName()) && arrayList2.contains(next.getComponent().getPackageName())) {
                arrayList3.add(next);
            }
        }
        return arrayList3;
    }
}
