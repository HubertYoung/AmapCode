package com.autonavi.miniapp.plugin.map;

import android.content.Context;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.gmap.AMapTextureSurface;
import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.glinterface.MapEngineInitParam;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.suspend.refactor.compass.UICompassWidget;
import com.autonavi.map.suspend.refactor.gps.GpsOverlay;
import com.autonavi.miniapp.plugin.map.indoor.MiniAppFloorManager;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppLineOverlay;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlay;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlayItem;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPolygonOverlayHolder;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppRasterOverlay;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureCacheManager;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureIdManager;
import com.autonavi.miniapp.plugin.util.MiniAppGpsOverlayUpdater;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayHolderImpl;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdapterTextureMapView extends FrameLayout {
    public static final int DEFAULT_CENTER_X_BOTTOM_DP = 60;
    public static final int DEFAULT_CENTER_Y_BOTTOM_DP = 30;
    public static final float LOGO_HEIGHT_DP = 20.0f;
    public static final float LOGO_WIDTH_DP = 80.0f;
    public static final int MAP_TYPE_BUS = 4;
    public static final int MAP_TYPE_CAR_NAVI = 3;
    public static final int MAP_TYPE_DEFAULT = 0;
    public static final int MAP_TYPE_NIGHT = 2;
    public static final int MAP_TYPE_SATELLITE = 1;
    public static final float SCALE_LOGO_SPACE_DP = 10.0f;
    private static final String TAG = "AdapterTextureMapView";
    private cdp floorWidgetChangedListener = new cdp() {
        public void onFloorWidgetVisibilityChanged(ami ami, boolean z, int i) {
            StringBuilder sb = new StringBuilder("onFloorWidgetVisibilityChanged, indoorBuilding: ");
            sb.append(ami);
            sb.append(", isIndoor: ");
            sb.append(z);
            sb.append(", floorIndex: ");
            sb.append(i);
            AMapLog.debug("infoservice.miniapp", AdapterTextureMapView.TAG, sb.toString());
            AdapterTextureMapView.this.notifyStateChange(true);
        }

        public void onFloorChanged(int i, int i2) {
            StringBuilder sb = new StringBuilder("onFloorChanged, oldValue: ");
            sb.append(i);
            sb.append(", newValue: ");
            sb.append(i2);
            AMapLog.debug("infoservice.miniapp", AdapterTextureMapView.TAG, sb.toString());
            AdapterTextureMapView.this.notifyStateChange(false);
        }
    };
    private akq mAMapController;
    private AMapTextureSurface mAMapSurface;
    private UICompassWidget mCompassView;
    private FrameLayout mControllerContainer;
    /* access modifiers changed from: private */
    public View mCoverView;
    /* access modifiers changed from: private */
    public AtomicBoolean mCoverViewAdded = new AtomicBoolean();
    private FrameLayout mFloorContainerView;
    private GpsOverlay mGpsOverlay;
    private MiniAppLineOverlay mLineOverlay;
    private int mLogoHeight;
    private ImageView mLogoView;
    private int mLogoWidth;
    private MapManager mMapManager;
    /* access modifiers changed from: private */
    public bty mMapView;
    private MiniAppFloorManager mMiniAppFloorManager;
    private OnFloorChangeListener mOnFloorChangeListener;
    /* access modifiers changed from: private */
    public OnMotionEventListener mOnMotionEventListener;
    private OverlayHolderImpl mOverlayHolder;
    /* access modifiers changed from: private */
    public boolean mPaused;
    /* access modifiers changed from: private */
    public MiniAppPointOverlay mPointOverlay;
    private MiniAppPolygonOverlayHolder mPolygonOverlay;
    private MiniAppRasterOverlay mRasterOverlay;
    private int mScaleLogoSpace;
    /* access modifiers changed from: private */
    public H5MapScaleLineView mScaleView;
    private MiniAppTextureCacheManager mTextureCacheManager = new MiniAppTextureCacheManager();
    private MiniAppTextureIdManager mTextureIdManager = new MiniAppTextureIdManager();
    private amk mapListener = new amp() {
        public void afterDrawFrame(final int i, GLMapState gLMapState) {
            if (AdapterTextureMapView.this.mMapView != null && i == AdapterTextureMapView.this.mMapView.j() && AdapterTextureMapView.this.mCoverViewAdded.get()) {
                AdapterTextureMapView.this.post(new Runnable() {
                    public void run() {
                        if (!AdapterTextureMapView.this.mPaused && AdapterTextureMapView.this.mCoverViewAdded.get() && AdapterTextureMapView.this.mCoverView != null) {
                            StringBuilder sb = new StringBuilder("remove, engineId: ");
                            sb.append(i);
                            AMapLog.debug("infoservice.miniapp", AdapterTextureMapView.TAG, sb.toString());
                            AdapterTextureMapView.this.removeView(AdapterTextureMapView.this.mCoverView);
                            AdapterTextureMapView.this.mCoverViewAdded.set(false);
                        }
                    }
                });
            }
        }
    };

    public interface OnFloorChangeListener {
        void notifyStateChange(boolean z);
    }

    public interface OnMotionEventListener {
        void onMotionBegin();

        void onMotionEnd();
    }

    public AdapterTextureMapView(Context context) {
        super(context);
    }

    public AdapterTextureMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AdapterTextureMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void initMapView(int i, int i2) {
        brw brw;
        StringBuilder sb = new StringBuilder("initMapView:");
        sb.append(i);
        sb.append("， ");
        sb.append(i2);
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
        this.mLogoWidth = agn.a(getContext(), 80.0f);
        this.mLogoHeight = agn.a(getContext(), 20.0f);
        this.mScaleLogoSpace = agn.a(getContext(), 10.0f);
        this.mAMapController = akq.b();
        this.mMapManager = DoNotUseTool.getMapManager();
        if (this.mMapManager == null) {
            AMapLog.error("infoservice.miniapp", TAG, "initMapView mMapManager is null!");
            return;
        }
        brx mapViewManager = this.mMapManager.getMapViewManager();
        if (mapViewManager == null) {
            AMapLog.error("infoservice.miniapp", TAG, "initMapView mMapViewManager is null!");
            return;
        }
        this.mAMapSurface = new AMapTextureSurface(getContext());
        this.mAMapSurface.setLayoutParams(new LayoutParams(-1, -1));
        this.mAMapSurface.init(this.mAMapController);
        MapEngineInitParam ExternalMapInitParam = MapEngineInitParam.ExternalMapInitParam();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        AMapTextureSurface aMapTextureSurface = this.mAMapSurface;
        Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        int i3 = displayMetrics.widthPixels;
        int i4 = displayMetrics.heightPixels;
        if (aMapTextureSurface == null) {
            AMapLog.d("MapViewManager", "amapSurface can not null");
            brw = null;
        } else {
            brw brw2 = new brw(mapViewManager.b, aMapTextureSurface, ExternalMapInitParam, rect, i3, i4);
            mapViewManager.a.put(Integer.valueOf(brw2.j()), brw2);
            brw2.a(mapViewManager.a.get(Integer.valueOf(brx.d)));
            akq c = brw2.c();
            if (c != null) {
                c.K = mapViewManager.a;
            }
            brw = brw2;
        }
        this.mMapView = brw;
        this.mMapView.a(this.mMapView.j(), this.mMapView.p(false), this.mMapView.ae(), 0, 0);
        this.mMapView.f(18.0f);
        this.mAMapController.c(this.mAMapSurface.getDeviceId(), true);
        if (bno.a) {
            akq.y = true;
        }
        this.mOverlayHolder = new OverlayHolderImpl(this.mMapView);
        this.mRasterOverlay = new MiniAppRasterOverlay(this.mMapView, this.mTextureIdManager, this.mTextureCacheManager);
        this.mRasterOverlay.setOverlayPriority(80, 0);
        this.mPointOverlay = new MiniAppPointOverlay(this.mMapView, this.mTextureIdManager, this.mTextureCacheManager);
        this.mLineOverlay = new MiniAppLineOverlay(this.mMapView, this.mTextureIdManager, this.mTextureCacheManager);
        this.mGpsOverlay = new GpsOverlay(getContext(), this.mMapView);
        this.mPolygonOverlay = new MiniAppPolygonOverlayHolder(this.mMapView, this.mOverlayHolder, this.mTextureCacheManager);
        addOverlay(this.mRasterOverlay);
        addOverlay(this.mGpsOverlay);
        this.mPolygonOverlay.addOverlay();
        addOverlay(this.mLineOverlay);
        addOverlay(this.mPointOverlay);
        this.mAMapSurface.setMapSurfaceListener(new amr() {
            public void onDrawFrameFirst(int i) {
            }

            public void onSurfaceCreated(int i) {
                AdapterTextureMapView.this.setBackgroundDrawable(null);
            }
        });
        this.mAMapSurface.setMapGestureListener(new amj() {
            public boolean onClick(int i, float f, float f2) {
                return false;
            }

            public boolean onDoubleClick(int i, float f, float f2) {
                return false;
            }

            public boolean onLongPress(int i, float f, float f2) {
                return false;
            }

            public boolean onTouchEvent(int i, MotionEvent motionEvent) {
                return false;
            }

            public boolean onMontionStart(int i, float f, float f2) {
                if (AdapterTextureMapView.this.mOnMotionEventListener != null) {
                    AdapterTextureMapView.this.mOnMotionEventListener.onMotionBegin();
                }
                return false;
            }

            public boolean onMontionFinish(int i) {
                if (AdapterTextureMapView.this.mOnMotionEventListener != null) {
                    AdapterTextureMapView.this.mOnMotionEventListener.onMotionEnd();
                }
                return false;
            }
        });
        this.mAMapController.a(this.mapListener);
        akq.b().a((aml) new aml() {
            public boolean onBlankClick(int i) {
                return false;
            }

            public void onLineOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
            }

            public boolean onNoBlankClick(int i) {
                return false;
            }

            public void onNoFeatureClick(int i) {
            }

            public void onPointOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
                if (AdapterTextureMapView.this.mMapView != null && i == AdapterTextureMapView.this.mMapView.j() && AdapterTextureMapView.this.mPointOverlay != null && AdapterTextureMapView.this.mPointOverlay.isClickable() && AdapterTextureMapView.this.mPointOverlay.isVisible() && ((long) AdapterTextureMapView.this.mPointOverlay.hashCode()) == gLAmapFocusHits.mOverlayHashCode) {
                    MiniAppPointOverlayItem miniAppPointOverlayItem = (MiniAppPointOverlayItem) AdapterTextureMapView.this.mPointOverlay.getItem((int) gLAmapFocusHits.mHitedIndex);
                    if (miniAppPointOverlayItem != null) {
                        AdapterTextureMapView.this.mPointOverlay.dispatchItemClick(miniAppPointOverlayItem, (long) gLAmapFocusHits.markerIndex);
                    }
                }
            }
        });
        addView(this.mAMapSurface);
        int a = agn.a(getContext(), 60.0f);
        int a2 = agn.a(getContext(), 30.0f);
        initScaleline(a, a2);
        initLogoView(a, a2);
        initIndoorView();
        initCompassView();
        this.mControllerContainer = new FrameLayout(getContext());
        this.mControllerContainer.setLayoutParams(new LayoutParams(-1, -1));
        addView(this.mControllerContainer);
        AMapLog.debug("infoservice.miniapp", TAG, "initMapView complete");
        setEnableRotateGesture(false);
        setEnableTiltGesture(false);
        this.mCoverView = new View(getContext());
        this.mCoverView.setLayoutParams(new LayoutParams(-1, -1));
        this.mCoverView.setBackgroundColor(-986896);
        addView(this.mCoverView);
        this.mCoverViewAdded.set(true);
    }

    private void initScaleline(int i, int i2) {
        this.mScaleView = new H5MapScaleLineView(getContext(), null);
        this.mScaleView.mAlignRight = false;
        this.mScaleView.setMapView(this.mMapView);
        LayoutParams layoutParams = new LayoutParams(-2, this.mLogoHeight);
        layoutParams.gravity = 80;
        layoutParams.bottomMargin = i2 + (this.mLogoHeight / 2) + this.mScaleLogoSpace;
        layoutParams.leftMargin = i - (this.mLogoWidth / 2);
        this.mScaleView.setLayoutParams(layoutParams);
        this.mScaleView.setVisibility(8);
        addView(this.mScaleView);
        this.mMapView.a((amn) new ams() {
            public void refreshScaleLineView(int i) {
                AdapterTextureMapView.this.post(new Runnable() {
                    public void run() {
                        if (AdapterTextureMapView.this.mScaleView != null) {
                            AdapterTextureMapView.this.mScaleView.refreshScaleLineView();
                        }
                    }
                });
            }
        });
    }

    private void initIndoorView() {
        this.mFloorContainerView = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 83;
        layoutParams.bottomMargin = agn.a(getContext(), 10.0f);
        layoutParams.leftMargin = agn.a(getContext(), 10.0f);
        this.mFloorContainerView.setLayoutParams(layoutParams);
        addView(this.mFloorContainerView);
        this.mMiniAppFloorManager = new MiniAppFloorManager();
        this.mMiniAppFloorManager.setMiniAppContext(this.mMapManager, this.mMapView, getContext());
        this.mMiniAppFloorManager.init();
        this.mMiniAppFloorManager.setFloorWidgetParent(this.mFloorContainerView);
        this.mMiniAppFloorManager.setTipPosition(true);
        this.mMiniAppFloorManager.addFloorWidgetChangedListener(this.floorWidgetChangedListener);
    }

    private void initLogoView(int i, int i2) {
        this.mLogoView = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(this.mLogoWidth, this.mLogoHeight);
        layoutParams.bottomMargin = i2 - (this.mLogoHeight / 2);
        layoutParams.leftMargin = i - (this.mLogoWidth / 2);
        layoutParams.gravity = 80;
        this.mLogoView.setLayoutParams(layoutParams);
        this.mLogoView.setImageResource(R.drawable.default_main_autonavi_logo);
        addView(this.mLogoView);
    }

    private void initCompassView() {
        this.mCompassView = new UICompassWidget(getContext());
        this.mCompassView.attachMapView(this.mMapView);
        this.mCompassView.setCompassRes(R.drawable.suspend_compass);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(getContext(), 13.0f);
        layoutParams.topMargin = agn.a(getContext(), 13.0f);
        layoutParams.gravity = 48;
        this.mCompassView.setLayoutParams(layoutParams);
        this.mCompassView.setVisibility(8);
        addView(this.mCompassView);
    }

    public bty getMap() {
        return this.mMapView;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        StringBuilder sb = new StringBuilder("onLayout:");
        sb.append(i3 - i);
        sb.append("/");
        sb.append(i4 - i2);
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
    }

    public void setMapListener(amk amk) {
        if (amk != null && this.mAMapController != null) {
            this.mAMapController.b(amk);
            this.mAMapController.a(amk);
        }
    }

    public void removeMapListener(amk amk) {
        if (amk != null && this.mAMapController != null) {
            this.mAMapController.b(amk);
        }
    }

    public void onCreate(Bundle bundle) {
        AMapLog.debug("infoservice.miniapp", TAG, "onCreate:".concat(String.valueOf(bundle)));
    }

    public void onResume() {
        AMapLog.debug("infoservice.miniapp", TAG, "onConResume");
        if (this.mMapView != null) {
            this.mMapView.N();
            this.mPaused = false;
            MiniAppGpsOverlayUpdater.getInstance().addOverlay(this.mGpsOverlay);
        }
    }

    public void onPause() {
        AMapLog.debug("infoservice.miniapp", TAG, "onPause");
        if (this.mMapView != null) {
            MiniAppGpsOverlayUpdater.getInstance().removeOverlay(this.mGpsOverlay);
            this.mMapView.M();
            addView(this.mCoverView);
            this.mCoverViewAdded.set(true);
            this.mPaused = true;
            StringBuilder sb = new StringBuilder("add, engineId: ");
            sb.append(this.mMapView.j());
            AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onDestroy(boolean z) {
        this.mMapView.a((amn) null);
        this.mMiniAppFloorManager.removeFloorWidgetChangedListener(this.floorWidgetChangedListener);
        this.mMiniAppFloorManager.destroy();
        this.mMiniAppFloorManager = null;
        this.mMapView.a((amm) null);
        this.mAMapSurface.setMapSurfaceListener(null);
        this.mAMapController.b(this.mapListener);
        if (z) {
            MiniAppGpsOverlayUpdater.getInstance().removeOverlay(this.mGpsOverlay);
            removeOverlay(this.mPointOverlay);
            removeOverlay(this.mLineOverlay);
            removeOverlay(this.mRasterOverlay);
            this.mPolygonOverlay.removeOverlay();
            removeOverlay(this.mGpsOverlay);
            brx mapViewManager = this.mMapManager.getMapViewManager();
            int j = this.mMapView.j();
            bty bty = mapViewManager.a.get(Integer.valueOf(j));
            if (bty != null) {
                bty.ap().onHide();
                bty.ao().release();
            } else {
                AMapLog.d("MapViewManager", "mapView has removed");
            }
            mapViewManager.a.remove(Integer.valueOf(j));
            this.mAMapSurface.uninit();
        }
        this.mPointOverlay = null;
        this.mLineOverlay = null;
        this.mPolygonOverlay = null;
        this.mRasterOverlay = null;
        this.mGpsOverlay = null;
        this.mOverlayHolder = null;
        this.mTextureCacheManager.clearTextureCache(this.mMapView);
        this.mMapView = null;
    }

    public void clear() {
        if (this.mLineOverlay != null) {
            this.mLineOverlay.clear();
        }
        if (this.mPointOverlay != null) {
            this.mPointOverlay.clear();
        }
        if (this.mPolygonOverlay != null) {
            this.mPolygonOverlay.clear();
        }
        if (this.mRasterOverlay != null) {
            this.mRasterOverlay.clear();
        }
    }

    public int getAdapterChildCount() {
        return this.mControllerContainer.getChildCount();
    }

    public View getAdapterChildAt(int i) {
        return this.mControllerContainer.getChildAt(i);
    }

    public void addAdapterView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mControllerContainer.addView(view, layoutParams);
    }

    public void removeAdapterView(View view) {
        this.mControllerContainer.removeView(view);
    }

    private void addOverlay(BaseOverlay baseOverlay) {
        if (this.mOverlayHolder != null) {
            this.mOverlayHolder.simpleOverlayHolder.addOverlay(baseOverlay, true);
        }
    }

    private void removeOverlay(BaseOverlay baseOverlay) {
        if (this.mOverlayHolder != null) {
            this.mOverlayHolder.simpleOverlayHolder.removeOverlay(baseOverlay);
        }
    }

    public void setLocationMarkerVisible(boolean z) {
        int i;
        if (z) {
            this.mGpsOverlay.setVisible(true);
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            cfg.a(latestLocation.getLatitude(), latestLocation.getLongitude());
            Bundle extras = latestLocation.getExtras();
            if (extras == null) {
                i = (int) latestLocation.getAccuracy();
            } else {
                i = (int) extras.getDouble("render_accuracy", (double) latestLocation.getAccuracy());
            }
            int i2 = i;
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition == null) {
                latestPosition = this.mMapView.o();
            }
            if (LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
                this.mGpsOverlay.setItem(latestPosition.x, latestPosition.y, i2, (int) latestLocation.getAltitude(), 0, null);
            } else {
                this.mGpsOverlay.setItem(latestPosition.x, latestPosition.y, i2, 0, 1, null);
            }
            this.mGpsOverlay.refreshItem(true);
            return;
        }
        this.mGpsOverlay.setVisible(false);
    }

    public MiniAppPointOverlay getPointOverlay() {
        return this.mPointOverlay;
    }

    public MiniAppLineOverlay getLineOverlay() {
        return this.mLineOverlay;
    }

    public MiniAppPolygonOverlayHolder getPolygonOverlay() {
        return this.mPolygonOverlay;
    }

    public MiniAppRasterOverlay getRasterOverlay() {
        return this.mRasterOverlay;
    }

    public void setMapMode(int i) {
        switch (i) {
            case 0:
                this.mMapView.a(this.mMapView.j(), this.mMapView.p(false), this.mMapView.ae(), 0, 0);
                return;
            case 1:
                this.mMapView.a(this.mMapView.j(), this.mMapView.p(false), this.mMapView.ae(), 11, 0);
                break;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        r10.mMapView.a(r10.mMapView.j(), r6, r7, r8, 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0030, code lost:
        if (r11.intValue() != 2) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0032, code lost:
        r10.mScaleView.setScaleLineColor(-1, -16777216);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0038, code lost:
        r10.mScaleView.setScaleLineColor(-16777216, -1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003d, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        r7 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setMapType(java.lang.Integer r11) {
        /*
            r10 = this;
            if (r11 != 0) goto L_0x0003
            return
        L_0x0003:
            int r0 = r11.intValue()
            r1 = 1
            r2 = 2
            r3 = 0
            switch(r0) {
                case 0: goto L_0x001a;
                case 1: goto L_0x0018;
                case 2: goto L_0x0015;
                case 3: goto L_0x0010;
                case 4: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x001a
        L_0x000e:
            r6 = 2
            goto L_0x001b
        L_0x0010:
            r0 = 4
            r6 = 0
            r7 = 0
            r8 = 4
            goto L_0x001d
        L_0x0015:
            r6 = 0
            r7 = 1
            goto L_0x001c
        L_0x0018:
            r6 = 1
            goto L_0x001b
        L_0x001a:
            r6 = 0
        L_0x001b:
            r7 = 0
        L_0x001c:
            r8 = 0
        L_0x001d:
            bty r4 = r10.mMapView
            bty r0 = r10.mMapView
            int r5 = r0.j()
            r9 = 1
            r4.a(r5, r6, r7, r8, r9)
            int r11 = r11.intValue()
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r1 = -1
            if (r11 != r2) goto L_0x0038
            com.autonavi.miniapp.plugin.map.H5MapScaleLineView r11 = r10.mScaleView
            r11.setScaleLineColor(r1, r0)
            return
        L_0x0038:
            com.autonavi.miniapp.plugin.map.H5MapScaleLineView r11 = r10.mScaleView
            r11.setScaleLineColor(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.map.AdapterTextureMapView.setMapType(java.lang.Integer):void");
    }

    public void setShowLogo(boolean z) {
        this.mLogoView.setVisibility(z ? 0 : 8);
    }

    public void setInnerLogoPosition(int i, int i2, int i3) {
        LayoutParams layoutParams = new LayoutParams(this.mLogoWidth, this.mLogoHeight);
        layoutParams.bottomMargin = i3;
        if (i == 0) {
            layoutParams.gravity = 83;
            layoutParams.leftMargin = i2;
        } else {
            layoutParams.gravity = 85;
            layoutParams.rightMargin = i2;
        }
        this.mLogoView.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams(-2, this.mLogoHeight);
        layoutParams2.bottomMargin = i3 + this.mLogoHeight + this.mScaleLogoSpace;
        if (i == 0) {
            layoutParams2.gravity = 83;
            layoutParams2.leftMargin = i2;
            this.mScaleView.mAlignRight = false;
        } else {
            layoutParams2.gravity = 85;
            layoutParams2.rightMargin = i2;
            this.mScaleView.mAlignRight = true;
        }
        this.mScaleView.setLayoutParams(layoutParams2);
    }

    public void setLogoPosition(int i, int i2) {
        int i3 = i - (this.mLogoWidth / 2);
        int i4 = i2 - (this.mLogoHeight / 2);
        if (!isLogoBoundsValid(i3, i4)) {
            AMapLog.info("infoservice.miniapp", TAG, "invalid logo position, logo is forced to be shown.");
            return;
        }
        LayoutParams layoutParams = new LayoutParams(this.mLogoWidth, this.mLogoHeight);
        layoutParams.leftMargin = i3;
        layoutParams.topMargin = i4;
        this.mLogoView.setLayoutParams(layoutParams);
        LayoutParams layoutParams2 = new LayoutParams(-2, this.mLogoHeight);
        layoutParams2.leftMargin = i3;
        layoutParams2.topMargin = (i4 - this.mLogoHeight) - this.mScaleLogoSpace;
        this.mScaleView.setLayoutParams(layoutParams2);
    }

    private boolean isLogoBoundsValid(int i, int i2) {
        return i >= 0 && i <= getWidth() - this.mLogoWidth && i2 >= 0 && i2 <= getHeight() - this.mLogoHeight;
    }

    public void setShowScaleView(boolean z) {
        this.mScaleView.setVisibility(z ? 0 : 8);
    }

    public void setLockCameraDegree(boolean z) {
        boolean h = this.mAMapController.h(this.mMapView.j());
        if (z) {
            if (!h) {
                this.mAMapController.a(this.mMapView.j(), false);
            }
        } else if (h) {
            this.mAMapController.g(this.mMapView.j());
        }
    }

    public void setLockMapAngle(boolean z) {
        boolean j = this.mAMapController.j(this.mMapView.j());
        if (z) {
            if (!j) {
                this.mAMapController.b(this.mMapView.j(), false);
            }
        } else if (j) {
            this.mAMapController.i(this.mMapView.j());
        }
    }

    public void setShowCompass(boolean z) {
        this.mCompassView.setVisibility(z ? 0 : 8);
    }

    public void repaintCompass() {
        this.mCompassView.paintCompass();
    }

    public void setDisableGesture(boolean z) {
        if (this.mMapView != null && this.mAMapController != null && this.mAMapController.d != null) {
            this.mAMapController.d.setSrvViewStateBoolValue(this.mMapView.j(), 11, z);
        }
    }

    public void setEnableZoomGesture(boolean z) {
        if (this.mMapView != null && this.mAMapController != null && this.mAMapController.d != null) {
            this.mAMapController.a(this.mMapView.j(), 2, z);
            this.mAMapController.a(this.mMapView.j(), 4, z);
            this.mAMapController.a(this.mMapView.j(), 16, z);
            this.mAMapController.a(this.mMapView.j(), 512, z);
        }
    }

    public void setEnableTiltGesture(boolean z) {
        if (this.mMapView != null && this.mAMapController != null && this.mAMapController.d != null) {
            this.mAMapController.a(this.mMapView.j(), 64, z);
        }
    }

    public void setEnableScrollGesture(boolean z) {
        if (this.mMapView != null && this.mAMapController != null && this.mAMapController.d != null) {
            this.mAMapController.a(this.mMapView.j(), 8, z);
        }
    }

    public void setEnableRotateGesture(boolean z) {
        if (this.mMapView != null && this.mAMapController != null && this.mAMapController.d != null) {
            this.mAMapController.a(this.mMapView.j(), 32, z);
        }
    }

    public void refreshRender() {
        if (this.mAMapSurface != null && this.mAMapController != null) {
            this.mAMapController.r(this.mAMapSurface.getDeviceId());
        }
    }

    public MiniAppFloorManager getMiniAppFloorManager() {
        return this.mMiniAppFloorManager;
    }

    public void setIndoormapPosition(int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        if (z) {
            layoutParams.gravity = 85;
            layoutParams.rightMargin = i4;
        } else {
            layoutParams.gravity = 83;
            layoutParams.leftMargin = i3;
        }
        layoutParams.bottomMargin = i2;
        this.mFloorContainerView.setLayoutParams(layoutParams);
    }

    public void setIndoormapEnable(Boolean bool) {
        if (bool != null) {
            getMiniAppFloorManager().setEnable(bool.booleanValue());
            getMap().c().d(getMap().j(), bool.booleanValue());
        }
    }

    public void setActiveFloor(int i) {
        this.mMiniAppFloorManager.setCurrentValue(this.mMiniAppFloorManager.getLastIndoorBuilding().h[i]);
    }

    /* access modifiers changed from: private */
    public void notifyStateChange(boolean z) {
        if (this.mOnFloorChangeListener != null) {
            this.mOnFloorChangeListener.notifyStateChange(z);
        }
    }

    public void setOnFloorChangeListener(OnFloorChangeListener onFloorChangeListener) {
        this.mOnFloorChangeListener = onFloorChangeListener;
    }

    public void setOnMotionEventListener(OnMotionEventListener onMotionEventListener) {
        this.mOnMotionEventListener = onMotionEventListener;
    }
}
