package org.altbeacon.beacon.service;

import android.os.AsyncTask;
import android.support.annotation.WorkerThread;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.j;
import org.altbeacon.beacon.service.a.q;

/* compiled from: ScanHelper */
final class p extends AsyncTask<o, Void, Void> {
    final c a = c.a();
    final /* synthetic */ m b;
    private final q c;

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void onProgressUpdate(Object[] objArr) {
    }

    p(m mVar, q nonBeaconLeScanCallback) {
        this.b = mVar;
        this.c = nonBeaconLeScanCallback;
    }

    /* access modifiers changed from: private */
    @WorkerThread
    /* renamed from: a */
    public Void doInBackground(o... params) {
        o scanData = params[0];
        Beacon beacon = null;
        for (j a2 : this.b.i) {
            beacon = a2.a(scanData.c, scanData.a, scanData.b);
            if (beacon != null) {
                break;
            }
        }
        if (beacon != null) {
            if (d.a()) {
                d.a(m.a, "Beacon packet detected for: " + beacon + " with rssi " + beacon.f(), new Object[0]);
            }
            this.a.c();
            if (this.b.d != null && !this.b.d.c() && !this.b.g.a(scanData.b.getAddress(), scanData.c)) {
                d.b(m.a, "Non-distinct packets detected in a single scan.  Restarting scans unecessary.", new Object[0]);
                this.b.d.d();
            }
            this.b.a(beacon);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final void onPreExecute() {
    }
}
