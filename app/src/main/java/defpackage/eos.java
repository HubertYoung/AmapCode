package defpackage;

import android.location.Location;
import java.util.ArrayList;
import java.util.List;

/* renamed from: eos reason: default package */
/* compiled from: GpsCollector */
public final class eos {
    public final List<Location> a = new ArrayList(3);
    public long b = 0;

    public final void a() {
        synchronized (this.a) {
            this.a.clear();
        }
    }
}
