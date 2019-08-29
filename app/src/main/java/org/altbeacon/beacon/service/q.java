package org.altbeacon.beacon.service;

import android.app.job.JobParameters;
import org.altbeacon.beacon.b.d;

/* compiled from: ScanJob */
final class q implements Runnable {
    final /* synthetic */ JobParameters a;
    final /* synthetic */ ScanJob b;

    q(ScanJob this$0, JobParameters jobParameters) {
        this.b = this$0;
        this.a = jobParameters;
    }

    public final void run() {
        d.b(ScanJob.a, "Scan job runtime expired", new Object[0]);
        this.b.c();
        this.b.b.j();
        this.b.b();
        this.b.jobFinished(this.a, false);
    }
}
