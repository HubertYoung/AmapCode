package defpackage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.minimap.bundle.evaluate.callback.EvaluateLifecycleCallbacks;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: cxh reason: default package */
/* compiled from: EvaluateMapListener */
public class cxh implements amk {
    private static final ExecutorService a = Executors.newSingleThreadExecutor();
    private static cxh b;

    public void afterDrawFrame(int i, GLMapState gLMapState) {
    }

    public void beforeDrawFrame(int i, GLMapState gLMapState) {
    }

    public void onDoubleTap(int i, MotionEvent motionEvent) {
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

    private cxh() {
    }

    public static amk a() {
        if (b == null) {
            synchronized (cxh.class) {
                try {
                    if (b == null) {
                        b = new cxh();
                    }
                }
            }
        }
        return b;
    }

    public void onEngineActionGesture(int i, final alg alg) {
        if (EvaluateLifecycleCallbacks.a()) {
            try {
                if (a != null) {
                    cxk.a();
                    final String c = cxk.c();
                    a.execute(new Runnable() {
                        public final void run() {
                            Handler handler = cxi.a().a;
                            if (handler != null && alg.a != 0) {
                                Bundle bundle = new Bundle();
                                Message obtainMessage = handler.obtainMessage();
                                obtainMessage.what = 768;
                                obtainMessage.arg1 = 769;
                                bundle.putString("_view_name", c);
                                bundle.putInt("_gesture_type", alg.a);
                                bundle.putInt("_gesture_sub_type", alg.d);
                                obtainMessage.setData(bundle);
                                handler.sendMessage(obtainMessage);
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
