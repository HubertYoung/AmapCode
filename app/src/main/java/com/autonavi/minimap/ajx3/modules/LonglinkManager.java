package com.autonavi.minimap.ajx3.modules;

import com.amap.bundle.mqtt.MQTTBizType;
import com.amap.bundle.mqtt.MQTTConnectionStauts;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class LonglinkManager {
    private static ConcurrentHashMap<Integer, AjxMQTTBiz> sAjxMQTTBiz = new ConcurrentHashMap<>();

    public static class AjxMQTTBiz implements yf {
        private yg ajxIMQTTBizController;
        private int biz;
        private ConcurrentHashMap<Long, JsFunctionCallback> hashMap;

        private AjxMQTTBiz(int i) {
            this.biz = i;
            this.hashMap = new ConcurrentHashMap<>();
        }

        public void setAjxIMQTTBizController(yg ygVar) {
            this.ajxIMQTTBizController = ygVar;
        }

        public yg getAjxIMQTTBizController() {
            return this.ajxIMQTTBizController;
        }

        public void addListener(long j, JsFunctionCallback jsFunctionCallback) {
            if (jsFunctionCallback != null) {
                this.hashMap.put(Long.valueOf(j), jsFunctionCallback);
            }
        }

        public boolean removeListener(long j) {
            this.hashMap.remove(Long.valueOf(j));
            return this.hashMap.isEmpty();
        }

        private void notifyJS(Object... objArr) {
            for (Entry<Long, JsFunctionCallback> value : this.hashMap.entrySet()) {
                JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) value.getValue();
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(objArr);
                }
            }
        }

        public MQTTBizType getMQTTBizType() {
            return MQTTBizType.valueOf(this.biz);
        }

        public void onConnectionStatusChanged(MQTTConnectionStauts mQTTConnectionStauts) {
            if (mQTTConnectionStauts == MQTTConnectionStauts.DISCONNECTED) {
                notifyJS("status", Integer.valueOf(0));
                return;
            }
            if (mQTTConnectionStauts == MQTTConnectionStauts.CONNECTED) {
                notifyJS("status", Integer.valueOf(2));
            }
        }

        public void onMessageArrived(JSONObject jSONObject) {
            Object[] objArr = new Object[2];
            objArr[0] = ModuleLongLinkService.CALLBACK_KEY_ARRIVED;
            objArr[1] = jSONObject != null ? jSONObject.toString() : null;
            notifyJS(objArr);
        }

        public void onResponse(int i, JSONObject jSONObject) {
            notifyJS(ModuleLongLinkService.CALLBACK_KEY_RESPONSE, Integer.valueOf(i), jSONObject);
        }
    }

    public static void register(long j, int i, JsFunctionCallback jsFunctionCallback) {
        AjxMQTTBiz ajxMQTTBiz = sAjxMQTTBiz.get(Integer.valueOf(i));
        if (ajxMQTTBiz == null) {
            ajxMQTTBiz = new AjxMQTTBiz(i);
            sAjxMQTTBiz.put(Integer.valueOf(i), ajxMQTTBiz);
            ajxMQTTBiz.setAjxIMQTTBizController(yh.a((yf) ajxMQTTBiz));
        }
        yg ajxIMQTTBizController = ajxMQTTBiz.getAjxIMQTTBizController();
        if (ajxIMQTTBizController != null) {
            MQTTConnectionStauts a = ajxIMQTTBizController.a();
            if (a == MQTTConnectionStauts.DISCONNECTED) {
                jsFunctionCallback.callback("status", Integer.valueOf(0));
            } else if (a == MQTTConnectionStauts.CONNECTED) {
                jsFunctionCallback.callback("status", Integer.valueOf(2));
            }
        }
        ajxMQTTBiz.addListener(j, jsFunctionCallback);
    }

    public static void disconnect(long j, int i) {
        AjxMQTTBiz ajxMQTTBiz = sAjxMQTTBiz.get(Integer.valueOf(i));
        if (ajxMQTTBiz != null && ajxMQTTBiz.removeListener(j)) {
            sAjxMQTTBiz.remove(Integer.valueOf(i));
            yh.a(MQTTBizType.valueOf(i));
        }
    }

    public static void send(int i, JSONObject jSONObject, boolean z) {
        AjxMQTTBiz ajxMQTTBiz = sAjxMQTTBiz.get(Integer.valueOf(i));
        if (ajxMQTTBiz != null) {
            yg ajxIMQTTBizController = ajxMQTTBiz.getAjxIMQTTBizController();
            if (ajxIMQTTBizController != null) {
                ajxIMQTTBizController.a(jSONObject, z);
            }
        }
    }

    public static void clear(long j) {
        for (Entry<Integer, AjxMQTTBiz> key : sAjxMQTTBiz.entrySet()) {
            disconnect(j, ((Integer) key.getKey()).intValue());
        }
    }
}
