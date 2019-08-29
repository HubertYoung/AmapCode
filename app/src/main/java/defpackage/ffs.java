package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.common.Constants;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: ffs reason: default package */
/* compiled from: OpenNetworkConverter */
public final class ffs extends ffq {
    private static final Map<String, String> a;

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        a = concurrentHashMap;
        concurrentHashMap.put("x-act", "accessToken");
        a.put("x-wuat", "wua");
        a.put("x-sid", Constants.KEY_SID);
        a.put("x-t", LogItem.MM_C15_K4_TIME);
        a.put("x-appkey", "appKey");
        a.put("x-ttid", "ttid");
        a.put("x-utdid", "utdid");
        a.put("x-sign", "sign");
        a.put("x-pv", "pv");
        a.put("x-uid", Oauth2AccessToken.KEY_UID);
        a.put("x-features", "x-features");
        a.put("x-app-ver", "x-app-ver");
        a.put(MtopJSParam.USER_AGENT, MtopJSParam.USER_AGENT);
    }

    /* access modifiers changed from: protected */
    public final Map<String, String> a() {
        return a;
    }
}
