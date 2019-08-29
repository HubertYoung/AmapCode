package com.ut.mini.core.sign;

import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.MD5Utils;
import com.alibaba.analytics.utils.RC4;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.security.mobile.module.crypto.CryptoUtil;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class UTBaseRequestAuthentication implements IUTRequestAuthentication {
    private String mAppSecret = null;
    private String mAppkey = null;
    private byte[] mDefaultAppAppSecret = null;
    private boolean mEncode = false;

    public String getAppkey() {
        return this.mAppkey;
    }

    public String getAppSecret() {
        return this.mAppSecret;
    }

    public UTBaseRequestAuthentication(String str, String str2) {
        this.mAppkey = str;
        this.mAppSecret = str2;
    }

    public UTBaseRequestAuthentication(String str, String str2, boolean z) {
        this.mAppkey = str;
        this.mAppSecret = str2;
        this.mEncode = z;
    }

    public boolean isEncode() {
        return this.mEncode;
    }

    public String getSign(String str) {
        String str2;
        if (this.mAppkey == null || this.mAppSecret == null) {
            Logger.e((String) "UTBaseRequestAuthentication", "There is no appkey,please check it!");
            return null;
        } else if (str == null) {
            return null;
        } else {
            try {
                if (this.mEncode) {
                    str2 = calcHmac(this.mAppSecret.getBytes(), str.getBytes());
                } else {
                    str2 = calcHmac(getDefaultAppAppSecret(), str.getBytes());
                }
            } catch (Exception unused) {
                str2 = "";
            }
            return str2;
        }
    }

    private byte[] getDefaultAppAppSecret() {
        if (this.mDefaultAppAppSecret == null) {
            this.mDefaultAppAppSecret = RC4.rc4(new byte[]{66, 37, 42, -119, 118, -104, -30, 4, -95, 15, -26, -12, -75, -102, ImageFileType.HEAD_GIF_0, 23, -3, -120, -1, -57, 42, 99, -16, -101, 103, -74, 93, -114, 112, -26, -24, -24});
        }
        return this.mDefaultAppAppSecret;
    }

    public static String calcHmac(byte[] bArr, byte[] bArr2) throws Exception {
        Mac instance = Mac.getInstance(CryptoUtil.HMAC_SHA1);
        instance.init(new SecretKeySpec(bArr, instance.getAlgorithm()));
        return MD5Utils.toHexString(instance.doFinal(bArr2));
    }
}
