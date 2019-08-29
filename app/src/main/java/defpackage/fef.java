package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.antiattack.AntiAttackHandlerImpl$2;

/* renamed from: fef reason: default package */
/* compiled from: AntiAttackHandlerImpl */
public final class fef implements fee {
    public final AtomicBoolean a = new AtomicBoolean(false);
    public final Handler b = new Handler(Looper.getMainLooper());
    final Context c;
    public final Runnable d = new Runnable() {
        public final void run() {
            if (fef.this.a.compareAndSet(true, false)) {
                TBSdkLog.c("mtopsdk.AntiAttackHandlerImpl", "waiting antiattack result time out!");
                try {
                    fef.this.c.unregisterReceiver(fef.this.e);
                } catch (Exception unused) {
                }
            }
        }
    };
    final BroadcastReceiver e = new AntiAttackHandlerImpl$2(this);
    private final IntentFilter f = new IntentFilter("mtopsdk.extra.antiattack.result.notify.action");

    public fef(Context context) {
        this.c = context;
    }

    public final void a(String str) {
        if (fdd.a(str)) {
            try {
                boolean b2 = fgy.b();
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb = new StringBuilder("[handle]execute new 419 Strategy,");
                    sb.append("location=");
                    sb.append(str);
                    sb.append(", isBackground=");
                    sb.append(b2);
                    TBSdkLog.b("mtopsdk.AntiAttackHandlerImpl", sb.toString());
                }
                if (!b2 && this.a.compareAndSet(false, true)) {
                    fff.a();
                    long g = fff.g();
                    this.b.postDelayed(this.d, g > 0 ? g * 1000 : 20000);
                    Intent intent = new Intent();
                    intent.setAction("mtopsdk.mtop.antiattack.checkcode.validate.activity_action");
                    intent.setPackage(this.c.getPackageName());
                    intent.setFlags(268435456);
                    intent.putExtra("Location", str);
                    this.c.startActivity(intent);
                    this.c.registerReceiver(this.e, this.f);
                }
            } catch (Exception e2) {
                TBSdkLog.a((String) "mtopsdk.AntiAttackHandlerImpl", (String) "[handle] execute new 419 Strategy error.", (Throwable) e2);
            }
        }
    }
}
