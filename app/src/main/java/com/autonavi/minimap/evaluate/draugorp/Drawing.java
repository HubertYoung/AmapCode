package com.autonavi.minimap.evaluate.draugorp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.InputDeviceCompat;
import com.autonavi.minimap.bundle.evaluate.api.IEvaluateOperationManager.Channel;
import com.autonavi.minimap.bundle.evaluate.callback.EvaluateLifecycleCallbacks;
import org.json.JSONObject;

public class Drawing {
    private static void drawLine(String str) {
    }

    public static void drawLine1(JSONObject jSONObject) {
        drawLine(jSONObject.toString());
        if (Channel.UT_CHANNEL == cxk.a().h()) {
            cxl.a((String) "BAT", jSONObject);
        } else {
            cxl.a((String) "P00001", (String) "B206", jSONObject);
        }
    }

    public static void drawLine2(JSONObject jSONObject) {
        drawLine(jSONObject.toString());
        if (Channel.UT_CHANNEL == cxk.a().h()) {
            cxl.a((String) "QAL", jSONObject);
        } else {
            cxl.a((String) "P00394", (String) "B002", jSONObject);
        }
    }

    public static String setAjxInfo(String str) {
        if (str == null || str.trim().length() <= 0) {
            return "";
        }
        if (!EvaluateLifecycleCallbacks.a()) {
            return "";
        }
        Handler handler = cxi.a().a;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage();
            obtainMessage.what = 1024;
            obtainMessage.arg1 = InputDeviceCompat.SOURCE_GAMEPAD;
            Bundle bundle = new Bundle();
            bundle.putString("_ajx_property_", str);
            obtainMessage.setData(bundle);
            handler.sendMessage(obtainMessage);
        }
        return "";
    }
}
