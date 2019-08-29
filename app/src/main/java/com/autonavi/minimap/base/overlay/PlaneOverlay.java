package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import com.autonavi.jni.ae.gmap.gloverlay.GLPlaneOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.base.overlay.PlaneOverlayItem;
import java.util.Iterator;

public abstract class PlaneOverlay<E extends PlaneOverlayItem> extends BaseOverlayDelegate<GLPlaneOverlay, E> {
    private int mBgRes;
    private int mBgResId;
    private c mOverlayTextureCallBack;

    public PlaneOverlay(int i, bty bty, int i2) {
        super(i, bty);
        this.mBgRes = i2;
    }

    public PlaneOverlay(bty bty, int i) {
        super(bty);
        this.mBgRes = i;
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLPlaneOverlay(this.mEngineID, this.mMapView.c(), hashCode());
    }

    public void addItem(E e) {
        if (e != null) {
            e.mItemId = ((GLPlaneOverlay) this.mGLOverlay).addPlaneItem(getProperty(e));
            this.mItemList.add(e);
        }
    }

    public boolean clear() {
        Iterator it = this.mItemList.iterator();
        while (it.hasNext()) {
            recycleItem((PlaneOverlayItem) it.next());
        }
        this.mItemList.clear();
        return super.clear();
    }

    public void updateItem(E e) {
        if (e != null && this.mItemList.contains(e)) {
            ((GLPlaneOverlay) this.mGLOverlay).updatePlaneItem(e.mItemId, getProperty(e));
        }
    }

    public void setVisible(boolean z) {
        Iterator it = this.mItemList.iterator();
        while (it.hasNext()) {
            setVisible(((PlaneOverlayItem) it.next()).mItemId, z);
        }
    }

    public void setVisible(long j, boolean z) {
        ((GLPlaneOverlay) this.mGLOverlay).setPlaneItemVisble(j, z);
    }

    public boolean isVisible(long j) {
        return ((GLPlaneOverlay) this.mGLOverlay).getPlaneItemVisible(j);
    }

    private void recycleItem(E e) {
        if (e != null) {
            ((GLPlaneOverlay) this.mGLOverlay).removePlaneItem(e.mItemId);
            this.mMapView.c().a(this.mEngineID, e.mMarker.mID);
        }
    }

    private amd getProperty(E e) {
        if (e.mMarker == null) {
            e.mMarker = createMarker(e.mIndex, e.getBitmap(), 0, 0.0f, 0.0f, false);
        }
        if (this.mBgResId == 0) {
            this.mBgResId = SimpleMarkerFactory.createMarker(this.mBgRes, 0, 0.0f, 0.0f, false, this.mMapView).mID;
        }
        amd amd = new amd();
        amd.a = e.mMarker.mID;
        amd.b = this.mBgResId;
        amd.e = e.mWidth;
        amd.f = e.mHeight;
        amd.c = e.mWinX;
        amd.d = e.mWinY;
        return amd;
    }

    public Marker createMarker(int i, Bitmap bitmap, int i2, float f, float f2, boolean z) {
        return SimpleMarkerFactory.createMarker(generateMarkerId(i), bitmap, i2, f, f2, z, this.mMapView);
    }

    public void setOverlayTextureCallBack(c cVar) {
        this.mOverlayTextureCallBack = cVar;
    }

    public void release() {
        SimpleMarkerFactory.removeMarker(this.mBgResId, this.mMapView, this.mEngineID, this.mOverlayTextureCallBack);
    }
}
