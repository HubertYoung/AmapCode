package com.autonavi.miniapp.plugin.safe;

import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.taobao.wireless.security.sdk.SecurityGuardManager;
import com.taobao.wireless.security.sdk.staticdataencrypt.IStaticDataEncryptComponent;

public class TinyAppSafeUtils {
    private static final String KEY = "openUserDataEncrypt";
    private static final String TAG = "TinyAppSafeUtils";

    public static String encrypt(String str) {
        if (TextUtils.isEmpty(str)) {
            H5Log.e((String) TAG, (String) "data is empty, skip encrypt.");
            return null;
        }
        SecurityGuardManager instance = SecurityGuardManager.getInstance(H5Utils.getContext());
        if (instance == null) {
            return null;
        }
        IStaticDataEncryptComponent staticDataEncryptComp = instance.getStaticDataEncryptComp();
        if (staticDataEncryptComp == null) {
            return null;
        }
        try {
            return staticDataEncryptComp.staticSafeEncrypt(16, KEY, str);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("encrypt data error: ");
            sb.append(e.getMessage());
            H5Log.e((String) TAG, sb.toString());
            return null;
        }
    }

    public static String decrypt(String str) {
        if (TextUtils.isEmpty(str)) {
            H5Log.e((String) TAG, (String) "encrypted data is empty, skip decrypt.");
            return null;
        }
        SecurityGuardManager instance = SecurityGuardManager.getInstance(H5Utils.getContext());
        if (instance == null) {
            return null;
        }
        IStaticDataEncryptComponent staticDataEncryptComp = instance.getStaticDataEncryptComp();
        if (staticDataEncryptComp == null) {
            return null;
        }
        try {
            return staticDataEncryptComp.staticSafeDecrypt(16, KEY, str);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("decrypt data error: ");
            sb.append(e.getMessage());
            H5Log.e((String) TAG, sb.toString());
            return null;
        }
    }
}
