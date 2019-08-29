package com.autonavi.minimap.bundle.share.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("share")
@KeepPublicClassMembers
@KeepName
public class ModuleShare extends AbstractModule {
    public static final String MODULE_NAME = "share";

    public ModuleShare(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("activateShareChannel")
    public void activateShareChannel(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("shareMsg");
            String optString2 = jSONObject.optString("channel");
            dcb dcb = (dcb) a.a.a(dcb.class);
            if (dcb != null) {
                dcb.a(optString, optString2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
