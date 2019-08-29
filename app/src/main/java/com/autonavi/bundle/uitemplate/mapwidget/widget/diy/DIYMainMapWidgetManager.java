package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.amaphome.api.MapHomeIntentAction;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapView.Position;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYPopupWindow.DIYPopupWindowEventListener;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DragRecyclerView.OnItemClickListener;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.suspend.SuspendPartitionView.LayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.agroup.api.IDataService;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.minimap.bundle.agroup.api.IDataService.a;
import com.autonavi.minimap.bundle.maphome.diy.DIYMainMapEntry;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.tencent.connect.common.Constants;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class DIYMainMapWidgetManager implements btx, DIYMainMapEventListener, DIYPopupWindowEventListener, OnItemClickListener, IPageStateListener, czn, czq, czr, czu, czv, czw, czx, czy {
    public static final String LOG_BTN_MAIN_MAP_DIY_BTN_CLICK = "B261";
    public static final String LOG_BTN_MAIN_MAP_DIY_BTN_SHOW = "B262";
    public static final String SP_KEY_DIY_MAIN_MAP_ENTRIES_DRAGGED = "main_map_entries_dragged";
    /* access modifiers changed from: private */
    public static final String TAG = "DIYMainMapWidgetManager";
    private static WeakReference<DIYMainMapWidgetManager> mLastInstance;
    private boolean mAGroupGuideDisabled = false;
    private cui mAGroupGuideHelper;
    private PageBundle mBundle;
    private Context mContext;
    DIYDataServiceListener mDIYDataServiceListener = new DIYDataServiceListener(this);
    private DIYMainMapPresenter mDIYMainMapPresenter;
    private DIYMainMapView mDIYMainMapView;
    /* access modifiers changed from: private */
    public DIYPopupWindow mDIYPopupWindow;
    /* access modifiers changed from: private */
    public IDataService mDataService;
    /* access modifiers changed from: private */
    public PageBundle mFeedBackBundle;
    private FreerideTipView mFreerideTipView;
    private boolean mFullScreenState = false;
    private boolean mHasMapCreated = false;
    private boolean mIndoor = false;
    private MapManager mMapManager;
    private MapSharePreference mMapSharePreference;
    private bty mMapView;
    private DIYEntryView mMoreImageView;
    /* access modifiers changed from: private */
    public AbstractBasePage mPageContext;
    private boolean mScenic = false;
    private SchoolBusTipView mSchoolBusTipView;

    static class DIYDataServiceListener implements a {
        Boolean mLastIsInTeam = null;
        Integer mLastMemberCount = null;
        WeakReference<DIYMainMapWidgetManager> mOwner;

        public DIYDataServiceListener(DIYMainMapWidgetManager dIYMainMapWidgetManager) {
            this.mOwner = new WeakReference<>(dIYMainMapWidgetManager);
        }

        private void refreshAGroupItems() {
            int i;
            DIYMainMapWidgetManager dIYMainMapWidgetManager = (DIYMainMapWidgetManager) this.mOwner.get();
            if (dIYMainMapWidgetManager != null && dIYMainMapWidgetManager.mDataService != null) {
                if (dIYMainMapWidgetManager.mDataService.j() > 0) {
                    i = dIYMainMapWidgetManager.mDataService.j();
                } else {
                    i = dIYMainMapWidgetManager.mDataService.i();
                }
                Boolean valueOf = Boolean.valueOf(dIYMainMapWidgetManager.mDataService.l());
                if (this.mLastMemberCount == null || this.mLastMemberCount.intValue() != i || this.mLastIsInTeam == null || this.mLastIsInTeam != valueOf) {
                    DIYMainMapWidgetManager.TAG;
                    String.format("refreshAGroupItems. memberCount: %s, isInTeam %s", new Object[]{Integer.valueOf(i), valueOf});
                    this.mLastMemberCount = Integer.valueOf(i);
                    this.mLastIsInTeam = valueOf;
                    dIYMainMapWidgetManager.refreshAGroupState();
                    dIYMainMapWidgetManager.refreshRealtimeState();
                    dIYMainMapWidgetManager.refreshCheckTestState();
                    if (dIYMainMapWidgetManager.mDIYPopupWindow != null) {
                        dIYMainMapWidgetManager.mDIYPopupWindow.notifyDataSetChanged();
                    }
                }
            }
        }

        public void onTeamInfoChanged() {
            refreshAGroupItems();
        }

        public void onMemberBaseInfoChanged() {
            refreshAGroupItems();
        }

        public void onMemberLocationInfoChanged() {
            refreshAGroupItems();
        }

        public void onTeamStatusChanged(TeamStatus teamStatus) {
            refreshAGroupItems();
        }

        public void onSuperGroupInfoChanged() {
            refreshAGroupItems();
        }
    }

    public boolean isOdd(int i) {
        return (i & 1) != 1;
    }

    public void onActivityStop() {
    }

    public void onAppear() {
    }

    public void onItemLongClick(int i, DIYMainMapEntry dIYMainMapEntry) {
    }

    public void onMapSurfaceChanged(int i, int i2) {
    }

    public void onPause() {
    }

    public void setVoiceOperationServiceDelegate(czn czn) {
    }

    public void setmDIYMainMapView(DIYMainMapView dIYMainMapView) {
        this.mDIYMainMapView = dIYMainMapView;
    }

    public void setmDIYMainMapPresenter(DIYMainMapPresenter dIYMainMapPresenter) {
        this.mDIYMainMapPresenter = dIYMainMapPresenter;
    }

    public DIYMainMapWidgetManager() {
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            this.mDataService = cuh.l();
        }
    }

    public void init() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        iMainMapService.a((Object) new dac() {
            public void onMapRenderCompleted() {
                super.onMapRenderCompleted();
                DIYMainMapWidgetManager.this.onMapRenderCompleted();
            }
        });
        this.mMapView = iMainMapService.g();
        this.mPageContext = (AbstractBasePage) iMainMapService.e();
        this.mContext = iMainMapService.e().getContext();
        this.mMapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        addWidget(iMainMapService);
        bid e = iMainMapService.e();
        if (e != null) {
            cuh cuh = (cuh) a.a.a(cuh.class);
            if (cuh != null) {
                this.mAGroupGuideHelper = cuh.a(e);
            }
        }
        cuh cuh2 = (cuh) a.a.a(cuh.class);
        if (cuh2 != null) {
            this.mDataService = cuh2.l();
        }
        ((czn) iMainMapService.a(czn.class)).setVoiceOperationServiceDelegate(this);
        mLastInstance = new WeakReference<>(this);
    }

    public static DIYMainMapWidgetManager getLastInstance() {
        if (mLastInstance != null) {
            return (DIYMainMapWidgetManager) mLastInstance.get();
        }
        return null;
    }

    public void setAGroupGuideDisabled(boolean z) {
        this.mAGroupGuideDisabled = z;
    }

    public bid getPageContect() {
        return this.mPageContext;
    }

    private void addWidget(IMainMapService iMainMapService) {
        this.mMapManager = iMainMapService.d();
        this.mDIYPopupWindow = new DIYPopupWindow(this.mContext);
        if (this.mDIYMainMapPresenter != null) {
            this.mDIYMainMapPresenter.attachView(this.mDIYMainMapView);
        }
        this.mDIYPopupWindow.attachView(this.mDIYMainMapView);
    }

    public void addEntry(int i, DIYMainMapEntry dIYMainMapEntry, OnTouchListener onTouchListener) {
        DIYEntryView dIYEntryView = new DIYEntryView(this.mContext);
        dIYEntryView.setOnTouchListener(onTouchListener);
        refreshEntryView(dIYEntryView, dIYMainMapEntry);
        int a = bet.a(this.mContext, 46);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_item_height);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.layer_tip_tv_land_bottom);
        if (i == 0) {
            dimensionPixelSize += dimensionPixelSize2;
        }
        this.mDIYMainMapView.addContentView(dIYEntryView, new LayoutParams(a, dimensionPixelSize), i == 0 ? Position.TOP : Position.MIDDLE);
    }

    public void addMore(int i, OnTouchListener onTouchListener) {
        this.mMoreImageView = new DIYEntryView(this.mContext);
        this.mMoreImageView.setTag(DIYMainMapPresenter.DIY_ENTRY_KEY_MORE);
        this.mMoreImageView.setOnTouchListener(onTouchListener);
        this.mMoreImageView.getIcon().setImageResource(R.drawable.icon_c_diy5);
        this.mMoreImageView.getIcon().setContentDescription(DIYMainMapPresenter.DIY_ENTRY_KEY_MORE);
        int a = bet.a(this.mContext, 46);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_more_height);
        this.mContext.getResources().getDimensionPixelSize(R.dimen.layer_tip_tv_land_bottom);
        this.mDIYMainMapView.addContentView(this.mMoreImageView, new LayoutParams(a, dimensionPixelSize), Position.BOTTOM);
    }

    public void showNewEntry(boolean z) {
        this.mMoreImageView.getIcon().setImageResource(z ? R.drawable.icon_c_diy4 : R.drawable.icon_c_diy5);
    }

    public void clearEntries() {
        this.mDIYMainMapView.removeAllViews();
    }

    public void onEntryClick(DIYMainMapEntry dIYMainMapEntry) {
        onEntryItemClick(dIYMainMapEntry, false);
    }

    public void onEntryMoreClick(List<DIYMainMapEntry> list) {
        showNewEntry(false);
        this.mDIYPopupWindow.setEntries(list, this);
        this.mDIYPopupWindow.setWindowEventListener(this);
        this.mDIYPopupWindow.showAsDropDown();
        log(DIYMainMapPresenter.DIY_ENTRY_KEY_MORE, "点击");
        logWindowDIYBtnsShow();
    }

    public Position getPosition(DIYEntryView dIYEntryView) {
        return this.mDIYMainMapView != null ? this.mDIYMainMapView.getPosition(dIYEntryView) : Position.MIDDLE;
    }

    public void onItemClick(int i, DIYMainMapEntry dIYMainMapEntry) {
        onEntryItemClick(dIYMainMapEntry, true);
    }

    public void onItemChanged(int i, DIYMainMapEntry dIYMainMapEntry, int i2, DIYMainMapEntry dIYMainMapEntry2, List<DIYMainMapEntry> list) {
        if (i != i2) {
            notifyViewDataChanged(list);
            if (dIYMainMapEntry != null) {
                log(dIYMainMapEntry.name, "排序");
                this.mMapSharePreference.putBooleanValue(SP_KEY_DIY_MAIN_MAP_ENTRIES_DRAGGED, true);
            }
        }
    }

    private void onEntryItemClick(final DIYMainMapEntry dIYMainMapEntry, boolean z) {
        if ("traffic_event".equals(dIYMainMapEntry.key)) {
            onTrafficEventClick();
            this.mDIYPopupWindow.notifyDataSetChanged();
        } else {
            if (DIYMainMapPresenter.DIY_ENTRY_KEY_FREERIDE.equals(dIYMainMapEntry.key)) {
                ank.a(IMainMapService.class);
            }
            if ("schoolbus".equals(dIYMainMapEntry.key)) {
                ank.a(IMainMapService.class);
            }
            if (z) {
                this.mDIYPopupWindow.dismiss(new AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(dIYMainMapEntry.scheme));
                        AMapPageUtil.getPageContext().startScheme(intent);
                    }
                });
            } else {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(dIYMainMapEntry.scheme));
                AMapPageUtil.getPageContext().startScheme(intent);
            }
        }
        if (dIYMainMapEntry != null) {
            log(dIYMainMapEntry.name, "点击");
            if ((this.mDIYMainMapPresenter == null ? null : this.mDIYMainMapPresenter.getEntries()) != null) {
                int indexOf = this.mDIYMainMapPresenter.getEntries().indexOf(dIYMainMapEntry);
                if (indexOf >= 0) {
                    logDIYBtnClick(dIYMainMapEntry.name, indexOf + 1, this.mMapSharePreference.getBooleanValue(SP_KEY_DIY_MAIN_MAP_ENTRIES_DRAGGED, false));
                }
            }
        }
    }

    public boolean voiceOpenTraffic() {
        if (!this.mHasMapCreated || this.mMapView == null) {
            return false;
        }
        if (!this.mMapView.s()) {
            onTrafficEventClick();
        }
        return true;
    }

    public boolean voiceCloseTraffic() {
        if (!this.mHasMapCreated || this.mMapView == null) {
            return false;
        }
        if (this.mMapView.s()) {
            onTrafficEventClick();
        }
        return true;
    }

    public int voiceZoomInMainMap() {
        if (!this.mHasMapCreated || this.mMapView == null) {
            return SDKFactory.getCoreType;
        }
        if (this.mMapView.v() + 1.0f > ((float) this.mMapView.l())) {
            return UCMPackageInfo.getKernelResFiles;
        }
        this.mMapView.x();
        return 10000;
    }

    public int voiceZoomOutMainMap() {
        if (!this.mHasMapCreated || this.mMapView == null) {
            return SDKFactory.getCoreType;
        }
        if (this.mMapView.v() - 1.0f < ((float) this.mMapView.m())) {
            return UCMPackageInfo.sortByLastModified;
        }
        this.mMapView.y();
        return 10000;
    }

    public float[] voiceMixMaxZoom() {
        float[] fArr = new float[3];
        if (this.mHasMapCreated && this.mMapView != null) {
            fArr[0] = (float) this.mMapView.m();
            fArr[1] = (float) this.mMapView.l();
            fArr[2] = this.mMapView.v();
        }
        return fArr;
    }

    public int voiceZoomInDiffMainMap(float f) {
        if (!this.mHasMapCreated || this.mMapView == null) {
            return SDKFactory.getCoreType;
        }
        float v = this.mMapView.v() + f;
        if (v > ((float) this.mMapView.l())) {
            this.mMapView.e(this.mMapView.l());
            return UCMPackageInfo.getKernelResFiles;
        }
        this.mMapView.d(v);
        return 10000;
    }

    public int voiceZoomOutDiffMainMap(float f) {
        if (!this.mHasMapCreated || this.mMapView == null) {
            return SDKFactory.getCoreType;
        }
        float v = this.mMapView.v() - Math.abs(f);
        if (v < ((float) this.mMapView.m())) {
            this.mMapView.e(this.mMapView.m());
            return UCMPackageInfo.sortByLastModified;
        }
        this.mMapView.d(v);
        return 10000;
    }

    public void moveMapView(int i, int i2) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null && iMainMapService.a()) {
            cde b = iMainMapService.b();
            MapManager d = iMainMapService.d();
            if (b != null && d != null) {
                cdz d2 = b.d();
                if (d2 != null) {
                    d2.f();
                    GeoPoint a = cfg.a(this.mMapView, GeoPoint.glGeoPoint2GeoPoint(this.mMapView.n()), i, i2, this.mMapView.v(), (int) this.mMapView.I());
                    double longitude = a.getLongitude() / 180.0d;
                    int ceil = (int) Math.ceil(longitude);
                    if (longitude > 1.0d) {
                        if (isOdd(ceil)) {
                            a.setLonLat((a.getLongitude() - ((double) (ceil * 180))) - 180.0d, a.getLatitude());
                        } else {
                            a.setLonLat(a.getLongitude() - ((double) (ceil * 180)), a.getLatitude());
                        }
                    }
                    if (longitude < -1.0d) {
                        if (isOdd(ceil)) {
                            a.setLonLat(180.0d - Math.abs(a.getLongitude() + ((double) (ceil * 180))), a.getLatitude());
                        } else {
                            a.setLonLat(-(a.getLongitude() + ((double) (ceil * 180))), a.getLatitude());
                        }
                    }
                    this.mMapView.a(a.x, a.y);
                }
            }
        }
    }

    private void onTrafficEventClick() {
        boolean z;
        boolean booleanValue = this.mMapSharePreference.getBooleanValue("traffic", false);
        boolean s = this.mMapManager.getMapView().s();
        if (booleanValue == s) {
            if (!booleanValue) {
                this.mMapManager.getMapView().t();
            }
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                bqx.a(!booleanValue, true, this.mMapManager, this.mContext);
            }
            z = !booleanValue;
        } else {
            if (!s) {
                this.mMapManager.getMapView().t();
            }
            bqx bqx2 = (bqx) ank.a(bqx.class);
            if (bqx2 != null) {
                bqx2.a(!s, true, this.mMapManager, this.mContext);
            }
            z = !s;
        }
        setTrafficEventSelected(z);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", booleanValue ? "0" : "1");
            jSONObject.put("status", bnv.d());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B014", jSONObject);
    }

    private void refreshTrafficState() {
        if (this.mHasMapCreated) {
            setTrafficState(this.mMapSharePreference.getBooleanValue("traffic", true), false);
        }
    }

    private void setTrafficState(boolean z, boolean z2) {
        setTrafficEventSelected(z);
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(z, z2, this.mMapManager, this.mContext);
        }
    }

    private void setTrafficEventSelected(boolean z) {
        int i = R.drawable.icon_c_diy1;
        int i2 = R.drawable.icon_c_diy8;
        DIYMainMapEntry dIYMainMapEntry = null;
        List entries = this.mDIYMainMapPresenter == null ? null : this.mDIYMainMapPresenter.getEntries();
        if (entries != null) {
            Iterator it = entries.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DIYMainMapEntry dIYMainMapEntry2 = (DIYMainMapEntry) it.next();
                if ("traffic_event".equals(dIYMainMapEntry2.key)) {
                    dIYMainMapEntry2.isChecked = z;
                    dIYMainMapEntry2.icon = Integer.valueOf(i);
                    dIYMainMapEntry2.highlightIcon = Integer.valueOf(i2);
                    dIYMainMapEntry = dIYMainMapEntry2;
                    break;
                }
            }
        }
        refreshEntryView((DIYEntryView) this.mDIYMainMapView.findViewWithTag("traffic_event"), dIYMainMapEntry);
    }

    /* access modifiers changed from: private */
    public void refreshAGroupState() {
        boolean z;
        int i;
        if (this.mDataService != null) {
            if (this.mDataService.j() > 0) {
                i = this.mDataService.j();
            } else {
                i = this.mDataService.i();
            }
            z = this.mDataService.l();
        } else {
            i = 0;
            z = false;
        }
        boolean z2 = z && i > 0;
        String str = "";
        if (z2) {
            cuh cuh = (cuh) a.a.a(cuh.class);
            if (cuh != null) {
                str = cuh.l().b(i);
            }
        }
        String[] strArr = {"agroup", DIYMainMapPresenter.DIY_ENTRY_KEY_AGROUP_TEST};
        List<DIYMainMapEntry> entries = this.mDIYMainMapPresenter == null ? null : this.mDIYMainMapPresenter.getEntries();
        if (entries != null) {
            int i2 = 0;
            for (DIYMainMapEntry next : entries) {
                int i3 = i2;
                for (int i4 = 0; i4 < 2; i4++) {
                    String str2 = strArr[i4];
                    if (str2.equals(next.key)) {
                        i3++;
                        next.isChecked = z2;
                        next.highlightText = str;
                        refreshEntryView((DIYEntryView) this.mDIYMainMapView.findViewWithTag(str2), next);
                        if (i3 >= 2) {
                            break;
                        }
                    }
                }
                i2 = i3;
            }
        }
    }

    /* access modifiers changed from: private */
    public void refreshRealtimeState() {
        bmn.b();
        List<DIYMainMapEntry> entries = this.mDIYMainMapPresenter == null ? null : this.mDIYMainMapPresenter.getEntries();
        if (entries != null) {
            for (DIYMainMapEntry next : entries) {
                if (DIYMainMapPresenter.DIY_ENTRY_REALTIME_BUS_EVENT_KEY.equals(next.key)) {
                    next.isChecked = false;
                    next.highlightText = "实时公交";
                    next.icon = Integer.valueOf(R.drawable.icon_c_diy1);
                    next.highlightIcon = Integer.valueOf(R.drawable.icon_c_diy8);
                    refreshEntryView((DIYEntryView) this.mDIYMainMapView.findViewWithTag(DIYMainMapPresenter.DIY_ENTRY_REALTIME_BUS_EVENT_KEY), next);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void refreshCheckTestState() {
        boolean l = this.mDataService != null ? this.mDataService.l() : false;
        List<DIYMainMapEntry> entries = this.mDIYMainMapPresenter == null ? null : this.mDIYMainMapPresenter.getEntries();
        if (entries != null) {
            for (DIYMainMapEntry next : entries) {
                if (DIYMainMapPresenter.DIY_ENTRY_KEY_CHECK_TEST.equals(next.key)) {
                    next.isChecked = l;
                    refreshEntryView((DIYEntryView) this.mDIYMainMapView.findViewWithTag(DIYMainMapPresenter.DIY_ENTRY_KEY_CHECK_TEST), next);
                    return;
                }
            }
        }
    }

    private void log(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
            jSONObject.put("action", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.getInstance();
        LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_DIY_CLICK, jSONObject);
    }

    public void showPopupWindow(List<DIYMainMapEntry> list) {
        loadColorAnimation(this.mDIYMainMapView, false);
    }

    public void dismissPopupWindow(List<DIYMainMapEntry> list) {
        loadColorAnimation(this.mDIYMainMapView, true);
        notifyViewDataChanged(list);
    }

    private void loadColorAnimation(final View view, boolean z) {
        float f = 1.0f;
        float f2 = z ? 0.0f : 1.0f;
        if (!z) {
            f = 0.0f;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setInterpolator(new FastOutSlowInInterpolator());
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }
        });
        view.startAnimation(alphaAnimation);
    }

    private void notifyViewDataChanged(List<DIYMainMapEntry> list) {
        int i = DIYMainMapPresenter.MAX_ENTRY_NUMBER;
        if (list.size() <= i) {
            i = list.size();
        }
        List<DIYEntryView> allEntryView = this.mDIYMainMapView.getAllEntryView();
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (allEntryView != null && i3 < allEntryView.size()) {
                refreshEntryView(allEntryView.get(i3), list.get(i3));
            }
        }
        while (i2 < list.size()) {
            i2++;
            list.get(i2).order = i2;
        }
        if (this.mDIYMainMapPresenter != null) {
            this.mDIYMainMapPresenter.updateOrder(list);
        }
    }

    private void refreshEntryView(DIYEntryView dIYEntryView, DIYMainMapEntry dIYMainMapEntry) {
        if (dIYEntryView != null && dIYMainMapEntry != null) {
            dIYEntryView.setTag(dIYMainMapEntry.key);
            dIYEntryView.getInfo().setTextColor(this.mContext.getResources().getColor(dIYMainMapEntry.isChecked ? R.color.f_c_6 : R.color.f_c_2));
            dIYEntryView.getInfo().setText(dIYMainMapEntry.isChecked ? dIYMainMapEntry.highlightText : dIYMainMapEntry.normalText);
            if (!TextUtils.isEmpty(dIYMainMapEntry.name)) {
                dIYEntryView.getIcon().setContentDescription(dIYMainMapEntry.name.trim());
            } else {
                dIYEntryView.getIcon().setContentDescription("");
            }
            loadViewImage(dIYEntryView.getIcon(), dIYMainMapEntry.isChecked ? dIYMainMapEntry.highlightIcon : dIYMainMapEntry.icon);
        }
    }

    private void loadViewImage(ImageView imageView, Object obj) {
        if (imageView != null) {
            if (obj instanceof Integer) {
                try {
                    ImageLoader.a(this.mContext).a((Object) imageView);
                    imageView.setImageResource(((Integer) obj).intValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                String obj2 = obj != null ? obj.toString() : "";
                if (!TextUtils.isEmpty(obj2)) {
                    try {
                        ImageLoader.a(this.mContext).a(obj2).a(R.drawable.icon_c_diy_default).a(imageView, (bjl) null);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    ImageLoader.a(this.mContext).a((Object) imageView);
                    imageView.setImageResource(R.drawable.icon_c_diy_default);
                }
            }
        }
    }

    public void onActivityStart() {
        lo.a().d(DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
    }

    public void onResume() {
        if (this.mDIYMainMapPresenter != null) {
            this.mDIYMainMapPresenter.getNativeEntries();
            if (this.mDIYPopupWindow != null && this.mDIYPopupWindow.isShowing()) {
                this.mDIYPopupWindow.setEntries(this.mDIYMainMapPresenter.getEntries(), this);
            }
        }
        refreshTrafficState();
        refreshAGroupState();
        refreshRealtimeState();
        refreshCheckTestState();
        if (this.mDIYPopupWindow == null || !this.mDIYPopupWindow.isShowing()) {
            logMainMapDIYBtnsShow();
        } else {
            logWindowDIYBtnsShow();
        }
        boolean z = this.mFullScreenState || this.mIndoor || this.mScenic;
        setVisibility(z);
        if (!z) {
            List<DIYMainMapEntry> entries = this.mDIYMainMapPresenter == null ? null : this.mDIYMainMapPresenter.getEntries();
            if (this.mMapSharePreference.getBooleanValue("diy_agroup_join_entry_show", true) && entries != null && entries.size() > 1) {
                ank.a(IMainMapService.class);
            }
            if (this.mMapSharePreference.getBooleanValue(SchoolBusTipView.SP_KEY_DIY_SCHOOL_BUS_ENTRY_SHOW, true) && entries != null && entries.size() > 1) {
                ank.a(IMainMapService.class);
            }
            if (this.mMapSharePreference.getBooleanValue(FreerideTipView.SP_KEY_DIY_FREE_RIDE_ENTRY_SHOW, true) && entries != null && entries.size() > 1) {
                ank.a(IMainMapService.class);
            }
            busCardTip(entries);
        }
    }

    private View findEntryAnchorView(ViewGroup viewGroup, View view, List<DIYMainMapEntry> list, String str) {
        View findViewWithTag = viewGroup.findViewWithTag(str);
        if (findViewWithTag != null) {
            return findViewWithTag;
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator<DIYMainMapEntry> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            DIYMainMapEntry next = it.next();
            if (next != null && TextUtils.equals(next.key, str)) {
                findViewWithTag = view;
                break;
            }
        }
        return findViewWithTag;
    }

    private View getGroupTipView(bid bid) {
        View inflate = LayoutInflater.from(bid.getContext()).inflate(R.layout.view_agroup_guide_layout, null);
        ((TextView) inflate.findViewById(R.id.agroup_guide_tips_tv)).setText(bid.getContext().getString(R.string.agroup_main_entry_join_guide_tips));
        return inflate;
    }

    public void onMapSurfaceCreated() {
        this.mHasMapCreated = true;
        if (this.mBundle != null) {
            handleTrafficMapEvent(this.mBundle);
        }
        refreshTrafficState();
        refreshAGroupState();
        refreshRealtimeState();
        refreshCheckTestState();
    }

    public void onMapSurfaceDestroy() {
        this.mHasMapCreated = false;
    }

    public void onFullScreenStateChanged(boolean z) {
        this.mFullScreenState = z;
        setVisibility(this.mFullScreenState || this.mIndoor || this.mScenic);
    }

    public void onIndoor(boolean z) {
        this.mIndoor = z;
        setVisibility(this.mFullScreenState || this.mIndoor || this.mScenic);
    }

    public void onScenic(boolean z) {
        this.mScenic = z;
        setVisibility(this.mFullScreenState || this.mIndoor || this.mScenic);
    }

    public void onCover() {
        onLeavePage();
    }

    private void onLeavePage() {
        cancelReportActivities();
        this.mDIYPopupWindow.dismiss();
        this.mDIYMainMapView.clearAnimation();
    }

    private void setVisibility(boolean z) {
        ank.a(IMainMapService.class);
        if (z) {
            this.mDIYPopupWindow.dismiss();
        }
        if (!this.mDIYPopupWindow.isShowing()) {
            this.mDIYMainMapView.clearAnimation();
            this.mDIYMainMapView.setVisibility(z ? 4 : 0);
        }
    }

    public boolean onNewIntent(PageBundle pageBundle) {
        if (pageBundle == null) {
            return false;
        }
        String string = pageBundle.getString(Constants.KEY_ACTION);
        if ("action_base_map_scheme".equals(string)) {
            if (MapHomeIntentAction.OPEN_TRAFFIC_CONDITION == ((MapHomeIntentAction) pageBundle.getObject("key_scheme_feature"))) {
                openTraffic(pageBundle);
                return true;
            }
        } else if ("action_show_traffic".equalsIgnoreCase(string) || "action_traffic_event".equalsIgnoreCase(string)) {
            if (this.mHasMapCreated) {
                handleTrafficMapEvent(pageBundle);
            } else {
                this.mBundle = pageBundle;
            }
            return true;
        }
        return false;
    }

    private void openTraffic(PageBundle pageBundle) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        cde b = iMainMapService.b();
        MapManager d = iMainMapService.d();
        if (b != null && d != null) {
            pageBundle.getObject("POI");
            int i = pageBundle.getInt(H5PermissionManager.level);
            b.d().a(false);
            setTrafficState(true, true);
            iMainMapService.g().f((float) i);
            iMainMapService.a(czl.class);
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                iMainMapService.g().a(latestPosition.x, latestPosition.y);
            }
        }
    }

    private void handleTrafficMapEvent(PageBundle pageBundle) {
        if (pageBundle != null && pageBundle.containsKey(Constants.KEY_ACTION)) {
            String string = pageBundle.getString(Constants.KEY_ACTION);
            if ("action_show_traffic".equalsIgnoreCase(string)) {
                handleShowTraffic(pageBundle);
                return;
            }
            if ("action_traffic_event".equalsIgnoreCase(string)) {
                handleTrafficEvent(pageBundle);
            }
        }
    }

    private void handleTrafficEvent(PageBundle pageBundle) {
        int i = pageBundle.getInt(IOverlayManager.EVENT_ID_KEY);
        double d = pageBundle.getDouble("lat", -1.0d);
        double d2 = pageBundle.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON, -1.0d);
        int i2 = pageBundle.getInt("zoom", 16);
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null && iMainMapService.a()) {
            cde b = iMainMapService.b();
            MapManager d3 = iMainMapService.d();
            if (b != null && d3 != null) {
                cdz d4 = b.d();
                if (d4 != null && d3.getOverlayManager() != null) {
                    bty mapView = d3.getMapView();
                    if (mapView != null) {
                        new MapSharePreference(SharePreferenceName.SharedPreferences).edit().putInt("X".toString(), 0).putInt("Y".toString(), 0).putFloat("PRESISE_ZOOM_LEVEL", -1.0f).commit();
                        d4.f();
                        d4.a(false);
                        GeoPoint geoPoint = new GeoPoint(d2, d);
                        mapView.a(geoPoint.x, geoPoint.y);
                        if (i2 >= 0) {
                            mapView.f((float) i2);
                        }
                        bdl bdl = (bdl) a.a.a(bdl.class);
                        if (bdl != null) {
                            bid pageContext = AMapPageUtil.getPageContext();
                            a aVar = new a();
                            als als = new als();
                            als.e = geoPoint.x;
                            als.f = geoPoint.y;
                            als.g = -1;
                            aVar.a = als;
                            aVar.b = i;
                            bdl.a(pageContext, aVar);
                        }
                        this.mBundle = null;
                    }
                }
            }
        }
    }

    public boolean onResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (resultType != ResultType.OK || i != 1021 || this.mMapView == null) {
            return false;
        }
        requestReportActivities();
        this.mMapView.f(1);
        postScreenShot(400);
        this.mFeedBackBundle = pageBundle;
        ank.a(cgd.class);
        return true;
    }

    public void onCreate() {
        if (this.mDataService != null) {
            this.mDataService.a(this.mDIYDataServiceListener);
        }
    }

    public void onDestroy() {
        onLeavePage();
        if (this.mDIYMainMapPresenter != null) {
            this.mDIYMainMapPresenter.onDestroy();
        }
    }

    /* access modifiers changed from: private */
    public void onMapRenderCompleted() {
        if (this.mMapView != null) {
            this.mMapView.f(0);
        }
        postScreenShot(0);
    }

    private void postScreenShot(long j) {
        aho.a(new Runnable() {
            public void run() {
                bid e = ((IMainMapService) ank.a(IMainMapService.class)).e();
                if (e != null && (e instanceof AbstractBasePage)) {
                    AbstractBasePage abstractBasePage = (AbstractBasePage) e;
                    cgd cgd = (cgd) ank.a(cgd.class);
                    if (!(cgd == null || cgd.a() || abstractBasePage == null)) {
                        DIYMainMapWidgetManager.this.mFeedBackBundle;
                    }
                }
            }
        }, j);
    }

    private void busCardTip(List<DIYMainMapEntry> list) {
        if (list != null && list.size() > 1) {
            ank.a(IMainMapService.class);
        }
    }

    public static void logDIYBtnClick(String str, int i, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
            jSONObject.put("status", i);
            jSONObject.put("type", z ? "用户自定义过" : "默认推荐");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LOG_BTN_MAIN_MAP_DIY_BTN_CLICK, jSONObject);
    }

    public static void logDIYBtnShow(String str, int i, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
            jSONObject.put("status", i);
            jSONObject.put("type", z ? "用户自定义过" : "默认推荐");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LOG_BTN_MAIN_MAP_DIY_BTN_SHOW, jSONObject);
    }

    public void logMainMapDIYBtnsShow() {
        if (this.mDIYMainMapPresenter != null) {
            List<DIYMainMapEntry> mainMapShowEntries = this.mDIYMainMapPresenter.getMainMapShowEntries();
            if (mainMapShowEntries != null) {
                int i = 0;
                boolean booleanValue = this.mMapSharePreference.getBooleanValue(SP_KEY_DIY_MAIN_MAP_ENTRIES_DRAGGED, false);
                while (i < mainMapShowEntries.size()) {
                    i++;
                    logDIYBtnShow(mainMapShowEntries.get(i).name, i, booleanValue);
                }
            }
        }
    }

    public void logWindowDIYBtnsShow() {
        if (this.mDIYMainMapPresenter != null) {
            List<DIYMainMapEntry> entries = this.mDIYMainMapPresenter.getEntries();
            if (entries != null) {
                int i = 0;
                boolean booleanValue = this.mMapSharePreference.getBooleanValue(SP_KEY_DIY_MAIN_MAP_ENTRIES_DRAGGED, false);
                while (i < entries.size()) {
                    i++;
                    logDIYBtnShow(entries.get(i).name, i, booleanValue);
                }
            }
        }
    }

    private void handleShowTraffic(PageBundle pageBundle) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        double d = pageBundle.getDouble("lat", latestPosition.getLatitude());
        double d2 = pageBundle.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON, latestPosition.getLongitude());
        if (d < 0.0d) {
            d = latestPosition.getLatitude();
        }
        if (d2 < 0.0d) {
            d2 = latestPosition.getLongitude();
        }
        int i = pageBundle.getInt("zoom", -1);
        if (d >= 0.0d || d2 >= 0.0d) {
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null && iMainMapService.a()) {
                cde b = iMainMapService.b();
                MapManager d3 = iMainMapService.d();
                if (b != null && d3 != null) {
                    cdz d4 = b.d();
                    bty mapView = d3.getMapView();
                    if (mapView != null && d4 != null) {
                        new MapSharePreference(SharePreferenceName.SharedPreferences).edit().putInt("X".toString(), 0).putInt("Y".toString(), 0).putFloat("PRESISE_ZOOM_LEVEL", -1.0f).commit();
                        d4.f();
                        d4.a(false);
                        GeoPoint geoPoint = new GeoPoint(d2, d);
                        mapView.a(geoPoint.x, geoPoint.y);
                        if (i > 0) {
                            mapView.f((float) i);
                        }
                        setTrafficState(true, true);
                        this.mBundle = null;
                    }
                }
            }
        }
    }

    private void requestReportActivities() {
        final ctl ctl = (ctl) a.a.a(ctl.class);
        if (ctl != null) {
            ctl.a("5", new Callback<ctm>() {
                public void error(Throwable th, boolean z) {
                }

                public void callback(ctm ctm) {
                    if (ctm != null && ctm.a == 1) {
                        ctl.a(DIYMainMapWidgetManager.this.mPageContext, "5", ctm.c);
                    }
                }
            });
        }
    }

    private void cancelReportActivities() {
        ctl ctl = (ctl) a.a.a(ctl.class);
        if (ctl != null) {
            ctl.a("5");
        }
    }
}
