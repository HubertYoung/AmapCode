package com.alibaba.baichuan.android.trade.adapter.ut.performance;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;

public class Point4Init extends PerformancePoint {
    private static final String c = "Point4Init";
    public long allTime;
    public long securityInitTime;
    public long utInitTime;

    public static MeasureSet getMeasureSet() {
        return MeasureSet.create().addMeasure((String) UserTrackerConstants.PM_SECURITY_INIT_TIME).addMeasure((String) UserTrackerConstants.PM_UT_INIT_TIME).addMeasure((String) UserTrackerConstants.PM_ALL_TIME);
    }

    public boolean checkData() {
        return checkTime(this.securityInitTime) && checkTime(this.utInitTime) && checkTime(this.allTime);
    }

    public MeasureValueSet getMeasureValues() {
        return MeasureValueSet.create().setValue((String) UserTrackerConstants.PM_SECURITY_INIT_TIME, (double) this.securityInitTime).setValue((String) UserTrackerConstants.PM_UT_INIT_TIME, (double) this.utInitTime).setValue((String) UserTrackerConstants.PM_ALL_TIME, (double) this.allTime);
    }

    public String getMonitorPoint() {
        return "init";
    }

    public void timeBegin(String str) {
        if (str == null) {
            a.a(c, "timeBegin", "type is null");
            AlibcLogger.e(c, "timeBegin:type is null");
            return;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -912160754) {
            if (hashCode != -395475012) {
                if (hashCode == 2065809245 && str.equals(UserTrackerConstants.PM_SECURITY_INIT_TIME)) {
                    c2 = 0;
                }
            } else if (str.equals(UserTrackerConstants.PM_UT_INIT_TIME)) {
                c2 = 1;
            }
        } else if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
            c2 = 2;
        }
        switch (c2) {
            case 0:
                this.securityInitTime = System.currentTimeMillis();
                return;
            case 1:
                this.utInitTime = System.currentTimeMillis();
                return;
            case 2:
                this.allTime = System.currentTimeMillis();
                return;
            default:
                a.a(c, "timeBegin", "type is not right");
                AlibcLogger.e(c, "timeBegin:type is not right");
                return;
        }
    }

    public void timeEnd(String str) {
        if (str == null) {
            a.a(c, "timeEnd", "type is null");
            AlibcLogger.e(c, "timeEnd:type is null");
            return;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -912160754) {
            if (hashCode != -395475012) {
                if (hashCode == 2065809245 && str.equals(UserTrackerConstants.PM_SECURITY_INIT_TIME)) {
                    c2 = 0;
                }
            } else if (str.equals(UserTrackerConstants.PM_UT_INIT_TIME)) {
                c2 = 1;
            }
        } else if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
            c2 = 2;
        }
        switch (c2) {
            case 0:
                this.securityInitTime = System.currentTimeMillis() - this.securityInitTime;
                return;
            case 1:
                this.utInitTime = System.currentTimeMillis() - this.utInitTime;
                return;
            case 2:
                this.allTime = System.currentTimeMillis() - this.allTime;
                return;
            default:
                a.a(c, "timeEnd", "type is not right");
                AlibcLogger.e(c, "timeEnd:type is not right");
                return;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Point4Init{securityInitTime=");
        sb.append(this.securityInitTime);
        sb.append(", utInitTime=");
        sb.append(this.utInitTime);
        sb.append('}');
        return sb.toString();
    }
}
