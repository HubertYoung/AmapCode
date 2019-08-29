package com.alibaba.baichuan.android.trade.adapter.ut.performance;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;

public class Point4ShowH5 extends PagePerformancePoint {
    private static final String c = "Point4ShowH5";
    public long allTime;
    public long analysisTime;
    public long taokeTime;
    public long urlHandleTime;
    public long urlLoadTime;

    public static MeasureSet getMeasureSet() {
        return MeasureSet.create().addMeasure((String) UserTrackerConstants.PM_ANALYSIS_TIME).addMeasure((String) UserTrackerConstants.PM_TAOKE_TIME).addMeasure((String) UserTrackerConstants.PM_URL_HANDLE_TIME).addMeasure((String) UserTrackerConstants.PM_URL_LOAD_TIME).addMeasure((String) UserTrackerConstants.PM_ALL_TIME);
    }

    public boolean checkData() {
        return checkTime(this.analysisTime) && checkTime(this.taokeTime) && checkTime(this.urlHandleTime) && checkTime(this.urlLoadTime) && checkTime(this.allTime);
    }

    public MeasureValueSet getMeasureValues() {
        return MeasureValueSet.create().setValue((String) UserTrackerConstants.PM_ANALYSIS_TIME, (double) this.analysisTime).setValue((String) UserTrackerConstants.PM_TAOKE_TIME, (double) this.taokeTime).setValue((String) UserTrackerConstants.PM_URL_HANDLE_TIME, (double) this.urlHandleTime).setValue((String) UserTrackerConstants.PM_URL_LOAD_TIME, (double) this.urlLoadTime).setValue((String) UserTrackerConstants.PM_ALL_TIME, (double) this.allTime);
    }

    public String getMonitorPoint() {
        return UserTrackerConstants.P_SHOWH5;
    }

    public void timeBegin(String str) {
        if (str == null) {
            a.a(c, "timeBegin", "type is null");
            AlibcLogger.e(c, "timeBegin:type is null");
            return;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1748292663:
                if (str.equals(UserTrackerConstants.PM_ANALYSIS_TIME)) {
                    c2 = 0;
                    break;
                }
                break;
            case -912160754:
                if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
                    c2 = 4;
                    break;
                }
                break;
            case -738921630:
                if (str.equals(UserTrackerConstants.PM_URL_LOAD_TIME)) {
                    c2 = 3;
                    break;
                }
                break;
            case 750896100:
                if (str.equals(UserTrackerConstants.PM_URL_HANDLE_TIME)) {
                    c2 = 2;
                    break;
                }
                break;
            case 930452841:
                if (str.equals(UserTrackerConstants.PM_TAOKE_TIME)) {
                    c2 = 1;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                this.analysisTime = System.currentTimeMillis();
                return;
            case 1:
                this.taokeTime = System.currentTimeMillis();
                return;
            case 2:
                this.urlHandleTime = System.currentTimeMillis();
                return;
            case 3:
                this.urlLoadTime = System.currentTimeMillis();
                return;
            case 4:
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
        switch (str.hashCode()) {
            case -1748292663:
                if (str.equals(UserTrackerConstants.PM_ANALYSIS_TIME)) {
                    c2 = 0;
                    break;
                }
                break;
            case -912160754:
                if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
                    c2 = 4;
                    break;
                }
                break;
            case -738921630:
                if (str.equals(UserTrackerConstants.PM_URL_LOAD_TIME)) {
                    c2 = 3;
                    break;
                }
                break;
            case 750896100:
                if (str.equals(UserTrackerConstants.PM_URL_HANDLE_TIME)) {
                    c2 = 2;
                    break;
                }
                break;
            case 930452841:
                if (str.equals(UserTrackerConstants.PM_TAOKE_TIME)) {
                    c2 = 1;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                this.analysisTime = System.currentTimeMillis() - this.analysisTime;
                return;
            case 1:
                this.taokeTime = System.currentTimeMillis() - this.taokeTime;
                return;
            case 2:
                this.urlHandleTime = System.currentTimeMillis() - this.urlHandleTime;
                return;
            case 3:
                this.urlLoadTime = System.currentTimeMillis() - this.urlLoadTime;
                return;
            case 4:
                this.allTime = System.currentTimeMillis() - this.allTime;
                return;
            default:
                a.a(c, "timeEnd", "type is not right");
                AlibcLogger.e(c, "timeEnd:type is not right");
                return;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Point4ShowH5{analysisTime=");
        sb.append(this.analysisTime);
        sb.append(", taokeTime=");
        sb.append(this.taokeTime);
        sb.append(", urlHandleTime=");
        sb.append(this.urlHandleTime);
        sb.append(", urlLoadTime=");
        sb.append(this.urlLoadTime);
        sb.append(", allTime=");
        sb.append(this.allTime);
        sb.append('}');
        return sb.toString();
    }
}
