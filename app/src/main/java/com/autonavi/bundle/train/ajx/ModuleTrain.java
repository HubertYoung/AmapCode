package com.autonavi.bundle.train.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("route_train")
public class ModuleTrain extends AbstractModule {
    public static final String MODULE_NAME = "route_train";
    private static final String START_PAGE_BUYHISTORY = "buyHistory";
    private axi mHistoryItemClickListener;

    public ModuleTrain(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setHistoryItemClickListener(axi axi) {
        this.mHistoryItemClickListener = axi;
    }

    @AjxMethod("requestRoute")
    public void requestRoute(String str) {
        chk chk = new chk();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("start_poi");
            String optString2 = jSONObject.optString("end_poi");
            chk.n = bnx.a(optString);
            chk.p = bnx.a(optString2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.mHistoryItemClickListener != null) {
            this.mHistoryItemClickListener.a(chk);
        }
    }
}
