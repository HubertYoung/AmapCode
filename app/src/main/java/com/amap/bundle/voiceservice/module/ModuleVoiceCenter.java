package com.amap.bundle.voiceservice.module;

import android.text.TextUtils;
import android.util.Pair;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("voice_center")
@Keep
@KeepClassMembers
public class ModuleVoiceCenter extends AbstractModule {
    public static final String MODULE_NAME = "voice_center";
    public static final String TAG = "ModuleVoiceCenter";
    private final String DATA = "data";
    private JsFunctionCallback mVoiceCommandCallback = null;

    @AjxMethod("test")
    public void test(String str) {
    }

    public ModuleVoiceCenter(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setVoiceCommandCallback")
    public void setVoiceCommandCallback(JsFunctionCallback jsFunctionCallback) {
        this.mVoiceCommandCallback = jsFunctionCallback;
    }

    public void sendVoiceCommand(String str, int i) {
        sendVoiceCommand(str, i, new JSONObject());
    }

    public void sendVoiceCommand(String str, int i, Pair<String, Object> pair) {
        sendVoiceCommand(str, i, aid.a(pair));
    }

    public void sendVoiceCommand(String str, int i, List<Pair<String, Object>> list) {
        sendVoiceCommand(str, i, aid.a(list));
    }

    @AjxMethod("sendVoiceResult")
    public void sendVoiceResult(String str) {
        if (!TextUtils.isEmpty(str)) {
            AMapLog.d(TAG, "voice sendVoiceResult() value = ".concat(String.valueOf(str)));
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = jSONObject.getInt("token");
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                if (jSONObject2.opt("data") == null) {
                    jSONObject2.put("data", new JSONObject());
                }
                aip.a().a(i, jSONObject2.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendVoiceCommand(String str, int i, JSONObject jSONObject) {
        String jSONObject2 = air.a(str, i, jSONObject).toString();
        if (this.mVoiceCommandCallback != null) {
            this.mVoiceCommandCallback.callback(jSONObject2);
            AMapLog.d(TAG, "voice sendVoiceCommand() value = ".concat(String.valueOf(jSONObject2)));
        }
    }
}
