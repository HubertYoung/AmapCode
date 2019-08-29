package com.alipay.mobile.common.netsdkextdepend.security;

import android.content.ContextWrapper;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.netsdkextdepend.selfutil.EnvUtil;
import com.alipay.mobile.common.netsdkextdependapi.security.SecurityManagerAdapter;
import com.alipay.mobile.common.netsdkextdependapi.security.SignRequest;
import com.alipay.mobile.common.netsdkextdependapi.security.SignResult;
import java.util.HashMap;

public class DefaultSecurityManager extends SecurityManagerAdapter {
    public SignResult signature(SignRequest signRequest) {
        try {
            return a(signRequest);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error("SecurityManager", "[signature] Exception: " + e.toString(), e);
            return SignResult.newEmptySignData();
        }
    }

    public byte[] encrypt(byte[] source) {
        if (EnvUtil.getContext() != null) {
            return TaobaoSecurityEncryptor.encrypt((ContextWrapper) EnvUtil.getContext(), source);
        }
        throw new IllegalStateException("The context in EnvUtil is null");
    }

    public byte[] encrypt(byte[] source, String type) {
        if (EnvUtil.getContext() != null) {
            return TaobaoSecurityEncryptor.encrypt((ContextWrapper) EnvUtil.getContext(), source, type);
        }
        throw new IllegalStateException("The context in EnvUtil is null");
    }

    public byte[] decrypt(byte[] encrypted) {
        if (EnvUtil.getContext() != null) {
            return TaobaoSecurityEncryptor.decrypt((ContextWrapper) EnvUtil.getContext(), encrypted);
        }
        throw new IllegalStateException("The context in EnvUtil is null");
    }

    public byte[] decrypt(byte[] encrypted, String type) {
        if (EnvUtil.getContext() != null) {
            return TaobaoSecurityEncryptor.decrypt((ContextWrapper) EnvUtil.getContext(), encrypted, type);
        }
        throw new IllegalStateException("The context in EnvUtil is null");
    }

    private static SignResult a(SignRequest signRequest) {
        SecurityGuardManager sgMng = SecurityGuardManager.getInstance(EnvUtil.getContext());
        if (sgMng == null) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityManager", (String) "[doSignature] request data sign fail, sgMng is null");
            return SignResult.newEmptySignData();
        }
        ISecureSignatureComponent ssComp = sgMng.getSecureSignatureComp();
        if (ssComp == null) {
            LoggerFactory.getTraceLogger().warn((String) "SecurityManager", (String) "[doSignature] request data sign fail, ssComp is null");
            return SignResult.newEmptySignData();
        }
        SignResult signResult = new SignResult();
        HashMap paramMap = new HashMap();
        paramMap.put("INPUT", signRequest.content);
        SecurityGuardParamContext sgc = new SecurityGuardParamContext();
        sgc.paramMap = paramMap;
        sgc.appKey = signRequest.appkey;
        if (signRequest.isSignTypeMD5()) {
            sgc.requestType = 4;
        } else if (signRequest.isSignTypeHmacSha1()) {
            sgc.requestType = 3;
            signResult.signType = SignRequest.SIGN_TYPE_HMAC_SHA1;
        } else if (signRequest.isSignTypeAtlas()) {
            paramMap.put("ATLAS", "a");
            sgc.requestType = 5;
            signResult.signType = SignRequest.SIGN_TYPE_ATLAS;
        }
        signResult.sign = ssComp.signRequest(sgc, "");
        signResult.setSuccess(true);
        LoggerFactory.getTraceLogger().warn((String) "SecurityManager", "[doSignature] Get security signed string: " + signResult.sign + ",  requestType: " + sgc.requestType + ",  appKey: " + sgc.appKey);
        return signResult;
    }
}
