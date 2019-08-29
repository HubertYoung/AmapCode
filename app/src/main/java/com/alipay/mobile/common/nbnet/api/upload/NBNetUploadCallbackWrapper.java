package com.alipay.mobile.common.nbnet.api.upload;

import com.alipay.mobile.common.nbnet.api.NBNetFactory;
import com.alipay.mobile.common.nbnet.api.NBNetLog;
import java.util.LinkedHashSet;
import java.util.Set;

class NBNetUploadCallbackWrapper implements NBNetUploadCallback {
    private NBNetUploadCallback a;
    private Set<String> b = new LinkedHashSet();

    public void onUploadStart(NBNetUploadRequest req) {
        if (req.getFile() != null) {
            a().info("nbnet_NBNetUploadCallbackWrapper", "onUploadStart. file: " + req.getFile().getAbsolutePath());
        } else {
            a().info("nbnet_NBNetUploadCallbackWrapper", "onUploadStart. ");
        }
        if (this.a != null) {
            try {
                this.a.onUploadStart(req);
            } catch (Throwable e) {
                a().error("nbnet_NBNetUploadCallbackWrapper", "onUploadStart. Business exceptions: ", e);
            }
        }
    }

    public void onUploadProgress(NBNetUploadRequest req, int progress, int fileLength, int uploadedFileLength) {
        this.b.add(String.valueOf(progress));
        if (this.a != null) {
            try {
                this.a.onUploadProgress(req, progress, fileLength, uploadedFileLength);
            } catch (Throwable e) {
                a().error("nbnet_NBNetUploadCallbackWrapper", "onUploadProgress. Business exceptions: ", e);
            }
        }
    }

    public void onUploadFinished(NBNetUploadRequest req, NBNetUploadResponse rsp) {
        String logStr = "onUploadFinished. ";
        if (!this.b.isEmpty()) {
            logStr = logStr + "progress: " + this.b.toString();
        }
        a().info("nbnet_NBNetUploadCallbackWrapper", logStr);
        if (this.a != null) {
            try {
                this.a.onUploadFinished(req, rsp);
            } catch (Throwable e) {
                a().error("nbnet_NBNetUploadCallbackWrapper", "onUploadFinished. Business exceptions: ", e);
            }
        }
    }

    public void onUploadError(NBNetUploadRequest req, int errorCode, String message) {
        String logStr = "onUploadError. ";
        if (!this.b.isEmpty()) {
            logStr = logStr + "progress: " + this.b.toString();
        }
        a().info("nbnet_NBNetUploadCallbackWrapper", logStr.toString());
        if (this.a != null) {
            try {
                this.a.onUploadError(req, errorCode, message);
            } catch (Throwable e) {
                a().error("nbnet_NBNetUploadCallbackWrapper", "onUploadError. Business exceptions: ", e);
            }
        }
    }

    public void setNBNetUploadCallback(NBNetUploadCallback nbNetUploadCallback) {
        if (nbNetUploadCallback != null) {
            a().info("nbnet_NBNetUploadCallbackWrapper", "setNBNetUploadCallback. nbNetUploadCallback hashcode=" + nbNetUploadCallback.hashCode());
            this.a = nbNetUploadCallback;
        }
    }

    private static NBNetLog a() {
        return NBNetFactory.getDefault().getNBNetLog();
    }
}
