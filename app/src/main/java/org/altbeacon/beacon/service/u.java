package org.altbeacon.beacon.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.altbeacon.beacon.b.d;

/* compiled from: Stats */
public final class u {
    private static final u a = new u();
    private static final SimpleDateFormat b = new SimpleDateFormat("HH:mm:ss.SSS");
    private ArrayList<v> c;
    private long d = 0;
    private boolean e;
    private boolean f;
    private boolean g;
    private v h;

    public static u a() {
        return a;
    }

    private u() {
        e();
    }

    public final boolean b() {
        return this.g;
    }

    public final void c() {
        g();
        this.h.a++;
        if (this.h.c == null) {
            this.h.c = new Date();
        }
        if (this.h.d != null) {
            long timeSinceLastDetection = new Date().getTime() - this.h.d.getTime();
            if (timeSinceLastDetection > this.h.b) {
                this.h.b = timeSinceLastDetection;
            }
        }
        this.h.d = new Date();
    }

    private void d() {
        Date boundaryTime = new Date();
        if (this.h != null) {
            boundaryTime = new Date(this.h.e.getTime() + this.d);
            this.h.f = boundaryTime;
            if (!this.f && this.e) {
                a(this.h, true);
            }
        }
        this.h = new v();
        this.h.e = boundaryTime;
        this.c.add(this.h);
        if (this.f) {
            f();
        }
    }

    private void e() {
        this.c = new ArrayList<>();
        d();
    }

    private static void a(v sample, boolean showHeader) {
        if (showHeader) {
            d.a("Stats", "sample start time, sample stop time, first detection time, last detection time, max millis between detections, detection count", new Object[0]);
        }
        d.a("Stats", "%s, %s, %s, %s, %s, %s", a(sample.e), a(sample.f), a(sample.c), a(sample.d), Long.valueOf(sample.b), Long.valueOf(sample.a));
    }

    private static String a(Date d2) {
        String formattedDate = "";
        if (d2 != null) {
            synchronized (b) {
                formattedDate = b.format(d2);
            }
        }
        return formattedDate;
    }

    private void f() {
        d.a("Stats", "--- Stats for %s samples", Integer.valueOf(this.c.size()));
        boolean firstPass = true;
        Iterator<v> it = this.c.iterator();
        while (it.hasNext()) {
            a(it.next(), firstPass);
            firstPass = false;
        }
    }

    private void g() {
        if (this.h == null || (this.d > 0 && new Date().getTime() - this.h.e.getTime() >= this.d)) {
            d();
        }
    }
}
