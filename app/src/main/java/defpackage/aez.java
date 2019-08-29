package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.entity.common.OfflineSearchMode;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: aez reason: default package */
/* compiled from: OfflineModeFactory */
public final class aez {
    public static OfflineSearchMode a(int i, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return a(str);
        }
        OfflineSearchMode offlineSearchMode = new OfflineSearchMode();
        offlineSearchMode.searchType = i;
        offlineSearchMode.strDataPath = afa.a();
        offlineSearchMode.strKeyWord = str;
        offlineSearchMode.strAdCode = String.valueOf(new GeoPoint(Double.parseDouble(str2), Double.parseDouble(str3)).getAdCode());
        offlineSearchMode.strLongitude = str2;
        offlineSearchMode.strLatitude = str3;
        return offlineSearchMode;
    }

    public static OfflineSearchMode a(int i, String str, GeoPoint geoPoint) {
        DPoint a = cfg.a((long) geoPoint.x, (long) geoPoint.y);
        return a(i, str, String.valueOf(a.x), String.valueOf(a.y));
    }

    public static OfflineSearchMode a(String str) {
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapCenter());
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            int a = li.a().a(latestPosition.getLongitude(), latestPosition.getLatitude());
            if (glGeoPoint2GeoPoint != null && a == glGeoPoint2GeoPoint.getAdCode()) {
                glGeoPoint2GeoPoint = latestPosition;
            }
        }
        if (glGeoPoint2GeoPoint == null) {
            return null;
        }
        return a(1, str, glGeoPoint2GeoPoint);
    }
}
