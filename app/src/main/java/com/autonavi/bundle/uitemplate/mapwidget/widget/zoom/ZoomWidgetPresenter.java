package com.autonavi.bundle.uitemplate.mapwidget.widget.zoom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.ZoomWidgetLayout.ZoomTouchListener;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.BalloonLayout;
import org.json.JSONException;
import org.json.JSONObject;

public class ZoomWidgetPresenter extends BaseMapWidgetPresenter<ZoomInOutMapWidget> {
    ZoomTouchListener ltl = new ZoomTouchListener() {
        private static final int ZOOM_IN_DELAY = 1028;
        private static final int ZOOM_OUT_DELAY = 1280;
        Handler mTipHandler = new Handler() {
            public void handleMessage(Message message) {
                AnonymousClass2.this.mTipHandler.removeCallbacks(AnonymousClass2.this.tipRunZoomIn);
                AnonymousClass2.this.mTipHandler.removeCallbacks(AnonymousClass2.this.tipRunZoomOut);
                if (message.what == AnonymousClass2.ZOOM_IN_DELAY) {
                    if (ZoomWidgetPresenter.this.mBindWidget != null) {
                        View zoomInTip = ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomInTip();
                        if (zoomInTip != null) {
                            zoomInTip.setVisibility(8);
                        }
                    }
                } else if (message.what == 1280 && ZoomWidgetPresenter.this.mBindWidget != null) {
                    View zoomOutTip = ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomOutTip();
                    if (zoomOutTip != null) {
                        zoomOutTip.setVisibility(8);
                    }
                }
            }
        };
        Runnable tipRunZoomIn = new Runnable() {
            public void run() {
                AnonymousClass2.this.mTipHandler.obtainMessage(AnonymousClass2.ZOOM_IN_DELAY).sendToTarget();
            }
        };
        Runnable tipRunZoomOut = new Runnable() {
            public void run() {
                AnonymousClass2.this.mTipHandler.obtainMessage(1280).sendToTarget();
            }
        };

        public void timeIsComing(View view) {
            Integer num;
            Integer num2;
            ZoomWidgetPresenter.this.mIsTouchEvent = true;
            if (lh.a().orientation != 2) {
                int w = ZoomWidgetPresenter.this.mMapManager.getMapView().w();
                TextView zoomInTipText = ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomInTipText();
                TextView zoomOutTipText = ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomOutTipText();
                if (view.equals(((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomInView())) {
                    if (w < 17) {
                        try {
                            ((Vibrator) ZoomWidgetPresenter.this.mContext.getSystemService("vibrator")).vibrate(50);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                        if (w >= 11) {
                            zoomInTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_street));
                            num2 = Integer.valueOf(17);
                        } else if (w >= 8) {
                            zoomInTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_city));
                            num2 = Integer.valueOf(11);
                        } else if (w >= 4) {
                            zoomInTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_province));
                            num2 = Integer.valueOf(8);
                        } else {
                            zoomInTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_china));
                            num2 = Integer.valueOf(4);
                        }
                        ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomOutTip().setVisibility(4);
                        this.mTipHandler.removeCallbacks(this.tipRunZoomOut);
                        this.mTipHandler.postDelayed(this.tipRunZoomIn, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                        ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomInTip().setVisibility(0);
                        ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomInTip().setTag(num2);
                    }
                } else if (w > 4) {
                    try {
                        ((Vibrator) ZoomWidgetPresenter.this.mContext.getSystemService("vibrator")).vibrate(50);
                    } catch (SecurityException e2) {
                        e2.printStackTrace();
                    }
                    if (w <= 8) {
                        zoomOutTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_china));
                        num = Integer.valueOf(4);
                    } else if (w <= 11) {
                        zoomOutTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_province));
                        num = Integer.valueOf(8);
                    } else if (w <= 17) {
                        zoomOutTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_city));
                        num = Integer.valueOf(11);
                    } else {
                        zoomOutTipText.setText(ZoomWidgetPresenter.this.mContext.getResources().getString(R.string.v4_zoom_tip_street));
                        num = Integer.valueOf(17);
                    }
                    ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomInTip().setVisibility(4);
                    this.mTipHandler.removeCallbacks(this.tipRunZoomIn);
                    this.mTipHandler.postDelayed(this.tipRunZoomOut, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomOutTip().setVisibility(0);
                    ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomOutTip().setTag(num);
                }
            }
        }

        public void touchToZoom(View view) {
            cct cct = (cct) ank.a(cct.class);
            if (cct != null && cct.a(ZoomWidgetPresenter.this.mSuspendManager.d().c())) {
                cdd.n().e(true);
            }
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(ZoomWidgetPresenter.this.mMapManager.getMapView().n());
            int i = ZoomWidgetPresenter.this.mMapManager.getMapView().s() ? 1 : 2;
            int j = ZoomWidgetPresenter.this.mMapManager.getMapView().j(false) + 1;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", j);
                jSONObject.put("from", ZoomWidgetPresenter.this.mMapManager.getMapView().w());
                jSONObject.put("lat", glGeoPoint2GeoPoint.getLatitude());
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, glGeoPoint2GeoPoint.getLongitude());
                jSONObject.put("status", i);
                jSONObject.put(TrafficUtil.KEYWORD, ((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getWidgetProperty().isLoadNewWidgetStyle() ? 1 : 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (view.equals(((ZoomInOutMapWidget) ZoomWidgetPresenter.this.mBindWidget).getZoomInView())) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_REPORT_ZOOMIN, jSONObject);
                ZoomWidgetPresenter.this.doZoomIn();
                return;
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_REPORT_ZOOMOUT, jSONObject);
            ZoomWidgetPresenter.this.doZoomOut();
        }
    };
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public boolean mIsTouchEvent = false;
    /* access modifiers changed from: private */
    public MapManager mMapManager = DoNotUseTool.getMapManager();
    /* access modifiers changed from: private */
    public cde mSuspendManager = DoNotUseTool.getSuspendManager();
    g mZoomButtonStateListener = new g() {
        public void updateZoomButtonState(bty bty) {
            aho.a(new Runnable() {
                public void run() {
                    ZoomWidgetPresenter.this.updateZoomButtonState();
                }
            });
        }

        public void updateZoomViewVisibility() {
            aho.a(new Runnable() {
                public void run() {
                    ZoomWidgetPresenter.this.updateZoomViewVisibility();
                }
            });
        }
    };
    private ZoomMapEventListener mZoomMapEventListener;

    class ZoomMapEventListener extends awc {
        ZoomMapEventListener() {
        }

        public void onMapLevelChange(boolean z) {
            ZoomWidgetPresenter.this.updateZoomButtonState();
        }
    }

    public ZoomWidgetPresenter() {
        cdd.n().a(this.mZoomButtonStateListener);
    }

    public void bindListener() {
        setWidgetEventIndex(((ZoomInOutMapWidget) this.mBindWidget).getZoomInView(), 0);
        setWidgetEventIndex(((ZoomInOutMapWidget) this.mBindWidget).getZoomOutView(), 1);
        setWidgetEventIndex(((ZoomInOutMapWidget) this.mBindWidget).getZoomInTip(), 2);
        setWidgetEventIndex(((ZoomInOutMapWidget) this.mBindWidget).getZoomOutTip(), 3);
        onBindListener(((ZoomInOutMapWidget) this.mBindWidget).getZoomInView(), ((ZoomInOutMapWidget) this.mBindWidget).getZoomInTip(), ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutView(), ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutTip());
    }

    public void initContext(Context context) {
        this.mContext = context;
        ((ZoomInOutMapWidget) this.mBindWidget).getZoomInView().setTouchListener(this.ltl);
        ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutView().setTouchListener(this.ltl);
    }

    public void internalClickListener(View view) {
        int zoomInTipId = ((ZoomInOutMapWidget) this.mBindWidget).getZoomInTipId();
        int zoomInViewId = ((ZoomInOutMapWidget) this.mBindWidget).getZoomInViewId();
        int zoomOutTipId = ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutTipId();
        int zoomOutViewId = ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutViewId();
        if (view != null) {
            int id = view.getId();
            if (id == zoomInTipId || id == zoomInViewId) {
                if (id != zoomInViewId || !this.mIsTouchEvent) {
                    zoomInTip();
                } else {
                    this.mIsTouchEvent = false;
                }
            } else if (id == zoomOutTipId || id == zoomOutViewId) {
                if (id != zoomOutViewId || !this.mIsTouchEvent) {
                    zoomOutTip();
                } else {
                    this.mIsTouchEvent = false;
                }
            }
        }
    }

    private void zoomInTip() {
        View zoomInTip = ((ZoomInOutMapWidget) this.mBindWidget).getZoomInTip();
        View zoomOutTip = ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutTip();
        Integer num = (Integer) zoomInTip.getTag();
        if (num != null && zoomInTip != null && zoomOutTip != null) {
            cct cct = (cct) ank.a(cct.class);
            if (cct != null && cct.a(this.mSuspendManager.d().c())) {
                cdd.n().e(true);
            }
            zoomInTip.setTag(null);
            zoomInTip.setVisibility(8);
            zoomOutTip.setVisibility(8);
            this.mMapManager.getMapView().c((float) num.intValue());
        }
    }

    private void zoomOutTip() {
        View zoomInTip = ((ZoomInOutMapWidget) this.mBindWidget).getZoomInTip();
        View zoomOutTip = ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutTip();
        Integer num = (Integer) zoomOutTip.getTag();
        if (num != null && zoomInTip != null && zoomOutTip != null) {
            cct cct = (cct) ank.a(cct.class);
            if (cct != null && cct.a(this.mSuspendManager.d().c())) {
                cdd.n().e(true);
            }
            zoomOutTip.setTag(null);
            zoomInTip.setVisibility(8);
            zoomOutTip.setVisibility(8);
            this.mMapManager.getMapView().c((float) num.intValue());
        }
    }

    public void updateZoomButtonState() {
        if (isWidgetNotNull() && ((ZoomInOutMapWidget) this.mBindWidget).getVisibility() == 0) {
            bty mapView = this.mMapManager != null ? this.mMapManager.getMapView() : null;
            if (mapView != null) {
                int w = mapView.w();
                boolean z = false;
                boolean z2 = w < mapView.l();
                ((ZoomInOutMapWidget) this.mBindWidget).getZoomInView().setEnabled(z2);
                float f = 0.4f;
                ((ZoomInOutMapWidget) this.mBindWidget).getZoomInView().setAlpha(z2 ? 1.0f : 0.4f);
                if (w > mapView.m()) {
                    z = true;
                }
                ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutView().setEnabled(z);
                ZoomWidgetLayout zoomOutView = ((ZoomInOutMapWidget) this.mBindWidget).getZoomOutView();
                if (z) {
                    f = 1.0f;
                }
                zoomOutView.setAlpha(f);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateZoomViewVisibility() {
        if (isWidgetNotNull()) {
            ((ZoomInOutMapWidget) this.mBindWidget).setVisibility(bim.aa().k((String) UploadConstants.STATUS_TASK_BY_PUSH) ? 0 : 8);
        }
    }

    /* access modifiers changed from: private */
    public void doZoomOut() {
        if (this.mSuspendManager.b() == null || this.mSuspendManager.b().isSuspendActionEnable(2)) {
            this.mMapManager.getMapView().y();
            IZoomWidgetEventDelegate iZoomWidgetEventDelegate = (IZoomWidgetEventDelegate) getEventDelegate();
            if (iZoomWidgetEventDelegate != null) {
                iZoomWidgetEventDelegate.onClickToZoomOut();
            }
        }
    }

    /* access modifiers changed from: private */
    public void doZoomIn() {
        if (this.mSuspendManager.b() == null || this.mSuspendManager.b().isSuspendActionEnable(1)) {
            this.mMapManager.getMapView().x();
            IZoomWidgetEventDelegate iZoomWidgetEventDelegate = (IZoomWidgetEventDelegate) getEventDelegate();
            if (iZoomWidgetEventDelegate != null) {
                iZoomWidgetEventDelegate.onClickToZoomIn();
            }
        }
    }

    public void pageCreated(bid bid) {
        if (bid != null && (bid instanceof AbstractBaseMapPage)) {
            AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) bid;
            if (isContainerZoomInOutWidget(abstractBaseMapPage)) {
                this.mZoomMapEventListener = new ZoomMapEventListener();
                abstractBaseMapPage.addMainMapEventListener(this.mZoomMapEventListener);
            }
        }
    }

    private boolean isContainerZoomInOutWidget(AbstractBaseMapPage abstractBaseMapPage) {
        IWidgetProperty[] customPageWidgets = abstractBaseMapPage == null ? null : abstractBaseMapPage.customPageWidgets();
        if (customPageWidgets != null && customPageWidgets.length > 0) {
            for (IWidgetProperty widgetType : customPageWidgets) {
                if (WidgetType.ZOOM_IN_OUT.equals(widgetType.getWidgetType())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void pageDestroy(bid bid) {
        if (bid != null && (bid instanceof AbstractBaseMapPage) && this.mZoomMapEventListener != null) {
            AbstractBaseMapPage abstractBaseMapPage = (AbstractBaseMapPage) bid;
            if (isContainerZoomInOutWidget(abstractBaseMapPage)) {
                abstractBaseMapPage.removeMainMapEventListener(this.mZoomMapEventListener);
            }
        }
    }
}
