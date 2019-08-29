package defpackage;

import com.alibaba.mtl.appmonitor.AppMonitor;
import com.alibaba.mtl.appmonitor.AppMonitor.Stat;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: ffw reason: default package */
/* compiled from: UploadStatAppMonitorImpl */
public final class ffw implements ffv {
    private static boolean a = false;

    public ffw() {
        try {
            Class.forName("com.alibaba.mtl.appmonitor.AppMonitor");
            a = true;
        } catch (Throwable unused) {
            if (TBSdkLog.a(LogEnable.ErrorEnable)) {
                TBSdkLog.d("mtopsdk.UploadStatImpl", "didn't find app-monitor-sdk or ut-analytics sdk.");
            }
        }
    }

    public final void a(String str, String str2, Set<String> set, Set<String> set2) {
        if (a) {
            try {
                AppMonitor.register(str, str2, set2 != null ? MeasureSet.create((Collection<String>) set2) : null, DimensionSet.create((Collection<String>) set), false);
            } catch (Throwable th) {
                if (TBSdkLog.a(LogEnable.ErrorEnable)) {
                    TBSdkLog.b((String) "mtopsdk.UploadStatImpl", (String) "call AppMonitor.register error.", th);
                }
            }
        }
    }

    public final void a(String str, String str2, Map<String, String> map, Map<String, Double> map2) {
        if (a) {
            MeasureValueSet measureValueSet = null;
            try {
                DimensionValueSet create = DimensionValueSet.create();
                create.setMap(map);
                if (map2 != null) {
                    measureValueSet = MeasureValueSet.create();
                    for (Entry next : map2.entrySet()) {
                        measureValueSet.setValue((String) next.getKey(), ((Double) next.getValue()).doubleValue());
                    }
                }
                Stat.commit(str, str2, create, measureValueSet);
            } catch (Throwable th) {
                if (TBSdkLog.a(LogEnable.ErrorEnable)) {
                    TBSdkLog.b((String) "mtopsdk.UploadStatImpl", (String) "call AppMonitor.onCommit error.", th);
                }
            }
        }
    }
}
