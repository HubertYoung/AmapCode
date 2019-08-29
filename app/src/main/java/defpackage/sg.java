package defpackage;

import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: sg reason: default package */
/* compiled from: RouteCarResultShareUtil */
public final class sg {
    public ICarRouteResult a = null;

    public sg(ICarRouteResult iCarRouteResult) {
        this.a = iCarRouteResult;
    }

    public final String a() {
        if (this.a == null || !this.a.hasData()) {
            return null;
        }
        NavigationPath focusNavigationPath = this.a.getFocusNavigationPath();
        if (focusNavigationPath == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a(R.string.autonavi_route_car_result_full_string));
        sb.append(cfe.a(focusNavigationPath.mPathlength));
        sb.append("(约");
        sb.append(lf.a(focusNavigationPath.mCostTime));
        sb.append(")\n");
        if (focusNavigationPath.mTaxiFee > 0) {
            sb.append(String.format(a(R.string.autonavi_car_share_taxi_format), new Object[]{Integer.valueOf(focusNavigationPath.mTaxiFee)}));
        }
        return sb.toString();
    }

    public final String a(String str) {
        if (this.a == null || !this.a.hasData()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_car_share_from_text));
        sb2.append(this.a.getShareFromPOI().getName());
        sb.append(sb2.toString());
        ArrayList<POI> shareMidPOIs = this.a.getShareMidPOIs();
        if (shareMidPOIs != null && shareMidPOIs.size() > 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a(R.string.autonavi_car_result_share_pass));
            sb3.append(shareMidPOIs.get(0).getName());
            sb.append(sb3);
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_car_share_text_to_string));
        sb4.append(this.a.getShareToPOI().getName());
        sb.append(sb4.toString());
        return sb.toString();
    }

    public final String b(String str) {
        if (this.a == null || !this.a.hasData()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_car_share_from_text));
        sb.append(this.a.getShareFromPOI().getName());
        if (this.a.hasMidPos()) {
            ArrayList<POI> shareMidPOIs = this.a.getShareMidPOIs();
            if (shareMidPOIs != null && shareMidPOIs.size() > 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(a(R.string.autonavi_car_result_share_pass));
                sb2.append(shareMidPOIs.get(0).getName());
                sb.append(sb2);
            }
        }
        sb.append(a(R.string.autonavi_car_share_text_to_string));
        sb.append(this.a.getShareToPOI().getName());
        NavigationPath focusNavigationPath = this.a.getFocusNavigationPath();
        if (focusNavigationPath != null) {
            sb.append(",");
            sb.append(a(R.string.autonavi_route_car_result_full_string));
            sb.append(cfe.a(focusNavigationPath.mPathlength));
            sb.append(",");
            sb.append(a(R.string.autonavi_car_result_share_about_need));
            sb.append(lf.a(focusNavigationPath.mCostTime));
            if (focusNavigationPath.mTaxiFee > 0) {
                sb.append(",");
                sb.append(String.format(a(R.string.autonavi_car_share_taxi_format), new Object[]{Integer.valueOf(focusNavigationPath.mTaxiFee)}));
            }
        }
        return sb.toString();
    }

    private static String a(int i) {
        return AMapAppGlobal.getApplication().getResources().getString(i);
    }
}
