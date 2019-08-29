package com.alipay.mobile.common.transport.http.selfencrypt;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MpaasNetConfigUtil;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;

public class SelfEncryptUtils {
    public static boolean isRpcEncryptSwitchOn() {
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RPC_SELF_ENCTYPT), "T")) {
            return true;
        }
        return false;
    }

    public static boolean isNeedSelfEncrypt() {
        return MpaasNetConfigUtil.isCrypt(TransportEnvUtil.getContext());
    }

    public static boolean isDefaultGlobalCrypt() {
        return MpaasNetConfigUtil.isDefaultGlobalCrypt(TransportEnvUtil.getContext());
    }

    public static boolean isInGwWhiteList(String gwUrl) {
        if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            return true;
        }
        return MpaasNetConfigUtil.getGWWhiteList(TransportEnvUtil.getContext()).contains(gwUrl);
    }

    public static AbstractHttpEntity getEncryptedEntity(byte[] data, ClientRpcPack clientPack, HttpUrlRequest originRequest) {
        LogCatUtil.debug("SelfEncryptUtils", "start encrypted...");
        try {
            if (!isNeedSelfEncrypt()) {
                return new ByteArrayEntity(data);
            }
            byte[] encryptRequestData = clientPack.encrypt(data);
            originRequest.setReqData(encryptRequestData);
            AbstractHttpEntity entity = new ByteArrayEntity(encryptRequestData);
            entity.setContentEncoding("mgss");
            LogCatUtil.debug("SelfEncryptUtils", "after encrypted,len: " + encryptRequestData.length);
            return entity;
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().error((String) "SelfEncryptUtils", "getEncryptedEntity ex:" + ex.toString());
            throw ex;
        }
    }

    public static byte[] getDecryptedContent(byte[] encryptData, ClientRpcPack clientPack) {
        LogCatUtil.debug("SelfEncryptUtils", "start decrypted...");
        try {
            if (!isNeedSelfEncrypt()) {
                return encryptData;
            }
            byte[] decryptContent = clientPack.decrypt(encryptData);
            LogCatUtil.debug("SelfEncryptUtils", "after decrypted,len: " + decryptContent.length);
            return decryptContent;
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().error((String) "SelfEncryptUtils", "getDecryptedContent ex:" + ex.toString());
            throw ex;
        }
    }
}
