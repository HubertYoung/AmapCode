package com.alibaba.baichuan.android.trade.adapter.ut.performance;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;

public class Point4ShowNative extends PagePerformancePoint {
    private static final String c = "Point4ShowNative";
    public long allTime;
    public long analysisTime;
    public long goTaobaoTime;

    public static MeasureSet getMeasureSet() {
        return MeasureSet.create().addMeasure((String) UserTrackerConstants.PM_ANALYSIS_TIME).addMeasure((String) UserTrackerConstants.PM_GO_TAOBAO_TIME).addMeasure((String) UserTrackerConstants.PM_ALL_TIME);
    }

    public boolean checkData() {
        return checkTime(this.analysisTime) && checkTime(this.goTaobaoTime) && checkTime(this.allTime);
    }

    public MeasureValueSet getMeasureValues() {
        return MeasureValueSet.create().setValue((String) UserTrackerConstants.PM_ANALYSIS_TIME, (double) this.analysisTime).setValue((String) UserTrackerConstants.PM_GO_TAOBAO_TIME, (double) this.goTaobaoTime).setValue((String) UserTrackerConstants.PM_ALL_TIME, (double) this.allTime);
    }

    public String getMonitorPoint() {
        return UserTrackerConstants.P_SHOWNATIVE;
    }

    public void timeBegin(String str) {
        if (str == null) {
            a.a(c, "timeBegin", "type is null");
            AlibcLogger.e(c, "timeBegin:type is null");
            return;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1748292663) {
            if (hashCode != -912160754) {
                if (hashCode == -197564381 && str.equals(UserTrackerConstants.PM_GO_TAOBAO_TIME)) {
                    c2 = 1;
                }
            } else if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
                c2 = 2;
            }
        } else if (str.equals(UserTrackerConstants.PM_ANALYSIS_TIME)) {
            c2 = 0;
        }
        switch (c2) {
            case 0:
                this.analysisTime = System.currentTimeMillis();
                return;
            case 1:
                this.goTaobaoTime = System.currentTimeMillis();
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
        if (hashCode != -1748292663) {
            if (hashCode != -912160754) {
                if (hashCode == -197564381 && str.equals(UserTrackerConstants.PM_GO_TAOBAO_TIME)) {
                    c2 = 1;
                }
            } else if (str.equals(UserTrackerConstants.PM_ALL_TIME)) {
                c2 = 2;
            }
        } else if (str.equals(UserTrackerConstants.PM_ANALYSIS_TIME)) {
            c2 = 0;
        }
        switch (c2) {
            case 0:
                this.analysisTime = System.currentTimeMillis() - this.analysisTime;
                return;
            case 1:
                this.goTaobaoTime = System.currentTimeMillis() - this.goTaobaoTime;
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
        StringBuilder sb = new StringBuilder("Point4ShowNative{analysisTime=");
        sb.append(this.analysisTime);
        sb.append(", goTaobaoTime=");
        sb.append(this.goTaobaoTime);
        sb.append(", allTime=");
        sb.append(this.allTime);
        sb.append('}');
        return sb.toString();
    }
}
