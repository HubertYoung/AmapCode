package com.autonavi.bundle.routecommon.entity;

import android.content.res.Resources;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.Serializable;
import java.util.ArrayList;

public class BusPath implements axb, Serializable {
    private static final long serialVersionUID = -5458892500051865036L;
    public int busindex;
    public int endfoottime;
    public WalkPath endwalk;
    public int etastatus;
    public double expense;
    public int expenseTime;
    public boolean isExtBusStartCityPath;
    public boolean isRidePath;
    public int mAllFootLength;
    public a[] mBusTags;
    public int mDataLength;
    public BusDisplayObj mEndObj;
    public int mEndWalkLength;
    public int mFailTimes;
    public boolean mHadData;
    public Trip mNearTrip;
    public BusPathSection[] mPathSections;
    public int mRealTimeStatus;
    public int mSectionNum;
    public BusDisplayObj mStartObj;
    public int mStartWalkLength;
    public int mTotalLength;
    public int mendX;
    public int mendY;
    public int min_tag;
    public int mstartX;
    public int mstartY;
    public long reqStartTime;
    public String risk_time_des;
    public TaxiBusPath taxiBusPath;
    public int taxi_price;
    public int taxt_time;
    public int time_tag;
    public String time_tag_des;
    public int totaldriverlength;

    public static class BusDisplayObj implements Serializable {
        private static final long serialVersionUID = 1;
        public String mDisName = null;
        public int mDisType = 0;
        public int mDisX = 0;
        public int mDisY = 0;
        public String mPOI = null;
    }

    public static class TaxiBusPath implements Serializable {
        private static final long serialVersionUID = 4050082827863743048L;
        public String color;
        public boolean isStart;
        public int mCost;
        public int mDriveLength;
        public int mDriveTime;
        public String mEndName;
        public String mStartName;
        public int[] mXs;
        public int[] mYs;
        public int mendX;
        public int mendY;
        public int mstartX;
        public int mstartY;
    }

    public static class WalkPath implements Serializable {
        private static final long serialVersionUID = 6074615824429865020L;
        public int dir;
        public ArrayList<OnFootNaviSection> infolist;
    }

    public static class a {
        public final String a;
        public final String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    public static boolean isBus(int i) {
        return i == 1 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 16 || i == 17 || i == 18;
    }

    public static boolean isSubway(int i) {
        return i == 2 || i == 3 || i == 10;
    }

    private String getExpenseTimeStr() {
        int i = this.expenseTime;
        if (this.taxiBusPath != null && this.taxiBusPath.mDriveTime > 0) {
            i += this.taxiBusPath.mDriveTime;
        }
        return TextUtils.isEmpty(axt.a(i, false)) ? "" : axt.a(i, false);
    }

    private String getMainDes() {
        StringBuffer stringBuffer = new StringBuffer();
        Resources resources = AMapAppGlobal.getApplication().getResources();
        try {
            if (this.taxiBusPath != null && this.taxiBusPath.isStart) {
                if (this.taxiBusPath.mCost > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(resources.getString(R.string.taxi));
                    sb.append("(");
                    sb.append(resources.getString(R.string.approx));
                    sb.append(this.taxiBusPath.mCost);
                    sb.append(resources.getString(R.string.rmb));
                    sb.append(")>");
                    stringBuffer.append(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(resources.getString(R.string.taxi));
                    sb2.append(SimpleComparison.GREATER_THAN_OPERATION);
                    stringBuffer.append(sb2.toString());
                }
            }
            for (int i = 0; i < this.mSectionNum; i++) {
                if (this.mPathSections[i] != null && !TextUtils.isEmpty(this.mPathSections[i].mExactSectionName)) {
                    String str = this.mPathSections[i].mExactSectionName;
                    stringBuffer.append(str);
                    stringBuffer.append(getSectionAlertListName(false, str, this.mPathSections[i].alter_list));
                    if (this.mSectionNum > 1 && i < this.mSectionNum - 1) {
                        stringBuffer.append(SimpleComparison.GREATER_THAN_OPERATION);
                    }
                }
            }
            if (this.taxiBusPath != null && !this.taxiBusPath.isStart) {
                if (this.taxiBusPath.mCost > 0) {
                    StringBuilder sb3 = new StringBuilder(SimpleComparison.GREATER_THAN_OPERATION);
                    sb3.append(resources.getString(R.string.taxi));
                    sb3.append("(");
                    sb3.append(resources.getString(R.string.approx));
                    sb3.append(this.taxiBusPath.mCost);
                    sb3.append(resources.getString(R.string.rmb));
                    sb3.append(")");
                    stringBuffer.append(sb3.toString());
                } else {
                    StringBuilder sb4 = new StringBuilder(SimpleComparison.GREATER_THAN_OPERATION);
                    sb4.append(resources.getString(R.string.taxi));
                    stringBuffer.append(sb4.toString());
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return stringBuffer.toString();
    }

    private String getSectionAlertListName(boolean z, String str, BusPathSection[] busPathSectionArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (busPathSectionArr != null) {
            try {
                int length = busPathSectionArr.length;
                ArrayList arrayList = new ArrayList();
                int i = 0;
                while (i < length && (z || i < 2)) {
                    String dealName = dealName(busPathSectionArr[i].mSectionName);
                    if (!TextUtils.isEmpty(dealName) && !arrayList.contains(dealName)) {
                        stringBuffer.append("/".concat(String.valueOf(dealName)));
                        arrayList.add(dealName);
                    }
                    i++;
                }
                if (!z && length > 2) {
                    stringBuffer.append(AMapAppGlobal.getApplication().getString(R.string.etc));
                    return stringBuffer.toString();
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        return stringBuffer.toString();
    }

    private String dealName(String str) {
        if (str == null) {
            return "";
        }
        int indexOf = str.indexOf(40);
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        return str;
    }

    public String getDestDesc() {
        if (this.taxiBusPath == null || this.taxiBusPath.isStart) {
            return this.mPathSections[this.mPathSections.length - 1].mEndName;
        }
        return this.taxiBusPath.mEndName;
    }

    public String getCostDesc() {
        StringBuilder sb = new StringBuilder();
        sb.append(cfe.a(this.mTotalLength));
        sb.append("/");
        sb.append(getExpenseTimeStr());
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("(");
        sb3.append(sb2);
        sb3.append(")");
        return sb3.toString();
    }

    public String getPathDesc() {
        return getMainDes();
    }

    public int getPathIcon() {
        return R.drawable.direction_bus_list_bus;
    }

    public String getStartDesc() {
        if (this.taxiBusPath == null || !this.taxiBusPath.isStart) {
            return this.mPathSections[0].mStartName;
        }
        return this.taxiBusPath.mStartName;
    }

    public int getCostTime() {
        return this.expenseTime;
    }

    public int getCostDistance() {
        return this.mTotalLength;
    }

    public double getCostFee() {
        return this.expense;
    }

    public String getStartEndName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStartDesc());
        sb.append("→");
        sb.append(getDestDesc());
        return sb.toString();
    }
}
