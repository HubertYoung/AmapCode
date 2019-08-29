package defpackage;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Iterator;

/* renamed from: ecv reason: default package */
/* compiled from: FootNaviUtil */
public final class ecv {
    public static int a(int i) {
        if (i == 0) {
            return 4;
        }
        if (i < 2) {
            return 3;
        }
        return i < 4 ? 2 : 1;
    }

    public static int a() {
        GpsStatus gpsStatus = LocationInstrument.getInstance().getGpsStatus(null);
        int i = 0;
        if (gpsStatus != null) {
            int maxSatellites = gpsStatus.getMaxSatellites();
            Iterator<GpsSatellite> it = gpsStatus.getSatellites().iterator();
            while (it.hasNext() && i <= maxSatellites) {
                if (it.next().usedInFix()) {
                    i++;
                }
            }
        }
        return i;
    }
}
