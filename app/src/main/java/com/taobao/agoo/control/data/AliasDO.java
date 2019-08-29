package com.taobao.agoo.control.data;

import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility.JsonObjectBuilder;

public class AliasDO extends BaseDO {
    public static final String JSON_CMD_REMOVEALIAS = "removeAlias";
    public static final String JSON_CMD_SETALIAS = "setAlias";
    public static final String JSON_PUSH_USER_TOKEN = "pushAliasToken";
    private static final String TAG = "AliasDO";
    public String alias;
    public String appKey;
    public String deviceId;
    public String pushAliasToken;

    public byte[] buildData() {
        try {
            String jSONObject = new JsonObjectBuilder().put((String) BaseDO.JSON_CMD, this.cmd).put((String) "appKey", this.appKey).put((String) "deviceId", this.deviceId).put((String) "alias", this.alias).put((String) JSON_PUSH_USER_TOKEN, this.pushAliasToken).build().toString();
            ALog.i(TAG, "buildData", "data", jSONObject);
            return jSONObject.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.e(TAG, "buildData", th, new Object[0]);
            return null;
        }
    }

    public static byte[] buildsetAlias(String str, String str2, String str3) {
        AliasDO aliasDO = new AliasDO();
        aliasDO.appKey = str;
        aliasDO.deviceId = str2;
        aliasDO.alias = str3;
        aliasDO.cmd = JSON_CMD_SETALIAS;
        return aliasDO.buildData();
    }

    public static byte[] buildremoveAlias(String str, String str2, String str3) {
        AliasDO aliasDO = new AliasDO();
        aliasDO.appKey = str;
        aliasDO.deviceId = str2;
        aliasDO.pushAliasToken = str3;
        aliasDO.cmd = JSON_CMD_REMOVEALIAS;
        return aliasDO.buildData();
    }

    public static byte[] buildremoveAliasByName(String str, String str2, String str3) {
        AliasDO aliasDO = new AliasDO();
        aliasDO.appKey = str;
        aliasDO.deviceId = str2;
        aliasDO.alias = str3;
        aliasDO.cmd = JSON_CMD_REMOVEALIAS;
        return aliasDO.buildData();
    }
}
