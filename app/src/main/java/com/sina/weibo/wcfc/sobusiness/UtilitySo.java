package com.sina.weibo.wcfc.sobusiness;

import android.content.Context;

public class UtilitySo {
    private static UtilitySo sInstance;

    public native String calculateS(Context context, String str);

    public native String generateCheckToken(Context context, String str, String str2);

    public native String getDecryptionString(Context context, String str);

    public native String getIValue(Context context, String str);

    static {
        try {
            System.loadLibrary("utility");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    private UtilitySo() {
    }

    public static synchronized UtilitySo getInstance() {
        UtilitySo utilitySo;
        synchronized (UtilitySo.class) {
            try {
                if (sInstance == null) {
                    sInstance = new UtilitySo();
                }
                utilitySo = sInstance;
            }
        }
        return utilitySo;
    }
}
