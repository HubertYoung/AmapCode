package com.alipay.mobile.worker;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WorkerContentProvider {
    private H5ContentPackage a;

    public WorkerContentProvider(H5ContentPackage contentPackage) {
        this.a = contentPackage;
    }

    public WebResourceResponse getContent(String originUrl) {
        byte[] data = null;
        try {
            data = H5ServiceUtils.getH5Service().getH5GlobalDegradePkg(originUrl);
        } catch (Throwable thr) {
            H5Log.e("WorkerContentProvider", "getResource error!", thr);
        }
        if (data == null) {
            String pureUrl = H5UrlHelper.purifyUrl(originUrl);
            if (this.a != null) {
                data = this.a.get(pureUrl);
            }
        }
        if (data != null) {
            return a(originUrl, new ByteArrayInputStream(data));
        }
        return null;
    }

    private static WebResourceResponse a(String url, InputStream inputStream) {
        String mimeType = null;
        if (!TextUtils.isEmpty(url)) {
            Uri uri = H5UrlHelper.parseUrl(url);
            if (uri != null) {
                mimeType = H5FileUtil.getMimeType(uri.getPath());
            }
        }
        return new WebResourceResponse(mimeType, "UTF-8", inputStream);
    }
}
