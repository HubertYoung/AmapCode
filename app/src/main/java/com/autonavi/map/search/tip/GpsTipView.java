package com.autonavi.map.search.tip;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter.IGPSTipView;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.mappage.TipsView.AbstractGpsTipView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.search.inter.ISearchManager;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class GpsTipView extends AbstractGpsTipView implements OnClickListener, IGPSTipView {
    private static final int GPS_LOCATE_WARN_METERS = 3000;
    private static final String TAG = "GpsTipView";
    private View btnAround;
    private View btnReport;
    private View btnRouteNew;
    private View btnTaxi;
    /* access modifiers changed from: private */
    public cdx gpsLayer;
    private int mFromID = 0;
    private a mGpsLocateRequestThread;
    private bid mPageContext;
    /* access modifiers changed from: private */
    public POI poi;
    private bcg poiDetailHelper;
    private View rootView;
    private TextView tvFloorLabel;
    private TextView tvMainTitle;
    private TextView tvViceTitle;
    private TextView tvWarnTitle;
    private View viewTips;

    class a extends Thread {
        boolean a;
        /* access modifiers changed from: private */
        public com.autonavi.common.Callback.a c;
        /* access modifiers changed from: private */
        public List<POI> d;
        /* access modifiers changed from: private */
        public String e;

        private a() {
            this.d = new ArrayList();
            this.e = null;
            this.a = false;
        }

        /* synthetic */ a(GpsTipView gpsTipView, byte b2) {
            this();
        }

        public final void run() {
            if (GpsTipView.this.gpsLayer != null && !this.a) {
                GpsTipView.this.gpsLayer;
                cdy b2 = cdx.b();
                int i = b2.a;
                final boolean z = false;
                if (!agq.b(AMapAppGlobal.getApplication()) && !agq.a((Context) AMapAppGlobal.getApplication()) && i >= 3000) {
                    z = true;
                }
                GpsTipView.this.post(new Runnable() {
                    public final void run() {
                        GpsTipView.this.setWarnTitle(z);
                    }
                });
                GeoPoint a2 = b2.a();
                if (a2 != null && !this.a) {
                    this.d.clear();
                    if (this.c != null) {
                        this.c.cancel();
                    }
                    this.e = AMapAppGlobal.getApplication().getString(R.string.my_location);
                    final POI createPOI = POIFactory.createPOI(this.e, a2);
                    GpsTipView.this.post(new Runnable() {
                        public final void run() {
                            GpsTipView.this.setPoi(createPOI);
                        }
                    });
                    this.c = ReverseGeocodeManager.getReverseGeocodeResult(a2, 5, new GpsTipView$GpsLocateRequestThread$3(this));
                }
                if (!this.a) {
                    GpsTipView.this.post(new Runnable() {
                        public final void run() {
                            String str;
                            GpsTipView gpsTipView = GpsTipView.this;
                            if (a.this.d.isEmpty()) {
                                str = "";
                            } else {
                                str = ((POI) a.this.d.get(0)).getName();
                            }
                            gpsTipView.setViceTitle(str);
                        }
                    });
                }
            }
        }
    }

    public View getView() {
        return this;
    }

    public GpsTipView(bid bid, cdx cdx) {
        super(DoNotUseTool.getContext());
        this.mPageContext = bid;
        this.gpsLayer = cdx;
        addViews();
    }

    public void setFromPageID(int i) {
        this.mFromID = i;
    }

    private void addViews() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        if (((ISearchManager) ank.a(ISearchManager.class)) != null) {
            this.poiDetailHelper = new ekz();
        }
        this.rootView = layoutInflater.inflate(R.layout.basemap_gpstip_layout, this);
        this.viewTips = this.rootView.findViewById(R.id.basemap_gpstip);
        this.tvMainTitle = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_name);
        this.tvViceTitle = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_addr);
        this.tvWarnTitle = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_warn);
        this.tvFloorLabel = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_floor);
        this.btnReport = this.rootView.findViewById(R.id.basemap_gpstip_report);
        this.btnAround = this.rootView.findViewById(R.id.basemap_gpstip_around);
        this.btnTaxi = this.rootView.findViewById(R.id.basemap_gpstip_taxi);
        this.btnRouteNew = this.rootView.findViewById(R.id.route_btn);
        this.btnAround.setOnClickListener(this);
        this.btnTaxi.setOnClickListener(this);
        this.btnRouteNew.setOnClickListener(this);
        this.btnReport.setOnClickListener(this);
    }

    public POI getPoi() {
        return this.poi;
    }

    public void setPoi(POI poi2) {
        this.poi = poi2.as(GpsPOI.class);
        setRoute();
    }

    public boolean isTaxiOpen() {
        String a2 = lo.a().a((String) DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
        return !TextUtils.isEmpty(a2) && a2.contains("\"cab\"");
    }

    private void setRoute() {
        if ((this.poi == null || this.poi.getPoint().getAdCode() == 0) ? false : isTaxiOpen()) {
            this.btnTaxi.setVisibility(0);
        } else {
            this.btnTaxi.setVisibility(8);
        }
    }

    public void setWarnTitle(boolean z) {
        if (!z) {
            if (this.tvWarnTitle.getVisibility() != 8) {
                this.tvWarnTitle.setVisibility(8);
            }
        } else if (this.tvWarnTitle.getVisibility() != 0) {
            this.tvWarnTitle.setVisibility(0);
        }
    }

    private void setFloorLabel(String str) {
        if (this.tvFloorLabel != null) {
            if (TextUtils.isEmpty(str)) {
                this.tvFloorLabel.setVisibility(8);
            } else {
                this.tvFloorLabel.setText(str);
                this.tvFloorLabel.setVisibility(0);
            }
        }
    }

    public void setViceTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            this.tvViceTitle.setVisibility(8);
            return;
        }
        if (this.tvFloorLabel.getVisibility() != 0) {
            String string = AMapAppGlobal.getApplication().getResources().getString(R.string.basemap_gpstip_at);
            String string2 = AMapAppGlobal.getApplication().getResources().getString(R.string.basemap_gpstip_near);
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(str);
            sb.append(string2);
            str = sb.toString();
        }
        this.tvViceTitle.setText(str);
        this.tvViceTitle.setVisibility(0);
    }

    private void logBtnClick() {
        try {
            JSONObject jSONObject = new JSONObject();
            if (this.poi != null && !TextUtils.isEmpty(this.poi.getId())) {
                jSONObject.put("PoiId", this.poi.getId());
            }
            jSONObject.put("from_id", this.mFromID);
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
    }

    public void onClick(View view) {
        if (view == this.btnReport) {
            toReportWrong();
        } else if (view == this.btnAround) {
            LogManager.actionLogV2("P00001", LogConstant.MAIN_LOCATION_SOU_ZHOU_BIAN);
            logBtnClick();
            if (this.poiDetailHelper != null) {
                this.poiDetailHelper.a(this.mPageContext, this.poi);
            }
        } else if (view == this.btnTaxi) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_route_type", RouteType.TAXI);
            this.mPageContext.startPage((String) "amap.extra.route.route", pageBundle);
            LogManager.actionLogV2("P00001", LogConstant.MAIN_TAXI_CLICK);
        } else {
            if (view == this.btnRouteNew) {
                logBtnClick();
                if (this.poiDetailHelper != null) {
                    this.poiDetailHelper.b(this.mPageContext, this.poi);
                }
            }
        }
    }

    private void toReportWrong() {
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            iErrorReportStarter.startLocationError(this.poi);
        }
    }

    private void gotoDetailView() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", this.poi);
        pageBundle.putBoolean("isGeoCode", false);
        pageBundle.putBoolean("isGPSPoint", true);
        pageBundle.putBoolean("isMarkPoint", false);
        if (this.mFromID == 10001) {
            pageBundle.putString("fromSource", "mainmap");
        } else if (this.mFromID == 11101) {
            pageBundle.putString("fromSource", "poitip");
        } else if (this.mFromID == 12409) {
            pageBundle.putString("fromSource", "poilist");
        } else if (this.mFromID == 11107) {
            pageBundle.putString("fromSource", "singlepoi");
        } else if (this.mFromID == 11109) {
            pageBundle.putString("fromSource", "buslinemap");
        } else {
            pageBundle.putString("fromSource", "default");
        }
        pageBundle.putBoolean(Constant.KEY_IS_BACK, true);
        this.mPageContext.startPage((String) "amap.search.action.poidetail", pageBundle);
        try {
            JSONObject jSONObject = new JSONObject();
            if (this.poi != null && TextUtils.isEmpty(this.poi.getId())) {
                jSONObject.put("PoiId", this.poi.getId());
            }
            jSONObject.put("from_id", this.mFromID);
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
    }

    public void reset() {
        this.poi = null;
        this.mFromID = 0;
        if (this.tvFloorLabel == null || this.tvFloorLabel.getVisibility() != 0) {
            setViceTitle("");
        }
        this.tvMainTitle.setText(R.string.my_location);
        this.btnAround.setVisibility(0);
        this.btnTaxi.setVisibility(8);
        if (!setFloorLabelWhileIndoor()) {
            startRequest();
        }
    }

    private void startRequest() {
        this.mGpsLocateRequestThread = new a(this, 0);
        this.mGpsLocateRequestThread.start();
    }

    private void cancelRequest() {
        if (this.mGpsLocateRequestThread != null) {
            this.mGpsLocateRequestThread.a = true;
            this.mGpsLocateRequestThread = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0064  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean setFloorLabelWhileIndoor() {
        /*
            r10 = this;
            cdx r0 = r10.gpsLayer
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            cdy r0 = defpackage.cdx.b()
            int r2 = r0.c
            r3 = 2
            if (r2 != r3) goto L_0x0092
            android.os.Bundle r0 = r0.f
            if (r0 == 0) goto L_0x0092
            java.lang.String r2 = "poiid"
            java.lang.String r2 = r0.getString(r2)
            java.lang.String r3 = "floor"
            java.lang.String r3 = r0.getString(r3)
            java.lang.String r4 = "idrGcjLon"
            double r4 = r0.getDouble(r4)
            java.lang.String r6 = "idrGcjLat"
            double r6 = r0.getDouble(r6)
            cde r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getSuspendManager()
            cdn r0 = r0.c()
            ami r0 = r0.a()
            if (r0 == 0) goto L_0x0092
            boolean r8 = android.text.TextUtils.isEmpty(r2)
            if (r8 != 0) goto L_0x0092
            java.lang.String r8 = r0.e
            boolean r8 = r2.equals(r8)
            if (r8 == 0) goto L_0x0092
            boolean r8 = android.text.TextUtils.isEmpty(r3)
            r9 = 0
            if (r8 != 0) goto L_0x005f
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ Throwable -> 0x005f }
            cde r8 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getSuspendManager()     // Catch:{ Throwable -> 0x005f }
            cdn r8 = r8.c()     // Catch:{ Throwable -> 0x005f }
            cds r3 = r8.a(r3)     // Catch:{ Throwable -> 0x005f }
            goto L_0x0060
        L_0x005f:
            r3 = r9
        L_0x0060:
            java.lang.String r0 = r0.a
            if (r3 == 0) goto L_0x0066
            java.lang.String r9 = r3.b
        L_0x0066:
            r10.setFloorLabel(r9)
            r10.setViceTitle(r0)
            com.autonavi.common.model.GeoPoint r3 = new com.autonavi.common.model.GeoPoint
            r3.<init>(r4, r6)
            com.autonavi.common.model.POI r3 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r0, r3)
            r3.setId(r2)
            r3.setName(r0)
            com.autonavi.map.search.tip.GpsTipView$1 r0 = new com.autonavi.map.search.tip.GpsTipView$1
            r0.<init>(r3)
            r10.post(r0)
            android.widget.TextView r0 = r10.tvFloorLabel
            if (r0 == 0) goto L_0x0091
            android.widget.TextView r0 = r10.tvFloorLabel
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x0091
            r0 = 1
            return r0
        L_0x0091:
            return r1
        L_0x0092:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.tip.GpsTipView.setFloorLabelWhileIndoor():boolean");
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        if (i != 0) {
            setFloorLabel(null);
            cancelRequest();
        } else if (!setFloorLabelWhileIndoor()) {
            startRequest();
        }
        super.onVisibilityChanged(view, i);
    }
}
