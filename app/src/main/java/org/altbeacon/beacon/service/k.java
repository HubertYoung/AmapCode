package org.altbeacon.beacon.service;

import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.altbeacon.beacon.b.d;

/* compiled from: RunningAverageRssiFilter */
public class k implements j {
    private static long a = 20000;
    private ArrayList<l> b = new ArrayList<>();

    public final void a(Integer rssi) {
        l measurement = new l(this, 0);
        measurement.a = rssi;
        measurement.b = SystemClock.elapsedRealtime();
        this.b.add(measurement);
    }

    public final boolean a() {
        return this.b.size() == 0;
    }

    public final double b() {
        c();
        int size = this.b.size();
        int startIndex = 0;
        int endIndex = size - 1;
        if (size > 2) {
            startIndex = (size / 10) + 1;
            endIndex = (size - (size / 10)) - 2;
        }
        double sum = 0.0d;
        for (int i = startIndex; i <= endIndex; i++) {
            sum += (double) this.b.get(i).a.intValue();
        }
        double runningAverage = sum / ((double) ((endIndex - startIndex) + 1));
        d.a("RunningAverageRssiFilter", "Running average mRssi based on %s measurements: %s", Integer.valueOf(size), Double.valueOf(runningAverage));
        return runningAverage;
    }

    private synchronized void c() {
        ArrayList newMeasurements = new ArrayList();
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            l measurement = iterator.next();
            if (SystemClock.elapsedRealtime() - measurement.b < a) {
                newMeasurements.add(measurement);
            }
        }
        this.b = newMeasurements;
        Collections.sort(this.b);
    }

    public static void a(long newSampleExpirationMilliseconds) {
        a = newSampleExpirationMilliseconds;
    }
}
