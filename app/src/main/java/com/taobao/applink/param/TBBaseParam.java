package com.taobao.applink.param;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.applink.TBAppLinkSDK;
import com.taobao.applink.a.a;
import com.taobao.applink.auth.TBAppLinkAuthListener;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.util.TBAppLinkUtil;
import com.taobao.applink.util.c;
import com.taobao.applink.util.e;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public abstract class TBBaseParam {
    protected static final String ACTION = "action";
    protected static final String APPKEY = "appkey";
    protected static final String APPNAME = "appName";
    protected static final String APPSERCET = "AppSecret";
    protected static final String BACKURL = "backURL";
    protected static final String E = "e";
    protected static final String EXTRAPARAMS = "params";
    protected static final String H5URL = "h5Url";
    protected static final String ITMEID = "itemId";
    protected static final String MODULE = "module";
    protected static final String PACKAGENAME = "packageName";
    protected static final String PID = "pid";
    protected static final String SDKVERSION = "v";
    protected static final String SHOPID = "shopId";
    protected static final String SIGN = "sign";
    protected static final String SOURCE = "source";
    protected static final String TAG = "tag";
    protected static final String TIME = "time";
    protected static final String TTID = "TTID";
    protected static final String TYPE = "type";
    protected static final String UTDID = "utdid";
    protected static final String YBHPSS = "ybhpss";
    public String linkKey = TBAppLinkUtil.TAOBAO_SCHEME;
    protected HashMap mExtraParams = new HashMap();
    public TBAppLinkAuthListener mListener;
    protected HashMap mParams = new HashMap();
    public a mTBAPIType;

    protected TBBaseParam(a aVar) {
        this.mTBAPIType = aVar;
        if (TBAppLinkSDK.getInstance().sOpenParam != null) {
            if (!e.a(TBAppLinkSDK.getInstance().sOpenParam.mBackUrl)) {
                this.mParams.put("backURL", TBAppLinkSDK.getInstance().sOpenParam.mBackUrl);
            }
            if (!e.a(TBAppLinkSDK.getInstance().sOpenParam.mAppkey)) {
                this.mParams.put("appkey", TBAppLinkSDK.getInstance().sOpenParam.mAppkey);
            }
            if (!e.a(TBAppLinkSDK.getInstance().sOpenParam.mPid)) {
                this.mExtraParams.put("pid", TBAppLinkSDK.getInstance().sOpenParam.mPid);
            }
            if (!e.a(TBAppLinkSDK.getInstance().sOpenParam.mTtid)) {
                this.mParams.put("TTID", TBAppLinkSDK.getInstance().sOpenParam.mTtid);
            }
            if (!e.a(TBAppLinkSDK.getInstance().sOpenParam.mTag)) {
                this.mParams.put("tag", TBAppLinkSDK.getInstance().sOpenParam.mTag);
            }
            if (!e.a(TBAppLinkSDK.getInstance().sOpenParam.mUtdid)) {
                this.mParams.put("utdid", TBAppLinkSDK.getInstance().sOpenParam.mUtdid);
            }
            if (!e.a(TBAppLinkSDK.getInstance().sOpenParam.mSource)) {
                this.mParams.put("source", TBAppLinkSDK.getInstance().sOpenParam.mSource);
            }
        }
    }

    public TBBaseParam addExtraParams(HashMap hashMap) {
        if (hashMap != null) {
            for (Entry entry : hashMap.entrySet()) {
                if (!e.a((String) entry.getKey()) && !e.a((String) entry.getValue())) {
                    this.mExtraParams.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return this;
    }

    public abstract boolean checkParams(JSONObject jSONObject);

    public String getExtraAplus() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("module=");
        stringBuffer.append((String) this.mParams.get("module"));
        stringBuffer.append("&clientType=");
        stringBuffer.append(this.linkKey == null ? TBAppLinkUtil.TAOBAO_SCHEME : this.linkKey);
        return stringBuffer.toString();
    }

    public abstract String getH5URL() throws TBAppLinkException;

    /* access modifiers changed from: protected */
    public String getH5URL(String str) throws TBAppLinkException {
        if (e.a(str)) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (this.mExtraParams != null) {
            for (Entry entry : this.mExtraParams.entrySet()) {
                if (!e.a((String) entry.getKey()) && !e.a((String) entry.getValue())) {
                    try {
                        String encode = URLEncoder.encode((String) entry.getKey(), "UTF-8");
                        String encode2 = URLEncoder.encode((String) entry.getValue(), "UTF-8");
                        stringBuffer.append(encode);
                        stringBuffer.append("=");
                        stringBuffer.append(encode2);
                        stringBuffer.append("&");
                    } catch (Throwable unused) {
                    }
                }
            }
        }
        if (!e.a((String) this.mParams.get("TTID"))) {
            try {
                String encode3 = URLEncoder.encode("TTID", "UTF-8");
                String encode4 = URLEncoder.encode((String) this.mParams.get("TTID"), "UTF-8");
                stringBuffer.append(encode3);
                stringBuffer.append("=");
                stringBuffer.append(encode4);
                stringBuffer.append("&");
            } catch (Throwable unused2) {
            }
        }
        if (!e.a((String) this.mParams.get("tag"))) {
            try {
                String encode5 = URLEncoder.encode("tag", "UTF-8");
                String encode6 = URLEncoder.encode((String) this.mParams.get("tag"), "UTF-8");
                stringBuffer.append(encode5);
                stringBuffer.append("=");
                stringBuffer.append(encode6);
                stringBuffer.append("&");
            } catch (Throwable unused3) {
            }
        }
        if (!e.a((String) this.mParams.get("utdid"))) {
            try {
                String encode7 = URLEncoder.encode("utdid", "UTF-8");
                String encode8 = URLEncoder.encode((String) this.mParams.get("utdid"), "UTF-8");
                stringBuffer.append(encode7);
                stringBuffer.append("=");
                stringBuffer.append(encode8);
                stringBuffer.append("&");
            } catch (Throwable unused4) {
            }
        }
        if (!e.a((String) this.mParams.get("source"))) {
            try {
                String encode9 = URLEncoder.encode("source", "UTF-8");
                String encode10 = URLEncoder.encode((String) this.mParams.get("source"), "UTF-8");
                stringBuffer.append(encode9);
                stringBuffer.append("=");
                stringBuffer.append(encode10);
                stringBuffer.append("&");
            } catch (Throwable unused5) {
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(stringBuffer.toString());
        return sb.toString();
    }

    public String getJumpUrl(Context context) throws TBAppLinkException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TBAppLinkUtil.BASE_URL);
        this.mParams.put("appName", com.taobao.applink.b.a.b(context));
        this.mParams.put("packageName", com.taobao.applink.b.a.a(context));
        this.mParams.put("v", "2.0.0");
        for (Entry entry : this.mParams.entrySet()) {
            if (!e.a((String) entry.getKey()) && !e.a((String) entry.getValue())) {
                try {
                    String encode = URLEncoder.encode((String) entry.getKey(), "UTF-8");
                    String encode2 = URLEncoder.encode((String) entry.getValue(), "UTF-8");
                    stringBuffer.append(encode);
                    stringBuffer.append("=");
                    stringBuffer.append(encode2);
                    stringBuffer.append("&");
                } catch (Throwable unused) {
                }
            }
        }
        if (TBAppLinkSDK.getInstance().mTBAppLinkSecret != null) {
            try {
                String sign = TBAppLinkSDK.getInstance().mTBAppLinkSecret.sign(getSignContent(context));
                if (!e.a(sign)) {
                    this.mExtraParams.put("sign", sign);
                }
            } catch (Throwable unused2) {
            }
        } else {
            String a = c.a(getSignContent(context));
            if (!e.a(a)) {
                this.mExtraParams.put("sign", a);
            }
        }
        JSONObject jSONObject = new JSONObject();
        if (this.mExtraParams != null) {
            for (Entry entry2 : this.mExtraParams.entrySet()) {
                if (!e.a((String) entry2.getKey()) && !e.a((String) entry2.getValue())) {
                    try {
                        jSONObject.put(URLEncoder.encode((String) entry2.getKey(), "UTF-8"), URLEncoder.encode((String) entry2.getValue(), "UTF-8"));
                    } catch (Throwable unused3) {
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(jSONObject.toString())) {
            stringBuffer.append("params=");
            stringBuffer.append(jSONObject.toString());
            stringBuffer.append("&");
        }
        if (stringBuffer.toString().endsWith("&")) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public Map getParams() {
        return this.mParams;
    }

    public String getSignContent(Context context) {
        if (TBAppLinkSDK.getInstance().sOpenParam == null) {
            return null;
        }
        String valueOf = String.valueOf(System.currentTimeMillis());
        this.mExtraParams.put("time", valueOf);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("appkey");
        stringBuffer.append(TBAppLinkSDK.getInstance().sOpenParam.mAppkey);
        stringBuffer.append("packagename");
        stringBuffer.append(com.taobao.applink.b.a.a(context));
        stringBuffer.append("time");
        stringBuffer.append(valueOf);
        if (TBAppLinkSDK.getInstance().mTBAppLinkSecret != null) {
            return stringBuffer.toString();
        }
        if (e.a(TBAppLinkSDK.getInstance().sOpenParam.mAppSecret)) {
            return null;
        }
        stringBuffer.insert(0, TBAppLinkSDK.getInstance().sOpenParam.mAppSecret).append(TBAppLinkSDK.getInstance().sOpenParam.mAppSecret);
        return stringBuffer.toString();
    }

    public TBBaseParam setAppType(String str) {
        if (!e.a(str)) {
            this.linkKey = str;
        }
        return this;
    }

    public TBBaseParam setBackUrl(String str) {
        if (!e.a(str)) {
            this.mParams.put("backURL", str);
        }
        return this;
    }

    public TBBaseParam setE(String str) {
        if (!e.a(str)) {
            this.mExtraParams.put("e", str);
        }
        return this;
    }

    public abstract void setParams(JSONObject jSONObject);

    public TBBaseParam setSign(String str) {
        if (!e.a(str)) {
            this.mExtraParams.put("sign", str);
        }
        return this;
    }

    public TBBaseParam setType(String str) {
        if (!e.a(str)) {
            this.mExtraParams.put("type", str);
        }
        return this;
    }

    public TBBaseParam setYbhpss(String str) {
        if (!e.a(str)) {
            this.mParams.put("ybhpss", str);
        }
        return this;
    }
}
