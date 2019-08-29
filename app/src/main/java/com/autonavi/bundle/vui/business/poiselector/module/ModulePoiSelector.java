package com.autonavi.bundle.vui.business.poiselector.module;

import com.alibaba.baichuan.android.trade.constants.AppLinkConstants;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.vcs.NativeVcsManager;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("poi_selector")
@Keep
@KeepClassMembers
public class ModulePoiSelector extends AbstractModule {
    public static final String MODULE_NAME = "poi_selector";
    private static final String TAG = "ModulePoiSelector ";
    private asv mPoiSelectorResult = null;

    public ModulePoiSelector(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("onPoiSelectorResult")
    public void onPoiSelectorResult(String str) {
        if (this.mPoiSelectorResult != null) {
            try {
                this.mPoiSelectorResult.a(new JSONObject(str).optJSONObject("channelArgs").optInt(AppLinkConstants.REQUESTCODE), str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getSearchParams")
    public String getSearchParams() {
        return NativeVcsManager.getInstance().getUniversalScenceData();
    }

    public void setPoiSelectorResult(asv asv) {
        this.mPoiSelectorResult = asv;
    }
}
