package com.autonavi.map.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.ae.gmap.AMapSurface;
import com.autonavi.jni.ae.gmap.utils.GLMapUtil;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;

import defpackage.akq;
import defpackage.bns;
import defpackage.bty;
import defpackage.emv;

public final class GLMapViewGroup extends FrameLayout {
    private AMapSurface mAMapSurface;
    private akq mAmapController;
    private boolean mHasUnInit;
    private bty mMapView;
    private Point mScreenPoint;
    private ImageView mScreenshotImageView;

    public GLMapViewGroup(@NonNull Context context) {
        this(context, null);
    }

    public GLMapViewGroup(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GLMapViewGroup(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        emv.a();
        this.mAmapController = akq.b();
        akq akq = this.mAmapController;
        Context applicationContext = context.getApplicationContext();
        akq.c = applicationContext;
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("http.agent"));
        sb.append(" amap/");
        sb.append(GLMapUtil.getAppVersionName(applicationContext));
        akq.a = sb.toString();
        this.mAmapController.c(new bns(context).a(false));
        this.mAMapSurface = new AMapSurface(context, attributeSet);
        setupMapViewBackground();
        emv.b();
        this.mAMapSurface.init(this.mAmapController);
        this.mHasUnInit = false;
        addView(this.mAMapSurface, new MapViewLayoutParams(-1, -1));
        this.mScreenshotImageView = createScreenshotView();
        addView(this.mScreenshotImageView, new MapViewLayoutParams(new LayoutParams(-1, -1)));
    }

    public final AMapSurface getAMapSurface() {
        return this.mAMapSurface;
    }

    public final akq getAmapController() {
        return this.mAmapController;
    }

    public final ImageView getScreenshotImageView() {
        return this.mScreenshotImageView;
    }

    public final void setMapView(@NonNull bty bty) {
        this.mMapView = bty;
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
            }
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight() + 0;
        int paddingTop = getPaddingTop() + getPaddingBottom() + 0;
        Drawable background = getBackground();
        if (background != null) {
            paddingTop = Math.max(paddingTop, background.getMinimumHeight());
            paddingLeft = Math.max(paddingLeft, background.getMinimumWidth());
        }
        setMeasuredDimension(resolveSize(paddingLeft, i), resolveSize(paddingTop, i2));
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = super.getChildCount();
        if (this.mScreenPoint == null) {
            this.mScreenPoint = new Point();
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) childAt.getLayoutParams();
                if (mapViewLayoutParams.mode == 0 && this.mMapView != null) {
                    this.mMapView.a(mapViewLayoutParams.point, this.mScreenPoint);
                    this.mScreenPoint.x += mapViewLayoutParams.x;
                    this.mScreenPoint.y += mapViewLayoutParams.y;
                    int paddingBottom = childAt.getPaddingBottom();
                    if (paddingBottom != 0) {
                        float e = this.mMapView.e(paddingBottom, this.mScreenPoint.y);
                        bty bty = this.mMapView;
                        this.mScreenPoint.y -= (int) (((float) paddingBottom) * (1.0f - (e / bty.e(paddingBottom, bty.am() / 2))));
                    }
                } else if (mapViewLayoutParams.alignment != -1) {
                    this.mScreenPoint.x = mapViewLayoutParams.x;
                    this.mScreenPoint.y = mapViewLayoutParams.y;
                } else {
                    super.onLayout(z, i, i2, i3, i4);
                }
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i6 = mapViewLayoutParams.alignment & 7;
                if (i6 == 1) {
                    this.mScreenPoint.x -= measuredWidth / 2;
                } else if (i6 != 3 && i6 == 5) {
                    this.mScreenPoint.x -= measuredWidth;
                }
                int i7 = mapViewLayoutParams.alignment & 112;
                if (i7 == 16) {
                    this.mScreenPoint.y -= measuredHeight / 2;
                } else if (i7 != 48 && i7 == 80) {
                    this.mScreenPoint.y -= measuredHeight;
                }
                int paddingLeft = getPaddingLeft();
                int i8 = this.mScreenPoint.x + paddingLeft;
                int paddingTop = this.mScreenPoint.y + getPaddingTop();
                childAt.layout(i8, paddingTop, measuredWidth + i8, measuredHeight + paddingTop);
            }
        }
    }

    public final void clearMapViewBackground(boolean z) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        boolean booleanValue = mapSharePreference.getBooleanValue("map_background_reset", true);
        if (!z || !booleanValue) {
            this.mAMapSurface.setBackgroundDrawable(null);
        } else {
            mapSharePreference.putBooleanValue("map_background_reset", false);
        }
    }

    private void setupMapViewBackground() {
        this.mAMapSurface.setBackgroundColor(Color.argb(255, FavoritesPointFragment.REQUEST_TAG_SELECT, 237, 232));
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        destroyMapView();
        super.onDetachedFromWindow();
    }

    public final void destroyMapView() {
        if (this.mAMapSurface != null && !this.mHasUnInit) {
            this.mHasUnInit = true;
            this.mAMapSurface.uninit();
        }
    }

    private ImageView createScreenshotView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ScaleType.FIT_XY);
        imageView.setBackgroundColor(0);
        imageView.setVisibility(4);
        return imageView;
    }
}
