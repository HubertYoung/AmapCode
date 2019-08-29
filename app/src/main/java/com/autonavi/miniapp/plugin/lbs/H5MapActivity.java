package com.autonavi.miniapp.plugin.lbs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.nebula.util.H5Log;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlayItem;
import com.autonavi.miniapp.plugin.util.MapUtil;
import com.autonavi.miniapp.plugin.view.RoundedBackgroundDrawable;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;

public class H5MapActivity extends BaseActivity {
    private static final String HIDE_OPEN_NAVI = "0";
    private static final String HIDE_OPEN_TAXI = "1";
    public static final String TAG = "H5MapActivity";
    private View mInfoWindowView;
    /* access modifiers changed from: private */
    public AdapterTextureMapView mMiniAppMapView;
    private View mMoveToLocationView;
    private ImageView mOpenNaviView;
    /* access modifiers changed from: private */
    public Handler mainHandler = new Handler(Looper.getMainLooper());
    private amk mapListener;

    class InfoWindowMapListener implements amk {
        private GeoPoint centerPoint;
        /* access modifiers changed from: private */
        public View infoWindowView;
        /* access modifiers changed from: private */
        public int markerHeight;
        private MiniAppPointOverlayItem markerItem;
        private int markerWidth;

        public void beforeDrawFrame(int i, GLMapState gLMapState) {
        }

        public void onDoubleTap(int i, MotionEvent motionEvent) {
        }

        public void onEngineActionGesture(int i, alg alg) {
        }

        public void onEngineVisible(int i, boolean z) {
        }

        public boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public void onHorizontalMove(int i, float f) {
        }

        public void onHorizontalMoveEnd(int i) {
        }

        public void onHoveBegin(int i, MotionEvent motionEvent) {
        }

        public void onLongPress(int i, MotionEvent motionEvent) {
        }

        public void onMapAnimationFinished(int i, int i2) {
        }

        public void onMapAnimationFinished(int i, aln aln) {
        }

        public void onMapLevelChange(int i, boolean z) {
        }

        public void onMapRenderCompleted(int i) {
        }

        public void onMapSizeChange(int i) {
        }

        public void onMapTipClear(int i) {
        }

        public void onMapTipInfo(int i, String str) {
        }

        public void onMotionFinished(int i, int i2) {
        }

        public void onMoveBegin(int i, MotionEvent motionEvent) {
        }

        public void onOfflineMap(int i, String str, int i2) {
        }

        public void onRealCityAnimateFinish(int i) {
        }

        public void onScaleRotateBegin(int i, MotionEvent motionEvent) {
        }

        public void onScreenShotFinished(int i, long j) {
        }

        public void onScreenShotFinished(int i, Bitmap bitmap) {
        }

        public void onScreenShotFinished(int i, String str) {
        }

        public void onSelectSubWayActive(int i, byte[] bArr) {
        }

        public void onShowPress(int i, MotionEvent motionEvent) {
        }

        public boolean onSingleTapUp(int i, MotionEvent motionEvent) {
            return false;
        }

        public void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
        }

        public void onZoomOutTap(int i, MotionEvent motionEvent) {
        }

        public InfoWindowMapListener(GeoPoint geoPoint, View view, MiniAppPointOverlayItem miniAppPointOverlayItem) {
            this.centerPoint = geoPoint;
            this.infoWindowView = view;
            this.markerItem = miniAppPointOverlayItem;
            this.markerHeight = miniAppPointOverlayItem.mDefaultMarker.mHeight;
            this.markerWidth = miniAppPointOverlayItem.mDefaultMarker.mWidth;
        }

        public void afterDrawFrame(int i, GLMapState gLMapState) {
            if (H5MapActivity.this.mMiniAppMapView.getMap().j() == i) {
                final PointF f = H5MapActivity.this.mMiniAppMapView.getMap().f(this.centerPoint.x, this.centerPoint.y);
                H5MapActivity.this.mainHandler.post(new Runnable() {
                    public void run() {
                        InfoWindowMapListener.this.infoWindowView.setTranslationX(f.x - ((float) (InfoWindowMapListener.this.infoWindowView.getWidth() / 2)));
                        InfoWindowMapListener.this.infoWindowView.setTranslationY((f.y - ((float) InfoWindowMapListener.this.markerHeight)) - ((float) InfoWindowMapListener.this.infoWindowView.getHeight()));
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_h5map);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.container);
        ((AUTitleBar) findViewById(R.id.aptitlebar)).setTitleText(getResources().getString(R.string.open_location));
        this.mMoveToLocationView = findViewById(R.id.activity_h5map_location);
        this.mMoveToLocationView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                if (H5MapActivity.this.mMiniAppMapView != null && H5MapActivity.this.mMiniAppMapView.getMap() != null) {
                    H5MapActivity.this.mMiniAppMapView.getMap().a(latestPosition.x, latestPosition.y);
                }
            }
        });
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mMiniAppMapView = new AdapterTextureMapView(this);
        this.mMiniAppMapView.initMapView(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mMiniAppMapView.onCreate(new Bundle());
        this.mMiniAppMapView.setLayoutParams(new LayoutParams(-1, -1));
        viewGroup.addView(this.mMiniAppMapView, 0);
        Intent intent = getIntent();
        if (intent == null) {
            H5Log.d("H5MapActivity", "intent == null");
            return;
        }
        double doubleExtra = intent.getDoubleExtra("longitude", 0.0d);
        double doubleExtra2 = intent.getDoubleExtra("latitude", 0.0d);
        int doubleExtra3 = (int) intent.getDoubleExtra(WidgetType.SCALE, 17.0d);
        String stringExtra = intent.getStringExtra("name");
        String stringExtra2 = intent.getStringExtra("address");
        String stringExtra3 = intent.getStringExtra("hidden");
        LatLonPointEx latLonPointEx = new LatLonPointEx(doubleExtra2, doubleExtra);
        if (!TextUtils.isEmpty(stringExtra)) {
            latLonPointEx.setTitle(stringExtra);
        }
        GeoPoint geoPoint = new GeoPoint(doubleExtra, doubleExtra2);
        latLonPointEx.setSnippet(stringExtra2);
        this.mMiniAppMapView.getMap().e(doubleExtra3);
        this.mMiniAppMapView.getMap().a(geoPoint.x, geoPoint.y);
        this.mMiniAppMapView.setLocationMarkerVisible(true);
        this.mMiniAppMapView.setShowLogo(true);
        this.mMiniAppMapView.setShowScaleView(true);
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mMoveToLocationView.getLayoutParams();
        this.mMiniAppMapView.setInnerLogoPosition(1, marginLayoutParams.leftMargin, marginLayoutParams.bottomMargin);
        MiniAppPointOverlayItem addMiniAppViewMarker = this.mMiniAppMapView.getPointOverlay().addMiniAppViewMarker(R.drawable.lbs_marker_bottom, geoPoint);
        this.mInfoWindowView = getInfoWindow(viewGroup, latLonPointEx, stringExtra3);
        viewGroup.addView(this.mInfoWindowView);
        this.mapListener = new InfoWindowMapListener(geoPoint, this.mInfoWindowView, addMiniAppViewMarker);
        this.mMiniAppMapView.getMap().a(this.mapListener);
    }

    private View getInfoWindow(ViewGroup viewGroup, final LatLonPointEx latLonPointEx, String str) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService("layout_inflater");
        if (layoutInflater == null) {
            return null;
        }
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.view_info_window, viewGroup, false);
        RoundedBackgroundDrawable roundedBackgroundDrawable = new RoundedBackgroundDrawable();
        roundedBackgroundDrawable.setRoundCorner(agn.a((Context) this, 6.0f));
        roundedBackgroundDrawable.setDrawBottomTriangle(true);
        roundedBackgroundDrawable.setBottomTriangleSize(agn.a((Context) this, 10.0f), agn.a((Context) this, 5.0f));
        roundedBackgroundDrawable.setDrawColor(-11711153);
        linearLayout.setBackground(roundedBackgroundDrawable);
        TextView textView = (TextView) linearLayout.findViewById(R.id.title);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.snippet);
        this.mOpenNaviView = (ImageView) linearLayout.findViewById(R.id.open_navi);
        this.mOpenNaviView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MapUtil.openRoute(latLonPointEx.getLongitude(), latLonPointEx.getLatitude(), latLonPointEx.getTitle());
            }
        });
        String title = latLonPointEx.getTitle();
        String snippet = latLonPointEx.getSnippet();
        if (!TextUtils.isEmpty(title)) {
            textView.setText(title);
            textView.setVisibility(0);
        }
        if (!TextUtils.isEmpty(snippet)) {
            textView2.setText(snippet);
            textView2.setVisibility(0);
        }
        if (!TextUtils.isEmpty(str)) {
            for (String equals : str.split("_")) {
                if (TextUtils.equals(equals, "0")) {
                    this.mOpenNaviView.setVisibility(8);
                }
            }
        }
        return linearLayout;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mMiniAppMapView.getMap().b(this.mapListener);
        this.mMiniAppMapView.onDestroy(true);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mMiniAppMapView.onResume();
        this.mInfoWindowView.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mMiniAppMapView.onPause();
        this.mInfoWindowView.setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
}
