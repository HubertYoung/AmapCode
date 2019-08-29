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
import com.autonavi.minimap.route.ride.dest.page.AjxRideMapPage;
import com.autonavi.minimap.route.ride.dest.presenter.AjxRideMapPresenter$3;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: edm reason: default package */
/* compiled from: AjxRideMapPresenter */
public final class edm extends Ajx3PagePresenter implements axe, bgo {
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

    private static boolean c() {
        ebm.a(2);
        return false;
    }

    private static boolean d() {
        ebm.a(2);
        return false;
    }

    public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        if (iRouteHeaderEvent == null) {
            return false;
        }
        switch (iRouteHeaderEvent) {
            case START_CLICK:
                c();
                break;
            case END_CLICK:
                d();
                break;
            case PREPARE_SWITCH_TAB:
                if (!RouteType.RIDE.equals((RouteType) pageBundle.getObject(IRouteHeaderEvent.PREPARE_SWITCH_TAB.name()))) {
                    bdf bdf = (bdf) a.a.a(bdf.class);
                    if (bdf != null) {
                        bdf.a(null);
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

    public edm(AjxRideMapPage ajxRideMapPage) {
        super(ajxRideMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        eba.a().a = true;
        ebf.a(((Ajx3Page) this.mPage).getMapView());
        this.b = (acg) a.a.a(acg.class);
        if (this.b != null) {
            this.e = this.b.f();
            this.c = this.b.h();
            this.b.a(this.e);
            this.b.b(this.c);
            this.b.a(new String[]{"输入起点", "输入终点"});
        }
    }

    public final void onStart() {
        super.onStart();
        if (this.f) {
            anf.a(1, 1);
            this.f = false;
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
        boolean z = false;
        if (!(a() == 102 || this.b == null || this.b.f() == null || TextUtils.isEmpty(this.b.f().getName()) || this.b.h() == null || TextUtils.isEmpty(this.b.h().getName()) || this.e == null || this.c == null || this.b == null)) {
            POI f2 = this.b.f();
            POI h = this.b.h();
            if (eal.a(f2) && eal.a(h) && ((this.a != null && this.a.o()) || !bnx.a(this.e, f2) || !bnx.a(this.c, h))) {
                z = true;
            }
        }
        AjxRideMapPage ajxRideMapPage = (AjxRideMapPage) this.mPage;
        String a2 = a(z);
        if (ajxRideMapPage.a != null) {
            ajxRideMapPage.a.setRequestData(a2);
        }
        this.e = this.b != null ? this.b.f() : null;
        this.c = this.b != null ? this.b.h() : null;
        this.d = (ctl) a.a.a(ctl.class);
        if (!(!eba.a().a || a() == 102 || this.d == null)) {
            this.d.a("7", new AjxRideMapPresenter$3(this));
        }
        if (this.b != null) {
            this.b.a((acy) new acy() {
                public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                    boolean z = !edm.this.b.a(poi, edm.this.e);
                    boolean z2 = !edm.this.b.a(poi2, edm.this.c);
                    edm.this.e = poi;
                    edm.this.c = poi2;
                    if (z || z2) {
                        if (z) {
                            edm.this.b.a(edm.this.e);
                        }
                        if (z2) {
                            edm.this.b.b(edm.this.c);
                        }
                        AjxRideMapPage d = edm.d(edm.this);
                        String a2 = edm.this.a(true);
                        if (d.a != null) {
                            d.a.requestRideRoute(a2);
                        }
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
                int a3 = eqg.a(arguments.getString("bundleKeyVoiceCmd"));
                if (a3 != -1) {
                    d.a.a(a3, 10000, (String) null);
                }
            }
        }
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((Ajx3Page) this.mPage).setArguments(pageBundle);
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
            PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
            if (arguments != null) {
                this.g = arguments.getInt("key_source", 100);
            } else {
                this.g = 100;
            }
        }
        return this.g;
    }

    public final void b() {
        if (this.d != null) {
            this.d.a("7");
        }
    }

    public final String a(boolean z) {
        boolean z2;
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
            int i = 0;
            if (a() == 102) {
                z2 = false;
            } else {
                z2 = ebm.g();
                if (!z2) {
                    edr.a(0);
                }
            }
            jSONObject.put("ele_support", z2);
            jSONObject.put("ele_remind_anim", edr.c());
            if (a() != 102) {
                i = edr.a();
            }
            jSONObject.put("ride_type", i);
            jSONObject.put("share_bike", eas.a());
            jSONObject.put(ConfigerHelper.AOS_URL_KEY, ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY));
            jSONObject.put("root_path", FileUtil.getAppSDCardFileDir());
            jSONObject.put("source_type", a() == 102 ? "source_etrip" : "source_common");
            jSONObject.put("timewarn", ebm.f());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    static /* synthetic */ AjxRideMapPage d(edm edm) {
        return (AjxRideMapPage) edm.mPage;
    }
}
