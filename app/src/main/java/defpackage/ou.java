package defpackage;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.request.RouteCarParamUrlWrapper;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.pos.LocHistoryPoint;
import com.autonavi.jni.ae.pos.LocHistoryTrace;
import com.autonavi.jni.ae.pos.LocManager;
import com.autonavi.jni.ae.pos.LocMapPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* renamed from: ou reason: default package */
/* compiled from: DriveRouteRequestParamUrlBuilder */
public final class ou {
    public static final String a = AMapAppGlobal.getApplication().getResources().getString(R.string.my_location);

    public static RouteCarParamUrlWrapper a(int i, ta taVar) {
        ArrayList<GeoPoint> arrayList;
        ArrayList<GeoPoint> arrayList2;
        Iterator<POI> it;
        ArrayList<GeoPoint> arrayList3;
        ArrayList<GeoPoint> arrayList4;
        String str;
        int i2 = i;
        ta taVar2 = taVar;
        POI poi = taVar2.a;
        POI poi2 = taVar2.b;
        RouteCarParamUrlWrapper routeCarParamUrlWrapper = new RouteCarParamUrlWrapper();
        routeCarParamUrlWrapper.refresh = taVar2.y;
        routeCarParamUrlWrapper.playstyle = String.valueOf(DriveSpUtil.getInt(AMapAppGlobal.getApplication().getApplicationContext(), DriveSpUtil.BROADCAST_MODE, 2));
        routeCarParamUrlWrapper.soundtype = rp.b();
        routeCarParamUrlWrapper.end_name = "";
        if (poi2 != null) {
            if (!TextUtils.isEmpty(poi2.getName())) {
                routeCarParamUrlWrapper.end_name = poi2.getName();
            }
            String endPoiExtension = poi2.getEndPoiExtension();
            if (TextUtils.isEmpty(endPoiExtension)) {
                endPoiExtension = "0";
            }
            routeCarParamUrlWrapper.end_poi_extension = endPoiExtension;
            if (DriveUtil.isLegalPoiId(poi2.getId())) {
                routeCarParamUrlWrapper.end_poiid = poi2.getId();
            }
            routeCarParamUrlWrapper.end_typecode = poi2.getType();
            routeCarParamUrlWrapper.end_types = String.valueOf(DriveUtil.genPointType(poi2));
            arrayList = poi2.getEntranceList();
        } else {
            arrayList = null;
        }
        if (DriveUtil.isLegalPoiId(poi.getId())) {
            routeCarParamUrlWrapper.start_poiid = poi.getId();
        }
        routeCarParamUrlWrapper.start_typecode = poi.getType();
        routeCarParamUrlWrapper.start_types = String.valueOf(DriveUtil.genPointType(poi));
        ArrayList<GeoPoint> exitList = poi.getExitList();
        if (LocationInstrument.getInstance().getLocInfo() != null) {
            taVar2.j = (float) LocationInstrument.getInstance().getLocInfo().courseAcc;
        }
        boolean z = taVar2.h;
        int i3 = 0;
        if (!poi.getName().equals("我的位置") || !z) {
            arrayList2 = poi.getExitList();
        } else {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation != null) {
                Bundle extras = latestLocation.getExtras();
                if (extras != null) {
                    LocMapPoint locMapPoint = (LocMapPoint) extras.getSerializable(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_POS);
                    arrayList4 = exitList;
                    taVar2.i = (float) extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_COURSE, -1.0d);
                    poi.setPoint(new GeoPoint((((double) locMapPoint.lon) / 1000000.0d) / 3.6d, (((double) locMapPoint.lat) / 1000000.0d) / 3.6d));
                    routeCarParamUrlWrapper.sloc_speed = (double) latestLocation.getSpeed();
                    routeCarParamUrlWrapper.angle_type = String.valueOf(extras.getInt(LocationInstrument.LOCATION_EXTRAS_KEY_COURSE_TYPE, -1));
                    routeCarParamUrlWrapper.angle_gps = String.valueOf(extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURSE, -1.0d));
                    routeCarParamUrlWrapper.angle_comp = String.valueOf(extras.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_COMPASS_COURSE, -1.0d));
                    routeCarParamUrlWrapper.angle_radius = String.valueOf(extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ERROR_DIST, -1.0f));
                    routeCarParamUrlWrapper.angle_sigtype = String.valueOf(extras.getInt(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_POS_TYPE, 0));
                    routeCarParamUrlWrapper.gps_cre = String.valueOf(extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURE_ACC, 0.0f));
                    routeCarParamUrlWrapper.angle_fittingdir = String.valueOf(extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE, -1.0f));
                    routeCarParamUrlWrapper.fitting_cre = String.valueOf(extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE_ACC, 0.0f));
                    routeCarParamUrlWrapper.angle_matchingdir = String.valueOf(extras.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ROAD_COURSE, -1.0f));
                } else {
                    arrayList4 = exitList;
                }
                routeCarParamUrlWrapper.sloc_precision = String.valueOf(latestLocation.getAccuracy());
            } else {
                arrayList4 = exitList;
            }
            LocHistoryTrace locHistoryTrace = new LocHistoryTrace();
            LocManager.getHistoryTrace(0, locHistoryTrace);
            LocHistoryPoint[] locHistoryPointArr = locHistoryTrace.historyPosBuffer;
            if (locHistoryPointArr == null || locHistoryPointArr.length == 0) {
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i4 = 0; i4 < locHistoryPointArr.length - 1; i4++) {
                    sb.append(a(locHistoryPointArr[i4]));
                    if (i4 != locHistoryPointArr.length - 2) {
                        sb.append(MergeUtil.SEPARATOR_KV);
                    }
                }
                StringBuilder sb2 = new StringBuilder("buildHistoryPoints=");
                sb2.append(sb.toString());
                AMapLog.d("ParamUrlBuilder", sb2.toString());
                str = sb.toString();
            }
            routeCarParamUrlWrapper.history_points = str;
            arrayList2 = arrayList4;
        }
        if (rq.a()) {
            routeCarParamUrlWrapper.sigshelter = "0";
        }
        routeCarParamUrlWrapper.route_mode = "1";
        if (poi2.getName().equals("我的位置") && z) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (latestPosition != null) {
                poi2.setPoint(new GeoPoint(DriveUtil.getDecimal(latestPosition.getLongitude()), DriveUtil.getDecimal(latestPosition.getLatitude())));
            }
        }
        if (arrayList2 == null || arrayList2.size() <= 0) {
            routeCarParamUrlWrapper.fromX = String.valueOf(poi.getPoint().getLongitude());
            routeCarParamUrlWrapper.fromY = String.valueOf(poi.getPoint().getLatitude());
        } else {
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            int i5 = 0;
            while (i5 < arrayList2.size()) {
                GeoPoint geoPoint = arrayList2.get(i5);
                if (geoPoint != null) {
                    arrayList3 = arrayList2;
                    DPoint a2 = cfg.a((long) geoPoint.x, (long) geoPoint.y);
                    if (sb3.length() > 0) {
                        sb3.append(MergeUtil.SEPARATOR_KV);
                        sb3.append(a2.x);
                        sb4.append(MergeUtil.SEPARATOR_KV);
                        sb4.append(a2.y);
                    } else {
                        sb3.append(a2.x);
                        sb4.append(a2.y);
                    }
                } else {
                    arrayList3 = arrayList2;
                }
                i5++;
                arrayList2 = arrayList3;
            }
            routeCarParamUrlWrapper.fromX = sb3.toString();
            routeCarParamUrlWrapper.fromY = sb4.toString();
        }
        int i6 = Integer.MIN_VALUE;
        if (poi2.getPoiExtra() != null && poi2.getPoiExtra().containsKey("build_type")) {
            i6 = ((Integer) poi2.getPoiExtra().get("build_type")).intValue();
        }
        if (i6 == 0 && poi2.getPoiExtra().containsKey("build_type_train_station_entrance_exit_poies")) {
            List<ISearchPoiData> list = (List) poi2.getPoiExtra().get("build_type_train_station_entrance_exit_poies");
            POI poi3 = (POI) poi2.getPoiExtra().get("main_poi");
            StringBuilder sb5 = new StringBuilder();
            StringBuilder sb6 = new StringBuilder();
            GeoPoint point = poi3 != null ? poi3.getPoint() : null;
            if (a(point)) {
                sb5.append(point.getLongitude());
                sb6.append(point.getLatitude());
            }
            if (list != null) {
                for (ISearchPoiData iSearchPoiData : list) {
                    if (iSearchPoiData != null) {
                        ArrayList<GeoPoint> entranceList = iSearchPoiData.getEntranceList();
                        if (entranceList == null || entranceList.size() <= 0) {
                            GeoPoint point2 = iSearchPoiData.getPoint();
                            if (a(point2)) {
                                sb5.append(MergeUtil.SEPARATOR_KV);
                                sb5.append(point2.getLongitude());
                                sb6.append(MergeUtil.SEPARATOR_KV);
                                sb6.append(point2.getLatitude());
                            }
                        } else if (a(entranceList.get(0))) {
                            sb5.append(MergeUtil.SEPARATOR_KV);
                            sb5.append(entranceList.get(0).getLongitude());
                            sb6.append(MergeUtil.SEPARATOR_KV);
                            sb6.append(entranceList.get(0).getLatitude());
                        } else {
                            GeoPoint point3 = iSearchPoiData.getPoint();
                            if (a(point3)) {
                                sb5.append(MergeUtil.SEPARATOR_KV);
                                sb5.append(point3.getLongitude());
                                sb6.append(MergeUtil.SEPARATOR_KV);
                                sb6.append(point3.getLatitude());
                            }
                        }
                    }
                }
            }
            routeCarParamUrlWrapper.toX = sb5.toString();
            routeCarParamUrlWrapper.toY = sb6.toString();
        } else if (arrayList == null || arrayList.size() <= 0) {
            routeCarParamUrlWrapper.toX = String.valueOf(poi2.getPoint().getLongitude());
            routeCarParamUrlWrapper.toY = String.valueOf(poi2.getPoint().getLatitude());
        } else {
            StringBuilder sb7 = new StringBuilder();
            StringBuilder sb8 = new StringBuilder();
            sb7.append(poi2.getPoint().getLongitude());
            sb8.append(poi2.getPoint().getLatitude());
            for (int i7 = 0; i7 < arrayList.size(); i7++) {
                GeoPoint geoPoint2 = arrayList.get(i7);
                if (a(geoPoint2)) {
                    if (sb7.length() > 0) {
                        sb7.append(MergeUtil.SEPARATOR_KV);
                        sb7.append(geoPoint2.getLongitude());
                        sb8.append(MergeUtil.SEPARATOR_KV);
                        sb8.append(geoPoint2.getLatitude());
                    } else {
                        sb7.append(geoPoint2.getLongitude());
                        sb8.append(geoPoint2.getLatitude());
                    }
                }
            }
            routeCarParamUrlWrapper.toX = sb7.toString();
            routeCarParamUrlWrapper.toY = sb8.toString();
        }
        List<POI> list2 = taVar2.c;
        if (list2 != null && list2.size() > 0) {
            StringBuilder sb9 = new StringBuilder();
            StringBuilder sb10 = new StringBuilder();
            StringBuilder sb11 = new StringBuilder();
            StringBuilder sb12 = new StringBuilder();
            StringBuilder sb13 = new StringBuilder();
            Iterator<POI> it2 = list2.iterator();
            while (it2.hasNext()) {
                POI next = it2.next();
                if (next != null) {
                    if (next.getName().equals("我的位置") && z) {
                        GeoPoint latestPosition2 = LocationInstrument.getInstance().getLatestPosition(5);
                        if (latestPosition2 != null) {
                            next.setPoint(latestPosition2);
                        }
                    }
                    if (next.getEntranceList() == null || next.getEntranceList().size() <= 0) {
                        it = it2;
                        sb9.append(next.getPoint().getLongitude());
                        sb9.append(",");
                        sb9.append(next.getPoint().getLatitude());
                    } else {
                        it = it2;
                        double latitude = next.getEntranceList().get(i3).getLatitude();
                        double longitude = next.getEntranceList().get(i3).getLongitude();
                        if (longitude > 0.0d && latitude > 0.0d) {
                            sb9.append(longitude);
                            sb9.append(",");
                            sb9.append(latitude);
                        }
                    }
                    if (DriveUtil.isLegalPoiId(next.getId())) {
                        sb10.append(next.getId());
                    }
                    sb11.append(DriveUtil.genPointType(next));
                    sb13.append(next.getName());
                    sb13.append(MergeUtil.SEPARATOR_KV);
                    sb12.append(next.getType());
                    sb12.append(MergeUtil.SEPARATOR_KV);
                    sb9.append(MergeUtil.SEPARATOR_KV);
                    sb10.append(MergeUtil.SEPARATOR_KV);
                    sb11.append(MergeUtil.SEPARATOR_KV);
                } else {
                    it = it2;
                }
                it2 = it;
                i3 = 0;
            }
            routeCarParamUrlWrapper.viapoints = sb9.toString();
            routeCarParamUrlWrapper.viapoint_poiids = sb10.toString();
            routeCarParamUrlWrapper.viapoint_types = sb11.toString();
            routeCarParamUrlWrapper.via_typecodes = sb12.toString();
            routeCarParamUrlWrapper.via_names = sb13.toString();
        }
        String str2 = taVar2.e;
        if (!TextUtils.isEmpty(str2)) {
            if (str2.indexOf(MergeUtil.SEPARATOR_KV) == 0) {
                str2 = str2.substring(1);
            }
            routeCarParamUrlWrapper.policy2 = str2;
        } else {
            routeCarParamUrlWrapper.policy2 = "1";
            taVar2.e = "1";
        }
        routeCarParamUrlWrapper.output = "bin";
        NaviManager.a();
        routeCarParamUrlWrapper.sdk_version = NaviManager.h();
        NaviManager.a();
        routeCarParamUrlWrapper.route_version = NaviManager.i();
        StringBuilder sb14 = new StringBuilder("urlWrapper.sdk_version:");
        sb14.append(routeCarParamUrlWrapper.sdk_version);
        sb14.append(" urlWrapper.route_version:");
        sb14.append(routeCarParamUrlWrapper.route_version);
        DriveUtil.addCarRouteLog(sb14.toString());
        routeCarParamUrlWrapper.v_type = taVar2.D;
        routeCarParamUrlWrapper.contentoptions = DriveUtil.getContentOptions(taVar2.D);
        routeCarParamUrlWrapper.v_height = taVar2.l;
        routeCarParamUrlWrapper.v_load = taVar2.m;
        routeCarParamUrlWrapper.v_weight = taVar2.n;
        routeCarParamUrlWrapper.v_width = taVar2.o;
        routeCarParamUrlWrapper.v_length = taVar2.p;
        routeCarParamUrlWrapper.v_size = taVar2.q;
        routeCarParamUrlWrapper.v_axis = taVar2.r;
        if (DriveUtil.getCarTypeByVtype(taVar2.D) == 1) {
            routeCarParamUrlWrapper.carplate = DriveUtil.getTruckCarPlateNumber();
            routeCarParamUrlWrapper.use_truck_engine = 1;
        } else if (DriveUtil.getCarTypeByVtype(taVar2.D) == 11) {
            routeCarParamUrlWrapper.carplate = DriveUtil.getMotorPlateNum();
            routeCarParamUrlWrapper.cc = DriveUtil.getMotorCC(DriveUtil.getMotorInfo());
            routeCarParamUrlWrapper.use_truck_engine = 2;
        } else {
            if (!TextUtils.equals(taVar2.d, CalcRouteScene.invoker_taxi)) {
                routeCarParamUrlWrapper.carplate = DriveUtil.getCarPlateNumber();
            }
            routeCarParamUrlWrapper.use_truck_engine = 0;
        }
        taVar2.k = routeCarParamUrlWrapper.carplate;
        ls lsVar = lt.a().c;
        if (!(lsVar.A != null ? lsVar.A.booleanValue() : false) || i2 != 0) {
            taVar2.E = false;
        } else {
            GeoPoint point4 = poi.getPoint();
            GeoPoint point5 = poi2.getPoint();
            if (!(point4 == null || point5 == null)) {
                Location location = new Location(WidgetType.GPS);
                Location location2 = new Location(WidgetType.GPS);
                location.setLongitude(point4.getLongitude());
                location.setLatitude(point4.getLatitude());
                location2.setLongitude(point5.getLongitude());
                location2.setLatitude(point5.getLatitude());
                if (taVar2.c != null && !taVar2.c.isEmpty()) {
                    taVar2.E = false;
                } else if (location.distanceTo(location2) <= 50000.0f) {
                    taVar2.E = true;
                }
            }
        }
        if (taVar2.E) {
            routeCarParamUrlWrapper.contentoptions |= 16384;
        }
        if (i2 == 1) {
            routeCarParamUrlWrapper.contentoptions |= 512;
        }
        if (!ro.a()) {
            routeCarParamUrlWrapper.contentoptions |= 128;
        } else {
            routeCarParamUrlWrapper.contentoptions &= -129;
        }
        taVar2.s = routeCarParamUrlWrapper.contentoptions;
        routeCarParamUrlWrapper.invoker = taVar2.d;
        if (taVar2.i >= 0.0f && taVar2.i < 360.0f) {
            StringBuilder sb15 = new StringBuilder();
            sb15.append(taVar2.i);
            routeCarParamUrlWrapper.angle = sb15.toString();
            StringBuilder sb16 = new StringBuilder();
            sb16.append(taVar2.j);
            routeCarParamUrlWrapper.credibility = sb16.toString();
        }
        boolean booleanValue = new MapSharePreference((String) "SharedPreferences").getBooleanValue("key_navi_3d_support", true);
        tq.a(AutoConstants.AUTO_FILE_3DCROSS, "isSupport3d = ".concat(String.valueOf(booleanValue)), "");
        routeCarParamUrlWrapper.threeD = booleanValue ? 1 : 0;
        if (!TextUtils.isEmpty(taVar2.B)) {
            routeCarParamUrlWrapper.superid = taVar2.B;
        }
        ISearchPoiData iSearchPoiData2 = (ISearchPoiData) taVar2.b.as(ISearchPoiData.class);
        String towardsAngle = iSearchPoiData2.getTowardsAngle();
        if (!TextUtils.isEmpty(towardsAngle)) {
            routeCarParamUrlWrapper.end_poi_angle = towardsAngle;
        } else {
            routeCarParamUrlWrapper.end_poi_angle = ((FavoritePOI) taVar2.b.as(FavoritePOI.class)).getTowardsAngle();
        }
        String fnona = iSearchPoiData2.getFnona();
        if (!TextUtils.isEmpty(fnona)) {
            routeCarParamUrlWrapper.end_floor = fnona;
        } else {
            routeCarParamUrlWrapper.end_floor = ((FavoritePOI) taVar2.b.as(FavoritePOI.class)).getFnona();
        }
        String parent = iSearchPoiData2.getParent();
        String childType = iSearchPoiData2.getChildType();
        if (TextUtils.isEmpty(parent) || TextUtils.isEmpty(childType)) {
            FavoritePOI favoritePOI = (FavoritePOI) taVar2.b.as(FavoritePOI.class);
            String parent2 = favoritePOI.getParent();
            String childType2 = favoritePOI.getChildType();
            if (!TextUtils.isEmpty(parent2) && !TextUtils.isEmpty(childType2)) {
                routeCarParamUrlWrapper.end_parentid = parent2;
                routeCarParamUrlWrapper.end_parentrel = childType2;
            }
        } else {
            routeCarParamUrlWrapper.end_parentid = parent;
            routeCarParamUrlWrapper.end_parentrel = childType;
        }
        if (!TextUtils.isEmpty(taVar2.C)) {
            routeCarParamUrlWrapper.frompage = taVar2.C;
        } else {
            routeCarParamUrlWrapper.frompage = "";
        }
        if (bno.a) {
            ku a3 = ku.a();
            StringBuilder sb17 = new StringBuilder("RouteCarParamUrlWrapper   ");
            sb17.append(routeCarParamUrlWrapper.toString());
            String sb18 = sb17.toString();
            AMapLog.e("NaviMonitor", sb18);
            ku.a.execute(new Runnable(sb18) {
                final /* synthetic */ String a;

                {
                    this.a = r2;
                }

                public final void run() {
                    Date date = new Date();
                    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
                    StringBuilder sb = new StringBuilder();
                    sb.append(format);
                    sb.append(Token.SEPARATOR);
                    sb.append(this.a);
                    sb.append("\n");
                    String sb2 = sb.toString();
                    String format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
                    StringBuilder sb3 = new StringBuilder("dlg_log/dlg_log_");
                    sb3.append(format2);
                    sb3.append(".txt");
                    FileUtil.saveLogToFile(sb2, sb3.toString());
                }
            });
        }
        return routeCarParamUrlWrapper;
    }

    private static String a(LocHistoryPoint locHistoryPoint) {
        if (locHistoryPoint == null || locHistoryPoint.pos == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(((float) locHistoryPoint.pos.lon) / 3600000.0f);
            sb.append(",");
            sb.append(((float) locHistoryPoint.pos.lat) / 3600000.0f);
            sb.append(",");
            sb.append(locHistoryPoint.course);
            sb.append(",");
            sb.append(locHistoryPoint.speed);
            sb.append(",");
            sb.append(locHistoryPoint.tickTime);
            sb.append(",");
            sb.append(locHistoryPoint.flag);
            StringBuilder sb2 = new StringBuilder("buildLocHistory=");
            sb2.append(sb.toString());
            AMapLog.d("ParamUrlBuilder", sb2.toString());
        } catch (Exception e) {
            DebugLog.e("ParamUrlBuilder", "error:", e);
        }
        return sb.toString();
    }

    private static boolean a(GeoPoint geoPoint) {
        return geoPoint != null && geoPoint.x > 0 && geoPoint.y > 0 && geoPoint.getLongitude() > 0.0d && geoPoint.getLatitude() > 0.0d;
    }
}
