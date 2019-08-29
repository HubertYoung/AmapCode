package org.altbeacon.beacon.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import java.util.List;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.distance.ModelSpecificDistanceCalculator;
import org.altbeacon.beacon.distance.c;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.utils.ProcessUtils;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

@TargetApi(21)
public class ScanJob extends JobService {
    /* access modifiers changed from: private */
    public static final String a = ScanJob.class.getSimpleName();
    /* access modifiers changed from: private */
    public ScanState b;
    private Handler c = new Handler();
    private m d;
    private boolean e = false;

    public boolean onStartJob(JobParameters jobParameters) {
        boolean startedScan;
        this.d = new m(this);
        if (jobParameters.getJobId() == 2) {
            d.b(a, "Running immdiate scan job: instance is " + this, new Object[0]);
        } else {
            d.b(a, "Running periodic scan job: instance is " + this, new Object[0]);
        }
        List queuedScanResults = r.a().b();
        d.a(a, "Processing %d queued scan resuilts", Integer.valueOf(queuedScanResults.size()));
        for (ScanResult result : queuedScanResults) {
            ScanRecord scanRecord = result.getScanRecord();
            if (scanRecord != null) {
                this.d.a(result.getDevice(), result.getRssi(), scanRecord.getBytes());
            }
        }
        d.a(a, "Done processing queued scan resuilts", new Object[0]);
        if (this.e) {
            d.a(a, "Scanning already started.  Resetting for current parameters", new Object[0]);
            startedScan = d();
        } else {
            startedScan = e();
        }
        this.c.removeCallbacksAndMessages(null);
        if (startedScan) {
            d.b(a, "Scan job running for " + this.b.l() + " millis", new Object[0]);
            this.c.postDelayed(new q(this, jobParameters), (long) this.b.l());
        } else {
            d.b(a, "Scanning not started so Scan job is complete.", new Object[0]);
            jobFinished(jobParameters, false);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void b() {
        d.a(a, "Checking to see if we need to start a passive scan", new Object[0]);
        boolean insideAnyRegion = false;
        for (Region region : this.b.f().a()) {
            i state = this.b.f().b(region);
            if (state != null && state.e()) {
                insideAnyRegion = true;
            }
        }
        if (insideAnyRegion) {
            d.b(a, "We are inside a beacon region.  We will not scan between cycles.", new Object[0]);
        } else if (VERSION.SDK_INT >= 26) {
            this.d.b(this.b.i());
        } else {
            d.a(a, "This is not Android O.  No scanning between cycles when using ScanJob", new Object[0]);
        }
    }

    public boolean onStopJob(JobParameters params) {
        if (params.getJobId() == 1) {
            d.b(a, "onStopJob called for periodic scan", new Object[0]);
        } else {
            d.b(a, "onStopJob called for immediate scan", new Object[0]);
        }
        this.c.removeCallbacksAndMessages(null);
        c();
        b();
        return false;
    }

    /* access modifiers changed from: private */
    public void c() {
        this.e = false;
        this.d.a().b();
        this.d.a().e();
        d.a(a, "Scanning stopped", new Object[0]);
    }

    private boolean d() {
        Long d2;
        this.b = ScanState.a((Context) this);
        this.b.a(System.currentTimeMillis());
        this.d.a(this.b.f());
        this.d.a(this.b.g());
        this.d.a(this.b.i());
        this.d.a(this.b.h());
        if (this.d.a() == null) {
            this.d.a(this.b.a().booleanValue(), (BluetoothCrashResolver) null);
        }
        if (VERSION.SDK_INT >= 26) {
            this.d.e();
        }
        long scanPeriod = (this.b.a().booleanValue() ? this.b.c() : this.b.e()).longValue();
        if (this.b.a().booleanValue()) {
            d2 = this.b.b();
        } else {
            d2 = this.b.d();
        }
        this.d.a().a(scanPeriod, d2.longValue(), this.b.a().booleanValue());
        this.e = true;
        if (scanPeriod <= 0) {
            d.c(a, "Starting scan with scan period of zero.  Exiting ScanJob.", new Object[0]);
            this.d.a().b();
            return false;
        } else if (this.d.c().size() > 0 || this.d.b().a().size() > 0) {
            this.d.a().a();
            return true;
        } else {
            this.d.a().b();
            return false;
        }
    }

    private boolean e() {
        g beaconManager = g.a(getApplicationContext());
        beaconManager.c();
        if (beaconManager.b()) {
            d.b(a, "scanJob version %s is starting up on the main process", "1.0");
        } else {
            d.b(a, "beaconScanJob library version %s is starting up on a separate process", "1.0");
            d.b(a, "beaconScanJob PID is " + ProcessUtils.c() + " with process name " + new ProcessUtils(this).a(), new Object[0]);
        }
        g.t();
        Beacon.a((c) new ModelSpecificDistanceCalculator(this));
        return d();
    }
}
