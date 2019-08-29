package defpackage;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import java.util.Iterator;

/* renamed from: rx reason: default package */
/* compiled from: CoverSnrCalculator */
public final class rx {
    public static float a(GpsStatus gpsStatus) {
        DriveUtil.addSnrLog("CoverSnrCalculator getCoverSnrPercentage gpsStatus：".concat(String.valueOf(gpsStatus)));
        System.currentTimeMillis();
        if (gpsStatus == null) {
            return -1.0f;
        }
        int i = 0;
        float f = 0.0f;
        try {
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            if (satellites != null) {
                Iterator<GpsSatellite> it = satellites.iterator();
                if (it != null) {
                    float f2 = 0.0f;
                    while (it.hasNext()) {
                        GpsSatellite next = it.next();
                        if (next.usedInFix()) {
                            i++;
                            f2 += next.getSnr();
                        }
                    }
                    if (i != 0) {
                        f2 /= (float) i;
                    }
                    f = (float) ((Math.atan((((double) f2) - 28.0d) / 6.1554d) + 1.5707963267948966d) / 3.141592653589793d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.currentTimeMillis();
        if (Float.isNaN(f)) {
            f = 0.1f;
        }
        return f;
    }
}
