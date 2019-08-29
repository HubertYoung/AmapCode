package defpackage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import java.lang.ref.WeakReference;

/* renamed from: bmc reason: default package */
/* compiled from: AlipayTools */
public class bmc {
    public static final String a = "bmc";
    /* access modifiers changed from: private */
    public WeakReference<Activity> b;
    /* access modifiers changed from: private */
    public ans<bmb> c;
    private a d = new a(this);

    /* renamed from: bmc$a */
    /* compiled from: AlipayTools */
    static class a extends Handler {
        private WeakReference<bmc> a;

        public a(bmc bmc) {
            this.a = new WeakReference<>(bmc);
        }

        public final void handleMessage(Message message) {
            Object obj = message.obj;
            if (obj != null && (obj instanceof String) && !TextUtils.isEmpty((String) message.obj)) {
                bmb bmb = new bmb((String) message.obj);
                if (message.what == 1) {
                    WeakReference<bmc> weakReference = this.a;
                    bmc bmc = weakReference != null ? (bmc) weakReference.get() : null;
                    if (!(bmc == null || bmc.c == null)) {
                        bmc.c.a(bmb);
                    }
                }
            }
        }
    }

    public bmc(Activity activity, ans ans) {
        this.b = new WeakReference<>(activity);
        this.c = ans;
    }

    static /* synthetic */ void a(bmc bmc, String str) {
        Message message = new Message();
        message.what = 1;
        message.obj = str;
        bmc.d.sendMessage(message);
    }
}
