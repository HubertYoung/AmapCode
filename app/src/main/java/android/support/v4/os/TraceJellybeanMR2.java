package android.support.v4.os;

import android.os.Trace;

class TraceJellybeanMR2 {
    TraceJellybeanMR2() {
    }

    public static void a(String str) {
        Trace.beginSection(str);
    }

    public static void a() {
        Trace.endSection();
    }
}
