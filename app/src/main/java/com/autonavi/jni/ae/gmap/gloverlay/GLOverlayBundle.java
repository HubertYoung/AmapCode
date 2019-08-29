package com.autonavi.jni.ae.gmap.gloverlay;

import android.util.SparseArray;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import java.util.ArrayList;
import java.util.List;

public class GLOverlayBundle<E extends BaseMapOverlay<?, ?>> {
    private int mEngineID;
    akq mGLMapView = null;
    private long mNativeInstance = 0;
    private final List<E> mOverlayList = new ArrayList();
    private SparseArray<amc> mTextureCaches = new SparseArray<>();

    public static class GLAmapFocusHits {
        public long mHitedIndex = 0;
        public long mHitedTimes = 1000;
        public long mOverlayHashCode = 0;
        public long mOverlaySubType = -1;
        public int markerIndex = -1;
        public int x = 0;
        public int y = 0;
    }

    private static native void nativeAddGLOverlay(long j, long j2, long j3);

    private static native void nativeAddGLOverlayEx(long j, long j2, long j3, int i);

    private static native void nativeClearAllGLOverlay(long j, boolean z);

    private static native boolean nativeOnSingleTapLineOverlay(long j, int i, int i2, long[] jArr);

    private static native boolean nativeOnSingleTapPointOverlay(long j, int i, int i2, long[] jArr);

    private static native void nativeRemoveGLOverlay(long j, long j2);

    private static native void nativeRemoveGLOverlayEx(long j, long j2, int i);

    private static native void nativeSortAllGLOverlay(long j);

    public static void IntClr2PVRClr(float[] fArr, int i) {
        fArr[2] = ((float) (i & 255)) / 255.0f;
        fArr[1] = ((float) ((i >> 8) & 255)) / 255.0f;
        fArr[0] = ((float) ((i >> 16) & 255)) / 255.0f;
        fArr[3] = ((float) ((i >> 24) & 255)) / 255.0f;
    }

    public GLOverlayBundle(int i, akq akq) {
        this.mEngineID = i;
        this.mGLMapView = akq;
        this.mNativeInstance = akq.d.getGlOverlayMgrPtr(this.mEngineID);
    }

    public int getOverlayCount() {
        int size;
        synchronized (this.mOverlayList) {
            try {
                size = this.mOverlayList.size();
            }
        }
        return size;
    }

    public boolean cotainsOverlay(E e) {
        boolean contains;
        if (e == null) {
            return false;
        }
        synchronized (this.mOverlayList) {
            try {
                contains = this.mOverlayList.contains(e);
            }
        }
        return contains;
    }

    public E getOverlay(int i) {
        synchronized (this.mOverlayList) {
            if (i >= 0) {
                try {
                    if (i <= this.mOverlayList.size() - 1) {
                        E e = (BaseMapOverlay) this.mOverlayList.get(i);
                        return e;
                    }
                } finally {
                }
            }
            return null;
        }
    }

    private int getOverlyExType(E e) {
        if (e.getGLOverlay() instanceof GLNaviOverlay) {
            return 1;
        }
        return e.getGLOverlay() instanceof GLRctRouteOverlay ? 2 : 0;
    }

    public void addOverlay(E e) {
        if (e != null) {
            if ((e.getGLOverlay() instanceof GLNaviOverlay) || (e.getGLOverlay() instanceof GLRctRouteOverlay)) {
                nativeAddGLOverlayEx(this.mNativeInstance, e.getGLOverlay().getNativeInstatnce(), (long) e.getGLOverlay().getCode(), getOverlyExType(e));
            } else {
                nativeAddGLOverlay(this.mNativeInstance, e.getGLOverlay().getNativeInstatnce(), (long) e.getGLOverlay().getCode());
            }
            e.getGLOverlay().mIsInBundle = true;
            synchronized (this.mOverlayList) {
                this.mOverlayList.add(e);
            }
        }
    }

    public void sortOverlay() {
        nativeSortAllGLOverlay(this.mNativeInstance);
    }

    public void removeOverlay(E e) {
        if (e != null) {
            if ((e.getGLOverlay() instanceof GLNaviOverlay) || (e.getGLOverlay() instanceof GLRctRouteOverlay)) {
                nativeRemoveGLOverlayEx(this.mNativeInstance, e.getGLOverlay().getNativeInstatnce(), getOverlyExType(e));
            } else {
                nativeRemoveGLOverlay(this.mNativeInstance, e.getGLOverlay().getNativeInstatnce());
            }
            e.getGLOverlay().mIsInBundle = false;
            synchronized (this.mOverlayList) {
                this.mOverlayList.remove(e);
            }
        }
    }

    public void removeAll(boolean z) {
        nativeClearAllGLOverlay(this.mNativeInstance, z);
        synchronized (this.mOverlayList) {
            for (int i = 0; i < this.mOverlayList.size(); i++) {
                BaseMapOverlay baseMapOverlay = (BaseMapOverlay) this.mOverlayList.get(i);
                if (baseMapOverlay != null) {
                    baseMapOverlay.getGLOverlay().mIsInBundle = false;
                }
            }
            this.mOverlayList.clear();
        }
    }

    public void clearFocus() {
        if (this.mOverlayList != null) {
            synchronized (this.mOverlayList) {
                for (int i = 0; i < this.mOverlayList.size(); i++) {
                    BaseMapOverlay baseMapOverlay = (BaseMapOverlay) this.mOverlayList.get(i);
                    if (baseMapOverlay != null) {
                        baseMapOverlay.clearFocus();
                    }
                }
            }
        }
    }

    public boolean onSingleTap(int i, int i2, int i3, int i4) {
        boolean onSingleTapPoints = (i4 & 1) == 1 ? onSingleTapPoints(i, i2, i3) : false;
        if (onSingleTapPoints) {
            return true;
        }
        if ((i4 & 2) == 2) {
            onSingleTapPoints = onSingleTapLines(i, i2, i3);
        }
        return onSingleTapPoints;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0049, code lost:
        r0 = new long[5];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        if (nativeOnSingleTapPointOverlay(r6.mNativeInstance, r8, r9, r0) == false) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0055, code lost:
        r1 = new com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits();
        r1.mOverlayHashCode = r0[0];
        r1.mHitedIndex = r0[1];
        r1.mHitedTimes = r0[2];
        r1.mOverlaySubType = r0[3];
        r1.markerIndex = (int) r0[4];
        r1.x = r8;
        r1.y = r9;
        r8 = r6.mGLMapView;
        r9 = new java.lang.StringBuilder("focusPointOverlayItems: ");
        r9.append(r7);
        r9.append(", ");
        r9.append(r1);
        defpackage.akq.b(r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0093, code lost:
        if (r8.r == null) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0095, code lost:
        r8.u.post(new defpackage.akq.AnonymousClass4(r7, r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009f, code lost:
        r7 = r8.d.getBelongToRenderDeviceId(r7);
        defpackage.akq.b((java.lang.String) "resetRenderTimeInTouch: ");
        r8.e(r7, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ae, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00af, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onSingleTapPoints(int r7, int r8, int r9) {
        /*
            r6 = this;
            java.util.List<E> r0 = r6.mOverlayList
            monitor-enter(r0)
            java.util.List<E> r1 = r6.mOverlayList     // Catch:{ all -> 0x00b0 }
            int r1 = r1.size()     // Catch:{ all -> 0x00b0 }
            r2 = 1
            int r1 = r1 - r2
        L_0x000b:
            if (r1 < 0) goto L_0x0048
            java.util.List<E> r3 = r6.mOverlayList     // Catch:{ all -> 0x00b0 }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ all -> 0x00b0 }
            com.autonavi.ae.gmap.gloverlay.BaseMapOverlay r3 = (com.autonavi.ae.gmap.gloverlay.BaseMapOverlay) r3     // Catch:{ all -> 0x00b0 }
            com.autonavi.jni.ae.gmap.gloverlay.GLOverlay r4 = r3.getGLOverlay()     // Catch:{ all -> 0x00b0 }
            boolean r4 = r4 instanceof com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay     // Catch:{ all -> 0x00b0 }
            if (r4 == 0) goto L_0x0045
            boolean r4 = r3.isVisible()     // Catch:{ all -> 0x00b0 }
            if (r4 == 0) goto L_0x0045
            boolean r3 = r3.isClickable()     // Catch:{ all -> 0x00b0 }
            if (r3 == 0) goto L_0x0045
            java.util.List<E> r3 = r6.mOverlayList     // Catch:{ all -> 0x00b0 }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ all -> 0x00b0 }
            com.autonavi.ae.gmap.gloverlay.BaseMapOverlay r3 = (com.autonavi.ae.gmap.gloverlay.BaseMapOverlay) r3     // Catch:{ all -> 0x00b0 }
            com.autonavi.jni.ae.gmap.gloverlay.GLOverlay r3 = r3.getGLOverlay()     // Catch:{ all -> 0x00b0 }
            com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay r3 = (com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay) r3     // Catch:{ all -> 0x00b0 }
            boolean r4 = r3.onSingleTap(r7, r8, r9)     // Catch:{ all -> 0x00b0 }
            if (r4 == 0) goto L_0x0045
            boolean r3 = r3.getOnlyRespToClickArea()     // Catch:{ all -> 0x00b0 }
            if (r3 == 0) goto L_0x0045
            monitor-exit(r0)     // Catch:{ all -> 0x00b0 }
            return r2
        L_0x0045:
            int r1 = r1 + -1
            goto L_0x000b
        L_0x0048:
            monitor-exit(r0)     // Catch:{ all -> 0x00b0 }
            r0 = 5
            long[] r0 = new long[r0]
            long r3 = r6.mNativeInstance
            boolean r1 = nativeOnSingleTapPointOverlay(r3, r8, r9, r0)
            r3 = 0
            if (r1 == 0) goto L_0x00af
            com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle$GLAmapFocusHits r1 = new com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle$GLAmapFocusHits
            r1.<init>()
            r3 = r0[r3]
            r1.mOverlayHashCode = r3
            r3 = r0[r2]
            r1.mHitedIndex = r3
            r3 = 2
            r4 = r0[r3]
            r1.mHitedTimes = r4
            r4 = 3
            r4 = r0[r4]
            r1.mOverlaySubType = r4
            r4 = 4
            r4 = r0[r4]
            int r0 = (int) r4
            r1.markerIndex = r0
            r1.x = r8
            r1.y = r9
            akq r8 = r6.mGLMapView
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r0 = "focusPointOverlayItems: "
            r9.<init>(r0)
            r9.append(r7)
            java.lang.String r0 = ", "
            r9.append(r0)
            r9.append(r1)
            java.lang.String r9 = r9.toString()
            defpackage.akq.b(r9)
            amq r9 = r8.r
            if (r9 == 0) goto L_0x009f
            android.os.Handler r9 = r8.u
            akq$4 r0 = new akq$4
            r0.<init>(r7, r1)
            r9.post(r0)
        L_0x009f:
            com.autonavi.jni.ae.gmap.GLMapEngine r9 = r8.d
            int r7 = r9.getBelongToRenderDeviceId(r7)
            java.lang.String r9 = "resetRenderTimeInTouch: "
            defpackage.akq.b(r9)
            r8.e(r7, r3)
            return r2
        L_0x00af:
            return r3
        L_0x00b0:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00b0 }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.onSingleTapPoints(int, int, int):boolean");
    }

    /* access modifiers changed from: 0000 */
    public boolean onSingleTapLines(int i, int i2, int i3) {
        long[] jArr = new long[4];
        if (!nativeOnSingleTapLineOverlay(this.mNativeInstance, i2, i3, jArr)) {
            return false;
        }
        GLAmapFocusHits gLAmapFocusHits = new GLAmapFocusHits();
        gLAmapFocusHits.mOverlayHashCode = jArr[0];
        gLAmapFocusHits.mHitedIndex = jArr[1];
        gLAmapFocusHits.mHitedTimes = jArr[2];
        gLAmapFocusHits.mOverlaySubType = jArr[3];
        gLAmapFocusHits.x = i2;
        gLAmapFocusHits.y = i3;
        akq akq = this.mGLMapView;
        StringBuilder sb = new StringBuilder("focusLineOverlayItems: ");
        sb.append(i);
        sb.append(", ");
        sb.append(gLAmapFocusHits);
        akq.b(sb.toString());
        if (akq.r != null) {
            akq.u.post(new Runnable(i, gLAmapFocusHits) {
                final /* synthetic */ int a;
                final /* synthetic */ GLAmapFocusHits b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final void run() {
                    akq.this.r.onLineOverlayClick(this.a, this.b);
                }
            });
        }
        return true;
    }

    public long checkSingleTapOnLine(int i, int i2) {
        long[] jArr = new long[4];
        if (nativeOnSingleTapLineOverlay(this.mNativeInstance, i, i2, jArr)) {
            return jArr[0];
        }
        return -1;
    }

    public long checkSingleTapOnPoint(int i, int i2) {
        long[] jArr = new long[5];
        if (nativeOnSingleTapPointOverlay(this.mNativeInstance, i, i2, jArr)) {
            return jArr[0];
        }
        return -1;
    }

    public amc getOverlayTextureItem(int i) {
        amc amc;
        synchronized (this.mTextureCaches) {
            try {
                amc = this.mTextureCaches.get(i);
            }
        }
        return amc;
    }

    public boolean addOverlayTextureItem(int i, int i2, int i3, int i4) {
        amc amc = new amc(i, i2, i3, i4);
        synchronized (this.mTextureCaches) {
            this.mTextureCaches.put(i, amc);
        }
        return true;
    }

    public boolean addOverlayTextureItem(int i, int i2, float f, float f2, int i3, int i4) {
        amc amc = new amc(i, i2, f, f2, i3, i4);
        synchronized (this.mTextureCaches) {
            this.mTextureCaches.put(i, amc);
        }
        return true;
    }

    public void clearOverlayTexture() {
        synchronized (this.mTextureCaches) {
            this.mTextureCaches.clear();
        }
    }

    public void reculateRouteBoard(akq akq) {
        synchronized (this.mOverlayList) {
            for (int size = this.mOverlayList.size() - 1; size >= 0; size--) {
                BaseMapOverlay baseMapOverlay = (BaseMapOverlay) this.mOverlayList.get(size);
                if (baseMapOverlay instanceof amg) {
                    ((amg) baseMapOverlay).reculateOverlay(akq);
                }
            }
        }
    }
}
