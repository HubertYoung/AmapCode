package com.autonavi.bundle.routecommon.entity;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.entity.BusPath.WalkPath;
import com.autonavi.bundle.routecommon.entity.Trip.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class BusPathSection implements Serializable {
    private static final long serialVersionUID = 9102412329334729658L;
    public BusPathSection[] alter_list;
    public int busTimeTag;
    public String bus_des;
    public String bus_id;
    public int driver_time;
    public String endPOIID;
    public String end_id;
    public String end_time;
    public int foot_time;
    public String intervalDesc;
    public IrregularTime irregulartime;
    public boolean isLoopLine;
    public boolean isNeedRequest;
    public boolean isRealTime;
    public boolean isRidePath;
    public boolean is_night;
    public byte[] mBusData;
    public int mBusType = 0;
    public String mCityCode;
    public int mDataLength;
    public int mDriverLength;
    public Emergency mEmergency;
    public String mEndName;
    public BusEta mEta;
    public String mExactSectionName;
    public int mFailTimes;
    public int mFootLength = 0;
    public boolean mHadData;
    private Object mLock = new Object();
    public int mPointNum;
    public int mRealTimeStatus;
    public String mRouteColor;
    public String mSectionName;
    public String mStartName;
    public int mStationNum;
    public Station[] mStations;
    public String mTransferTip;
    public int mTransferType;
    public int[] mXs;
    public int[] mYs;
    public String startPOIID;
    public String start_id;
    public String start_time;
    public String stationEndTime;
    public String stationId;
    public String stationStartTime;
    public SubwayPort subway_inport;
    public SubwayPort subway_outport;
    public String tmLimit;
    public int tmcT2early;
    public Vector<Trip> tripList;
    public WalkPath walk_path;

    public static class Emergency implements Serializable {
        public String esdescription;
        public String esstatus;
        public String eventTagDesc;
        public String ldescription;
        public int lineType;
        public String ssdescription;
        public String ssstatus;
    }

    public static class IrregularTime implements Serializable {
        public String holiday;
        public String normalday;
        public String workday;
    }

    public static class SubwayPort implements Serializable {
        private static final long serialVersionUID = -3365178716898535941L;
        public GeoPoint coord;
        public String description;
        public String name;
        public String subwayName;
    }

    public Vector<Trip> getTripList() {
        if (this.tripList == null) {
            this.tripList = new Vector<>();
        }
        return this.tripList;
    }

    @Nullable
    public Trip getNearRealTimeBusTrip() {
        if (this.tripList == null || this.tripList.isEmpty()) {
            return null;
        }
        Vector vector = new Vector();
        if (!TextUtils.isEmpty(this.mExactSectionName)) {
            Iterator<Trip> it = this.tripList.iterator();
            while (it.hasNext()) {
                Trip next = it.next();
                if (!TextUtils.isEmpty(next.lindName) && next.lindName.contains(this.mExactSectionName)) {
                    vector.add(next);
                }
            }
        }
        if (vector.isEmpty()) {
            return null;
        }
        Vector vector2 = new Vector();
        Iterator it2 = vector.iterator();
        while (it2.hasNext()) {
            Trip trip = (Trip) it2.next();
            if (trip.track != null) {
                vector2.add(trip);
            }
        }
        if (vector2.isEmpty()) {
            synchronized (this.mLock) {
                Collections.sort(vector, new a());
            }
            if (!vector.isEmpty()) {
                return (Trip) vector.get(0);
            }
        } else {
            synchronized (this.mLock) {
                Collections.sort(vector2, new a());
            }
            if (!vector2.isEmpty()) {
                return (Trip) vector2.get(0);
            }
        }
        return null;
    }

    public String getGetOnStationDesc() {
        StringBuilder sb = new StringBuilder();
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (BusPath.isSubway(this.mBusType)) {
            if (this.subway_inport != null) {
                sb.append(this.mStartName);
                sb.append(resources.getString(R.string.route_busfoot_subwaystation));
                sb.append(this.subway_inport.name);
            } else {
                sb.append(this.mStartName);
                sb.append(resources.getString(R.string.route_busfoot_subwaystation));
            }
        } else if (BusPath.isBus(this.mBusType)) {
            sb.append(this.mStartName);
            sb.append(resources.getString(R.string.route_busfoot_busstation));
        } else {
            sb.append(this.mStartName);
        }
        return sb.toString();
    }

    public String getGetOffStationDesc() {
        StringBuilder sb = new StringBuilder();
        if (!BusPath.isSubway(this.mBusType)) {
            sb.append(this.mEndName);
        } else if (this.subway_outport != null) {
            sb.append(this.mEndName);
            sb.append(this.subway_outport.name);
        } else {
            sb.append(this.mEndName);
        }
        return sb.toString();
    }

    public String getSubwayPortDesc() {
        StringBuilder sb = new StringBuilder();
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (!BusPath.isSubway(this.mBusType)) {
            sb.append(this.mStartName);
            sb.append(resources.getString(R.string.get_on_subway));
            sb.append("，");
            sb.append(this.mEndName);
            sb.append(resources.getString(R.string.get_off_subway));
        } else if (this.subway_inport == null || this.subway_outport == null) {
            sb.append(this.mStartName);
            sb.append(resources.getString(R.string.get_on_subway));
            sb.append("，");
            sb.append(this.mEndName);
            sb.append(resources.getString(R.string.get_off_subway));
        } else {
            sb.append(this.mStartName);
            sb.append(resources.getString(R.string.get_on_subway));
            sb.append(dealSubWayPortName(this.subway_inport.name, false));
            sb.append("，");
            sb.append(this.mEndName);
            sb.append(resources.getString(R.string.get_off_subway));
            sb.append(dealSubWayPortName(this.subway_outport.name, true));
        }
        return sb.toString();
    }

    public static String dealSubWayPortName(String str, boolean z) {
        String str2;
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!z) {
            String replace = str.replace("(", "").replace(")", "");
            if (replace.length() > 0) {
                if (resources.getString(R.string.subway_in).equals(String.valueOf(replace.charAt(replace.length() - 1)))) {
                    replace = replace.substring(0, replace.length() - 1);
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(replace);
            sb.append(resources.getString(R.string.subway_in));
            str2 = sb.toString();
        } else if (str.equals(resources.getString(R.string.exit))) {
            return "";
        } else {
            String replace2 = str.replace("(", "").replace(")", "");
            if (replace2.length() > 0) {
                if (resources.getString(R.string.subway_out).equals(String.valueOf(replace2.charAt(replace2.length() - 1)))) {
                    replace2 = replace2.substring(0, replace2.length() - 1);
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(replace2);
            sb2.append(resources.getString(R.string.subway_out));
            str2 = sb2.toString();
        }
        return str2;
    }

    public static String dealSubWayPortName(SubwayPort subwayPort, boolean z) {
        if (TextUtils.isEmpty(subwayPort.description)) {
            return dealSubWayPortName(subwayPort.name, z);
        }
        return dealSubWayPortName(subwayPort.description, z);
    }

    public String getSectionFastSimpleName() {
        if (!TextUtils.isEmpty(this.mExactSectionName)) {
            return this.mExactSectionName.replace(AMapAppGlobal.getApplication().getString(R.string.subway), "");
        }
        return this.mExactSectionName;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00a8 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getSectionDirection(boolean r6) {
        /*
            r5 = this;
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.res.Resources r0 = r0.getResources()
            java.lang.String r1 = r5.mSectionName
            java.lang.String r2 = "--"
            int r1 = r1.indexOf(r2)
            if (r1 < 0) goto L_0x008f
            java.lang.String r1 = r5.mSectionName
            java.lang.String r2 = "--"
            java.lang.String[] r1 = r1.split(r2)
            int r2 = r1.length
            r3 = 1
            if (r2 <= r3) goto L_0x008f
            r2 = r1[r3]
            r1 = r1[r3]
            int r1 = r1.length()
            int r1 = r1 - r3
            r4 = 0
            java.lang.String r1 = r2.substring(r4, r1)
            int r2 = r1.length()
            if (r2 <= 0) goto L_0x0090
            boolean r2 = r5.isLoopLine
            if (r2 == 0) goto L_0x0079
            com.autonavi.bundle.routecommon.entity.Station[] r1 = r5.mStations
            int r1 = r1.length
            if (r1 <= r3) goto L_0x005d
            com.autonavi.bundle.routecommon.entity.Station[] r1 = r5.mStations
            r1 = r1[r3]
            if (r1 == 0) goto L_0x005d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r2 = com.autonavi.minimap.R.string.direct_to
            java.lang.String r2 = r0.getString(r2)
            r1.append(r2)
            com.autonavi.bundle.routecommon.entity.Station[] r2 = r5.mStations
            r2 = r2[r3]
            java.lang.String r2 = r2.mName
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L_0x0090
        L_0x005d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r2 = com.autonavi.minimap.R.string.direct_to
            java.lang.String r2 = r0.getString(r2)
            r1.append(r2)
            com.autonavi.bundle.routecommon.entity.Station[] r2 = r5.mStations
            r2 = r2[r4]
            java.lang.String r2 = r2.mName
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            goto L_0x0090
        L_0x0079:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            int r3 = com.autonavi.minimap.R.string.direct_to
            java.lang.String r3 = r0.getString(r3)
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            goto L_0x0090
        L_0x008f:
            r1 = 0
        L_0x0090:
            if (r6 == 0) goto L_0x00a8
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r1)
            int r1 = com.autonavi.minimap.R.string.direction
            java.lang.String r0 = r0.getString(r1)
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            return r6
        L_0x00a8:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommon.entity.BusPathSection.getSectionDirection(boolean):java.lang.String");
    }

    public String getDriverTime() {
        String str;
        if (this.driver_time <= 0) {
            return null;
        }
        int i = this.driver_time / 60;
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (i >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(i / 60);
            sb.append(resources.getString(R.string.hour));
            String sb2 = sb.toString();
            int i2 = i % 60;
            if (i2 > 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(i2);
                sb3.append(resources.getString(R.string.minutes));
                str = sb3.toString();
            } else {
                str = sb2;
            }
        } else if (i == 0) {
            StringBuilder sb4 = new StringBuilder(SimpleComparison.LESS_THAN_OPERATION);
            sb4.append(resources.getString(R.string.a_minute));
            str = sb4.toString();
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(i);
            sb5.append(resources.getString(R.string.minutes));
            str = sb5.toString();
        }
        return str;
    }

    public String getIntervalDesc() {
        if (this.intervalDesc == null) {
            return "";
        }
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (!this.intervalDesc.contains(resources.getString(R.string.approx))) {
            return this.intervalDesc;
        }
        int indexOf = this.intervalDesc.indexOf(resources.getString(R.string.approx));
        String str = this.intervalDesc;
        return str.substring(indexOf, str.length());
    }

    public String getBusId() {
        return this.isRidePath ? ShowRouteActionProcessor.SEARCH_TYPE_RIDE : this.bus_id;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001b  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getColor() {
        /*
            r2 = this;
            java.lang.String r0 = r2.mRouteColor
            if (r0 == 0) goto L_0x0018
            java.lang.String r0 = r2.mRouteColor
            int r0 = r0.length()
            r1 = 1
            if (r0 <= r1) goto L_0x0018
            java.lang.String r0 = r2.mRouteColor     // Catch:{ Exception -> 0x0014 }
            int r0 = android.graphics.Color.parseColor(r0)     // Catch:{ Exception -> 0x0014 }
            goto L_0x0019
        L_0x0014:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0018:
            r0 = 0
        L_0x0019:
            if (r0 != 0) goto L_0x001e
            r0 = -12409345(0xffffffffff42a5ff, float:-2.5873213E38)
        L_0x001e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommon.entity.BusPathSection.getColor():int");
    }
}
