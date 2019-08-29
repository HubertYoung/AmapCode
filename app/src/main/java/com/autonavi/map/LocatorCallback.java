package com.autonavi.map;

import android.support.annotation.NonNull;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import java.util.ArrayList;

public class LocatorCallback implements Callback<Status> {
    public static final String LOG_TAG = "LocatorCallback";
    /* access modifiers changed from: private */
    @NonNull
    public final ceg mGpsController;
    private final bro mMapManager;
    @NonNull
    private final ArrayList<Runnable> mPendingActions = new ArrayList<>();
    private Runnable[] mTmpActions;

    public LocatorCallback(ceg ceg, bro bro) {
        this.mGpsController = (ceg) age.a(ceg, "gpsController is null?");
        this.mMapManager = bro;
    }

    public void callback(Status status) {
        if (status == Status.ON_LOCATION_OK) {
            bty bty = null;
            if (this.mMapManager != null) {
                bty = this.mMapManager.getMapView();
            }
            if (bty == null || !bty.k()) {
                synchronized (this) {
                    this.mPendingActions.clear();
                }
                this.mPendingActions.add(new Runnable() {
                    public final void run() {
                        LocatorCallback.this.mGpsController.f();
                    }
                });
                return;
            }
            synchronized (this) {
                this.mPendingActions.clear();
            }
            this.mGpsController.f();
            return;
        }
        this.mGpsController.e();
    }

    public void error(Throwable th, boolean z) {
        th.printStackTrace();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        if (r1 >= r0) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0038, code lost:
        defpackage.aho.a(r3.mTmpActions[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        r3.mTmpActions = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execPendingActions() {
        /*
            r3 = this;
            java.lang.String r0 = "LocatorCallback"
            java.lang.String r1 = "execPendingActions"
            com.amap.bundle.logs.AMapLog.d(r0, r1)
            monitor-enter(r3)
            java.util.ArrayList<java.lang.Runnable> r0 = r3.mPendingActions     // Catch:{ all -> 0x0048 }
            if (r0 == 0) goto L_0x0046
            java.util.ArrayList<java.lang.Runnable> r0 = r3.mPendingActions     // Catch:{ all -> 0x0048 }
            int r0 = r0.size()     // Catch:{ all -> 0x0048 }
            if (r0 != 0) goto L_0x0015
            goto L_0x0046
        L_0x0015:
            java.util.ArrayList<java.lang.Runnable> r0 = r3.mPendingActions     // Catch:{ all -> 0x0048 }
            int r0 = r0.size()     // Catch:{ all -> 0x0048 }
            java.lang.Runnable[] r1 = r3.mTmpActions     // Catch:{ all -> 0x0048 }
            if (r1 == 0) goto L_0x0024
            java.lang.Runnable[] r1 = r3.mTmpActions     // Catch:{ all -> 0x0048 }
            int r1 = r1.length     // Catch:{ all -> 0x0048 }
            if (r1 >= r0) goto L_0x0028
        L_0x0024:
            java.lang.Runnable[] r1 = new java.lang.Runnable[r0]     // Catch:{ all -> 0x0048 }
            r3.mTmpActions = r1     // Catch:{ all -> 0x0048 }
        L_0x0028:
            java.util.ArrayList<java.lang.Runnable> r1 = r3.mPendingActions     // Catch:{ all -> 0x0048 }
            java.lang.Runnable[] r2 = r3.mTmpActions     // Catch:{ all -> 0x0048 }
            r1.toArray(r2)     // Catch:{ all -> 0x0048 }
            java.util.ArrayList<java.lang.Runnable> r1 = r3.mPendingActions     // Catch:{ all -> 0x0048 }
            r1.clear()     // Catch:{ all -> 0x0048 }
            monitor-exit(r3)     // Catch:{ all -> 0x0048 }
            r1 = 0
        L_0x0036:
            if (r1 >= r0) goto L_0x0042
            java.lang.Runnable[] r2 = r3.mTmpActions
            r2 = r2[r1]
            defpackage.aho.a(r2)
            int r1 = r1 + 1
            goto L_0x0036
        L_0x0042:
            r0 = 0
            r3.mTmpActions = r0
            return
        L_0x0046:
            monitor-exit(r3)     // Catch:{ all -> 0x0048 }
            return
        L_0x0048:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0048 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.LocatorCallback.execPendingActions():void");
    }
}
