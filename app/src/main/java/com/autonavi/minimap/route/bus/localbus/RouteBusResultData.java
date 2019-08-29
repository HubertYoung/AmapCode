package com.autonavi.minimap.route.bus.localbus;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.routecommon.entity.BusEta;
import com.autonavi.bundle.routecommon.entity.BusEtaLink;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPath.BusDisplayObj;
import com.autonavi.bundle.routecommon.entity.BusPath.WalkPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.BusPathSection.Emergency;
import com.autonavi.bundle.routecommon.entity.BusPathSection.IrregularTime;
import com.autonavi.bundle.routecommon.entity.BusPathSection.SubwayPort;
import com.autonavi.bundle.routecommon.entity.BusPaths;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.ExTrainPath.AlterTrain;
import com.autonavi.bundle.routecommon.entity.ExTrainPath.Price;
import com.autonavi.bundle.routecommon.entity.ExTrainPath.Station;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.bundle.routecommon.entity.ExtBusPath.ExBusPathSegment;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.bundle.routecommon.entity.OnFootNaviSection;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.route.bus.model.ExTaxiPath;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"SimpleDateFormat"})
public class RouteBusResultData implements IBusRouteResult {
    private static final long serialVersionUID = -1974708667958876829L;
    private byte[] basedata;
    private int mAlternative = 0;
    private BusPaths mBusPathsResult;
    private ArrayList<ExtBusPath> mExtBusPathList;
    private int mFocusBusPathIndex = 0;
    private int mFocusStationIndex = 0;
    private POI mFromPoi = null;
    private boolean mHasRealTimeBus = false;
    private boolean mIsExtBusResult = false;
    private boolean mIsRidePath;
    private String mKey;
    private String mMethod_;
    private long mReqTimeInMillis = 0;
    private POI mToPoi = null;
    private POI share_from_poi = null;
    private POI share_to_poi = null;

    public ArrayList<POI> getMidPOIs() {
        return null;
    }

    public ArrayList<POI> getShareMidPOIs() {
        return null;
    }

    public boolean needSaveHistory() {
        return false;
    }

    public void restoreData() {
    }

    public void saveData() {
    }

    public void setMidPOIs(ArrayList<POI> arrayList) {
    }

    public Class<RouteBusResultData> getClassType() {
        return RouteBusResultData.class;
    }

    public void reset() {
        this.mBusPathsResult = null;
        this.mFromPoi = null;
        this.mToPoi = null;
        this.mMethod_ = "0";
        this.mFocusStationIndex = 0;
    }

    public boolean hasData() {
        if (this.mIsExtBusResult && this.mExtBusPathList != null && this.mExtBusPathList.size() > 0) {
            return true;
        }
        if (this.mFromPoi == null || this.mToPoi == null || this.mBusPathsResult == null || this.mBusPathsResult.mBusPaths == null || this.mBusPathsResult.mBusPaths.length == 0) {
            return false;
        }
        return true;
    }

    public void setBusPathsData(POI poi, POI poi2, BusPaths busPaths, String str) {
        this.mFromPoi = poi;
        this.mToPoi = poi2;
        this.mBusPathsResult = busPaths;
        this.mMethod_ = str;
    }

    public BusPaths getBusPathsResult() {
        return this.mBusPathsResult;
    }

    public int getFromAdCode() {
        if (this.mFromPoi == null || this.mFromPoi.getPoint() == null) {
            return 0;
        }
        return this.mFromPoi.getPoint().getAdCode();
    }

    public int getToAdCode() {
        if (this.mToPoi == null || this.mToPoi.getPoint() == null) {
            return 0;
        }
        return this.mToPoi.getPoint().getAdCode();
    }

    public int getFocusBusPathIndex() {
        if (this.mFocusBusPathIndex >= 0) {
            return this.mFocusBusPathIndex;
        }
        return 0;
    }

    public void setFocusBusPathIndex(int i) {
        this.mFocusBusPathIndex = i;
    }

    public int getFocusStationIndex() {
        return this.mFocusStationIndex;
    }

    public void setFocusStationIndex(int i) {
        this.mFocusStationIndex = i;
    }

    public BusPath getFocusBusPath() {
        return getBusPathWithIndex(this.mFocusBusPathIndex);
    }

    public BusPath getBusPathWithIndex(int i) {
        if (this.mFromPoi == null || this.mToPoi == null || this.mBusPathsResult == null || this.mBusPathsResult.mBusPaths == null || this.mBusPathsResult.mBusPaths.length <= 0 || !hasData() || i < 0 || i > this.mBusPathsResult.mBusPaths.length - 1) {
            return null;
        }
        return this.mBusPathsResult.mBusPaths[i];
    }

    public void setFromPOI(POI poi) {
        this.mFromPoi = poi;
    }

    public POI getFromPOI() {
        return this.mFromPoi;
    }

    public POI getShareFromPOI() {
        if (this.share_from_poi == null) {
            this.share_from_poi = this.mFromPoi.clone();
        }
        return this.share_from_poi;
    }

    public void setToPOI(POI poi) {
        this.mToPoi = poi;
    }

    public POI getToPOI() {
        return this.mToPoi;
    }

    public POI getShareToPOI() {
        if (this.share_to_poi == null) {
            this.share_to_poi = this.mToPoi.clone();
        }
        return this.share_to_poi;
    }

    public void setMethod(String str) {
        this.mMethod_ = str;
    }

    public String getMethod() {
        return this.mMethod_;
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x02b2 A[LOOP:4: B:101:0x02b2->B:104:0x02bb, LOOP_START, PHI: r1 r3 
      PHI: (r1v15 int) = (r1v14 int), (r1v16 int) binds: [B:94:0x0290, B:104:0x02bb] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r3v8 org.json.JSONObject) = (r3v7 org.json.JSONObject), (r3v9 org.json.JSONObject) binds: [B:94:0x0290, B:104:0x02bb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0292 A[LOOP:3: B:95:0x0292->B:99:0x029c, LOOP_START, PHI: r1 r3 
      PHI: (r1v17 int) = (r1v14 int), (r1v18 int) binds: [B:94:0x0290, B:99:0x029c] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r3v11 org.json.JSONObject) = (r3v7 org.json.JSONObject), (r3v12 org.json.JSONObject) binds: [B:94:0x0290, B:99:0x029c] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parse(org.json.JSONObject r14, int r15) throws org.json.JSONException, java.lang.Exception {
        /*
            r13 = this;
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = new com.autonavi.bundle.routecommon.entity.BusPaths
            r0.<init>()
            r13.mBusPathsResult = r0
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            com.autonavi.common.model.POI r1 = r13.mFromPoi
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()
            int r1 = r1.x
            r0.mstartX = r1
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            com.autonavi.common.model.POI r1 = r13.mFromPoi
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()
            int r1 = r1.y
            r0.mstartY = r1
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            com.autonavi.common.model.POI r1 = r13.mToPoi
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()
            int r1 = r1.x
            r0.mendX = r1
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            com.autonavi.common.model.POI r1 = r13.mToPoi
            com.autonavi.common.model.GeoPoint r1 = r1.getPoint()
            int r1 = r1.y
            r0.mendY = r1
            java.lang.String r0 = "res_info"
            boolean r0 = r14.has(r0)
            if (r0 == 0) goto L_0x0044
            java.lang.String r0 = "res_info"
            r14.remove(r0)
        L_0x0044:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r1 = "start_poi"
            com.autonavi.common.model.POI r2 = r13.mFromPoi     // Catch:{ Exception -> 0x0060 }
            org.json.JSONObject r2 = defpackage.bnx.b(r2)     // Catch:{ Exception -> 0x0060 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0060 }
            java.lang.String r1 = "end_poi"
            com.autonavi.common.model.POI r2 = r13.mToPoi     // Catch:{ Exception -> 0x0060 }
            org.json.JSONObject r2 = defpackage.bnx.b(r2)     // Catch:{ Exception -> 0x0060 }
            r0.put(r1, r2)     // Catch:{ Exception -> 0x0060 }
        L_0x0060:
            java.lang.String r1 = "res_info"
            r14.put(r1, r0)
            java.lang.String r0 = r14.toString()
            byte[] r0 = r0.getBytes()
            r13.setBaseData(r0)
            java.lang.String r0 = "ticketshow"
            boolean r0 = r14.has(r0)
            if (r0 == 0) goto L_0x0084
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            java.lang.String r1 = "ticketshow"
            int r1 = defpackage.axr.b(r14, r1)
            r0.mTicketShow = r1
        L_0x0084:
            java.lang.String r0 = "alternative"
            boolean r0 = r14.has(r0)
            if (r0 == 0) goto L_0x0094
            java.lang.String r0 = "alternative"
            int r0 = defpackage.axr.b(r14, r0)
            r13.mAlternative = r0
        L_0x0094:
            java.lang.String r0 = "start_desc"
            boolean r0 = r14.has(r0)
            if (r0 == 0) goto L_0x00a8
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            java.lang.String r1 = "start_desc"
            java.lang.String r1 = defpackage.axr.e(r14, r1)
            r0.mStartDes = r1
        L_0x00a8:
            java.lang.String r0 = "end_desc"
            boolean r0 = r14.has(r0)
            if (r0 == 0) goto L_0x00ba
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            java.lang.String r1 = "end_desc"
            java.lang.String r1 = defpackage.axr.e(r14, r1)
            r0.mEndDes = r1
        L_0x00ba:
            java.lang.String r0 = "bsid"
            boolean r0 = r14.has(r0)
            if (r0 == 0) goto L_0x00cc
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            java.lang.String r1 = "bsid"
            java.lang.String r1 = defpackage.axr.e(r14, r1)
            r0.mBsid = r1
        L_0x00cc:
            java.lang.String r0 = "show_input"
            boolean r0 = r14.has(r0)
            if (r0 == 0) goto L_0x0116
            java.lang.String r0 = "show_input"
            org.json.JSONObject r0 = r14.getJSONObject(r0)
            java.lang.String r1 = "type"
            boolean r1 = r0.has(r1)
            if (r1 == 0) goto L_0x00f0
            com.autonavi.bundle.routecommon.entity.BusPaths r1 = r13.mBusPathsResult
            java.lang.String r2 = "type"
            java.lang.String r2 = defpackage.axr.e(r0, r2)
            r1.mShowInput_Type = r2
        L_0x00f0:
            java.lang.String r1 = "title"
            boolean r1 = r0.has(r1)
            if (r1 == 0) goto L_0x0104
            com.autonavi.bundle.routecommon.entity.BusPaths r1 = r13.mBusPathsResult
            java.lang.String r2 = "title"
            java.lang.String r2 = defpackage.axr.e(r0, r2)
            r1.mShowInput_Title = r2
        L_0x0104:
            java.lang.String r1 = "content"
            boolean r1 = r0.has(r1)
            if (r1 == 0) goto L_0x0116
            com.autonavi.bundle.routecommon.entity.BusPaths r1 = r13.mBusPathsResult
            java.lang.String r2 = "content"
            java.lang.String r0 = defpackage.axr.e(r0, r2)
            r1.mShowInput_Content = r0
        L_0x0116:
            java.lang.String r0 = "routelist"
            boolean r0 = r14.has(r0)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0231
            java.lang.String r0 = "routelist"
            org.json.JSONArray r0 = defpackage.axr.a(r14, r0)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r0 == 0) goto L_0x0231
            int r3 = r0.length()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r3 <= 0) goto L_0x0231
            r13.mIsExtBusResult = r2     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r3.<init>()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r13.mExtBusPathList = r3     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r3 = "buslist"
            org.json.JSONArray r3 = defpackage.axr.a(r14, r3)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r4.<init>()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r3 == 0) goto L_0x015d
            int r5 = r3.length()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r6 = 0
        L_0x014b:
            if (r6 >= r5) goto L_0x015d
            org.json.JSONObject r7 = r3.getJSONObject(r6)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            com.autonavi.bundle.routecommon.entity.BusPaths r8 = r13.mBusPathsResult     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            com.autonavi.bundle.routecommon.entity.BusPath r7 = r13.parseJson2BusPath(r7, r8)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r4.add(r7)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r6 = r6 + 1
            goto L_0x014b
        L_0x015d:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r3.<init>()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r5 = "taxilist"
            boolean r5 = r14.has(r5)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r5 == 0) goto L_0x0211
            java.lang.String r5 = "taxilist"
            org.json.JSONArray r5 = defpackage.axr.a(r14, r5)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r5 == 0) goto L_0x0211
            int r6 = r5.length()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r6 <= 0) goto L_0x0211
            r6 = 0
        L_0x017b:
            int r7 = r5.length()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r6 >= r7) goto L_0x0211
            org.json.JSONObject r7 = r5.getJSONObject(r6)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            com.autonavi.minimap.route.bus.model.ExTaxiPath r8 = new com.autonavi.minimap.route.bus.model.ExTaxiPath     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.<init>()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = "length"
            int r9 = defpackage.axr.b(r7, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.length = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = "cost"
            int r9 = defpackage.axr.b(r7, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.cost = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = "drivetime"
            int r9 = defpackage.axr.b(r7, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.time = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = "startpoint"
            java.lang.String r9 = defpackage.axr.e(r7, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.startpoint = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = "endpoint"
            java.lang.String r9 = defpackage.axr.e(r7, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.endpoint = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = "sname"
            java.lang.String r9 = defpackage.axr.e(r7, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.mStartName = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = "ename"
            java.lang.String r7 = defpackage.axr.e(r7, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.mEndName = r7     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r7 = r8.startpoint     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = ","
            java.lang.String[] r7 = r7.split(r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r9 = r7.length     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r9 <= 0) goto L_0x01e7
            r9 = r7[r1]     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            double r9 = java.lang.Double.parseDouble(r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r7 = r7[r2]     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            double r11 = java.lang.Double.parseDouble(r7)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            android.graphics.Point r7 = defpackage.cfg.a(r11, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r9 = r7.x     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.startX = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r7 = r7.y     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.startY = r7     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
        L_0x01e7:
            java.lang.String r7 = r8.endpoint     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.lang.String r9 = ","
            java.lang.String[] r7 = r7.split(r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r9 = r7.length     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r9 <= 0) goto L_0x020a
            r9 = r7[r1]     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            double r9 = java.lang.Double.parseDouble(r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r7 = r7[r2]     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            double r11 = java.lang.Double.parseDouble(r7)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            android.graphics.Point r7 = defpackage.cfg.a(r11, r9)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r9 = r7.x     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.endX = r9     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r7 = r7.y     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r8.endY = r7     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
        L_0x020a:
            r3.add(r8)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r6 = r6 + 1
            goto L_0x017b
        L_0x0211:
            r5 = 0
        L_0x0212:
            int r6 = r0.length()     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            if (r5 >= r6) goto L_0x0231
            org.json.JSONObject r6 = r0.getJSONObject(r5)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            com.autonavi.bundle.routecommon.entity.ExtBusPath r6 = r13.parseJson2ExtBusPath(r6, r4, r3)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.ExtBusPath> r7 = r13.mExtBusPathList     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            r7.add(r6)     // Catch:{ RuntimeException -> 0x022d, Exception -> 0x0228 }
            int r5 = r5 + 1
            goto L_0x0212
        L_0x0228:
            r0 = move-exception
            defpackage.kf.a(r0)
            goto L_0x0231
        L_0x022d:
            r0 = move-exception
            defpackage.kf.a(r0)
        L_0x0231:
            boolean r0 = r13.mIsExtBusResult
            if (r0 != 0) goto L_0x02ca
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            java.lang.String r3 = "taxicost"
            int r3 = defpackage.axr.b(r14, r3)
            r0.mtaxiPrice = r3
            com.autonavi.bundle.routecommon.entity.BusPaths r0 = r13.mBusPathsResult
            java.lang.String r3 = "taxitime"
            int r3 = defpackage.axr.b(r14, r3)
            r0.mTaxiTime = r3
            java.lang.String r0 = "buslist"
            org.json.JSONArray r0 = defpackage.axr.a(r14, r0)
            java.lang.String r3 = "count"
            int r3 = defpackage.axr.b(r14, r3)
            if (r3 != 0) goto L_0x025f
            if (r0 == 0) goto L_0x025f
            int r3 = r0.length()
        L_0x025f:
            if (r3 <= r15) goto L_0x0262
            goto L_0x0263
        L_0x0262:
            r15 = r3
        L_0x0263:
            if (r15 != 0) goto L_0x0266
            return r1
        L_0x0266:
            r3 = 0
            java.lang.String r4 = "taxibus"
            org.json.JSONArray r14 = defpackage.axr.a(r14, r4)     // Catch:{ Exception -> 0x0281 }
            if (r14 == 0) goto L_0x0285
            int r4 = r14.length()     // Catch:{ Exception -> 0x0281 }
            if (r4 <= r2) goto L_0x0285
            com.autonavi.bundle.routecommon.entity.BusPaths r4 = r13.mBusPathsResult     // Catch:{ Exception -> 0x0281 }
            com.autonavi.bundle.routecommon.entity.BusPath r14 = r13.parseJson2TaxiBusPath(r14, r4)     // Catch:{ Exception -> 0x0281 }
            if (r14 == 0) goto L_0x0286
            int r15 = r15 + 1
            goto L_0x0286
        L_0x0281:
            r14 = move-exception
            defpackage.kf.a(r14)
        L_0x0285:
            r14 = r3
        L_0x0286:
            com.autonavi.bundle.routecommon.entity.BusPaths r4 = r13.mBusPathsResult
            r4.mPathNum = r15
            com.autonavi.bundle.routecommon.entity.BusPaths r4 = r13.mBusPathsResult
            com.autonavi.bundle.routecommon.entity.BusPath[] r5 = new com.autonavi.bundle.routecommon.entity.BusPath[r15]
            r4.mBusPaths = r5
            if (r14 == 0) goto L_0x02b2
        L_0x0292:
            int r4 = r15 + -1
            if (r1 >= r4) goto L_0x02ab
            if (r0 == 0) goto L_0x029c
            org.json.JSONObject r3 = r0.getJSONObject(r1)
        L_0x029c:
            com.autonavi.bundle.routecommon.entity.BusPaths r4 = r13.mBusPathsResult
            com.autonavi.bundle.routecommon.entity.BusPath r4 = r13.parseJson2BusPath(r3, r4)
            com.autonavi.bundle.routecommon.entity.BusPaths r5 = r13.mBusPathsResult
            com.autonavi.bundle.routecommon.entity.BusPath[] r5 = r5.mBusPaths
            r5[r1] = r4
            int r1 = r1 + 1
            goto L_0x0292
        L_0x02ab:
            com.autonavi.bundle.routecommon.entity.BusPaths r15 = r13.mBusPathsResult
            com.autonavi.bundle.routecommon.entity.BusPath[] r15 = r15.mBusPaths
            r15[r4] = r14
            goto L_0x02ca
        L_0x02b2:
            if (r1 >= r15) goto L_0x02ca
            if (r0 == 0) goto L_0x02bb
            org.json.JSONObject r14 = r0.getJSONObject(r1)
            r3 = r14
        L_0x02bb:
            com.autonavi.bundle.routecommon.entity.BusPaths r14 = r13.mBusPathsResult
            com.autonavi.bundle.routecommon.entity.BusPath r14 = r13.parseJson2BusPath(r3, r14)
            com.autonavi.bundle.routecommon.entity.BusPaths r4 = r13.mBusPathsResult
            com.autonavi.bundle.routecommon.entity.BusPath[] r4 = r4.mBusPaths
            r4[r1] = r14
            int r1 = r1 + 1
            goto L_0x02b2
        L_0x02ca:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.RouteBusResultData.parse(org.json.JSONObject, int):boolean");
    }

    private ExtBusPath parseJson2ExtBusPath(JSONObject jSONObject, ArrayList<BusPath> arrayList, ArrayList<ExTaxiPath> arrayList2) throws JSONException {
        int i;
        ExtBusPath extBusPath = new ExtBusPath();
        extBusPath.mFromPoi = this.mFromPoi.clone();
        extBusPath.mToPoi = this.mToPoi.clone();
        extBusPath.cost = axr.c(jSONObject, "cost");
        extBusPath.time = axr.b(jSONObject, "time");
        extBusPath.tag = axr.d(jSONObject, "tag");
        extBusPath.distance = axr.b(jSONObject, "distance");
        JSONArray a = axr.a(jSONObject, "segments");
        if (a != null && a.length() > 0) {
            ArrayList<ExBusPathSegment> segmentList = extBusPath.getSegmentList();
            for (int i2 = 0; i2 < a.length(); i2++) {
                ExBusPathSegment exBusPathSegment = new ExBusPathSegment();
                JSONArray jSONArray = a.getJSONArray(i2);
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    JSONArray jSONArray2 = jSONArray.getJSONArray(i3);
                    String str = (String) jSONArray2.get(0);
                    if (str.equals(ShowRouteActionProcessor.SEARCH_TYPE_BUS)) {
                        try {
                            i = ahh.b(((String) jSONArray2.get(1)).trim());
                        } catch (NumberFormatException unused) {
                            i = 0;
                        }
                        if (arrayList.size() > 0 && i >= 0) {
                            BusPath busPath = arrayList.get(i);
                            if (busPath.mPathSections != null && busPath.mPathSections.length > 0) {
                                exBusPathSegment.getBusPathList().add(busPath);
                            }
                        }
                    } else if (str.equals(FunctionSupportConfiger.TAXI_TAG)) {
                        int b = ahh.b(((String) jSONArray2.get(1)).trim());
                        if (arrayList2.size() > 0 && b >= 0) {
                            exBusPathSegment.getBusPathList().add(arrayList2.get(b));
                        }
                    } else if (str.equals("railway")) {
                        exBusPathSegment.getBusPathList().add(parseJson2ExTrainPath(jSONArray2.getJSONObject(1)));
                    }
                }
                segmentList.add(exBusPathSegment);
            }
        }
        return extBusPath;
    }

    private ExTrainPath parseJson2ExTrainPath(JSONObject jSONObject) {
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        double d6;
        JSONObject jSONObject2 = jSONObject;
        ExTrainPath exTrainPath = new ExTrainPath();
        exTrainPath.sstid = axr.e(jSONObject2, "sstid");
        exTrainPath.spoiid = axr.e(jSONObject2, "spoiid");
        exTrainPath.sad = axr.e(jSONObject2, "sad");
        exTrainPath.tad = axr.e(jSONObject2, "tad");
        exTrainPath.id = axr.e(jSONObject2, "id");
        exTrainPath.trip = axr.e(jSONObject2, "trip");
        exTrainPath.tou = axr.b(jSONObject2, "tou");
        exTrainPath.tst = axr.e(jSONObject2, "tst");
        exTrainPath.type = axr.b(jSONObject2, "type");
        exTrainPath.sin = axr.b(jSONObject2, "sin");
        exTrainPath.sst = axr.e(jSONObject2, "sst");
        exTrainPath.sint = axr.e(jSONObject2, "sint");
        exTrainPath.time = axr.b(jSONObject2, "time");
        exTrainPath.distance = axr.b(jSONObject2, "dis");
        exTrainPath.name = axr.e(jSONObject2, "name");
        exTrainPath.tstid = axr.e(jSONObject2, "tstid");
        exTrainPath.tpoiid = axr.e(jSONObject2, "tpoiid");
        exTrainPath.tout = axr.e(jSONObject2, "tout");
        exTrainPath.viastid = axr.e(jSONObject2, "viastid");
        exTrainPath.viast = axr.e(jSONObject2, "viast");
        exTrainPath.viawait = axr.e(jSONObject2, "viawait");
        exTrainPath.viaint = axr.e(jSONObject2, "viaint");
        exTrainPath.scord = axr.e(jSONObject2, "scord");
        exTrainPath.tcord = axr.e(jSONObject2, "tcord");
        exTrainPath.viastcord = axr.e(jSONObject2, "viastcord");
        String[] split = exTrainPath.scord.split(Token.SEPARATOR);
        if (split.length > 0) {
            try {
                d5 = Double.parseDouble(split[0]);
                d6 = Double.parseDouble(split[1]);
            } catch (NumberFormatException unused) {
                d5 = LocationInstrument.getInstance().getLatestPosition().getLongitude();
                d6 = LocationInstrument.getInstance().getLatestPosition().getLatitude();
            }
            Point a = cfg.a(d6, d5);
            exTrainPath.startX = a.x;
            exTrainPath.startY = a.y;
        }
        String[] split2 = exTrainPath.tcord.split(Token.SEPARATOR);
        if (split2.length > 0) {
            try {
                d3 = Double.parseDouble(split2[0]);
                d4 = Double.parseDouble(split2[1]);
            } catch (NumberFormatException unused2) {
                d3 = LocationInstrument.getInstance().getLatestPosition().getLongitude();
                d4 = LocationInstrument.getInstance().getLatestPosition().getLatitude();
            }
            Point a2 = cfg.a(d4, d3);
            exTrainPath.endX = a2.x;
            exTrainPath.endY = a2.y;
        }
        String[] split3 = exTrainPath.viastcord.split(Token.SEPARATOR);
        if (split3.length > 0) {
            int length = split3.length / 2;
            String[] split4 = exTrainPath.viastid.split(Token.SEPARATOR);
            String[] split5 = exTrainPath.viast.split(Token.SEPARATOR);
            String[] split6 = exTrainPath.viaint.split(Token.SEPARATOR);
            String[] split7 = exTrainPath.viawait.split(Token.SEPARATOR);
            exTrainPath.mXs = new int[length];
            exTrainPath.mYs = new int[length];
            ArrayList<Station> stationList = exTrainPath.getStationList();
            int i = 0;
            while (i < length) {
                int i2 = i * 2;
                try {
                    d2 = Double.parseDouble(split3[i2 + 1]);
                    d = Double.parseDouble(split3[i2]);
                } catch (NumberFormatException unused3) {
                    d = LocationInstrument.getInstance().getLatestPosition().getLongitude();
                    d2 = LocationInstrument.getInstance().getLatestPosition().getLatitude();
                }
                double d7 = d;
                Point a3 = cfg.a(d2, d7);
                String[] strArr = split3;
                exTrainPath.mXs[i] = a3.x;
                exTrainPath.mYs[i] = a3.y;
                try {
                    Station station = new Station();
                    station.id = split4[i];
                    station.name = split5[i];
                    station.time = split6[i];
                    station.wait = ahh.b(split7[i]);
                    station.x = a3.x;
                    station.y = a3.y;
                    station.lat = d2;
                    station.lon = d7;
                    stationList.add(station);
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                i++;
                split3 = strArr;
                JSONObject jSONObject3 = jSONObject;
            }
            int i3 = length + 2;
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            for (int i4 = 0; i4 < iArr.length; i4++) {
                if (i4 == 0) {
                    iArr[i4] = exTrainPath.startX;
                    iArr2[i4] = exTrainPath.startY;
                } else if (i4 == iArr.length - 1) {
                    iArr[i4] = exTrainPath.endX;
                    iArr2[i4] = exTrainPath.endY;
                } else {
                    int i5 = i4 - 1;
                    iArr[i4] = exTrainPath.mXs[i5];
                    iArr2[i4] = exTrainPath.mYs[i5];
                }
            }
            exTrainPath.mXs = iArr;
            exTrainPath.mYs = iArr2;
        }
        JSONObject jSONObject4 = jSONObject;
        if (jSONObject4.has("alter")) {
            try {
                JSONObject jSONObject5 = jSONObject4.getJSONObject("alter");
                String e2 = axr.e(jSONObject5, "id");
                String e3 = axr.e(jSONObject5, "name");
                if (e2 != null && !e2.trim().equals("") && e3 != null && !e3.trim().equals("")) {
                    String[] split8 = e2.split(Token.SEPARATOR);
                    String[] split9 = e3.split(Token.SEPARATOR);
                    if (split8.length == split9.length) {
                        for (int i6 = 0; i6 < split8.length; i6++) {
                            AlterTrain alterTrain = new AlterTrain();
                            alterTrain.id = split8[i6];
                            alterTrain.name = split9[i6];
                            exTrainPath.getAlterList().add(alterTrain);
                        }
                    }
                }
            } catch (Exception e4) {
                kf.a((Throwable) e4);
            }
        }
        if (jSONObject4.has("price")) {
            try {
                JSONObject jSONObject6 = jSONObject4.getJSONObject("price");
                String e5 = axr.e(jSONObject6, "type");
                String[] strArr2 = null;
                String[] split10 = !TextUtils.isEmpty(e5) ? e5.split(Token.SEPARATOR) : null;
                String e6 = axr.e(jSONObject6, "value");
                if (!TextUtils.isEmpty(e6)) {
                    strArr2 = e6.split(Token.SEPARATOR);
                }
                double d8 = 0.0d;
                if (split10 != null && strArr2 != null && split10.length > 0 && strArr2.length > 0 && split10.length == strArr2.length) {
                    double d9 = 0.0d;
                    for (int i7 = 0; i7 < split10.length; i7++) {
                        Price price = new Price();
                        price.type = ahh.b(split10[i7]);
                        double parseDouble = Double.parseDouble(strArr2[i7]);
                        double floor = Math.floor(parseDouble);
                        if (parseDouble - floor > 0.0d) {
                            price.value = floor;
                        } else {
                            price.value = parseDouble;
                        }
                        if (d9 == 0.0d) {
                            d9 = price.value;
                        }
                        if (price.value <= d9) {
                            d9 = price.value;
                        }
                        exTrainPath.getPriceList().add(price);
                    }
                    d8 = d9;
                }
                exTrainPath.minPrice = d8;
            } catch (Exception e7) {
                kf.a((Throwable) e7);
            }
        }
        return exTrainPath;
    }

    /* JADX WARNING: Removed duplicated region for block: B:88:0x020d A[SYNTHETIC, Splitter:B:88:0x020d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.bundle.routecommon.entity.BusPath parseJson2BusPath(org.json.JSONObject r10, com.autonavi.bundle.routecommon.entity.BusPaths r11) {
        /*
            r9 = this;
            com.autonavi.bundle.routecommon.entity.BusPath r0 = new com.autonavi.bundle.routecommon.entity.BusPath
            r0.<init>()
            int r1 = r11.mtaxiPrice
            r0.taxi_price = r1
            int r11 = r11.mTaxiTime
            r0.taxt_time = r11
            java.lang.String r11 = "expense"
            double r1 = defpackage.axr.c(r10, r11)
            r0.expense = r1
            java.lang.String r11 = "expensetime"
            int r11 = defpackage.axr.b(r10, r11)
            r0.expenseTime = r11
            java.lang.String r11 = "allfootlength"
            int r11 = defpackage.axr.b(r10, r11)
            r0.mAllFootLength = r11
            java.lang.String r11 = "busindex"
            boolean r11 = r10.has(r11)
            if (r11 == 0) goto L_0x0039
            java.lang.String r11 = "busindex"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            int r11 = java.lang.Integer.parseInt(r11)     // Catch:{ Exception -> 0x0039 }
            r0.busindex = r11     // Catch:{ Exception -> 0x0039 }
        L_0x0039:
            java.lang.String r11 = "startride"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            boolean r11 = android.text.TextUtils.isEmpty(r11)
            r1 = 1
            r2 = 0
            if (r11 != 0) goto L_0x004a
        L_0x0048:
            r11 = 1
            goto L_0x0058
        L_0x004a:
            java.lang.String r11 = "endride"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            boolean r11 = android.text.TextUtils.isEmpty(r11)
            if (r11 != 0) goto L_0x0057
            goto L_0x0048
        L_0x0057:
            r11 = 0
        L_0x0058:
            r9.setIsRidePathFlag(r11)
            if (r11 == 0) goto L_0x006e
            java.lang.String r3 = "endridelength"
            int r3 = defpackage.axr.b(r10, r3)
            r0.mEndWalkLength = r3
            java.lang.String r3 = "endridetime"
            int r3 = defpackage.axr.b(r10, r3)
            r0.endfoottime = r3
            goto L_0x007e
        L_0x006e:
            java.lang.String r3 = "endfootlength"
            int r3 = defpackage.axr.b(r10, r3)
            r0.mEndWalkLength = r3
            java.lang.String r3 = "endfoottime"
            int r3 = defpackage.axr.b(r10, r3)
            r0.endfoottime = r3
        L_0x007e:
            r0.isRidePath = r11
            java.lang.String r11 = "endwalk"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            boolean r3 = android.text.TextUtils.isEmpty(r11)
            if (r3 == 0) goto L_0x0092
            java.lang.String r11 = "endride"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
        L_0x0092:
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r11 = r9.getWalkPath(r11)
            r0.endwalk = r11
            java.lang.String r11 = "spoi"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r11 = r9.getBusDisplayObj(r11)
            r0.mStartObj = r11
            java.lang.String r11 = "epoi"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r11 = r9.getBusDisplayObj(r11)
            r0.mEndObj = r11
            r0.totaldriverlength = r2
            java.lang.String r11 = "time_tag"
            int r11 = defpackage.axr.b(r10, r11)
            r0.time_tag = r11
            java.lang.String r11 = "time_tag_des"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            r0.time_tag_des = r11
            java.lang.String r11 = "risk_time_des"
            java.lang.String r11 = defpackage.axr.e(r10, r11)
            r0.risk_time_des = r11
            java.lang.String r11 = "min_tag"
            int r11 = defpackage.axr.b(r10, r11)
            r0.min_tag = r11
            java.lang.String r11 = "etastatus"
            int r11 = defpackage.axr.b(r10, r11)
            r0.etastatus = r11
            java.lang.String r11 = "busTag"
            org.json.JSONArray r11 = defpackage.axr.a(r10, r11)
            if (r11 == 0) goto L_0x0141
            int r3 = r11.length()
            if (r3 <= 0) goto L_0x0141
            int r3 = r11.length()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>(r3)
            r5 = 0
        L_0x00f6:
            if (r5 >= r3) goto L_0x0127
            org.json.JSONObject r6 = r11.getJSONObject(r5)     // Catch:{ Exception -> 0x0120 }
            if (r6 == 0) goto L_0x0124
            java.lang.String r7 = "desc"
            java.lang.String r7 = defpackage.axr.e(r6, r7)     // Catch:{ Exception -> 0x0120 }
            java.lang.String r8 = "color"
            java.lang.String r6 = defpackage.axr.e(r6, r8)     // Catch:{ Exception -> 0x0120 }
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x0120 }
            if (r8 != 0) goto L_0x0124
            boolean r8 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x0120 }
            if (r8 == 0) goto L_0x0117
            goto L_0x0124
        L_0x0117:
            com.autonavi.bundle.routecommon.entity.BusPath$a r8 = new com.autonavi.bundle.routecommon.entity.BusPath$a     // Catch:{ Exception -> 0x0120 }
            r8.<init>(r7, r6)     // Catch:{ Exception -> 0x0120 }
            r4.add(r8)     // Catch:{ Exception -> 0x0120 }
            goto L_0x0124
        L_0x0120:
            r6 = move-exception
            defpackage.kf.a(r6)
        L_0x0124:
            int r5 = r5 + 1
            goto L_0x00f6
        L_0x0127:
            int r11 = r4.size()
            if (r11 <= 0) goto L_0x0141
            com.autonavi.bundle.routecommon.entity.BusPath$a[] r3 = new com.autonavi.bundle.routecommon.entity.BusPath.a[r11]
            r0.mBusTags = r3
            r3 = 0
        L_0x0132:
            if (r3 >= r11) goto L_0x0141
            com.autonavi.bundle.routecommon.entity.BusPath$a[] r5 = r0.mBusTags
            java.lang.Object r6 = r4.get(r3)
            com.autonavi.bundle.routecommon.entity.BusPath$a r6 = (com.autonavi.bundle.routecommon.entity.BusPath.a) r6
            r5[r3] = r6
            int r3 = r3 + 1
            goto L_0x0132
        L_0x0141:
            java.lang.String r11 = "segmentlist"
            org.json.JSONArray r10 = defpackage.axr.a(r10, r11)
            if (r10 == 0) goto L_0x027a
            int r11 = r10.length()
            if (r11 <= 0) goto L_0x027a
            int r11 = r10.length()
            r0.mSectionNum = r11
            int r11 = r0.mSectionNum
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r11 = new com.autonavi.bundle.routecommon.entity.BusPathSection[r11]
            r0.mPathSections = r11
            r11 = 0
            r3 = 0
        L_0x015e:
            int r4 = r10.length()
            if (r11 >= r4) goto L_0x0198
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r4 = r0.mPathSections     // Catch:{ Exception -> 0x0191 }
            org.json.JSONObject r5 = r10.getJSONObject(r11)     // Catch:{ Exception -> 0x0191 }
            com.autonavi.bundle.routecommon.entity.BusPathSection r5 = r9.getBusPathSection(r5)     // Catch:{ Exception -> 0x0191 }
            r4[r11] = r5     // Catch:{ Exception -> 0x0191 }
            if (r3 != 0) goto L_0x0179
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r4 = r0.mPathSections     // Catch:{ Exception -> 0x0191 }
            r4 = r4[r11]     // Catch:{ Exception -> 0x0191 }
            boolean r4 = r4.isRidePath     // Catch:{ Exception -> 0x0191 }
            r3 = r4
        L_0x0179:
            if (r11 != 0) goto L_0x0185
            com.autonavi.bundle.routecommon.entity.BusPath$BusDisplayObj r4 = r0.mStartObj     // Catch:{ Exception -> 0x0191 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r5 = r0.mPathSections     // Catch:{ Exception -> 0x0191 }
            r5 = r5[r11]     // Catch:{ Exception -> 0x0191 }
            java.lang.String r5 = r5.startPOIID     // Catch:{ Exception -> 0x0191 }
            r4.mPOI = r5     // Catch:{ Exception -> 0x0191 }
        L_0x0185:
            int r4 = r0.totaldriverlength     // Catch:{ Exception -> 0x0191 }
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r5 = r0.mPathSections     // Catch:{ Exception -> 0x0191 }
            r5 = r5[r11]     // Catch:{ Exception -> 0x0191 }
            int r5 = r5.mDriverLength     // Catch:{ Exception -> 0x0191 }
            int r4 = r4 + r5
            r0.totaldriverlength = r4     // Catch:{ Exception -> 0x0191 }
            goto L_0x0195
        L_0x0191:
            r4 = move-exception
            defpackage.kf.a(r4)
        L_0x0195:
            int r11 = r11 + 1
            goto L_0x015e
        L_0x0198:
            boolean r10 = r0.isRidePath
            if (r10 != 0) goto L_0x019e
            r0.isRidePath = r3
        L_0x019e:
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r10 = r0.mPathSections
            r10 = r10[r2]
            int r10 = r10.mFootLength
            r0.mStartWalkLength = r10
            int r10 = r0.mAllFootLength
            int r11 = r0.totaldriverlength
            int r10 = r10 + r11
            r0.mTotalLength = r10
            boolean r10 = r9.mIsExtBusResult
            if (r10 == 0) goto L_0x0252
            r0.isExtBusStartCityPath = r1
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r10 = r0.mPathSections
            r10 = r10[r2]
            int r11 = r10.mFootLength
            if (r11 <= 0) goto L_0x01f4
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r11 = r10.walk_path     // Catch:{ Exception -> 0x01ee }
            if (r11 == 0) goto L_0x01f4
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r11 = r10.walk_path     // Catch:{ Exception -> 0x01ee }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r11 = r11.infolist     // Catch:{ Exception -> 0x01ee }
            if (r11 == 0) goto L_0x01f4
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r11 = r10.walk_path     // Catch:{ Exception -> 0x01ee }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r11 = r11.infolist     // Catch:{ Exception -> 0x01ee }
            int r11 = r11.size()     // Catch:{ Exception -> 0x01ee }
            if (r11 <= 0) goto L_0x01f4
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r11 = r10.walk_path     // Catch:{ Exception -> 0x01ee }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r11 = r11.infolist     // Catch:{ Exception -> 0x01ee }
            java.lang.Object r11 = r11.get(r2)     // Catch:{ Exception -> 0x01ee }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r11 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r11     // Catch:{ Exception -> 0x01ee }
            int[] r11 = r11.mXs     // Catch:{ Exception -> 0x01ee }
            r11 = r11[r2]     // Catch:{ Exception -> 0x01ee }
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r3 = r10.walk_path     // Catch:{ Exception -> 0x01ec }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r3 = r3.infolist     // Catch:{ Exception -> 0x01ec }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ Exception -> 0x01ec }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r3 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r3     // Catch:{ Exception -> 0x01ec }
            int[] r3 = r3.mYs     // Catch:{ Exception -> 0x01ec }
            r3 = r3[r2]     // Catch:{ Exception -> 0x01ec }
            goto L_0x01f6
        L_0x01ec:
            r3 = move-exception
            goto L_0x01f0
        L_0x01ee:
            r3 = move-exception
            r11 = 0
        L_0x01f0:
            defpackage.kf.a(r3)
            goto L_0x01f5
        L_0x01f4:
            r11 = 0
        L_0x01f5:
            r3 = 0
        L_0x01f6:
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r4 = r0.mPathSections
            int r5 = r0.mSectionNum
            int r5 = r5 - r1
            r4 = r4[r5]
            if (r11 == 0) goto L_0x0201
            if (r3 != 0) goto L_0x0209
        L_0x0201:
            int[] r11 = r10.mXs
            r11 = r11[r2]
            int[] r10 = r10.mYs
            r3 = r10[r2]
        L_0x0209:
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r10 = r0.endwalk
            if (r10 == 0) goto L_0x0234
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r10 = r0.endwalk     // Catch:{ Exception -> 0x022e }
            java.util.ArrayList<com.autonavi.bundle.routecommon.entity.OnFootNaviSection> r10 = r10.infolist     // Catch:{ Exception -> 0x022e }
            if (r10 == 0) goto L_0x0234
            int r5 = r10.size()     // Catch:{ Exception -> 0x022e }
            int r5 = r5 - r1
            java.lang.Object r10 = r10.get(r5)     // Catch:{ Exception -> 0x022e }
            com.autonavi.bundle.routecommon.entity.OnFootNaviSection r10 = (com.autonavi.bundle.routecommon.entity.OnFootNaviSection) r10     // Catch:{ Exception -> 0x022e }
            int[] r5 = r10.mXs     // Catch:{ Exception -> 0x022e }
            int[] r10 = r10.mYs     // Catch:{ Exception -> 0x022e }
            int r6 = r5.length     // Catch:{ Exception -> 0x022e }
            int r6 = r6 - r1
            r5 = r5[r6]     // Catch:{ Exception -> 0x022e }
            int r6 = r10.length     // Catch:{ Exception -> 0x022c }
            int r6 = r6 - r1
            r10 = r10[r6]     // Catch:{ Exception -> 0x022c }
            r2 = r5
            goto L_0x0235
        L_0x022c:
            r10 = move-exception
            goto L_0x0230
        L_0x022e:
            r10 = move-exception
            r5 = 0
        L_0x0230:
            defpackage.kf.a(r10)
            r2 = r5
        L_0x0234:
            r10 = 0
        L_0x0235:
            if (r2 == 0) goto L_0x0239
            if (r10 != 0) goto L_0x0249
        L_0x0239:
            int[] r10 = r4.mXs
            int[] r2 = r4.mXs
            int r2 = r2.length
            int r2 = r2 - r1
            r2 = r10[r2]
            int[] r10 = r4.mYs
            int[] r4 = r4.mYs
            int r4 = r4.length
            int r4 = r4 - r1
            r10 = r10[r4]
        L_0x0249:
            r0.mstartX = r11
            r0.mstartY = r3
            r0.mendX = r2
            r0.mendY = r10
            goto L_0x027a
        L_0x0252:
            com.autonavi.common.model.POI r10 = r9.mFromPoi
            com.autonavi.common.model.GeoPoint r10 = r10.getPoint()
            int r10 = r10.x
            r0.mstartX = r10
            com.autonavi.common.model.POI r10 = r9.mFromPoi
            com.autonavi.common.model.GeoPoint r10 = r10.getPoint()
            int r10 = r10.y
            r0.mstartY = r10
            com.autonavi.common.model.POI r10 = r9.mToPoi
            com.autonavi.common.model.GeoPoint r10 = r10.getPoint()
            int r10 = r10.x
            r0.mendX = r10
            com.autonavi.common.model.POI r10 = r9.mToPoi
            com.autonavi.common.model.GeoPoint r10 = r10.getPoint()
            int r10 = r10.y
            r0.mendY = r10
        L_0x027a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.RouteBusResultData.parseJson2BusPath(org.json.JSONObject, com.autonavi.bundle.routecommon.entity.BusPaths):com.autonavi.bundle.routecommon.entity.BusPath");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01e9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.bundle.routecommon.entity.BusPath parseJson2TaxiBusPath(org.json.JSONArray r12, com.autonavi.bundle.routecommon.entity.BusPaths r13) throws java.lang.Exception {
        /*
            r11 = this;
            com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath r0 = new com.autonavi.bundle.routecommon.entity.BusPath$TaxiBusPath
            r0.<init>()
            r1 = 0
            org.json.JSONArray r2 = r12.getJSONArray(r1)
            r3 = 1
            if (r2 == 0) goto L_0x00f8
            int r4 = r2.length()
            if (r4 <= r3) goto L_0x00f8
            java.lang.Object r4 = r2.get(r1)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "taxi"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x00e5
            r0.isStart = r3
            org.json.JSONObject r2 = r2.getJSONObject(r3)
            java.lang.String r4 = "length"
            int r4 = defpackage.axr.b(r2, r4)
            r0.mDriveLength = r4
            java.lang.String r4 = "cost"
            int r4 = defpackage.axr.b(r2, r4)
            r0.mCost = r4
            java.lang.String r4 = "drivetime"
            int r4 = defpackage.axr.b(r2, r4)
            r0.mDriveTime = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "#"
            r4.<init>(r5)
            java.lang.String r5 = "color"
            java.lang.String r5 = defpackage.axr.e(r2, r5)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r0.color = r4
            java.lang.String r4 = "startpoint"
            java.lang.String r4 = defpackage.axr.e(r2, r4)
            java.lang.String r5 = ","
            java.lang.String[] r4 = r4.split(r5)
            int r5 = r4.length
            if (r5 <= 0) goto L_0x007e
            r5 = r4[r1]
            double r5 = java.lang.Double.parseDouble(r5)
            r4 = r4[r3]
            double r7 = java.lang.Double.parseDouble(r4)
            android.graphics.Point r4 = defpackage.cfg.a(r7, r5)
            int r5 = r4.x
            r0.mstartX = r5
            int r4 = r4.y
            r0.mstartY = r4
        L_0x007e:
            java.lang.String r4 = "endpoint"
            java.lang.String r4 = defpackage.axr.e(r2, r4)
            java.lang.String r5 = ","
            java.lang.String[] r4 = r4.split(r5)
            int r5 = r4.length
            if (r5 <= 0) goto L_0x00a5
            r5 = r4[r1]
            double r5 = java.lang.Double.parseDouble(r5)
            r4 = r4[r3]
            double r7 = java.lang.Double.parseDouble(r4)
            android.graphics.Point r4 = defpackage.cfg.a(r7, r5)
            int r5 = r4.x
            r0.mendX = r5
            int r4 = r4.y
            r0.mendY = r4
        L_0x00a5:
            java.lang.String r4 = "coord"
            java.lang.String r2 = defpackage.axr.e(r2, r4)
            java.lang.String r4 = ","
            java.lang.String[] r2 = r2.split(r4)
            int r4 = r2.length
            if (r4 <= 0) goto L_0x00f8
            int r4 = r2.length
            int r4 = r4 / 2
            int[] r5 = new int[r4]
            r0.mXs = r5
            int[] r5 = new int[r4]
            r0.mYs = r5
            r5 = 0
        L_0x00c0:
            if (r5 >= r4) goto L_0x00f8
            int r6 = r5 * 2
            int r7 = r6 + 1
            r7 = r2[r7]
            double r7 = java.lang.Double.parseDouble(r7)
            r6 = r2[r6]
            double r9 = java.lang.Double.parseDouble(r6)
            android.graphics.Point r6 = defpackage.cfg.a(r7, r9)
            int[] r7 = r0.mXs
            int r8 = r6.x
            r7[r5] = r8
            int[] r7 = r0.mYs
            int r6 = r6.y
            r7[r5] = r6
            int r5 = r5 + 1
            goto L_0x00c0
        L_0x00e5:
            java.lang.String r5 = "bus"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00f8
            r0.isStart = r1
            org.json.JSONObject r2 = r2.getJSONObject(r3)
            com.autonavi.bundle.routecommon.entity.BusPath r2 = r11.parseJson2BusPath(r2, r13)
            goto L_0x00f9
        L_0x00f8:
            r2 = 0
        L_0x00f9:
            org.json.JSONArray r12 = r12.getJSONArray(r3)
            if (r12 == 0) goto L_0x01e7
            int r4 = r12.length()
            if (r4 <= r3) goto L_0x01e7
            java.lang.Object r4 = r12.get(r1)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "bus"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x011d
            org.json.JSONObject r12 = r12.getJSONObject(r3)
            com.autonavi.bundle.routecommon.entity.BusPath r2 = r11.parseJson2BusPath(r12, r13)
            goto L_0x01e7
        L_0x011d:
            java.lang.String r13 = "taxi"
            boolean r13 = r4.equals(r13)
            if (r13 == 0) goto L_0x01e7
            org.json.JSONObject r12 = r12.getJSONObject(r3)
            java.lang.String r13 = "length"
            int r13 = defpackage.axr.b(r12, r13)
            r0.mDriveLength = r13
            java.lang.String r13 = "cost"
            int r13 = defpackage.axr.b(r12, r13)
            r0.mCost = r13
            java.lang.String r13 = "drivetime"
            int r13 = defpackage.axr.b(r12, r13)
            r0.mDriveTime = r13
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r4 = "#"
            r13.<init>(r4)
            java.lang.String r4 = "color"
            java.lang.String r4 = defpackage.axr.e(r12, r4)
            r13.append(r4)
            java.lang.String r13 = r13.toString()
            r0.color = r13
            java.lang.String r13 = "startpoint"
            java.lang.String r13 = defpackage.axr.e(r12, r13)
            java.lang.String r4 = ","
            java.lang.String[] r13 = r13.split(r4)
            int r4 = r13.length
            if (r4 <= 0) goto L_0x0180
            r4 = r13[r1]
            double r4 = java.lang.Double.parseDouble(r4)
            r13 = r13[r3]
            double r6 = java.lang.Double.parseDouble(r13)
            android.graphics.Point r13 = defpackage.cfg.a(r6, r4)
            int r4 = r13.x
            r0.mstartX = r4
            int r13 = r13.y
            r0.mstartY = r13
        L_0x0180:
            java.lang.String r13 = "endpoint"
            java.lang.String r13 = defpackage.axr.e(r12, r13)
            java.lang.String r4 = ","
            java.lang.String[] r13 = r13.split(r4)
            int r4 = r13.length
            if (r4 <= 0) goto L_0x01a7
            r4 = r13[r1]
            double r4 = java.lang.Double.parseDouble(r4)
            r13 = r13[r3]
            double r6 = java.lang.Double.parseDouble(r13)
            android.graphics.Point r13 = defpackage.cfg.a(r6, r4)
            int r4 = r13.x
            r0.mendX = r4
            int r13 = r13.y
            r0.mendY = r13
        L_0x01a7:
            java.lang.String r13 = "coord"
            java.lang.String r12 = defpackage.axr.e(r12, r13)
            java.lang.String r13 = ","
            java.lang.String[] r12 = r12.split(r13)
            int r13 = r12.length
            if (r13 <= 0) goto L_0x01e7
            int r13 = r12.length
            int r13 = r13 / 2
            int[] r4 = new int[r13]
            r0.mXs = r4
            int[] r4 = new int[r13]
            r0.mYs = r4
            r4 = 0
        L_0x01c2:
            if (r4 >= r13) goto L_0x01e7
            int r5 = r4 * 2
            int r6 = r5 + 1
            r6 = r12[r6]
            double r6 = java.lang.Double.parseDouble(r6)
            r5 = r12[r5]
            double r8 = java.lang.Double.parseDouble(r5)
            android.graphics.Point r5 = defpackage.cfg.a(r6, r8)
            int[] r6 = r0.mXs
            int r7 = r5.x
            r6[r4] = r7
            int[] r6 = r0.mYs
            int r5 = r5.y
            r6[r4] = r5
            int r4 = r4 + 1
            goto L_0x01c2
        L_0x01e7:
            if (r2 == 0) goto L_0x0213
            boolean r12 = r0.isStart
            if (r12 == 0) goto L_0x01fe
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections
            r12 = r12[r1]
            java.lang.String r12 = r12.mStartName
            r0.mEndName = r12
            com.autonavi.common.model.POI r12 = r11.mFromPoi
            java.lang.String r12 = r12.getName()
            r0.mStartName = r12
            goto L_0x0211
        L_0x01fe:
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r12 = r2.mPathSections
            int r13 = r2.mSectionNum
            int r13 = r13 - r3
            r12 = r12[r13]
            java.lang.String r12 = r12.mEndName
            r0.mStartName = r12
            com.autonavi.common.model.POI r12 = r11.mToPoi
            java.lang.String r12 = r12.getName()
            r0.mEndName = r12
        L_0x0211:
            r2.taxiBusPath = r0
        L_0x0213:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.RouteBusResultData.parseJson2TaxiBusPath(org.json.JSONArray, com.autonavi.bundle.routecommon.entity.BusPaths):com.autonavi.bundle.routecommon.entity.BusPath");
    }

    private BusDisplayObj getBusDisplayObj(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            BusDisplayObj busDisplayObj = new BusDisplayObj();
            busDisplayObj.mDisName = axr.e(jSONObject, "name");
            busDisplayObj.mDisType = axr.b(jSONObject, "type");
            busDisplayObj.mPOI = axr.e(jSONObject, "id");
            Point a = cfg.a(axr.c(jSONObject, DictionaryKeys.CTRLXY_Y), axr.c(jSONObject, DictionaryKeys.CTRLXY_X));
            busDisplayObj.mDisX = a.x;
            busDisplayObj.mDisY = a.y;
            return busDisplayObj;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }

    private WalkPath getWalkPath(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            WalkPath walkPath = new WalkPath();
            walkPath.dir = axr.b(jSONObject, "dir");
            byte f = axr.f(jSONObject, "action");
            JSONArray a = axr.a(jSONObject, "infolist");
            if (a != null && a.length() > 0) {
                walkPath.infolist = new ArrayList<>();
                for (int i = 0; i < a.length(); i++) {
                    JSONObject jSONObject2 = a.getJSONObject(i);
                    if (jSONObject2 == null) {
                        return null;
                    }
                    OnFootNaviSection onFootNaviSection = new OnFootNaviSection();
                    onFootNaviSection.mNaviActionStr = axr.e(jSONObject2, "main");
                    onFootNaviSection.mNavigtionAction = axr.f(jSONObject2, "main");
                    onFootNaviSection.mNaviAssiActionStr = axr.e(jSONObject2, "assist");
                    onFootNaviSection.mNaviAssiAction = axr.f(jSONObject2, "assist");
                    if (i == 0) {
                        onFootNaviSection.isBusSectionStart = true;
                        if (f > 0) {
                            onFootNaviSection.mNaviAssiAction = f;
                        }
                    }
                    onFootNaviSection.mWalkType = axr.b(jSONObject2, "walktype");
                    onFootNaviSection.mPathlength = axr.b(jSONObject2, "distance");
                    onFootNaviSection.mStreetName = axr.e(jSONObject2, "road");
                    String e = axr.e(jSONObject2, "coord");
                    if (e != null && e.length() > 0) {
                        String[] split = e.split(",");
                        int length = split.length / 2;
                        onFootNaviSection.mXs = new int[length];
                        onFootNaviSection.mYs = new int[length];
                        for (int i2 = 0; i2 < length; i2++) {
                            int i3 = i2 * 2;
                            Point a2 = cfg.a(Double.parseDouble(split[i3 + 1]), Double.parseDouble(split[i3]));
                            onFootNaviSection.mXs[i2] = a2.x;
                            onFootNaviSection.mYs[i2] = a2.y;
                        }
                    }
                    walkPath.infolist.add(onFootNaviSection);
                }
            }
            return walkPath;
        } catch (JSONException unused) {
            return null;
        }
    }

    private IrregularTime getIrregularTime(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            IrregularTime irregularTime = new IrregularTime();
            irregularTime.normalday = axr.e(jSONObject, "normalday");
            irregularTime.workday = axr.e(jSONObject, "workday");
            irregularTime.holiday = axr.e(jSONObject, "holiday");
            return irregularTime;
        } catch (JSONException unused) {
            return null;
        }
    }

    private Emergency getEmergency(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Emergency emergency = new Emergency();
            emergency.lineType = axr.b(jSONObject, "linetype");
            emergency.ldescription = axr.e(jSONObject, "ldescription");
            emergency.ssstatus = axr.e(jSONObject, "ssstatus");
            emergency.ssdescription = axr.e(jSONObject, "ssdescription");
            emergency.esstatus = axr.e(jSONObject, "esstatus");
            emergency.esdescription = axr.e(jSONObject, "esdescription");
            emergency.eventTagDesc = axr.e(jSONObject, "eventTagDesc");
            return emergency;
        } catch (JSONException unused) {
            return null;
        }
    }

    private SubwayPort getSubWayPort(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            SubwayPort subwayPort = new SubwayPort();
            subwayPort.subwayName = axr.e(jSONObject, "buskeyname");
            String e = axr.e(jSONObject, "name");
            if (e != null && e.indexOf(Token.SEPARATOR) > 0) {
                e = e.split(Token.SEPARATOR)[0];
            }
            subwayPort.name = e;
            String e2 = axr.e(jSONObject, "coord");
            if (e2 != null && e2.length() > 0) {
                String[] split = e2.split(",");
                if (split.length > 0) {
                    Point a = cfg.a(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                    subwayPort.coord = new GeoPoint(a.x, a.y);
                }
            }
            subwayPort.description = axr.e(jSONObject, "description");
            return subwayPort;
        } catch (JSONException unused) {
            return null;
        }
    }

    private void parseDrivercoord(BusPathSection busPathSection, JSONObject jSONObject) {
        if (busPathSection != null && jSONObject != null) {
            String[] strArr = null;
            String e = axr.e(jSONObject, "drivercoord");
            if (!TextUtils.isEmpty(e)) {
                strArr = e.split(",");
            }
            if (strArr != null) {
                int length = strArr.length / 2;
                busPathSection.mXs = new int[length];
                busPathSection.mYs = new int[length];
                busPathSection.mPointNum = length;
                for (int i = 0; i < length; i++) {
                    int i2 = i * 2;
                    Point a = cfg.a(Double.parseDouble(strArr[i2 + 1]), Double.parseDouble(strArr[i2]));
                    busPathSection.mXs[i] = a.x;
                    busPathSection.mYs[i] = a.y;
                }
            }
        }
    }

    private void parsePassDepot(BusPathSection busPathSection, JSONObject jSONObject) throws Exception {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        int i;
        BusPathSection busPathSection2 = busPathSection;
        JSONObject jSONObject2 = jSONObject;
        if (busPathSection2 != null && jSONObject2 != null) {
            busPathSection2.mStationNum = axr.b(jSONObject2, "passdepotcount") + 2;
            busPathSection2.mStations = new com.autonavi.bundle.routecommon.entity.Station[busPathSection2.mStationNum];
            int i2 = busPathSection2.mStationNum;
            String[] strArr4 = null;
            if (i2 > 2) {
                String[] split = axr.e(jSONObject2, "passdepotname").split(Token.SEPARATOR);
                strArr2 = axr.e(jSONObject2, "passdepotid").split(Token.SEPARATOR);
                if (jSONObject2.has("passdepotcoord")) {
                    strArr4 = axr.e(jSONObject2, "passdepotcoord").split(",");
                }
                strArr = axr.e(jSONObject2, "depotdirection").split(Token.SEPARATOR);
                String[] strArr5 = split;
                strArr3 = strArr4;
                strArr4 = strArr5;
            } else {
                strArr3 = null;
                strArr2 = null;
                strArr = null;
            }
            com.autonavi.bundle.routecommon.entity.Station station = new com.autonavi.bundle.routecommon.entity.Station();
            station.mName = busPathSection2.mStartName;
            station.id = busPathSection2.start_id;
            if (busPathSection2.mXs != null && busPathSection2.mYs != null && busPathSection2.mXs.length > 0 && busPathSection2.mYs.length > 0) {
                station.mX = busPathSection2.mXs[0];
                station.mY = busPathSection2.mYs[0];
                station.mStationdirection = axr.b(jSONObject2, "startdirection");
            }
            busPathSection2.mStations[0] = station;
            int i3 = 1;
            while (true) {
                i = i2 - 1;
                if (i3 >= i) {
                    break;
                }
                busPathSection2.mStations[i3] = new com.autonavi.bundle.routecommon.entity.Station();
                if (strArr4 != null) {
                    busPathSection2.mStations[i3].mName = strArr4[i3 - 1];
                }
                if (strArr2 != null) {
                    busPathSection2.mStations[i3].id = strArr2[i3 - 1];
                }
                if (strArr != null) {
                    int i4 = i3 - 1;
                    if (i4 < strArr.length && !TextUtils.isEmpty(strArr[i4])) {
                        busPathSection2.mStations[i3].mStationdirection = ahh.b(strArr[i4]);
                    }
                }
                if (strArr3 != null && strArr3.length > 0) {
                    int i5 = (i3 - 1) * 2;
                    Point a = cfg.a(Double.parseDouble(strArr3[i5 + 1]), Double.parseDouble(strArr3[i5]));
                    busPathSection2.mStations[i3].mX = a.x;
                    busPathSection2.mStations[i3].mY = a.y;
                }
                i3++;
            }
            new com.autonavi.bundle.routecommon.entity.Station();
            com.autonavi.bundle.routecommon.entity.Station station2 = new com.autonavi.bundle.routecommon.entity.Station();
            station2.mName = busPathSection2.mEndName;
            station2.id = busPathSection2.end_id;
            if (busPathSection2.mXs != null && busPathSection2.mYs != null && busPathSection2.mXs.length > 0 && busPathSection2.mYs.length > 0) {
                station2.mX = busPathSection2.mXs[busPathSection2.mXs.length - 1];
                station2.mY = busPathSection2.mYs[busPathSection2.mXs.length - 1];
                station2.mStationdirection = axr.b(jSONObject2, "enddirection");
            }
            busPathSection2.mStations[i] = station2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x016e, code lost:
        if (r0.mRouteColor.equals(com.alipay.mobile.security.bio.common.record.MetaRecord.LOG_SEPARATOR) != false) goto L_0x0170;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.bundle.routecommon.entity.BusPathSection getBusPathAlterSection(org.json.JSONObject r5, com.autonavi.bundle.routecommon.entity.BusPathSection r6) throws java.lang.Exception {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0175
            if (r6 != 0) goto L_0x0006
            goto L_0x0175
        L_0x0006:
            com.autonavi.bundle.routecommon.entity.BusPathSection r0 = new com.autonavi.bundle.routecommon.entity.BusPathSection
            r0.<init>()
            java.lang.String r1 = "busid"
            java.lang.String r1 = defpackage.axr.e(r5, r1)
            r0.bus_id = r1
            java.lang.String r1 = "busname"
            java.lang.String r1 = defpackage.axr.e(r5, r1)
            r0.mSectionName = r1
            java.lang.String r1 = "bustype"
            int r1 = defpackage.axr.b(r5, r1)
            r0.mBusType = r1
            java.lang.String r1 = "startid"
            java.lang.String r1 = defpackage.axr.e(r5, r1)
            if (r1 == 0) goto L_0x0037
            java.lang.String r2 = ""
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x0037
            r0.start_id = r1
            goto L_0x003b
        L_0x0037:
            java.lang.String r1 = r6.start_id
            r0.start_id = r1
        L_0x003b:
            java.lang.String r1 = "endid"
            java.lang.String r1 = defpackage.axr.e(r5, r1)
            if (r1 == 0) goto L_0x004e
            java.lang.String r2 = ""
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x004e
            r0.end_id = r1
            goto L_0x0052
        L_0x004e:
            java.lang.String r1 = r6.end_id
            r0.end_id = r1
        L_0x0052:
            java.lang.String r1 = r6.mStartName
            r0.mStartName = r1
            java.lang.String r1 = r6.mEndName
            r0.mEndName = r1
            boolean r6 = r6.is_night
            r0.is_night = r6
            java.lang.String r6 = "starttime"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.start_time = r6
            java.lang.String r6 = "endtime"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.end_time = r6
            java.lang.String r6 = "stationStartTime"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.stationStartTime = r6
            java.lang.String r6 = "stationEndTime"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.stationEndTime = r6
            java.lang.String r6 = "irregulartime"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r6 = r4.getIrregularTime(r6)
            r0.irregulartime = r6
            java.lang.String r6 = "startridelength"
            java.lang.String r6 = r5.optString(r6)
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x00ad
            java.lang.String r6 = "startridelength"
            int r6 = defpackage.axr.b(r5, r6)
            r0.mFootLength = r6
            java.lang.String r6 = "startridetime"
            int r6 = defpackage.axr.b(r5, r6)
            r0.foot_time = r6
            goto L_0x00bd
        L_0x00ad:
            java.lang.String r6 = "footlength"
            int r6 = defpackage.axr.b(r5, r6)
            r0.mFootLength = r6
            java.lang.String r6 = "foottime"
            int r6 = defpackage.axr.b(r5, r6)
            r0.foot_time = r6
        L_0x00bd:
            java.lang.String r6 = "driverlength"
            int r6 = defpackage.axr.b(r5, r6)
            r0.mDriverLength = r6
            java.lang.String r6 = "drivertime"
            int r6 = defpackage.axr.b(r5, r6)
            r0.driver_time = r6
            java.lang.String r6 = "interval_desc"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.intervalDesc = r6
            java.lang.String r6 = "bus_key_name"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.mExactSectionName = r6
            java.lang.String r6 = "transfertype"
            int r6 = defpackage.axr.b(r5, r6)
            r0.mTransferType = r6
            java.lang.String r6 = "bus_des"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.bus_des = r6
            java.lang.String r6 = "bus_time_tag"
            int r6 = defpackage.axr.b(r5, r6)
            r0.busTimeTag = r6
            java.lang.String r6 = "emergency"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            com.autonavi.bundle.routecommon.entity.BusPathSection$Emergency r6 = r4.getEmergency(r6)
            r0.mEmergency = r6
            java.lang.String r6 = "cityCode"
            java.lang.String r6 = defpackage.axr.e(r5, r6)
            r0.mCityCode = r6
            java.lang.String r6 = "loop"
            int r6 = defpackage.axr.b(r5, r6)
            java.lang.String r1 = "realtime"
            int r1 = defpackage.axr.b(r5, r1)
            r2 = 0
            r3 = 1
            if (r6 != r3) goto L_0x011c
            r6 = 1
            goto L_0x011d
        L_0x011c:
            r6 = 0
        L_0x011d:
            r0.isLoopLine = r6
            if (r1 != r3) goto L_0x0122
            r2 = 1
        L_0x0122:
            r0.isRealTime = r2
            boolean r6 = r0.isRealTime
            if (r6 == 0) goto L_0x012a
            r4.mHasRealTimeBus = r3
        L_0x012a:
            r4.parseDrivercoord(r0, r5)     // Catch:{ Exception -> 0x0142 }
            r4.parsePassDepot(r0, r5)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r6 = "eta"
            boolean r6 = r5.has(r6)     // Catch:{ Exception -> 0x0142 }
            if (r6 == 0) goto L_0x0146
            java.lang.String r6 = "eta"
            org.json.JSONObject r6 = r5.getJSONObject(r6)     // Catch:{ Exception -> 0x0142 }
            r4.parseBusEta(r0, r6)     // Catch:{ Exception -> 0x0142 }
            goto L_0x0146
        L_0x0142:
            r6 = move-exception
            defpackage.kf.a(r6)
        L_0x0146:
            r0.isNeedRequest = r3
            java.lang.String r6 = "color"
            boolean r6 = r5.has(r6)
            if (r6 == 0) goto L_0x0170
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r1 = "#"
            r6.<init>(r1)
            java.lang.String r1 = "color"
            java.lang.String r5 = defpackage.axr.e(r5, r1)
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r0.mRouteColor = r5
            java.lang.String r5 = r0.mRouteColor
            java.lang.String r6 = "#"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0174
        L_0x0170:
            java.lang.String r5 = "#42a5ff"
            r0.mRouteColor = r5
        L_0x0174:
            return r0
        L_0x0175:
            r5 = 0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.RouteBusResultData.getBusPathAlterSection(org.json.JSONObject, com.autonavi.bundle.routecommon.entity.BusPathSection):com.autonavi.bundle.routecommon.entity.BusPathSection");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x01fe, code lost:
        if (r0.mRouteColor.equals(com.alipay.mobile.security.bio.common.record.MetaRecord.LOG_SEPARATOR) != false) goto L_0x0200;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.autonavi.bundle.routecommon.entity.BusPathSection getBusPathSection(org.json.JSONObject r8) throws java.lang.Exception {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0004
            r8 = 0
            return r8
        L_0x0004:
            com.autonavi.bundle.routecommon.entity.BusPathSection r0 = new com.autonavi.bundle.routecommon.entity.BusPathSection
            r0.<init>()
            java.lang.String r1 = "busid"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.bus_id = r1
            java.lang.String r1 = "busname"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.mSectionName = r1
            java.lang.String r1 = "sstation_poiid"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.startPOIID = r1
            java.lang.String r1 = "estation_poiid"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.endPOIID = r1
            java.lang.String r1 = "bustype"
            int r1 = defpackage.axr.b(r8, r1)
            r0.mBusType = r1
            java.lang.String r1 = "inport"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            com.autonavi.bundle.routecommon.entity.BusPathSection$SubwayPort r1 = r7.getSubWayPort(r1)
            r0.subway_inport = r1
            java.lang.String r1 = "outport"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            com.autonavi.bundle.routecommon.entity.BusPathSection$SubwayPort r1 = r7.getSubWayPort(r1)
            r0.subway_outport = r1
            java.lang.String r1 = "startid"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.start_id = r1
            java.lang.String r1 = "endid"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.end_id = r1
            java.lang.String r1 = "startname"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.mStartName = r1
            java.lang.String r1 = "endname"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.mEndName = r1
            java.lang.String r1 = "starttime"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.start_time = r1
            java.lang.String r1 = "endtime"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.end_time = r1
            java.lang.String r1 = "stationStartTime"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.stationStartTime = r1
            java.lang.String r1 = "stationEndTime"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.stationEndTime = r1
            java.lang.String r1 = "irregulartime"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            com.autonavi.bundle.routecommon.entity.BusPathSection$IrregularTime r1 = r7.getIrregularTime(r1)
            r0.irregulartime = r1
            java.lang.String r1 = "night"
            int r1 = defpackage.axr.b(r8, r1)
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x00a7
            r1 = 0
            goto L_0x00a8
        L_0x00a7:
            r1 = 1
        L_0x00a8:
            r0.is_night = r1
            java.lang.String r1 = "driverlength"
            int r1 = defpackage.axr.b(r8, r1)
            r0.mDriverLength = r1
            java.lang.String r1 = "drivertime"
            int r1 = defpackage.axr.b(r8, r1)
            r0.driver_time = r1
            java.lang.String r1 = "bus_time_tag"
            int r1 = defpackage.axr.b(r8, r1)
            r0.busTimeTag = r1
            java.lang.String r1 = "bus_des"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.bus_des = r1
            java.lang.String r1 = "tm_limit"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.tmLimit = r1
            java.lang.String r1 = "tmct_2early"
            int r1 = defpackage.axr.b(r8, r1)
            r0.tmcT2early = r1
            java.lang.String r1 = "interval_desc"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.intervalDesc = r1
            java.lang.String r1 = "bus_key_name"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.mExactSectionName = r1
            java.lang.String r1 = "transfertype"
            int r1 = defpackage.axr.b(r8, r1)
            r0.mTransferType = r1
            java.lang.String r1 = "transfertip"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.mTransferTip = r1
            java.lang.String r1 = "emergency"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            com.autonavi.bundle.routecommon.entity.BusPathSection$Emergency r1 = r7.getEmergency(r1)
            r0.mEmergency = r1
            java.lang.String r1 = "cityCode"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            r0.mCityCode = r1
            java.lang.String r1 = "loop"
            int r1 = defpackage.axr.b(r8, r1)
            java.lang.String r4 = "realtime"
            int r4 = defpackage.axr.b(r8, r4)
            if (r1 != r3) goto L_0x0122
            r1 = 1
            goto L_0x0123
        L_0x0122:
            r1 = 0
        L_0x0123:
            r0.isLoopLine = r1
            if (r4 != r3) goto L_0x0129
            r1 = 1
            goto L_0x012a
        L_0x0129:
            r1 = 0
        L_0x012a:
            r0.isRealTime = r1
            boolean r1 = r0.isRealTime
            if (r1 == 0) goto L_0x0132
            r7.mHasRealTimeBus = r3
        L_0x0132:
            r7.parseDrivercoord(r0, r8)
            r7.parsePassDepot(r0, r8)
            java.lang.String r1 = "eta"
            boolean r1 = r8.has(r1)
            if (r1 == 0) goto L_0x0149
            java.lang.String r1 = "eta"
            org.json.JSONObject r1 = r8.getJSONObject(r1)
            r7.parseBusEta(r0, r1)
        L_0x0149:
            java.lang.String r1 = "startride"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0157
            goto L_0x0165
        L_0x0157:
            java.lang.String r1 = "endride"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0164
            goto L_0x0165
        L_0x0164:
            r3 = 0
        L_0x0165:
            r7.setIsRidePathFlag(r3)
            if (r3 == 0) goto L_0x017d
            java.lang.String r1 = "startridelength"
            int r1 = defpackage.axr.b(r8, r1)
            r0.mFootLength = r1
            java.lang.String r1 = "startridetime"
            int r1 = defpackage.axr.b(r8, r1)
            r0.foot_time = r1
            goto L_0x018d
        L_0x017d:
            java.lang.String r1 = "footlength"
            int r1 = defpackage.axr.b(r8, r1)
            r0.mFootLength = r1
            java.lang.String r1 = "foottime"
            int r1 = defpackage.axr.b(r8, r1)
            r0.foot_time = r1
        L_0x018d:
            r0.isRidePath = r3
            java.lang.String r1 = "walk"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L_0x01a3
            java.lang.String r1 = "startride"
            java.lang.String r1 = defpackage.axr.e(r8, r1)
        L_0x01a3:
            com.autonavi.bundle.routecommon.entity.BusPath$WalkPath r1 = r7.getWalkPath(r1)
            r0.walk_path = r1
            java.lang.String r1 = "alterlist"
            org.json.JSONArray r1 = defpackage.axr.a(r8, r1)
            if (r1 == 0) goto L_0x01d6
            int r3 = r1.length()
            if (r3 <= 0) goto L_0x01d6
            int r3 = r1.length()
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r4 = new com.autonavi.bundle.routecommon.entity.BusPathSection[r3]
            r0.alter_list = r4
            r4 = 0
        L_0x01c0:
            if (r4 >= r3) goto L_0x01d6
            com.autonavi.bundle.routecommon.entity.BusPathSection[] r5 = r0.alter_list     // Catch:{ Exception -> 0x01cf }
            org.json.JSONObject r6 = r1.getJSONObject(r4)     // Catch:{ Exception -> 0x01cf }
            com.autonavi.bundle.routecommon.entity.BusPathSection r6 = r7.getBusPathAlterSection(r6, r0)     // Catch:{ Exception -> 0x01cf }
            r5[r4] = r6     // Catch:{ Exception -> 0x01cf }
            goto L_0x01d3
        L_0x01cf:
            r5 = move-exception
            defpackage.kf.a(r5)
        L_0x01d3:
            int r4 = r4 + 1
            goto L_0x01c0
        L_0x01d6:
            r0.isNeedRequest = r2
            java.lang.String r1 = "color"
            boolean r1 = r8.has(r1)
            if (r1 == 0) goto L_0x0200
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "#"
            r1.<init>(r2)
            java.lang.String r2 = "color"
            java.lang.String r8 = defpackage.axr.e(r8, r2)
            r1.append(r8)
            java.lang.String r8 = r1.toString()
            r0.mRouteColor = r8
            java.lang.String r8 = r0.mRouteColor
            java.lang.String r1 = "#"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0204
        L_0x0200:
            java.lang.String r8 = "#42a5ff"
            r0.mRouteColor = r8
        L_0x0204:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.localbus.RouteBusResultData.getBusPathSection(org.json.JSONObject):com.autonavi.bundle.routecommon.entity.BusPathSection");
    }

    private void parseBusEta(BusPathSection busPathSection, JSONObject jSONObject) {
        if (jSONObject != null && busPathSection != null) {
            BusEta busEta = new BusEta();
            busEta.linestatus = axr.b(jSONObject, "linestatus");
            parseEtacoord(busEta, jSONObject);
            busEta.etalinks = parseEtaLink(busEta, jSONObject);
            busPathSection.mEta = busEta;
        }
    }

    private BusEtaLink[] parseEtaLink(BusEta busEta, JSONObject jSONObject) {
        if (busEta == null || jSONObject == null) {
            return null;
        }
        try {
            JSONArray a = axr.a(jSONObject, "lnk");
            if (a == null || a.length() <= 0) {
                return null;
            }
            int length = a.length();
            busEta.etalinks = new BusEtaLink[length];
            for (int i = 0; i < length; i++) {
                BusEtaLink busEtaLink = new BusEtaLink();
                busEtaLink.startindex = axr.b(a.getJSONObject(i), "sidx");
                busEtaLink.endindex = axr.b(a.getJSONObject(i), "eidx");
                busEtaLink.etastate = axr.b(a.getJSONObject(i), "v");
                busEta.etalinks[i] = busEtaLink;
            }
            return busEta.etalinks;
        } catch (Exception unused) {
            return null;
        }
    }

    private void parseEtacoord(BusEta busEta, JSONObject jSONObject) {
        if (busEta != null && jSONObject != null) {
            String[] strArr = null;
            String e = axr.e(jSONObject, "etacoords");
            if (!TextUtils.isEmpty(e)) {
                strArr = e.split(",");
            }
            if (strArr != null) {
                int length = strArr.length / 2;
                busEta.mXs = new int[length];
                busEta.mYs = new int[length];
                for (int i = 0; i < length; i++) {
                    int i2 = i * 2;
                    Point a = cfg.a(Double.parseDouble(strArr[i2 + 1]), Double.parseDouble(strArr[i2]));
                    busEta.mXs[i] = a.x;
                    busEta.mYs[i] = a.y;
                }
            }
        }
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public void setReqTime(long j) {
        this.mReqTimeInMillis = j;
    }

    public long getReqTime() {
        return this.mReqTimeInMillis;
    }

    public boolean isExtBusResult() {
        return this.mIsExtBusResult;
    }

    public ArrayList<ExtBusPath> getExtBusPathList() {
        if (this.mExtBusPathList == null) {
            this.mExtBusPathList = new ArrayList<>();
        }
        return this.mExtBusPathList;
    }

    public ExtBusPath getFocusExtBusPath() {
        if (this.mExtBusPathList == null || this.mExtBusPathList.size() == 0) {
            return null;
        }
        if (this.mFocusBusPathIndex >= this.mExtBusPathList.size()) {
            this.mFocusBusPathIndex = this.mExtBusPathList.size() - 1;
        }
        if (this.mFocusBusPathIndex < 0) {
            return null;
        }
        return this.mExtBusPathList.get(this.mFocusBusPathIndex);
    }

    public ExtBusPath getExtBusPath(int i) {
        if (this.mExtBusPathList == null) {
            return null;
        }
        if (i >= this.mExtBusPathList.size()) {
            i = this.mExtBusPathList.size() - 1;
        }
        if (i < 0) {
            return null;
        }
        return this.mExtBusPathList.get(i);
    }

    public void setFocusExtBusPath(int i) {
        if (this.mExtBusPathList != null) {
            if (i >= this.mExtBusPathList.size()) {
                i = this.mExtBusPathList.size() - 1;
            }
            this.mFocusBusPathIndex = i;
        }
    }

    public int getFocusExBusPathIndex() {
        return this.mFocusBusPathIndex;
    }

    public void setExtBusResultFlag(boolean z) {
        this.mIsExtBusResult = z;
    }

    public boolean hasRealTimeBusLine() {
        return this.mHasRealTimeBus;
    }

    public byte[] getBaseData() {
        return this.basedata;
    }

    public void setBaseData(byte[] bArr) {
        this.basedata = bArr;
    }

    public String getBaseDataForFavorite(int i) {
        if (this.basedata == null || this.mIsExtBusResult) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(new String(this.basedata, "UTF-8"));
            JSONArray a = axr.a(jSONObject, "buslist");
            if (a != null) {
                JSONObject optJSONObject = a.optJSONObject(i);
                if (optJSONObject != null) {
                    jSONObject.put("buslist", new JSONArray().put(optJSONObject));
                    return jSONObject.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isExistOutageBus() {
        int i = 0;
        while (i < this.mBusPathsResult.mBusPaths.length && i < 3) {
            BusPath busPath = this.mBusPathsResult.mBusPaths[i];
            if (busPath != null && busPath.time_tag == 2) {
                return true;
            }
            i++;
        }
        return false;
    }

    public ArrayList<axp> getBusResultFootErrorData() {
        BusPath focusBusPath = getFocusBusPath();
        if (focusBusPath == null || focusBusPath.mPathSections == null || focusBusPath.mPathSections.length <= 0) {
            return null;
        }
        ArrayList<axp> arrayList = new ArrayList<>();
        for (int i = 0; i < focusBusPath.mPathSections.length; i++) {
            if (focusBusPath.mPathSections[i].walk_path != null && focusBusPath.mPathSections[i].mFootLength >= 0) {
                axp axp = new axp();
                BusPathSection busPathSection = focusBusPath.mPathSections[i];
                if (!(busPathSection == null || busPathSection.mTransferType == 1 || busPathSection.mTransferType == 4)) {
                    WalkPath walkPath = busPathSection.walk_path;
                    if (walkPath != null) {
                        ArrayList<OnFootNaviSection> arrayList2 = walkPath.infolist;
                        if (arrayList2 != null && arrayList2.size() > 0) {
                            OnFootNaviSection onFootNaviSection = arrayList2.get(0);
                            if (onFootNaviSection != null) {
                                int[] iArr = onFootNaviSection.mXs;
                                int[] iArr2 = onFootNaviSection.mYs;
                                if (iArr != null && iArr2 != null && iArr.length > 0 && iArr2.length > 0) {
                                    axp.a = new GeoPoint(iArr[0], iArr2[0]);
                                    OnFootNaviSection onFootNaviSection2 = arrayList2.get(arrayList2.size() - 1);
                                    if (onFootNaviSection2 != null) {
                                        int[] iArr3 = onFootNaviSection2.mXs;
                                        int[] iArr4 = onFootNaviSection2.mYs;
                                        if (iArr3 != null && iArr4 != null && iArr3.length > 0 && iArr4.length > 0) {
                                            axp.b = new GeoPoint(iArr3[0], iArr4[0]);
                                            if (i == 0) {
                                                POI fromPOI = getFromPOI();
                                                if (fromPOI == null) {
                                                    axp.c = busPathSection.mStartName;
                                                } else {
                                                    axp.c = fromPOI.getName();
                                                }
                                            } else {
                                                axp.c = focusBusPath.mPathSections[i - 1].mEndName;
                                            }
                                            com.autonavi.bundle.routecommon.entity.Station[] stationArr = busPathSection.mStations;
                                            if (stationArr != null && stationArr.length > 0) {
                                                axp.d = stationArr[0].mName;
                                                arrayList.add(axp);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (focusBusPath.endwalk != null && focusBusPath.mEndWalkLength >= 0) {
            axp axp2 = new axp();
            ArrayList<OnFootNaviSection> arrayList3 = focusBusPath.endwalk.infolist;
            if (arrayList3 == null || arrayList3.size() <= 0) {
                return arrayList;
            }
            OnFootNaviSection onFootNaviSection3 = arrayList3.get(0);
            if (onFootNaviSection3 == null) {
                return arrayList;
            }
            int[] iArr5 = onFootNaviSection3.mXs;
            int[] iArr6 = onFootNaviSection3.mYs;
            if (iArr5 == null || iArr6 == null || iArr5.length <= 0 || iArr6.length <= 0) {
                return arrayList;
            }
            axp2.a = new GeoPoint(iArr5[0], iArr6[0]);
            axp2.b = new GeoPoint(focusBusPath.mendX, focusBusPath.mendY);
            axp2.c = focusBusPath.mPathSections[focusBusPath.mPathSections.length - 1].mEndName;
            POI toPOI = getToPOI();
            if (toPOI != null) {
                axp2.d = toPOI.getName();
            }
            arrayList.add(axp2);
        }
        return arrayList;
    }

    public String getBusUserMethod() {
        return new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("bus_method", "0");
    }

    public String getBsid() {
        return (this.mBusPathsResult == null || TextUtils.isEmpty(this.mBusPathsResult.mBsid)) ? "" : this.mBusPathsResult.mBsid;
    }

    public int getAlternative() {
        return this.mAlternative;
    }

    public boolean isRidePath() {
        return this.mIsRidePath;
    }

    private void setIsRidePathFlag(boolean z) {
        if (z) {
            this.mIsRidePath = z;
        }
    }
}
