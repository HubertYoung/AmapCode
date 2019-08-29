package com.alipay.android.phone.inside.wallet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import com.alipay.android.phone.inside.common.util.RandamUtil;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.wallet.encrypt.BizDataProvider;
import com.alipay.android.phone.inside.wallet.model.INotifyChecker;
import com.alipay.android.phone.inside.wallet.model.NotifyResult;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.alipay.sdk.cons.a;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class JumpAlipaySchemeProvider {
    static final String ACTION_JUMP_CALLBACK = "com.alipay.android.phone.inside.wallet.JumpAlipaySchemeCallback";
    static final String APPID = "20000082";
    public static final String BIZ_AUTH = "buscode_auth";
    public static final String BIZ_CASHIER_PAY = "cashier_pay";
    public static final String BIZ_COMMON_BIZ_AUTH = "common_biz_auth";
    public static final String BIZ_COMMON_H5_VERIFY = "common_h5_verify";
    public static final String BIZ_RECEIVECARD = "buscode_receivecard";
    public static final String BIZ_VERITY = "buscode_vertify";
    static final Map<String, NotifyResult> JUMP_MAP = new HashMap();
    static final String KEY_APP_ID = "appId";
    static final String KEY_INS_ACTION = "insAction";
    public static final String KEY_INS_BIZDATA = "insBizData";
    static final String KEY_INS_BIZTYPE = "insBizType";
    static final String KEY_INS_ENC_MODE = "insEncMode";
    static final String KEY_INS_PASSBACK = "insPassBack";
    static final String KEY_INS_PKG = "insPkg";
    static final String KEY_INS_UUID = "insUuid";
    static final String KEY_LOGIN_ID = "aluTargetLoginId";
    static final int MAX_TIMEOUT_MILLIS = 120000;
    public static final String VALUE_ENC_MODE_V1 = "v1";
    public static final String VALUE_ENC_MODE_V2 = "v2";
    private boolean isEncrypt = false;

    public JumpAlipaySchemeProvider() {
    }

    public JumpAlipaySchemeProvider(boolean z) {
        this.isEncrypt = z;
    }

    public NotifyResult jumpScheme(Context context, String str, String str2, Map<String, String> map) throws TimeoutException, Exception {
        return jumpScheme(context, str, str2, map, getNotifyChecker());
    }

    public NotifyResult jumpScheme(Context context, String str, String str2, Map<String, String> map, INotifyChecker iNotifyChecker) throws TimeoutException, Exception {
        LoggerFactory.f().e("inside", "JumpAlipaySchemeProvider::jumpScheme > start");
        String a = RandamUtil.a();
        NotifyResult notifyResult = new NotifyResult(iNotifyChecker);
        JUMP_MAP.put(a, notifyResult);
        jumpAlipayScheme(context, getJumpScheme(context, str, str2, a, map));
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (notifyResult) {
            try {
                notifyResult.wait(120000);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "JumpWalletWaitEx", th);
            }
        }
        if (System.currentTimeMillis() - currentTimeMillis > 119000) {
            throw new TimeoutException();
        }
        LoggerFactory.f().e("inside", "JumpAlipaySchemeProvider::jumpScheme > end");
        return decryptBizData(context, JUMP_MAP.get(a));
    }

    private NotifyResult decryptBizData(Context context, NotifyResult notifyResult) throws Exception {
        if (!(notifyResult == null || notifyResult.result == null || !this.isEncrypt)) {
            notifyResult.result.putString(KEY_INS_BIZDATA, new BizDataProvider(this.isEncrypt).analysisBizData(context, notifyResult.result.getString(KEY_INS_BIZDATA)));
        }
        return notifyResult;
    }

    private INotifyChecker getNotifyChecker() {
        return new INotifyChecker() {
            public boolean illegel(Bundle bundle) {
                if (bundle == null || bundle.getBoolean("insideFlag", false)) {
                    return false;
                }
                LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "AuthNotifyInsideFlagIllegel");
                return true;
            }
        };
    }

    public void notifyJumpResult(Bundle bundle) {
        LoggerFactory.f().e("inside", "JumpAlipaySchemeProvider::notifyJumpResult > ".concat(String.valueOf(bundle)));
        if (bundle != null) {
            String insUuid = getInsUuid(bundle.getString(KEY_INS_PASSBACK));
            if (JUMP_MAP.containsKey(insUuid)) {
                NotifyResult notifyResult = JUMP_MAP.get(insUuid);
                if (notifyResult.illegel(bundle)) {
                    LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "NotifyCheckerIllegel");
                    return;
                }
                notifyResult.result = bundle;
                synchronized (notifyResult) {
                    notifyResult.notifyAll();
                }
                return;
            }
            LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "NoMatchJumpUUID");
        }
    }

    private String getInsUuid(String str) {
        try {
            return new JSONObject(new String(Base64.decode(str, 10))).optString(KEY_INS_UUID);
        } catch (Throwable unused) {
            LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "GetInsUUIDEx");
            r4 = "";
            return "";
        }
    }

    private String getJumpScheme(Context context, String str, String str2, String str3, Map<String, String> map) throws Exception {
        String insBizData = getInsBizData(context, map);
        String insPassBackData = getInsPassBackData(str3);
        String packageName = context.getApplicationContext().getPackageName();
        HashMap hashMap = new HashMap();
        hashMap.put("appId", APPID);
        hashMap.put(KEY_LOGIN_ID, str2);
        hashMap.put(KEY_INS_BIZTYPE, str);
        hashMap.put(KEY_INS_BIZDATA, insBizData);
        hashMap.put(KEY_INS_PKG, packageName);
        hashMap.put(KEY_INS_ACTION, ACTION_JUMP_CALLBACK);
        hashMap.put(KEY_INS_PASSBACK, insPassBackData);
        hashMap.put(KEY_INS_ENC_MODE, this.isEncrypt ? VALUE_ENC_MODE_V2 : VALUE_ENC_MODE_V1);
        String str4 = a.i;
        Set<String> keySet = hashMap.keySet();
        int i = 0;
        for (String str5 : keySet) {
            StringBuilder sb = new StringBuilder();
            sb.append(str4);
            sb.append(str5);
            sb.append("=");
            sb.append(Uri.encode((String) hashMap.get(str5)));
            str4 = sb.toString();
            if (i < keySet.size() - 1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str4);
                sb2.append("&");
                str4 = sb2.toString();
            }
            i++;
        }
        return str4;
    }

    private String getInsBizData(Context context, Map<String, String> map) throws Exception {
        if (this.isEncrypt) {
            return new BizDataProvider(this.isEncrypt).packageBizData(context, map);
        }
        JSONObject jSONObject = new JSONObject();
        for (String next : map.keySet()) {
            try {
                jSONObject.put(next, map.get(next));
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        return jSONObject.toString();
    }

    private String getInsPassBackData(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEY_INS_UUID, str);
            return Base64.encodeToString(jSONObject.toString().getBytes(), 10);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            r4 = "";
            return "";
        }
    }

    private void jumpAlipayScheme(Context context, String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.setFlags(268435456);
            context.startActivity(intent);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }
}
