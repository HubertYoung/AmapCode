package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.framework.service.CommonService;

public abstract class SilentDownloadService extends CommonService {

    public interface SilentDownloadCallback {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void onCancel(Request request, String str);

        void onComplete(Request request, Response response, String str);

        void onFailed(Request request, int i, String str, String str2);

        void onProgress(Request request, double d);

        void onStart(Request request, String str);
    }

    public abstract boolean isDownloading(String str, String str2);

    public abstract void startSilentDownload(String str, String str2, SilentDownloadCallback silentDownloadCallback);

    public abstract void stopSilentDownload(String str, String str2);

    public SilentDownloadService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
