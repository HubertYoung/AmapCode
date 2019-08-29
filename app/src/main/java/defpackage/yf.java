package defpackage;

import com.amap.bundle.mqtt.MQTTBizType;
import com.amap.bundle.mqtt.MQTTConnectionStauts;
import org.json.JSONObject;

/* renamed from: yf reason: default package */
/* compiled from: IMQTTBiz */
public interface yf {
    MQTTBizType getMQTTBizType();

    void onConnectionStatusChanged(MQTTConnectionStauts mQTTConnectionStauts);

    void onMessageArrived(JSONObject jSONObject);

    void onResponse(int i, JSONObject jSONObject);
}
