package defpackage;

import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatRequest;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses;

/* renamed from: dyc reason: default package */
/* compiled from: RealTimeCallback */
public abstract class dyc<T> {
    private boolean a;

    public abstract void a();

    public abstract void a(T t);

    public final void a(HeartBeatRequest heartBeatRequest, RealtimeBuses realtimeBuses) {
        if (!(realtimeBuses == null || heartBeatRequest == null)) {
            if (realtimeBuses.isContainArrivingBus()) {
                StringBuilder sb = new StringBuilder("---updatePollingTime----10s:");
                sb.append(this.a);
                eao.b(sb.toString());
                if (!this.a) {
                    HeartBeatManager.a().a(heartBeatRequest, 10);
                    this.a = true;
                }
            } else {
                StringBuilder sb2 = new StringBuilder("----updatePollingTime---30s:");
                sb2.append(this.a);
                eao.b(sb2.toString());
                if (this.a) {
                    HeartBeatManager.a().a(heartBeatRequest, 30);
                    this.a = false;
                }
            }
        }
    }
}
