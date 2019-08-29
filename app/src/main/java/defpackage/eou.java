package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashSet;
import java.util.Iterator;

/* renamed from: eou reason: default package */
/* compiled from: LocHandler */
public final class eou extends Handler {
    public HashSet<Callback<Status>> a = new HashSet<>();
    public Object b = new Object();
    public int c;
    public HashSet<ang<Status>> d = new HashSet<>();
    public Object e = new Object();
    public HashSet<Callback<Status>> f = new HashSet<>();
    public long g = 0;
    private LocationInstrument h;

    public eou(LocationInstrument locationInstrument, Looper looper) {
        super(looper);
        this.h = locationInstrument;
        this.c = 7;
    }

    public final boolean a(Callback<Status> callback) {
        boolean remove;
        if (!this.a.contains(callback)) {
            return false;
        }
        synchronized (this.b) {
            try {
                remove = this.a.remove(callback);
            }
        }
        return remove;
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 241) {
            a(Status.ON_LOCATION_GPS_FAIL_LOOP, false);
            if (this.h.isLocating() && this.h.isProviderUsed(Provider.PROVIDER_NETWORK, this.c)) {
                this.h.requestLocationManagerUpdates(this.c);
            }
            synchronized (this.d) {
                Iterator<ang<Status>> it = this.d.iterator();
                while (it.hasNext()) {
                    ang next = it.next();
                    if (next != null) {
                        next.onOriginalLocationChange(Status.ON_LOCATION_GPS_FAIL_LOOP);
                    }
                }
            }
        } else if (i == 243) {
            a(Status.ON_LOCATION_GPS_OK, false);
        } else if (i == 240) {
            a(Status.ON_LOCATION_OK, true);
        } else if (i == 242) {
            synchronized (this.d) {
                Iterator<ang<Status>> it2 = this.d.iterator();
                while (it2.hasNext()) {
                    ang next2 = it2.next();
                    if (next2 != null) {
                        next2.onOriginalLocationChange(Status.ON_LOCATION_OK);
                    }
                }
            }
        }
    }

    private void a(Status status, boolean z) {
        Callback[] callbackArr;
        Callback[] callbackArr2;
        long j = this.h.getLocInfo() == null ? 0 : this.h.getLocInfo().ticktime;
        if (status == null) {
            this.g = j;
            return;
        }
        if (this.a != null) {
            long j2 = j - this.g;
            if (!z || j2 > 400 || this.g == 0) {
                synchronized (this.b) {
                    callbackArr2 = new Callback[this.a.size()];
                    this.a.toArray(callbackArr2);
                }
                for (Callback callback : callbackArr2) {
                    if (callback != null) {
                        callback.callback(status);
                    }
                }
                if (z || this.g == 0) {
                    this.g = j;
                }
            }
        }
        if (this.f != null) {
            synchronized (this.e) {
                callbackArr = new Callback[this.f.size()];
                this.f.toArray(callbackArr);
            }
            for (Callback callback2 : callbackArr) {
                if (callback2 != null) {
                    callback2.callback(status);
                }
            }
        }
    }
}
