package com.amap.bundle.drivecommon.overlay;

import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OnDrawOverlayListener;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayDrawEvent;
import com.autonavi.minimap.base.overlay.RouteItem;
import com.autonavi.minimap.base.overlay.RouteOverlay;
import com.autonavi.minimap.base.overlay.RouteOverlay.OnLineOverlayClickListener;

public class RouteCarResultRouteOverlay extends RouteOverlay {
    /* access modifiers changed from: private */
    public transient OnLineOverlayClickListener mClickListener;
    private NavigationPath mNavigationPath;
    /* access modifiers changed from: private */
    public int mPathIndex = -1;

    public void onPause() {
    }

    public void onResume() {
    }

    public void setBoundCache(boolean z) {
    }

    public RouteCarResultRouteOverlay(bty bty) {
        super(bty);
    }

    public int addLineItem(RouteItem routeItem) {
        addItem(routeItem);
        if (routeItem.isSelected()) {
            setRouteRTLog();
        }
        return this.mItemList.size() - 1;
    }

    public void setOnLineOverlayClickListener(OnLineOverlayClickListener onLineOverlayClickListener) {
        this.mClickListener = onLineOverlayClickListener;
        super.setOnLineOverlayClickListener(new OnLineOverlayClickListener() {
            public final void onLineOverlayClick(bty bty, BaseMapOverlay baseMapOverlay, long j) {
                if (RouteCarResultRouteOverlay.this.mClickListener != null) {
                    RouteCarResultRouteOverlay.this.mClickListener.onLineOverlayClick(bty, baseMapOverlay, (long) RouteCarResultRouteOverlay.this.mPathIndex);
                }
            }
        });
    }

    public int getGlLineCode() {
        return getGLOverlay().getCode();
    }

    public NavigationPath getNavigationPath() {
        return this.mNavigationPath;
    }

    private void setRouteRTLog() {
        td.a().a(5);
        if (this.mGLOverlay != null && td.a().a == 5) {
            this.mGLOverlay.setDrawOverlayListener(new OnDrawOverlayListener() {
                public final void onProcessOverlayDrawEvent(OverlayDrawEvent overlayDrawEvent) {
                    td.a().a(6);
                    RouteCarResultRouteOverlay.this.mGLOverlay.setDrawOverlayListener(null);
                    if (bno.a && overlayDrawEvent != null && overlayDrawEvent.getOverlay() != null) {
                        StringBuilder sb = new StringBuilder("onProcessOverlayDrawEvent   ");
                        sb.append(overlayDrawEvent.getOverlay().getClass().getSimpleName());
                        sb.append("  EventType:");
                        sb.append(overlayDrawEvent.getEventType());
                        AMapLog.d("RouteCarResultRouteOverlay", sb.toString());
                    }
                }
            });
            this.mGLOverlay.setOverlayDrawObserverType(1);
        }
    }
}
