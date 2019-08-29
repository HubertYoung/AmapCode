package com.autonavi.miniapp.plugin.map;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.autonavi.jni.ae.gmap.GLMapState;

public class SimpleMapListener implements amk {
    public void afterDrawFrame(int i, GLMapState gLMapState) {
    }

    public void beforeDrawFrame(int i, GLMapState gLMapState) {
    }

    public void onDoubleTap(int i, MotionEvent motionEvent) {
    }

    public void onEngineActionGesture(int i, alg alg) {
    }

    public void onEngineVisible(int i, boolean z) {
    }

    public boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onHorizontalMove(int i, float f) {
    }

    public void onHorizontalMoveEnd(int i) {
    }

    public void onHoveBegin(int i, MotionEvent motionEvent) {
    }

    public void onLongPress(int i, MotionEvent motionEvent) {
    }

    public void onMapAnimationFinished(int i, int i2) {
    }

    public void onMapAnimationFinished(int i, aln aln) {
    }

    public void onMapLevelChange(int i, boolean z) {
    }

    public void onMapRenderCompleted(int i) {
    }

    public void onMapSizeChange(int i) {
    }

    public void onMapTipClear(int i) {
    }

    public void onMapTipInfo(int i, String str) {
    }

    public void onMotionFinished(int i, int i2) {
    }

    public void onMoveBegin(int i, MotionEvent motionEvent) {
    }

    public void onOfflineMap(int i, String str, int i2) {
    }

    public void onRealCityAnimateFinish(int i) {
    }

    public void onScaleRotateBegin(int i, MotionEvent motionEvent) {
    }

    public void onScreenShotFinished(int i, long j) {
    }

    public void onScreenShotFinished(int i, Bitmap bitmap) {
    }

    public void onScreenShotFinished(int i, String str) {
    }

    public void onSelectSubWayActive(int i, byte[] bArr) {
    }

    public void onShowPress(int i, MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(int i, MotionEvent motionEvent) {
        return false;
    }

    public void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
    }

    public void onZoomOutTap(int i, MotionEvent motionEvent) {
    }
}
