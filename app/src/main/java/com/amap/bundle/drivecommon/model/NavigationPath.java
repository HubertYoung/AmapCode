package com.amap.bundle.drivecommon.model;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.internal.view.SupportMenu;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.amap.bundle.drivecommon.overlay.RouteCarResultRouteItem;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.route.model.LineIconPoint;
import com.autonavi.jni.ae.route.model.LineItem;
import com.autonavi.jni.ae.route.model.RestAreaInfo;
import com.autonavi.jni.ae.route.model.TDRJamFadeArea;
import com.autonavi.jni.ae.route.model.TipInfo;
import com.autonavi.minimap.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavigationPath implements dia, Serializable {
    public static final int MAX_TMC_DISTANCE = 500000;
    public static final int MIN_TMC_DISTANCE = 100000;
    public static final int RESTRICTED_AREA_AVOID = 1;
    public static final int RESTRICTED_AREA_NOT_AVOID = 0;
    public static final int RESTRICTED_AREA_NO_INFO = -1;
    public static final int RESTRICTED_AREA_OPEN_SWITCH = 3;
    public static final int RESTRICTED_AREA_SET_PLATE = 2;
    public static final int TIP_TYPE_OFFLINE = 0;
    public static final int TIP_TYPE_ONLINE = 1;
    private static final long serialVersionUID = -7726567184192452714L;
    public int[] cityCodes;
    public int isTruckRoute = 0;
    public transient List<RouteCarResultRouteItem> mArrowItemList = new ArrayList();
    public int mCostTime = 0;
    public int mDataLength;
    public transient LineItem mEngineLineItem;
    public transient List<GroupNavigationSection> mGroupNaviSectionList = new ArrayList();
    public boolean mHasShowAvoidJamArea = false;
    public boolean mHasShowDetour = false;
    public boolean mHasShowIncident = false;
    public boolean[] mHasShowIncidentArray = null;
    public boolean mHasShownLimitedPathsInfo = false;
    public int[] mIncidentEventId = null;
    public String mIncidentStr = null;
    public String[] mIncidentStrArray = null;
    public int[] mIncidentTipsTypeArray = null;
    public boolean mIsHolidayFree;
    public transient List<JamInfo> mJamInfo = new ArrayList();
    public int mLimitRoadFlag;
    public ArrayList<RouteCarResultRouteItem> mLineBgOverlayList = new ArrayList<>();
    public LineIconPoint[] mLineIconPoints;
    public ArrayList<RouteCarResultRouteItem> mLineOverlayList = new ArrayList<>();
    public transient LongDistnceSceneData mLongDistnceSceneData;
    public ArrayList<Object> mPathDetailDesItemList = new ArrayList<>();
    public long mPathId;
    public int mPathStrategy = -1;
    public int mPathlength;
    public double[] mRarefyPoints;
    public transient RestAreaInfo[] mRestAreaInfo;
    public transient List<RouteCarResultRouteItem> mRestrictedLineItemList = new ArrayList();
    public transient sq mRestrictionInfo = null;
    public int mRouteAbnormalCount;
    public int mRouteAbnormalState;
    public long mRouteId;
    public transient TipInfo mRouteTip = null;
    public int mSectionNum;
    public NavigationSection[] mSections;
    public ArrayList<GeoPoint> mStackGeoPoint = new ArrayList<>();
    public transient TDRJamFadeArea mTDRJamFadeAreas = null;
    public String mTagName;
    public int mTaxiFee = -1;
    public int mTmcTime;
    public int mTollCost = 0;
    public int mTollLength = 0;
    public int mTrafficNum = 0;

    public boolean isGroupPath() {
        return this.mGroupNaviSectionList != null && this.mGroupNaviSectionList.size() > 0;
    }

    public SpannableString getSubDesSP() {
        StringBuilder sb = new StringBuilder();
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (this.mTrafficNum > 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(resources.getString(R.string.traffic_light));
            sb2.append(this.mTrafficNum);
            sb2.append(resources.getString(R.string.unit));
            sb.append(sb2.toString());
        }
        if (this.mTollCost > 0) {
            if (sb.length() > 0) {
                StringBuilder sb3 = new StringBuilder("  ");
                sb3.append(resources.getString(R.string.road_fee));
                sb3.append(this.mTollCost);
                sb3.append(resources.getString(R.string.rmb));
                sb.append(sb3.toString());
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(resources.getString(R.string.road_fee_approx));
                sb4.append(this.mTollCost);
                sb4.append(resources.getString(R.string.rmb));
                sb.append(sb4.toString());
            }
        }
        if (this.mTaxiFee > 0) {
            if (sb.length() > 0) {
                StringBuilder sb5 = new StringBuilder("  ");
                sb5.append(resources.getString(R.string.taxi_fee));
                sb5.append(this.mTaxiFee);
                sb5.append(resources.getString(R.string.rmb));
                sb.append(sb5.toString());
            } else {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(resources.getString(R.string.taxi_fee));
                sb6.append(this.mTaxiFee);
                sb6.append(resources.getString(R.string.rmb));
                sb.append(sb6.toString());
            }
        }
        if (sb.length() == 0) {
            return new SpannableString("");
        }
        StringBuilder sb7 = new StringBuilder("·");
        sb7.append(sb.toString());
        return new SpannableString(sb7.toString());
    }

    public SpannableString getSubDes4MapResult(boolean z) {
        int i;
        StringBuilder sb = new StringBuilder();
        Resources resources = AMapAppGlobal.getApplication().getResources();
        boolean z2 = false;
        boolean z3 = this.mTollCost > 0;
        boolean z4 = this.mTrafficNum > 0;
        boolean z5 = this.mTaxiFee > 0;
        boolean z6 = z && z3 && ((z4 && !z5) || (!z4 && z5));
        if (z && !z3 && z4 && z5) {
            z2 = true;
        }
        String str = "";
        if (z3) {
            if (sb.length() > 0) {
                str = resources.getString(R.string.road_fee);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(this.mTollCost);
                sb2.append(resources.getString(R.string.rmb));
                sb.append(sb2.toString());
            } else {
                str = resources.getString(R.string.road_fee_approx);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(this.mTollCost);
                sb3.append(resources.getString(R.string.rmb));
                sb.append(sb3.toString());
            }
            if (z6) {
                sb.append("\n");
            }
        }
        int i2 = -1;
        if (z4) {
            if (sb.length() == 0 || z6) {
                i = -1;
            } else {
                i = sb.length() + 1;
                sb.append(" · ");
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(resources.getString(R.string.traffic_light));
            sb4.append(this.mTrafficNum);
            sb4.append(resources.getString(R.string.unit));
            sb.append(sb4.toString());
            if (z2) {
                sb.append("\n");
            }
        } else {
            i = -1;
        }
        if (z5) {
            if (sb.length() <= 0 || z6 || z2) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(resources.getString(R.string.taxi_fee));
                sb5.append(this.mTaxiFee);
                sb5.append(resources.getString(R.string.rmb));
                sb.append(sb5.toString());
            } else {
                i2 = sb.length() + 1;
                StringBuilder sb6 = new StringBuilder(" · ");
                sb6.append(resources.getString(R.string.taxi_fee));
                sb6.append(this.mTaxiFee);
                sb6.append(resources.getString(R.string.rmb));
                sb.append(sb6.toString());
            }
        }
        if (sb.length() == 0) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(sb.toString());
        if (this.mTollCost > 0) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(this.mTollCost);
            spannableString.setSpan(new ForegroundColorSpan(SupportMenu.CATEGORY_MASK), str.length(), str.length() + sb7.toString().length(), 33);
        }
        if (i > 0) {
            spannableString.setSpan(new ForegroundColorSpan(-1381654), i, i + 1, 33);
        }
        if (i2 > 0) {
            spannableString.setSpan(new ForegroundColorSpan(-1381654), i2, i2 + 1, 33);
        }
        return spannableString;
    }

    public SpannableString getSubDes4MapResult2Lines() {
        StringBuilder sb = new StringBuilder();
        Resources resources = AMapAppGlobal.getApplication().getResources();
        String str = "";
        if (this.mTollCost > 0) {
            if (sb.length() > 0) {
                str = resources.getString(R.string.road_fee);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(this.mTollCost);
                sb2.append(resources.getString(R.string.rmb));
                sb.append(sb2.toString());
            } else {
                str = resources.getString(R.string.road_fee_approx);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(this.mTollCost);
                sb3.append(resources.getString(R.string.rmb));
                sb.append(sb3.toString());
            }
        }
        if (this.mTrafficNum > 0) {
            if (sb.length() != 0) {
                sb.append(" · ");
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(resources.getString(R.string.traffic_light));
            sb4.append(this.mTrafficNum);
            sb4.append(resources.getString(R.string.unit));
            sb.append(sb4.toString());
        }
        if (this.mTaxiFee > 0) {
            if (this.mTrafficNum <= 0 || this.mTollCost < 5) {
                StringBuilder sb5 = new StringBuilder(" · ");
                sb5.append(resources.getString(R.string.taxi_fee));
                sb5.append(this.mTaxiFee);
                sb5.append(resources.getString(R.string.rmb));
                sb.append(sb5.toString());
            } else {
                StringBuilder sb6 = new StringBuilder("\n");
                sb6.append(resources.getString(R.string.taxi_fee));
                sb6.append(this.mTaxiFee);
                sb6.append(resources.getString(R.string.rmb));
                sb.append(sb6.toString());
            }
        }
        if (sb.length() == 0) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(sb.toString());
        if (this.mTollCost > 0) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(this.mTollCost);
            spannableString.setSpan(new ForegroundColorSpan(SupportMenu.CATEGORY_MASK), str.length(), str.length() + sb7.toString().length(), 33);
        }
        return spannableString;
    }

    public String getGroupDes() {
        StringBuilder sb = new StringBuilder();
        for (GroupNavigationSection next : this.mGroupNaviSectionList) {
            if (next.m_bIsSrucial) {
                sb.append(next.m_GroupName);
                sb.append("→");
            }
        }
        if (sb.length() > 0) {
            return sb.subSequence(0, sb.length() - 1).toString();
        }
        return null;
    }

    public String getTaxiFeeStr() {
        if (this.mTaxiFee <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("  ");
        sb.append(AMapAppGlobal.getApplication().getString(R.string.taxi_fee));
        sb.append(this.mTaxiFee);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.rmb));
        return sb.toString();
    }

    public SpannableString getTmcTimeDescSP() {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getString(R.string.traffic_avoid_solution));
        sb.append(lf.a(this.mTmcTime * 60));
        SpannableString spannableString = new SpannableString(sb.toString());
        try {
            spannableString.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 255, 255)), 1, 5, 33);
            spannableString.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 255, 255)), 11, sb.length(), 33);
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return spannableString;
    }

    public GeoPoint getLastPoint() {
        if (this.mStackGeoPoint == null || this.mStackGeoPoint.size() <= 0) {
            return null;
        }
        return this.mStackGeoPoint.get(this.mStackGeoPoint.size() - 1);
    }

    public GeoPoint getFirstPoint() {
        if (this.mStackGeoPoint == null || this.mStackGeoPoint.size() <= 0) {
            return null;
        }
        return this.mStackGeoPoint.get(0);
    }

    public List<GeoPoint> getTDRJamFadeAreasGeoPoints() {
        ArrayList arrayList = new ArrayList();
        if (this.mTDRJamFadeAreas != null) {
            int length = this.mTDRJamFadeAreas.coorlist == null ? 0 : this.mTDRJamFadeAreas.coorlist.length;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    int i2 = i * 2;
                    int i3 = i2 + 1;
                    if (i3 < this.mTDRJamFadeAreas.coorlist.length) {
                        arrayList.add(new GeoPoint((((double) this.mTDRJamFadeAreas.coorlist[i2]) / 1000000.0d) / 3.6d, (((double) this.mTDRJamFadeAreas.coorlist[i3]) / 1000000.0d) / 3.6d));
                    }
                }
                return arrayList;
            }
        }
        return null;
    }

    public int checkAvoidRestricted() {
        int i = -1;
        if (this.mRestrictionInfo == null) {
            return -1;
        }
        switch (this.mRestrictionInfo.e) {
            case 0:
                i = 2;
                break;
            case 1:
                i = 3;
                break;
            case 2:
            case 7:
            case 8:
                i = 1;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                i = 0;
                break;
        }
        return i;
    }

    public int getPathStrategy() {
        return this.mPathStrategy;
    }

    public int getPathlength() {
        return this.mPathlength;
    }

    public int getCostTime() {
        return this.mCostTime;
    }

    public boolean hasLinePoints() {
        return (this.mStackGeoPoint == null || this.mStackGeoPoint.size() == 0) ? false : true;
    }

    public Rect getBound() {
        if (this.mStackGeoPoint.size() == 0) {
            return null;
        }
        int i = 0;
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MAX_VALUE;
        int i4 = Integer.MIN_VALUE;
        int i5 = Integer.MIN_VALUE;
        while (i < this.mStackGeoPoint.size()) {
            try {
                GeoPoint geoPoint = this.mStackGeoPoint.get(i);
                i2 = Math.min(i2, geoPoint.x);
                i3 = Math.min(i3, geoPoint.y);
                i4 = Math.max(i4, geoPoint.x);
                i5 = Math.max(i5, geoPoint.y);
                i++;
            } catch (Exception unused) {
                return null;
            }
        }
        Rect rect = new Rect();
        rect.set(i2, i3, i4, i5);
        return rect;
    }
}
