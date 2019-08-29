package com.autonavi.minimap.ajx3.modules;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.AjxInit;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajx.ut")
public class ModuleAmapUT extends AbstractModule {
    public static final String MODULE_NAME = "ajx.ut";

    public ModuleAmapUT(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "controlHit")
    public int controlHit(String str, String str2) {
        return kd.a(str, parseProperty(str, str2));
    }

    @AjxMethod(invokeMode = "sync", value = "customHit")
    public int customHit(String str, String str2) {
        return kd.b(str, parseProperty(str, str2));
    }

    private Map<String, String> parseProperty(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            HashMap hashMap = new HashMap();
            JSONObject jSONObject = new JSONObject(str2);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, jSONObject.getString(next));
            }
            return hashMap;
        } catch (JSONException e) {
            e.printStackTrace();
            StringBuilder sb = new StringBuilder("module=ajx.ut, spm=");
            sb.append(str);
            sb.append(", properties=");
            sb.append(str2);
            AjxInit.showErrorMsg("UT埋点参数错误", sb.toString());
            return null;
        }
    }
}
