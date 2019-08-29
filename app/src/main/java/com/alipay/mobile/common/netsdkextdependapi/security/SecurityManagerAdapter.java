package com.alipay.mobile.common.netsdkextdependapi.security;

import com.alipay.mobile.common.netsdkextdependapi.InnerMiscUtil;
import java.util.logging.Level;

public class SecurityManagerAdapter implements SecurityManager {
    public SignResult signature(SignRequest signRequest) {
        if (InnerMiscUtil.logger.isLoggable(Level.INFO)) {
            InnerMiscUtil.logger.info("[SecurityManagerAdapter#signature] No signature logic implemented.");
        }
        return SignResult.newEmptySignData();
    }

    public byte[] encrypt(byte[] source) {
        if (source == null) {
            return new byte[0];
        }
        return source;
    }

    public byte[] encrypt(byte[] source, String type) {
        if (source == null) {
            return new byte[0];
        }
        return source;
    }

    public byte[] decrypt(byte[] encrypted) {
        if (encrypted == null) {
            return new byte[0];
        }
        return encrypted;
    }

    public byte[] decrypt(byte[] encrypted, String type) {
        if (encrypted == null) {
            return new byte[0];
        }
        return encrypted;
    }
}
