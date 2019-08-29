package com.autonavi.bundle.searchresult.ajx;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import org.json.JSONObject;

@AjxModule("search_no_result")
public class ModuleSearchNoResult extends AbstractModule {
    public static final String MODULE_NAME = "search_no_result";
    public b mOffLineUiListener;
    public a mOnCitySuggUiListener;
    public c mOnLineUiListener;

    public interface a {
        void a(String str, String str2);
    }

    public interface b {
        void a();

        void a(int i);
    }

    public interface c {
        void a();

        void a(String str);

        void b();

        void c();
    }

    public ModuleSearchNoResult(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("onOfflineErrorBtnClick")
    public void onOfflineErrorBtnClick(String str) {
        try {
            int optInt = new JSONObject(str).optInt("noResultType");
            if (this.mOffLineUiListener != null) {
                this.mOffLineUiListener.a(optInt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("onOfflineSearchBarTitleClick")
    public void onOfflineSearchBarTitleClick() {
        if (this.mOffLineUiListener != null) {
            this.mOffLineUiListener.a();
        }
    }

    @AjxMethod("onOnlineSearchBarTitleClick")
    public void onOnlineSearchBarTitleClick() {
        if (this.mOnLineUiListener != null) {
            this.mOnLineUiListener.c();
        }
    }

    @AjxMethod("onOnlineKeyWordClick")
    public void onOnlineKeyWordClick(String str) {
        if (this.mOnLineUiListener != null) {
            this.mOnLineUiListener.a(str);
        }
    }

    @AjxMethod("onOnlineAddLocationClick")
    public void onOnlineAddLocationClick() {
        if (this.mOnLineUiListener != null) {
            this.mOnLineUiListener.a();
        }
    }

    @AjxMethod("onOnlineSmSearchClick")
    public void onOnlineSmSearchClick(String str) {
        if (this.mOnLineUiListener != null) {
            this.mOnLineUiListener.b();
        }
    }

    @AjxMethod("onOnlineSearchBarClick")
    public void onOnlineSearchBarClick() {
        if (this.mOnLineUiListener != null) {
            this.mOnLineUiListener.c();
        }
    }

    @AjxMethod("onCitySuggestioCellClicked")
    public void onCitySuggestioCellClicked(String str) {
        if (this.mOnCitySuggUiListener != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                jSONObject.optString("name");
                this.mOnCitySuggUiListener.a(jSONObject.optString(AutoJsonUtils.JSON_ADCODE), jSONObject.optString("pIndex"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void seOffLinetUiListener(b bVar) {
        this.mOffLineUiListener = bVar;
    }

    public void setOnLineUiListener(c cVar) {
        this.mOnLineUiListener = cVar;
    }

    public void setOnCitySuggUiListener(a aVar) {
        this.mOnCitySuggUiListener = aVar;
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        if (this.mOnLineUiListener != null) {
            this.mOnLineUiListener = null;
        }
        if (this.mOffLineUiListener != null) {
            this.mOffLineUiListener = null;
        }
        if (this.mOnCitySuggUiListener != null) {
            this.mOnCitySuggUiListener = null;
        }
    }
}
