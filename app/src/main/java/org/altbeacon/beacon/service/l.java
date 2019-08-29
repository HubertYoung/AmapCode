package org.altbeacon.beacon.service;

/* compiled from: RunningAverageRssiFilter */
final class l implements Comparable<l> {
    Integer a;
    long b;
    final /* synthetic */ k c;

    private l(k kVar) {
        this.c = kVar;
    }

    /* synthetic */ l(k x0, byte b2) {
        this(x0);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public int compareTo(l arg0) {
        return this.a.compareTo(arg0.a);
    }
}
