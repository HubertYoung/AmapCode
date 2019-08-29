package com.alipay.mobile.tinyappcommon.tinypermission;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.a;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: H5PermissionUtil */
public final class c {
    public static b a(String appId, byte[] bytes) {
        try {
            String data = TemplateTinyApp.getInstance().modifyApiPermission(appId, new String(bytes, "UTF-8"));
            if (!TextUtils.isEmpty(data)) {
                return a(appId, H5Utils.parseObject(data));
            }
        } catch (Exception e) {
            H5Log.e((String) "H5PermissionUtil", (Throwable) e);
        }
        return null;
    }

    public static b a(String appId, JSONObject jsonObject) {
        try {
            a();
            if (jsonObject != null && !jsonObject.isEmpty()) {
                b h5ApiPermissionInfo = new b();
                h5ApiPermissionInfo.a((String) H5ApiManager.Enable_Proxy, H5Utils.getString(jsonObject, (String) H5ApiManager.Enable_Proxy));
                JSONArray JSAPI_List = H5Utils.getJSONArray(jsonObject, H5ApiManager.JSAPI_List, null);
                if (JSAPI_List != null && !JSAPI_List.isEmpty()) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<Object> it = JSAPI_List.iterator();
                    while (it.hasNext()) {
                        Object api = it.next();
                        if (api instanceof String) {
                            arrayList.add((String) api);
                        }
                    }
                    h5ApiPermissionInfo.a((String) H5ApiManager.JSAPI_List, (List<String>) arrayList);
                }
                JSONArray EVENT_List = H5Utils.getJSONArray(jsonObject, H5ApiManager.EVENT_List, null);
                if (EVENT_List != null && !EVENT_List.isEmpty()) {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<Object> it2 = EVENT_List.iterator();
                    while (it2.hasNext()) {
                        Object event = it2.next();
                        if (event instanceof String) {
                            arrayList2.add((String) event);
                        }
                    }
                    h5ApiPermissionInfo.a((String) H5ApiManager.EVENT_List, (List<String>) arrayList2);
                }
                JSONArray validDomain = H5Utils.getJSONArray(jsonObject, H5ApiManager.validDomain, null);
                if (validDomain != null && !validDomain.isEmpty()) {
                    ArrayList arrayList3 = new ArrayList();
                    Iterator<Object> it3 = validDomain.iterator();
                    while (it3.hasNext()) {
                        Object domain = it3.next();
                        if (domain instanceof String) {
                            arrayList3.add((String) domain);
                        }
                    }
                    h5ApiPermissionInfo.a((String) H5ApiManager.validDomain, (List<String>) arrayList3);
                }
                JSONObject sp_config = H5Utils.getJSONObject(jsonObject, H5ApiManager.JSAPI_SP_Config, null);
                if (sp_config != null && !sp_config.isEmpty()) {
                    for (String key : sp_config.keySet()) {
                        JSONObject value = H5Utils.getJSONObject(sp_config, key, null);
                        if (value != null) {
                            ArrayList arrayList4 = new ArrayList();
                            for (String paramKey : value.keySet()) {
                                arrayList4.add(paramKey);
                                JSONArray paramValue = H5Utils.getJSONArray(value, paramKey, null);
                                if (paramValue != null && !paramValue.isEmpty()) {
                                    ArrayList arrayList5 = new ArrayList();
                                    Iterator<Object> it4 = paramValue.iterator();
                                    while (it4.hasNext()) {
                                        Object paramRegex = it4.next();
                                        if (paramRegex instanceof String) {
                                            arrayList5.add((String) paramRegex);
                                        }
                                    }
                                    h5ApiPermissionInfo.a("JSAPI_SP_Config_" + key + "_" + paramKey, (List<String>) arrayList5);
                                }
                            }
                            h5ApiPermissionInfo.a("JSAPI_SP_Config_" + key, (List<String>) arrayList4);
                        }
                    }
                }
                JSONArray alid_SubResMimeList = H5Utils.getJSONArray(jsonObject, H5ApiManager.Valid_SubResMimeList, null);
                if (alid_SubResMimeList != null && !alid_SubResMimeList.isEmpty()) {
                    ArrayList arrayList6 = new ArrayList();
                    Iterator<Object> it5 = alid_SubResMimeList.iterator();
                    while (it5.hasNext()) {
                        Object mimeRegex = it5.next();
                        if (mimeRegex instanceof String) {
                            arrayList6.add((String) mimeRegex);
                        }
                    }
                    h5ApiPermissionInfo.a((String) H5ApiManager.Valid_SubResMimeList, (List<String>) arrayList6);
                }
                JSONArray http = H5Utils.getJSONArray(jsonObject, H5ApiManager.HttpLink_SubResMimeList, null);
                if (http != null && !http.isEmpty()) {
                    ArrayList arrayList7 = new ArrayList();
                    Iterator<Object> it6 = http.iterator();
                    while (it6.hasNext()) {
                        Object mimeRegex2 = it6.next();
                        if (mimeRegex2 instanceof String) {
                            arrayList7.add((String) mimeRegex2);
                        }
                    }
                    h5ApiPermissionInfo.a((String) H5ApiManager.HttpLink_SubResMimeList, (List<String>) arrayList7);
                }
                JSONObject webview_Config = H5Utils.getJSONObject(jsonObject, H5ApiManager.Webview_Config, null);
                if (!webview_Config.isEmpty()) {
                    JSONArray webview_Config_domain_List = webview_Config.getJSONArray(H5ApiManager.allowedDomain);
                    if (webview_Config_domain_List != null) {
                        List domainList = new ArrayList();
                        Iterator<Object> it7 = webview_Config_domain_List.iterator();
                        while (it7.hasNext()) {
                            Object domain2 = it7.next();
                            if (domain2 instanceof String) {
                                domainList.add((String) domain2);
                            }
                        }
                        h5ApiPermissionInfo.a((String) "Webview_Config_allowedDomain", domainList);
                    }
                }
                h5ApiPermissionInfo.a(true);
                return h5ApiPermissionInfo;
            }
        } catch (Exception e) {
            H5Log.e((String) "H5PermissionUtil", (Throwable) e);
            H5LogProvider h5LogProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
            if (h5LogProvider != null) {
                h5LogProvider.logV2("H5_PERMISSION_FILE_PARSE_FAIL", null, null, null, "appId=" + appId + "^error=" + e.toString(), H5Logger.LOG_HEADER_EM);
            }
        }
        return null;
    }

    public static void a() {
        try {
            a.a().b();
        } catch (Throwable e) {
            H5Log.e((String) "H5PermissionUtil", "jsonToInfo...e=" + e);
        }
    }
}
