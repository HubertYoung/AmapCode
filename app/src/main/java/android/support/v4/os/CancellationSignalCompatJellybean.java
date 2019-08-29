package android.support.v4.os;

import android.os.CancellationSignal;

class CancellationSignalCompatJellybean {
    CancellationSignalCompatJellybean() {
    }

    public static Object a() {
        return new CancellationSignal();
    }

    public static void a(Object obj) {
        ((CancellationSignal) obj).cancel();
    }
}
