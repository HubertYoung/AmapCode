package defpackage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteTipBean;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.AlterBus;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.BusSegment;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.Eta;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusInfo;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRouteResponse;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* renamed from: ayt reason: default package */
/* compiled from: RouteCommuteDataUtil */
public final class ayt {
    public static int a(BusPath busPath) {
        if (!(busPath == null || busPath.segmentlist == null || busPath.segmentlist.size() <= 0)) {
            BusSegment busSegment = busPath.segmentlist.get(0);
            if (busSegment != null && !TextUtils.isEmpty(busSegment.color)) {
                String str = busSegment.color;
                if (!str.startsWith(MetaRecord.LOG_SEPARATOR) && str.length() == 6) {
                    try {
                        return Color.parseColor("#FF".concat(String.valueOf(str)));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return -11239681;
    }

    public static boolean b(BusPath busPath) {
        int i;
        if (busPath != null) {
            ArrayList<BusSegment> arrayList = busPath.segmentlist;
            if (arrayList != null && arrayList.size() > 0) {
                BusSegment busSegment = arrayList.get(0);
                if (busSegment != null && !TextUtils.isEmpty(busSegment.bustype)) {
                    try {
                        i = Integer.parseInt(busSegment.bustype);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (i != 2 || i == 3 || i == 10) {
                        return true;
                    }
                    return false;
                }
            }
        }
        i = -1;
        if (i != 2) {
        }
        return true;
    }

    public static GeoPoint c(BusPath busPath) {
        ArrayList<BusSegment> arrayList = busPath.segmentlist;
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        return a(arrayList.get(0));
    }

    private static GeoPoint a(BusSegment busSegment) {
        if (busSegment == null) {
            return null;
        }
        if (!TextUtils.isEmpty(busSegment.drivercoord)) {
            String[] split = busSegment.drivercoord.split(",");
            if (split.length >= 2) {
                return new GeoPoint(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
            }
        } else {
            Eta eta = busSegment.eta;
            if (eta == null || TextUtils.isEmpty(eta.etaCoords)) {
                return null;
            }
            String[] split2 = eta.etaCoords.split(",");
            if (split2.length >= 2) {
                return new GeoPoint(Double.parseDouble(split2[0]), Double.parseDouble(split2[1]));
            }
        }
        return null;
    }

    public static Set<String> d(BusPath busPath) {
        HashSet hashSet = new HashSet();
        ArrayList<BusSegment> arrayList = busPath.segmentlist;
        if (arrayList != null) {
            int i = ayi.a().b;
            Iterator<BusSegment> it = arrayList.iterator();
            while (it.hasNext()) {
                BusSegment next = it.next();
                if (next != null) {
                    if (next.alterlist == null || i < 0 || i >= next.alterlist.size()) {
                        hashSet.add(next.startid);
                    } else {
                        hashSet.add(next.alterlist.get(i).startid);
                    }
                }
            }
        }
        return hashSet;
    }

    public static RealTimeBusLineInfo a(Set<String> set, ArrayList<RealTimeBusLineInfo> arrayList) {
        if (!(set == null || set.size() == 0 || arrayList == null)) {
            for (int i = 0; i < arrayList.size(); i++) {
                RealTimeBusLineInfo realTimeBusLineInfo = arrayList.get(i);
                if (realTimeBusLineInfo != null && set.contains(realTimeBusLineInfo.station)) {
                    return realTimeBusLineInfo;
                }
            }
        }
        return null;
    }

    public static BusCommuteTipBean a(ayi ayi, boolean z, boolean z2, boolean z3) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        BusCommuteTipBean busCommuteTipBean = new BusCommuteTipBean();
        AlterBus alterBus = null;
        if (ayi == null) {
            azb.a("RouteCommuteDataUtil", "BusCommuteResult is null");
            return null;
        }
        BusRouteResponse busRouteResponse = ayi.c;
        if (busRouteResponse == null) {
            azb.a("RouteCommuteDataUtil", "BusRouteResponse is null");
            return null;
        }
        int i = ayi.a;
        ArrayList<BusPath> arrayList = busRouteResponse.buslist;
        if (arrayList == null || arrayList.isEmpty() || arrayList.size() <= i) {
            azb.a("RouteCommuteDataUtil", "buslist may be null");
            return null;
        }
        BusPath busPath = arrayList.get(i);
        if (busPath == null) {
            azb.a("RouteCommuteDataUtil", "busPath is null");
            return null;
        }
        ArrayList<BusSegment> arrayList2 = busPath.segmentlist;
        if (arrayList2 == null || arrayList2.isEmpty()) {
            azb.a("RouteCommuteDataUtil", "segments is null");
            return null;
        }
        busCommuteTipBean.foucsIndex = i;
        busCommuteTipBean.isGoHome = z2;
        busCommuteTipBean.isSettingUser = z;
        boolean z4 = false;
        busCommuteTipBean.isSingleLine = arrayList2.size() == 1;
        busCommuteTipBean.currentLocPoint = LocationInstrument.getInstance().getLatestPosition(5);
        try {
            busCommuteTipBean.time_tag = Integer.parseInt(busPath.time_tag);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        busCommuteTipBean.stopEventList = busRouteResponse.stopEventList;
        CommuteControlBean commuteControlBean = a.a.a;
        boolean z5 = commuteControlBean != null && commuteControlBean.isOperateEventEnable(ShowRouteActionProcessor.SEARCH_TYPE_BUS);
        a busOperationOptions = commuteControlBean != null ? commuteControlBean.getBusOperationOptions() : null;
        if (busCommuteTipBean.stopEventList != null && busCommuteTipBean.stopEventList.size() > 0) {
            busCommuteTipBean.iconId = R.drawable.route_commute_tip_ic_emergency;
            if (z5) {
                if (busOperationOptions == null) {
                    str6 = null;
                } else {
                    str6 = busOperationOptions.a;
                }
                busCommuteTipBean.iconUrl = str6;
            }
        } else if (z2) {
            busCommuteTipBean.iconId = R.drawable.route_commute_tip_ic_home;
            if (z5) {
                if (busOperationOptions == null) {
                    str5 = null;
                } else {
                    str5 = busOperationOptions.c;
                }
                busCommuteTipBean.iconUrl = str5;
            }
        } else {
            busCommuteTipBean.iconId = R.drawable.route_commute_tip_ic_company;
            if (z5) {
                if (busOperationOptions == null) {
                    str4 = null;
                } else {
                    str4 = busOperationOptions.d;
                }
                busCommuteTipBean.iconUrl = str4;
            }
        }
        if (z) {
            busCommuteTipBean.simIconId = z2 ? R.drawable.route_commute_tip_tiny_bubble_home : R.drawable.route_commute_tip_tiny_bubble_company;
        } else {
            busCommuteTipBean.simIconId = R.drawable.route_commute_tip_ic_point_c;
        }
        int parseInt = Integer.parseInt(busPath.expensetime);
        if (!z) {
            StringBuilder sb = new StringBuilder("去");
            sb.append(ayi.g.getName());
            busCommuteTipBean.destinationStr = sb.toString();
            StringBuilder sb2 = new StringBuilder("约");
            sb2.append(a(parseInt));
            busCommuteTipBean.etaStr = sb2.toString();
            busCommuteTipBean.hasIcon = false;
        } else {
            busCommuteTipBean.destinationStr = z2 ? "回家" : "去公司";
            if (z2 || z3) {
                StringBuilder sb3 = new StringBuilder("约");
                sb3.append(a(parseInt));
                busCommuteTipBean.etaStr = sb3.toString();
            } else {
                StringBuilder sb4 = new StringBuilder("约");
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(System.currentTimeMillis());
                int i2 = instance.get(11);
                int i3 = instance.get(12);
                int round = Math.round(((float) parseInt) / 60.0f);
                int i4 = round / 60;
                int i5 = i3 + (round % 60);
                int i6 = i5 / 60;
                int i7 = i5 % 60;
                int i8 = ((i2 + i4) + i6) % 24;
                if (i8 < 10) {
                    str2 = "0".concat(String.valueOf(i8));
                } else {
                    str2 = String.valueOf(i8);
                }
                if (i7 < 10) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str2);
                    sb5.append(":0");
                    sb5.append(i7);
                    str3 = sb5.toString();
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str2);
                    sb6.append(":");
                    sb6.append(i7);
                    str3 = sb6.toString();
                }
                sb4.append(str3);
                sb4.append("到达");
                busCommuteTipBean.etaStr = sb4.toString();
            }
            busCommuteTipBean.hasIcon = true;
        }
        if (busCommuteTipBean.time_tag != 0) {
            busCommuteTipBean.busStatus = busPath.time_tag_des;
        }
        int size = arrayList2.size() - 1;
        if (busCommuteTipBean.isSingleLine) {
            str = "直达";
        } else {
            StringBuilder sb7 = new StringBuilder("换乘");
            sb7.append(size);
            sb7.append("次");
            str = sb7.toString();
        }
        busCommuteTipBean.transferInfo = str;
        BusSegment busSegment = arrayList2.get(0);
        if (busSegment == null) {
            return null;
        }
        busCommuteTipBean.firstSegmentBusType = Integer.parseInt(busSegment.bustype);
        int i9 = ayi.b;
        if (i9 != -1) {
            alterBus = busSegment.alterlist.get(i9);
        }
        if (alterBus != null) {
            busCommuteTipBean.isRealtime = TextUtils.equals("1", alterBus.realtime);
            busCommuteTipBean.lineName = alterBus.bus_key_name;
        } else {
            busCommuteTipBean.isRealtime = TextUtils.equals("1", busSegment.realtime);
            busCommuteTipBean.lineName = busSegment.bus_key_name;
        }
        String str7 = alterBus == null ? busSegment.color : alterBus.color;
        if (busCommuteTipBean.firstSegmentBusType == 2 || busCommuteTipBean.firstSegmentBusType == 3 || busCommuteTipBean.firstSegmentBusType == 10 || busCommuteTipBean.firstSegmentBusType == 12) {
            z4 = true;
        }
        if (z4 && !TextUtils.isEmpty(str7)) {
            if (!busSegment.color.startsWith(MetaRecord.LOG_SEPARATOR)) {
                busCommuteTipBean.customLineColor = MetaRecord.LOG_SEPARATOR.concat(String.valueOf(str7));
            } else {
                busCommuteTipBean.customLineColor = str7;
            }
        }
        BusRealTimeResponse busRealTimeResponse = ayi.e;
        if (busRealTimeResponse != null && busCommuteTipBean.isRealtime && (busCommuteTipBean.time_tag == 0 || busCommuteTipBean.time_tag == 1)) {
            ArrayList<RealTimeBusLineInfo> arrayList3 = busRealTimeResponse.buses;
            if (arrayList3 == null || arrayList3.isEmpty()) {
                return busCommuteTipBean;
            }
            RealTimeBusLineInfo a = a(d(busPath), arrayList3);
            if (a == null) {
                return busCommuteTipBean;
            }
            busCommuteTipBean.realtimeStr = a(a);
            String string = AMapPageUtil.getAppContext().getResources().getString(R.string.bus_commute_rt_waiting_msg);
            String str8 = a.sub_status;
            if (TextUtils.equals("201", str8) || TextUtils.equals(UploadConstants.STATUS_TASK_BY_CONFIG, str8) || TextUtils.equals(UploadConstants.STATUS_NET_NOT_MATCH, str8)) {
                busCommuteTipBean.realtimeColorId = R.color.f_c_16;
            } else if (TextUtils.equals(string, busCommuteTipBean.realtimeStr)) {
                busCommuteTipBean.realtimeColorId = R.color.f_c_12;
            } else {
                busCommuteTipBean.realtimeColorId = R.color.f_c_16;
            }
        }
        return busCommuteTipBean;
    }

    private static String a(int i) {
        int round = Math.round(((float) i) / 60.0f);
        if (round >= 60) {
            int i2 = round % 60;
            int i3 = round / 60;
            if (i2 == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(i3);
                sb.append("小时");
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i3);
            sb2.append("小时");
            sb2.append(i2);
            sb2.append("分钟");
            return sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(round);
        sb3.append("分钟");
        return sb3.toString();
    }

    @SuppressLint({"DefaultLocale"})
    public static String a(RealTimeBusLineInfo realTimeBusLineInfo) {
        int i;
        int i2;
        if (realTimeBusLineInfo == null) {
            return "";
        }
        String str = realTimeBusLineInfo.sub_status;
        if (TextUtils.equals("201", str) || TextUtils.equals(UploadConstants.STATUS_TASK_BY_CONFIG, str) || TextUtils.equals(UploadConstants.STATUS_NET_NOT_MATCH, str) || TextUtils.equals("001", str) || TextUtils.equals("200", str)) {
            return realTimeBusLineInfo.sub_text;
        }
        if (realTimeBusLineInfo.trip == null || realTimeBusLineInfo.trip.size() == 0) {
            return "";
        }
        RealTimeBusInfo realTimeBusInfo = realTimeBusLineInfo.trip.get(0);
        if (realTimeBusInfo != null) {
            try {
                i = Integer.parseInt(realTimeBusInfo.station_left);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                i = -1;
            }
            try {
                i2 = Integer.parseInt(realTimeBusInfo.arrival);
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                i2 = -1;
            }
            if (i == 0 && i2 == 0) {
                return "车已到达";
            }
            if (i2 < 60 && i2 > 0 && i >= 0 && i <= 1) {
                return "即将到站";
            }
            if (i2 >= 60 && i2 < 1800 && i >= 0) {
                int i3 = i + 1;
                int i4 = i2 / 60;
                int i5 = i4 / 60;
                if (i5 > 0) {
                    i4 %= 60;
                }
                if (i2 % 60 >= 30) {
                    i4++;
                }
                if (i5 > 0) {
                    return String.format("%d小时%d分钟·%d站", new Object[]{Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)});
                }
                return String.format("%d分钟·%d站", new Object[]{Integer.valueOf(i4), Integer.valueOf(i3)});
            } else if (i2 >= 1800) {
                return AMapPageUtil.getAppContext().getResources().getString(R.string.bus_commute_rt_waiting_msg);
            }
        }
        return "";
    }

    public static boolean a() {
        return a(LocationInstrument.getInstance().getLatestPosition(), ayi.a());
    }

    private static boolean a(GeoPoint geoPoint, ayi ayi) {
        if (geoPoint == null || ayi == null) {
            return false;
        }
        BusRouteResponse busRouteResponse = ayi.c;
        if (busRouteResponse == null) {
            return false;
        }
        ArrayList<BusPath> arrayList = busRouteResponse.buslist;
        if (arrayList == null || arrayList.isEmpty()) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<BusPath> it = arrayList.iterator();
        while (it.hasNext()) {
            BusPath next = it.next();
            if (next != null) {
                Iterator<BusSegment> it2 = next.segmentlist.iterator();
                while (it2.hasNext()) {
                    BusSegment next2 = it2.next();
                    if (next2 != null) {
                        if (!TextUtils.isEmpty(next2.drivercoord)) {
                            sb.append(next2.drivercoord);
                            sb.append(",");
                        } else {
                            Eta eta = next2.eta;
                            if (eta != null) {
                                sb.append(eta.etaCoords);
                                sb.append(",");
                            }
                        }
                    }
                }
            }
        }
        String str = null;
        if (sb.length() > 0) {
            str = sb.substring(0, sb.length() - 1);
        }
        return ayv.a(new ayx(geoPoint.getLongitude(), geoPoint.getLatitude()), str);
    }
}
