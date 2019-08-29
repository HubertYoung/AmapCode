package com.autonavi.jni.ae.busplan;

import com.autonavi.jni.ae.busplan.interfaces.IBusServiceListener;
import com.autonavi.jni.ae.busplan.model.BusPlanParam;
import java.lang.reflect.Field;
import org.json.JSONException;
import org.json.JSONObject;

public class MiniAppBusService {
    private IBusServiceListener mBusServiceListener;
    private long mPtr;

    public native void cancel(int i);

    public final native void cancelAll();

    public native void destory();

    public final native void init(int i, String str);

    public native void request(int i, String str);

    public void setBusServiceListener(IBusServiceListener iBusServiceListener) {
        this.mBusServiceListener = iBusServiceListener;
    }

    public void request(int i, BusPlanParam busPlanParam) {
        String convertBusPlanParam2Json = convertBusPlanParam2Json(busPlanParam);
        if (convertBusPlanParam2Json != null) {
            request(i, convertBusPlanParam2Json);
        }
    }

    private String convertBusPlanParam2Json(BusPlanParam busPlanParam) {
        if (busPlanParam == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        Field[] declaredFields = busPlanParam.getClass().getDeclaredFields();
        if (declaredFields == null) {
            return null;
        }
        for (Field field : declaredFields) {
            if (!(field == null || field.getName() == null)) {
                try {
                    jSONObject.put(field.getName(), field.get(busPlanParam));
                } catch (IllegalAccessException | JSONException unused) {
                }
            }
        }
        return jSONObject.toString();
    }
}
