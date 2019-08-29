package com.autonavi.miniapp.plugin.map.indoor;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorChangedListener;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetLayoutWithGuildTip;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetLayoutWithLocationTip;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetView;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetView.IContainer;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppMapIndoorFloor;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppMapIndoorFloorAdapter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MiniAppFloorManager {
    public static final String TAG = "MiniAppFloorManager";
    private IContainer floorWidgetContainer;
    /* access modifiers changed from: private */
    public MiniAppFloorWidgetView floorWidgetView;
    /* access modifiers changed from: private */
    public MiniAppFloorWidgetLayoutWithLocationTip floorWidgetViewLayout;
    private a indoorBuildingListener = new a() {
        public void indoorBuildingActivity(int i, ami ami) {
            if (i == MiniAppFloorManager.this.mMapView.j()) {
                MiniAppFloorManager.this.onIndoorBuildingActive(ami);
            }
        }
    };
    private Context mContext;
    private MiniAppFloorChangedListener mFloorChangedListener = new MiniAppFloorChangedListener() {
        public void onFloorChanged(int i, int i2) {
            if (MiniAppFloorManager.this.mIndoorBuilding != null) {
                MiniAppMapIndoorFloor mapIndoorFloorByFloorNum = MiniAppFloorManager.this.getMapIndoorFloorByFloorNum(i2);
                MiniAppFloorManager.this.setIndoorBuildingToBeActive(MiniAppFloorManager.this.mIndoorBuilding.e, mapIndoorFloorByFloorNum != null ? mapIndoorFloorByFloorNum.getmFloor_number() : i2, mapIndoorFloorByFloorNum != null ? mapIndoorFloorByFloorNum.getmFloor_name() : "");
                MiniAppFloorManager.this.mOwner.getMapView().Q();
                if (MiniAppFloorManager.this.floorWidgetViewLayout != null) {
                    if (MiniAppFloorManager.this.floorWidgetView != null) {
                        MiniAppFloorManager.this.floorWidgetView.requestLayout();
                    }
                    MiniAppFloorManager.this.floorWidgetViewLayout.requestLayout();
                }
                MiniAppFloorManager.this.notifyOnFloorChanged(i, i2);
            }
        }
    };
    private agl<cdp> mFloorWidgetChangedListeners = new agl<>();
    WeakReference<ViewGroup> mFloorWidgetParent = null;
    /* access modifiers changed from: private */
    public ami mIndoorBuilding;
    private String mIndoorLocationFloorNum = "";
    private ami mLastIndoorBuilding;
    private bro mMapManager;
    /* access modifiers changed from: private */
    public bty mMapView;
    /* access modifiers changed from: private */
    public MiniAppFloorWidgetOwner mOwner;
    private ami mPreIndoorBuilding;
    private ami mPreIndoorBuildingNotNull;

    public void setMiniAppContext(bro bro, bty bty, Context context) {
        this.mMapManager = bro;
        this.mMapView = bty;
        this.mContext = context;
    }

    public void setEnable(boolean z) {
        this.mOwner.setEnable(z);
    }

    public void init() {
        this.mOwner = new MiniAppFloorWidgetOwner(this.mContext, this.mMapView, new MiniAppFloorWidgetLayoutWithGuildTip(this.mContext));
        this.mMapManager.addIndoorBuildingListener(this.indoorBuildingListener);
        attachView();
    }

    public void destroy() {
        this.mMapManager.removeIndoorBuidingListener(this.indoorBuildingListener);
    }

    private void attachView() {
        this.floorWidgetViewLayout = this.mOwner.getFloorWidgetViewLayout();
        this.floorWidgetContainer = this.mOwner.getContainer();
        if (this.floorWidgetViewLayout != null) {
            this.floorWidgetViewLayout.setVisibility(4);
        }
    }

    public void addFloorWidgetChangedListener(cdp cdp) {
        this.mFloorWidgetChangedListeners.a(cdp);
    }

    public void removeFloorWidgetChangedListener(cdp cdp) {
        this.mFloorWidgetChangedListeners.b(cdp);
    }

    private void notifyFloorWidgetVisibilityChanged(final ami ami, final boolean z, final int i) {
        this.mFloorWidgetChangedListeners.a((a<T>) new a<cdp>() {
            public void onNotify(cdp cdp) {
                cdp.onFloorWidgetVisibilityChanged(ami, z, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void notifyOnFloorChanged(final int i, final int i2) {
        this.mFloorWidgetChangedListeners.a((a<T>) new a<cdp>() {
            public void onNotify(cdp cdp) {
                cdp.onFloorChanged(i, i2);
            }
        });
    }

    private void resumeLastBuilding() {
        if (this.mIndoorBuilding != null && this.mLastIndoorBuilding != null && !TextUtils.isEmpty(this.mIndoorBuilding.e) && !TextUtils.isEmpty(this.mLastIndoorBuilding.e) && this.mIndoorBuilding.e.equals(this.mLastIndoorBuilding.e) && this.mIndoorBuilding.d != this.mLastIndoorBuilding.d) {
            setIndoorBuildingToBeActive(this.mLastIndoorBuilding.e, this.mLastIndoorBuilding.d, this.mLastIndoorBuilding.c);
            this.mOwner.getMapView().Q();
        }
    }

    public ami getIndoorBuilding() {
        return this.mIndoorBuilding;
    }

    public ami getLastIndoorBuilding() {
        return this.mLastIndoorBuilding;
    }

    public boolean isIndoor() {
        return this.mIndoorBuilding != null;
    }

    private void updateLastBuildingInfo(String str, int i, String str2) {
        if (this.mLastIndoorBuilding != null && !TextUtils.isEmpty(this.mLastIndoorBuilding.e) && this.mLastIndoorBuilding.e.equals(str)) {
            this.mLastIndoorBuilding.d = i;
            this.mLastIndoorBuilding.c = str2;
        }
    }

    public void setIndoorBuildingToBeActive(String str, int i, String str2) {
        if (this.mIndoorBuilding != null && !TextUtils.isEmpty(this.mIndoorBuilding.e) && this.mIndoorBuilding.e.equals(str)) {
            this.mIndoorBuilding.d = i;
            if (TextUtils.isEmpty(str2)) {
                MiniAppMapIndoorFloor mapIndoorFloorByFloorNum = getMapIndoorFloorByFloorNum(i);
                str2 = mapIndoorFloorByFloorNum != null ? mapIndoorFloorByFloorNum.getmFloor_name() : "";
            }
            this.mIndoorBuilding.c = str2;
            this.mOwner.getMapView().a(str2, i, str);
            updateLastBuildingInfo(str, i, str2);
        }
    }

    private String getIndoorLocationFloorNum() {
        return this.mIndoorLocationFloorNum;
    }

    private void setIndoorLocationFloorNum(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (!compare(str, this.mIndoorLocationFloorNum)) {
            this.mIndoorLocationFloorNum = str;
            if (this.floorWidgetView != null) {
                this.floorWidgetView.setCurrentLocationFloor(str);
                if (this.floorWidgetViewLayout != null) {
                    this.floorWidgetView.requestLayout();
                    this.floorWidgetViewLayout.requestLayout();
                }
            }
        }
    }

    public MiniAppMapIndoorFloor getCurrentMapIndoorFloor() {
        if (this.floorWidgetView != null) {
            return this.floorWidgetView.getCurrentMapIndoorFloor();
        }
        return null;
    }

    public MiniAppMapIndoorFloor getMapIndoorFloorByFloorNum(int i) {
        if (this.floorWidgetView != null) {
            return this.floorWidgetView.getMapIndoorFloorByFloorNum(i);
        }
        return null;
    }

    public void setCurrentValue(int i) {
        if (this.floorWidgetView != null) {
            MiniAppMapIndoorFloor currentMapIndoorFloor = this.floorWidgetView.getCurrentMapIndoorFloor();
            if (currentMapIndoorFloor == null || i != currentMapIndoorFloor.getmFloor_number()) {
                this.floorWidgetView.setCurrentValue(i, true);
            }
        }
    }

    private void addFloorWidgetView(ami ami) {
        if (this.floorWidgetViewLayout != null) {
            String[] strArr = ami.i;
            int[] iArr = ami.h;
            int i = ami.d;
            String str = ami.c;
            this.floorWidgetViewLayout.setVisibility(4);
            this.floorWidgetViewLayout.removeAllViews();
            if (this.floorWidgetView != null) {
                this.floorWidgetView.removeChangingListener(this.mFloorChangedListener);
            }
            this.floorWidgetView = new MiniAppFloorWidgetView(this.mContext);
            this.floorWidgetView.setCurrentLocationFloor(this.mIndoorLocationFloorNum);
            this.floorWidgetView.setBuildingPoiId(ami.e);
            this.floorWidgetView.setBuildingName(ami.a);
            this.floorWidgetView.setBuildingType(ami.f);
            MiniAppFloorWidgetView miniAppFloorWidgetView = this.floorWidgetView;
            StringBuilder sb = new StringBuilder();
            sb.append(ami.d);
            miniAppFloorWidgetView.setBuildingFloor(sb.toString());
            this.floorWidgetViewLayout.setFloorView(this.floorWidgetView, this.floorWidgetContainer);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < strArr.length; i2++) {
                MiniAppMapIndoorFloor miniAppMapIndoorFloor = new MiniAppMapIndoorFloor();
                miniAppMapIndoorFloor.setmFloor_name(strArr[i2]);
                miniAppMapIndoorFloor.setmFloor_number(iArr[i2]);
                arrayList.add(miniAppMapIndoorFloor);
            }
            this.floorWidgetView.setAdapter(new MiniAppMapIndoorFloorAdapter(arrayList));
            this.floorWidgetView.setCyclic(false);
            this.floorWidgetView.setCurrentValue(i, false);
            this.floorWidgetView.addChangingListener(this.mFloorChangedListener);
            if (!(this.mPreIndoorBuildingNotNull == null || isIndoorBuildingChanged(ami, this.mPreIndoorBuildingNotNull) || ami.d == this.mPreIndoorBuildingNotNull.d)) {
                setIndoorBuildingToBeActive(ami.e, i, str);
            }
        }
    }

    public boolean isShowFloorWidget() {
        return this.mIndoorBuilding != null;
    }

    public boolean isFloorWidgetVisible() {
        return this.floorWidgetViewLayout != null && this.floorWidgetViewLayout.getVisibility() == 0;
    }

    public void updateFloorWidgetVisibility() {
        boolean isFloorWidgetVisible = isFloorWidgetVisible();
        boolean z = false;
        int i = 1;
        boolean z2 = isShowFloorWidget() && !this.mOwner.isHideWidget();
        if (z2 && this.floorWidgetView == null) {
            addFloorWidgetView(this.mIndoorBuilding);
        }
        if (this.floorWidgetViewLayout != null) {
            this.floorWidgetViewLayout.setVisibility(z2 ? 0 : 8);
        }
        boolean isFloorWidgetVisible2 = isFloorWidgetVisible();
        if (this.mIndoorBuilding != null) {
            z = true;
        }
        if (isFloorWidgetVisible != isFloorWidgetVisible2) {
            ami ami = this.mIndoorBuilding;
            if (this.mIndoorBuilding != null) {
                i = this.mIndoorBuilding.d;
            }
            notifyFloorWidgetVisibilityChanged(ami, z, i);
        }
    }

    public void setFloorWidgetAlpha(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        if (this.floorWidgetViewLayout != null) {
            this.floorWidgetViewLayout.setAlpha(f);
        }
    }

    public void indoorBuildingActivity(ami ami) {
        this.mPreIndoorBuilding = this.mIndoorBuilding;
        if (this.mIndoorBuilding != null) {
            this.mPreIndoorBuildingNotNull = this.mIndoorBuilding;
        }
        this.mIndoorBuilding = ami;
        resumeLastBuilding();
        if (this.mIndoorBuilding != null) {
            if (this.mLastIndoorBuilding == null || isIndoorBuildingChanged(ami, this.mLastIndoorBuilding)) {
                this.mLastIndoorBuilding = ami;
            }
            updateFloorWidgetVisibility();
            if (isIndoorBuildingChanged(ami, this.mPreIndoorBuildingNotNull)) {
                if (this.floorWidgetViewLayout != null) {
                    this.floorWidgetViewLayout.clearLocationType();
                }
                setIndoorLocationFloorNum("");
            }
            return;
        }
        updateFloorWidgetVisibility();
        if (this.floorWidgetViewLayout != null) {
            if (this.floorWidgetView != null) {
                this.floorWidgetView.removeChangingListener(this.mFloorChangedListener);
            }
            this.floorWidgetViewLayout.removeAllViews();
            this.floorWidgetView = null;
        }
    }

    private boolean isIndoorBuildingChanged(ami ami, ami ami2) {
        String str = null;
        String str2 = ami != null ? ami.e : null;
        if (ami2 != null) {
            str = ami2.e;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        return !str2.equals(str);
    }

    private static boolean compare(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public void setFloorWidgetParent(ViewGroup viewGroup) {
        if (this.mOwner.getFloorWidgetWithGuideTip() == null) {
            return;
        }
        if (this.mFloorWidgetParent == null || this.mFloorWidgetParent.get() == null || this.mFloorWidgetParent.get() != viewGroup) {
            if (!(this.mFloorWidgetParent == null || this.mFloorWidgetParent.get() == null)) {
                ((ViewGroup) this.mFloorWidgetParent.get()).removeView(this.mOwner.getFloorWidgetWithGuideTip());
                this.mFloorWidgetParent = null;
            }
            viewGroup.addView(this.mOwner.getFloorWidgetWithGuideTip());
            this.mFloorWidgetParent = new WeakReference<>(viewGroup);
        }
    }

    public void setTipPosition(boolean z) {
        if (this.mOwner.getFloorWidgetWithGuideTip() != null) {
            this.mOwner.getFloorWidgetWithGuideTip().setTipInRight(z);
        }
    }

    public void removeFloorWidgetLayoutWithGuildTip() {
        if (this.mOwner.getFloorWidgetWithGuideTip() != null) {
            ViewParent parent = this.mOwner.getFloorWidgetWithGuideTip().getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ((ViewGroup) parent).removeView(this.mOwner.getFloorWidgetWithGuideTip());
            }
        }
    }

    public boolean onIndoorBuildingActive(ami ami) {
        indoorBuildingActivity(ami);
        return isFloorWidgetVisible();
    }
}
