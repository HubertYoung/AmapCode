package com.autonavi.bundle.account.facerecognition;

import android.util.Log;
import com.alipay.mobile.security.zim.api.ZIMCallback;
import com.alipay.mobile.security.zim.api.ZIMResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class AMapFaceResponse implements ZIMCallback {
    private static final String FACE_NO_RESPONSE = "{\n\t\"code\": -4,\n\t\"reason\": \"获取身份认证信息失败，无返回结果\"\n";
    private static final String TAG = "AMapFaceResponse";
    private JsFunctionCallback mCallback;

    public AMapFaceResponse(JsFunctionCallback jsFunctionCallback) {
        this.mCallback = jsFunctionCallback;
    }

    public boolean response(ZIMResponse zIMResponse) {
        if (this.mCallback == null) {
            AMapLog.error("basemap.account", TAG, "js callback is null");
            return true;
        } else if (zIMResponse == null) {
            this.mCallback.callback(FACE_NO_RESPONSE);
            AMapLog.error("basemap.account", TAG, "response is null");
            return true;
        } else {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("code", zIMResponse.code);
                jSONObject.put("reason", zIMResponse.reason);
                jSONObject.put("subCode", zIMResponse.subCode);
                jSONObject.put("msg", zIMResponse.msg);
                jSONObject.put("bizData", zIMResponse.bizData);
                jSONObject.put("extInfo", new JSONObject(zIMResponse.extInfo));
                this.mCallback.callback(jSONObject.toString());
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("response:");
                    sb.append(jSONObject.toString());
                    AMapLog.debug("basemap.account", TAG, sb.toString());
                }
            } catch (JSONException e) {
                this.mCallback.callback(FACE_NO_RESPONSE);
                StringBuilder sb2 = new StringBuilder("startFaceVerification-response:");
                sb2.append(Log.getStackTraceString(e));
                AMapLog.error("basemap.account", TAG, sb2.toString());
            }
            return true;
        }
    }
}
