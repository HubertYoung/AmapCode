package com.alipay.mobile.tinyappcommon.subpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.download.H5DownloadRequest;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.utils.Callback;

/* compiled from: SubPackagePrepareTask */
public final class e implements Runnable {
    private a a;
    private Callback b;
    private String c;
    private String d;
    private String e;

    /* compiled from: SubPackagePrepareTask */
    private class a extends b {
        private Callback<JSONObject> b;
        private String c;

        public a(c installCallback, Callback<JSONObject> parseCallback, String rootAttrib) {
            super(installCallback);
            this.b = parseCallback;
            this.c = rootAttrib;
        }

        public final void onFailed(@Nullable H5DownloadRequest h5DownloadRequest, int errorCode, String errorMsg) {
            if (this.b == null) {
                H5Log.d("SubPackagePrepareTask", "onFailed...callback is null");
                return;
            }
            JSONObject params = new JSONObject();
            params.put((String) TinyAppSubPackagePlugin.DOWNLOAD_STATUS, (Object) Boolean.valueOf(false));
            params.put((String) TinyAppSubPackagePlugin.ROOT_ATTRIB, (Object) this.c);
            this.b.callback(params);
        }
    }

    /* compiled from: SubPackagePrepareTask */
    private class b implements c {
        private Callback<JSONObject> b;
        private String c;

        public b(Callback<JSONObject> parseCallback, String rootAttrib) {
            this.b = parseCallback;
            this.c = rootAttrib;
        }

        public final void a(boolean success, String downloadUrl) {
            if (this.b == null) {
                H5Log.w("SubPackagePrepareTask", "onResult...parseCallback is null");
                return;
            }
            JSONObject params = new JSONObject();
            params.put((String) TinyAppSubPackagePlugin.INSTALL_STATUS, (Object) Boolean.valueOf(success));
            params.put((String) TinyAppSubPackagePlugin.ROOT_ATTRIB, (Object) this.c);
            params.put((String) TinyAppSubPackagePlugin.DOWNLOAD_URL, (Object) downloadUrl);
            this.b.callback(params);
        }
    }

    public e(String appId, String rootAttrib, String downloadUrl, Callback<JSONObject> parseCallback) {
        this.c = appId;
        this.d = rootAttrib;
        this.e = downloadUrl;
        this.b = parseCallback;
    }

    public final void run() {
        if (TextUtils.isEmpty(this.c) || TextUtils.isEmpty(this.e)) {
            H5Log.w("SubPackagePrepareTask", "prepareTask run...appId or url is null");
            return;
        }
        if (this.a == null) {
            this.a = new a(this.c);
        }
        this.a.a(this.e, (b) new a(new b(this.b, this.d), this.b, this.d));
    }
}
