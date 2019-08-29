package com.taobao.applink.param;

import android.content.Context;
import com.alibaba.appmonitor.offline.TempEvent;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.exception.a;
import com.taobao.applink.util.e;
import org.json.JSONException;
import org.json.JSONObject;

public class TBURIParam extends TBNavParam {
    private static final String MODEL_NAME = "h5";
    private String mUrl;

    private TBURIParam() {
    }

    public TBURIParam(String str) {
        this.mParams.put(TempEvent.TAG_MODULE, "h5");
        this.mUrl = str;
    }

    public boolean checkParams(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString("h5Url");
            if (!e.c(string)) {
                return false;
            }
            this.mParams.put(TempEvent.TAG_MODULE, "h5");
            this.mUrl = string;
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public String getH5URL() throws TBAppLinkException {
        if (e.c(this.mUrl)) {
            return super.getH5URL(this.mUrl);
        }
        throw new TBAppLinkException(a.H5URL_ILLEGAL);
    }

    public String getJumpUrl(Context context) throws TBAppLinkException {
        if (!e.c(this.mUrl)) {
            throw new TBAppLinkException(a.H5URL_ILLEGAL);
        }
        this.mParams.put("h5Url", this.mUrl);
        return super.getJumpUrl(context);
    }
}
