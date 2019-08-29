package com.autonavi.minimap.search.inter.impl;

import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.searchresult.util.TipLogHelper;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.search.fragment.BaseCQDetailOwner;
import com.autonavi.map.search.fragment.BaseCQDetailOwner.MiddleViewSlideType;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.map.search.tip.SearchPoiTipWrapper;
import com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView;
import com.autonavi.map.search.utils.PoiDetailRequester;
import com.autonavi.map.search.view.PoiDetailViewForCQ;
import com.autonavi.miniapp.plugin.lbs.H5Location;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page.AjxPageResultExecutor;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView.OnRequestCallbackListener;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.OnSetPoiListener;
import com.autonavi.minimap.search.inter.ICQDetailPageController;
import com.autonavi.minimap.search.inter.ICQDetailPageController.DetailLayerState;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@Keep
@SuppressFBWarnings({"BC_VACUOUS_INSTANCEOF"})
public class CQDetailPageControllerImpl implements defpackage.bra.a, ICQDetailPageController {
    public static final int CQ_TOP_OFFSET = 39;
    private static final int INVALID_MAP_LEVEL = -1;
    public static final int MSG_ADJUST_MARK = 4;
    public static final int MSG_ANIMATE_TO = 1;
    public static final int MSG_DISPATCH_SLIDEDEVENT = 2;
    public static final int MSG_REFRESH_LAYOUT = 3;
    public static final int MSG_SLIDE_MONITOR = 0;
    private static final int TIP_TYPE_LONG_PRESS = 4;
    private static final int TIP_TYPE_MY_LOCATION = 3;
    private static final int TIP_TYPE_SHORT_PRESS = 2;
    /* access modifiers changed from: private */
    public String mCurState;
    private int mCurrentTipType;
    /* access modifiers changed from: private */
    public bxw mDataHelper;
    /* access modifiers changed from: private */
    public DetailLayerState mDetailLayerState = DetailLayerState.COLLAPSED;
    /* access modifiers changed from: private */
    public long mFinishSequence = 0;
    /* access modifiers changed from: private */
    public Handler mHandler = new a(this, 0);
    private int mHeadHeight = 600;
    private float mHeaderMapLevel = -1.0f;
    /* access modifiers changed from: private */
    public View mIndicatorView;
    private boolean mIsFavWhenShow;
    boolean mIsLayerHidden = false;
    boolean mIsMapMiddleViewHidden = false;
    private boolean mIsScenic = false;
    /* access modifiers changed from: private */
    public boolean mIsShowing = false;
    /* access modifiers changed from: private */
    public cap mLayerManager;
    /* access modifiers changed from: private */
    public View mMapPointTipView;
    private boolean mNeedAddGpsHeight;
    /* access modifiers changed from: private */
    public float mOldLevel = -1.0f;
    private com.autonavi.bundle.searchresult.ajx.AjxModuleTipDetailPage.a mOnTipDetailStateChangeListener = new com.autonavi.bundle.searchresult.ajx.AjxModuleTipDetailPage.a() {
        public final void a(String str, JsFunctionCallback jsFunctionCallback) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int optInt = jSONObject.optInt("from");
                String optString = jSONObject.optString("action", "");
                if (optInt == 1) {
                    if (!TextUtils.isEmpty(optString)) {
                        POI poi = null;
                        JSONObject jSONObject2 = jSONObject.has("param") ? new JSONObject(jSONObject.optString("param", "")) : null;
                        if (optString.equals("detailStateDidChange")) {
                            String optString2 = jSONObject2.optString("state");
                            if (!TextUtils.isEmpty(optString2)) {
                                char c = 65535;
                                int hashCode = optString2.hashCode();
                                if (hashCode != -1068259250) {
                                    if (hashCode != 3154575) {
                                        if (hashCode == 3560248) {
                                            if (optString2.equals(ModulePoi.TIPS)) {
                                                c = 2;
                                            }
                                        }
                                    } else if (optString2.equals("full")) {
                                        c = 0;
                                    }
                                } else if (optString2.equals(ModulePoi.MOVE)) {
                                    c = 1;
                                }
                                switch (c) {
                                    case 0:
                                        CQDetailPageControllerImpl.this.mTargetState = "full";
                                        CQDetailPageControllerImpl.this.mDetailLayerState = DetailLayerState.EXPAND;
                                        if (CQDetailPageControllerImpl.this.mOwner.c() != null) {
                                            CQDetailPageControllerImpl.this.mOwner.c().setVisibility(8);
                                        }
                                        CQDetailPageControllerImpl.this.mIsMapMiddleViewHidden = true;
                                        break;
                                    case 1:
                                        CQDetailPageControllerImpl.this.mIsMapMiddleViewHidden = true;
                                        if (CQDetailPageControllerImpl.this.mOwner.c() != null) {
                                            CQDetailPageControllerImpl.this.mOwner.c().setVisibility(8);
                                            break;
                                        }
                                        break;
                                    case 2:
                                        CQDetailPageControllerImpl.this.mDetailLayerState = DetailLayerState.COLLAPSED;
                                        CQDetailPageControllerImpl.this.mIsMapMiddleViewHidden = false;
                                        if (CQDetailPageControllerImpl.this.mOwner.c() != null) {
                                            CQDetailPageControllerImpl.this.mOwner.c().setVisibility(0);
                                        }
                                        if (!(CQDetailPageControllerImpl.this.mMapPointTipView instanceof PoiDetailViewForCQ)) {
                                            if (!TipLogHelper.a(CQDetailPageControllerImpl.this.mPoi)) {
                                                CQDetailPageControllerImpl.this.mPoi;
                                                CQDetailPageControllerImpl.this.logOnTipShow();
                                                break;
                                            }
                                        }
                                        CQDetailPageControllerImpl.this.mPoi;
                                        CQDetailPageControllerImpl.this.logOnTipShow();
                                }
                                CQDetailPageControllerImpl.this.mCurState = optString2;
                                return;
                            }
                            return;
                        }
                        if (!optString.equals("detailStateWillChange")) {
                            if (optString.equals("tipClose")) {
                                CQDetailPageControllerImpl.this.mOwner.a.finish();
                            } else if (optString.equals("tipPoiDetailData")) {
                                int optInt2 = jSONObject2.optInt(H5Location.REQUEST_TYPE, 0);
                                String optString3 = jSONObject2.optString("poiDetailData", "");
                                if (optInt2 != 0) {
                                    if (!TextUtils.isEmpty(optString3)) {
                                        JSONObject jSONObject3 = new JSONObject(optString3);
                                        if (optInt2 == 1) {
                                            new ekq();
                                            CQDetailPageControllerImpl.this.mPoi = ekq.a(jSONObject3).a.get(0);
                                            CQDetailPageControllerImpl.this.mOwner.a.a(CQDetailPageControllerImpl.this.mPoi);
                                            return;
                                        }
                                        if (optInt2 == 2) {
                                            new awf();
                                            awg a2 = awf.a(jSONObject);
                                            if (!TextUtils.isEmpty(a2.c)) {
                                                CQDetailPageControllerImpl.this.mPoi.setName(a2.c);
                                            }
                                            CQDetailPageControllerImpl.this.mPoi.setAdCode(a2.f);
                                            CQDetailPageControllerImpl.this.mPoi.setCityCode(a2.a);
                                        }
                                    }
                                }
                            } else if (optString.equals("tipChildClick")) {
                                String optString4 = jSONObject2.optString("childIndex");
                                if (!TextUtils.isEmpty(optString4)) {
                                    int parseInt = Integer.parseInt(optString4);
                                    ChildrenPoiData poiChildrenInfo = ((SearchPoi) CQDetailPageControllerImpl.this.mPoi.as(SearchPoi.class)).getPoiChildrenInfo();
                                    if (poiChildrenInfo != null && (poiChildrenInfo.poiList instanceof List) && poiChildrenInfo.poiList.size() > parseInt) {
                                        Object obj = ((List) poiChildrenInfo.poiList).get(parseInt);
                                        if (obj instanceof POI) {
                                            poi = (POI) obj;
                                        }
                                    }
                                    if (poi != null) {
                                        CQDetailPageControllerImpl.this.mOwner.a.getMapManager().getOverlayManager().clearAllFocus();
                                        SearchCQDetailPage searchCQDetailPage = CQDetailPageControllerImpl.this.mOwner.a;
                                        GeoPoint point = poi.getPoint();
                                        if (searchCQDetailPage.e != null) {
                                            byv byv = searchCQDetailPage.e;
                                            byv.a.clear();
                                            PointOverlayItem pointOverlayItem = new PointOverlayItem();
                                            pointOverlayItem.defaultTexture = byv.makeTextureParam(R.drawable.b_poi_hl, 0.5f, 0.87f);
                                            pointOverlayItem.coord = new Coord(point.getLongitude(), point.getLatitude());
                                            byv.a.addItem(pointOverlayItem);
                                            byv.b.a((GLGeoPoint) point);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }

        public final void a(int i) {
            if (CQDetailPageControllerImpl.this.mIsShowing) {
                int i2 = 0;
                CQDetailPageControllerImpl.this.mIsMapMiddleViewHidden = !CQDetailPageControllerImpl.this.mLayerManager.a(i) || CQDetailPageControllerImpl.this.mDetailLayerState == DetailLayerState.EXPAND;
                if (CQDetailPageControllerImpl.this.mOwner.c() != null) {
                    CQDetailPageControllerImpl.this.mOwner.c().setVisibility(CQDetailPageControllerImpl.this.mIsMapMiddleViewHidden ? 8 : 0);
                }
                CQDetailPageControllerImpl.this.doOffsetBG(i);
                CQDetailPageControllerImpl.this.mHandler.removeMessages(0);
                CQDetailPageControllerImpl.this.mHandler.sendEmptyMessageDelayed(0, 500);
                if (CQDetailPageControllerImpl.this.mDetailLayerState != DetailLayerState.EXPAND) {
                    if (CQDetailPageControllerImpl.this.mOwner.b() != null) {
                        i2 = CQDetailPageControllerImpl.this.mOwner.b().getHeight();
                    }
                    CQDetailPageControllerImpl.this.setMapTopViewTranslateY(CQDetailPageControllerImpl.this.getTopViewTranslateY(CQDetailPageControllerImpl.this.mScreenH, i, CQDetailPageControllerImpl.this.mLayerManager.d().d, i2));
                }
            }
        }
    };
    BaseCQDetailOwner mOwner = null;
    /* access modifiers changed from: private */
    public POI mPoi;
    private View mPoiDetailTipView;
    private ely mPoiTipView;
    private long mRemoveTipsStateSequence = 0;
    private Point mSavedScreenMapCenter = null;
    /* access modifiers changed from: private */
    public int mScreenH;
    /* access modifiers changed from: private */
    public long mShowSequence = 0;
    /* access modifiers changed from: private */
    public String mTargetState;
    private PoiDetailRequester mTipRequester;
    private com.autonavi.map.search.tip.SearchPoiTipView.a mTipsHeightChangeListener = new com.autonavi.map.search.tip.SearchPoiTipView.a() {
    };

    class TipRefreshCallback implements Callback<POI> {
        public void error(Throwable th, boolean z) {
        }

        private TipRefreshCallback() {
        }

        /* synthetic */ TipRefreshCallback(CQDetailPageControllerImpl cQDetailPageControllerImpl, byte b) {
            this();
        }

        public void callback(POI poi) {
            CQDetailPageControllerImpl.this.onTipRefreshCallback(poi);
        }
    }

    static class a extends Handler {
        private WeakReference<CQDetailPageControllerImpl> a;

        /* synthetic */ a(CQDetailPageControllerImpl cQDetailPageControllerImpl, byte b) {
            this(cQDetailPageControllerImpl);
        }

        private a(CQDetailPageControllerImpl cQDetailPageControllerImpl) {
            super(Looper.getMainLooper());
            this.a = new WeakReference<>(cQDetailPageControllerImpl);
        }

        public final void handleMessage(Message message) {
            if (this.a != null && this.a.get() != null && ((CQDetailPageControllerImpl) this.a.get()).mOwner != null && ((CQDetailPageControllerImpl) this.a.get()).mOwner.a != null && ((CQDetailPageControllerImpl) this.a.get()).mOwner.a.isAlive()) {
                switch (message.what) {
                    case 0:
                        ((CQDetailPageControllerImpl) this.a.get()).doSlideMonitor();
                        return;
                    case 1:
                        if (message.obj != null && (message.obj instanceof GLGeoPoint)) {
                            ((CQDetailPageControllerImpl) this.a.get()).doAnimateTo((GLGeoPoint) message.obj);
                            return;
                        }
                    case 2:
                        ((CQDetailPageControllerImpl) this.a.get()).doDispatchSlidedEvent();
                        return;
                    case 3:
                        if (((CQDetailPageControllerImpl) this.a.get()).mOwner.d() != null) {
                            ((CQDetailPageControllerImpl) this.a.get()).mOwner.d().requestLayout();
                            return;
                        }
                        break;
                    case 4:
                        if (((CQDetailPageControllerImpl) this.a.get()).validToAdjustMark() && ((CQDetailPageControllerImpl) this.a.get()).mPoi != null) {
                            byj byj = ((CQDetailPageControllerImpl) this.a.get()).mOwner.a.c;
                            if (byj instanceof byj) {
                                ((byk) byj).a(((CQDetailPageControllerImpl) this.a.get()).mPoi.getPoint());
                                break;
                            }
                        }
                        break;
                }
            }
        }
    }

    private float getMiddleViewAlpha(int i, int i2, int i3) {
        int i4 = (int) (((double) (i - i3)) * 0.75d);
        int i5 = i4 + i3;
        return i2 < i5 ? 1.0f - (((float) (i2 - i3)) / ((float) i4)) : i2 >= i5 ? 0.0f : 1.0f;
    }

    public int getCQTopOffset() {
        return 39;
    }

    public boolean isMapPointRequestOutter() {
        return true;
    }

    private void recoverScreenMapCenter() {
        bty mapView = this.mOwner.a.getMapView();
        Point screenMapCenter = getScreenMapCenter();
        if (mapView != null && screenMapCenter != null) {
            mapView.b(screenMapCenter.x, screenMapCenter.y);
        }
    }

    public CQDetailPageControllerImpl() {
    }

    public CQDetailPageControllerImpl(BaseCQDetailOwner baseCQDetailOwner) {
        init(baseCQDetailOwner);
    }

    public void init(BaseCQDetailOwner baseCQDetailOwner) {
        this.mOwner = baseCQDetailOwner;
        this.mScreenH = ags.a(baseCQDetailOwner.a.getContext()).height();
    }

    private void processWillChangeFullState() {
        if (this.mHeaderMapLevel != -1.0f) {
            animateToMapVision(this.mHeaderMapLevel);
        }
    }

    /* access modifiers changed from: private */
    public void processWillChangeTipsState() {
        animateToMapVision(this.mOldLevel);
    }

    public void showCQLayer(POI poi, Object obj, boolean z, boolean z2) {
        showCQLayer(poi, obj, z, false, z2);
    }

    public void showCQLayer(POI poi, Object obj, boolean z, boolean z2, boolean z3) {
        SearchPoiIndicatorView searchPoiIndicatorView;
        if (poi != null && obj != null && this.mOwner.a != null && this.mOwner.a() != null) {
            this.mNeedAddGpsHeight = this.mOwner.a.b();
            updateTipType(obj);
            if (!z3) {
                onFinish();
            }
            this.mIsLayerHidden = false;
            this.mIsShowing = true;
            this.mPoi = poi;
            this.mShowSequence = SystemClock.elapsedRealtime();
            if (this.mOwner != null) {
                cfe.a((IMapPage) this.mOwner.a);
            }
            if (this.mPoi.getPoiExtra() == null || !this.mPoi.getPoiExtra().containsKey(IOverlayManager.POI_EXTRA_IS_SCENIC)) {
                this.mIsScenic = false;
            } else {
                this.mIsScenic = true;
            }
            if (this.mPoi.getPoiExtra() == null || (!this.mPoi.getPoiExtra().containsKey(IOverlayManager.POI_EXTRA_FROM_FAV_ON_MAP) && !this.mPoi.getPoiExtra().containsKey(IOverlayManager.POI_EXTRA_FROM_FAV))) {
                this.mIsFavWhenShow = false;
            } else {
                this.mIsFavWhenShow = true;
            }
            if (this.mLayerManager == null) {
                this.mLayerManager = new cat();
            }
            this.mDataHelper = new bxw(poi);
            if (this.mOldLevel == -1.0f) {
                updateOldMapLevel();
            }
            PageBundle initData = initData(poi, obj);
            if (initData != null) {
                if (obj instanceof SearchPoiTipWrapper) {
                    initData.putInt("key_tip_request_type", 1);
                    initData.putInt("key_tip_type", 0);
                    if (!(this.mMapPointTipView instanceof PoiDetailViewForCQ)) {
                        searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_press_short");
                    } else {
                        searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_default");
                    }
                    ((SearchPoiTipWrapper) obj).a(null, this.mTipsHeightChangeListener);
                } else {
                    searchPoiIndicatorView = null;
                }
                if (this.mMapPointTipView instanceof AbstractPoiDetailView) {
                    initData.putInt("key_tip_request_type", 2);
                    initData.putInt("key_tip_type", 1);
                    searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_press_long");
                    initData.putString("point_type", "2");
                    ((AbstractPoiDetailView) this.mMapPointTipView).setOnSetPoiListener(new OnSetPoiListener() {
                        public final void onSetPoi(POI poi) {
                            if ((CQDetailPageControllerImpl.this.mMapPointTipView instanceof AbstractPoiDetailView) && poi != null && CQDetailPageControllerImpl.this.mIsShowing) {
                                boolean z = !TextUtils.isEmpty(poi.getAddr()) && (CQDetailPageControllerImpl.this.mPoi == null || TextUtils.isEmpty(CQDetailPageControllerImpl.this.mPoi.getAddr()));
                                if (!DriveUtil.MAP_PLACE_DES_3.equals(poi.getName()) || z) {
                                    if ((poi == null || !poi.getPoiExtra().containsKey("isWhole") || !((Boolean) poi.getPoiExtra().get("isWhole")).booleanValue()) && CQDetailPageControllerImpl.this.mDataHelper != null) {
                                        CQDetailPageControllerImpl.this.mDataHelper;
                                        bxw.a(poi);
                                        poi.getPoiExtra().put("isWhole", Boolean.TRUE);
                                        CQDetailPageControllerImpl.this.mPoi = poi;
                                        if (CQDetailPageControllerImpl.this.mIndicatorView instanceof SearchPoiIndicatorView) {
                                            ((SearchPoiIndicatorView) CQDetailPageControllerImpl.this.mIndicatorView).updatePoiData(null, (SearchPoi) poi.as(SearchPoi.class), -1);
                                        }
                                    } else {
                                        return;
                                    }
                                }
                                CQDetailPageControllerImpl.this.doDispatchSlidedEvent();
                            }
                        }
                    });
                }
                if (this.mMapPointTipView instanceof AbstractGpsTipView) {
                    initData.putInt("key_tip_request_type", 2);
                    initData.putInt("key_tip_type", 2);
                    searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_my_position");
                    ((AbstractGpsTipView) this.mMapPointTipView).setOnRequestCallbackListener(new OnRequestCallbackListener() {
                        public final void onRequestCallback(POI poi) {
                            if ((CQDetailPageControllerImpl.this.mMapPointTipView instanceof AbstractGpsTipView) && poi != null && CQDetailPageControllerImpl.this.mIsShowing) {
                                if ((poi == null || !poi.getPoiExtra().containsKey("isWhole") || !((Boolean) poi.getPoiExtra().get("isWhole")).booleanValue()) && CQDetailPageControllerImpl.this.mDataHelper != null) {
                                    CQDetailPageControllerImpl.this.mDataHelper;
                                    bxw.a(poi);
                                    poi.getPoiExtra().put("isWhole", Boolean.TRUE);
                                    CQDetailPageControllerImpl.this.mPoi = poi;
                                }
                            }
                        }

                        public final long getShowSequence() {
                            return CQDetailPageControllerImpl.this.mShowSequence;
                        }

                        public final long getFinishSequence() {
                            return CQDetailPageControllerImpl.this.mFinishSequence;
                        }
                    });
                }
                SearchPoiIndicatorView searchPoiIndicatorView2 = searchPoiIndicatorView == null ? new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_default") : searchPoiIndicatorView;
                this.mIndicatorView = searchPoiIndicatorView2;
                searchPoiIndicatorView2.updatePoiData(null, (SearchPoi) poi.as(SearchPoi.class), -1);
                initData.putInt("key_tip_from", 1);
                JSONObject b = bnx.b(poi);
                SearchCQDetailPage searchCQDetailPage = this.mOwner.a;
                StringBuilder sb = new StringBuilder();
                if (searchCQDetailPage.g != null && !searchCQDetailPage.g.isEmpty()) {
                    for (Long l : searchCQDetailPage.g) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(l.toString());
                        sb2.append(",");
                        sb.append(sb2.toString());
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));
                }
                initData.putString("key_subway_activeid", sb.toString());
                if (this.mPoi.getPoiExtra() != null && this.mPoi.getPoiExtra().containsKey("titleName")) {
                    try {
                        b.put("titleName", (String) this.mPoi.getPoiExtra().get("titleName"));
                    } catch (JSONException unused) {
                    }
                }
                initData.putObject("key_tip_poi", b);
                addDetailView(initData, this.mMapPointTipView, searchPoiIndicatorView2, z, z3);
                cancelAllTipDetailRequest();
                if ((this.mPoiTipView != null && !isMapPointRequestOutter()) || z2) {
                    this.mTipRequester = new PoiDetailRequester(this.mOwner.a.getContext());
                    new TipRefreshCallback(this, 0);
                }
                if (this.mLayerManager.j() == PanelState.EXPANDED) {
                    this.mLayerManager.a(false);
                }
                showLayer();
                BaseCQDetailOwner baseCQDetailOwner = this.mOwner;
                if (baseCQDetailOwner.d() != null) {
                    baseCQDetailOwner.d().setVisibility(4);
                }
            }
        }
    }

    private void updateOldMapLevel() {
        bty mapView = this.mOwner.a.getMapView();
        if (mapView != null) {
            this.mOldLevel = mapView.v();
        }
    }

    private void unlockGps() {
        if (this.mOwner.a != null && this.mOwner.a.getSuspendManager() != null && this.mOwner.a.getSuspendManager().d() != null) {
            this.mOwner.a.getSuspendManager().d().f();
        }
    }

    private void cancelAllTipDetailRequest() {
        if (this.mTipRequester != null) {
            this.mTipRequester.a();
        }
    }

    private PageBundle initData(POI poi, Object obj) {
        PageBundle pageBundle;
        if (obj instanceof ely) {
            ely ely = (ely) obj;
            this.mMapPointTipView = ely.getView();
            this.mPoiTipView = ely;
            pageBundle = eks.a(poi, ely);
        } else if (obj instanceof View) {
            View view = (View) obj;
            this.mMapPointTipView = view;
            this.mPoiTipView = null;
            pageBundle = eks.a(poi, view);
        } else {
            MapBasePage.LogCQ(String.format("CQLayerController showCQLayer. type of poiTipView is wrong", new Object[0]));
            return null;
        }
        if (this.mMapPointTipView.getVisibility() != 0) {
            this.mMapPointTipView.setVisibility(0);
        }
        if ((poi instanceof GpsPOI) || (this.mMapPointTipView instanceof AbstractGpsTipView)) {
            pageBundle.putString("point_type", "1");
            if (TextUtils.isEmpty(poi.getName())) {
                poi.setName("我的位置");
            }
        }
        resetDetailViewListener();
        return pageBundle;
    }

    private void resetDetailViewListener() {
        if (this.mMapPointTipView != null) {
            if (this.mMapPointTipView instanceof AbstractPoiDetailView) {
                ((AbstractPoiDetailView) this.mMapPointTipView).setOnSetPoiListener(null);
            }
            if (this.mMapPointTipView instanceof AbstractGpsTipView) {
                ((AbstractGpsTipView) this.mMapPointTipView).setOnRequestCallbackListener(null);
            }
        }
    }

    private void addDetailView(PageBundle pageBundle, View view, View view2, boolean z, boolean z2) {
        if (this.mPoi != null && this.mLayerManager != null && this.mDataHelper != null) {
            if (this.mPoiDetailTipView != null && !z2) {
                destroyPoiDetailLayer();
            }
            this.mLayerManager.a(this.mOnTipDetailStateChangeListener);
            this.mTargetState = z ? "full" : ModulePoi.TIPS;
            this.mDetailLayerState = z ? DetailLayerState.EXPAND : DetailLayerState.COLLAPSED;
            if (view2 instanceof SearchPoiIndicatorView) {
                this.mDataHelper.b = ((SearchPoiIndicatorView) view2).getIndicatorInfo();
            }
            this.mLayerManager.b(this.mDataHelper.a(pageBundle, this.mOwner.a, z, false));
            if (!z2) {
                this.mPoiDetailTipView = this.mLayerManager.a(this.mOwner.a, this.mOwner.a(), z, pageBundle);
                setDataHelperToAjxView();
                attachTipToDetailView(view, view2);
            } else {
                refreshAjxView(pageBundle, false, true, null, this.mMapPointTipView instanceof AbstractPoiDetailView ? "2" : "");
            }
            setBundleToJsMethod(pageBundle);
            this.mLayerManager.a((BackCallback) new BackCallback() {
                public final void back(Object obj, String str) {
                    AMapLog.d("PoiDetail", "js back");
                    if (CQDetailPageControllerImpl.this.doBackAction(2) == ON_BACK_TYPE.TYPE_NORMAL && CQDetailPageControllerImpl.this.mLayerManager != null && CQDetailPageControllerImpl.this.mLayerManager.j() == PanelState.EXPANDED) {
                        CQDetailPageControllerImpl.this.mLayerManager.a(true);
                        CQDetailPageControllerImpl.this.mTargetState = ModulePoi.TIPS;
                        CQDetailPageControllerImpl.this.animateToMapVision(CQDetailPageControllerImpl.this.mOldLevel);
                    }
                }
            });
            addLogListener();
        }
    }

    private void setDataHelperToAjxView() {
        new AjxPageResultExecutor() {
            public final void onFragmentResult(AbstractBasePage abstractBasePage, int i, ResultType resultType, PageBundle pageBundle, JsAdapter jsAdapter) {
                if (CQDetailPageControllerImpl.this.mDataHelper != null) {
                    CQDetailPageControllerImpl.this.mDataHelper.onFragmentResult(abstractBasePage, i, resultType, pageBundle, jsAdapter);
                }
            }
        };
    }

    private void attachTipToDetailView(View view, View view2) {
        if (view != null && view2 != null && this.mLayerManager != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            ViewGroup viewGroup2 = (ViewGroup) view2.getParent();
            if (viewGroup2 != null) {
                viewGroup2.removeAllViews();
            }
            this.mLayerManager.a(view, view2);
        }
    }

    private void setBundleToJsMethod(PageBundle pageBundle) {
        if (this.mLayerManager != null && this.mPoi != null) {
            this.mLayerManager.a(pageBundle);
            if (this.mLayerManager.h() == null) {
                this.mLayerManager.b(pageBundle);
            } else {
                this.mLayerManager.h().setBundle(pageBundle);
            }
        }
    }

    public void onTipRefreshCallback(POI poi) {
        if (this.mOwner.a != null && this.mOwner.a.isAlive() && this.mIsShowing && poi != null && this.mPoiTipView != null && this.mLayerManager != null && this.mDataHelper != null) {
            this.mPoi = poi;
            if (isMapPointRequestOutter() && (this.mPoiTipView instanceof SearchPoiTipWrapper)) {
                SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                if (searchPoi.getTemplateData() == null && searchPoi.getTemplateDataMap() == null) {
                    bxz.a(this.mOwner.a.getContext(), searchPoi);
                }
            }
            this.mPoiTipView.initData(null, poi, -1);
            ((SearchPoiIndicatorView) this.mIndicatorView).updatePoiData(null, (SearchPoi) poi.as(SearchPoi.class), -1);
            if (this.mLayerManager.j() == PanelState.COLLAPSED) {
                doOffsetBG(this.mLayerManager.d().d);
            }
            bxz.a(new PageBundle(), SuperId.getInstance(), null, poi, "poitip", 0);
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public void onMapPointRequestReturnNull() {
        if (this.mPoi != null) {
            bxz.a(new PageBundle(), SuperId.getInstance(), null, this.mPoi, "poitip", 0);
        }
    }

    private void refreshAjxView(PageBundle pageBundle, boolean z, boolean z2, Boolean bool, String str) {
        if (pageBundle != null && this.mLayerManager != null && this.mDataHelper != null) {
            boolean z3 = bool != null ? bool.booleanValue() : this.mLayerManager.j() == PanelState.EXPANDED;
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString("point_type", str);
            }
            if (z) {
                pageBundle.putString("toggle", "1");
            }
            if (z2) {
                pageBundle.putString("is_whole", "1");
            }
            setBundleToJsMethod(pageBundle);
            if (this.mIndicatorView instanceof SearchPoiIndicatorView) {
                this.mDataHelper.b = ((SearchPoiIndicatorView) this.mIndicatorView).getIndicatorInfo();
            }
            this.mLayerManager.b(this.mDataHelper.a(pageBundle, this.mOwner.a, z3, false));
            this.mLayerManager.i();
        }
    }

    private void onFinish() {
        this.mFinishSequence = SystemClock.elapsedRealtime();
        this.mIsShowing = false;
        this.mIsScenic = false;
        this.mIsFavWhenShow = false;
        this.mIsLayerHidden = true;
        resetDetailViewListener();
        cancelAllTipDetailRequest();
        destroyPoiDetailLayer();
        this.mTipRequester = null;
        this.mDataHelper = null;
        this.mMapPointTipView = null;
        this.mPoiTipView = null;
        this.mSavedScreenMapCenter = null;
        this.mRemoveTipsStateSequence = 0;
    }

    public void onResume(boolean z) {
        if (!this.mIsLayerHidden) {
            if (this.mLayerManager != null) {
                if (this.mDataHelper != null) {
                    this.mLayerManager.a(z, this.mDataHelper.a);
                    this.mDataHelper.a = null;
                } else {
                    this.mLayerManager.a(z, (String) null);
                }
            }
            this.mHandler.post(new Runnable() {
                public final void run() {
                    CQDetailPageControllerImpl.this.recoverSavedScreenMapCenter();
                }
            });
        }
    }

    public void onPause() {
        if (!this.mIsLayerHidden) {
            if (this.mLayerManager != null) {
                this.mLayerManager.a();
            }
            resetMapCenter(false);
        }
    }

    public void onDestroy() {
        onFinish();
        this.mNeedAddGpsHeight = false;
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
    }

    private void destroyPoiDetailLayer() {
        this.mIsMapMiddleViewHidden = false;
        this.mOldLevel = -1.0f;
        if (!(this.mPoiDetailTipView == null || this.mOwner == null)) {
            ViewGroup a2 = this.mOwner.a();
            if (a2 != null) {
                a2.removeView(this.mPoiDetailTipView);
            }
        }
        if (this.mLayerManager != null) {
            this.mLayerManager.e();
            this.mLayerManager.b();
        }
    }

    public void onPageCover() {
        if (!this.mIsLayerHidden && this.mIsMapMiddleViewHidden && this.mOwner.c() != null) {
            this.mOwner.c().setVisibility(0);
        }
    }

    public void onPageAppear() {
        if (!this.mIsLayerHidden && this.mIsMapMiddleViewHidden && this.mOwner.c() != null) {
            this.mOwner.c().setVisibility(8);
        }
    }

    public ON_BACK_TYPE onBackPressed() {
        return onBackPressed(1);
    }

    private ON_BACK_TYPE onBackPressed(int i) {
        if (this.mLayerManager == null || this.mLayerManager.j() != PanelState.EXPANDED) {
            recoverScreenMapCenter();
            return doBackAction(i);
        }
        this.mLayerManager.c();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    /* access modifiers changed from: private */
    public ON_BACK_TYPE doBackAction(int i) {
        if (this.mLayerManager == null) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        }
        if (this.mLayerManager.j() == PanelState.DRAGGING) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        if (shouldCollapseWhenBack()) {
            this.mLayerManager.a(true);
            this.mTargetState = ModulePoi.TIPS;
            animateToMapVision(this.mOldLevel);
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (this.mLayerManager.j() != PanelState.EXPANDED || this.mRemoveTipsStateSequence <= 0 || this.mRemoveTipsStateSequence != this.mShowSequence) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        } else {
            dismissCQLayer(true, false, i);
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    private boolean shouldCollapseWhenBack() {
        return this.mLayerManager != null && this.mLayerManager.j() == PanelState.EXPANDED && (this.mRemoveTipsStateSequence == 0 || this.mRemoveTipsStateSequence != this.mShowSequence);
    }

    public void onFragmentResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (this.mLayerManager != null && this.mDataHelper != null && this.mOwner.a != null) {
            JsAdapter h = this.mLayerManager.h();
            if (h != null) {
                this.mDataHelper.onFragmentResult(this.mOwner.a, i, resultType, pageBundle, h);
            }
        }
    }

    public void onMapSurfaceChanged(int i, int i2) {
        if (!this.mIsLayerHidden) {
            recoverSavedScreenMapCenter();
        }
    }

    /* access modifiers changed from: private */
    public void recoverSavedScreenMapCenter() {
        bty mapView = this.mOwner.a.getMapView();
        Point point = this.mSavedScreenMapCenter;
        if (mapView != null && point != null) {
            mapView.b(point.x, point.y);
            updateMapCenter(false, this.mPoi.getPoint());
            if ("full".equals(this.mTargetState)) {
                mapView.d(this.mHeaderMapLevel);
            }
        }
    }

    public void recoverFocusCenter() {
        if (this.mPoi != null) {
            this.mOwner.a.getMapView().a((GLGeoPoint) this.mPoi.getPoint());
        }
    }

    public boolean isScenic() {
        return this.mIsScenic;
    }

    public boolean isFavWhenShow() {
        return this.mIsFavWhenShow;
    }

    public void showLayer() {
        if (this.mLayerManager != null) {
            this.mLayerManager.k();
        }
    }

    public void collapseCQLayer() {
        if (this.mLayerManager != null && (this.mLayerManager.j() == PanelState.EXPANDED || this.mLayerManager.j() == PanelState.DRAGGING)) {
            if (this.mRemoveTipsStateSequence <= 0 || this.mRemoveTipsStateSequence != this.mShowSequence) {
                this.mLayerManager.a(true);
            } else {
                dismissCQLayer(false);
            }
        }
    }

    public void expendCQLayer() {
        this.mLayerManager.f();
    }

    public void hideCQLayer() {
        dismissCQLayer(false, false, 0);
    }

    public void setLayerVisibility(boolean z) {
        int i = 0;
        if (this.mPoiDetailTipView != null) {
            this.mPoiDetailTipView.setVisibility(z ? 0 : 8);
            this.mIsLayerHidden = !z;
        }
        if (z && "full".equals(this.mTargetState)) {
            if (this.mOwner.b() != null) {
                i = this.mOwner.b().getHeight();
            }
            setMapTopViewTranslateY(-i);
        }
    }

    public void resetHeaderTranslation() {
        setMapTopViewTranslateY(0);
    }

    public boolean dismissCQLayer(boolean z) {
        return dismissCQLayer(true, z, 0);
    }

    private boolean dismissCQLayer(boolean z, boolean z2, int i) {
        if (this.mLayerManager != null) {
            if (!z2 || !(this.mLayerManager.j() == PanelState.EXPANDED || this.mLayerManager.j() == PanelState.DRAGGING)) {
                boolean z3 = isShowing() && z;
                this.mIsMapMiddleViewHidden = false;
                this.mLayerManager.l();
                if (z) {
                    onFinish();
                }
                if (!z3) {
                    return z3;
                }
                BaseCQDetailOwner baseCQDetailOwner = this.mOwner;
                if (baseCQDetailOwner.d() != null) {
                    baseCQDetailOwner.d().setVisibility(0);
                }
                if (baseCQDetailOwner.a == null || baseCQDetailOwner.a.c == null) {
                    return z3;
                }
                baseCQDetailOwner.a.c.a();
                return z3;
            }
            this.mRemoveTipsStateSequence = this.mShowSequence;
        }
        return false;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public boolean isLayerShowing() {
        return this.mLayerManager != null && this.mLayerManager.m();
    }

    public View getMapPointTipView() {
        return this.mMapPointTipView;
    }

    public void doDispatchSlidedEvent() {
        if (this.mLayerManager != null && this.mLayerManager.m() && this.mMapPointTipView != null && this.mIndicatorView != null) {
            this.mLayerManager.d().d = (ccd.a(this.mMapPointTipView) - agn.a(this.mMapPointTipView.getContext(), 39.0f)) + ccd.a(this.mIndicatorView);
            doOffsetBG(this.mLayerManager.d().d);
        }
    }

    public DetailLayerState getDetailLayerState() {
        return this.mDetailLayerState;
    }

    public View getMapMiddleView() {
        return this.mOwner.c();
    }

    public boolean isMapMiddleViewHidden() {
        return this.mIsMapMiddleViewHidden;
    }

    public POI getCurPoi() {
        return this.mPoi;
    }

    /* access modifiers changed from: private */
    public boolean animateToMapVision(float f) {
        if (TextUtils.equals(this.mTargetState, "full")) {
            updateOldMapLevel();
        } else if (TextUtils.equals(this.mTargetState, ModulePoi.TIPS)) {
            this.mOldLevel = -1.0f;
        }
        if (f != -1.0f) {
            bty mapView = this.mOwner.a.getMapView();
            if (mapView != null) {
                mapView.d(f);
            }
        }
        if (this.mPoi == null) {
            return false;
        }
        animateTo(this.mPoi.getPoint());
        return true;
    }

    /* access modifiers changed from: private */
    public void doAnimateTo(GLGeoPoint gLGeoPoint) {
        if (this.mOwner.a != null && this.mOwner.a.isAlive()) {
            updateMapCenter(true, gLGeoPoint);
        }
    }

    public void animateTo(GLGeoPoint gLGeoPoint) {
        this.mHandler.removeMessages(1);
        Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 1;
        obtainMessage.obj = gLGeoPoint;
        this.mHandler.sendMessageDelayed(obtainMessage, 10);
    }

    private void updateMapCenter(boolean z, GLGeoPoint gLGeoPoint) {
        bty mapView = this.mOwner.a.getMapView();
        if (mapView != null) {
            if (this.mLayerManager.j() == PanelState.HIDDEN) {
                resetMapCenter(true);
                return;
            }
            Point focusMapCenter = getFocusMapCenter();
            this.mSavedScreenMapCenter = focusMapCenter;
            if (z && gLGeoPoint != null) {
                mapView.a(200, gLGeoPoint, focusMapCenter, true);
                mapView.S();
            } else if (this.mMapPointTipView instanceof AbstractPoiDetailView) {
                if (gLGeoPoint == null) {
                    gLGeoPoint = mapView.c(focusMapCenter.x, focusMapCenter.y);
                }
                mapView.a(200, gLGeoPoint, focusMapCenter, true);
                mapView.S();
            } else {
                mapView.b(focusMapCenter.x, focusMapCenter.y);
                if (gLGeoPoint != null) {
                    mapView.a(gLGeoPoint);
                } else if (validToAdjustMark()) {
                    this.mHandler.removeMessages(4);
                    this.mHandler.sendEmptyMessageDelayed(4, 100);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean validToAdjustMark() {
        return this.mPoi != null && (this.mMapPointTipView instanceof AbstractPoiDetailView);
    }

    public Point getScreenMapCenter() {
        int i;
        int bottom = this.mOwner.b() != null ? this.mOwner.b().getBottom() : 0;
        if (this.mOwner.d() != null) {
            i = this.mOwner.d().getTop();
        } else {
            i = ags.a(this.mOwner.a.getContext()).height();
        }
        int i2 = ((i - bottom) / 2) + bottom;
        if (TextUtils.equals(this.mTargetState, "full")) {
            i2 = (this.mHeadHeight / 2) + 20;
        } else if (TextUtils.equals(this.mTargetState, ModulePoi.TIPS)) {
            i2 = ags.a(this.mOwner.a.getContext()).height() / 2;
        }
        if (i2 == 0) {
            i2 = ags.a(this.mOwner.a.getContext()).height() / 2;
        }
        return new Point(ags.a(this.mOwner.a.getContext()).width() / 2, i2);
    }

    public Point getFocusMapCenter() {
        int i;
        int bottom = this.mOwner.b() != null ? this.mOwner.b().getBottom() : 0;
        if (this.mOwner.d() != null) {
            i = this.mOwner.d().getTop();
        } else {
            i = ags.a(this.mOwner.a.getContext()).height();
        }
        int i2 = ((i - bottom) / 2) + bottom;
        if (TextUtils.equals(this.mTargetState, "full")) {
            i2 = (this.mHeadHeight / 2) + 20;
        } else if (TextUtils.equals(this.mTargetState, ModulePoi.TIPS)) {
            i2 = ((ags.a(this.mOwner.a.getContext()).height() - this.mLayerManager.d().d) + this.mOwner.b().getBottom()) / 2;
        }
        return new Point(ags.a(this.mOwner.a.getContext()).width() / 2, i2);
    }

    private void resetMapCenter(boolean z) {
        if (this.mSavedScreenMapCenter != null) {
            bty mapView = this.mOwner.a.getMapView();
            Point defaultMapCenter = getDefaultMapCenter();
            if (mapView != null) {
                mapView.b(defaultMapCenter.x, defaultMapCenter.y);
            }
            if (z) {
                this.mSavedScreenMapCenter = null;
            }
        }
    }

    private void resetMapWidgetOffset() {
        if (this.mMapPointTipView != null && this.mIndicatorView != null) {
            int a2 = (ccd.a(this.mMapPointTipView) - agn.a(this.mMapPointTipView.getContext(), 39.0f)) + ccd.a(this.mIndicatorView);
            this.mLayerManager.d().d = a2;
            doOffsetBG(a2);
        }
    }

    private Point getDefaultMapCenter() {
        return getFocusMapCenter();
    }

    /* access modifiers changed from: private */
    public void doSlideMonitor() {
        if (this.mOwner.a != null && this.mOwner.a.isAlive()) {
            if (this.mLayerManager == null) {
                doResetBG();
                return;
            }
            if (!this.mLayerManager.m()) {
                doResetBG();
            }
        }
    }

    private void handleSuspendSliding(int i, int i2, int i3, int i4, boolean z) {
        if (this.mLayerManager != null) {
            caq d = this.mLayerManager.d();
            if (d != null && d.d > 0 && d.d < i) {
                int i5 = d.d;
                int height = this.mOwner.b() != null ? this.mOwner.b().getHeight() : 0;
                switch (MiddleViewSlideType.Dismiss) {
                    case Alpha_Press:
                    case Alpha:
                        i += height;
                        setMiddleViewAlpha(getMiddleViewAlpha(i, i2, i5));
                        break;
                }
                setMapTopViewTranslateY(getTopViewTranslateY(i, i2, i5, height));
            }
        }
    }

    private void setMiddleViewAlpha(float f) {
        if (this.mOwner.c() != null) {
            this.mOwner.c().setAlpha(f);
        }
    }

    /* access modifiers changed from: private */
    public int getTopViewTranslateY(int i, int i2, int i3, int i4) {
        int i5 = 0;
        if (i4 <= 0) {
            return 0;
        }
        double d = (double) (i - i3);
        int i6 = (int) (0.15d * d);
        int i7 = (int) (d * 0.7d);
        int i8 = i7 + i6;
        if ((this.mLayerManager == null || this.mLayerManager.j() != PanelState.COLLAPSED) && (this.mLayerManager == null || this.mLayerManager.j() != PanelState.HIDDEN)) {
            if (this.mLayerManager != null && this.mLayerManager.j() == PanelState.EXPANDED) {
                i5 = -i4;
            } else if (i2 > i7 && i2 < i8) {
                i5 = ((i7 - i2) * i4) / i6;
            } else if (i2 >= i8) {
                i5 = -i4;
            }
        }
        return i5;
    }

    /* access modifiers changed from: private */
    public void setMapTopViewTranslateY(int i) {
        if (this.mOwner.b() != null) {
            this.mOwner.b().setTranslationY((float) i);
        }
    }

    private void resetMapBottomLayoutParams() {
        MapBasePage.LogCQ(String.format("CQLayerController resetMapBottomLayoutParams", new Object[0]));
        if (this.mOwner.d() != null) {
            LayoutParams layoutParams = this.mOwner.d().getLayoutParams();
            if (layoutParams.height != -2) {
                layoutParams.height = -2;
                this.mOwner.d().setLayoutParams(layoutParams);
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessage(3);
            }
        }
    }

    public void onOffsetBG(int i) {
        int i2;
        if (this.mOwner.d() != null) {
            caq d = this.mLayerManager.d();
            if ((MiddleViewSlideType.Dismiss == MiddleViewSlideType.Alpha_Press) || i <= d.d) {
                LayoutParams layoutParams = this.mOwner.d().getLayoutParams();
                if (layoutParams.height != i) {
                    layoutParams.height = i;
                    this.mOwner.d().setLayoutParams(layoutParams);
                    if (this.mOwner.a.b() && this.mNeedAddGpsHeight) {
                        i += agn.a(this.mOwner.a.getContext(), 18.0f);
                    }
                    if (!Stub.getMapWidgetManager().isNewHomePage()) {
                        i2 = i - agn.a(this.mOwner.a.getContext(), 38.0f);
                    } else {
                        i2 = i + agn.a(this.mOwner.a.getContext(), 38.0f);
                    }
                    if (this.mOwner.a.isAlive()) {
                        Stub.getMapWidgetManager().setContainerMargin(0, 0, 0, i2);
                    }
                    this.mNeedAddGpsHeight = false;
                }
            }
        }
    }

    public void onResetBG() {
        resetMapBottomLayoutParams();
    }

    /* access modifiers changed from: private */
    public void doOffsetBG(int i) {
        onOffsetBG(i);
    }

    private void doResetBG() {
        onResetBG();
    }

    private void addLogListener() {
        final boolean z = this.mMapPointTipView instanceof PoiDetailViewForCQ;
        final boolean z2 = this.mOwner.a instanceof SearchCQDetailPage;
        this.mLayerManager.a((defpackage.cap.a) new defpackage.cap.a() {
            public final void a() {
                new ccj(z, z2).b(CQDetailPageControllerImpl.this.mPoi);
            }
        });
    }

    private void updateTipType(Object obj) {
        if (obj instanceof SearchPoiTipWrapper) {
            this.mCurrentTipType = 2;
        } else if (obj instanceof PoiDetailViewForCQ) {
            this.mCurrentTipType = 4;
        } else {
            this.mCurrentTipType = 3;
        }
    }

    /* access modifiers changed from: private */
    public void logOnTipShow() {
        if (this.mPoi != null) {
            this.mPoi.getId();
        }
    }

    public String getState() {
        return this.mCurState;
    }
}
