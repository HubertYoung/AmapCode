package com.alipay.mobile.security.faceauth.api;

import android.content.Context;
import com.alipay.mobile.security.bio.module.MicroModule;

public class AntDetectFactory {
    public static int TYPE_FACE = 0;

    public static AntDetector create(Context context, int i, MicroModule microModule) {
        if (context == null) {
            throw new RuntimeException("context Can't be null");
        }
        switch (i) {
            case 0:
                return (AntDetector) a(context, "com.alipay.mobile.security.faceauth.bean.FaceAuthenticator", microModule);
            default:
                throw new RuntimeException("Can't find Authenticator");
        }
    }

    private static Object a(Context context, String str, MicroModule microModule) {
        boolean z = false;
        try {
            return Class.forName(str).getConstructor(new Class[]{Context.class, MicroModule.class}).newInstance(new Object[]{context, microModule});
        } catch (Exception e) {
            return z;
        }
    }
}
