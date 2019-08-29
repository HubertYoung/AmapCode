package com.mpaas.nebula.provider;

import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandler;
import com.alipay.mobile.nebula.util.H5Log;
import com.mpaas.nebula.NebulaBiz;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class H5ResourceHandlerImpl implements H5ResourceHandler {

    private class ResourceRequest {
        String localId;

        public ResourceRequest(String localId2) {
            this.localId = localId2;
        }

        public InputStream run() {
            try {
                String filePath = NebulaBiz.decodeToPath(this.localId);
                H5Log.d("H5ResourceHandlerImpl", "localId " + this.localId + " filePath:" + filePath);
                if (TextUtils.isEmpty(filePath)) {
                    return null;
                }
                if (filePath.startsWith("file://")) {
                    filePath = filePath.replaceAll("file://", "");
                }
                return new FileInputStream(new File(filePath));
            } catch (Exception e) {
                H5Log.e((String) "H5ResourceHandlerImpl", (Throwable) e);
                return null;
            }
        }
    }

    public WebResourceResponse shouldInterceptRequest(String url) {
        String localId = NebulaBiz.matchLocalId(url, "image");
        if (!TextUtils.isEmpty(localId)) {
            try {
                return new WebResourceResponse("image/jpeg", "utf-8", new ResourceRequest(localId).run());
            } catch (Exception e) {
                H5Log.e((String) "H5ResourceHandlerImpl", (Throwable) e);
            }
        }
        return null;
    }
}
