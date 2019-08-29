package com.taobao.agoo.control.data;

import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility.JsonObjectBuilder;

public class SwitchDO extends BaseDO {
    public static final String JSON_CMD_DISABLEPUSH = "disablePush";
    public static final String JSON_CMD_ENABLEPUSH = "enablePush";
    private static final String TAG = "SwitchDO";
    public String appKey;
    public String deviceId;
    public String utdid;

    public byte[] buildData() {
        try {
            JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
            jsonObjectBuilder.put((String) BaseDO.JSON_CMD, this.cmd).put((String) "appKey", this.appKey);
            if (TextUtils.isEmpty(this.deviceId)) {
                jsonObjectBuilder.put((String) "utdid", this.utdid);
            } else {
                jsonObjectBuilder.put((String) "deviceId", this.deviceId);
            }
            String jSONObject = jsonObjectBuilder.build().toString();
            ALog.i(TAG, "buildData", "data", jSONObject);
            return jSONObject.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.e(TAG, "buildData", th, new Object[0]);
            return null;
        }
    }

    public static byte[] buildSwitchDO(String str, String str2, String str3, boolean z) {
        SwitchDO switchDO = new SwitchDO();
        switchDO.appKey = str;
        switchDO.deviceId = str2;
        switchDO.utdid = str3;
        if (z) {
            switchDO.cmd = JSON_CMD_ENABLEPUSH;
        } else {
            switchDO.cmd = JSON_CMD_DISABLEPUSH;
        }
        return switchDO.buildData();
    }
}
