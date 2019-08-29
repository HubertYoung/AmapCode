package defpackage;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.b;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: mx reason: default package */
/* compiled from: NodeNavigationHelper */
public final class mx {
    public static boolean a(Context context) {
        if (!b(context)) {
            DriveSpUtil.saveCurrentNavigation(context, -1, -1, null, null);
            return false;
        } else if (NaviManager.a().e()) {
            return false;
        } else {
            return true;
        }
    }

    public static void a(POI poi) {
        Activity topActivity = AMapAppGlobal.getTopActivity();
        POI createPOI = POIFactory.createPOI();
        if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            createPOI.setName(AMapPageUtil.getAppContext().getString(R.string.LocationMe));
            createPOI.setPoint(latestPosition);
        } else if (LocationInstrument.getInstance().getLatestPosition() == null) {
            GeoPoint geoPoint = new GeoPoint();
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            geoPoint.x = mapSharePreference.getIntValue("X", 221010326);
            if (geoPoint.x == 0) {
                geoPoint.x = 221010326;
            }
            geoPoint.y = mapSharePreference.getIntValue("Y", 101713397);
            if (geoPoint.y == 0) {
                geoPoint.y = 101713397;
            }
        }
        ArrayList<POI> pointsPassbyAtException = DriveSpUtil.getPointsPassbyAtException(AMapPageUtil.getAppContext());
        new mx();
        String c = c(AMapPageUtil.getAppContext());
        if (TextUtils.equals(DriveUtil.NAVI_TYPE_MOTORBIKE, c)) {
            no.a(topActivity, createPOI, pointsPassbyAtException, poi);
        } else {
            nm.a(topActivity, createPOI, pointsPassbyAtException, poi, c);
        }
    }

    public static void a() {
        DriveSpUtil.saveCurrentNavigation(AMapPageUtil.getAppContext(), -1, -1, null, null);
        DriveSpUtil.setSafeHomeShareId(AMapPageUtil.getAppContext(), null);
        DriveSpUtil.setSafeHomeShareEnd(AMapPageUtil.getAppContext(), true);
    }

    private static boolean b(Context context) {
        String navigationTimeAtException = DriveSpUtil.getNavigationTimeAtException(context);
        if (TextUtils.isEmpty(navigationTimeAtException)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(navigationTimeAtException);
            if (lf.a() - jSONObject.getLong(b.a) < ((long) jSONObject.getInt(b.b))) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String c(Context context) {
        return DriveSpUtil.getString(context, "navigation_navitype_at_exception", "");
    }
}
