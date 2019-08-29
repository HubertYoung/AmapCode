package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.route.foot.page.AjxFootMapPage;
import com.autonavi.minimap.route.foot.presenter.AjxFootMapPresenter$3;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ecq reason: default package */
/* compiled from: AjxFootMapPresenter */
public final class ecq extends Ajx3PagePresenter implements axe, bgo {
    public IRouteUI a;
    public acg b;
    public POI c;
    /* access modifiers changed from: private */
    public ctl d;
    /* access modifiers changed from: private */
    public POI e;
    private boolean f = true;
    private int g = -1;

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    private static boolean d() {
        ebm.a(3);
        return false;
    }

    private static boolean e() {
        ebm.a(3);
        return false;
    }

    public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        if (iRouteHeaderEvent == null) {
            return false;
        }
        switch (iRouteHeaderEvent) {
            case START_CLICK:
                d();
                break;
            case END_CLICK:
                e();
                break;
            case PREPARE_SWITCH_TAB:
                if (!RouteType.RIDE.equals((RouteType) pageBundle.getObject(IRouteHeaderEvent.PREPARE_SWITCH_TAB.name()))) {
                    bdf bdf = (bdf) a.a.a(bdf.class);
                    if (bdf != null) {
                        bdf.a(this.b != null ? this.b.h() : null);
                    }
                }
                cuh cuh = (cuh) a.a.a(cuh.class);
                if (cuh != null) {
                    cuh.b().f();
                    break;
                }
                break;
        }
        return false;
    }

    public ecq(AjxFootMapPage ajxFootMapPage) {
        super(ajxFootMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        eba.a().a = true;
        ebf.a(((AjxFootMapPage) this.mPage).getMapView());
        this.b = (acg) a.a.a(acg.class);
        if (this.b != null) {
            this.e = this.b.f();
            this.c = this.b.h();
            this.b.a(this.e);
            this.b.b(this.c);
            this.b.a(new String[]{"输入起点", "输入终点"});
        }
    }

    public final void onResume() {
        ebo.a((AbstractBaseMapPage) this.mPage);
        this.a = ((axd) ((Ajx3Page) this.mPage).getContentView().getParent()).getRouteInputUI();
        this.a.a((axe) this);
        if (this.b != null) {
            bdf bdf = (bdf) a.a.a(bdf.class);
            if (bdf != null) {
                bdf.a(this.b.h());
            }
        }
        ((AjxFootMapPage) this.mPage).c(a(c()));
        this.e = this.b != null ? this.b.f() : null;
        this.c = this.b != null ? this.b.h() : null;
        this.d = (ctl) a.a.a(ctl.class);
        if (eba.a().a && a() == 100 && this.d != null) {
            this.d.a("11", new AjxFootMapPresenter$3(this));
        }
        if (this.b != null) {
            this.b.a((acy) new acy() {
                public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                    boolean z = !ecq.this.b.a(poi, ecq.this.e);
                    boolean z2 = !ecq.this.b.a(poi2, ecq.this.c);
                    ecq.this.e = poi;
                    ecq.this.c = poi2;
                    if (z || z2) {
                        if (z) {
                            ecq.this.b.a(ecq.this.e);
                        }
                        if (z2) {
                            ecq.this.b.b(ecq.this.c);
                        }
                        ecq.d(ecq.this).a(ecq.this.a(true));
                    }
                }
            });
            this.b.a(this.e);
            this.b.b(this.c);
        }
        super.onResume();
        if (this.a.o()) {
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (arguments != null) {
                int a2 = eqg.a(arguments.getString("bundleKeyVoiceCmd"));
                if (a2 != -1) {
                    d.a.a(a2, 10000, (String) null);
                }
            }
        }
        eav.a("performance-", "AjxFootMapPage onResume");
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((AjxFootMapPage) this.mPage).setArguments(pageBundle);
    }

    public final void onPause() {
        super.onPause();
        if (this.b != null) {
            this.b.a((acy) null);
        }
    }

    public final void onStart() {
        super.onStart();
        if (this.f) {
            anf.a(1, 2);
            this.f = false;
        }
    }

    public final void onStop() {
        super.onStop();
        if (this.a != null) {
            this.a.a((axe) null);
        }
        if (drl.a().b()) {
            anf.a(0, -1);
            this.f = true;
        }
    }

    public final void onDestroy() {
        b();
        bdf bdf = (bdf) a.a.a(bdf.class);
        if (bdf != null) {
            bdf.a(null);
        }
        super.onDestroy();
    }

    public final int a() {
        if (this.g == -1) {
            PageBundle arguments = ((AjxFootMapPage) this.mPage).getArguments();
            if (arguments == null) {
                this.g = 100;
            } else if (arguments.getBoolean("key_favorites", false)) {
                this.g = 101;
            } else {
                this.g = arguments.getInt("key_source", 100);
            }
        }
        return this.g;
    }

    public final void b() {
        if (this.d != null) {
            this.d.a("11");
        }
    }

    private boolean f() {
        if (this.e == null || this.c == null || this.b == null) {
            return false;
        }
        POI f2 = this.b.f();
        POI h = this.b.h();
        if (!eal.a(f2) || !eal.a(h)) {
            return false;
        }
        if ((this.a == null || !this.a.o()) && bnx.a(this.e, f2) && bnx.a(this.c, h)) {
            return false;
        }
        return true;
    }

    public final boolean c() {
        if (a() == 102 || this.b == null || this.b.f() == null || TextUtils.isEmpty(this.b.f().getName()) || this.b.h() == null || TextUtils.isEmpty(this.b.h().getName())) {
            return false;
        }
        return f();
    }

    public final String a(boolean z) {
        JSONObject jSONObject = new JSONObject();
        POI poi = null;
        POI f2 = this.b != null ? this.b.f() : null;
        if (this.b != null) {
            poi = this.b.h();
        }
        eal.b(f2);
        eal.b(poi);
        try {
            jSONObject.put("is_need_request", z);
            jSONObject.put("start_poi", bnx.b(f2));
            jSONObject.put("end_poi", bnx.b(poi));
            jSONObject.put("share_bike", eas.a());
            jSONObject.put(ConfigerHelper.AOS_URL_KEY, ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY));
            jSONObject.put("root_path", FileUtil.getAppSDCardFileDir());
            String str = "source_common";
            switch (a()) {
                case 100:
                    str = "source_common";
                    break;
                case 101:
                    str = "source_save";
                    break;
                case 102:
                    str = "source_etrip";
                    break;
            }
            jSONObject.put("source_type", str);
            jSONObject.put("timewarn", ebm.f());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    static /* synthetic */ AjxFootMapPage d(ecq ecq) {
        return (AjxFootMapPage) ecq.mPage;
    }
}
