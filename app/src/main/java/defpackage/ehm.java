package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.HealthParamKey;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.IHealthBikeSharing;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;

/* renamed from: ehm reason: default package */
/* compiled from: TrackRecordReverter */
public final class ehm {
    public static synchronized void a(final ecy ecy, final boolean z, final boolean z2) {
        synchronized (ehm.class) {
            AnonymousClass1 r1 = new a() {
                public final void a(HealthPoint[] healthPointArr) {
                    long currentTimeMillis = System.currentTimeMillis();
                    AMapLog.e("TestShare", "start ---> ".concat(String.valueOf(currentTimeMillis)));
                    ehk ehk = new ehk(ecy, currentTimeMillis, z, z2);
                    StringBuilder sb = new StringBuilder("IHealthBikeSharing ---> ");
                    sb.append(System.currentTimeMillis());
                    AMapLog.e("TestShare", sb.toString());
                    IHealthBikeSharing CreateHealthBikeSharing = IHealthBikeSharing.CreateHealthBikeSharing(ehk);
                    boolean a2 = a.a();
                    CreateHealthBikeSharing.SetParam(HealthParamKey.HPK_WORKSPACE, TrackPostUtils.a(TrackType.SHAREBIKE, a2));
                    CreateHealthBikeSharing.SetParam(HealthParamKey.HPK_ENABLE_LOG, a2 ? "1" : "0");
                    CreateHealthBikeSharing.SetGPSArray(healthPointArr);
                    CreateHealthBikeSharing.StopTrace();
                    IHealthBikeSharing.Release(CreateHealthBikeSharing);
                }
            };
            ehl.a(AMapPageUtil.getAppContext()).a();
            ehl.a(AMapPageUtil.getAppContext()).a((a) r1);
        }
    }
}
