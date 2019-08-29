package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLMapItemMarkerInfo;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePointMiniAppOverlay extends BaseMiniAppOverlay<GLPointOverlay, MiniAppPointOverlayItem> {
    private List<OnItemClickListener> mItemClickListeners = new ArrayList();

    public interface OnItemClickListener {
        void onItemClick(MiniAppPointOverlayItem miniAppPointOverlayItem, int i);
    }

    public BasePointMiniAppOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        this.mGLOverlay = new MiniAppGLPointOverlay(this.mEngineID, akq.b(), hashCode());
    }

    public void addItem(MiniAppPointOverlayItem miniAppPointOverlayItem) {
        if (miniAppPointOverlayItem != null && miniAppPointOverlayItem.mGeoPoint != null && miniAppPointOverlayItem.mDefaultMarker != null) {
            GeoPoint geoPoint = miniAppPointOverlayItem.mGeoPoint;
            miniAppPointOverlayItem.mItemId = ((GLPointOverlay) this.mGLOverlay).addPointOverlayItem(geoPoint.x, geoPoint.y, miniAppPointOverlayItem.mDefaultMarker.mID);
            this.mItemList.add(miniAppPointOverlayItem);
        }
    }

    public void addItemMarker(MiniAppPointOverlayItem miniAppPointOverlayItem, GLMapItemMarkerInfo gLMapItemMarkerInfo) {
        ((GLPointOverlay) this.mGLOverlay).addPointItemMarker(miniAppPointOverlayItem.mItemId, gLMapItemMarkerInfo);
    }

    public void getItemMarker(MiniAppPointOverlayItem miniAppPointOverlayItem, int i) {
        ((GLPointOverlay) this.mGLOverlay).getPointItemMarker(miniAppPointOverlayItem.mItemId, i);
    }

    public int getItemMarkerCount(MiniAppPointOverlayItem miniAppPointOverlayItem) {
        return ((GLPointOverlay) this.mGLOverlay).getPointItemMarkerCount(miniAppPointOverlayItem.mItemId);
    }

    public void updateItem(MiniAppPointOverlayItem miniAppPointOverlayItem, GeoPoint geoPoint, int i) {
        if (miniAppPointOverlayItem != null && miniAppPointOverlayItem.mGeoPoint != null) {
            GeoPoint geoPoint2 = miniAppPointOverlayItem.mGeoPoint;
            if (miniAppPointOverlayItem.mAngle == i && geoPoint2.x == geoPoint.x && geoPoint2.y == geoPoint.y && geoPoint2.x3D == geoPoint.x3D && geoPoint2.y3D == geoPoint.y3D && geoPoint2.z3D == geoPoint.z3D) {
                miniAppPointOverlayItem.mGeoPoint = geoPoint;
                return;
            }
            miniAppPointOverlayItem.mGeoPoint = geoPoint;
            miniAppPointOverlayItem.mAngle = i;
            ((GLPointOverlay) this.mGLOverlay).upDatePointParam(miniAppPointOverlayItem.mItemId, geoPoint.x, geoPoint.y, geoPoint.x3D, geoPoint.y3D, (float) geoPoint.z3D, (float) i);
        }
    }

    public void updateItem(MiniAppPointOverlayItem miniAppPointOverlayItem, int i) {
        ((GLPointOverlay) this.mGLOverlay).setPointItemTexture(miniAppPointOverlayItem.mItemId, i, 0);
    }

    public void setPointItemVisible(int i, boolean z) {
        if (this.mGLOverlay != null && i >= 0 && i < getSize()) {
            setPointItemVisible((MiniAppPointOverlayItem) getItem(i), z);
        }
    }

    public void setPointItemVisible(MiniAppPointOverlayItem miniAppPointOverlayItem, boolean z) {
        if (miniAppPointOverlayItem != null) {
            ((GLPointOverlay) this.mGLOverlay).setPointItemVisble(miniAppPointOverlayItem.mItemId, z, false);
            miniAppPointOverlayItem.mVisible = z;
        }
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListeners.add(onItemClickListener);
    }

    public void removeOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListeners.remove(onItemClickListener);
    }

    public void dispatchItemClick(MiniAppPointOverlayItem miniAppPointOverlayItem, long j) {
        for (OnItemClickListener onItemClick : this.mItemClickListeners) {
            try {
                onItemClick.onItemClick(miniAppPointOverlayItem, (int) j);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
