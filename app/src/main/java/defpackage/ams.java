package defpackage;

import android.util.Log;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: ams reason: default package */
/* compiled from: MapWidgetListenerAdapter */
public class ams implements amn {
    private String TAG = "MapWidgetListenerAdapter";
    private ReentrantLock mLock = new ReentrantLock();
    private ArrayList<amn> mMapListeners = new ArrayList<>();

    public void refreshScaleLineView(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amn amn = this.mMapListeners.get(i2);
                if (amn != null) {
                    amn.refreshScaleLineView(i);
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

    public void setScaleColor(int i, int i2, int i3) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i4 = 0; i4 < size; i4++) {
                amn amn = this.mMapListeners.get(i4);
                if (amn != null) {
                    amn.setScaleColor(i, i2, i3);
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

    public void paintCompass(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amn amn = this.mMapListeners.get(i2);
                if (amn != null) {
                    amn.paintCompass(i);
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

    public void fadeCompassWidget(int i) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amn amn = this.mMapListeners.get(i2);
                if (amn != null) {
                    amn.fadeCompassWidget(i);
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

    public void setFrontViewVisibility(int i, boolean z) {
        this.mLock.lock();
        try {
            int size = this.mMapListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                amn amn = this.mMapListeners.get(i2);
                if (amn != null) {
                    amn.setFrontViewVisibility(i, z);
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

    public void addMapWidgetListener(amn amn) {
        this.mLock.lock();
        try {
            this.mMapListeners.add(amn);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        } catch (Throwable th) {
            this.mLock.unlock();
            throw th;
        }
        this.mLock.unlock();
    }

    public void removeMapWidgetListener(amn amn) {
        this.mLock.lock();
        try {
            this.mMapListeners.remove(amn);
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
