package com.alipay.android.phone.inside.offlinecode.biz;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.common.util.RandamUtil;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.rpc.ScardCenterRpcProvider;
import com.alipay.android.phone.inside.offlinecode.rpc.model.ScardCenterRes;
import com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider;
import com.alipay.android.phone.inside.wallet.model.INotifyChecker;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.util.HashMap;
import java.util.Map;

public class IdentifyVerify {
    static final String CODE_SUCESS = "SUCCESS";
    static final Map<String, IdentifyVerify> NOTIFY_MAP = new HashMap();
    /* access modifiers changed from: private */
    public String initBizId;
    /* access modifiers changed from: private */
    public String initVerifyId;

    public class VerifyResult {
        public String bizId;
        public boolean success;

        public VerifyResult() {
        }
    }

    public boolean verify(Context context, String str) throws Exception {
        show(context);
        return verifyNow(context, str);
    }

    private void show(Context context) {
        String a = RandamUtil.a();
        NOTIFY_MAP.put(a, this);
        BusVerifyConfirmDialog.show(context, a);
        synchronized (this) {
            try {
                wait();
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        }
    }

    public static void onConfirm(String str) {
        IdentifyVerify identifyVerify = NOTIFY_MAP.get(str);
        if (identifyVerify != null) {
            synchronized (identifyVerify) {
                identifyVerify.notifyAll();
            }
            NOTIFY_MAP.remove(str);
        }
    }

    private boolean verifyNow(Context context, String str) throws Exception {
        try {
            return verifyImpl(context, str);
        } catch (TimeoutException e) {
            throw e;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return false;
        }
    }

    private boolean verifyImpl(Context context, String str) throws Exception {
        ScardCenterRpcProvider scardCenterRpcProvider = new ScardCenterRpcProvider();
        ScardCenterRes verifyRequest = scardCenterRpcProvider.verifyRequest(context, RandamUtil.a(), false, null);
        if (TextUtils.equals(verifyRequest.code, "SUCCESS")) {
            this.initBizId = verifyRequest.getJSONResult().optString("bizId", "");
            this.initVerifyId = verifyRequest.getJSONResult().optString(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, "");
            if (!jumpAlipayVerify(context, str, this.initBizId, this.initVerifyId).success) {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "BusVerityFail", "start verify failed!");
                return false;
            } else if (TextUtils.equals(scardCenterRpcProvider.verifyRequest(context, this.initBizId, true, this.initVerifyId).code, "SUCCESS")) {
                return true;
            } else {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "BusVerityFail", "scardcenter submit failed!");
                return false;
            }
        } else {
            LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "BusVerityFail", "scardcenter init failed!");
            return false;
        }
    }

    private VerifyResult jumpAlipayVerify(Context context, String str, String str2, String str3) throws TimeoutException {
        JumpAlipaySchemeProvider jumpAlipaySchemeProvider = new JumpAlipaySchemeProvider();
        HashMap hashMap = new HashMap();
        hashMap.put("bizId", str2);
        hashMap.put(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, str3);
        VerifyResult verifyResult = new VerifyResult();
        try {
            if (TextUtils.equals(jumpAlipaySchemeProvider.jumpScheme(context, JumpAlipaySchemeProvider.BIZ_VERITY, str, hashMap, getNotifyChecker()).getString("resultCode"), "SUCCESS")) {
                verifyResult.success = true;
            }
        } catch (TimeoutException e) {
            throw e;
        } catch (Throwable th) {
            verifyResult.success = false;
            LoggerFactory.f().c((String) "buscde", th);
        }
        return verifyResult;
    }

    private INotifyChecker getNotifyChecker() {
        return new INotifyChecker() {
            public boolean illegel(Bundle bundle) {
                if (bundle == null) {
                    return false;
                }
                if (!bundle.getBoolean("insideFlag", false)) {
                    LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "VerifyNotifyInsideFlagIllegel");
                    return true;
                }
                String string = bundle.getString("bizId");
                String string2 = bundle.getString(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID);
                if (TextUtils.equals(string, IdentifyVerify.this.initBizId) && TextUtils.equals(string2, IdentifyVerify.this.initVerifyId)) {
                    return false;
                }
                StringBuilder sb = new StringBuilder("bizId=");
                sb.append(string);
                sb.append(",verifyId=");
                sb.append(string2);
                sb.append(",initBizId=");
                sb.append(IdentifyVerify.this.initBizId);
                sb.append(",initVerifyId=");
                sb.append(IdentifyVerify.this.initVerifyId);
                String sb2 = sb.toString();
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "VerifyNotifyIdIllegel", sb2);
                return true;
            }
        };
    }
}
