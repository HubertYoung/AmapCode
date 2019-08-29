package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.tencent.connect.common.Constants;
import com.uc.webview.export.internal.setup.UCAsyncTask;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/* renamed from: rh reason: default package */
/* compiled from: MitVerifyCheckCommandUtils */
public final class rh {
    public static int a(POI poi, POI poi2, List<POI> list, int i, int i2, boolean z, int i3) {
        if (list == null || list.size() <= 3) {
            int i4 = 0;
            if (list != null && list.size() == 0) {
                boolean z2 = true;
                if (!DriveUtil.isSamePoi(poi, poi2) && (poi != null || !a(poi2))) {
                    z2 = false;
                }
                if (z2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(i2);
                    sb.append("CODE_ERROR_START_POINT_SAME_TO_END_POINT ");
                    tq.b("NaviMonitor", "verifyMitRequestParms", sb.toString());
                    return 10009;
                }
            }
            if (rg.a(poi, poi2, list) || a(list)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i2);
                sb2.append("CODE_ERROR_MID_POINT_WITHIN_START_POINT_OR_WITHIN_END_POINT ");
                tq.b("NaviMonitor", "verifyMitRequestParms", sb2.toString());
                return 10010;
            } else if (rg.a(list)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(i2);
                sb3.append("CODE_ERROR_HAS_VIA_POINT_SAME ");
                tq.b("NaviMonitor", "verifyMitRequestParms", sb3.toString());
                return 10111;
            } else {
                while (i4 < list.size()) {
                    if (rg.a(list.get(i4))) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(i2);
                        sb4.append("CODE_ERROR_NOT_SET_HOME ");
                        tq.b("NaviMonitor", "verifyMitRequestParms", sb4.toString());
                        return 10012;
                    } else if (rg.b(list.get(i4))) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(i2);
                        sb5.append("CODE_ERROR_NOT_SET_COMPANY ");
                        tq.b("NaviMonitor", "verifyMitRequestParms", sb5.toString());
                        return UCAsyncTask.getPriority;
                    } else {
                        i4++;
                    }
                }
                if (rg.a(poi)) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(i2);
                    sb6.append("CODE_ERROR_NOT_SET_HOME ");
                    tq.b("NaviMonitor", "verifyMitRequestParms", sb6.toString());
                    return 10012;
                } else if (rg.b(poi)) {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(i2);
                    sb7.append("CODE_ERROR_NOT_SET_COMPANY ");
                    tq.b("NaviMonitor", "verifyMitRequestParms", sb7.toString());
                    return UCAsyncTask.getPriority;
                } else if (rg.a(poi2)) {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(i2);
                    sb8.append("CODE_ERROR_NOT_SET_HOME ");
                    tq.b("NaviMonitor", "verifyMitRequestParms", sb8.toString());
                    return 10012;
                } else if (rg.b(poi2)) {
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(i2);
                    sb9.append("CODE_ERROR_NOT_SET_COMPANY ");
                    tq.b("NaviMonitor", "verifyMitRequestParms", sb9.toString());
                    return UCAsyncTask.getPriority;
                } else if (!sa.a(i)) {
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(i2);
                    sb10.append(" !VoiceDriveUtils.isSupportParamMethod(params) CODE_ERROR_UNSUPPORT_PREFERENCE_SETTING ");
                    tq.b("NaviMonitor", "verifyMitRequestParms", sb10.toString());
                    return 10034;
                } else {
                    String c = sa.c(i);
                    if ((!NetworkReachability.b() || DriveSpUtil.shouldRouteOffline()) && c.contains("2")) {
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append(i2);
                        sb11.append(" CODE_ERROR_OFFLINE_UNSUPPORT_AVOID_JAM ");
                        tq.b("NaviMonitor", "verifyMitRequestParms", sb11.toString());
                        return -1;
                    }
                    if (!TextUtils.isEmpty(c)) {
                        if (i2 != 1003) {
                            DriveUtil.putLastRoutingChoice(c);
                        } else if (((bax) a.a.a(bax.class)) != null) {
                            if (z || i3 == RouteType.CAR.getValue()) {
                                DriveUtil.putLastRoutingChoice(c);
                            } else if (i3 == RouteType.TRUCK.getValue()) {
                                DriveUtil.putTruckRoutingChoice(c);
                            } else if (i3 == RouteType.MOTOR.getValue() && (c.equals("4") || c.equals("8") || c.equals("16"))) {
                                DriveUtil.putMotorRoutingChoice(c);
                            }
                        }
                    }
                    return -1;
                }
            }
        } else {
            StringBuilder sb12 = new StringBuilder();
            sb12.append(i2);
            sb12.append("CODE_ERROR_VIA_NUMBER_OVER_THREE ");
            tq.b("NaviMonitor", "verifyMitRequestParms", sb12.toString());
            return Constants.REQUEST_QZONE_SHARE;
        }
    }

    public static int a(int i) {
        if (!sa.a(i)) {
            tq.b("NaviMonitor", "verifyCancelRouteParams", " !VoiceDriveUtils.isSupportParamMethod(params) CODE_ERROR_UNSUPPORT_PREFERENCE_SETTING ");
            return 10034;
        }
        String c = sa.c(i);
        String lastRoutingChoice = DriveUtil.getLastRoutingChoice();
        String str = "";
        String[] split = c.split("\\|");
        if (split != null && split.length > 0) {
            int i2 = 0;
            while (i2 < split.length) {
                String str2 = split[i2];
                if (lastRoutingChoice.contains(MergeUtil.SEPARATOR_KV.concat(String.valueOf(str2)))) {
                    lastRoutingChoice = lastRoutingChoice.replace(MergeUtil.SEPARATOR_KV.concat(String.valueOf(str2)), "");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(MergeUtil.SEPARATOR_KV);
                    if (lastRoutingChoice.contains(sb.toString())) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str2);
                        sb2.append(MergeUtil.SEPARATOR_KV);
                        lastRoutingChoice = lastRoutingChoice.replace(sb2.toString(), "");
                    } else {
                        lastRoutingChoice = lastRoutingChoice.replace(str2, "");
                    }
                }
                i2++;
                str = lastRoutingChoice;
            }
        }
        if (i == 0) {
            str = "";
        }
        DriveUtil.putLastRoutingChoice(str);
        return -1;
    }

    private static boolean a(POI poi) {
        if (poi == null) {
            return false;
        }
        HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
        if (poiExtra != null && poiExtra.size() != 0 && poiExtra.containsKey("poi_type") && "3".equals(poiExtra.get("poi_type"))) {
            return true;
        }
        return false;
    }

    private static boolean a(List<POI> list) {
        if (list != null && list.size() > 0) {
            for (POI a : list) {
                if (a(a)) {
                    return true;
                }
            }
        }
        return false;
    }
}
