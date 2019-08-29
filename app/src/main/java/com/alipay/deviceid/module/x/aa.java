package com.alipay.deviceid.module.x;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.alipay.apmobilesecuritysdk.rpc.gen.DeviceData;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class aa {
    public static void a(Context context, r rVar) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        boolean z = true;
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected() || activeNetworkInfo.getType() != 1) {
            z = false;
        }
        if (z && ax.a(context)) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getFilesDir().getAbsolutePath());
            sb.append("/log/ap");
            new Thread(new y(new x(sb.toString(), rVar))).start();
        }
    }

    public static synchronized void a(Context context, String str, String str2, String str3) {
        synchronized (aa.class) {
            w wVar = new w(Build.MODEL, context.getApplicationContext().getApplicationInfo().packageName, DeviceData.DEFAULT_AA3, "6.1.0.20180401", str, str2, str3);
            StringBuilder sb = new StringBuilder();
            sb.append(context.getFilesDir().getAbsolutePath());
            sb.append("/log/ap");
            String sb2 = sb.toString();
            String format = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(format);
            sb3.append(".log");
            z.a(sb2, sb3.toString(), wVar.toString());
        }
    }

    public static synchronized void a(String str) {
        synchronized (aa.class) {
            z.a(str);
        }
    }

    public static synchronized void a(Throwable th) {
        synchronized (aa.class) {
            z.a(th);
        }
    }
}
