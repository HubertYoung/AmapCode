package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import java.util.List;

public abstract class LineOverlay<E extends LineOverlayItem> extends BaseOverlayDelegate<GLLineOverlay, E> {
    public static final int LINE_SEG_CAP = 50;
    public Rect mLineBound;
    private OnLineOverlayClickListener mOnLineOverlayClickListener;

    public interface OnLineOverlayClickListener<E> {
        void onLineOverlayClick(bty bty, BaseMapOverlay<?, ?> baseMapOverlay, long j);
    }

    public void setOnLineOverlayClickListener(OnLineOverlayClickListener onLineOverlayClickListener) {
        this.mOnLineOverlayClickListener = onLineOverlayClickListener;
    }

    public boolean onLineOverlayClick(long j) {
        if (!((GLLineOverlay) this.mGLOverlay).isVisible() || !((GLLineOverlay) this.mGLOverlay).isClickable()) {
            return false;
        }
        if (this.mOnLineOverlayClickListener != null) {
            this.mOnLineOverlayClickListener.onLineOverlayClick(this.mMapView, this, j);
        }
        return true;
    }

    public LineOverlay(int i, bty bty) {
        super(i, bty);
    }

    public LineOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLLineOverlay(this.mEngineID, this.mMapView.c(), hashCode());
    }

    private void createTexture(LineOverlayItem lineOverlayItem, Bitmap bitmap) {
        if (lineOverlayItem != null) {
            createResTexture(lineOverlayItem.mLineType, lineOverlayItem.mLineProperty.f, bitmap);
            if (lineOverlayItem.mLineProperty.h != -1) {
                createResTexture(lineOverlayItem.mLineType, lineOverlayItem.mLineProperty.h);
            }
        }
    }

    public void addItem(LineOverlayItem lineOverlayItem, Bitmap bitmap) {
        if (lineOverlayItem != null) {
            this.mLineBound = null;
            createTexture(lineOverlayItem, bitmap);
            if (lineOverlayItem != null && lineOverlayItem.mPoints.length > 0) {
                ((GLLineOverlay) this.mGLOverlay).addLineItem(lineOverlayItem.mLineProperty);
                this.mItemList.add(lineOverlayItem);
            }
        }
    }

    private void createTexture(LineOverlayItem lineOverlayItem) {
        if (lineOverlayItem != null) {
            createResTexture(lineOverlayItem.mLineType, lineOverlayItem.mLineProperty.f);
            if (lineOverlayItem.mLineProperty.h != -1) {
                createResTexture(lineOverlayItem.mLineType, lineOverlayItem.mLineProperty.h);
            }
        }
    }

    public void addItem(LineOverlayItem lineOverlayItem) {
        if (lineOverlayItem != null) {
            this.mLineBound = null;
            createTexture(lineOverlayItem);
            if (lineOverlayItem != null && lineOverlayItem.mPoints.length > 0) {
                ((GLLineOverlay) this.mGLOverlay).addLineItem(lineOverlayItem.mLineProperty);
                this.mItemList.add(lineOverlayItem);
            }
        }
    }

    public void removeLineItem(int i) {
        if (i >= 0 && this.mItemList != null && i <= this.mItemList.size() - 1) {
            this.mItemList.remove(i);
            ((GLLineOverlay) this.mGLOverlay).removeItem(i);
        }
    }

    public boolean clear() {
        this.mLineBound = null;
        return super.clear();
    }

    public E getLineItem(int i) {
        if (i < 0 || i >= this.mItemList.size()) {
            return null;
        }
        return (LineOverlayItem) this.mItemList.get(i);
    }

    public List<E> items() {
        return this.mItemList;
    }

    private void createResTexture(int i, int i2) {
        SimpleMarkerFactory.createLineTexure(this.mMapView, i, i2);
    }

    private void createResTexture(int i, int i2, Bitmap bitmap) {
        SimpleMarkerFactory.createLineTexure(this.mMapView, i, i2, bitmap);
    }

    public Rect getBound() {
        if (this.mItemList.size() == 0) {
            return null;
        }
        if (this.mLineBound == null) {
            int i = 999999999;
            int i2 = 0;
            int i3 = 999999999;
            int i4 = -999999999;
            int i5 = -999999999;
            while (i2 < this.mItemList.size()) {
                LineOverlayItem lineOverlayItem = (LineOverlayItem) this.mItemList.get(i2);
                int length = lineOverlayItem.mPoints.length;
                int i6 = length > 1000 ? 5 : (length <= 500 || length > 1000) ? (length <= 200 || length > 500) ? (length <= 20 || length > 200) ? 1 : 2 : 3 : 4;
                int i7 = i5;
                int i8 = i4;
                int i9 = i3;
                int i10 = i;
                for (int i11 = 0; i11 < length; i11 += i6) {
                    i10 = Math.min(i10, lineOverlayItem.mPoints[i11].x);
                    i9 = Math.min(i9, lineOverlayItem.mPoints[i11].y);
                    i8 = Math.max(i8, lineOverlayItem.mPoints[i11].x);
                    i7 = Math.max(i7, lineOverlayItem.mPoints[i11].y);
                }
                i2++;
                i = i10;
                i3 = i9;
                i4 = i8;
                i5 = i7;
            }
            this.mLineBound = new Rect();
            this.mLineBound.set(i, i3, i4, i5);
        }
        return this.mLineBound;
    }

    public Rect getBoundWithStartAndEnd(GeoPoint geoPoint, GeoPoint geoPoint2) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (this.mItemList.size() == 0) {
            return null;
        }
        Rect rect = new Rect();
        int i7 = 999999999;
        if (geoPoint == null || geoPoint2 == null) {
            i = -999999999;
            i3 = 999999999;
            i2 = -999999999;
        } else {
            i7 = Math.min(geoPoint.x, geoPoint2.x);
            i3 = Math.min(geoPoint.y, geoPoint2.y);
            i2 = Math.max(geoPoint.x, geoPoint2.x);
            i = Math.max(geoPoint.y, geoPoint2.y);
        }
        if (this.mLineBound != null) {
            i4 = Math.min(i7, this.mLineBound.left);
            i3 = Math.min(i3, this.mLineBound.top);
            i6 = Math.max(i2, this.mLineBound.right);
            i5 = Math.max(i, this.mLineBound.bottom);
        } else {
            int i8 = i;
            for (int i9 = 0; i9 < this.mItemList.size(); i9++) {
                LineOverlayItem lineOverlayItem = (LineOverlayItem) this.mItemList.get(i9);
                if (lineOverlayItem != null) {
                    int length = lineOverlayItem.mPoints.length;
                    int i10 = length > 1000 ? 5 : (length <= 500 || length > 1000) ? (length <= 200 || length > 500) ? (length <= 20 || length > 200) ? 1 : 2 : 3 : 4;
                    int i11 = i8;
                    int i12 = i2;
                    int i13 = i3;
                    for (int i14 = 0; i14 < length; i14 += i10) {
                        i7 = Math.min(i7, lineOverlayItem.mPoints[i14].x);
                        i13 = Math.min(i13, lineOverlayItem.mPoints[i14].y);
                        i12 = Math.max(i12, lineOverlayItem.mPoints[i14].x);
                        i11 = Math.max(i11, lineOverlayItem.mPoints[i14].y);
                    }
                    i3 = i13;
                    i2 = i12;
                    i8 = i11;
                }
            }
            i4 = i7;
            i6 = i2;
            i5 = i8;
        }
        rect.set(i4, i3, i6, i5);
        return rect;
    }

    public Rect getBound(GeoPoint geoPoint) {
        if (this.mItemList.size() == 0) {
            return null;
        }
        if (this.mLineBound == null) {
            int i = 999999999;
            int i2 = 0;
            int i3 = 999999999;
            int i4 = -999999999;
            int i5 = -999999999;
            while (i2 < this.mItemList.size()) {
                LineOverlayItem lineOverlayItem = (LineOverlayItem) this.mItemList.get(i2);
                int i6 = i5;
                int i7 = i4;
                int i8 = i3;
                int i9 = i;
                for (int i10 = 0; i10 < lineOverlayItem.mPoints.length; i10++) {
                    i9 = Math.min(i9, lineOverlayItem.mPoints[i10].x);
                    i8 = Math.min(i8, lineOverlayItem.mPoints[i10].y);
                    i7 = Math.max(i7, lineOverlayItem.mPoints[i10].x);
                    i6 = Math.max(i6, lineOverlayItem.mPoints[i10].y);
                    if (i2 == this.mItemList.size() - 1 && i10 == lineOverlayItem.mPoints.length - 1 && geoPoint != null) {
                        i9 = Math.min(i9, geoPoint.x);
                        i8 = Math.min(i8, geoPoint.y);
                        i7 = Math.max(i7, geoPoint.x);
                        i6 = Math.max(i6, geoPoint.y);
                    }
                }
                i2++;
                i = i9;
                i3 = i8;
                i4 = i7;
                i5 = i6;
            }
            this.mLineBound = new Rect();
            this.mLineBound.set(i, i3, i4, i5);
        }
        return this.mLineBound;
    }
}
