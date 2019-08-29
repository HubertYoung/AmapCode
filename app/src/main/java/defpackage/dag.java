package defpackage;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.amap.api.service.IndoorLocationProvider;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.bundle.maphome.api.IMapEventListener;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dag reason: default package */
/* compiled from: GpsMapControllerImpl */
public class dag implements ceg {
    private static boolean f = false;
    private boolean a = false;
    private boolean b = true;
    private ProgressDlg c;
    private boolean d = false;
    private volatile a e;
    /* access modifiers changed from: private */
    public cdc g;
    /* access modifiers changed from: private */
    public bty h;
    private cdv i;
    private defpackage.ceg.a j;

    /* renamed from: dag$a */
    /* compiled from: GpsMapControllerImpl */
    class a extends defpackage.ahl.a<Void> {
        public final void onError(Throwable th) {
        }

        public final /* bridge */ /* synthetic */ void onFinished(Object obj) {
        }

        private a() {
        }

        /* synthetic */ a(dag dag, byte b) {
            this();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Void doBackground() throws Exception {
            Thread.sleep(500);
            synchronized (this) {
                if (isStopped()) {
                    return null;
                }
                dag.this.g.b().onMotionFinished(dag.this.h.j(), 0);
                return null;
            }
        }
    }

    public final void a(cdc cdc, cdv cdv) {
        this.g = cdc;
        this.h = cdc.b().getMapView();
        this.i = cdv;
    }

    private cdx h() {
        return this.g.b().getOverlayManager().getGpsLayer();
    }

    public final synchronized void a(boolean z) {
        f = z;
    }

    public final void a() {
        cdv cdv = this.i;
        if (cdv != null && cdv.i() != 2) {
            h();
            cdx.b();
            int h2 = cdv.h();
            if (h2 == 4) {
                this.h.i(15);
                h().a(0);
            } else if (h2 == 7) {
                cdd.n().e(true);
                h().a(0);
                if (bim.aa().k((String) "201")) {
                    this.h.e(true);
                } else {
                    this.h.e(false);
                }
            } else if (h2 == 5) {
                j();
            }
            h().a(cdv.f());
            i();
        }
    }

    private void i() {
        if (this.e != null) {
            this.e.cancel();
        }
        this.e = new a(this, 0);
        ahl.b(this.e);
    }

    private void j() {
        cdd.n().e(true);
        h().a(1);
    }

    public final void b() {
        cdv cdv = this.i;
        if (cdv != null && cdv.i() != 2) {
            cdv.a(6);
            j();
            i();
        }
    }

    public final void c() {
        cdv cdv = this.i;
        if (cdv != null && cdv.b()) {
            cdv.a(2);
            h().a(0);
        }
    }

    public final void d() {
        cdv cdv = this.i;
        if (cdv != null) {
            if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                cdv.a(1);
            }
            cdx h2 = h();
            h2.b = 0;
            h2.a();
            h().a(false);
            this.h.i(0);
        }
    }

    public final void a(int i2) {
        cdv cdv = this.i;
        if (cdv != null) {
            cdv.a(i2);
        }
    }

    public final void e() {
        cdv cdv = this.i;
        if (cdv != null) {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            GeoPoint offsetedPoint = LocationInstrument.getOffsetedPoint(latestLocation);
            if (latestLocation == null || offsetedPoint == null) {
                cdv.a(false);
                cdv.a(0);
                return;
            }
            f();
        }
    }

    public final void f() {
        cdv cdv = this.i;
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        if (latestLocation != null) {
            GeoPoint offsetedPoint = LocationInstrument.getOffsetedPoint(latestLocation);
            if (offsetedPoint != null) {
                if (cdv != null && !cdv.c()) {
                    cdv.a(true);
                }
                if (latestLocation.getProvider().equals(WidgetType.GPS)) {
                    cdd.n().e(false);
                    if (this.g.c() != null) {
                        this.g.c().a((String) "");
                    }
                } else if (latestLocation.getProvider().equals(IndoorLocationProvider.NAME)) {
                    ami a2 = this.g.c().a();
                    if (a2 != null) {
                        Bundle extras = latestLocation.getExtras();
                        if (extras != null) {
                            String string = extras.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                            String string2 = extras.getString("floor");
                            this.g.c().d();
                            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                                if (!string.equals(a2.e)) {
                                    this.g.c().a((String) "");
                                } else {
                                    this.g.c().a(string2);
                                }
                            }
                        }
                    }
                } else {
                    ank.a(IMapEventListener.class);
                    cdd.n().e(false);
                    if (this.g.c() != null) {
                        this.g.c().a((String) "");
                    }
                }
                if (cdv != null && !cdv.a() && !cdv.b()) {
                    cdv.d();
                }
                if (this.j != null) {
                    this.j.a(offsetedPoint);
                }
                if (this.b && f) {
                    this.h.w();
                    k();
                    if (this.h.w() < 16) {
                        this.h.c(16.0f);
                        cdd.n().g();
                    } else if (this.h.w() > 17) {
                        this.h.c(17.0f);
                        cdd.n().g();
                    }
                    this.h.i(15);
                    if (cdv != null) {
                        cdv.a(2);
                    }
                    if (this.j != null) {
                        this.j.a();
                    }
                    this.b = false;
                }
                if (!this.a) {
                    this.a = true;
                    IMapEventListener iMapEventListener = (IMapEventListener) ank.a(IMapEventListener.class);
                    if (iMapEventListener != null) {
                        iMapEventListener.a();
                    }
                }
                if (this.d) {
                    this.d = false;
                    if (this.c != null && this.c.isShowing()) {
                        this.c.dismiss();
                    }
                    k();
                }
            }
        }
    }

    public final void b(boolean z) {
        this.b = z;
    }

    public final boolean g() {
        return this.b;
    }

    public final void c(boolean z) {
        cdx h2 = h();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("enable", z ? 1 : 0);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        h2.a.sendCommand(102206, jSONObject.toString());
    }

    public final void a(defpackage.ceg.a aVar) {
        this.j = aVar;
    }

    private void k() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            if (!(this.h.p() == latestPosition.x && this.h.q() == latestPosition.y)) {
                this.h.a((GLGeoPoint) latestPosition);
            }
        }
    }
}
