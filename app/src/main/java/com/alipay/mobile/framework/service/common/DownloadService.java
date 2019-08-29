package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.framework.service.CommonService;
import java.util.ArrayList;
import java.util.concurrent.Future;
import org.apache.http.Header;

public abstract class DownloadService extends CommonService {
    public abstract Future<?> addDownload(DownloadRequest downloadRequest);

    @Deprecated
    public abstract Future<?> addDownload(String str, String str2, ArrayList<Header> arrayList, TransportCallback transportCallback);

    public DownloadService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
