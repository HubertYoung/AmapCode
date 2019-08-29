package defpackage;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.MotionEvent;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import java.util.Set;

/* renamed from: xj reason: default package */
/* compiled from: MapEventDispatcher */
final class xj implements amk, aml {
    SparseArray<Set<MainMapEventListener>> a = new SparseArray<>();
    private int b;

    public final void onLineOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
    }

    public final void onPointOverlayClick(int i, GLAmapFocusHits gLAmapFocusHits) {
    }

    xj(int i) {
        this.b = i;
    }

    public final void onMapTipInfo(int i, String str) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onMapTipInfo : set) {
                        onMapTipInfo.onMapTipInfo(str);
                    }
                }
            }
        }
    }

    public final void onMapTipClear(int i) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onMapTipClear : set) {
                        onMapTipClear.onMapTipClear();
                    }
                }
            }
        }
    }

    public final void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onUserMapTouchEvent : set) {
                        onUserMapTouchEvent.onUserMapTouchEvent(motionEvent);
                    }
                }
            }
        }
    }

    public final void onShowPress(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onShowPress : set) {
                        onShowPress.onShowPress(motionEvent);
                    }
                }
            }
        }
    }

    public final void onLongPress(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onLongPress : set) {
                        onLongPress.onLongPress(motionEvent);
                    }
                }
            }
        }
    }

    public final void onDoubleTap(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onDoubleTap : set) {
                        onDoubleTap.onDoubleTap(motionEvent);
                    }
                }
            }
        }
    }

    public final void onMoveBegin(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onMoveBegin : set) {
                        onMoveBegin.onMoveBegin(motionEvent);
                    }
                }
            }
        }
    }

    public final void onZoomOutTap(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onZoomOutTap : set) {
                        onZoomOutTap.onZoomOutTap(motionEvent);
                    }
                }
            }
        }
    }

    public final void onScaleRotateBegin(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onScaleRotateBegin : set) {
                        onScaleRotateBegin.onScaleRotateBegin(motionEvent);
                    }
                }
            }
        }
    }

    public final void onHoveBegin(int i, MotionEvent motionEvent) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onHoveBegin : set) {
                        onHoveBegin.onHoveBegin(motionEvent);
                    }
                }
            }
        }
    }

    public final boolean onSingleTapUp(int i, MotionEvent motionEvent) {
        if (i != this.b) {
            return false;
        }
        boolean z = false;
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onSingleTapUp : set) {
                    z = z || onSingleTapUp.onSingleTapUp(motionEvent);
                }
            }
        }
        return z;
    }

    public final void onEngineActionGesture(int i, alg alg) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onEngineActionGesture : set) {
                        onEngineActionGesture.onEngineActionGesture(alg);
                    }
                }
            }
        }
    }

    public final boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (i != this.b) {
            return false;
        }
        boolean z = false;
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onFling : set) {
                    z = z || onFling.onFling(motionEvent, motionEvent2, f, f2);
                }
            }
        }
        return z;
    }

    public final void onMapSizeChange(int i) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onMapSizeChange : set) {
                        onMapSizeChange.onMapSizeChange();
                    }
                }
            }
        }
    }

    public final void onMapLevelChange(int i, boolean z) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onMapLevelChange : set) {
                        onMapLevelChange.onMapLevelChange(z);
                    }
                }
            }
        }
    }

    public final void onEngineVisible(int i, boolean z) {
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onEngineVisible : set) {
                    onEngineVisible.onEngineVisible(i, z);
                }
            }
        }
    }

    public final void onMotionFinished(int i, int i2) {
        if (i == this.b) {
            for (int i3 = 2; i3 >= 0; i3--) {
                Set<MainMapEventListener> set = this.a.get(i3);
                if (set != null) {
                    for (MainMapEventListener onMotionFinished : set) {
                        onMotionFinished.onMotionFinished(i2);
                    }
                }
            }
        }
    }

    public final void onHorizontalMove(int i, float f) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onHorizontalMove : set) {
                        onHorizontalMove.onHorizontalMove(f);
                    }
                }
            }
        }
    }

    public final void onHorizontalMoveEnd(int i) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onHorizontalMoveEnd : set) {
                        onHorizontalMoveEnd.onHorizontalMoveEnd();
                    }
                }
            }
        }
    }

    public final void onOfflineMap(int i, String str, int i2) {
        if (i == this.b) {
            for (int i3 = 2; i3 >= 0; i3--) {
                Set<MainMapEventListener> set = this.a.get(i3);
                if (set != null) {
                    for (MainMapEventListener onOfflineMap : set) {
                        onOfflineMap.onOfflineMap(str, i2);
                    }
                }
            }
        }
    }

    public final void onRealCityAnimateFinish(int i) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onRealCityAnimateFinish : set) {
                        onRealCityAnimateFinish.onRealCityAnimateFinish();
                    }
                }
            }
        }
    }

    public final void beforeDrawFrame(int i, GLMapState gLMapState) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener beforeDrawFrame : set) {
                        beforeDrawFrame.beforeDrawFrame(gLMapState);
                    }
                }
            }
        }
    }

    public final void afterDrawFrame(int i, GLMapState gLMapState) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener afterDrawFrame : set) {
                        afterDrawFrame.afterDrawFrame(gLMapState);
                    }
                }
            }
        }
    }

    public final void onMapAnimationFinished(int i, int i2) {
        if (i == this.b) {
            for (int i3 = 2; i3 >= 0; i3--) {
                Set<MainMapEventListener> set = this.a.get(i3);
                if (set != null) {
                    for (MainMapEventListener onMapAnimationFinished : set) {
                        onMapAnimationFinished.onMapAnimationFinished(i2);
                    }
                }
            }
        }
    }

    public final void onMapAnimationFinished(int i, aln aln) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onMapAnimationFinished : set) {
                        onMapAnimationFinished.onMapAnimationFinished(aln);
                    }
                }
            }
        }
    }

    public final void onSelectSubWayActive(int i, byte[] bArr) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onSelectSubWayActive : set) {
                        onSelectSubWayActive.onSelectSubWayActive(bArr);
                    }
                }
            }
        }
    }

    public final void onMapRenderCompleted(int i) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onMapRenderCompleted : set) {
                        onMapRenderCompleted.onMapRenderCompleted();
                    }
                }
            }
        }
    }

    public final void onScreenShotFinished(int i, long j) {
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onScreenShotFinished : set) {
                    onScreenShotFinished.onScreenShotFinished(i, j);
                }
            }
        }
    }

    public final void onScreenShotFinished(int i, Bitmap bitmap) {
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onScreenShotFinished : set) {
                    onScreenShotFinished.onScreenShotFinished(i, bitmap);
                }
            }
        }
    }

    public final void onScreenShotFinished(int i, String str) {
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onScreenShotFinished : set) {
                    onScreenShotFinished.onScreenShotFinished(i, str);
                }
            }
        }
    }

    public final void onNoFeatureClick(int i) {
        if (i == this.b) {
            for (int i2 = 2; i2 >= 0; i2--) {
                Set<MainMapEventListener> set = this.a.get(i2);
                if (set != null) {
                    for (MainMapEventListener onNoFeatureClick : set) {
                        onNoFeatureClick.onNoFeatureClick();
                    }
                }
            }
        }
    }

    public final boolean onBlankClick(int i) {
        if (i != this.b) {
            return false;
        }
        boolean z = false;
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onBlankClick : set) {
                    z = z || onBlankClick.onBlankClick();
                }
            }
        }
        return z;
    }

    public final boolean onNoBlankClick(int i) {
        if (i != this.b) {
            return false;
        }
        boolean z = false;
        for (int i2 = 2; i2 >= 0; i2--) {
            Set<MainMapEventListener> set = this.a.get(i2);
            if (set != null) {
                for (MainMapEventListener onNoBlankClick : set) {
                    z = z || onNoBlankClick.onNoBlankClick();
                }
            }
        }
        return z;
    }
}
