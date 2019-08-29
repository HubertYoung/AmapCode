package com.alibaba.baichuan.android.trade.adapter.ut.performance;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;

public class Point4UrlLoad extends PerformancePoint {
    private static final String c = "Point4UrlLoad";
    public long allTime;
    public long urlHandleTime;
    public long urlLoadTime;

    public static MeasureSet getMeasureSet() {
        return MeasureSet.create().addMeasure((String) UserTrackerConstants.PM_URL_HANDLE_TIME).addMeasure((String) UserTrackerConstants.PM_URL_LOAD_TIME).addMeasure((String) UserTrackerConstants.PM_ALL_TIME);
    }

    public boolean checkData() {
        return checkTime(this.urlHandleTime) && checkTime(this.urlLoadTime) && checkTime(this.allTime);
    }

    public MeasureValueSet getMeasureValues() {
        return MeasureValueSet.create().setValue((String) UserTrackerConstants.PM_URL_HANDLE_TIME, (double) this.urlHandleTime).setValue((String) UserTrackerConstants.PM_URL_LOAD_TIME, (double) this.urlLoadTime).setValue((String) UserTrackerConstants.PM_ALL_TIME, (double) this.allTime);
    }

    public String getMonitorPoint() {
        return UserTrackerConstants.P_URLLOAD;
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
            if (hashCode != -738921630) {
                if (hashCode == 750896100 && str.equals(UserTrackerConstants.PM_URL_HANDLE_TIME)) {
                    c2 = 0;
                }
            } else if (str.equals(UserTrackerConstants.PM_URL_LOAD_TIME)) {
                c2 = 1;
            }
        } else if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
            c2 = 2;
        }
        switch (c2) {
            case 0:
                this.urlHandleTime = System.currentTimeMillis();
                return;
            case 1:
                this.urlLoadTime = System.currentTimeMillis();
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
            if (hashCode != -738921630) {
                if (hashCode == 750896100 && str.equals(UserTrackerConstants.PM_URL_HANDLE_TIME)) {
                    c2 = 0;
                }
            } else if (str.equals(UserTrackerConstants.PM_URL_LOAD_TIME)) {
                c2 = 1;
            }
        } else if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
            c2 = 2;
        }
        switch (c2) {
            case 0:
                this.urlHandleTime = System.currentTimeMillis() - this.urlHandleTime;
                return;
            case 1:
                this.urlLoadTime = System.currentTimeMillis() - this.urlLoadTime;
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
        StringBuilder sb = new StringBuilder("Point4UrlLoad{urlHandleTime=");
        sb.append(this.urlHandleTime);
        sb.append(", urlLoadTime=");
        sb.append(this.urlLoadTime);
        sb.append('}');
        return sb.toString();
    }
}
