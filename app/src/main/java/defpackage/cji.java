package defpackage;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.agroup.overlay.manager.EFAgroupOverlayManager$3;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cji reason: default package */
/* compiled from: EFAgroupOverlayManager */
public class cji implements cjd, cje, IAgroupOverlayService, cuj {
    public static volatile cji a;
    public cjh b;
    public bty c;
    public cjl d;
    public boolean e;
    public boolean f;
    public boolean g;
    public int h;
    public IOverlayManager i;
    public cir j;
    public cuj k;
    public cuj l;
    public int m;
    public HandlerThread n;
    public Handler o;
    public Callback<Status> p = new EFAgroupOverlayManager$3(this);
    private MemberIconStyle q = MemberIconStyle.SMALL_DAY;
    /* access modifiers changed from: private */
    public double r;
    /* access modifiers changed from: private */
    public double s;
    private MemberInfo t;
    /* access modifiers changed from: private */
    public AtomicBoolean u = new AtomicBoolean(false);
    private Runnable v = new Runnable() {
        public final void run() {
            if (cji.this.u.compareAndSet(false, true)) {
                try {
                    cji.b(cji.this);
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    cji.this.u.set(false);
                    throw th;
                }
                cji.this.u.set(false);
            }
        }
    };
    private Runnable w = new Runnable() {
        public final void run() {
            cji.this.b(400);
        }
    };

    public static cji e() {
        if (a == null) {
            synchronized (cji.class) {
                try {
                    if (a == null) {
                        a = new cji();
                    }
                }
            }
        }
        return a;
    }

    private cji() {
    }

    public final void a(MemberIconStyle memberIconStyle) {
        if (memberIconStyle == MemberIconStyle.BIG_DAY || memberIconStyle == MemberIconStyle.BIG_NIGHT) {
            throw new IllegalStateException("not support BIG_xxx style");
        } else if (this.q != memberIconStyle) {
            this.q = memberIconStyle;
            e(true);
            if (this.j.a != null) {
                this.j.a.e();
            }
            if (this.b != null) {
                this.b.a();
            }
        }
    }

    public final void a(boolean z) {
        if (!this.e) {
            this.g = z;
            return;
        }
        if (z && !this.g) {
            this.g = true;
            this.j.show();
            if (this.b != null) {
                this.b.a();
            }
        } else if (!z && this.g) {
            g();
            this.j.hide();
            this.g = false;
        }
    }

    public final boolean d() {
        return this.e && this.g && this.f;
    }

    public final void a(ArrayList<MemberInfo> arrayList) {
        if (this.e) {
            if (l()) {
                k();
            }
            this.j.c.a(arrayList);
            this.j.b.a(arrayList);
            boolean z = false;
            if (this.t != null) {
                if (this.t.uid != null) {
                    Iterator<MemberInfo> it = arrayList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        MemberInfo next = it.next();
                        if (this.t.uid.equals(next.uid)) {
                            this.j.a.e(next);
                            z = true;
                            break;
                        }
                    }
                }
                if (!z) {
                    b(true);
                }
            }
            b(20);
        }
    }

    private void b(final MemberInfo memberInfo) {
        if (memberInfo != null && this.e) {
            this.j.a.a(memberInfo, (b) new b() {
                public final boolean a(MemberInfo memberInfo) {
                    boolean z = memberInfo != null && memberInfo.hashCode() == memberInfo.hashCode();
                    if (z) {
                        cji.this.j.c.c(memberInfo);
                        cji.this.j.b.c(memberInfo);
                        cji.this.a(new GeoPoint(memberInfo.lon, memberInfo.lat));
                    }
                    return z;
                }
            });
            this.j.c.e();
            this.j.b.e();
        }
    }

    private void j() {
        if (this.e) {
            this.j.a.e();
            this.j.b.e();
            this.j.c.e();
        }
    }

    /* access modifiers changed from: private */
    public void b(long j2) {
        if (this.e && this.g && this.f && this.o != null) {
            this.o.removeCallbacks(this.v);
            this.o.postDelayed(this.v, j2);
        }
    }

    private void k() {
        if (this.o != null) {
            this.o.removeCallbacks(this.v);
        }
        if (this.e) {
            this.j.b.b();
            this.j.c.b();
            this.j.a.b();
        }
    }

    public final void g() {
        if (this.e && this.g) {
            k();
            if (!this.u.get()) {
                this.j.clear();
            }
            e(false);
        }
    }

    /* access modifiers changed from: private */
    public void a(GeoPoint geoPoint) {
        if (this.e && this.g && this.c != null && geoPoint != null) {
            if (this.d != null) {
                this.d.a();
            }
            this.c.X();
            this.c.a((GLGeoPoint) geoPoint);
        }
    }

    public final MemberInfo a() {
        return this.t;
    }

    public final void a(MemberInfo memberInfo) {
        a(memberInfo, true);
        if (memberInfo != null && this.h >= 0) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("from", this.h);
                jSONObject.put("type", memberInfo.online ? "online" : "offline");
                LogManager.actionLogV2("P00001", LogConstant.BUTTON_ID_AGROUP_MEMBER_CLICK, jSONObject);
            } catch (JSONException unused) {
            }
        }
    }

    private void a(MemberInfo memberInfo, boolean z) {
        if (memberInfo != null && this.e && this.g) {
            if (this.t == null || this.t.uid == null || !this.t.uid.equals(memberInfo.uid)) {
                this.t = memberInfo;
                b(memberInfo);
                if (z && this.l != null) {
                    this.l.onMemberSelected(this.t.uid);
                }
                if (z && this.k != null) {
                    this.k.onMemberSelected(this.t.uid);
                }
            }
        }
    }

    public final boolean b() {
        return b(true);
    }

    public final boolean b(boolean z) {
        boolean z2 = this.e && this.g && this.t != null;
        if (z2) {
            j();
            if (z && this.l != null) {
                this.l.onMemberSelected(null);
            }
            this.t = null;
        }
        return z2;
    }

    public final MemberIconStyle c() {
        return this.q;
    }

    private boolean l() {
        return this.b != null && this.b.d;
    }

    private void e(boolean z) {
        if (this.b != null) {
            this.b.d = z;
        }
    }

    public final void h() {
        if (this.e && this.g && this.b != null) {
            ArrayList<GeoPoint> b2 = this.b.b();
            if (b2.size() <= 1) {
                m();
            } else {
                this.d.a(b2);
            }
        }
    }

    public final void a(int i2, int i3, int i4, int i5) {
        if (this.d != null) {
            this.d.a(i2, i3, i4, i5);
        }
    }

    private void m() {
        if (this.e && this.g) {
            a(LocationInstrument.getInstance().getLatestPosition());
        }
    }

    public final void a(cuj cuj) {
        this.k = cuj;
    }

    public final void c(boolean z) {
        cir cir = this.j;
        if (cir.b != null) {
            cir.b.setShowOnTop(z);
        }
    }

    public final void d(boolean z) {
        cir cir = this.j;
        if (cir.c != null) {
            cir.c.setShowOnTop(z);
        }
    }

    public final void i() {
        cir cir = this.j;
        if (cir.a != null) {
            cir.a.setShowOnTop(true);
        }
    }

    public void onMemberSelected(String str) {
        if (this.t != null) {
            b(false);
        }
        if (this.e && this.g && this.b != null) {
            if (TextUtils.isEmpty(str)) {
                b(false);
                return;
            }
            MemberInfo a2 = this.b.a(str);
            if (a2 != null) {
                a(a2, false);
                return;
            }
            b(false);
            ToastHelper.showToast("未获取到该队员位置");
        }
    }

    public final void a(@NonNull Class<?> cls, AgroupScenes agroupScenes, PageBundle pageBundle, boolean z) {
        cuf cuf;
        switch (defpackage.cit.AnonymousClass1.a[agroupScenes.ordinal()]) {
            case 1:
                cuf = new cja(z);
                break;
            case 2:
                cuf = new cjb(pageBundle);
                break;
            case 3:
            case 4:
            case 5:
                cuf = new ciy();
                break;
            case 6:
                cuf = new civ(pageBundle);
                break;
            case 7:
                cuf = new cix();
                break;
            case 8:
                cuf = new cjc(pageBundle);
                break;
            default:
                cuf = new ciw();
                break;
        }
        a.a.a(cls, cuf);
    }

    public final void a(@NonNull Class<?> cls) {
        ciu a2 = a.a;
        if (cls != null) {
            a2.a.remove(cls);
        }
    }

    public final void f() {
        b(true);
    }

    public final void a(long j2) {
        if (j2 == 0 || !(((long) this.j.a.hashCode()) == j2 || ((long) this.j.b.hashCode()) == j2 || ((long) this.j.c.hashCode()) == j2)) {
            b(true);
        }
    }

    static /* synthetic */ void b(cji cji) {
        if (cji.e && cji.g && cji.f) {
            if (cji.l()) {
                cji.j.b.clear();
                cji.j.c.clear();
                cji.e(false);
            }
            if (cji.e) {
                cji.j.b.a();
                cji.j.c.a();
                cji.j.a.a();
            }
            cji.j.a.d();
            cji.j.b.d();
            cji.j.c.d();
        }
    }

    public static /* synthetic */ void e(cji cji) {
        ahm.b(cji.w);
        ahm.a(cji.w);
    }
}
