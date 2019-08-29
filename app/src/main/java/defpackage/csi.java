package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.ReportType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: csi reason: default package */
/* compiled from: TrafficReportLabelMap */
public final class csi {
    private static csi b;
    private static final Object c = new Object();
    public HashMap<ReportType, List<csj>> a = new HashMap<>();

    public static csi a() {
        if (b == null) {
            synchronized (c) {
                try {
                    if (b == null) {
                        b = new csi();
                    }
                }
            }
        }
        return b;
    }

    private csi() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new csj(101001, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_101001)));
        arrayList.add(new csj(101002, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_101002)));
        arrayList.add(new csj(101003, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_101003)));
        this.a.put(ReportType.ACCIDENT, arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new csj(302001, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_302001)));
        arrayList2.add(new csj(302002, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_302002)));
        arrayList2.add(new csj(302003, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_302003)));
        this.a.put(ReportType.STOP, arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new csj(201001, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_201001)));
        arrayList3.add(new csj(201002, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_201002)));
        arrayList3.add(new csj(201003, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_201003)));
        this.a.put(ReportType.PROCESS, arrayList3);
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(new csj(4001, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_4001)));
        arrayList4.add(new csj(4002, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_4002)));
        arrayList4.add(new csj(4003, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_4003)));
        this.a.put(ReportType.CONGESTION, arrayList4);
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(new csj(501001, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_501001)));
        arrayList5.add(new csj(501002, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_501002)));
        arrayList5.add(new csj(501003, AMapAppGlobal.getApplication().getString(R.string.traffic_report_label_501003)));
        this.a.put(ReportType.PONDING, arrayList5);
    }
}
