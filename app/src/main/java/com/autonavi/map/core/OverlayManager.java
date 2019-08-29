package com.autonavi.map.core;

import android.content.Context;
import android.view.MotionEvent;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager.a;
import com.autonavi.map.core.IOverlayManager.b;
import com.autonavi.map.core.IOverlayManager.c;
import com.autonavi.map.fragmentcontainer.page.IPoiDetailDelegate;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.map.TrafficPointOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import java.util.List;

public class OverlayManager implements IOverlayManager {
    private final IOverlayManager mRealOverlayManager = ((IOverlayManager) ank.a(IOverlayManager.class));

    public void init(bro bro, bty bty, Context context, List<bts> list) {
    }

    public OverlayManager(bro bro, bty bty, Context context, List<bts> list) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.init(bro, bty, context, list);
        }
    }

    public cdx getGpsLayer() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getGpsLayer();
        }
        return null;
    }

    public xq getUniversalOverlay(UvOverlay uvOverlay) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getUniversalOverlay(uvOverlay);
        }
        return null;
    }

    public MapPointOverlay getGeoCodeOverlay() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getGeoCodeOverlay();
        }
        return null;
    }

    public MapPointOverlay getMapPointOverlay() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getMapPointOverlay();
        }
        return null;
    }

    public TrafficPointOverlay getTrafficPointOverlay() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getTrafficPointOverlay();
        }
        return null;
    }

    public a getDeepInfoOverlayManager() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getDeepInfoOverlayManager();
        }
        return null;
    }

    public void removeWhenMapDestroy() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.removeWhenMapDestroy();
        }
    }

    public void restoreWhenMapCreate() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.restoreWhenMapCreate();
        }
    }

    public int saveFocus() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.saveFocus();
        }
        return 0;
    }

    public List<c> solveSavedFocusWithKey(int i, boolean z) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.solveSavedFocusWithKey(i, z);
        }
        return null;
    }

    public void recoverSubwayHighlight() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.recoverSubwayHighlight();
        }
    }

    public void setPoiDetailDelegate(IPoiDetailDelegate iPoiDetailDelegate) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.setPoiDetailDelegate(iPoiDetailDelegate);
        }
    }

    public void setMapCommonOverlayListener(btu btu) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.setMapCommonOverlayListener(btu);
        }
    }

    public void clearAllFocus() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.clearAllFocus();
        }
    }

    public void dimissTips() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.dimissTips();
        }
    }

    public void handleTrafficItemClick(PageBundle pageBundle) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.handleTrafficItemClick(pageBundle);
        }
    }

    public void clearAllMapPointRequest() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.clearAllMapPointRequest();
        }
    }

    public void clearScenicSelectMapPois() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.clearScenicSelectMapPois();
        }
    }

    public boolean isScenicSelected() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.isScenicSelected();
        }
        return false;
    }

    public void resetMapPointAnimatorType() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.resetMapPointAnimatorType();
        }
    }

    public void deleteSaveFocusKey(int i) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.deleteSaveFocusKey(i);
        }
    }

    public brp getAffectAreaOverlayManager() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getAffectAreaOverlayManager();
        }
        return null;
    }

    public void showTrafficFooter(int i, int i2, int i3, int i4, boolean z) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.showTrafficFooter(i, i2, i3, i4, z);
        }
    }

    public void setIMapPointRequestingCallBack(b bVar) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.setIMapPointRequestingCallBack(bVar);
        }
    }

    public int getGpsAngle() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.getGpsAngle();
        }
        return 0;
    }

    public void setGPSShowMode(int i) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.setGPSShowMode(i);
        }
    }

    public void setGPSCenterLocked(boolean z) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.setGPSCenterLocked(z);
        }
    }

    public boolean isGPSVisible() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.isGPSVisible();
        }
        return false;
    }

    public void setGPSVisible(boolean z) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.setGPSVisible(z);
        }
    }

    public void showGpsFooter() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.showGpsFooter();
        }
    }

    public void setIRealtimeBusStateListener(ccz ccz) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.setIRealtimeBusStateListener(ccz);
        }
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapTouchEvent(motionEvent);
        }
        return false;
    }

    public boolean onMapShowPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapShowPress(motionEvent, geoPoint);
        }
        return false;
    }

    public boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapLongPress(motionEvent, geoPoint);
        }
        return false;
    }

    public boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapSingleClick(motionEvent, geoPoint);
        }
        return false;
    }

    public boolean onMapSingleClick(int i, MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapSingleClick(i, motionEvent, geoPoint);
        }
        return false;
    }

    public boolean onEngineActionGesture(alg alg) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onEngineActionGesture(alg);
        }
        return false;
    }

    public boolean onMapDoubleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapDoubleClick(motionEvent, geoPoint);
        }
        return false;
    }

    public boolean onMapSizeChange() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapSizeChange();
        }
        return false;
    }

    public boolean onMapLevelChange(boolean z) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapLevelChange(z);
        }
        return false;
    }

    public boolean onMapCompassClick() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapCompassClick();
        }
        return false;
    }

    public boolean onMapMotionFinish() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapMotionFinish();
        }
        return false;
    }

    public boolean onMapMotionStop() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onMapMotionStop();
        }
        return false;
    }

    public boolean onPointOverlayClick(long j, int i) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onPointOverlayClick(j, i);
        }
        return false;
    }

    public boolean onLineOverlayClick(long j) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onLineOverlayClick(j);
        }
        return false;
    }

    public boolean onLabelClick(List<als> list) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onLabelClick(list);
        }
        return false;
    }

    public boolean onNonFeatureClick() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onNonFeatureClick();
        }
        return false;
    }

    public boolean onHorizontalMove(float f) {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onHorizontalMove(f);
        }
        return false;
    }

    public boolean onHorizontalMoveEnd() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onHorizontalMoveEnd();
        }
        return false;
    }

    public boolean onGpsBtnClick() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onGpsBtnClick();
        }
        return false;
    }

    public boolean onBlankClick() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onBlankClick();
        }
        return false;
    }

    public boolean onNoBlankClick() {
        if (this.mRealOverlayManager != null) {
            return this.mRealOverlayManager.onNoBlankClick();
        }
        return false;
    }

    public void onMapAnimationFinished(int i) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.onMapAnimationFinished(i);
        }
    }

    public void onMapAnimationFinished(aln aln) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.onMapAnimationFinished(aln);
        }
    }

    public void onMapTouch() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.onMapTouch();
        }
    }

    public void onSelectSubWayActive(List<Long> list) {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.onSelectSubWayActive(list);
        }
    }

    public void onMapRenderCompleted() {
        if (this.mRealOverlayManager != null) {
            this.mRealOverlayManager.onMapRenderCompleted();
        }
    }
}
