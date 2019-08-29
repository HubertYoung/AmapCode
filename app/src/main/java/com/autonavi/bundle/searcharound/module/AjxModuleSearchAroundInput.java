package com.autonavi.bundle.searcharound.module;

import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleSearchAroundInput;
import org.json.JSONException;
import org.json.JSONObject;
import search.page.SearchFromAroundPage;

public class AjxModuleSearchAroundInput extends AbstractModuleSearchAroundInput {
    public static final String MODULE_NAME = "searchAroundInput";
    private a mModulePage;

    public interface a {
        void a(TipItem tipItem, boolean z, String str);

        void a(String str, String str2);
    }

    public AjxModuleSearchAroundInput(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void onTipItemClick(String str, boolean z, String str2) {
        TipItem tipItem;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (this.mModulePage != null) {
                if (jSONObject.optInt("type", 1) == 1) {
                    tipItem = bxs.a(jSONObject);
                } else {
                    tipItem = bxs.b(jSONObject);
                }
                if (tipItem != null) {
                    this.mModulePage.a(tipItem, z, str2);
                }
            }
        } catch (JSONException unused) {
        }
    }

    public void setModulePage(SearchFromAroundPage searchFromAroundPage) {
        this.mModulePage = searchFromAroundPage;
    }

    public void keywordSearch(String str, String str2) {
        this.mModulePage.a(str, str2);
    }
}
