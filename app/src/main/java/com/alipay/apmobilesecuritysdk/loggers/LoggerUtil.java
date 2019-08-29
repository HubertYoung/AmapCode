package com.alipay.apmobilesecuritysdk.loggers;

import android.content.Context;
import android.os.Build;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.security.mobile.module.deviceinfo.DeviceInfo;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoggerUtil {
    public static synchronized void a(String str) {
        synchronized (LoggerUtil.class) {
            Logger.a(str);
        }
    }

    public static synchronized void a(Throwable th) {
        synchronized (LoggerUtil.class) {
            Logger.a(th);
        }
    }

    public static void a(Context context) {
        boolean isConnectedWifi = DeviceInfo.getInstance().isConnectedWifi(context);
        boolean e = SettingsStorage.e(context);
        if (isConnectedWifi && e) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getFilesDir().getAbsolutePath());
            sb.append("/log/ap");
            new Thread(new Runnable(context) {
                final /* synthetic */ Context a;

                {
                    this.a = r2;
                }

                public void run() {
                    try {
                        LogUploadManager.this.a(this.a);
                    } catch (Exception e) {
                        Logger.a((Throwable) e);
                    }
                }
            }).start();
        }
    }

    public static synchronized void a(Context context, String str, String str2, String str3) {
        synchronized (LoggerUtil.class) {
            LogTag logTag = new LogTag(Build.MODEL, context.getApplicationContext().getApplicationInfo().packageName, "security-sdk-VR", "3.6.5-20170830", str, str2, str3);
            StringBuilder sb = new StringBuilder();
            sb.append(context.getFilesDir().getAbsolutePath());
            sb.append("/log/ap");
            String sb2 = sb.toString();
            String format = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(format);
            sb3.append(".log");
            Logger.a(sb2, sb3.toString(), logTag.toString());
        }
    }
}
