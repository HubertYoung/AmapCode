package com.uc.webview.export.cyclone.service;

import android.content.Context;
import com.uc.webview.export.cyclone.Constant;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCKnownException;
import com.uc.webview.export.cyclone.UCLibrary;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.cyclone.UCService;

@Constant
/* compiled from: ProGuard */
public class UCUnSevenZipMultiThreadImpl implements UCUnSevenZip {
    private static final String LOG_TAG = "UCUnSevenZipMultiThreadImplConstant";
    private static boolean mSoIsLoaded = false;
    private static UCKnownException mSoIsLoadedException;

    private static native int dec7z(String str, String str2, String str3);

    public int getServiceVersion() {
        return 0;
    }

    static {
        try {
            UCService.registerImpl(UCUnSevenZip.class, new UCUnSevenZipMultiThreadImpl());
        } catch (Throwable th) {
            UCLogger create = UCLogger.create("w", LOG_TAG);
            if (create != null) {
                create.print("UCUnSevenZipMultiThreadImplConstant register exception:", th);
            }
        }
    }

    public int deccompress(Context context, String str, String str2) {
        loadSo(context);
        int dec7z = dec7z(str, str2, "");
        UCLogger create = !UCCyclone.enableDebugLog ? null : UCLogger.create("d", LOG_TAG);
        if (create != null) {
            create.print("UCUnSevenZipMultiThreadImpl.dec ret=".concat(String.valueOf(dec7z)), new Throwable[0]);
        }
        return dec7z;
    }

    public int deccompress(Context context, String str, String str2, String str3) {
        loadSo(context);
        int dec7z = dec7z(str, str2, str3);
        UCLogger create = !UCCyclone.enableDebugLog ? null : UCLogger.create("d", LOG_TAG);
        if (create != null) {
            create.print("UCUnSevenZipMultiThreadImpl.dec ret=".concat(String.valueOf(dec7z)), new Throwable[0]);
        }
        return dec7z;
    }

    private static synchronized void loadSo(Context context) {
        synchronized (UCUnSevenZipMultiThreadImpl.class) {
            if (!mSoIsLoaded) {
                if (mSoIsLoadedException != null) {
                    throw mSoIsLoadedException;
                }
                try {
                    UCLibrary.load(context, UCCyclone.genFile(context, null, "libdec7zmt", ".so", 25448223, "8f14ec3419b92cb84a97c021b573b73c", UCUnSevenZipMultiThreadImplConstant.genCodes(), new Object[0]).getAbsolutePath(), null);
                    mSoIsLoaded = true;
                } catch (Throwable th) {
                    UCKnownException uCKnownException = new UCKnownException(th);
                    mSoIsLoadedException = uCKnownException;
                    throw uCKnownException;
                }
            }
        }
    }
}
