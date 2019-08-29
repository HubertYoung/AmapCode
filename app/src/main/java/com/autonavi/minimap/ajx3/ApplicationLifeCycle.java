package com.autonavi.minimap.ajx3;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class ApplicationLifeCycle {
    private static final String AJX_SERVICES = "path://amap_bundle_dynamic_ui/src/service/GlobalService.page.js";
    private static final String MSG_ON_AJX_INITED = "ajx_init_finished";
    private static final String MSG_ON_BACKGROUND = "app_enter_background";
    private static final String MSG_ON_FOREGROUND = "app_enter_foreground";
    private static final String MSG_ON_MAP_FIRST_RENDERED = "app_map_first_rendered";

    public enum APPLifeCycle {
        APP_MAP_FIRST_RENDERED,
        APP_ENTER_FOREGROUND,
        APP_ENTER_BACKGROUND,
        APP_ON_CREATE_WITH_PERMISSION,
        APP_ON_ACTIVITY_CREATE,
        APP_ON_ACTIVITY_START,
        APP_ON_ACTIVITY_RESTART,
        APP_ON_ACTIVITY_RESUME,
        APP_ON_ACTIVITY_PAUSE,
        APP_ON_ACTIVITY_STOP,
        APP_ON_ACTIVITY_DESTROY,
        APP_ON_ACTIVITY_NEW_INTENT,
        APP_ON_BUNDLE_CREATE,
        APP_ON_CREATE,
        APP_ON_TERMINATE,
        APP_ON_AJX_REGISTER,
        APP_ON_RECEIVE_ACTIVITY_RESULT
    }

    ApplicationLifeCycle() {
    }

    /* access modifiers changed from: 0000 */
    public void onAppLifeCycle(APPLifeCycle aPPLifeCycle, String str, ConcurrentHashMap<Long, JsContextRef> concurrentHashMap) {
        String generateMessage = generateMessage(aPPLifeCycle, str);
        if (!TextUtils.isEmpty(generateMessage)) {
            switch (aPPLifeCycle) {
                case APP_MAP_FIRST_RENDERED:
                case APP_ON_AJX_REGISTER:
                    if (!TextUtils.isEmpty(AJX_SERVICES) && pageExist(AJX_SERVICES)) {
                        Ajx.getInstance().startService("Ajx_services", AJX_SERVICES, generateMessage, "");
                    }
                    return;
                default:
                    if (concurrentHashMap.size() > 0) {
                        for (Long next : concurrentHashMap.keySet()) {
                            if (next != null) {
                                JsContextRef jsContextRef = concurrentHashMap.get(next);
                                if (jsContextRef != null) {
                                    jsContextRef.sendMessage(generateMessage);
                                }
                            }
                        }
                        return;
                    }
                    return;
            }
        }
    }

    private String generateMessage(APPLifeCycle aPPLifeCycle, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            switch (aPPLifeCycle) {
                case APP_MAP_FIRST_RENDERED:
                    jSONObject.put("lifecycle", MSG_ON_MAP_FIRST_RENDERED);
                    jSONObject.put("data", str);
                    return jSONObject.toString();
                case APP_ON_AJX_REGISTER:
                    jSONObject.put("lifecycle", MSG_ON_AJX_INITED);
                    jSONObject.put("data", str);
                    return jSONObject.toString();
                case APP_ENTER_FOREGROUND:
                    jSONObject.put("lifecycle", MSG_ON_FOREGROUND);
                    jSONObject.put("data", str);
                    return jSONObject.toString();
                case APP_ENTER_BACKGROUND:
                    jSONObject.put("lifecycle", MSG_ON_BACKGROUND);
                    jSONObject.put("data", str);
                    return jSONObject.toString();
            }
        } catch (Exception unused) {
        }
        return "";
    }

    public boolean pageExist(String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith(AjxPathLoader.DOMAIN)) {
            return false;
        }
        return AjxFileInfo.isFileExists(Ajx.getInstance().lookupLoader(str).processingPath(PictureParams.make(null, str, false)));
    }
}
