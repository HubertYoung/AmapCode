package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import android.view.View;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import defpackage.*;

public abstract class PointOverlay<E extends PointOverlayItem> extends BaseOverlayDelegate<GLPointOverlay, E> implements xq {
    private static int MAX_ZOOM_LEVEL = 19;
    private static int MIN_ZOOM_LEVEL = 3;
    private static ConcurrentHashMap<Integer, Marker> sMarkerCache = new ConcurrentHashMap<>();
    protected boolean mAutoSetFocus;
    protected int mBubbleAnimator;
    private boolean mIsClearWhenLoseFocus;
    private boolean mMoveToFocus;
    private OnFocusChangedListener mOnFocusChangedListener;
    protected OnItemClickListener mOnItemClickListener;
    protected Marker mOverlayBgFocusMarker;
    protected Marker mOverlayBgMarker;
    protected Marker mOverlayBubbleMarker;
    protected Marker mOverlayDefaultMarker;
    protected Marker mOverlayFocusMarker;

    public interface OnFocusChangedListener<E> {
        void onFocusChanged(boolean z, PointOverlay pointOverlay, E e);
    }

    public interface OnItemClickListener<E> {
        void onItemClick( bty bty, BaseMapOverlay<?, ?> baseMapOverlay, E e);
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void setClearWhenLoseFocus(boolean z) {
        this.mIsClearWhenLoseFocus = z;
    }

    public boolean isClearWhenLoseFocus() {
        return this.mIsClearWhenLoseFocus;
    }

    public void setOnFocusChangedListener(OnFocusChangedListener onFocusChangedListener) {
        this.mOnFocusChangedListener = onFocusChangedListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void resetItemDefaultMarker(int i, Marker marker) {
        if (this.mGLOverlay != null && i >= 0 && i < getSize()) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) getItem(i);
            if (pointOverlayItem != null) {
                pointOverlayItem.mDefaultMarker = marker;
                amc b = this.mMapView.F().b(marker.mID);
                if (b != null) {
                    ((GLPointOverlay) this.mGLOverlay).setPointItemTexture(pointOverlayItem.mItemId, b.a, getBgMarkerMarkerId(pointOverlayItem));
                }
            }
        }
    }

    public boolean onPointOverlayClick(int i) {
        if (!((GLPointOverlay) this.mGLOverlay).isVisible() || !((GLPointOverlay) this.mGLOverlay).isClickable() || i < 0 || i >= this.mItemList.size()) {
            return false;
        }
        PointOverlayItem pointOverlayItem = (PointOverlayItem) this.mItemList.get(i);
        if (pointOverlayItem == null || (pointOverlayItem.mDefaultMarker != null && pointOverlayItem.mDefaultMarker.mID == -999 && this.mOverlayDefaultMarker != null && this.mOverlayDefaultMarker.mID == -999)) {
            return false;
        }
        if (this.mAutoSetFocus) {
            setFocus(i, true);
        }
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(this.mMapView, this, pointOverlayItem);
        }
        return true;
    }

    public PointOverlay(bty bty) {
        super(bty);
        this.mIsClearWhenLoseFocus = false;
        this.mMoveToFocus = true;
        this.mAutoSetFocus = true;
        this.mBubbleAnimator = 0;
        this.mLastFocusedIndex = -1;
    }

    public PointOverlay(int i, bty bty) {
        super(i, bty);
        this.mIsClearWhenLoseFocus = false;
        this.mMoveToFocus = true;
        this.mAutoSetFocus = true;
        this.mBubbleAnimator = 0;
        this.mLastFocusedIndex = -1;
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLPointOverlay(this.mEngineID, this.mMapView.c(), hashCode());
    }

    public void showReversed(boolean z) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).showReversed(z);
        }
    }

    public void setClickList(List< aly > list) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setClickList(list);
        }
    }

    public void setPerspective(boolean z) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setPerspective(z);
        }
    }

    public void setMinDisplayLevel(int i) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setMinDisplayLevel((float) i);
        }
    }

    public void setMaxDisplayLevel(int i) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setMaxDisplayLevel((float) i);
        }
    }

    public void setMinDisplayLevel(float f) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setMinDisplayLevel(f);
        }
    }

    public void setMaxDisplayLevel(float f) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setMaxDisplayLevel(f);
        }
    }

    public float getMinDisplayLevel() {
        if (this.mGLOverlay != null) {
            return ((GLPointOverlay) this.mGLOverlay).getMinDisplayLevel();
        }
        return (float) MIN_ZOOM_LEVEL;
    }

    public float getMaxDisplayLevel() {
        if (this.mGLOverlay != null) {
            return ((GLPointOverlay) this.mGLOverlay).getMaxDisplayLevel();
        }
        return (float) MAX_ZOOM_LEVEL;
    }

    public void setCheckCover(boolean z) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setCheckCover(z);
        }
    }

    public void setMaxCountShown(int i) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setMaxCountShown(i);
        }
    }

    public void setShowFocusTop(boolean z) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setShowFocusTop(z);
        }
    }

    public void setBubbleAnimator(int i) {
        this.mBubbleAnimator = i;
    }

    public void setAnimatorType(int i) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setAnimatorType(i);
        }
    }

    public void setOverlayOnTop(boolean z) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setOverlayOnTop(z);
        }
    }

    public void addItem(E e) {
        if (e != null) {
            GeoPoint geoPoint = e.getGeoPoint();
            if (geoPoint != null) {
                e.onPrepareAddItem(this);
                if (e.mBgMarker == null && this.mOverlayBgMarker == null) {
                    e.mItemId = ((GLPointOverlay) this.mGLOverlay).addPointOverlayItem(geoPoint.x, geoPoint.y, getDefaultMarkerId(e));
                } else {
                    e.mItemId = ((GLPointOverlay) this.mGLOverlay).addPointOverlayItemWithBGMarker(e.getGeoPoint().x, e.getGeoPoint().y, getDefaultMarkerId(e), getBgMarkerMarkerId(e));
                }
                ((GLPointOverlay) this.mGLOverlay).setPointOverlay3DPoint(e.mItemId, geoPoint.x3D, geoPoint.y3D, (float) geoPoint.z3D);
                this.mItemList.add(e);
            }
        }
    }

    public void addItemWithZ(E e) {
        if (e != null && e.getGeoPoint() != null) {
            e.onPrepareAddItem(this);
            e.mItemId = ((GLPointOverlay) this.mGLOverlay).addPointOverlayItemWithZ(e.getGeoPoint().x, e.getGeoPoint().y, e.mZ, getDefaultMarkerId(e));
            this.mItemList.add(e);
        }
    }

    public void addItemWithAngle(E e) {
        if (e != null && e.getGeoPoint() != null) {
            e.onPrepareAddItem(this);
            e.mItemId = ((GLPointOverlay) this.mGLOverlay).addPointOverlayItemWithAngle(e.getGeoPoint().x, e.getGeoPoint().y, getDefaultMarkerId(e), e.mAngle, e.mAngleMode);
            this.mItemList.add(e);
        }
    }

    public void updateItem(E e, GeoPoint geoPoint, int i) {
        if (e != null) {
            GeoPoint geoPoint2 = e.mGeoPoint;
            if (e.mAngle == i && geoPoint2.x == geoPoint.x && geoPoint2.y == geoPoint.y && geoPoint2.x3D == geoPoint.x3D && geoPoint2.y3D == geoPoint.y3D && geoPoint2.z3D == geoPoint.z3D) {
                e.mGeoPoint = geoPoint;
                return;
            }
            e.mGeoPoint = geoPoint;
            e.mAngle = i;
            ((GLPointOverlay) this.mGLOverlay).upDatePointParam(e.mItemId, geoPoint.x, geoPoint.y, geoPoint.x3D, geoPoint.y3D, (float) geoPoint.z3D, (float) i);
        }
    }

    private int getFocusMarkerId(E e) {
        if (e.mFocusMarker != null) {
            return e.mFocusMarker.mID;
        }
        if (this.mOverlayFocusMarker != null) {
            return this.mOverlayFocusMarker.mID;
        }
        return getDefaultMarkerId(e);
    }

    private int getDefaultMarkerId(E e) {
        if (e.mDefaultMarker != null) {
            return e.mDefaultMarker.mID;
        }
        if (this.mOverlayDefaultMarker != null) {
            return this.mOverlayDefaultMarker.mID;
        }
        return -999;
    }

    private int getDefaultMarkerAnchor(E e) {
        if (e.mDefaultMarker != null) {
            return e.mDefaultMarker.mAnchor;
        }
        if (this.mOverlayDefaultMarker != null) {
            return this.mOverlayDefaultMarker.mAnchor;
        }
        return -999;
    }

    private int getBubbleMarkerId(E e) {
        if (e.mBubbleMarker != null) {
            return e.mBubbleMarker.mID;
        }
        if (this.mOverlayBubbleMarker != null) {
            return this.mOverlayBubbleMarker.mID;
        }
        return -999;
    }

    private int getBubbleMarkerAnchor(E e) {
        if (e.mBubbleMarker != null) {
            return e.mBubbleMarker.mAnchor;
        }
        if (this.mOverlayBubbleMarker != null) {
            return this.mOverlayBubbleMarker.mAnchor;
        }
        return 4;
    }

    private int getBgMarkerMarkerId(E e) {
        if (e.mBgMarker != null) {
            return e.mBgMarker.mID;
        }
        if (this.mOverlayBgMarker != null) {
            return this.mOverlayBgMarker.mID;
        }
        return -999;
    }

    private int getBgFocusMarkerId(E e) {
        if (e.mBgFocusMarker != null) {
            return e.mBgFocusMarker.mID;
        }
        if (this.mOverlayBgFocusMarker != null) {
            return this.mOverlayBgFocusMarker.mID;
        }
        return getBgMarkerMarkerId(e);
    }

    private int getBgFocusMarkerMarkerAnchor(E e) {
        if (e.mBgFocusMarker != null) {
            return e.mBgFocusMarker.mAnchor;
        }
        if (this.mOverlayBgFocusMarker != null) {
            return this.mOverlayBgFocusMarker.mAnchor;
        }
        return getBgMarkerMarkerAnchor(e);
    }

    private int getBgMarkerMarkerAnchor(E e) {
        if (e.mBgMarker != null) {
            return e.mBgMarker.mAnchor;
        }
        if (this.mOverlayBgMarker != null) {
            return this.mOverlayBgMarker.mAnchor;
        }
        return 4;
    }

    public void setFocus(PointOverlayItem pointOverlayItem, boolean z) {
        if (pointOverlayItem != null) {
            int indexOf = this.mItemList.indexOf(pointOverlayItem);
            if (indexOf >= 0) {
                setFocus(indexOf, z);
            }
        }
    }

    public void setFocus(int i, boolean z) {
        setFocusAndScale(i, z, -9999);
    }

    public void setFocusAndScale(int i, boolean z, int i2) {
        if (i >= 0 && i < this.mItemList.size()) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) this.mItemList.get(i);
            if (pointOverlayItem != null) {
                boolean z2 = this.mLastFocusedIndex != i;
                this.mLastFocusedIndex = i;
                pointOverlayItem.onPrepareSetFocus(this);
                ((GLPointOverlay) this.mGLOverlay).setFocus(i, getFocusMarkerId(pointOverlayItem), getBubbleMarkerId(pointOverlayItem), getBubbleMarkerAnchor(pointOverlayItem), this.mBubbleAnimator, getBgFocusMarkerId(pointOverlayItem), getBgFocusMarkerMarkerAnchor(pointOverlayItem));
                if (!z || !this.mMoveToFocus) {
                    if (i2 > 0) {
                        this.mMapView.a(300, (float) i2, -9999, -9999, pointOverlayItem.getGeoPoint().x, pointOverlayItem.getGeoPoint().y);
                    }
                } else if (i2 > 0) {
                    this.mMapView.a(300, (float) i2, -9999, -9999, pointOverlayItem.getGeoPoint().x, pointOverlayItem.getGeoPoint().y);
                } else {
                    this.mMapView.a((GLGeoPoint) pointOverlayItem.getGeoPoint());
                }
                if (this.mOnFocusChangedListener != null && z2) {
                    this.mOnFocusChangedListener.onFocusChanged(true, this, pointOverlayItem);
                }
            }
        }
    }

    public void setAutoSetFocus(boolean z) {
        this.mAutoSetFocus = z;
    }

    public boolean isAutoSetFocus() {
        return this.mAutoSetFocus;
    }

    public void setMoveToFocus(boolean z) {
        this.mMoveToFocus = z;
    }

    public boolean isMoveToFocus() {
        return this.mMoveToFocus;
    }

    public void clearFocus() {
        int i = this.mLastFocusedIndex;
        super.clearFocus();
        if (this.mOnFocusChangedListener != null) {
            this.mOnFocusChangedListener.onFocusChanged(false, this, getItem(i));
        }
    }

    public Marker createMarker(int i, int i2) {
        return createMarker(i, i2, 0.0f, 0.0f);
    }

    public Marker createMarker(int i, Bitmap bitmap, int i2, boolean z) {
        return createMarker(i, bitmap, i2, 0.0f, 0.0f, z);
    }

    public Marker createMarker(int i, int i2, float f, float f2) {
        return SimpleMarkerFactory.createMarker(i, i2, f, f2, this.mMapView);
    }

    public Marker createMarker(int i, Bitmap bitmap, int i2, float f, float f2, boolean z) {
        return SimpleMarkerFactory.createMarker(generateMarkerId(i), bitmap, i2, f, f2, z, this.mMapView);
    }

    public Marker testCreateMarkerFromViewWithResId(int i, View view, int i2, float f, float f2, boolean z) {
        return SimpleMarkerFactory.createMarker(i, view, i2, f, f2, z, this.mMapView);
    }

    public Marker createMarker(int i, View view, int i2, float f, float f2, boolean z) {
        return SimpleMarkerFactory.createMarker(generateMarkerId(i), view, i2, f, f2, z, this.mMapView);
    }

    /* access modifiers changed from: protected */
    public boolean removeAll(List<E> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        int i = 0;
        while (i < list.size()) {
            try {
                removeItem(list.get(i));
                i++;
            } catch (Exception unused) {
                return false;
            }
        }
        return true;
    }

    public int getItemIndex(PointOverlayItem pointOverlayItem) {
        if (pointOverlayItem == null || getSize() <= 0) {
            return -1;
        }
        return this.mItemList.indexOf(pointOverlayItem);
    }

    public void setHideIconWhenCovered(boolean z) {
        if (this.mGLOverlay != null) {
            ((GLPointOverlay) this.mGLOverlay).setHideIconWhenCovered(z);
        }
    }

    public void setPointItemVisble(int i, boolean z, boolean z2) {
        if (this.mGLOverlay != null && i >= 0 && i < getSize()) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) getItem(i);
            if (pointOverlayItem != null) {
                ((GLPointOverlay) this.mGLOverlay).setPointItemVisble(pointOverlayItem.mItemId, z, z2);
                pointOverlayItem.setIconVisible(z);
                pointOverlayItem.setBgVisible(z2);
            }
        }
    }

    public void setPointItemVisble(PointOverlayItem pointOverlayItem, boolean z, boolean z2) {
        if (pointOverlayItem != null) {
            ((GLPointOverlay) this.mGLOverlay).setPointItemVisble(pointOverlayItem.mItemId, z, z2);
            pointOverlayItem.setIconVisible(z);
            pointOverlayItem.setBgVisible(z2);
        }
    }

    public boolean isPointIconVisible(int i) {
        if (this.mGLOverlay != null && i >= 0 && i < getSize()) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) getItem(i);
            if (pointOverlayItem != null) {
                return pointOverlayItem.isIconVisible();
            }
        }
        return true;
    }

    public boolean isPointBgVisible(int i) {
        if (this.mGLOverlay != null && i >= 0 && i < getSize()) {
            PointOverlayItem pointOverlayItem = (PointOverlayItem) getItem(i);
            if (pointOverlayItem != null) {
                return pointOverlayItem.isBgVisible();
            }
        }
        return true;
    }

    public float[] getDisplayScale() {
        if (this.mGLOverlay != null) {
            return ((GLPointOverlay) this.mGLOverlay).getDisplayScale();
        }
        return new float[0];
    }
}
