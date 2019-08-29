package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import java.lang.reflect.Method;
import java.util.List;

/* renamed from: ezz reason: default package */
/* compiled from: CommandWorker */
public final class ezz extends ewz {
    private static ezz d;
    public String c;
    private Handler e = new Handler(Looper.getMainLooper());

    private ezz() {
    }

    public static synchronized ezz a() {
        ezz ezz;
        synchronized (ezz.class) {
            try {
                if (d == null) {
                    d = new ezz();
                }
                ezz = d;
            }
        }
        return ezz;
    }

    public final void a(Intent intent) {
        if (intent == null || this.a == null) {
            StringBuilder sb = new StringBuilder(" sendMessage error: intent : ");
            sb.append(intent);
            sb.append(", mContext: ");
            sb.append(this.a);
            fat.d("CommandWorker", sb.toString());
            return;
        }
        Message obtain = Message.obtain();
        obtain.obj = intent;
        a(obtain);
    }

    public final void b(Message message) {
        Intent intent = (Intent) message.obj;
        if (intent == null || this.a == null) {
            StringBuilder sb = new StringBuilder(" handleMessage error: intent : ");
            sb.append(intent);
            sb.append(", mContext: ");
            sb.append(this.a);
            fat.d("CommandWorker", sb.toString());
            return;
        }
        String action = intent.getAction();
        String packageName = this.a.getPackageName();
        if (TextUtils.isEmpty(this.c)) {
            this.c = a(this.a, packageName, action);
            if (TextUtils.isEmpty(this.c)) {
                StringBuilder sb2 = new StringBuilder(" reflectReceiver error: receiver for: ");
                sb2.append(action);
                sb2.append(" not found, package: ");
                sb2.append(packageName);
                fat.d("CommandWorker", sb2.toString());
                intent.setPackage(packageName);
                this.a.sendBroadcast(intent);
                return;
            }
        }
        try {
            Class<?> cls = Class.forName(this.c);
            Object newInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
            Method method = cls.getMethod("onReceive", new Class[]{Context.class, Intent.class});
            intent.setClassName(packageName, this.c);
            this.e.post(new faa(this, method, newInstance, new Object[]{this.a.getApplicationContext(), intent}));
        } catch (Exception e2) {
            fat.b("CommandWorker", "reflect e: ", e2);
        }
    }

    private static String a(Context context, String str, String str2) {
        String str3 = null;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str2);
        intent.setPackage(str);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            List<ResolveInfo> queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 64);
            if (queryBroadcastReceivers != null && queryBroadcastReceivers.size() > 0) {
                str3 = queryBroadcastReceivers.get(0).activityInfo.name;
            }
            return str3;
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("error  ");
            sb.append(e2.getMessage());
            fat.a((String) "CommandWorker", sb.toString());
        }
    }
}
