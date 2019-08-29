package defpackage;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import com.amap.bundle.datamodel.poi.POIBase;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.run.overlay.RunRecommendOverlayManager;
import com.autonavi.minimap.route.run.page.RunRecommendPage;
import com.autonavi.minimap.route.run.util.RecommendRequestManager;
import com.autonavi.minimap.route.run.util.RecommendRequestManager.a;
import com.autonavi.sdk.location.LocationInstrument;
import java.text.DecimalFormat;

/* renamed from: efo reason: default package */
/* compiled from: RunRecommendPresenter */
public final class efo extends eae<RunRecommendPage> implements a {
    public POI a = new POIBase();
    public RunRecommendOverlayManager b;
    private RecommendRequestManager c;
    private efb d;
    private boolean e;
    private String f;
    private POI g;

    public efo(RunRecommendPage runRecommendPage) {
        super(runRecommendPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        boolean z = false;
        edr.b((String) "runrecommendnew", false);
        this.e = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        this.c = new RecommendRequestManager();
        this.c.b = this;
        this.b = new RunRecommendOverlayManager((RunRecommendPage) this.mPage);
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        this.a.setPoint(latestPosition);
        this.a.setName(((RunRecommendPage) this.mPage).getString(R.string.route_foot_navi_end_from_my_position));
        ((RunRecommendPage) this.mPage).a(((RunRecommendPage) this.mPage).getString(R.string.route_foot_navi_end_from_my_position));
        RunRecommendOverlayManager runRecommendOverlayManager = this.b;
        if (this.d != null) {
            z = true;
        }
        runRecommendOverlayManager.drawableWheelOverlay(latestPosition, z);
        ebf.a(((RunRecommendPage) this.mPage).getMapView());
    }

    public final void onResume() {
        super.onResume();
        ((RunRecommendPage) this.mPage).a();
        RunRecommendPage runRecommendPage = (RunRecommendPage) this.mPage;
        if (runRecommendPage.c != null) {
            runRecommendPage.c.getViewTreeObserver().addOnPreDrawListener(runRecommendPage);
        }
        LocationInstrument.getInstance().addStatusCallback(this.b, this);
        this.b.drawableWheelOverlay(LocationInstrument.getInstance().getLatestPosition(), this.d != null);
        if (this.d != null) {
            a(this.d.a);
        }
    }

    public final void onPause() {
        super.onPause();
        LocationInstrument.getInstance().removeStatusCallback(this.b);
        this.b.clearOverlay();
    }

    public final void onDestroy() {
        super.onDestroy();
        bty mapView = ((RunRecommendPage) this.mPage).getMapView();
        if (mapView != null) {
            ebf.a(mapView, mapView.j(false), mapView.l(false), 0);
        }
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(false, this.e, false, ((RunRecommendPage) this.mPage).getMapManager(), ((RunRecommendPage) this.mPage).getContext());
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (i == 1001 && pageBundle != null && pageBundle.containsKey("result_poi")) {
            this.a = (POI) pageBundle.get("result_poi");
            ((RunRecommendPage) this.mPage).a(this.a.getName());
            a(((RunRecommendPage) this.mPage).h);
        }
    }

    public final void a(String str) {
        if (!NetworkReachability.b()) {
            b();
            ((RunRecommendPage) this.mPage).a(1);
        } else if (this.a != null && this.a.getPoint() != null && this.c != null) {
            if (TextUtils.equals(this.a.getName(), ((RunRecommendPage) this.mPage).getString(R.string.route_foot_navi_end_from_my_position))) {
                this.a.setPoint(LocationInstrument.getInstance().getLatestPosition());
            }
            if (this.g == null || this.f == null || !bnx.a(this.g, this.a) || !TextUtils.equals(str, this.f)) {
                this.d = null;
                double longitude = this.a.getPoint().getLongitude();
                double latitude = this.a.getPoint().getLatitude();
                ((RunRecommendPage) this.mPage).a(3);
                this.c.a(String.valueOf(longitude), String.valueOf(latitude), str);
                this.g = this.a;
                this.f = str;
            }
        }
    }

    public final void a(efb efb) {
        String str;
        ((RunRecommendPage) this.mPage).a(4);
        if (efb == null) {
            b();
            ((RunRecommendPage) this.mPage).a(0);
            return;
        }
        ((RunRecommendPage) this.mPage).a(2);
        this.d = efb;
        if (((RunRecommendPage) this.mPage).isStarted()) {
            a(efb.a);
        }
        RunRecommendPage runRecommendPage = (RunRecommendPage) this.mPage;
        if (efb != null) {
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            StringBuilder sb = new StringBuilder();
            sb.append(runRecommendPage.getString(R.string.route_run_recommend_distance));
            sb.append(decimalFormat.format((double) (((float) efb.c) / 1000.0f)));
            sb.append("km");
            String sb2 = sb.toString();
            SpannableString spannableString = new SpannableString(sb2);
            spannableString.setSpan(new ForegroundColorSpan(runRecommendPage.getResources().getColor(R.color.f_c_6)), 2, sb2.length() - 2, 33);
            runRecommendPage.d.setText(spannableString);
            float f2 = (float) (((double) efb.b) / 60.0d);
            if (f2 >= 60.0f) {
                int i = (int) (f2 / 60.0f);
                int round = Math.round(f2 % 60.0f);
                if (round == 0) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(runRecommendPage.getString(R.string.route_run_recommend_time));
                    sb3.append(i);
                    sb3.append("h");
                    str = sb3.toString();
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(runRecommendPage.getString(R.string.route_run_recommend_time));
                    sb4.append(i);
                    sb4.append("h");
                    sb4.append(round % 60);
                    sb4.append("min");
                    str = sb4.toString();
                }
            } else {
                if (f2 < 1.0f) {
                    f2 = 1.0f;
                }
                int round2 = Math.round(f2);
                if (round2 == 60) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(runRecommendPage.getString(R.string.route_run_recommend_time));
                    sb5.append(1);
                    sb5.append("h");
                    str = sb5.toString();
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(runRecommendPage.getString(R.string.route_run_recommend_time));
                    sb6.append(round2);
                    sb6.append("min");
                    str = sb6.toString();
                }
            }
            runRecommendPage.e.setText(str);
            int i2 = (int) ((((double) efb.c) * 0.001d * 60.0d * 1.036d) + 0.5d);
            if (i2 == 0) {
                i2 = 1;
            }
            if (i2 > 99999) {
                i2 = 99999;
            }
            StringBuilder sb7 = new StringBuilder();
            sb7.append(runRecommendPage.getString(R.string.route_run_recommend_kcal));
            sb7.append(i2);
            sb7.append("kcal，");
            sb7.append(ecm.a(i2));
            runRecommendPage.f.setText(sb7.toString());
        }
    }

    public final void a(int i) {
        b();
        ((RunRecommendPage) this.mPage).a(4);
        if (i == -1 || i == 3 || i == 6) {
            ((RunRecommendPage) this.mPage).a(0);
        } else {
            ((RunRecommendPage) this.mPage).a(1);
        }
    }

    public final GeoPoint[] a() {
        if (this.d == null || this.d.a == null) {
            return null;
        }
        return (GeoPoint[]) this.d.a.clone();
    }

    private void a(GeoPoint[] geoPointArr) {
        ((RunRecommendPage) this.mPage).getSuspendManager().d().f();
        this.b.drawableLineOverlay(geoPointArr);
        if (geoPointArr != null && geoPointArr.length > 0) {
            this.b.showWheelOverlay();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((RunRecommendPage) this.mPage).g == null || !((RunRecommendPage) this.mPage).g.isShown()) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        }
        ((RunRecommendPage) this.mPage).b();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    private void b() {
        this.b.clearOverlay();
        this.d = null;
        this.f = null;
        this.g = null;
    }
}
