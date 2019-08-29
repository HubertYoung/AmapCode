package com.autonavi.minimap.route.sharebike.overlay;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.imageloader.MemoryPolicy;
import com.autonavi.common.imageloader.NetworkPolicy;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.sharebike.model.BicycleBasicItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShareBikeIconOverlay extends PointOverlay {
    private static final int DRAW_ICON_ANI_DELAY = 40;
    private static final int PRIORITY_MAX_VALUE = 100;
    private static final String SOURCE_BULUEGOGO = "bluegogo";
    private static final String SOURCE_MOBIKE = "mobike";
    private static final String SOURCE_OFO = "ofo";
    /* access modifiers changed from: private */
    public List<BicycleBasicItem> mBikeDataList;
    private String mClickId = "";
    /* access modifiers changed from: private */
    public a mCurrentDrawRunnable = null;
    private HashMap<String, Bitmap> mDefaultIcon = new HashMap<>();
    private List<String> mIconCodeList;
    private Object mImageLoadLock = new Object();
    private boolean mIsDrawNear;
    private boolean mIsNeedAnim;
    private bty mMapView;
    private ShareBikeNearOverlay mNearOverlay;
    private int mPendingIconCount = 0;
    private ArrayList<bkf> mPendingImageTarget = new ArrayList<>();
    private ShareBikeShadowOverlay mShadowOverlay;

    class a implements Runnable {
        private int b;

        /* synthetic */ a(ShareBikeIconOverlay shareBikeIconOverlay) {
            this(0);
        }

        private a(int i) {
            this.b = i;
        }

        public final void run() {
            if (this.b == 0) {
                ShareBikeIconOverlay.this.clear();
                ShareBikeIconOverlay.this.clearNearOverlay();
            }
            ShareBikeIconOverlay.this.clearRunnable();
            if (ShareBikeIconOverlay.this.drawOneItemOnOverlay(this.b)) {
                ShareBikeIconOverlay.this.mCurrentDrawRunnable = new a(this.b + 1);
                aho.a(ShareBikeIconOverlay.this.mCurrentDrawRunnable, 40);
            }
        }
    }

    public ShareBikeIconOverlay(bty bty) {
        super(bty);
        this.mMapView = bty;
        this.mBikeDataList = new ArrayList();
        this.mIconCodeList = new ArrayList();
    }

    public void setLinkOverlay(ShareBikeShadowOverlay shareBikeShadowOverlay, ShareBikeNearOverlay shareBikeNearOverlay) {
        if (shareBikeShadowOverlay != null) {
            this.mShadowOverlay = shareBikeShadowOverlay;
            this.mNearOverlay = shareBikeNearOverlay;
        }
    }

    public void setClickId(String str) {
        this.mClickId = str;
    }

    public String getClickId() {
        return this.mClickId;
    }

    private int getDefaultIcon(String str) {
        if (!str.contains(SOURCE_MOBIKE)) {
            if (str.contains(SOURCE_BULUEGOGO)) {
                return R.drawable.bluegogo_pop;
            }
            if (str.contains(SOURCE_OFO)) {
                return R.drawable.ofo_icon_pop;
            }
        }
        return R.drawable.mobike_pop;
    }

    private void addIconCodeList(String str) {
        if (this.mIconCodeList != null && !this.mIconCodeList.toString().contains(str)) {
            this.mIconCodeList.add(str);
        }
    }

    private void resetData() {
        clearRunnable();
        this.mBikeDataList = new ArrayList();
        this.mIconCodeList = new ArrayList();
    }

    private void drawNearOverlay(PointOverlayItem pointOverlayItem, BicycleBasicItem bicycleBasicItem) {
        if (this.mNearOverlay != null && pointOverlayItem != null && bicycleBasicItem != null) {
            this.mNearOverlay.drawNearOverlay(bicycleBasicItem);
        }
    }

    private void clearBikeShadow() {
        if (this.mShadowOverlay != null) {
            this.mShadowOverlay.clear();
        }
    }

    public void clearNearOverlay() {
        if (this.mNearOverlay != null) {
            this.mNearOverlay.clear();
        }
    }

    public void nearOverlayDisappear() {
        if (this.mNearOverlay != null) {
            this.mNearOverlay.disappear();
        }
    }

    public void clearRunnable() {
        if (this.mCurrentDrawRunnable != null) {
            aho.b(this.mCurrentDrawRunnable);
            this.mCurrentDrawRunnable = null;
        }
    }

    public void drawShareBikeIcon(List<BicycleBasicItem> list, boolean z, boolean z2) {
        resetData();
        this.mIsDrawNear = z2;
        this.mIsNeedAnim = z;
        this.mBikeDataList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (BicycleBasicItem next : list) {
                if (!TextUtils.isEmpty(next.getSource())) {
                    this.mBikeDataList.add(next);
                }
            }
            prepareIcon();
        }
    }

    public void disappear() {
        for (PointOverlayItem pointOverlayItem : getItems()) {
            ((GLPointOverlay) getGLOverlay()).ModifyAnimationPointItem(pointOverlayItem.mItemId, 10);
            if (this.mMapView != null) {
                this.mMapView.b(25);
            }
        }
    }

    private void doDrawIcons() {
        clearRunnable();
        disappear();
        nearOverlayDisappear();
        clearBikeShadow();
        if (this.mIsNeedAnim) {
            this.mCurrentDrawRunnable = new a(this);
            aho.a(this.mCurrentDrawRunnable, 300);
            return;
        }
        aho.a(new Runnable() {
            public final void run() {
                int size = ShareBikeIconOverlay.this.mBikeDataList.size();
                ShareBikeIconOverlay.this.clear();
                ShareBikeIconOverlay.this.clearNearOverlay();
                for (int i = 0; i < size; i++) {
                    ShareBikeIconOverlay.this.drawOneItemOnOverlay(i);
                }
            }
        }, 300);
    }

    private BicycleBasicItem getItemData(int i) {
        if (i < 0 || i >= this.mBikeDataList.size()) {
            return null;
        }
        return this.mBikeDataList.get(i);
    }

    /* access modifiers changed from: private */
    public boolean drawOneItemOnOverlay(int i) {
        BicycleBasicItem itemData = getItemData(i);
        if (itemData == null) {
            return false;
        }
        String iconCode = itemData.getIconCode();
        Bitmap bitmap = this.mDefaultIcon.get(iconCode);
        String source = itemData.getSource();
        PointOverlayItem pointOverlayItem = new PointOverlayItem(new GeoPoint(itemData.getX(), itemData.getY()));
        if (bitmap == null) {
            pointOverlayItem.mDefaultMarker = createMarker(getDefaultIcon(source), 5);
        } else {
            addIconCodeList(iconCode);
            pointOverlayItem.mDefaultMarker = createMarker(this.mIconCodeList.indexOf(iconCode), bitmap, 5, false);
        }
        getGLOverlay().setOverlayItemPriority(100 - i);
        addItem(pointOverlayItem);
        if (TextUtils.equals(itemData.getId(), this.mClickId)) {
            setPointItemVisble(pointOverlayItem, false, false);
        }
        if (this.mShadowOverlay != null) {
            this.mShadowOverlay.drawShadow(pointOverlayItem.getGeoPoint());
        }
        if (this.mIsDrawNear && i == 0) {
            BicycleBasicItem bicycleBasicItem = this.mBikeDataList.get(0);
            if (!(this.mNearOverlay == null || bicycleBasicItem == null)) {
                drawNearOverlay(pointOverlayItem, bicycleBasicItem);
            }
        }
        if (this.mMapView != null) {
            this.mMapView.b(25);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void onImageLoaded(Bitmap bitmap, String str) {
        int i;
        synchronized (this.mImageLoadLock) {
            this.mPendingIconCount--;
            i = this.mPendingIconCount;
            this.mDefaultIcon.put(str, bitmap);
        }
        if (i == 0) {
            doDrawIcons();
        }
    }

    private void prepareIcon() {
        synchronized (this.mImageLoadLock) {
            this.mPendingIconCount++;
            for (BicycleBasicItem iconCode : this.mBikeDataList) {
                final String iconCode2 = iconCode.getIconCode();
                if (!this.mDefaultIcon.containsKey(iconCode2)) {
                    this.mDefaultIcon.put(iconCode2, null);
                    this.mPendingIconCount++;
                    AnonymousClass2 r4 = new bkf() {
                        public final void onPrepareLoad(Drawable drawable) {
                        }

                        public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
                            ShareBikeIconOverlay.this.onImageLoaded(bitmap, iconCode2);
                        }

                        public final void onBitmapFailed(Drawable drawable) {
                            ShareBikeIconOverlay.this.onImageLoaded(null, iconCode2);
                        }
                    };
                    this.mPendingImageTarget.add(r4);
                    ImageLoader a2 = ImageLoader.a(AMapPageUtil.getAppContext());
                    StringBuilder sb = new StringBuilder("file://");
                    sb.append(eht.a(iconCode2));
                    a2.a(sb.toString()).a(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).a(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE).a((bkf) r4);
                }
            }
            this.mPendingIconCount--;
            if (this.mPendingIconCount == 0) {
                doDrawIcons();
            }
        }
    }
}
