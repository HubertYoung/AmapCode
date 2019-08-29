package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.mqtt.MQTTBizType;
import com.amap.bundle.mqtt.MQTTConnectionStauts;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: dkv reason: default package */
/* compiled from: PersistentConnection */
public final class dkv {
    public int a = 0;
    public boolean b = false;
    public String[] c = new String[0];
    public dkr d;
    public dkw e;
    public ArrayList<dku> f = new ArrayList<>();
    public dku g = new dku() {
        private Handler b = null;

        private static boolean a() {
            return Looper.myLooper() == Looper.getMainLooper();
        }

        private void a(Runnable runnable) {
            if (this.b == null) {
                this.b = new Handler(Looper.getMainLooper());
            }
            this.b.post(runnable);
        }

        public final void a(final String str, final String str2) {
            if (a()) {
                for (int i = 0; i < dkv.this.f.size(); i++) {
                    dkv.this.f.get(i).a(str, str2);
                }
                return;
            }
            a(new Runnable() {
                public final void run() {
                    for (int i = 0; i < dkv.this.f.size(); i++) {
                        dkv.this.f.get(i).a(str, str2);
                    }
                }
            });
        }

        public final void b(final String str) {
            if (a()) {
                for (int i = 0; i < dkv.this.f.size(); i++) {
                    dkv.this.f.get(i).b(str);
                }
                return;
            }
            a(new Runnable() {
                public final void run() {
                    for (int i = 0; i < dkv.this.f.size(); i++) {
                        dkv.this.f.get(i).b(str);
                    }
                }
            });
        }

        public final void m() {
            if (a()) {
                for (int i = 0; i < dkv.this.f.size(); i++) {
                    dkv.this.f.get(i).m();
                }
                return;
            }
            a(new Runnable() {
                public final void run() {
                    for (int i = 0; i < dkv.this.f.size(); i++) {
                        dkv.this.f.get(i).m();
                    }
                }
            });
        }

        public final void n() {
            if (a()) {
                for (int i = 0; i < dkv.this.f.size(); i++) {
                    dkv.this.f.get(i).n();
                }
                return;
            }
            a(new Runnable() {
                public final void run() {
                    for (int i = 0; i < dkv.this.f.size(); i++) {
                        dkv.this.f.get(i).n();
                    }
                }
            });
        }
    };
    public Runnable h = new Runnable() {
        public final void run() {
            dkv.this.l = false;
        }
    };
    public yg i;
    boolean j = false;
    public boolean k = false;
    public boolean l = false;
    private yf m = new yf() {
        public final void onResponse(int i, JSONObject jSONObject) {
        }

        public final MQTTBizType getMQTTBizType() {
            return MQTTBizType.TEAM;
        }

        public final void onConnectionStatusChanged(MQTTConnectionStauts mQTTConnectionStauts) {
            if (mQTTConnectionStauts.equals(MQTTConnectionStauts.CONNECTED)) {
                dkv.this.g.m();
                return;
            }
            if (mQTTConnectionStauts.equals(MQTTConnectionStauts.DISCONNECTED)) {
                dkv.this.g.n();
            }
        }

        public final void onMessageArrived(JSONObject jSONObject) {
            dkv.this.g.a("", jSONObject != null ? jSONObject.toString() : "");
        }
    };

    public final void a() {
        if (this.b) {
            if (this.a == 1) {
                this.k = true;
            }
            if (this.a == 0 && this.d != null) {
                this.d.a();
            }
        }
    }

    public final void b() {
        if (this.b) {
            if (this.a == 1) {
                c();
                this.k = false;
            }
            if (this.a == 0 && this.d != null) {
                this.d.b();
            }
        }
    }

    public final void c() {
        ahm.b(this.h);
        this.h.run();
    }

    public final void d() {
        if (this.b) {
            if (this.a == 1) {
                f();
            }
            if (this.a == 0 && this.d != null) {
                this.d.c();
            }
            c();
            this.d = null;
            this.e = null;
            this.c = null;
            this.f.clear();
        }
    }

    public final void a(dku dku) {
        this.f.remove(dku);
    }

    public final void e() {
        if (!this.j) {
            this.j = true;
            this.i = yh.a(this.m);
        }
    }

    public final void f() {
        if (this.j) {
            this.j = false;
            yh.a(this.m.getMQTTBizType());
            this.i = null;
            this.k = false;
            this.l = false;
        }
    }
}
