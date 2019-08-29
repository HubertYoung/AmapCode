package defpackage;

import android.view.MotionEvent;
import com.autonavi.common.model.GeoPoint;
import java.util.List;

/* renamed from: btv reason: default package */
/* compiled from: IMapEventListener */
public interface btv {
    boolean onBlankClick();

    void onDoubleTap();

    boolean onEngineActionGesture(alg alg);

    void onEngineVisible(int i, boolean z);

    boolean onFocusClear();

    void onHoveBegin();

    boolean onLabelClick(List<als> list);

    boolean onLineOverlayClick(long j);

    void onMapAnimationFinished(int i);

    void onMapAnimationFinished(aln aln);

    boolean onMapLevelChange(boolean z);

    boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint);

    boolean onMapMotionStop();

    void onMapRenderCompleted();

    boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint);

    boolean onMapTouchEvent(MotionEvent motionEvent);

    void onMoveBegin();

    boolean onNoBlankClick();

    boolean onPointOverlayClick(long j, int i);

    void onScaleRotateBegin();

    void onSelectSubWayActive(List<Long> list);

    void onZoomOutTap();
}
