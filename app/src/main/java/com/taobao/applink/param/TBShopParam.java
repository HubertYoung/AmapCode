package com.taobao.applink.param;

import android.content.Context;
import com.alibaba.appmonitor.offline.TempEvent;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.exception.a;
import com.taobao.applink.util.TBAppLinkUtil;
import com.taobao.applink.util.e;
import org.json.JSONException;
import org.json.JSONObject;

public class TBShopParam extends TBNavParam {
    private static final String MODEL_NAME = "shop";
    private String mShopID;

    private TBShopParam() {
    }

    public TBShopParam(String str) {
        this.mParams.put(TempEvent.TAG_MODULE, "shop");
        this.mShopID = str;
    }

    public boolean checkParams(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString("shopId");
            if (!e.b(string)) {
                return false;
            }
            this.mParams.put(TempEvent.TAG_MODULE, "shop");
            this.mShopID = string;
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public String getH5URL() throws TBAppLinkException {
        if (!e.b(this.mShopID)) {
            throw new TBAppLinkException(a.SHOPID_ILLEGAL);
        }
        return super.getH5URL(String.format(TBAppLinkUtil.GO_SHOP_H5URL, new Object[]{this.mShopID}));
    }

    public String getJumpUrl(Context context) throws TBAppLinkException {
        if (!e.b(this.mShopID)) {
            throw new TBAppLinkException(a.SHOPID_ILLEGAL);
        }
        this.mExtraParams.put("shopId", this.mShopID);
        return super.getJumpUrl(context);
    }
}
