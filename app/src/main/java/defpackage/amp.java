package defpackage;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import com.autonavi.jni.ae.gmap.GLMapState;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: amp reason: default package */
/* compiled from: MapListenerAdapter */
public class amp implements amk {
    private String TAG = "MapListenerAdapter";
    private ReentrantLock mLock = new ReentrantLock();
    private ArrayList<amk> mMapListeners = new ArrayList<>();

    public void onHorizontalMove(int i, float f) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onHorizontalMove(i, f);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMapTipInfo(int i, String str) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onMapTipInfo(i, str);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMapTipClear(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onMapTipClear(i);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onUserMapTouchEvent(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onShowPress(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onShowPress(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onLongPress(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onLongPress(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onDoubleTap(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onDoubleTap(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMoveBegin(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onMoveBegin(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onZoomOutTap(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onZoomOutTap(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onScaleRotateBegin(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onScaleRotateBegin(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onHoveBegin(int i, MotionEvent motionEvent) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onHoveBegin(i, motionEvent);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public boolean onSingleTapUp(int i, MotionEvent motionEvent) {
        boolean z;
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            int i2 = 0;
            z = false;
            while (i2 < size) {
                try {
                    amk amk = this.mMapListeners.get(i2);
                    if (amk != null) {
                        z = z || amk.onSingleTapUp(i, motionEvent);
                    }
                    i2++;
                } catch (Exception e) {
                    e = e;
                    try {
                        Log.getStackTraceString(e);
                        this.mLock.unlock();
                        return z;
                    } catch (Throwable th) {
                        this.mLock.unlock();
                        throw th;
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
            z = false;
            Log.getStackTraceString(e);
            this.mLock.unlock();
            return z;
        }
        this.mLock.unlock();
        return z;
    }

    public void onEngineActionGesture(int i, alg alg) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onEngineActionGesture(i, alg);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public boolean onFling(int i, MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        boolean z;
        Throwable th;
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            int i2 = 0;
            z = false;
            while (i2 < size) {
                try {
                    amk amk = this.mMapListeners.get(i2);
                    if (amk != null) {
                        z = z || amk.onFling(i, motionEvent, motionEvent2, f, f2);
                    }
                    i2++;
                } catch (Exception e) {
                    th = e;
                    try {
                        Log.getStackTraceString(th);
                        this.mLock.unlock();
                        return z;
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        this.mLock.unlock();
                        throw th3;
                    }
                }
            }
        } catch (Exception e2) {
            th = e2;
            z = false;
            Log.getStackTraceString(th);
            this.mLock.unlock();
            return z;
        }
        this.mLock.unlock();
        return z;
    }

    public void onMapSizeChange(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onMapSizeChange(i);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMapLevelChange(int i, boolean z) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onMapLevelChange(i, z);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onEngineVisible(int i, boolean z) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onEngineVisible(i, z);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMotionFinished(int i, int i2) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                amk amk = this.mMapListeners.get(i3);
                if (amk != null) {
                    amk.onMotionFinished(i, i2);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onHorizontalMoveEnd(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onHorizontalMoveEnd(i);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onOfflineMap(int i, String str, int i2) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                amk amk = this.mMapListeners.get(i3);
                if (amk != null) {
                    amk.onOfflineMap(i, str, i2);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onRealCityAnimateFinish(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onRealCityAnimateFinish(i);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void beforeDrawFrame(int i, GLMapState gLMapState) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.beforeDrawFrame(i, gLMapState);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void afterDrawFrame(int i, GLMapState gLMapState) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.afterDrawFrame(i, gLMapState);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMapAnimationFinished(int i, int i2) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                amk amk = this.mMapListeners.get(i3);
                if (amk != null) {
                    amk.onMapAnimationFinished(i, i2);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMapAnimationFinished(int i, aln aln) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onMapAnimationFinished(i, aln);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onSelectSubWayActive(int i, byte[] bArr) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onSelectSubWayActive(i, bArr);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onMapRenderCompleted(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onMapRenderCompleted(i);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onScreenShotFinished(int i, long j) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onScreenShotFinished(i, j);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onScreenShotFinished(int i, Bitmap bitmap) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onScreenShotFinished(i, bitmap);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void onScreenShotFinished(int i, String str) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amk amk = this.mMapListeners.get(i2);
                if (amk != null) {
                    amk.onScreenShotFinished(i, str);
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void addMapListener(amk amk) {
        this.mLock.lock();
        try {
            this.mMapListeners.add(amk);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void removeMapListener(amk amk) {
        this.mLock.lock();
        try {
            this.mMapListeners.remove(amk);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void clearListeners() {
        this.mLock.lock();
        try {
            this.mMapListeners.clear();
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }
}
