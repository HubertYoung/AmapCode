package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.netsdkextdependapi.security.SecurityUtil;
import com.alipay.mobile.common.netsdkextdependapi.security.SignRequest;
import com.alipay.mobile.common.netsdkextdependapi.security.SignResult;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public class RpcSignUtil {

    public class SignData {
        @Deprecated
        public static int OPEN_ENUM_SIGN_ATLAS = SignRequest.SIGN_TYPE_ATLAS;
        private static SignData a;
        public String sign = "";
        public int signType = 0;

        public static final SignData newEmptySignData() {
            if (a == null) {
                a = new SignData();
            }
            return a;
        }

        public static final SignData createSignDataBySignResult(SignResult signResult) {
            if (!signResult.isSuccess()) {
                return newEmptySignData();
            }
            SignData signData = new SignData();
            signData.sign = signResult.sign;
            signData.signType = signResult.signType;
            return signData;
        }
    }

    public static SignData signature(Context context, String externalAppKey, boolean isReq2Online, String content, boolean pUseSignAtlas) {
        try {
            SignRequest signRequest = new SignRequest();
            signRequest.appkey = MpaasPropertiesUtil.getAppkey(externalAppKey, isReq2Online, context);
            signRequest.content = content;
            if (a(context, pUseSignAtlas)) {
                signRequest.signType = SignRequest.SIGN_TYPE_ATLAS;
            }
            return SignData.createSignDataBySignResult(SecurityUtil.signature(signRequest));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "RpcSignUtil", e);
            return SignData.newEmptySignData();
        }
    }

    private static boolean a(Context context, boolean pUseSignAtlas) {
        if (!MiscUtils.isInAlipayClient(context) || MiscUtils.isAlipayLocalPackage(context) || !pUseSignAtlas || !TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SIGN_ATLAS_OPEN), "T")) {
            return false;
        }
        return true;
    }
}
