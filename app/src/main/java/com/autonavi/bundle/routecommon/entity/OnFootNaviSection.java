package com.autonavi.bundle.routecommon.entity;

import android.content.res.Resources;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import java.io.Serializable;

public class OnFootNaviSection implements Serializable {
    private static final long serialVersionUID = -8807804800331730762L;
    public int index;
    public boolean isBusSectionStart = false;
    public int mDataLength;
    public IndoorInfo mIndoorInfo;
    public String mNaviActionStr;
    public byte mNaviAssiAction;
    public String mNaviAssiActionStr;
    public byte mNavigtionAction;
    public int mPathlength;
    public int mPointNum;
    public String mStreetName;
    public int mWalkType = 0;
    public int[] mXs;
    public int[] mYs;
    public int m_RealSegID;
    public int m_Split;

    public GeoPoint[] getPointArray() {
        if (this.mXs == null || this.mYs == null) {
            return null;
        }
        int length = this.mXs.length < this.mYs.length ? this.mXs.length : this.mYs.length;
        GeoPoint[] geoPointArr = new GeoPoint[length];
        for (int i = 0; i < length; i++) {
            geoPointArr[i] = new GeoPoint(this.mXs[i], this.mYs[i]);
        }
        return geoPointArr;
    }

    public static String getDirectionStr(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        switch (i) {
            case 0:
                return resources.getString(R.string.east);
            case 1:
                return resources.getString(R.string.northeast);
            case 2:
                return resources.getString(R.string.north);
            case 3:
                return resources.getString(R.string.northwest);
            case 4:
                return resources.getString(R.string.west);
            case 5:
                return resources.getString(R.string.southwest);
            case 6:
                return resources.getString(R.string.south);
            case 7:
                return resources.getString(R.string.southeast);
            default:
                return "";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0035, code lost:
        return com.autonavi.minimap.R.drawable.foot_turnpoint_flyover;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getOverlayMarker() {
        /*
            r2 = this;
            int r0 = r2.mWalkType
            r1 = 30
            if (r0 == r1) goto L_0x0039
            switch(r0) {
                case 3: goto L_0x0036;
                case 4: goto L_0x0033;
                case 5: goto L_0x0036;
                case 6: goto L_0x0030;
                case 7: goto L_0x002d;
                case 8: goto L_0x002a;
                case 9: goto L_0x0027;
                case 10: goto L_0x0024;
                case 11: goto L_0x0021;
                case 12: goto L_0x001e;
                default: goto L_0x0009;
            }
        L_0x0009:
            switch(r0) {
                case 14: goto L_0x0039;
                case 15: goto L_0x001b;
                case 16: goto L_0x0018;
                default: goto L_0x000c;
            }
        L_0x000c:
            switch(r0) {
                case 20: goto L_0x0015;
                case 21: goto L_0x0012;
                case 22: goto L_0x0033;
                default: goto L_0x000f;
            }
        L_0x000f:
            r0 = -999(0xfffffffffffffc19, float:NaN)
            return r0
        L_0x0012:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_incline
            return r0
        L_0x0015:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_ladder
            return r0
        L_0x0018:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_slip
            return r0
        L_0x001b:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_sightseeingbus
            return r0
        L_0x001e:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_doorway
            return r0
        L_0x0021:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_oversky
            return r0
        L_0x0024:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_cableway
            return r0
        L_0x0027:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_floor_lift
            return r0
        L_0x002a:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_floor_escalator
            return r0
        L_0x002d:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_passyard
            return r0
        L_0x0030:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_garden
            return r0
        L_0x0033:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_flyover
            return r0
        L_0x0036:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_underground
            return r0
        L_0x0039:
            int r0 = com.autonavi.minimap.R.drawable.foot_turnpoint_cruises
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommon.entity.OnFootNaviSection.getOverlayMarker():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
        r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication().getString(com.autonavi.minimap.R.string.enter);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003d, code lost:
        if (r3.mStreetName != null) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append(r0);
        r1.append(com.autonavi.amap.app.AMapAppGlobal.getApplication().getString(com.autonavi.minimap.R.string.unknown_road));
        r0 = r1.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append(r0);
        r1.append(r3.mStreetName);
        r0 = r1.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006a, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a9, code lost:
        return r0.getString(com.autonavi.minimap.R.string.take_over_street);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getSectionName() {
        /*
            r3 = this;
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.res.Resources r0 = r0.getResources()
            int r1 = r3.mWalkType
            r2 = 30
            if (r1 == r2) goto L_0x00b8
            switch(r1) {
                case 1: goto L_0x00b1;
                case 2: goto L_0x00b1;
                case 3: goto L_0x00aa;
                case 4: goto L_0x00a3;
                case 5: goto L_0x009c;
                case 6: goto L_0x0095;
                case 7: goto L_0x008e;
                case 8: goto L_0x0087;
                case 9: goto L_0x0080;
                case 10: goto L_0x0079;
                case 11: goto L_0x0072;
                case 12: goto L_0x006b;
                case 13: goto L_0x0031;
                case 14: goto L_0x00b8;
                case 15: goto L_0x002a;
                case 16: goto L_0x0023;
                default: goto L_0x0011;
            }
        L_0x0011:
            switch(r1) {
                case 20: goto L_0x001c;
                case 21: goto L_0x0015;
                case 22: goto L_0x00a3;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0031
        L_0x0015:
            int r1 = com.autonavi.minimap.R.string.slope
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x001c:
            int r1 = com.autonavi.minimap.R.string.stair
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0023:
            int r1 = com.autonavi.minimap.R.string.take_rope_way
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x002a:
            int r1 = com.autonavi.minimap.R.string.take_sightseeing_car
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0031:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r1 = com.autonavi.minimap.R.string.enter
            java.lang.String r0 = r0.getString(r1)
            java.lang.String r1 = r3.mStreetName
            if (r1 != 0) goto L_0x0059
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r2 = com.autonavi.minimap.R.string.unknown_road
            java.lang.String r0 = r0.getString(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x006a
        L_0x0059:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = r3.mStreetName
            r1.append(r0)
            java.lang.String r0 = r1.toString()
        L_0x006a:
            return r0
        L_0x006b:
            int r1 = com.autonavi.minimap.R.string.take_passway
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0072:
            int r1 = com.autonavi.minimap.R.string.take_over_passway
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0079:
            int r1 = com.autonavi.minimap.R.string.take_rope_way
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0080:
            int r1 = com.autonavi.minimap.R.string.take_elevator
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0087:
            int r1 = com.autonavi.minimap.R.string.take_staircase
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x008e:
            int r1 = com.autonavi.minimap.R.string.take_yard
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0095:
            int r1 = com.autonavi.minimap.R.string.take_park
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x009c:
            int r1 = com.autonavi.minimap.R.string.take_subway
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x00a3:
            int r1 = com.autonavi.minimap.R.string.take_over_street
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x00aa:
            int r1 = com.autonavi.minimap.R.string.take_underground
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x00b1:
            int r1 = com.autonavi.minimap.R.string.pass_crosswalk
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x00b8:
            int r1 = com.autonavi.minimap.R.string.take_boat
            java.lang.String r0 = r0.getString(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommon.entity.OnFootNaviSection.getSectionName():java.lang.String");
    }
}
