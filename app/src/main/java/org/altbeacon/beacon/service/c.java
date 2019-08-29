package org.altbeacon.beacon.service;

import android.os.SystemClock;

/* compiled from: DetectionTracker */
public final class c {
    private static final c a = new c();
    private long b = 0;

    private c() {
    }

    public static c a() {
        return a;
    }

    public final long b() {
        return this.b;
    }

    public final void c() {
        this.b = SystemClock.elapsedRealtime();
    }
}
