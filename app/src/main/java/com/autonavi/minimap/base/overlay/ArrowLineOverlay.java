package com.autonavi.minimap.base.overlay;

import android.graphics.Rect;
import com.autonavi.jni.ae.gmap.gloverlay.GLArrowOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.base.overlay.ArrowLineOverlayItem;

public class ArrowLineOverlay<E extends ArrowLineOverlayItem> extends BaseOverlayDelegate<GLArrowOverlay, E> {
    public static final int MIN_DISPLAY_LEVEL = 16;

    public void onPause() {
    }

    public void onResume() {
    }

    public ArrowLineOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLArrowOverlay(this.mMapView.ad(), this.mMapView.c(), hashCode());
        ((GLArrowOverlay) this.mGLOverlay).setMinDisplayLevel(16.0f);
    }

    public void addItem(E e) {
        if (e.mPoints.length > 1) {
            createMarker(e.mInnerTextureId, 4);
            createMarker(e.mOuterTextureId, 4);
            this.mItemList.add(e);
            int[] iArr = new int[e.mPoints.length];
            int[] iArr2 = new int[e.mPoints.length];
            for (int i = 0; i < e.mPoints.length; i++) {
                iArr[i] = e.mPoints[i].x;
                iArr2[i] = e.mPoints[i].y;
            }
            ((GLArrowOverlay) this.mGLOverlay).setTexture(e.mInnerTextureId, e.mOuterTextureId);
            ((GLArrowOverlay) this.mGLOverlay).setArrow(iArr, iArr2, e.mColor, e.mColorSide, e.mWidth);
        }
    }

    /* access modifiers changed from: protected */
    public Marker createMarker(int i, int i2) {
        return SimpleMarkerFactory.createMarkerWithRawResource(i, i2, 0.0f, 0.0f, this.mMapView);
    }

    public void removeLineItem(int i) {
        if (i >= 0 && this.mItemList != null && i <= this.mItemList.size() - 1) {
            this.mItemList.remove(i);
            ((GLArrowOverlay) this.mGLOverlay).removeItem(i);
        }
    }

    public Rect getBound() {
        if (this.mItemList.size() == 0) {
            return null;
        }
        int i = 999999999;
        int i2 = 0;
        int i3 = 999999999;
        int i4 = -999999999;
        int i5 = -999999999;
        while (i2 < this.mItemList.size()) {
            ArrowLineOverlayItem arrowLineOverlayItem = (ArrowLineOverlayItem) this.mItemList.get(i2);
            int i6 = i5;
            int i7 = i4;
            int i8 = i3;
            int i9 = i;
            for (int i10 = 0; i10 < arrowLineOverlayItem.mPoints.length; i10++) {
                i9 = Math.min(i9, arrowLineOverlayItem.mPoints[i10].x);
                i8 = Math.min(i8, arrowLineOverlayItem.mPoints[i10].y);
                i7 = Math.max(i7, arrowLineOverlayItem.mPoints[i10].x);
                i6 = Math.max(i6, arrowLineOverlayItem.mPoints[i10].y);
            }
            i2++;
            i = i9;
            i3 = i8;
            i4 = i7;
            i5 = i6;
        }
        Rect rect = new Rect();
        rect.set(i, i3, i4, i5);
        return rect;
    }

    public void setMinDisplayLevel(int i) {
        ((GLArrowOverlay) this.mGLOverlay).setMinDisplayLevel((float) i);
    }
}
