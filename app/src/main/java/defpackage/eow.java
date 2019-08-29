package defpackage;

import android.location.Location;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/* renamed from: eow reason: default package */
/* compiled from: LocationTemp */
public final class eow {
    private Location a;
    private GeoPoint b;
    private WriteLock c = new ReentrantReadWriteLock().writeLock();

    public final void a(Location location) {
        this.c.lock();
        try {
            this.a = new Location(location);
            this.b = null;
        } finally {
            this.c.unlock();
        }
    }

    public final GeoPoint a() {
        this.c.lock();
        try {
            if (this.b == null) {
                this.b = LocationInstrument.getOffsetedPoint(this.a);
            }
            return new GeoPoint(this.b.x, this.b.y);
        } finally {
            this.c.unlock();
        }
    }
}
