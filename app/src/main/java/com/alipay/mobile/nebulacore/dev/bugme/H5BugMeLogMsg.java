package com.alipay.mobile.nebulacore.dev.bugme;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;

public class H5BugMeLogMsg {
    private String a;

    H5BugMeLogMsg(JSONObject json) {
        this.a = a(json);
    }

    public String getContent() {
        return this.a;
    }

    private static String a(JSONObject json) {
        String type = json.getString("type");
        String content = "";
        char c = 65535;
        switch (type.hashCode()) {
            case 3433103:
                if (type.equals("page")) {
                    c = 0;
                    break;
                }
                break;
            case 96891546:
                if (type.equals("event")) {
                    c = 2;
                    break;
                }
                break;
            case 101415985:
                if (type.equals("jsapi")) {
                    c = 1;
                    break;
                }
                break;
            case 951510359:
                if (type.equals("console")) {
                    c = 3;
                    break;
                }
                break;
            case 1843485230:
                if (type.equals("network")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                String url = json.getString("url");
                String params = json.getString("params");
                String userAgent = json.getString("userAgent");
                String subType = json.getString("subType");
                String viewId = json.getString("viewId");
                if (!TextUtils.isEmpty(url)) {
                    content = content + "url - " + url;
                }
                if (!TextUtils.isEmpty(subType)) {
                    content = (content + (TextUtils.isEmpty(content) ? "" : System.getProperty("line.separator"))) + "subType - " + subType;
                }
                if (!TextUtils.isEmpty(viewId)) {
                    content = (content + (TextUtils.isEmpty(content) ? "" : System.getProperty("line.separator"))) + "viewId - " + viewId;
                }
                if (!TextUtils.isEmpty(params)) {
                    content = (content + (TextUtils.isEmpty(content) ? "" : System.getProperty("line.separator"))) + "params - " + params;
                }
                if (TextUtils.isEmpty(userAgent)) {
                    return content;
                }
                return (content + (TextUtils.isEmpty(content) ? "" : System.getProperty("line.separator"))) + "userAgent - " + userAgent;
            case 1:
                String apiName = json.getString("subType");
                String request = json.getString("request");
                String response = json.getString(ModuleLongLinkService.CALLBACK_KEY_RESPONSE);
                Object[] objArr = new Object[5];
                objArr[0] = apiName;
                objArr[1] = System.getProperty("line.separator");
                if (request == null) {
                    request = bny.c;
                }
                objArr[2] = request;
                objArr[3] = System.getProperty("line.separator");
                if (response == null) {
                    response = bny.c;
                }
                objArr[4] = response;
                return String.format("jsApi - %s %srequest - %s %sresponse - %s", objArr);
            case 2:
                return String.format("event - %s%sdata - %s", new Object[]{json.getString("subType"), System.getProperty("line.separator"), json.getString("data")});
            case 3:
                return json.getString("content");
            case 4:
                String method = json.getString("method");
                String reqUrl = json.getString("reqUrl");
                String statusCode = json.getString("statusCode");
                boolean fromLocalPkg = json.containsKey("fromLocalPkg") ? json.getBoolean("fromLocalPkg").booleanValue() : false;
                String content2 = content + "method - " + method;
                if (fromLocalPkg) {
                    content2 = content2 + " - fromLocalPkg";
                }
                if (!TextUtils.isEmpty(statusCode)) {
                    content2 = content2 + System.getProperty("line.separator") + String.format("statusCode - %s", new Object[]{statusCode});
                }
                if (TextUtils.isEmpty(reqUrl)) {
                    return content2;
                }
                return content2 + System.getProperty("line.separator") + String.format("reqUrl - %s", new Object[]{reqUrl});
            default:
                return json.toJSONString();
        }
    }
}
