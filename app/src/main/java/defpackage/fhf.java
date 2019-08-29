package defpackage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.deviceid.DeviceTokenClient.InitResultListener;
import com.alipay.sdk.app.PayTask;
import java.lang.ref.WeakReference;
import java.util.Map;

/* renamed from: fhf reason: default package */
/* compiled from: AlipayProxy */
public class fhf {
    public static final String a = "fhf";
    /* access modifiers changed from: private */
    public Handler b = new Handler() {
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    e eVar = (e) message.obj;
                    if (eVar != null) {
                        fhh fhh = new fhh(eVar.b);
                        if (eVar.a != null) {
                            eVar.a.a(fhh);
                        }
                        return;
                    }
                    break;
                case 2:
                    b bVar = (b) message.obj;
                    if (!(bVar == null || bVar.a == null)) {
                        bVar.a.a(bVar.b, bVar.c);
                        break;
                    }
            }
        }
    };

    /* renamed from: fhf$a */
    /* compiled from: AlipayProxy */
    public interface a {
        void a(String str, int i);
    }

    /* renamed from: fhf$b */
    /* compiled from: AlipayProxy */
    static class b {
        public a a;
        public String b;
        public int c;

        private b() {
        }

        /* synthetic */ b(byte b2) {
            this();
        }
    }

    /* renamed from: fhf$c */
    /* compiled from: AlipayProxy */
    public static class c implements InitResultListener {
        WeakReference<fhf> a;
        a b;

        public c(fhf fhf, a aVar) {
            this.a = new WeakReference<>(fhf);
            this.b = aVar;
        }

        public final void onResult(String str, int i) {
            if (this.a != null && this.a.get() != null) {
                fhf fhf = (fhf) this.a.get();
                if (fhf != null) {
                    b bVar = new b(0);
                    bVar.a = this.b;
                    bVar.b = str;
                    bVar.c = i;
                    Message message = new Message();
                    message.what = 2;
                    message.obj = bVar;
                    fhf.b.sendMessage(message);
                }
            }
        }
    }

    /* renamed from: fhf$d */
    /* compiled from: AlipayProxy */
    public static class d implements Runnable {
        WeakReference<fhf> a;
        String b;
        Activity c;
        ans<fhh> d;

        public d(fhf fhf, String str, Activity activity, ans<fhh> ans) {
            this.a = new WeakReference<>(fhf);
            this.b = str;
            this.c = activity;
            this.d = ans;
        }

        public final void run() {
            if (this.a != null && this.a.get() != null) {
                fhf fhf = (fhf) this.a.get();
                if (fhf != null && !TextUtils.isEmpty(this.b) && this.c != null) {
                    Map<String, String> payV2 = new PayTask(this.c).payV2(this.b, true);
                    new Object[1][0] = payV2.toString();
                    fhf.a();
                    e eVar = new e(0);
                    eVar.a = this.d;
                    eVar.b = payV2;
                    Message message = new Message();
                    message.what = 1;
                    message.obj = eVar;
                    fhf.b.sendMessage(message);
                }
            }
        }
    }

    /* renamed from: fhf$e */
    /* compiled from: AlipayProxy */
    static class e {
        public ans<fhh> a;
        public Map<String, String> b;

        private e() {
        }

        /* synthetic */ e(byte b2) {
            this();
        }
    }

    public static void a() {
    }

    public static void b() {
    }
}
