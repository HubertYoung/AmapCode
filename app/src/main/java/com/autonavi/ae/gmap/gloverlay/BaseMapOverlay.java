package com.autonavi.ae.gmap.gloverlay;

import android.content.Context;

import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import defpackage.akq;

public abstract class BaseMapOverlay<T extends GLOverlay, E> implements Serializable {
    private static final long serialVersionUID = 1;
    public Context mContext;
    protected int mEngineID = -1;
    public T mGLOverlay;
    protected Vector<E> mItemList = null;
    protected int mLastFocusedIndex;
    protected akq mMapView;

    public abstract void addItem(E e);

    /* access modifiers changed from: protected */
    public abstract void iniGLOverlay();

    public abstract void resumeMarker();

    public BaseMapOverlay(int i, Context context, akq akq) {
        this.mEngineID = i;
        this.mContext = context;
        this.mMapView = akq;
        this.mItemList = new Vector<>();
        iniGLOverlay();
    }

    public T getGLOverlay() {
        return this.mGLOverlay;
    }

    public void setVisible(boolean z) {
        if (this.mGLOverlay != null) {
            this.mGLOverlay.setVisible(z);
        }
    }

    public boolean isVisible() {
        if (this.mGLOverlay != null) {
            return this.mGLOverlay.isVisible();
        }
        return false;
    }

    public void setClickable(boolean z) {
        if (this.mGLOverlay != null) {
            this.mGLOverlay.setClickable(z);
        }
    }

    public boolean isClickable() {
        if (this.mGLOverlay != null) {
            return this.mGLOverlay.isClickable();
        }
        return false;
    }

    public int getSize() {
        return this.mItemList.size();
    }

    public boolean clear() {
        try {
            this.mItemList.clear();
            clearFocus();
            if (this.mGLOverlay != null) {
                this.mGLOverlay.removeAll();
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean removeAll() {
        return clear();
    }

    public void clearFocus() {
        this.mLastFocusedIndex = -1;
        this.mGLOverlay.clearFocus();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001c, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final E getItem(int r4) {
        /*
            r3 = this;
            r0 = 0
            java.util.Vector<E> r1 = r3.mItemList     // Catch:{ IndexOutOfBoundsException -> 0x001f }
            monitor-enter(r1)     // Catch:{ IndexOutOfBoundsException -> 0x001f }
            if (r4 < 0) goto L_0x001b
            java.util.Vector<E> r2 = r3.mItemList     // Catch:{ all -> 0x0019 }
            int r2 = r2.size()     // Catch:{ all -> 0x0019 }
            int r2 = r2 + -1
            if (r4 <= r2) goto L_0x0011
            goto L_0x001b
        L_0x0011:
            java.util.Vector<E> r2 = r3.mItemList     // Catch:{ all -> 0x0019 }
            java.lang.Object r4 = r2.get(r4)     // Catch:{ all -> 0x0019 }
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            return r4
        L_0x0019:
            r4 = move-exception
            goto L_0x001d
        L_0x001b:
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            return r0
        L_0x001d:
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            throw r4     // Catch:{ IndexOutOfBoundsException -> 0x001f }
        L_0x001f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.ae.gmap.gloverlay.BaseMapOverlay.getItem(int):java.lang.Object");
    }

    public void removeItem(int i) {
        if (i >= 0) {
            try {
                if (i <= this.mItemList.size() - 1) {
                    if (i == this.mLastFocusedIndex) {
                        this.mLastFocusedIndex = -1;
                        clearFocus();
                    }
                    this.mItemList.remove(i);
                    if (this.mGLOverlay != null) {
                        this.mGLOverlay.removeItem(i);
                    }
                }
            } catch (IndexOutOfBoundsException unused) {
            }
        }
    }

    public void removeItem(E e) {
        if (e != null) {
            try {
                synchronized (this.mItemList) {
                    removeItem(this.mItemList.indexOf(e));
                }
            } catch (IndexOutOfBoundsException unused) {
            }
        }
    }

    public List<E> getItems() {
        return this.mItemList;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        releaseInstance();
        super.finalize();
    }

    private void releaseInstance() {
        if (getGLOverlay() != null) {
            getGLOverlay().releaseInstance();
            this.mGLOverlay = null;
        }
    }
}
