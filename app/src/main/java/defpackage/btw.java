package defpackage;

import android.view.MotionEvent;
import com.autonavi.common.model.GeoPoint;
import java.util.List;

/* renamed from: btw reason: default package */
/* compiled from: IMapEventReceiver */
public interface btw {
    boolean onBlankClick();

    boolean onEngineActionGesture(alg alg);

    boolean onGpsBtnClick();

    boolean onHorizontalMove(float f);

    boolean onHorizontalMoveEnd();

    boolean onLabelClick(List<als> list);

    boolean onLineOverlayClick(long j);

    void onMapAnimationFinished(int i);

    void onMapAnimationFinished(aln aln);

    boolean onMapCompassClick();

    boolean onMapDoubleClick(MotionEvent motionEvent, GeoPoint geoPoint);

    boolean onMapLevelChange(boolean z);

    boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint);

    boolean onMapMotionFinish();

    boolean onMapMotionStop();

    void onMapRenderCompleted();

    boolean onMapShowPress(MotionEvent motionEvent, GeoPoint geoPoint);

    boolean onMapSingleClick(int i, MotionEvent motionEvent, GeoPoint geoPoint);

    boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint);

    boolean onMapSizeChange();

    void onMapTouch();

    boolean onMapTouchEvent(MotionEvent motionEvent);

    boolean onNoBlankClick();

    boolean onNonFeatureClick();

    boolean onPointOverlayClick(long j, int i);

    void onSelectSubWayActive(List<Long> list);
}
