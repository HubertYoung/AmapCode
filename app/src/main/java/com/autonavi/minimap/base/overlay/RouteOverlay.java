package com.autonavi.minimap.base.overlay;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.PolylineDrawType;
import com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay.Listener;
import com.autonavi.jni.ae.gmap.gloverlay.GLRouteOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.base.overlay.RouteItem;
import com.autonavi.minimap.base.overlay.RouteItem.Property;
import java.util.List;

public abstract class RouteOverlay<E extends RouteItem> extends BaseOverlayDelegate<GLRouteOverlay, E> {
    public static final int LINE_SEG_CAP = 50;
    private OnLineOverlayClickListener mOnLineOverlayClickListener;
    private int[] mPassedRouteLineColors;

    public interface OnLineOverlayClickListener<E> {
        void onLineOverlayClick(bty bty, BaseMapOverlay<?, ?> baseMapOverlay, long j);
    }

    public int getPassedRoadBoneColor() {
        return 0;
    }

    public int getPassedRoadBorderColor() {
        return 0;
    }

    public int getPassedRoadColor() {
        return 0;
    }

    public void setOnLineOverlayClickListener(OnLineOverlayClickListener onLineOverlayClickListener) {
        this.mOnLineOverlayClickListener = onLineOverlayClickListener;
    }

    public boolean onLineOverlayClick(long j) {
        if (!((GLRouteOverlay) this.mGLOverlay).isVisible() || !((GLRouteOverlay) this.mGLOverlay).isClickable()) {
            return false;
        }
        if (this.mOnLineOverlayClickListener != null) {
            this.mOnLineOverlayClickListener.onLineOverlayClick(this.mMapView, this, j);
        }
        return true;
    }

    public RouteOverlay(bty bty) {
        super(bty);
    }

    public RouteOverlay(int i, bty bty) {
        super(i, bty);
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLRouteOverlay(this.mMapView.ad(), this.mMapView.c(), hashCode());
    }

    private void createTexture(Property property) {
        if (property != null) {
            if (property.mSimple3DFillResId != -1) {
                createResTexture(property.type, property.mSimple3DFillResId);
            }
            createResTexture(property.type, property.mFilledResId);
            if (property.mBgResId != -1) {
                createResTexture(property.type, property.mBgResId);
            }
        }
    }

    public void addItem(RouteItem routeItem) {
        addItem(routeItem, true);
    }

    public void addItem(RouteItem routeItem, boolean z) {
        if (routeItem != null) {
            Property[] properties = routeItem.properties();
            if (properties != null && properties.length > 0) {
                if (this.mPassedRouteLineColors == null) {
                    this.mPassedRouteLineColors = new int[3];
                }
                this.mPassedRouteLineColors[0] = getPassedRoadColor();
                this.mPassedRouteLineColors[1] = getPassedRoadBorderColor();
                this.mPassedRouteLineColors[2] = getPassedRoadBoneColor();
                for (Property createTexture : properties) {
                    createTexture(createTexture);
                }
                if (!(routeItem.getLineItem() == null || routeItem.getLineItem().pLineData == 0 || routeItem.getLineItem().nDataSize == 0)) {
                    if (z) {
                        ((GLRouteOverlay) this.mGLOverlay).addRouteItem(routeItem.getScene(), routeItem.getProperties(), routeItem.isSelected(), routeItem.getLineItem().pLineData, routeItem.getLineItem().nDataSize, this.mPassedRouteLineColors, routeItem.isRefreshMap());
                    } else {
                        ((GLRouteOverlay) this.mGLOverlay).addRouteItemWithoutName(routeItem.getScene(), routeItem.getProperties(), routeItem.isSelected(), routeItem.getLineItem().pLineData, routeItem.getLineItem().nDataSize, this.mPassedRouteLineColors, routeItem.isRefreshMap());
                    }
                    this.mItemList.add(routeItem);
                }
            }
        }
    }

    public void addRouteName(boolean z) {
        ((GLRouteOverlay) this.mGLOverlay).addRouteName(z);
    }

    public void removeLineItem(int i) {
        if (i >= 0 && this.mItemList != null && i <= this.mItemList.size() - 1) {
            this.mItemList.remove(i);
            ((GLRouteOverlay) this.mGLOverlay).removeItem(i);
        }
    }

    public E getLineItem(int i) {
        if (i < 0 || i >= this.mItemList.size()) {
            return null;
        }
        return (RouteItem) this.mItemList.get(i);
    }

    public List<E> items() {
        return this.mItemList;
    }

    private void createResTexture(int i, int i2) {
        SimpleMarkerFactory.createLineTexure(this.mMapView, i, i2);
    }

    public boolean clear() {
        return super.clear();
    }

    public void removeRouteName() {
        ((GLRouteOverlay) this.mGLOverlay).removeRouteName(true);
    }

    public boolean checkIntersectionRect(alw alw) {
        return ((GLRouteOverlay) this.mGLOverlay).checkIntersectionRect(alw);
    }

    public void setOverlayPriority(int i) {
        if (this.mGLOverlay != null) {
            ((GLRouteOverlay) this.mGLOverlay).setOverlayPriority(i);
        }
    }

    public void setOverlayItemPriority(int i) {
        if (this.mGLOverlay != null) {
            ((GLRouteOverlay) this.mGLOverlay).setOverlayItemPriority(i);
        }
    }

    public void setDrawType(PolylineDrawType polylineDrawType) {
        if (this.mGLOverlay != null) {
            ((GLRouteOverlay) this.mGLOverlay).setDrawType(polylineDrawType);
        }
    }

    public void setGrownAnimation(int i) {
        if (this.mGLOverlay != null) {
            ((GLRouteOverlay) this.mGLOverlay).setGrownAnimation(i);
        }
    }

    public void setListener(Listener listener) {
        if (this.mGLOverlay != null && listener != null) {
            ((GLRouteOverlay) this.mGLOverlay).setListener(listener);
        }
    }
}
