package com.autonavi.map.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.autonavi.ae.gmap.AMapSurface;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;

import defpackage.akq;
import defpackage.aky;
import defpackage.alg;
import defpackage.aln;
import defpackage.ami;
import defpackage.amj;
import defpackage.amn;
import defpackage.ank;
import defpackage.awb;
import defpackage.bro;
import defpackage.brx;
import defpackage.bts;
import defpackage.btv;
import defpackage.btw;
import defpackage.bty;
import defpackage.ccz;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class MapManager implements bro {
    private final awb mMapEventService;
    private final bro mRealMapManager = ((bro) ank.a(bro.class));

    public void init( Context context, akq akq, AMapSurface aMapSurface, ImageView imageView) {
    }

    public MapManager(Context context, akq akq, AMapSurface aMapSurface, ImageView imageView) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.init(context, akq, aMapSurface, imageView);
        }
        this.mMapEventService = ( awb ) a.a.a(awb.class);
        if (this.mRealMapManager != null && this.mMapEventService != null) {
            this.mMapEventService.b(( btw ) this.mRealMapManager.getOverlayManager());
        }
    }

    public void resetMapView(Bitmap bitmap) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.resetMapView(bitmap);
        }
    }

    @NonNull
    public IOverlayManager getOverlayManager() {
        if (this.mRealMapManager != null) {
            return this.mRealMapManager.getOverlayManager();
        }
        return null;
    }

    public void release() {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.release();
        }
        if (this.mMapEventService != null) {
            this.mMapEventService.a();
        }
    }

    public void popMapEventListener(btw btw) {
        if (this.mMapEventService != null) {
            this.mMapEventService.a(btw);
        }
    }

    public void pushMapEventListener(btw btw) {
        if (this.mMapEventService != null) {
            this.mMapEventService.b(btw);
        }
    }

    public void addAllMapEventListener( bts bts) {
        if (this.mMapEventService != null) {
            this.mMapEventService.a(bts);
        }
    }

    public void removeAllMapEventListener(bts bts) {
        if (this.mMapEventService != null) {
            this.mMapEventService.b(bts);
        }
    }

    public void restoreMapStateWithouMapMode() {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.restoreMapStateWithouMapMode();
        }
    }

    public void saveMapState() {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.saveMapState();
        }
    }

    public void onMapTipInfo(int i, String str) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMapTipInfo(i, str);
        }
    }

    public void onMapTipClear(int i) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMapTipClear(i);
        }
    }

    public void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onUserMapTouchEvent(i, motionEvent);
        }
    }

    public void onShowPress(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onShowPress(i, motionEvent);
        }
    }

    public void onLongPress(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onLongPress(i, motionEvent);
        }
    }

    public void onDoubleTap(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onDoubleTap(i, motionEvent);
        }
    }

    public void onMoveBegin(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMoveBegin(i, motionEvent);
        }
    }

    public void onZoomOutTap(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onZoomOutTap(i, motionEvent);
        }
    }

    public void onScaleRotateBegin(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onScaleRotateBegin(i, motionEvent);
        }
    }

    public void onHoveBegin(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onHoveBegin(i, motionEvent);
        }
    }

    public boolean onSingleTapUp(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onSingleTapUp(i, motionEvent);
        }
        return false;
    }

    public void onEngineActionGesture(int i, alg alg) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onEngineActionGesture(i, alg);
        }
    }

    public boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onFling(i, motionEvent, motionEvent2, f, f2);
        }
        return false;
    }

    public void onMapSizeChange(int i) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMapSizeChange(i);
        }
    }

    public void onMapLevelChange(int i, boolean z) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMapLevelChange(i, z);
        }
    }

    public void onEngineVisible(int i, boolean z) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onEngineVisible(i, z);
        }
    }

    public void onGpsBtnClick() {
        if (this.mMapEventService != null) {
            this.mMapEventService.b();
        }
    }

    public void updateLockMapAngleState(float f) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.updateLockMapAngleState(f);
        }
    }

    public void updateLockMapAngleState( bty bty, float f) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.updateLockMapAngleState(bty, f);
        }
    }

    public void setCameraDegree(int i) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setCameraDegree(i);
        }
    }

    public void onMotionFinished(int i, int i2) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMotionFinished(i, i2);
        }
    }

    public void onPointOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onPointOverlayClick(i, gLAmapFocusHits);
        }
    }

    public void onLineOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onLineOverlayClick(i, gLAmapFocusHits);
        }
    }

    public void onNoFeatureClick(int i) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onNoFeatureClick(i);
        }
    }

    public boolean onBlankClick(int i) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onBlankClick(i);
        }
        return false;
    }

    public boolean onNoBlankClick(int i) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onNoBlankClick(i);
        }
        return false;
    }

    public void onHorizontalMove(int i, float f) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onHorizontalMove(i, f);
        }
    }

    public void onHorizontalMoveEnd(int i) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onHorizontalMoveEnd(i);
        }
    }

    public void onOfflineMap(int i, String str, int i2) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onOfflineMap(i, str, i2);
        }
    }

    public void onRealCityAnimateFinish(int i) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onRealCityAnimateFinish(i);
        }
    }

    public void beforeDrawFrame(int i, GLMapState gLMapState) {
        if (this.mMapEventService != null) {
            this.mMapEventService.beforeDrawFrame(i, gLMapState);
        }
    }

    public void afterDrawFrame(int i, GLMapState gLMapState) {
        if (this.mMapEventService != null) {
            this.mMapEventService.afterDrawFrame(i, gLMapState);
        }
    }

    public void onMapAnimationFinished(int i, int i2) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMapAnimationFinished(i, i2);
        }
    }

    public void onMapAnimationFinished(int i, aln aln) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMapAnimationFinished(i, aln);
        }
    }

    public void onSelectSubWayActive(int i, byte[] bArr) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onSelectSubWayActive(i, bArr);
        }
    }

    public void onMapRenderCompleted(int i) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onMapRenderCompleted(i);
        }
    }

    public void onScreenShotFinished(int i, long j) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onScreenShotFinished(i, j);
        }
    }

    public void onScreenShotFinished(int i, Bitmap bitmap) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onScreenShotFinished(i, bitmap);
        }
    }

    public void onScreenShotFinished(int i, String str) {
        if (this.mMapEventService != null) {
            this.mMapEventService.onScreenShotFinished(i, str);
        }
    }

    public brx getMapViewManager() {
        if (this.mRealMapManager != null) {
            return this.mRealMapManager.getMapViewManager();
        }
        return null;
    }

    public bty getMapView() {
        if (this.mRealMapManager != null) {
            return this.mRealMapManager.getMapView();
        }
        return null;
    }

    public bty getMapView(int i) {
        if (this.mRealMapManager != null) {
            return this.mRealMapManager.getMapView(i);
        }
        return null;
    }

    public void setMapEventListener(int i, btv btv) {
        if (this.mMapEventService != null) {
            this.mMapEventService.a(i, btv);
        }
    }

    public int getMapMode() {
        if (this.mRealMapManager != null) {
            return this.mRealMapManager.getMapMode();
        }
        return 0;
    }

    public boolean isMapSurfaceCreated() {
        if (this.mRealMapManager != null) {
            return this.mRealMapManager.isMapSurfaceCreated();
        }
        return false;
    }

    public void setMapSurfaceCreated(boolean z) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setMapSurfaceCreated(z);
        }
    }

    public void notifyMapModeChange(int i) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.notifyMapModeChange(i);
        }
    }

    public void addMapModeChangeListener(a aVar) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.addMapModeChangeListener(aVar);
        }
    }

    public void removeMapModeChangeListener(a aVar) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.removeMapModeChangeListener(aVar);
        }
    }

    public void renderPauseDelay() {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.renderPauseDelay();
        }
    }

    public void indoorBuildingActivity(int i, ami ami) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.indoorBuildingActivity(i, ami);
        }
    }

    public void refreshScaleLineView(int i) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.refreshScaleLineView(i);
        }
    }

    public void setScaleColor(int i, int i2, int i3) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setScaleColor(i, i2, i3);
        }
    }

    public void paintCompass(int i) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.paintCompass(i);
        }
    }

    public void fadeCompassWidget(int i) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.fadeCompassWidget(i);
        }
    }

    public void setFrontViewVisibility(int i, boolean z) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setFrontViewVisibility(i, z);
        }
    }

    public void setIndoorBuildingListener(a aVar) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setIndoorBuildingListener(aVar);
        }
    }

    public void addIndoorBuildingListener(a aVar) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.addIndoorBuildingListener(aVar);
        }
    }

    public void removeIndoorBuidingListener(a aVar) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.removeIndoorBuidingListener(aVar);
        }
    }

    public void setMapWidgetListener( amn amn) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setMapWidgetListener(amn);
        }
    }

    public void doMutex() {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.doMutex();
        }
    }

    public boolean checkMutex() {
        if (this.mRealMapManager != null) {
            return this.mRealMapManager.checkMutex();
        }
        return false;
    }

    public void setIRealtimeBusStateListener( ccz ccz) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setIRealtimeBusStateListener(ccz);
        }
    }

    public void setEyrieMapGestureListener( amj amj, aky aky) {
        if (this.mRealMapManager != null) {
            this.mRealMapManager.setEyrieMapGestureListener(amj, aky);
        }
    }

    public boolean onClick(int i, float f, float f2) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onClick(i, f, f2);
        }
        return false;
    }

    public boolean onDoubleClick(int i, float f, float f2) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onDoubleClick(i, f, f2);
        }
        return false;
    }

    public boolean onLongPress(int i, float f, float f2) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onLongPress(i, f, f2);
        }
        return false;
    }

    public boolean onMontionStart(int i, float f, float f2) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onMontionStart(i, f, f2);
        }
        return false;
    }

    public boolean onMontionFinish(int i) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onMontionFinish(i);
        }
        return false;
    }

    public boolean onTouchEvent(int i, MotionEvent motionEvent) {
        if (this.mMapEventService != null) {
            return this.mMapEventService.onTouchEvent(i, motionEvent);
        }
        return false;
    }
}
