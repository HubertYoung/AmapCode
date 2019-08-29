package com.autonavi.miniapp.plugin.lbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.map.model.geocode.PoiItem;
import com.alipay.mobile.nebula.util.H5Log;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.miniapp.plugin.constant.ConstantCompat;
import com.autonavi.miniapp.plugin.entity.ChoosePoiListItem;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView.OnMotionEventListener;
import com.autonavi.miniapp.plugin.view.ChoosePointBottomBar;
import com.autonavi.miniapp.plugin.view.ChoosePointBottomBar.BottomBarCallListener;
import com.autonavi.miniapp.plugin.view.ChoosePointBottomBar.IRequestDoneCallback;
import com.autonavi.miniapp.plugin.view.ChoosePointBottomBar.Mode;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.sdk.location.LocationInstrument;

public class H5MapChooseLocationActivity extends BaseActivity implements BottomBarCallListener, IRequestDoneCallback {
    public static final String TAG = "H5MapActivity";
    private AUTitleBar auTitleBar;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getExtras() != null) {
                PoiItem poiItem = (PoiItem) intent.getExtras().getSerializable("EVENT_POI_LOAD_SEARCH");
                GeoPointHD geoPointHD = new GeoPointHD(poiItem.getLatLonPoint().getLongitude(), poiItem.getLatLonPoint().getLatitude());
                H5MapChooseLocationActivity.this.setMapCenterPoint(geoPointHD.x, geoPointHD.y);
                H5MapChooseLocationActivity.this.mChoosePointView.setMode(Mode.SEARCH);
                H5MapChooseLocationActivity.this.mChoosePointView.setPoiSearched(poiItem);
                H5MapChooseLocationActivity.this.startTargetPointAnim(0);
            }
        }
    };
    private ViewGroup container;
    /* access modifiers changed from: private */
    public Handler handler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public ChoosePointBottomBar mChoosePointView;
    /* access modifiers changed from: private */
    public GLGeoPoint mLastMapCenter;
    /* access modifiers changed from: private */
    public AdapterTextureMapView mMiniAppMapView;
    private View mMoveToLocationView;
    /* access modifiers changed from: private */
    public TranslateAnimation mOvershootAnimation;
    private String mPoiName;
    private POI mResultPOI;
    private GeoPoint mTargetPoint;
    /* access modifiers changed from: private */
    public ImageView mTargetPointImageView;
    private View mTargetPointImageViewContainer;

    class ParseIntent {
        private GeoPoint centerPoint;
        private boolean myResult;
        private LatLonPointEx point;
        private int scale;

        private ParseIntent() {
        }

        /* access modifiers changed from: 0000 */
        public boolean is() {
            return this.myResult;
        }

        public int getScale() {
            return this.scale;
        }

        public LatLonPointEx getPoint() {
            return this.point;
        }

        public GeoPoint getCenterPoint() {
            return this.centerPoint;
        }

        public ParseIntent invoke() {
            Intent intent = H5MapChooseLocationActivity.this.getIntent();
            if (intent == null) {
                H5Log.d("H5MapActivity", "intent == null");
                this.myResult = true;
                return this;
            }
            double doubleExtra = intent.getDoubleExtra("longitude", 0.0d);
            double doubleExtra2 = intent.getDoubleExtra("latitude", 0.0d);
            this.scale = (int) intent.getDoubleExtra(WidgetType.SCALE, 15.0d);
            String stringExtra = intent.getStringExtra("name");
            String stringExtra2 = intent.getStringExtra("address");
            intent.getStringExtra("hidden");
            this.point = new LatLonPointEx(doubleExtra2, doubleExtra);
            if (!TextUtils.isEmpty(stringExtra)) {
                this.point.setTitle(stringExtra);
            }
            this.centerPoint = new GeoPoint(doubleExtra, doubleExtra2);
            this.point.setSnippet(stringExtra2);
            this.myResult = false;
            return this;
        }
    }

    public void onPointerCaptureChanged(boolean z) {
    }

    public void onRequestDone() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_h5map_chooselocation);
        this.container = (ViewGroup) findViewById(R.id.container);
        injectOtherView();
        addMapView(this.container);
        ParseIntent invoke = new ParseIntent().invoke();
        if (!invoke.is()) {
            int scale = invoke.getScale();
            GeoPoint centerPoint = invoke.getCenterPoint();
            this.mTargetPoint = centerPoint;
            setupMap(scale, centerPoint);
            initTargetPointAnim();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, new IntentFilter(ConstantCompat.MINI_APP_ACTION));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mMiniAppMapView.onResume();
        this.container.post(new Runnable() {
            public void run() {
                H5MapChooseLocationActivity.this.addTargetPointOnMap();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mMiniAppMapView.onPause();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        cancelRequestPoint();
        resetMapCenter();
        if (this.mChoosePointView != null) {
            this.mChoosePointView.setOnRequestDoneCallback(null);
            this.mChoosePointView.registerCallback(null);
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver);
        ((awi) a.a.a(awi.class)).b();
        this.mMiniAppMapView.onDestroy(true);
    }

    public void onBackPressed() {
        super.onBackPressed();
        awi awi = (awi) a.a.a(awi.class);
        if (awi != null) {
            awj a = awi.a();
            if (a instanceof OnMapPoiSelectedListener) {
                ((OnMapPoiSelectedListener) a).onPoiSelectCancel();
            }
        }
    }

    private void setupMap(int i, GeoPoint geoPoint) {
        this.mMiniAppMapView.getMap().e(i);
        this.mMiniAppMapView.getMap().a(geoPoint.x, geoPoint.y);
        this.mMiniAppMapView.setShowLogo(true);
        this.mMiniAppMapView.setShowScaleView(true);
        this.mMiniAppMapView.setLocationMarkerVisible(true);
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mMoveToLocationView.getLayoutParams();
        this.mMiniAppMapView.setInnerLogoPosition(1, marginLayoutParams.leftMargin, marginLayoutParams.bottomMargin);
    }

    private void addMapView(ViewGroup viewGroup) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mMiniAppMapView = new AdapterTextureMapView(this);
        this.mMiniAppMapView.initMapView(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mMiniAppMapView.setLayoutParams(new LayoutParams(-1, -1));
        this.mMiniAppMapView.onCreate(new Bundle());
        viewGroup.addView(this.mMiniAppMapView, 0);
        this.mMiniAppMapView.setOnMotionEventListener(new OnMotionEventListener() {
            public void onMotionBegin() {
            }

            public void onMotionEnd() {
                H5MapChooseLocationActivity.this.handler.post(new Runnable() {
                    public void run() {
                        H5MapChooseLocationActivity.this.mChoosePointView.setMode(Mode.LOCATION);
                        GLGeoPoint mapCenterPoint = H5MapChooseLocationActivity.this.getMapCenterPoint();
                        if (H5MapChooseLocationActivity.this.mLastMapCenter == null || !H5MapChooseLocationActivity.this.mLastMapCenter.equals(mapCenterPoint)) {
                            H5MapChooseLocationActivity.this.mLastMapCenter = mapCenterPoint;
                            H5MapChooseLocationActivity.this.startTargetPointAnim(0);
                        }
                    }
                });
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    private void injectOtherView() {
        this.mChoosePointView = (ChoosePointBottomBar) findViewById(R.id.choose_poi_bottomBar);
        this.mTargetPointImageView = (ImageView) findViewById(R.id.iv_center);
        this.mTargetPointImageViewContainer = findViewById(R.id.map_select_center_offset_layout);
        this.mChoosePointView.registerCallback(this);
        this.mChoosePointView.setOnRequestDoneCallback(this);
        this.mChoosePointView.setSelectType(SelectFor.DEFAULT_POI);
        this.auTitleBar = (AUTitleBar) findViewById(R.id.aptitlebar);
        this.auTitleBar.setTitleText("选择位置");
        this.auTitleBar.setRightButtonIcon(getResources().getDrawable(R.drawable.search_btn));
        this.auTitleBar.getRightButton().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("geoPoint", GeoPoint.glGeoPoint2GeoPoint(H5MapChooseLocationActivity.this.getMapCenterPoint()));
                intent.setClass(LauncherApplicationAgent.getInstance().getApplicationContext(), H5PoiSearchActivity.class);
                MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
                if (microApplicationContext.getTopApplication() != null) {
                    microApplicationContext.startActivity((MicroApplication) microApplicationContext.getTopApplication(), intent);
                    return;
                }
                Context context = (Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
                if (context == null) {
                    context = LauncherApplicationAgent.getInstance().getApplicationContext();
                    intent.setFlags(268435456);
                }
                if (context != null) {
                    context.startActivity(intent);
                }
            }
        });
        this.mTargetPointImageViewContainer.setVisibility(0);
        this.mMoveToLocationView = findViewById(R.id.activity_chooselocation_location);
        this.mMoveToLocationView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                H5MapChooseLocationActivity.this.setMapCenterPoint(latestPosition.x, latestPosition.y);
                H5MapChooseLocationActivity.this.startTargetPointAnim(0);
            }
        });
    }

    private void initTargetPointAnim() {
        if (this.mOvershootAnimation == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, -30.0f);
            this.mOvershootAnimation = translateAnimation;
            this.mOvershootAnimation.setDuration(250);
            this.mOvershootAnimation.setInterpolator(new AccelerateInterpolator());
        }
    }

    public void setTargetPointImageView(final int i) {
        this.container.postDelayed(new Runnable() {
            public void run() {
                if (H5MapChooseLocationActivity.this.mTargetPointImageView != null) {
                    H5MapChooseLocationActivity.this.mTargetPointImageView.setImageResource(i);
                }
            }
        }, 50);
    }

    public GLGeoPoint getMapCenterPoint() {
        if (this.mMiniAppMapView == null || this.mMiniAppMapView.getMap() == null) {
            return null;
        }
        return this.mMiniAppMapView.getMap().n();
    }

    public GeoPoint getMapCenterFromPixels() {
        bty map = this.mMiniAppMapView.getMap();
        return GeoPoint.glGeoPoint2GeoPoint(map.c(map.al() / 2, map.am() / 2));
    }

    public void setMapCenterPoint(int i, int i2) {
        if (this.mMiniAppMapView != null && this.mMiniAppMapView.getMap() != null) {
            this.mMiniAppMapView.getMap().a(i, i2);
        }
    }

    public void onClick(View view, String str, ChoosePoiListItem choosePoiListItem) {
        selectPoint(str, choosePoiListItem);
    }

    private void selectPoint(String str, ChoosePoiListItem choosePoiListItem) {
        POI poi;
        if (choosePoiListItem != null) {
            this.mPoiName = choosePoiListItem.mPoiName;
            poi = POIFactory.createPOI(this.mPoiName, choosePoiListItem.mPoint);
            poi.setId(choosePoiListItem.mPoiId);
            poi.setAddr(choosePoiListItem.mAddress);
            poi.setEntranceList(choosePoiListItem.mEntranceList);
            poi.setEndPoiExtension(choosePoiListItem.mEndPoiExtension);
            poi.setTransparent(choosePoiListItem.mTransparent);
            ((FavoritePOI) poi.as(FavoritePOI.class)).setNewType(choosePoiListItem.mNewType);
        } else {
            GeoPoint listRequestPoint = getListRequestPoint();
            if (listRequestPoint == null) {
                listRequestPoint = this.mTargetPoint;
            }
            if (listRequestPoint == null) {
                listRequestPoint = new GeoPoint();
            }
            POI createPOI = POIFactory.createPOI("", listRequestPoint);
            createPOI.setAddr(str);
            poi = createPOI;
        }
        this.mResultPOI = poi.clone();
        awj a = ((awi) a.a.a(awi.class)).a();
        if (a instanceof OnMapPoiSelectedListener) {
            a.onPoiSelected(this.mResultPOI);
        }
        finish();
    }

    public void onItemClick(View view, int i, GeoPoint geoPoint) {
        if (!(this.mMiniAppMapView == null || this.mMiniAppMapView.getMap() == null)) {
            bty map = this.mMiniAppMapView.getMap();
            if (geoPoint != null) {
                map.a(geoPoint.x, geoPoint.y);
                return;
            }
            GeoPoint targetPoint = getTargetPoint();
            if (targetPoint != null) {
                map.a(targetPoint.x, targetPoint.y);
            }
        }
    }

    private GeoPoint getTargetPoint() {
        return this.mTargetPoint;
    }

    public void cancelRequestPoint() {
        if (this.mChoosePointView != null) {
            this.mChoosePointView.cancleNetWork();
        }
    }

    private void resetMapCenter() {
        if (this.mChoosePointView != null) {
            DisplayMetrics displayMetrics = this.mChoosePointView.getContext().getResources().getDisplayMetrics();
            if (this.mMiniAppMapView != null && this.mMiniAppMapView.getMap() != null) {
                bty map = this.mMiniAppMapView.getMap();
                int i = displayMetrics.widthPixels / 2;
                int i2 = displayMetrics.heightPixels / 2;
                GLGeoPoint c = map.c(i, i2);
                map.b(i, i2);
                map.a(c.x, c.y);
            }
        }
    }

    public void startTargetPointAnim(long j) {
        this.container.postDelayed(new Runnable() {
            public void run() {
                H5MapChooseLocationActivity.this.updatePointInfo(GeoPoint.glGeoPoint2GeoPoint(H5MapChooseLocationActivity.this.mMiniAppMapView.getMap().n()));
                if (H5MapChooseLocationActivity.this.mTargetPointImageView != null && H5MapChooseLocationActivity.this.mOvershootAnimation != null) {
                    H5MapChooseLocationActivity.this.mTargetPointImageView.startAnimation(H5MapChooseLocationActivity.this.mOvershootAnimation);
                    H5MapChooseLocationActivity.this.mOvershootAnimation.startNow();
                }
            }
        }, j);
    }

    public void requestPointInfo(GeoPoint geoPoint) {
        if (!isFinishing()) {
            this.mChoosePointView.requestPoint(geoPoint);
        }
    }

    /* access modifiers changed from: private */
    public void addTargetPointOnMap() {
        setTargetPointImageView(R.drawable.b_poi);
        if (this.mTargetPoint == null) {
            this.mTargetPoint = getMapCenterFromPixels();
        } else {
            setMapCenterPoint(this.mTargetPoint.x, this.mTargetPoint.y);
        }
        addSelectedPointOnMap();
        if (this.mTargetPoint != null) {
            updatePointInfo(this.mTargetPoint);
        }
    }

    /* access modifiers changed from: private */
    public void updatePointInfo(GeoPoint geoPoint) {
        this.mPoiName = SearchConst.b;
        this.mTargetPoint = geoPoint;
        requestPointInfo(geoPoint);
    }

    private void addSelectedPointOnMap() {
        if (this.mTargetPoint != null) {
            requestPointInfo(this.mTargetPoint);
        }
    }

    public GeoPoint getListRequestPoint() {
        if (this.mChoosePointView != null) {
            return this.mChoosePointView.getListRequestPoint();
        }
        return null;
    }
}
