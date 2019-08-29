package com.ta.audid.store;

import android.content.Context;
import com.ta.audid.Variables;
import com.ta.audid.collect.DeviceFPCollect;
import com.ta.audid.upload.UtdidKeyFile;
import com.ta.audid.utils.MD5Utils;
import com.ta.utdid2.android.utils.StringUtils;
import java.util.HashMap;
import org.json.JSONObject;

public class UtdidContentBuilder {
    private static final String BODY = "{\"type\":\"%s\",\"timestamp\":%s,\"data\":%s}";
    public static final String TYPE_AUDID = "audid";
    public static final String TYPE_FP = "fp";
    public static final String TYPE_RS = "rs";

    public static String buildUDID(String str) {
        Context context = Variables.getInstance().getContext();
        if (context == null) {
            return "";
        }
        return StringUtils.getStringWithoutBlank(String.format(BODY, new Object[]{"audid", Variables.getInstance().getCurrentTimeMillisString(), buildAudidDataJsonString(str, UtdidKeyFile.readAudidFile(), Variables.getInstance().getAppkey(), context.getPackageName())}));
    }

    private static String buildAudidDataJsonString(String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put("audid", str2);
        hashMap.put("utdid", str);
        hashMap.put("appkey", str3);
        hashMap.put("appName", str4);
        return new JSONObject(StringUtils.sortMapByKey(hashMap)).toString();
    }

    public static String buildRS(String str, String str2, String str3, String str4) {
        Context context = Variables.getInstance().getContext();
        if (context == null) {
            return "";
        }
        return StringUtils.getStringWithoutBlank(String.format(BODY, new Object[]{TYPE_RS, Variables.getInstance().getCurrentTimeMillisString(), RSModle.buildJsonString(str, Variables.getInstance().getAppkey(), context.getPackageName(), str2, str3, str4)}));
    }

    public static String buildUtdidFp(String str) {
        Context context = Variables.getInstance().getContext();
        if (context == null) {
            return "";
        }
        return StringUtils.getStringWithoutBlank(String.format(BODY, new Object[]{TYPE_FP, Variables.getInstance().getCurrentTimeMillisString(), buildFPDataJsonString(str, Variables.getInstance().getAppkey(), context.getPackageName())}));
    }

    private static String buildFPDataJsonString(String str, String str2, String str3) {
        HashMap hashMap = new HashMap();
        hashMap.put("utdid", str);
        hashMap.put("appkey", str2);
        hashMap.put("appName", str3);
        hashMap.put(Module.MODULE_FP_INFO, DeviceFPCollect.getFPInfo(Variables.getInstance().getContext()));
        return new JSONObject(hashMap).toString();
    }

    public static String getRS_MD5(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return (!jSONObject.has("type") || !jSONObject.has("data") || !jSONObject.getString("type").equals(TYPE_RS)) ? "" : MD5Utils.getHmacMd5Hex(jSONObject.getString("data"));
        } catch (Exception unused) {
            return "";
        }
    }
}
