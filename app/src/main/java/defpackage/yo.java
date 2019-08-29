package defpackage;

import com.amap.bundle.mqtt.MQTTBizType;
import com.amap.bundle.mqtt.MQTTConnectionStauts;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.json.JSONObject;

/* renamed from: yo reason: default package */
/* compiled from: MQTTProtoDispatcher */
public final class yo {
    public LinkedHashMap<MQTTBizType, ym> a = new LinkedHashMap<>();

    /* renamed from: yo$a */
    /* compiled from: MQTTProtoDispatcher */
    interface a {
        void a(int i, ym ymVar);
    }

    public final void a(final MQTTConnectionStauts mQTTConnectionStauts) {
        a((a) new a() {
            public final void a(int i, ym ymVar) {
                if (ymVar != null) {
                    ymVar.a(mQTTConnectionStauts);
                }
            }
        });
    }

    public final void a(final int i, final JSONObject jSONObject) {
        a((a) new a() {
            public final void a(int i, ym ymVar) {
                if (ymVar != null && i == i) {
                    ymVar.a(jSONObject);
                }
            }
        });
    }

    public final void a(final int i, final int i2, final JSONObject jSONObject) {
        a((a) new a() {
            public final void a(int i, ym ymVar) {
                if (ymVar != null && i == i) {
                    ymVar.a(i2, jSONObject);
                }
            }
        });
    }

    private void a(a aVar) {
        for (Entry entry : new LinkedHashMap(this.a).entrySet()) {
            aVar.a(((MQTTBizType) entry.getKey()).value(), (ym) entry.getValue());
        }
    }
}
