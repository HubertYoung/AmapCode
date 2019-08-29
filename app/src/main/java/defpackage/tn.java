package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: tn reason: default package */
/* compiled from: RouteLifecycleMonitor */
public final class tn {
    private static final Handler f = new Handler(Looper.getMainLooper());
    private static final ahn g = new ahn(1);
    public final Map<CalcRouteScene, List<CalcRouteResult>> a = new HashMap();
    public final Map<Long, CalcRouteResult> b = new HashMap();
    public final SparseIntArray c = new SparseIntArray();
    private final Map<CalcRouteScene, CalcRouteResult> d = new HashMap();
    private final Map<Long, Object> e = new HashMap();

    /* renamed from: tn$a */
    /* compiled from: RouteLifecycleMonitor */
    static class a {
        /* access modifiers changed from: private */
        public static tn a = new tn();
    }

    public static tn a() {
        return a.a;
    }

    public final void a(CalcRouteScene calcRouteScene, CalcRouteResult calcRouteResult) {
        if (!calcRouteScene.isMultiRouteCachePlan()) {
            b(calcRouteScene, calcRouteResult);
        } else {
            c(calcRouteScene, calcRouteResult);
        }
    }

    public final void a(CalcRouteScene calcRouteScene) {
        if (!calcRouteScene.isMultiRouteCachePlan()) {
            b(calcRouteScene);
        } else {
            c(calcRouteScene);
        }
    }

    private void b(CalcRouteScene calcRouteScene, CalcRouteResult calcRouteResult) {
        if (calcRouteResult != null) {
            CalcRouteResult calcRouteResult2 = this.d.get(calcRouteScene);
            if (!(calcRouteResult2 == null || calcRouteResult2 == calcRouteResult)) {
                b(calcRouteResult2);
                a(calcRouteResult2);
            }
            this.d.put(calcRouteScene, calcRouteResult);
            c(calcRouteResult);
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("cacheRouteResult|");
            sb.append(calcRouteResult.toString());
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(calcRouteResult.getPathCount());
            a2.c("RouteLifecycleMonitor", sb.toString());
        }
    }

    private void b(CalcRouteScene calcRouteScene) {
        CalcRouteResult remove = this.d.remove(calcRouteScene);
        if (remove != null) {
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("destroyRouteResult|");
            sb.append(remove.toString());
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(remove.getPathCount());
            a2.c("RouteLifecycleMonitor", sb.toString());
            b(remove);
            a(remove);
        }
    }

    private void c(CalcRouteScene calcRouteScene, CalcRouteResult calcRouteResult) {
        if (calcRouteResult != null) {
            if (this.a.get(calcRouteScene) == null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(calcRouteResult);
                this.a.put(calcRouteScene, arrayList);
            } else {
                this.a.get(calcRouteScene).add(calcRouteResult);
            }
            c(calcRouteResult);
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("cacheRouteResult|");
            sb.append(calcRouteResult.toString());
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(calcRouteResult.getPathCount());
            a2.c("RouteLifecycleMonitor", sb.toString());
        }
    }

    private void c(CalcRouteScene calcRouteScene) {
        List<CalcRouteResult> list = this.a.get(calcRouteScene);
        if (list != null && list.size() > 0) {
            for (CalcRouteResult calcRouteResult : list) {
                b(calcRouteResult);
                a(calcRouteResult);
            }
            list.clear();
        }
    }

    public static void a(@NonNull CalcRouteResult calcRouteResult) {
        if (calcRouteResult != null) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("result ptr=");
                sb.append(calcRouteResult.getPtr());
                sb.append("---hashcode=");
                sb.append(calcRouteResult.hashCode());
                tq.a("RouteTrace", "destoryNativeRoute", "destoryNativeRoute", sb.toString());
            }
            calcRouteResult.destroy();
        }
    }

    public static boolean a(ICarRouteResult iCarRouteResult) {
        if (iCarRouteResult == null) {
            return false;
        }
        CalcRouteResult calcRouteResult = iCarRouteResult.getCalcRouteResult();
        if (calcRouteResult != null && calcRouteResult.mResultInfo != null && calcRouteResult.mResultInfo.containsKey("valid") && ((Boolean) calcRouteResult.mResultInfo.get("valid")).booleanValue()) {
            return true;
        }
        return false;
    }

    private void c(CalcRouteResult calcRouteResult) {
        if (calcRouteResult != null) {
            if (calcRouteResult.mResultInfo == null) {
                calcRouteResult.mResultInfo = new HashMap();
            }
            calcRouteResult.mResultInfo.put("valid", Boolean.TRUE);
            for (int i = 0; i < calcRouteResult.getPathCount(); i++) {
                this.b.put(Long.valueOf(calcRouteResult.getRoute(i).getRouteId()), calcRouteResult);
            }
        }
    }

    public final void b(CalcRouteResult calcRouteResult) {
        if (calcRouteResult != null) {
            if (calcRouteResult.mResultInfo != null) {
                calcRouteResult.mResultInfo.remove("valid");
            }
            for (int i = 0; i < calcRouteResult.getPathCount(); i++) {
                this.b.remove(Long.valueOf(calcRouteResult.getRoute(i).getRouteId()));
            }
        }
    }
}
