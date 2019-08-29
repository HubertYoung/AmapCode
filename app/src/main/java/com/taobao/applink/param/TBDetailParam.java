package com.taobao.applink.param;

import android.content.Context;
import com.alibaba.appmonitor.offline.TempEvent;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.exception.a;
import com.taobao.applink.util.TBAppLinkUtil;
import com.taobao.applink.util.e;
import org.json.JSONException;
import org.json.JSONObject;

public class TBDetailParam extends TBNavParam {
    private static final String MODEL_NAME = "detail";
    private String mItemID;

    private TBDetailParam() {
    }

    public TBDetailParam(String str) {
        this.mParams.put(TempEvent.TAG_MODULE, "detail");
        this.mItemID = str;
    }

    public boolean checkParams(JSONObject jSONObject) {
        try {
            String string = jSONObject.getString("itemId");
            if (!e.b(string)) {
                return false;
            }
            this.mParams.put(TempEvent.TAG_MODULE, "detail");
            this.mItemID = string;
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    public String getH5URL() throws TBAppLinkException {
        if (!e.b(this.mItemID)) {
            throw new TBAppLinkException(a.ITEMID_ILLEGAL);
        }
        return super.getH5URL(String.format(TBAppLinkUtil.GO_DETAIL_H5URL, new Object[]{this.mItemID}));
    }

    public String getJumpUrl(Context context) throws TBAppLinkException {
        if (!e.b(this.mItemID)) {
            throw new TBAppLinkException(a.ITEMID_ILLEGAL);
        }
        this.mExtraParams.put("itemId", this.mItemID);
        return super.getJumpUrl(context);
    }
}
