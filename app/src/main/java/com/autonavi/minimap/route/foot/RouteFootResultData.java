package com.autonavi.minimap.route.foot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.route.wtbt.WMilestone;
import com.autonavi.jni.route.wtbt.WPoint;
import com.autonavi.jni.route.wtbt.WTBTDecoder;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.foot.inter.IFootRouteResult;
import com.autonavi.minimap.route.foot.model.OnFootNaviPath;
import com.autonavi.minimap.route.foot.model.OnFootNaviResult;
import com.autonavi.wtbt.WMilestoneWrapper;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint({"UnsafeDynamicallyLoadedCode"})
public class RouteFootResultData implements IRouteResultData, IFootRouteResult {
    private static final long serialVersionUID = -7678116973590843411L;
    private boolean isParseSuccess = false;
    private Context mContext;
    private int mFocusStationIndex = 0;
    private POI mFootEndPOI;
    private OnFootNaviResult mFootRouteResult;
    private POI mFootStartPOI;
    private String mFootType = "0";
    private boolean mIsRecommendData;
    private String mKey;
    private String mNaviid;
    private int mPlanTabIndex;
    private efb mRecommendLine;
    private int[] mTaxiFee;
    private WTBTDecoder mWTbt;
    private byte[] routeData;
    private POI[] share_from_poi;
    private POI[] share_to_poi;

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

    public RouteFootResultData(Context context) {
        this.mContext = context;
    }

    public void setIsRecommend(boolean z) {
        this.mIsRecommendData = z;
    }

    public efb getRecommendLine() {
        return this.mRecommendLine;
    }

    public void reset() {
        this.mFootRouteResult = null;
        this.mFocusStationIndex = 0;
        this.mFootStartPOI = null;
        this.mFootEndPOI = null;
        this.mFootType = "0";
        if (this.mWTbt != null) {
            this.mWTbt.destroy();
            this.mWTbt = null;
        }
    }

    public boolean hasData() {
        return this.routeData != null && this.routeData.length > 0;
    }

    public Class<RouteFootResultData> getClassType() {
        return RouteFootResultData.class;
    }

    public void setFromPOI(POI poi) {
        this.mFootStartPOI = poi;
    }

    public void setToPOI(POI poi) {
        this.mFootEndPOI = poi;
    }

    public void setMethod(String str) {
        this.mFootType = str;
    }

    public POI getFromPOI() {
        return this.mFootStartPOI;
    }

    public POI getShareFromPOI() {
        if (this.share_from_poi == null || this.mPlanTabIndex >= this.share_from_poi.length) {
            return this.mFootStartPOI.clone();
        }
        return this.share_from_poi[this.mPlanTabIndex];
    }

    public POI getToPOI() {
        return this.mFootEndPOI;
    }

    public POI getShareToPOI() {
        if (this.share_to_poi == null || this.mPlanTabIndex >= this.share_to_poi.length) {
            return this.mFootEndPOI.clone();
        }
        return this.share_to_poi[this.mPlanTabIndex];
    }

    public OnFootNaviResult getOnFootPlanResult() {
        if (!isParseOK() && this.routeData != null && this.routeData.length > 0) {
            parseData(this.routeData);
        }
        return this.mFootRouteResult;
    }

    public void setFocusStationIndex(int i) {
        this.mFocusStationIndex = i;
    }

    public int getFocusStationIndex() {
        return this.mFocusStationIndex;
    }

    public void setFocusTabIndex(int i) {
        this.mPlanTabIndex = i;
    }

    public int getFocusTabIndex() {
        return this.mPlanTabIndex;
    }

    public String getMethod() {
        return this.mFootType;
    }

    private int[] parseTaxiCost(byte[] bArr, int i) {
        int length = bArr.length - i;
        if (length <= 0) {
            return null;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i, bArr2, 0, length);
        if ((bArr2[0] & 255) + ((bArr2[1] & 255) << 8) == 100) {
            byte[] bArr3 = new byte[8];
            System.arraycopy(bArr2, 2, bArr3, 0, 8);
            try {
                String str = new String(bArr2, 10, (int) ahg.b(bArr3), "UTF-16LE");
                if (str.length() > 0) {
                    String[] split = str.split(",");
                    int[] iArr = new int[split.length];
                    for (int i2 = 0; i2 < split.length; i2++) {
                        iArr[i2] = ahh.b(split[i2]);
                    }
                    return iArr;
                }
            } catch (UnsupportedEncodingException e) {
                kf.a((Throwable) e);
                return null;
            }
        }
        return null;
    }

    public boolean parseData(byte[] bArr) {
        int i;
        OnFootNaviResult onFootNaviResult;
        byte[] bArr2 = bArr;
        char c = 0;
        if (bArr2 == null || bArr2.length == 0) {
            return false;
        }
        this.routeData = bArr2;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return false;
        }
        char c2 = 1;
        if (this.mWTbt == null) {
            this.mWTbt = new WTBTDecoder();
            StringBuilder sb = new StringBuilder();
            sb.append(FileUtil.getMapBaseStorage(this.mContext));
            sb.append("/autonavi/");
            String sb2 = sb.toString();
            WTBTDecoder wTBTDecoder = this.mWTbt;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append("/wtbt");
            if (wTBTDecoder.init(sb3.toString()) != 1) {
                if (this.mWTbt != null) {
                    this.mWTbt.destroy();
                    this.mWTbt = null;
                }
                return false;
            }
        }
        StringBuilder sb4 = new StringBuilder("WTBT engine code --->");
        sb4.append(WTBTDecoder.getVersion());
        AMapLog.e("FootNaviEngine", sb4.toString());
        try {
            byte[] bArr3 = new byte[(bArr2.length - 10)];
            System.arraycopy(bArr2, 10, bArr3, 0, bArr3.length);
            int i2 = 2;
            this.mTaxiFee = parseTaxiCost(bArr3, (bArr3[0] & 255) + ((bArr3[1] & 255) << 8) + ((bArr3[2] & 255) << 16));
            if (this.mWTbt.pushRouteData(0, 0, bArr2, bArr2.length) == 0) {
                if (this.mWTbt != null) {
                    this.mWTbt.destroy();
                    this.mWTbt = null;
                }
                return false;
            }
            OnFootNaviResult onFootNaviResult2 = new OnFootNaviResult();
            if (this.mWTbt != null) {
                this.mNaviid = this.mWTbt.getNaviID();
            }
            onFootNaviResult2.mNaviId = this.mNaviid;
            int[] allRouteID = this.mWTbt.getAllRouteID();
            if (allRouteID != null) {
                if (allRouteID.length != 0) {
                    if (this.mIsRecommendData) {
                        this.mWTbt.selectRoute(allRouteID[0]);
                        return parseRecommendData(this.mWTbt);
                    }
                    onFootNaviResult2.mPathNum = allRouteID.length;
                    onFootNaviResult2.mOnFootNaviPath = new OnFootNaviPath[onFootNaviResult2.mPathNum];
                    this.share_from_poi = new POI[onFootNaviResult2.mPathNum];
                    this.share_to_poi = new POI[onFootNaviResult2.mPathNum];
                    int i3 = 0;
                    while (i3 < onFootNaviResult2.mPathNum) {
                        if (this.mWTbt.selectRoute(allRouteID[i3]) == allRouteID[i3]) {
                            OnFootNaviPath onFootNaviPath = new OnFootNaviPath();
                            GeoPoint geoPoint = new GeoPoint();
                            double[] startCoor = this.mWTbt.getStartCoor();
                            if (startCoor != null && startCoor.length == i2) {
                                float f = (float) startCoor[c];
                                float f2 = (float) startCoor[c2];
                                POI clone = this.mFootStartPOI.clone();
                                POI clone2 = this.mFootEndPOI.clone();
                                if (f == 0.0f || f2 == 0.0f) {
                                    onFootNaviPath.mstartX = this.mFootStartPOI.getPoint().x;
                                    onFootNaviPath.mstartY = this.mFootStartPOI.getPoint().y;
                                } else {
                                    geoPoint.setLonLat((double) f, (double) f2);
                                    onFootNaviPath.mstartX = geoPoint.x;
                                    onFootNaviPath.mstartY = geoPoint.y;
                                    clone.setPoint(geoPoint);
                                }
                                GeoPoint geoPoint2 = new GeoPoint();
                                double[] endCoor = this.mWTbt.getEndCoor();
                                if (endCoor != null && endCoor.length == i2) {
                                    float f3 = (float) endCoor[c];
                                    OnFootNaviResult onFootNaviResult3 = onFootNaviResult2;
                                    float f4 = (float) endCoor[1];
                                    if (f3 == 0.0f || f4 == 0.0f) {
                                        onFootNaviPath.mendX = this.mFootEndPOI.getPoint().x;
                                        onFootNaviPath.mendY = this.mFootEndPOI.getPoint().y;
                                    } else {
                                        geoPoint2.setLonLat((double) f3, (double) f4);
                                        onFootNaviPath.mendX = geoPoint2.x;
                                        onFootNaviPath.mendY = geoPoint2.y;
                                        clone2.setPoint(geoPoint2);
                                    }
                                    this.share_from_poi[i3] = clone;
                                    this.share_to_poi[i3] = clone2;
                                    onFootNaviPath.mStartPOI = clone;
                                    onFootNaviPath.mEndPOI = clone2;
                                    String endPoiID = this.mWTbt.getEndPoiID();
                                    if (!TextUtils.isEmpty(endPoiID) && !TextUtils.equals(clone2.getId(), endPoiID)) {
                                        onFootNaviPath.mEndPOI.getPoiExtra().put("end_sub_poiid", endPoiID);
                                    }
                                    onFootNaviPath.mStartDirection = this.mWTbt.getStartDirection();
                                    onFootNaviPath.mPathlength += this.mWTbt.getRouteLength();
                                    onFootNaviPath.mPathTime = this.mWTbt.getRouteTravelTime();
                                    onFootNaviPath.crossingCount = this.mWTbt.getRouteCrossingCount();
                                    onFootNaviPath.tabName = this.mWTbt.getRouteText();
                                    onFootNaviPath.mAllPoints = getAllPoints(this.mWTbt);
                                    if (this.mTaxiFee != null && this.mTaxiFee.length > i3) {
                                        onFootNaviPath.mTaxiFee = this.mTaxiFee[i3];
                                    }
                                    WMilestone[] routeMilestones = this.mWTbt.getRouteMilestones();
                                    if (routeMilestones != null) {
                                        onFootNaviPath.mMileStones = new ArrayList<>();
                                        WMilestoneWrapper[] wMilestoneWrapperArr = new WMilestoneWrapper[routeMilestones.length];
                                        for (int i4 = 0; i4 < routeMilestones.length; i4++) {
                                            wMilestoneWrapperArr[i4] = new WMilestoneWrapper();
                                            wMilestoneWrapperArr[i4].x = routeMilestones[i4].x;
                                            wMilestoneWrapperArr[i4].y = routeMilestones[i4].y;
                                            wMilestoneWrapperArr[i4].mile = routeMilestones[i4].mile;
                                        }
                                        onFootNaviPath.mMileStones.addAll(Arrays.asList(wMilestoneWrapperArr));
                                        for (int i5 = 0; i5 < routeMilestones.length; i5++) {
                                            StringBuilder sb5 = new StringBuilder("Milestone[");
                                            sb5.append(i5);
                                            sb5.append("] = ");
                                            sb5.append(routeMilestones[i5].mile);
                                            eao.a((String) "wtbt", sb5.toString());
                                            if (i5 == 0 && routeMilestones[i5].mile > 1000) {
                                                StringBuilder sb6 = new StringBuilder("parse wtbt data. len = ");
                                                sb6.append(bArr2.length);
                                                sb6.append(",hex=");
                                                sb6.append(agx.a(bArr));
                                                eao.a((String) "wtbt", sb6.toString());
                                            }
                                        }
                                    }
                                    WPoint[] routeSearchPoints = this.mWTbt.getRouteSearchPoints();
                                    if (routeSearchPoints != null) {
                                        onFootNaviPath.mRarefyPoints = new ArrayList<>();
                                        int length = routeSearchPoints.length;
                                        int i6 = 0;
                                        while (i6 < length) {
                                            WPoint wPoint = routeSearchPoints[i6];
                                            onFootNaviPath.mRarefyPoints.add(new GeoPoint((double) wPoint.x, (double) wPoint.y));
                                            i6++;
                                            i3 = i3;
                                        }
                                    }
                                    i = i3;
                                    onFootNaviResult = onFootNaviResult3;
                                    onFootNaviResult.mOnFootNaviPath[i] = onFootNaviPath;
                                    i3 = i + 1;
                                    onFootNaviResult2 = onFootNaviResult;
                                    c = 0;
                                    c2 = 1;
                                    i2 = 2;
                                }
                            }
                        }
                        onFootNaviResult = onFootNaviResult2;
                        i = i3;
                        i3 = i + 1;
                        onFootNaviResult2 = onFootNaviResult;
                        c = 0;
                        c2 = 1;
                        i2 = 2;
                    }
                    this.mFootRouteResult = onFootNaviResult2;
                    this.isParseSuccess = true;
                    if (this.mWTbt != null) {
                        this.mWTbt.destroy();
                        this.mWTbt = null;
                    }
                    return true;
                }
            }
            this.mWTbt.destroy();
            this.mWTbt = null;
            return false;
        } catch (NullPointerException e) {
            NullPointerException nullPointerException = e;
            nullPointerException.printStackTrace();
            kf.a((Throwable) nullPointerException);
            if (this.mWTbt != null) {
                this.mWTbt.destroy();
                this.mWTbt = null;
            }
            return false;
        } catch (Exception e2) {
            Exception exc = e2;
            exc.printStackTrace();
            kf.a((Throwable) exc);
            if (this.mWTbt != null) {
                this.mWTbt.destroy();
                this.mWTbt = null;
            }
            return false;
        }
    }

    private boolean parseRecommendData(WTBTDecoder wTBTDecoder) {
        if (wTBTDecoder == null || wTBTDecoder.getSegNum() == 0) {
            return false;
        }
        this.mRecommendLine = new efb();
        ArrayList<GeoPoint> allPoints = getAllPoints(wTBTDecoder);
        this.mRecommendLine.a = (GeoPoint[]) allPoints.toArray(new GeoPoint[allPoints.size()]);
        this.mRecommendLine.b = this.mWTbt.getRouteTravelTime();
        this.mRecommendLine.c = this.mWTbt.getRouteLength();
        this.isParseSuccess = true;
        if (this.mWTbt != null) {
            this.mWTbt.destroy();
            this.mWTbt = null;
        }
        return true;
    }

    private ArrayList<GeoPoint> getAllPoints(WTBTDecoder wTBTDecoder) {
        ArrayList<GeoPoint> arrayList = new ArrayList<>();
        int segNum = wTBTDecoder.getSegNum();
        for (int i = 0; i < segNum; i++) {
            double[] segCoor = this.mWTbt.getSegCoor(i);
            if (segCoor != null && segCoor.length > 1) {
                int length = segCoor.length / 2;
                for (int i2 = 0; i2 < length; i2++) {
                    int i3 = i2 * 2;
                    Point a = cfg.a(segCoor[i3 + 1], segCoor[i3]);
                    arrayList.add(new GeoPoint(a.x, a.y));
                }
            }
        }
        return arrayList;
    }

    public String getKey() {
        return this.mKey;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public byte[] getRouteData() {
        return this.routeData;
    }

    public boolean isParseOK() {
        if (this.mFootRouteResult == null || this.mFootRouteResult.mOnFootNaviPath == null || this.mFootRouteResult.mOnFootNaviPath.length <= 0) {
            return this.isParseSuccess;
        }
        return true;
    }

    public String getNaviid() {
        return this.mNaviid;
    }
}
