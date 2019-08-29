package defpackage;

import android.util.Log;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: amr reason: default package */
/* compiled from: MapSurfaceListenerAdapter */
public class amr implements amm {
    private String TAG = "MapSurfaceListenerAdapter";
    private ReentrantLock mLock = new ReentrantLock();
    private ArrayList<amm> mMapListeners = new ArrayList<>();

    public void onSurfaceCreated(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amm amm = this.mMapListeners.get(i2);
                if (amm != null) {
                    amm.onSurfaceCreated(i);
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

    public void onSurfaceChanged(int i, int i2, int i3, int i4) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i5 = 0; i5 < size; i5++) {
                amm amm = this.mMapListeners.get(i5);
                if (amm != null) {
                    amm.onSurfaceChanged(i, i2, i3, i4);
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

    public void onSurfaceDestroy(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amm amm = this.mMapListeners.get(i2);
                if (amm != null) {
                    amm.onSurfaceDestroy(i);
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

    public void onRenderDeviceCreated(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amm amm = this.mMapListeners.get(i2);
                if (amm != null) {
                    amm.onRenderDeviceCreated(i);
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

    public void onRenderDeviceDestroyed(int i) {
        this.mLock.lock();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mMapListeners);
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                amm amm = (amm) arrayList.get(i2);
                if (amm != null) {
                    amm.onRenderDeviceDestroyed(i);
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

    public void onDrawFrameFirst(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amm amm = this.mMapListeners.get(i2);
                if (amm != null) {
                    amm.onDrawFrameFirst(i);
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

    public void onDrawFrameFirstOnGLThread(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amm amm = this.mMapListeners.get(i2);
                if (amm != null) {
                    amm.onDrawFrameFirstOnGLThread(i);
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

    public void onSurfaceRenderFrame(int i, int i2) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i3 = 0; i3 < size; i3++) {
                amm amm = this.mMapListeners.get(i3);
                if (amm != null) {
                    amm.onSurfaceRenderFrame(i, i2);
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

    public void addMapSurfaceListener(amm amm) {
        this.mLock.lock();
        try {
            this.mMapListeners.add(amm);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void removeMapSurfaceListener(amm amm) {
        this.mLock.lock();
        try {
            this.mMapListeners.remove(amm);
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
