package com.tencent.open.web.security;

import com.tencent.open.a.b;
import com.tencent.open.a.f;

/* compiled from: ProGuard */
public class SecureJsInterface extends b {
    public static boolean isPWDEdit = false;
    private String a;

    public boolean customCallback() {
        return true;
    }

    public void curPosFromJS(String str) {
        int i;
        f.b("openSDK_LOG.SecureJsInterface", "-->curPosFromJS: ".concat(String.valueOf(str)));
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            f.b("openSDK_LOG.SecureJsInterface", "-->curPosFromJS number format exception.", e);
            i = -1;
        }
        if (i < 0) {
            throw new RuntimeException("position is illegal.");
        }
        boolean z = a.c;
        if (!a.b) {
            this.a = a.a;
            JniInterface.insetTextToArray(i, this.a, this.a.length());
            StringBuilder sb = new StringBuilder("curPosFromJS mKey: ");
            sb.append(this.a);
            f.a("openSDK_LOG.SecureJsInterface", sb.toString());
        } else if (JniInterface.BackSpaceChar(a.b, i)) {
            a.b = false;
        }
    }

    public void isPasswordEdit(String str) {
        int i;
        f.c("openSDK_LOG.SecureJsInterface", "-->is pswd edit, flag: ".concat(String.valueOf(str)));
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("-->is pswd edit exception: ");
            sb.append(e.getMessage());
            f.e("openSDK_LOG.SecureJsInterface", sb.toString());
            i = -1;
        }
        if (i != 0 && i != 1) {
            throw new RuntimeException("is pswd edit flag is illegal.");
        } else if (i == 0) {
            isPWDEdit = false;
        } else {
            if (i == 1) {
                isPWDEdit = true;
            }
        }
    }

    public void clearAllEdit() {
        f.c("openSDK_LOG.SecureJsInterface", "-->clear all edit.");
        try {
            JniInterface.clearAllPWD();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("-->clear all edit exception: ");
            sb.append(e.getMessage());
            f.e("openSDK_LOG.SecureJsInterface", sb.toString());
            throw new RuntimeException(e);
        }
    }

    public String getMD5FromNative() {
        f.c("openSDK_LOG.SecureJsInterface", "-->get md5 form native");
        try {
            String pWDKeyToMD5 = JniInterface.getPWDKeyToMD5(null);
            f.a("openSDK_LOG.SecureJsInterface", "-->getMD5FromNative, MD5= ".concat(String.valueOf(pWDKeyToMD5)));
            return pWDKeyToMD5;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("-->get md5 form native exception: ");
            sb.append(e.getMessage());
            f.e("openSDK_LOG.SecureJsInterface", sb.toString());
            throw new RuntimeException(e);
        }
    }
}
