package defpackage;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.autonavi.jni.ae.gmap.GLMapState;

/* renamed from: amk reason: default package */
/* compiled from: MapListener */
public interface amk {
    void afterDrawFrame(int i, GLMapState gLMapState);

    void beforeDrawFrame(int i, GLMapState gLMapState);

    void onDoubleTap(int i, MotionEvent motionEvent);

    void onEngineActionGesture(int i, alg alg);

    void onEngineVisible(int i, boolean z);

    boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

    void onHorizontalMove(int i, float f);

    void onHorizontalMoveEnd(int i);

    void onHoveBegin(int i, MotionEvent motionEvent);

    void onLongPress(int i, MotionEvent motionEvent);

    void onMapAnimationFinished(int i, int i2);

    void onMapAnimationFinished(int i, aln aln);

    void onMapLevelChange(int i, boolean z);

    void onMapRenderCompleted(int i);

    void onMapSizeChange(int i);

    void onMapTipClear(int i);

    void onMapTipInfo(int i, String str);

    void onMotionFinished(int i, int i2);

    void onMoveBegin(int i, MotionEvent motionEvent);

    void onOfflineMap(int i, String str, int i2);

    void onRealCityAnimateFinish(int i);

    void onScaleRotateBegin(int i, MotionEvent motionEvent);

    void onScreenShotFinished(int i, long j);

    void onScreenShotFinished(int i, Bitmap bitmap);

    void onScreenShotFinished(int i, String str);

    void onSelectSubWayActive(int i, byte[] bArr);

    void onShowPress(int i, MotionEvent motionEvent);

    boolean onSingleTapUp(int i, MotionEvent motionEvent);

    void onUserMapTouchEvent(int i, MotionEvent motionEvent);

    void onZoomOutTap(int i, MotionEvent motionEvent);
}
