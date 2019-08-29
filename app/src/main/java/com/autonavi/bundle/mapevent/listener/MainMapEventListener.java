package com.autonavi.bundle.mapevent.listener;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;

public interface MainMapEventListener {
    void afterDrawFrame(GLMapState gLMapState);

    void beforeDrawFrame(GLMapState gLMapState);

    boolean onBlankClick();

    void onDoubleTap(MotionEvent motionEvent);

    void onEngineActionGesture(alg alg);

    void onEngineVisible(int i, boolean z);

    boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

    void onHorizontalMove(float f);

    void onHorizontalMoveEnd();

    void onHoveBegin(MotionEvent motionEvent);

    void onLineOverlayClick(GLAmapFocusHits gLAmapFocusHits);

    void onLongPress(MotionEvent motionEvent);

    void onMapAnimationFinished(int i);

    void onMapAnimationFinished(aln aln);

    void onMapLevelChange(boolean z);

    void onMapRenderCompleted();

    void onMapSizeChange();

    void onMapTipClear();

    void onMapTipInfo(String str);

    void onMotionFinished(int i);

    void onMoveBegin(MotionEvent motionEvent);

    boolean onNoBlankClick();

    void onNoFeatureClick();

    void onOfflineMap(String str, int i);

    void onPointOverlayClick(GLAmapFocusHits gLAmapFocusHits);

    void onRealCityAnimateFinish();

    void onScaleRotateBegin(MotionEvent motionEvent);

    void onScreenShotFinished(int i, long j);

    void onScreenShotFinished(int i, Bitmap bitmap);

    void onScreenShotFinished(int i, String str);

    void onSelectSubWayActive(byte[] bArr);

    void onShowPress(MotionEvent motionEvent);

    boolean onSingleTapUp(MotionEvent motionEvent);

    void onUserMapTouchEvent(MotionEvent motionEvent);

    void onZoomOutTap(MotionEvent motionEvent);
}
