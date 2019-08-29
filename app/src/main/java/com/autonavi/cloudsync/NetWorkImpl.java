package com.autonavi.cloudsync;

import android.text.TextUtils;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.autonavi.minimap.net.Sign;
import com.autonavi.server.aos.serverkey;
import com.autonavi.sync.INetwork;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class NetWorkImpl implements INetwork {

    public enum ParamKeyValue {
        channel,
        diu,
        div,
        ver,
        done,
        batch_id,
        init,
        tid,
        uid,
        adiu
    }

    public String requestDownloadFile(String str, String str2, String str3, String str4, String str5) {
        return "";
    }

    public String requestUploadFile(String str, String str2, String str3, String str4, String str5, String str6) {
        return "";
    }

    public String requestByNetwork(String str, String str2, String str3, String str4) {
        AMapLog.i("SYNC_URL", "old:".concat(String.valueOf(str2)));
        if (!str2.contains("www.syncamap.com")) {
            return null;
        }
        String aosSyncUrl = NetworkParam.getAosSyncUrl();
        int i = 1;
        String replace = str2.replace("www.syncamap.com", aosSyncUrl.substring(0, aosSyncUrl.length() - 1));
        AMapLog.i("SYNC_URL", "new:".concat(String.valueOf(replace)));
        AMapLog.d("NetworkImpl", "enter requestByNetwork.");
        if (str == null) {
            AMapLog.d("NetworkImpl", "user＝null");
            return null;
        } else if (str.equals(a()) || "null".equals(a()) || a() == null) {
            if (replace.contains("/ws/sync/download")) {
                i = 0;
            } else if (!replace.contains("/ws/sync/bindinfo")) {
                i = replace.contains("ws/sync/files/upload") ? 3 : replace.contains("ws/sync/files/download") ? 4 : replace.contains("/ws/sync") ? 2 : -1;
            }
            return i == -1 ? "" : a(i, str4, replace);
        } else {
            StringBuilder sb = new StringBuilder("useid");
            sb.append(str);
            sb.append(":account:");
            sb.append(a());
            AMapLog.d("NetworkImpl", sb.toString());
            return null;
        }
    }

    private static String a(int i, String str, String str2) {
        boolean z;
        String str3;
        AosRequest aosRequest;
        AMapLog.d("NetworkImpl", "requestServer");
        HashMap hashMap = new HashMap();
        boolean z2 = i != 2;
        try {
            JSONObject jSONObject = !TextUtils.isEmpty(str) ? new JSONObject(str) : null;
            if (jSONObject != null) {
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    hashMap.put(next, jSONObject.get(next));
                }
            }
        } catch (Throwable th) {
            AMapLog.d("NetworkImpl", th.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ParamKeyValue.channel);
        hashMap.put(sb.toString(), serverkey.getAosChannel());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ParamKeyValue.div);
        hashMap.put(sb2.toString(), NetworkParam.getDiv());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(ParamKeyValue.diu);
        hashMap.put(sb3.toString(), NetworkParam.getDiu());
        String adiu = NetworkParam.getAdiu();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(ParamKeyValue.adiu);
        String sb5 = sb4.toString();
        if (adiu == null) {
            adiu = "";
        }
        hashMap.put(sb5, adiu);
        StringBuilder sb6 = new StringBuilder();
        sb6.append(ParamKeyValue.tid);
        hashMap.put(sb6.toString(), NetworkParam.getTaobaoID());
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            z = false;
        } else {
            z = iAccountService.a();
        }
        if (z) {
            String a = a();
            if (!TextUtils.isEmpty(a)) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(ParamKeyValue.uid);
                hashMap.put(sb7.toString(), a);
            }
        }
        if (i == 1) {
            str3 = a(new String[]{LocationParams.PARA_COMMON_DIU}, (Map<String, Object>) hashMap);
        } else if (i == 0) {
            str3 = a(new String[]{LocationParams.PARA_COMMON_DIU, LocationParams.PARA_COMMON_DIV, "ver", "init", "batch_id"}, (Map<String, Object>) hashMap);
        } else {
            str3 = a(new String[]{LocationParams.PARA_COMMON_DIU, LocationParams.PARA_COMMON_DIV, "ver", CaptureParam.ACTION_DONE_CAPTURE, "batch_id"}, (Map<String, Object>) hashMap);
        }
        hashMap.put("sign", Sign.getSign(str3));
        String a2 = a(str2, (Map<String, Object>) hashMap);
        if (z2) {
            aosRequest = new AosGetRequest();
        } else {
            aosRequest = new AosPostRequest();
        }
        aosRequest.setUrl(a2);
        if (hashMap.size() > 0) {
            for (Entry entry : hashMap.entrySet()) {
                aosRequest.addReqParam((String) entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        aosRequest.setWithoutSign(true);
        aosRequest.setCommonParamStrategy(-1);
        aosRequest.setRetryTimes(0);
        aosRequest.setChannel(1);
        yq.a();
        AosStringResponse aosStringResponse = (AosStringResponse) yq.a(aosRequest, AosStringResponse.class);
        if (aosStringResponse == null || TextUtils.isEmpty((CharSequence) aosStringResponse.getResult())) {
            AMapLog.e("NetworkImpl", "sync request, result is null!");
            return null;
        }
        String str4 = (String) aosStringResponse.getResult();
        new StringBuilder("-----------------").append(str4.length());
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("ret", 0);
            jSONObject2.put("code", aosStringResponse.getStatusCode());
            jSONObject2.put(ModuleLongLinkService.CALLBACK_KEY_RESPONSE, str4);
            return jSONObject2.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            new StringBuilder("POST====:").append(jSONObject2.toString());
            return null;
        }
    }

    private static String a(String[] strArr, Map<String, Object> map) {
        String str = "";
        if (strArr.length > 0 && map.size() > 0) {
            for (String str2 : strArr) {
                Object obj = map.get(str2);
                if (obj != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(obj);
                    str = sb.toString();
                }
            }
        }
        return str;
    }

    private static String a(String str, Map<String, Object> map) {
        if (!str.contains("$")) {
            return str;
        }
        int indexOf = str.indexOf(63);
        if (indexOf >= str.length() - 1) {
            return str;
        }
        String str2 = str;
        for (String split : str.substring(indexOf + 1).split("&")) {
            String str3 = split.split("=")[0];
            if (map.containsKey(str3)) {
                str2 = str2.replaceFirst("\\$", String.valueOf(map.get(str3)));
                map.remove(str3);
            }
        }
        return str2;
    }

    private static String a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }
}
