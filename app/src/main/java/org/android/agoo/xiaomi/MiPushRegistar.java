package org.android.agoo.xiaomi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.taobao.agoo.BaseNotifyClickActivity.INotifyListener;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageHelper;

public class MiPushRegistar {
    private static final String a = "Xiaomi".toLowerCase();
    private static String b = Build.BRAND;

    /* renamed from: org.android.agoo.xiaomi.MiPushRegistar$2 reason: invalid class name */
    static class AnonymousClass2 implements Runnable {
        final /* synthetic */ Context a;

        public final void run() {
            MiPushClient.unregisterPush(this.a);
        }
    }

    static class XiaoMiNotifyListener implements INotifyListener {
        public String getMsgSource() {
            return "xiaomi";
        }

        private XiaoMiNotifyListener() {
        }

        /* synthetic */ XiaoMiNotifyListener(byte b) {
            this();
        }

        public String parseMsgFromIntent(Intent intent) {
            String str;
            try {
                str = ((MiPushMessage) intent.getSerializableExtra(PushMessageHelper.KEY_MESSAGE)).getContent();
            } catch (Exception unused) {
                str = null;
            }
            ALog.i("MiPushRegistar", "parseMsgFromIntent", "msg", str);
            return str;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("INotifyListener: ");
            sb.append(getMsgSource());
            return sb.toString();
        }
    }

    private static boolean a(Context context) {
        boolean z;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (TextUtils.equals(a, b.toLowerCase())) {
                PackageInfo packageInfo = packageManager.getPackageInfo("com.xiaomi.xmsf", 4);
                if (packageInfo != null && packageInfo.versionCode >= 105) {
                    z = true;
                    ALog.d("MiPushRegistar", "checkDevice", "result", Boolean.valueOf(z));
                    return z;
                }
            }
        } catch (Throwable th) {
            ALog.e("MiPushRegistar", "checkDevice", th, new Object[0]);
        }
        z = false;
        ALog.d("MiPushRegistar", "checkDevice", "result", Boolean.valueOf(z));
        return z;
    }

    public static void a(final Context context, final String str, final String str2) {
        try {
            if (!UtilityImpl.isMainProcess(context)) {
                ALog.e("MiPushRegistar", "register not in main process, return", new Object[0]);
                return;
            }
            if (a(context)) {
                ALog.i("MiPushRegistar", "register begin", new Object[0]);
                BaseNotifyClickActivity.addNotifyListener(new XiaoMiNotifyListener(0));
                new Thread(new Runnable() {
                    public final void run() {
                        MiPushClient.registerPush(context, str, str2);
                    }
                }).start();
            }
        } catch (Throwable th) {
            ALog.e("MiPushRegistar", "register", th, new Object[0]);
        }
    }
}
