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
import com.autonavi.bundle.searchresult.ajx.ModulePoi.b;
import com.autonavi.bundle.searchresult.ajx.ModulePoi.c;
import com.autonavi.bundle.searchresult.util.TipLogHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.BaseCQLayerOwner;
import com.autonavi.map.fragmentcontainer.page.BaseCQLayerOwner.MiddleViewSlideType;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController.DetailLayerState;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.IPoiDetailDelegate;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.PoiDetailDelegate;
import com.autonavi.map.search.fragment.SearchResultBasePage;
import com.autonavi.map.search.tip.SearchPoiTipWrapper;
import com.autonavi.map.search.tip.indicator.SearchPoiIndicatorView;
import com.autonavi.map.search.utils.PoiDetailRequester;
import com.autonavi.map.search.view.PoiDetailViewForCQ;
import com.autonavi.minimap.ajx3.Ajx3Page.AjxPageResultExecutor;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView.OnRequestCallbackListener;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.OnSetPoiListener;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@Keep
@SuppressFBWarnings({"BC_VACUOUS_INSTANCEOF"})
public class CQLayerControllerImpl implements defpackage.bra.a, ICQLayerController {
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
    private int mCurrentTipType;
    /* access modifiers changed from: private */
    public bxw mDataHelper;
    /* access modifiers changed from: private */
    public DetailLayerState mDetailLayerState = DetailLayerState.COLLAPSED;
    /* access modifiers changed from: private */
    public long mFinishSequence = 0;
    /* access modifiers changed from: private */
    public Handler mHandler = new a(this, 0);
    /* access modifiers changed from: private */
    public int mHeadHeight = 600;
    /* access modifiers changed from: private */
    public float mHeaderMapLevel = -1.0f;
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
    /* access modifiers changed from: private */
    public float mOldLevel = -1.0f;
    private b mOnRegisterInfoListener = new b() {
        public final void a(float f, float f2) {
            CQLayerControllerImpl.this.mHeadHeight = (int) f2;
            CQLayerControllerImpl.this.mHeaderMapLevel = f;
        }
    };
    private c mOnStateChangeListener = new c() {
        private boolean b;
        private boolean c;

        public final void a(String str, boolean z) {
            CQLayerControllerImpl.this.mHandler.removeMessages(0);
            if (CQLayerControllerImpl.this.mLayerManager != null) {
                char c2 = 65535;
                int hashCode = str.hashCode();
                if (hashCode != -1068259250) {
                    if (hashCode != 3154575) {
                        if (hashCode == 3560248 && str.equals(ModulePoi.TIPS)) {
                            c2 = 2;
                        }
                    } else if (str.equals("full")) {
                        c2 = 0;
                    }
                } else if (str.equals(ModulePoi.MOVE)) {
                    c2 = 1;
                }
                switch (c2) {
                    case 0:
                        if (!this.b && z) {
                            CQLayerControllerImpl.this.mTargetState = "full";
                            CQLayerControllerImpl.this.processWillChangeFullState();
                        }
                        CQLayerControllerImpl.this.mDetailLayerState = DetailLayerState.EXPAND;
                        CQLayerControllerImpl.this.mIsMapMiddleViewHidden = true;
                        if (CQLayerControllerImpl.this.mOwner.getMapMiddleView() != null) {
                            CQLayerControllerImpl.this.mOwner.getMapMiddleView().setVisibility(8);
                        }
                        CQLayerControllerImpl.this.setMapTopViewTranslateY(-(CQLayerControllerImpl.this.mOwner.getMapTopView() != null ? CQLayerControllerImpl.this.mOwner.getMapTopView().getHeight() : 0));
                        this.b = false;
                        this.c = false;
                        break;
                    case 1:
                        CQLayerControllerImpl.this.mIsMapMiddleViewHidden = true;
                        if (CQLayerControllerImpl.this.mOwner.getMapMiddleView() != null) {
                            CQLayerControllerImpl.this.mOwner.getMapMiddleView().setVisibility(8);
                            break;
                        }
                        break;
                    case 2:
                        if (!this.c && z) {
                            CQLayerControllerImpl.this.mTargetState = ModulePoi.TIPS;
                            CQLayerControllerImpl.this.processWillChangeTipsState();
                        }
                        CQLayerControllerImpl.this.mDetailLayerState = DetailLayerState.COLLAPSED;
                        CQLayerControllerImpl.this.mIsMapMiddleViewHidden = false;
                        if (CQLayerControllerImpl.this.mOwner.getMapMiddleView() != null) {
                            CQLayerControllerImpl.this.mOwner.getMapMiddleView().setVisibility(0);
                        }
                        CQLayerControllerImpl.this.setMapTopViewTranslateY(0);
                        this.b = false;
                        this.c = false;
                        if ((CQLayerControllerImpl.this.mMapPointTipView instanceof PoiDetailViewForCQ) || TipLogHelper.a(CQLayerControllerImpl.this.mPoi)) {
                            CQLayerControllerImpl.this.mPoi;
                        } else {
                            CQLayerControllerImpl.this.mPoi;
                        }
                        CQLayerControllerImpl.this.logOnTipShow();
                        break;
                }
                CQLayerControllerImpl.this.mOwner.onSlideEnd(!TextUtils.equals(str, ModulePoi.TIPS));
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0037  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void b(java.lang.String r3, boolean r4) {
            /*
                r2 = this;
                com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl r4 = com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl.this
                r4.mTargetState = r3
                int r4 = r3.hashCode()
                r0 = 3154575(0x30228f, float:4.420501E-39)
                r1 = 1
                if (r4 == r0) goto L_0x0020
                r0 = 3560248(0x365338, float:4.98897E-39)
                if (r4 == r0) goto L_0x0015
                goto L_0x002a
            L_0x0015:
                java.lang.String r4 = "tips"
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x002a
                r3 = 1
                goto L_0x002b
            L_0x0020:
                java.lang.String r4 = "full"
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x002a
                r3 = 0
                goto L_0x002b
            L_0x002a:
                r3 = -1
            L_0x002b:
                switch(r3) {
                    case 0: goto L_0x0037;
                    case 1: goto L_0x002f;
                    default: goto L_0x002e;
                }
            L_0x002e:
                goto L_0x003f
            L_0x002f:
                r2.c = r1
                com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl r3 = com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl.this
                r3.processWillChangeTipsState()
                goto L_0x003f
            L_0x0037:
                r2.b = r1
                com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl r3 = com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl.this
                r3.processWillChangeFullState()
                return
            L_0x003f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.search.inter.impl.CQLayerControllerImpl.AnonymousClass1.b(java.lang.String, boolean):void");
        }

        public final void a(int i, boolean z) {
            if (CQLayerControllerImpl.this.mIsShowing) {
                int i2 = 0;
                CQLayerControllerImpl.this.mIsMapMiddleViewHidden = !CQLayerControllerImpl.this.mLayerManager.a(i) || CQLayerControllerImpl.this.mDetailLayerState == DetailLayerState.EXPAND;
                if (CQLayerControllerImpl.this.mOwner.getMapMiddleView() != null) {
                    CQLayerControllerImpl.this.mOwner.getMapMiddleView().setVisibility(CQLayerControllerImpl.this.mIsMapMiddleViewHidden ? 8 : 0);
                }
                CQLayerControllerImpl.this.doOffsetBG(i);
                CQLayerControllerImpl.this.mHandler.removeMessages(0);
                CQLayerControllerImpl.this.mHandler.sendEmptyMessageDelayed(0, 500);
                if (CQLayerControllerImpl.this.mOwner.isFullScreen() && i > 0) {
                    CQLayerControllerImpl.this.mOwner.onSetFullScreen(false);
                }
                if (CQLayerControllerImpl.this.mDetailLayerState != DetailLayerState.EXPAND) {
                    if (CQLayerControllerImpl.this.mOwner.getMapTopView() != null) {
                        i2 = CQLayerControllerImpl.this.mOwner.getMapTopView().getHeight();
                    }
                    CQLayerControllerImpl.this.setMapTopViewTranslateY(CQLayerControllerImpl.this.getTopViewTranslateY(CQLayerControllerImpl.this.mScreenH, i, CQLayerControllerImpl.this.mLayerManager.d().d, i2));
                }
            }
        }
    };
    BaseCQLayerOwner mOwner = null;
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
    private cbr mTipChildClickListener = new cbr() {
    };
    private PoiDetailRequester mTipRequester;
    /* access modifiers changed from: private */
    public com.autonavi.map.search.tip.SearchPoiTipView.a mTipsHeightChangeListener = new com.autonavi.map.search.tip.SearchPoiTipView.a() {
    };

    class TipRefreshCallback implements Callback<POI> {
        public void error(Throwable th, boolean z) {
        }

        private TipRefreshCallback() {
        }

        /* synthetic */ TipRefreshCallback(CQLayerControllerImpl cQLayerControllerImpl, byte b) {
            this();
        }

        public void callback(POI poi) {
            CQLayerControllerImpl.this.onTipRefreshCallback(poi);
        }
    }

    static class a extends Handler {
        private WeakReference<CQLayerControllerImpl> a;

        /* synthetic */ a(CQLayerControllerImpl cQLayerControllerImpl, byte b) {
            this(cQLayerControllerImpl);
        }

        private a(CQLayerControllerImpl cQLayerControllerImpl) {
            super(Looper.getMainLooper());
            this.a = new WeakReference<>(cQLayerControllerImpl);
        }

        public final void handleMessage(Message message) {
            if (this.a != null && this.a.get() != null && ((CQLayerControllerImpl) this.a.get()).mOwner != null && ((CQLayerControllerImpl) this.a.get()).mOwner.getPage() != null && ((CQLayerControllerImpl) this.a.get()).mOwner.getPage().isAlive()) {
                switch (message.what) {
                    case 0:
                        ((CQLayerControllerImpl) this.a.get()).doSlideMonitor();
                        return;
                    case 1:
                        if (message.obj != null && (message.obj instanceof GLGeoPoint)) {
                            ((CQLayerControllerImpl) this.a.get()).doAnimateTo((GLGeoPoint) message.obj);
                            return;
                        }
                    case 2:
                        ((CQLayerControllerImpl) this.a.get()).doDispatchSlidedEvent();
                        return;
                    case 3:
                        if (((CQLayerControllerImpl) this.a.get()).mOwner.getMapBottomView() != null) {
                            ((CQLayerControllerImpl) this.a.get()).mOwner.getMapBottomView().requestLayout();
                            return;
                        }
                        break;
                    case 4:
                        if (((CQLayerControllerImpl) this.a.get()).validToAdjustMark() && ((CQLayerControllerImpl) this.a.get()).mPoi != null) {
                            IPoiDetailDelegate poiDetailDelegate = ((CQLayerControllerImpl) this.a.get()).mOwner.getPage().getPoiDetailDelegate();
                            if (poiDetailDelegate instanceof PoiDetailDelegate) {
                                ((PoiDetailDelegate) poiDetailDelegate).adjustMarker(((CQLayerControllerImpl) this.a.get()).mPoi.getPoint());
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
        bty mapView = this.mOwner.getPage().getMapView();
        Point screenMapCenter = getScreenMapCenter();
        if (mapView != null && screenMapCenter != null) {
            mapView.b(screenMapCenter.x, screenMapCenter.y);
        }
    }

    public CQLayerControllerImpl() {
    }

    public CQLayerControllerImpl(BaseCQLayerOwner baseCQLayerOwner) {
        init(baseCQLayerOwner);
    }

    public void init(BaseCQLayerOwner baseCQLayerOwner) {
        this.mOwner = baseCQLayerOwner;
        this.mScreenH = ags.a(baseCQLayerOwner.getPage().getContext()).height();
    }

    /* access modifiers changed from: private */
    public void processWillChangeFullState() {
        if (this.mHeaderMapLevel != -1.0f) {
            animateToMapVision(this.mHeaderMapLevel);
        }
    }

    /* access modifiers changed from: private */
    public void processWillChangeTipsState() {
        animateToMapVision(this.mOldLevel);
    }

    public void showCQLayer(POI poi, Object obj, boolean z) {
        showCQLayer(poi, obj, z, false);
    }

    public void showCQLayer(POI poi, Object obj, boolean z, boolean z2) {
        SearchPoiIndicatorView searchPoiIndicatorView;
        if (poi != null && obj != null && this.mOwner.getPage() != null && this.mOwner.getCQContainer() != null) {
            updateTipType(obj);
            onFinish();
            this.mIsLayerHidden = false;
            this.mIsShowing = true;
            this.mPoi = poi;
            this.mShowSequence = SystemClock.elapsedRealtime();
            if (this.mOwner != null) {
                cfe.a((IMapPage) this.mOwner.getPage());
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
                    if (!(this.mMapPointTipView instanceof PoiDetailViewForCQ)) {
                        searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_press_short");
                    } else {
                        searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_default");
                    }
                    SearchPoiTipWrapper searchPoiTipWrapper = (SearchPoiTipWrapper) obj;
                    searchPoiTipWrapper.a(this.mTipChildClickListener);
                    searchPoiTipWrapper.a(null, this.mTipsHeightChangeListener);
                } else {
                    searchPoiIndicatorView = null;
                }
                if (this.mMapPointTipView instanceof AbstractPoiDetailView) {
                    searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_press_long");
                    initData.putString("point_type", "2");
                    ((AbstractPoiDetailView) this.mMapPointTipView).setOnSetPoiListener(new OnSetPoiListener() {
                        public final void onSetPoi(POI poi) {
                            if ((CQLayerControllerImpl.this.mMapPointTipView instanceof AbstractPoiDetailView) && poi != null && CQLayerControllerImpl.this.mIsShowing) {
                                boolean z = !TextUtils.isEmpty(poi.getAddr()) && (CQLayerControllerImpl.this.mPoi == null || TextUtils.isEmpty(CQLayerControllerImpl.this.mPoi.getAddr()));
                                if (!DriveUtil.MAP_PLACE_DES_3.equals(poi.getName()) || z) {
                                    if ((poi == null || !poi.getPoiExtra().containsKey("isWhole") || !((Boolean) poi.getPoiExtra().get("isWhole")).booleanValue()) && CQLayerControllerImpl.this.mDataHelper != null) {
                                        CQLayerControllerImpl.this.mDataHelper;
                                        CQLayerControllerImpl.this.refreshAjxView(bxw.a(poi), false, true, null, "2");
                                        poi.getPoiExtra().put("isWhole", Boolean.TRUE);
                                        CQLayerControllerImpl.this.mPoi = poi;
                                        if (CQLayerControllerImpl.this.mIndicatorView instanceof SearchPoiIndicatorView) {
                                            ((SearchPoiIndicatorView) CQLayerControllerImpl.this.mIndicatorView).updatePoiData(null, (SearchPoi) poi.as(SearchPoi.class), -1);
                                        }
                                    } else {
                                        return;
                                    }
                                }
                                CQLayerControllerImpl.this.doDispatchSlidedEvent();
                            }
                        }
                    });
                }
                if (this.mMapPointTipView instanceof AbstractGpsTipView) {
                    searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_my_position");
                    ((AbstractGpsTipView) this.mMapPointTipView).setOnRequestCallbackListener(new OnRequestCallbackListener() {
                        public final void onRequestCallback(POI poi) {
                            if ((CQLayerControllerImpl.this.mMapPointTipView instanceof AbstractGpsTipView) && poi != null && CQLayerControllerImpl.this.mIsShowing) {
                                if ((poi == null || !poi.getPoiExtra().containsKey("isWhole") || !((Boolean) poi.getPoiExtra().get("isWhole")).booleanValue()) && CQLayerControllerImpl.this.mDataHelper != null) {
                                    CQLayerControllerImpl.this.mDataHelper;
                                    CQLayerControllerImpl.this.refreshAjxView(bxw.a(poi), false, true, null, "");
                                    poi.getPoiExtra().put("isWhole", Boolean.TRUE);
                                    CQLayerControllerImpl.this.mPoi = poi;
                                }
                            }
                        }

                        public final long getShowSequence() {
                            return CQLayerControllerImpl.this.mShowSequence;
                        }

                        public final long getFinishSequence() {
                            return CQLayerControllerImpl.this.mFinishSequence;
                        }
                    });
                }
                if (searchPoiIndicatorView == null) {
                    searchPoiIndicatorView = new SearchPoiIndicatorView(DoNotUseTool.getContext(), "type_default");
                }
                this.mIndicatorView = searchPoiIndicatorView;
                searchPoiIndicatorView.updatePoiData(null, (SearchPoi) poi.as(SearchPoi.class), -1);
                addDetailView(initData, this.mMapPointTipView, searchPoiIndicatorView, z);
                cancelAllTipDetailRequest();
                if ((this.mPoiTipView != null && !isMapPointRequestOutter()) || z2) {
                    this.mTipRequester = new PoiDetailRequester(this.mOwner.getPage().getContext());
                    new TipRefreshCallback(this, 0);
                }
                if (this.mLayerManager.j() == PanelState.EXPANDED) {
                    this.mLayerManager.a(false);
                }
                showLayer();
                this.mOwner.onShowCQLayer();
            }
        }
    }

    private void updateOldMapLevel() {
        bty mapView = this.mOwner.getPage().getMapView();
        if (mapView != null) {
            this.mOldLevel = mapView.v();
        }
    }

    private void unlockGps() {
        if (this.mOwner.getPage() != null && this.mOwner.getPage().isGpsTipDisable() && this.mOwner.getPage().getSuspendManager() != null && this.mOwner.getPage().getSuspendManager().d() != null) {
            this.mOwner.getPage().getSuspendManager().d().f();
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

    private void addDetailView(PageBundle pageBundle, View view, View view2, boolean z) {
        if (this.mPoi != null && this.mLayerManager != null && this.mDataHelper != null) {
            if (this.mPoiDetailTipView != null) {
                destroyPoiDetailLayer();
            }
            this.mLayerManager.a(this.mOnRegisterInfoListener);
            this.mTargetState = z ? "full" : ModulePoi.TIPS;
            this.mDetailLayerState = z ? DetailLayerState.EXPAND : DetailLayerState.COLLAPSED;
            if (view2 instanceof SearchPoiIndicatorView) {
                this.mDataHelper.b = ((SearchPoiIndicatorView) view2).getIndicatorInfo();
            }
            this.mLayerManager.b(this.mDataHelper.a(pageBundle, this.mOwner.getPage(), z, false));
            this.mPoiDetailTipView = this.mLayerManager.a(this.mOwner.getPage(), this.mOwner.getCQContainer(), z, pageBundle);
            this.mLayerManager.a(this.mOnStateChangeListener);
            setBundleToJsMethod(pageBundle);
            this.mLayerManager.a((BackCallback) new BackCallback() {
                public final void back(Object obj, String str) {
                    AMapLog.d("PoiDetail", "js back");
                    if (CQLayerControllerImpl.this.doBackAction(2) == ON_BACK_TYPE.TYPE_NORMAL && CQLayerControllerImpl.this.mLayerManager != null) {
                        if (CQLayerControllerImpl.this.mLayerManager.j() != PanelState.EXPANDED || CQLayerControllerImpl.this.mOwner.disableCollapseWhenBack()) {
                            CQLayerControllerImpl.this.mOwner.onAjxBack();
                        } else {
                            CQLayerControllerImpl.this.mLayerManager.a(true);
                            CQLayerControllerImpl.this.mTargetState = ModulePoi.TIPS;
                            CQLayerControllerImpl.this.animateToMapVision(CQLayerControllerImpl.this.mOldLevel);
                        }
                    }
                }
            });
            addLogListener();
            setDataHelperToAjxView();
            attachTipToDetailView(view, view2);
        }
    }

    private void setDataHelperToAjxView() {
        new AjxPageResultExecutor() {
            public final void onFragmentResult(AbstractBasePage abstractBasePage, int i, ResultType resultType, PageBundle pageBundle, JsAdapter jsAdapter) {
                if (CQLayerControllerImpl.this.mDataHelper != null) {
                    CQLayerControllerImpl.this.mDataHelper.onFragmentResult(abstractBasePage, i, resultType, pageBundle, jsAdapter);
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
            doOffsetBG(this.mLayerManager.d().d);
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
        if (this.mOwner.getPage() != null && this.mOwner.getPage().isAlive() && this.mIsShowing && poi != null && this.mPoiTipView != null && this.mLayerManager != null && this.mDataHelper != null) {
            this.mPoi = poi;
            if (isMapPointRequestOutter() && (this.mPoiTipView instanceof SearchPoiTipWrapper)) {
                SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
                if (searchPoi.getTemplateData() == null && searchPoi.getTemplateDataMap() == null) {
                    bxz.a(this.mOwner.getPage().getContext(), searchPoi);
                }
            }
            this.mPoiTipView.initData(null, poi, -1);
            ((SearchPoiIndicatorView) this.mIndicatorView).updatePoiData(null, (SearchPoi) poi.as(SearchPoi.class), -1);
            if (this.mLayerManager.j() == PanelState.COLLAPSED) {
                doOffsetBG(this.mLayerManager.d().d);
            }
            refreshAjxView(bxz.a(new PageBundle(), SuperId.getInstance(), null, poi, "poitip", 0), false, true, null, "");
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public void onMapPointRequestReturnNull() {
        if (this.mPoi != null) {
            refreshAjxView(bxz.a(new PageBundle(), SuperId.getInstance(), null, this.mPoi, "poitip", 0), false, true, null, "");
        }
    }

    /* access modifiers changed from: private */
    public void refreshAjxView(PageBundle pageBundle, boolean z, boolean z2, Boolean bool, String str) {
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
            this.mLayerManager.b(this.mDataHelper.a(pageBundle, this.mOwner.getPage(), z3, false));
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

    public void onResume() {
        if (!this.mIsLayerHidden) {
            if (this.mLayerManager != null) {
                if (this.mDataHelper != null) {
                    this.mLayerManager.a(this.mDataHelper.a);
                    this.mDataHelper.a = null;
                } else {
                    this.mLayerManager.a((String) null);
                }
            }
            this.mHandler.post(new Runnable() {
                public final void run() {
                    CQLayerControllerImpl.this.recoverSavedScreenMapCenter();
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
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
    }

    private void destroyPoiDetailLayer() {
        this.mIsMapMiddleViewHidden = false;
        this.mOldLevel = -1.0f;
        if (!(this.mPoiDetailTipView == null || this.mOwner == null)) {
            ViewGroup cQContainer = this.mOwner.getCQContainer();
            if (cQContainer != null) {
                cQContainer.removeView(this.mPoiDetailTipView);
            }
        }
        if (this.mLayerManager != null) {
            this.mLayerManager.e();
            this.mLayerManager.b();
        }
    }

    public void onPageCover() {
        if (!this.mIsLayerHidden && this.mIsMapMiddleViewHidden && this.mOwner.getMapMiddleView() != null) {
            this.mOwner.getMapMiddleView().setVisibility(0);
        }
    }

    public void onPageAppear() {
        if (!this.mIsLayerHidden && this.mIsMapMiddleViewHidden && this.mOwner.getMapMiddleView() != null) {
            this.mOwner.getMapMiddleView().setVisibility(8);
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
        return !this.mOwner.disableCollapseWhenBack() && this.mLayerManager != null && this.mLayerManager.j() == PanelState.EXPANDED && (this.mRemoveTipsStateSequence == 0 || this.mRemoveTipsStateSequence != this.mShowSequence);
    }

    public void onFragmentResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (this.mLayerManager != null && this.mDataHelper != null && this.mOwner.getPage() != null) {
            JsAdapter h = this.mLayerManager.h();
            if (h != null) {
                this.mDataHelper.onFragmentResult(this.mOwner.getPage(), i, resultType, pageBundle, h);
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
        bty mapView = this.mOwner.getPage().getMapView();
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
            this.mOwner.getPage().getMapView().a((GLGeoPoint) this.mPoi.getPoint());
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
        if (this.mLayerManager != null) {
            this.mLayerManager.f();
        }
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
            if (this.mOwner.getMapTopView() != null) {
                i = this.mOwner.getMapTopView().getHeight();
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
                this.mOwner.onDismissCQLayer(i);
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
        return this.mOwner.getMapMiddleView();
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
            unlockGps();
        } else if (TextUtils.equals(this.mTargetState, ModulePoi.TIPS)) {
            this.mOldLevel = -1.0f;
        }
        if (f != -1.0f) {
            bty mapView = this.mOwner.getPage().getMapView();
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
        if (this.mOwner.getPage() != null && this.mOwner.getPage().isAlive()) {
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
        bty mapView = this.mOwner.getPage().getMapView();
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
        int bottom = this.mOwner.getMapTopView() != null ? this.mOwner.getMapTopView().getBottom() : 0;
        int top = (((this.mOwner.getMapBottomView() != null ? this.mOwner.getMapBottomView().getTop() : ags.a(this.mOwner.getPage().getContext()).height()) - bottom) / 2) + bottom;
        if (TextUtils.equals(this.mTargetState, "full")) {
            top = (this.mHeadHeight / 2) + 20;
        } else if (TextUtils.equals(this.mTargetState, ModulePoi.TIPS)) {
            top = ags.a(this.mOwner.getPage().getContext()).height() / 2;
        }
        if (top == 0) {
            top = ags.a(this.mOwner.getPage().getContext()).height() / 2;
        }
        return new Point(ags.a(this.mOwner.getPage().getContext()).width() / 2, top);
    }

    public Point getFocusMapCenter() {
        int bottom = this.mOwner.getMapTopView() != null ? this.mOwner.getMapTopView().getBottom() : 0;
        int top = (((this.mOwner.getMapBottomView() != null ? this.mOwner.getMapBottomView().getTop() : ags.a(this.mOwner.getPage().getContext()).height()) - bottom) / 2) + bottom;
        if (TextUtils.equals(this.mTargetState, "full")) {
            top = (this.mHeadHeight / 2) + 20;
        } else if (TextUtils.equals(this.mTargetState, ModulePoi.TIPS)) {
            top = ((ags.a(this.mOwner.getPage().getContext()).height() - this.mLayerManager.d().d) + this.mOwner.getMapTopView().getBottom()) / 2;
        }
        return new Point(ags.a(this.mOwner.getPage().getContext()).width() / 2, top);
    }

    private void resetMapCenter(boolean z) {
        if (this.mSavedScreenMapCenter != null) {
            bty mapView = this.mOwner.getPage().getMapView();
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
        if (this.mOwner.getPage() != null && this.mOwner.getPage().isAlive()) {
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
        if (!this.mOwner.isCancelHandleSuspendSliding() && this.mLayerManager != null) {
            caq d = this.mLayerManager.d();
            if (d != null && d.d > 0 && d.d < i) {
                int i5 = d.d;
                int height = this.mOwner.getMapTopView() != null ? this.mOwner.getMapTopView().getHeight() : 0;
                switch (this.mOwner.getMiddleViewSlideType()) {
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
        if (this.mOwner.getMapMiddleView() != null) {
            this.mOwner.getMapMiddleView().setAlpha(f);
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
        if (this.mOwner.getMapTopView() != null) {
            this.mOwner.getMapTopView().setTranslationY((float) i);
        }
    }

    private void resetMapBottomLayoutParams() {
        MapBasePage.LogCQ(String.format("CQLayerController resetMapBottomLayoutParams", new Object[0]));
        if (this.mOwner.getMapBottomView() != null) {
            LayoutParams layoutParams = this.mOwner.getMapBottomView().getLayoutParams();
            if (layoutParams.height != -2) {
                layoutParams.height = -2;
                this.mOwner.getMapBottomView().setLayoutParams(layoutParams);
                this.mHandler.removeMessages(3);
                this.mHandler.sendEmptyMessage(3);
            }
        }
    }

    public void onOffsetBG(int i) {
        if (this.mOwner.getMapBottomView() != null) {
            caq d = this.mLayerManager.d();
            if ((this.mOwner.getMiddleViewSlideType() == MiddleViewSlideType.Alpha_Press) || i <= d.d) {
                LayoutParams layoutParams = this.mOwner.getMapBottomView().getLayoutParams();
                if (layoutParams.height != i) {
                    layoutParams.height = i;
                    this.mOwner.getMapBottomView().setLayoutParams(layoutParams);
                }
            }
        }
    }

    public void onResetBG() {
        resetMapBottomLayoutParams();
    }

    /* access modifiers changed from: private */
    public void doOffsetBG(int i) {
        if (this.mOwner.getSlidePanelManager() != null) {
            bra slidePanelManager = this.mOwner.getSlidePanelManager();
            MapBasePage.LogCQ(String.format("SildePanelManager onOffesetChanged. panel: %s, offset: %s", new Object[]{getClass().getSimpleName(), Integer.valueOf(i)}));
            b a2 = slidePanelManager.a((defpackage.bra.a) this);
            if (a2 != null) {
                a2.b = i;
                a2.c = false;
            }
            slidePanelManager.b(this);
            return;
        }
        onOffsetBG(i);
    }

    private void doResetBG() {
        if (this.mOwner.enableResetBG()) {
            if (this.mOwner.getSlidePanelManager() != null) {
                bra slidePanelManager = this.mOwner.getSlidePanelManager();
                MapBasePage.LogCQ(String.format("SildePanelManager onReset. panel: %s", new Object[]{getClass().getSimpleName()}));
                b a2 = slidePanelManager.a((defpackage.bra.a) this);
                if (a2 != null) {
                    a2.c = true;
                    a2.b = 0;
                }
                slidePanelManager.b(this);
                return;
            }
            onResetBG();
        }
    }

    private void addLogListener() {
        final boolean z = this.mMapPointTipView instanceof PoiDetailViewForCQ;
        final boolean z2 = !(this.mOwner.getPage() instanceof SearchResultBasePage);
        this.mLayerManager.a((defpackage.cap.a) new defpackage.cap.a() {
            public final void a() {
                new ccj(z, z2).b(CQLayerControllerImpl.this.mPoi);
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
}
