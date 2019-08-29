package com.taobao.applink.param;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.appmonitor.offline.TempEvent;
import com.sina.weibo.sdk.constant.WBConstants;
import com.taobao.applink.TBAppLinkSDK;
import com.taobao.applink.a.a;
import com.taobao.applink.auth.TBAppLinkAuthListener;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.util.TBAppLinkUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class TBAuthParam extends TBBaseParam {
    public static final String ACTION_NAME = "ali.open.auth";
    private static final String MODEL_NAME = "auth";
    private String mRedirectUri;

    private TBAuthParam() {
        super(a.AUTH);
    }

    public TBAuthParam(TBAppLinkAuthListener tBAppLinkAuthListener) {
        super(a.AUTH);
        this.mListener = tBAppLinkAuthListener;
        this.mParams.put("action", ACTION_NAME);
        this.mParams.put(TempEvent.TAG_MODULE, "auth");
    }

    public boolean checkParams(JSONObject jSONObject) {
        this.mParams.put("action", ACTION_NAME);
        this.mParams.put(TempEvent.TAG_MODULE, "auth");
        return true;
    }

    public String getH5URL() throws TBAppLinkException {
        if (TBAppLinkSDK.getInstance().sOpenParam == null) {
            return null;
        }
        Object[] objArr = new Object[2];
        objArr[0] = TextUtils.isEmpty(TBAppLinkSDK.getInstance().sOpenParam.mAppkey) ? "" : TBAppLinkSDK.getInstance().sOpenParam.mAppkey;
        objArr[1] = TextUtils.isEmpty(this.mRedirectUri) ? "" : this.mRedirectUri;
        return String.format(TBAppLinkUtil.GO_AUTH_H5URL, objArr);
    }

    public String getJumpUrl(Context context) throws TBAppLinkException {
        return super.getJumpUrl(context);
    }

    public void setParams(JSONObject jSONObject) {
        try {
            this.mRedirectUri = jSONObject.getString(WBConstants.AUTH_PARAMS_REDIRECT_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TBBaseParam setRedirectUri(String str) {
        this.mRedirectUri = str;
        return this;
    }
}
