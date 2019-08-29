package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mqtt.internal.service.MQTTService;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: yj reason: default package */
/* compiled from: MQTTConnectionAocsConfig */
public final class yj {
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    private int e = 0;

    public final boolean a(String str) {
        boolean z;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject optJSONObject = new JSONObject(str).optJSONObject("mqtt_connection_config");
                if (optJSONObject != null) {
                    this.a = optJSONObject.optInt("reconnect_interval", 0);
                    this.b = optJSONObject.optInt("high_quality_network_ping_interval_android", 0);
                    this.c = optJSONObject.optInt("low_quality_network_ping_interval_android", 0);
                    this.e = optJSONObject.optInt("ping_timeout_limit", 0);
                    this.d = optJSONObject.optInt("proto_response_timeout_limit", 0);
                    z = true;
                } else {
                    z = false;
                }
                MQTTService.a((String) "MQTTConnection. MQTTConnectionAocsConfig. parse complete. reconnectIntervalSecs: %s, highQualityNetworkPingIntervalSecs: %s, lowQualityNetworkPingIntervalSecs: %s, pingTimeoutLimitSecs: %s, protoResponseTimeoutLimitSecs: %s", Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.e), Integer.valueOf(this.d));
                return z;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }
}
