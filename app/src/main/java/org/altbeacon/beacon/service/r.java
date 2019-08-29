package org.altbeacon.beacon.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.BeaconLocalBroadcastProcessor;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;

@TargetApi(21)
/* compiled from: ScanJobScheduler */
public class r {
    private static final String a = r.class.getSimpleName();
    private static final Object b = new Object();
    @Nullable
    private static volatile r c = null;
    @NonNull
    private Long d = Long.valueOf(0);
    @NonNull
    private List<ScanResult> e = new ArrayList();
    @Nullable
    private BeaconLocalBroadcastProcessor f;

    @NonNull
    public static r a() {
        r instance = c;
        if (instance == null) {
            synchronized (b) {
                try {
                    instance = c;
                    if (instance == null) {
                        r instance2 = new r();
                        try {
                            c = instance2;
                            instance = instance2;
                        } catch (Throwable th) {
                            th = th;
                            r rVar = instance2;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return instance;
    }

    private r() {
    }

    private void a(Context context) {
        if (this.f == null) {
            this.f = new BeaconLocalBroadcastProcessor(context);
            this.f.a();
        }
    }

    /* access modifiers changed from: 0000 */
    public final List<ScanResult> b() {
        List retval = this.e;
        this.e = new ArrayList();
        return retval;
    }

    private void a(Context context, g beaconManager, ScanState scanState) {
        scanState.a(beaconManager);
        d.a(a, "Applying scan job settings with background mode " + scanState.a(), new Object[0]);
        a(context, scanState, false);
    }

    public final void a(Context context, g beaconManager) {
        d.a(a, "Applying settings to ScanJob", new Object[0]);
        context.getSystemService("jobscheduler");
        a(context, beaconManager, ScanState.a(context));
    }

    public final void a(Context context, List<ScanResult> scanResults) {
        if (scanResults != null) {
            this.e.addAll(scanResults);
        }
        synchronized (this) {
            if (System.currentTimeMillis() - this.d.longValue() > 10000) {
                d.a(a, "scheduling an immediate scan job because last did " + (System.currentTimeMillis() - this.d.longValue()) + "seconds ago.", new Object[0]);
                this.d = Long.valueOf(System.currentTimeMillis());
                a(context, ScanState.a(context), true);
                return;
            }
            d.a(a, "Not scheduling an immediate scan job because we just did recently.", new Object[0]);
        }
    }

    private void a(Context context, ScanState scanState, boolean backgroundWakeup) {
        long millisToNextJobStart;
        a(context);
        long betweenScanPeriod = (long) (scanState.k() - scanState.l());
        if (backgroundWakeup) {
            d.a(a, "We just woke up in the background based on a new scan result.  Start scan job immediately.", new Object[0]);
            millisToNextJobStart = 0;
        } else {
            if (betweenScanPeriod > 0) {
                millisToNextJobStart = SystemClock.elapsedRealtime() % ((long) scanState.k());
            } else {
                millisToNextJobStart = 0;
            }
            if (millisToNextJobStart < 50) {
                millisToNextJobStart = 50;
            }
        }
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (!backgroundWakeup && scanState.a().booleanValue()) {
            d.a(a, "Not scheduling an immediate scan because we are in background mode.   Cancelling existing immediate scan.", new Object[0]);
            jobScheduler.cancel(2);
        } else if (millisToNextJobStart < ((long) (scanState.k() - 50))) {
            d.a(a, "Scheduling immediate ScanJob to run in " + millisToNextJobStart + " millis", new Object[0]);
            int error = jobScheduler.schedule(new Builder(2, new ComponentName(context, ScanJob.class)).setPersisted(true).setExtras(new PersistableBundle()).setMinimumLatency(millisToNextJobStart).setOverrideDeadline(millisToNextJobStart).build());
            if (error < 0) {
                d.d(a, "Failed to schedule scan job.  Beacons will not be detected. Error: " + error, new Object[0]);
            }
        }
        Builder periodicJobBuilder = new Builder(1, new ComponentName(context, ScanJob.class)).setPersisted(true).setExtras(new PersistableBundle());
        if (VERSION.SDK_INT >= 24) {
            periodicJobBuilder.setPeriodic((long) scanState.k(), 0).build();
        } else {
            periodicJobBuilder.setPeriodic((long) scanState.k()).build();
        }
        d.a(a, "Scheduling ScanJob to run every " + scanState.k() + " millis", new Object[0]);
        int error2 = jobScheduler.schedule(periodicJobBuilder.build());
        if (error2 < 0) {
            d.d(a, "Failed to schedule scan job.  Beacons will not be detected. Error: " + error2, new Object[0]);
        }
    }
}
