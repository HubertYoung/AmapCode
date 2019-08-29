package com.autonavi.minimap.basemap.traffic.bean;

import android.text.TextUtils;
import com.autonavi.minimap.basemap.traffic.ReportType;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;

public final class TwiceReportType {
    public ReportType a;
    public int b = 1;
    public int c = 0;
    @Type
    public int d;

    public @interface Type {
    }

    private TwiceReportType() {
    }

    public static TwiceReportType a(TrafficTopic trafficTopic, @Type int i) {
        int i2;
        String valueOf = String.valueOf(trafficTopic.getLayerTag());
        ReportType[] values = ReportType.values();
        ReportType reportType = ReportType.INVALID;
        int length = values.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            ReportType reportType2 = values[i3];
            if (TextUtils.equals(valueOf, reportType2.layerTag)) {
                reportType = reportType2;
                break;
            }
            i3++;
        }
        TwiceReportType twiceReportType = new TwiceReportType();
        twiceReportType.a = reportType;
        twiceReportType.c = trafficTopic.getId();
        twiceReportType.d = i;
        switch (i) {
            case 1:
                i2 = 4;
                break;
            case 2:
            case 3:
                i2 = 5;
                break;
            default:
                i2 = 1;
                break;
        }
        twiceReportType.b = i2;
        return twiceReportType;
    }
}
