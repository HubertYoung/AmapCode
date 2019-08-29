package defpackage;

import android.location.Location;
import android.os.Bundle;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: cqi reason: default package */
/* compiled from: ReportUtil */
public final class cqi {
    public static String a() {
        StringBuilder sb = new StringBuilder();
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        if (latestLocation != null) {
            Bundle extras = latestLocation.getExtras();
            if (extras != null && extras.containsKey("retype")) {
                sb.append("&retype=");
                sb.append(extras.getString("retype"));
            }
            sb.append("&timestamp=");
            sb.append(latestLocation.getTime());
        }
        return sb.toString();
    }
}
