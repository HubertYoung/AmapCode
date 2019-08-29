package com.autonavi.map.search.tip;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
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
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView.OnRequestCallbackListener;
import com.autonavi.minimap.search.inter.ISearchManager;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;

@KeepName
public class GpsTipViewForPoiDetailDelegate extends AbstractGpsTipView implements OnClickListener, cbs {
    private static final int GPS_LOCATE_WARN_METERS = 3000;
    private static final String TAG = "GpsTipView";
    private View btnArround;
    private View btnReport;
    private View btnRouteNew;
    private View btnTaxi;
    /* access modifiers changed from: private */
    public cdx gpsLayer;
    private View mCutOffLine;
    private int mFromID = 0;
    private a mGpsLocateRequestThread;
    private long mLastShowSequence = 0;
    private bid mPageContext;
    private View mShadow;
    private Boolean mShowTipClose = null;
    private View mTipClockBtn;
    private OnClickListener mTipCloseClickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (GpsTipViewForPoiDetailDelegate.this.mTipViewListener != null) {
                GpsTipViewForPoiDetailDelegate.this.mTipViewListener;
            }
        }
    };
    private RelativeLayout mTipDragLayout;
    /* access modifiers changed from: private */
    public defpackage.cbs.a mTipViewListener = null;
    /* access modifiers changed from: private */
    public POI poi;
    private bcg poiDetailHelper;
    private View rootView;
    private TextView tvFloorLabel;
    private TextView tvMainTitle;
    private TextView tvViceTitle;
    private TextView tvWarnTitle;

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

        /* synthetic */ a(GpsTipViewForPoiDetailDelegate gpsTipViewForPoiDetailDelegate, byte b2) {
            this();
        }

        public final void run() {
            AMapLog.d(GpsTipViewForPoiDetailDelegate.TAG, "request poi.");
            if (GpsTipViewForPoiDetailDelegate.this.gpsLayer != null && !this.a) {
                GpsTipViewForPoiDetailDelegate.this.gpsLayer;
                cdy b2 = cdx.b();
                int i = b2.a;
                final boolean z = false;
                if (!agq.b(AMapAppGlobal.getApplication()) && !agq.a((Context) AMapAppGlobal.getApplication()) && i >= 3000) {
                    z = true;
                }
                GpsTipViewForPoiDetailDelegate.this.post(new Runnable() {
                    public final void run() {
                        GpsTipViewForPoiDetailDelegate.this.setWarnTitle(z);
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
                    GpsTipViewForPoiDetailDelegate.this.post(new Runnable() {
                        public final void run() {
                            GpsTipViewForPoiDetailDelegate.this.setPoi(createPOI);
                        }
                    });
                    this.c = ReverseGeocodeManager.getReverseGeocodeResult(a2, 5, new GpsTipViewForPoiDetailDelegate$GpsLocateRequestThread$3(this));
                }
                if (!this.a) {
                    GpsTipViewForPoiDetailDelegate.this.post(new Runnable() {
                        public final void run() {
                            String str;
                            GpsTipViewForPoiDetailDelegate gpsTipViewForPoiDetailDelegate = GpsTipViewForPoiDetailDelegate.this;
                            if (a.this.d.isEmpty()) {
                                str = "";
                            } else {
                                str = ((POI) a.this.d.get(0)).getName();
                            }
                            gpsTipViewForPoiDetailDelegate.setViceTitle(str);
                        }
                    });
                }
            }
        }
    }

    public void adjustMargin() {
    }

    public void refreshByScreenState(boolean z) {
    }

    public GpsTipViewForPoiDetailDelegate(bid bid, cdx cdx) {
        super(bid.getContext());
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
        this.rootView = layoutInflater.inflate(R.layout.gpstip_layout_cq, this);
        this.mTipDragLayout = (RelativeLayout) this.rootView.findViewById(R.id.tip_drag_layout);
        this.tvMainTitle = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_name);
        this.tvViceTitle = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_addr);
        this.tvWarnTitle = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_warn);
        this.tvFloorLabel = (TextView) this.rootView.findViewById(R.id.basemap_gpstip_floor);
        this.mCutOffLine = this.rootView.findViewById(R.id.basemap_gpstip_cutoff_line);
        this.btnReport = this.rootView.findViewById(R.id.basemap_gpstip_report);
        this.btnArround = this.rootView.findViewById(R.id.basemap_gpstip_around);
        this.btnTaxi = this.rootView.findViewById(R.id.basemap_gpstip_taxi);
        this.btnRouteNew = this.rootView.findViewById(R.id.route_btn);
        this.btnArround.setOnClickListener(this);
        this.btnTaxi.setOnClickListener(this);
        this.btnRouteNew.setOnClickListener(this);
        this.btnReport.setOnClickListener(this);
        this.mShadow = findViewById(R.id.view_shadow);
        this.mTipClockBtn = findViewById(R.id.tip_close);
        this.mTipClockBtn.setOnClickListener(this.mTipCloseClickListener);
        setShowTipClose(false);
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
                this.mCutOffLine.setVisibility(8);
                return;
            }
            this.tvFloorLabel.setText(str);
            this.tvFloorLabel.setVisibility(0);
            this.mCutOffLine.setVisibility(0);
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
        } else if (view == this.btnArround) {
            LogManager.actionLogV2("P00001", LogConstant.MAIN_LOCATION_SOU_ZHOU_BIAN);
            logBtnClick();
            if (this.poiDetailHelper != null) {
                this.poiDetailHelper.a(this.mPageContext, this.poi);
            }
        } else if (view == this.btnTaxi) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_route_type", RouteType.TAXI);
            bax bax = (bax) defpackage.esb.a.a.a(bax.class);
            if (bax != null) {
                bax.b(pageBundle);
            }
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

    private void toDetailView() {
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
        this.btnArround.setVisibility(0);
        this.btnTaxi.setVisibility(8);
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

    /* JADX WARNING: Removed duplicated region for block: B:22:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean setFloorLableWhileIndoor() {
        /*
            r11 = this;
            cdx r0 = r11.gpsLayer
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            cdy r0 = defpackage.cdx.b()
            int r2 = r0.c
            r3 = 2
            if (r2 != r3) goto L_0x009a
            android.os.Bundle r0 = r0.f
            if (r0 == 0) goto L_0x009a
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
            if (r0 == 0) goto L_0x009a
            boolean r8 = android.text.TextUtils.isEmpty(r2)
            if (r8 != 0) goto L_0x009a
            java.lang.String r8 = r0.e
            boolean r8 = r2.equals(r8)
            if (r8 == 0) goto L_0x009a
            boolean r8 = android.text.TextUtils.isEmpty(r3)
            r9 = 0
            if (r8 != 0) goto L_0x0067
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ Throwable -> 0x005f }
            cde r8 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getSuspendManager()     // Catch:{ Throwable -> 0x005f }
            cdn r8 = r8.c()     // Catch:{ Throwable -> 0x005f }
            cds r3 = r8.a(r3)     // Catch:{ Throwable -> 0x005f }
            goto L_0x0068
        L_0x005f:
            r3 = move-exception
            java.lang.String r8 = "GpsTipView"
            java.lang.String r10 = "parse floor error"
            com.amap.bundle.blutils.log.DebugLog.e(r8, r10, r3)
        L_0x0067:
            r3 = r9
        L_0x0068:
            java.lang.String r0 = r0.a
            if (r3 == 0) goto L_0x006e
            java.lang.String r9 = r3.b
        L_0x006e:
            r11.setFloorLabel(r9)
            r11.setViceTitle(r0)
            com.autonavi.common.model.GeoPoint r3 = new com.autonavi.common.model.GeoPoint
            r3.<init>(r4, r6)
            com.autonavi.common.model.POI r3 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r0, r3)
            r3.setId(r2)
            r3.setName(r0)
            com.autonavi.map.search.tip.GpsTipViewForPoiDetailDelegate$1 r0 = new com.autonavi.map.search.tip.GpsTipViewForPoiDetailDelegate$1
            r0.<init>(r3)
            r11.post(r0)
            android.widget.TextView r0 = r11.tvFloorLabel
            if (r0 == 0) goto L_0x0099
            android.widget.TextView r0 = r11.tvFloorLabel
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x0099
            r0 = 1
            return r0
        L_0x0099:
            return r1
        L_0x009a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.tip.GpsTipViewForPoiDetailDelegate.setFloorLableWhileIndoor():boolean");
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View view, int i) {
        if (i == 0) {
            onViewAttached();
        } else {
            onViewDetached();
        }
        super.onVisibilityChanged(view, i);
    }

    public void setOnRequestCallbackListener(OnRequestCallbackListener onRequestCallbackListener) {
        super.setOnRequestCallbackListener(onRequestCallbackListener);
        this.mLastShowSequence = 0;
    }

    private void onViewAttached() {
        long showSequence = this.mOnRequestCallbackListener != null ? this.mOnRequestCallbackListener.getShowSequence() : 0;
        if (showSequence == 0 || this.mLastShowSequence == 0 || showSequence > this.mLastShowSequence) {
            this.mLastShowSequence = showSequence;
            if (!setFloorLableWhileIndoor()) {
                startRequest();
            }
        }
    }

    private void onViewDetached() {
        long finishSequence = this.mOnRequestCallbackListener != null ? this.mOnRequestCallbackListener.getFinishSequence() : 0;
        if (finishSequence == 0 || this.mLastShowSequence == 0 || finishSequence > this.mLastShowSequence) {
            setFloorLabel(null);
            cancelRequest();
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        onViewAttached();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        onViewDetached();
        super.onDetachedFromWindow();
    }

    public void setShowTipClose(boolean z) {
        if (this.mShowTipClose == null || this.mShowTipClose.booleanValue() != z) {
            this.mShowTipClose = Boolean.valueOf(z);
            refreshTipClose();
        }
    }

    public boolean getShowTipClose() {
        return this.mShowTipClose.booleanValue();
    }

    private void refreshTipClose() {
        int i = 8;
        if (this.mTipClockBtn != null) {
            this.mTipClockBtn.setVisibility(this.mShowTipClose.booleanValue() ? 0 : 8);
        }
        if (this.mTipDragLayout != null) {
            this.mTipDragLayout.setBackgroundResource(this.mShowTipClose.booleanValue() ? R.drawable.tip_bottom_sheet_bg : R.drawable.tip_view_bg);
        }
        View view = this.mShadow;
        if (!this.mShowTipClose.booleanValue()) {
            i = 0;
        }
        view.setVisibility(i);
    }

    public void setTipViewListener(defpackage.cbs.a aVar) {
        this.mTipViewListener = aVar;
    }

    public defpackage.cbs.a getTipViewListener() {
        return this.mTipViewListener;
    }
}
