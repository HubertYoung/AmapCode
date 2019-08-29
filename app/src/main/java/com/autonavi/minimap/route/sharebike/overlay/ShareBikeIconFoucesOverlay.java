package com.autonavi.minimap.route.sharebike.overlay;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.sharebike.model.BicycleBasicItem;
import java.util.ArrayList;
import java.util.List;

public class ShareBikeIconFoucesOverlay extends PointOverlay {
    private static final String SOURCE_BULUEGOGO = "bluegogo";
    private static final String SOURCE_MOBIKE = "mobike";
    private static final String SOURCE_OFO = "ofo";
    private List<String> mIconCodeList = new ArrayList();
    private boolean mIsClick;
    private a mListener;
    private ArrayList<bkf> mPendingImageTarget = new ArrayList<>();

    public interface a {
        void a(PointOverlayItem pointOverlayItem, BicycleBasicItem bicycleBasicItem);
    }

    public ShareBikeIconFoucesOverlay(bty bty) {
        super(bty);
    }

    public void drawBikeIconFouces(BicycleBasicItem bicycleBasicItem, boolean z) {
        clear();
        this.mPendingImageTarget.clear();
        if (bicycleBasicItem != null) {
            this.mIsClick = z;
            if (z) {
                setAnimatorType(9);
            } else {
                setAnimatorType(0);
            }
            bicycleBasicItem.getSource();
            bicycleBasicItem.getIconCode();
            prepareIcon(bicycleBasicItem);
        }
    }

    private void prepareIcon(final BicycleBasicItem bicycleBasicItem) {
        AnonymousClass1 r0 = new bkf() {
            public final void onPrepareLoad(Drawable drawable) {
            }

            public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
                ShareBikeIconFoucesOverlay.this.drawIcon(bicycleBasicItem, bitmap);
            }

            public final void onBitmapFailed(Drawable drawable) {
                ShareBikeIconFoucesOverlay.this.drawIcon(bicycleBasicItem, null);
            }
        };
        this.mPendingImageTarget.add(r0);
        ImageLoader a2 = ImageLoader.a(AMapPageUtil.getAppContext());
        StringBuilder sb = new StringBuilder("file://");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(bicycleBasicItem.getIconCode());
        sb2.append("_selecet");
        sb.append(eht.a(sb2.toString()));
        a2.a(sb.toString()).a((bkf) r0);
    }

    /* access modifiers changed from: private */
    public void drawIcon(BicycleBasicItem bicycleBasicItem, Bitmap bitmap) {
        if (bicycleBasicItem != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(new GeoPoint(bicycleBasicItem.getX(), bicycleBasicItem.getY()));
            String iconCode = bicycleBasicItem.getIconCode();
            if (TextUtils.isEmpty(iconCode) || bitmap == null) {
                pointOverlayItem.mDefaultMarker = createMarker(getDefaultFocusIcon(bicycleBasicItem.getSource()), 5);
            } else {
                addIconCodeList(iconCode);
                pointOverlayItem.mDefaultMarker = createMarker(this.mIconCodeList.indexOf(iconCode), bitmap, 5, false);
            }
            addItem(pointOverlayItem);
            if (this.mListener != null && this.mIsClick) {
                this.mListener.a(pointOverlayItem, bicycleBasicItem);
            }
        }
    }

    private int getDefaultFocusIcon(String str) {
        if (!str.contains(SOURCE_MOBIKE)) {
            if (str.contains(SOURCE_BULUEGOGO)) {
                return R.drawable.selected_bluegogo;
            }
            if (str.contains(SOURCE_OFO)) {
                return R.drawable.ofo_icon_selecet;
            }
        }
        return R.drawable.selected_mobike;
    }

    private void addIconCodeList(String str) {
        if (this.mIconCodeList != null && !this.mIconCodeList.toString().contains(str)) {
            this.mIconCodeList.add(str);
        }
    }

    public void setOnIconFoucesDrawOverCallBack(a aVar) {
        this.mListener = aVar;
    }
}
