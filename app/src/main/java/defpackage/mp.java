package defpackage;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drive.common.yuncontrol.YunConfigurationManager;
import com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage;
import com.amap.bundle.drive.navi.drivenavi.simulate.page.AjxRouteCarNaviSimulatePage;
import com.amap.bundle.drive.navi.motornavi.page.AjxRouteMotorNaviPage;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.context.AjxContext;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: mp reason: default package */
/* compiled from: DriveUtilImpl */
public class mp implements djk {
    private boolean a = false;

    public final void a() {
        this.a = false;
    }

    public final boolean b() {
        return DriveUtil.isAvoidLimitedPath();
    }

    public final boolean c() {
        return DriveUtil.getTruckAvoidSwitch();
    }

    public final boolean d() {
        return DriveUtil.getTruckAvoidLimitedLoad();
    }

    public final float e() {
        return DriveUtil.getTruckHeight();
    }

    public final float f() {
        return DriveUtil.getTruckLoad();
    }

    public final String g() {
        return DriveUtil.getCarPlateNumber();
    }

    public final void a(String str) {
        DriveUtil.putCarPlateNumber(str);
    }

    public final String h() {
        return DriveUtil.getTruckCarPlateNumber();
    }

    public final boolean i() {
        return (DriveUtil.getPOIHome() == null || DriveUtil.getPOICompany() == null) ? false : true;
    }

    public final JSONArray j() {
        JSONArray jSONArray = new JSONArray();
        List<RdCameraPaymentItem> localRdCameraPaymentData = DriveUtil.getLocalRdCameraPaymentData();
        if (localRdCameraPaymentData == null || localRdCameraPaymentData.isEmpty()) {
            return jSONArray;
        }
        for (RdCameraPaymentItem next : localRdCameraPaymentData) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("startT", String.valueOf(next.getNaviStarttimestamp()));
                jSONObject.put("endT", String.valueOf(next.navi_timestamp));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public final void a(int i) {
        if (bno.a) {
            ku.a().c("NaviMonitor", "[DriveUtilImpl]onNetworkConnected netType = ".concat(String.valueOf(i)));
        }
        if (NaviManager.a().b()) {
            NaviManager.a().a(53, DriveUtil.getTranslatedNetworkType());
        }
    }

    public final void a(Context context, Uri uri) {
        String host = uri.getHost();
        if (host != null) {
            List<String> pathSegments = uri.getPathSegments();
            String str = null;
            if (pathSegments != null && !pathSegments.isEmpty()) {
                str = pathSegments.get(0);
            }
            String queryParameter = uri.getQueryParameter("featureName");
            if (TextUtils.equals(host, AutoConstants.AUTO_FILE_ROUTE) && !TextUtils.isEmpty(str) && TextUtils.equals(str, "plan")) {
                DriveUtil.doOpenFeatureShowRouteResultNew(RouteType.CAR, uri);
            } else if (TextUtils.equals(AutoConstants.AUTO_FILE_ROUTE, host)) {
                DriveUtil.startRoute(context, RouteType.CAR, uri);
            } else if (!host.equalsIgnoreCase(BaseIntentDispatcher.HOST_OPENFEATURE) || TextUtils.isEmpty(queryParameter) || !queryParameter.equalsIgnoreCase("openFromToResult")) {
                if (host.equalsIgnoreCase(BaseIntentDispatcher.HOST_OPENFEATURE) && !TextUtils.isEmpty(queryParameter) && queryParameter.equalsIgnoreCase("FromTo")) {
                    DriveUtil.doOpenFeatureFromTo(RouteType.CAR, uri);
                }
            } else {
                DriveUtil.doOpenFeatureShowRouteResult(context, uri);
            }
        }
    }

    public final void a(Uri uri) {
        String host = uri.getHost();
        if (host != null) {
            List<String> pathSegments = uri.getPathSegments();
            String str = null;
            if (pathSegments != null && !pathSegments.isEmpty()) {
                str = pathSegments.get(0);
            }
            if (TextUtils.equals(host, AutoConstants.AUTO_FILE_ROUTE) && !TextUtils.isEmpty(str) && TextUtils.equals(str, "plan")) {
                DriveUtil.doOpenFeatureShowRouteResultNew(RouteType.MOTOR, uri);
            }
        }
    }

    public final void b(Context context, Uri uri) {
        String host = uri.getHost();
        if (host != null) {
            List<String> pathSegments = uri.getPathSegments();
            String str = null;
            if (pathSegments != null && !pathSegments.isEmpty()) {
                str = pathSegments.get(0);
            }
            String queryParameter = uri.getQueryParameter("featureName");
            if (TextUtils.equals(host, AutoConstants.AUTO_FILE_ROUTE) && !TextUtils.isEmpty(str) && TextUtils.equals(str, "plan")) {
                DriveUtil.doOpenFeatureShowRouteResultNew(RouteType.TRUCK, uri);
            } else if (TextUtils.equals(AutoConstants.AUTO_FILE_ROUTE, host)) {
                DriveUtil.startRoute(context, RouteType.TRUCK, uri);
            } else if (!host.equalsIgnoreCase(BaseIntentDispatcher.HOST_OPENFEATURE) || TextUtils.isEmpty(queryParameter) || !queryParameter.equalsIgnoreCase("openFromToResult")) {
                if (host.equalsIgnoreCase(BaseIntentDispatcher.HOST_OPENFEATURE) && !TextUtils.isEmpty(queryParameter) && queryParameter.equalsIgnoreCase("FromTo")) {
                    DriveUtil.doOpenFeatureFromTo(RouteType.TRUCK, uri);
                }
            } else {
                DriveUtil.doOpenFeatureShowRouteResult(context, uri);
            }
        }
    }

    public final void k() {
        YunConfigurationManager.a().b();
    }

    public final String l() {
        return DriveUtil.getLastRoutingChoice();
    }

    public final int m() {
        return DriveUtil.getContentOptions(0);
    }

    public final dic a(AbstractBasePage abstractBasePage, Object obj) {
        if (obj == null || !(obj instanceof AjxContext)) {
            return null;
        }
        return new pt(abstractBasePage, (AjxContext) obj);
    }

    public final dic b(AbstractBasePage abstractBasePage, Object obj) {
        if (obj == null || !(obj instanceof AjxContext)) {
            return null;
        }
        return new pv(abstractBasePage, (AjxContext) obj);
    }

    public final dic c(AbstractBasePage abstractBasePage, Object obj) {
        if (obj == null || !(obj instanceof AjxContext)) {
            return null;
        }
        return new nr(abstractBasePage, (AjxContext) obj);
    }

    public final dic d(AbstractBasePage abstractBasePage, Object obj) {
        if (obj == null || !(obj instanceof AjxContext)) {
            return null;
        }
        return new pu(abstractBasePage, (AjxContext) obj);
    }

    public final List<String> n() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(AjxRouteCarNaviPage.class.getSimpleName());
        arrayList.add(AjxRouteMotorNaviPage.class.getSimpleName());
        arrayList.add(AjxRouteCarNaviSimulatePage.class.getSimpleName());
        return arrayList;
    }
}
