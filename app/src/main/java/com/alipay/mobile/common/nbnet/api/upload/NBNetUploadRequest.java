package com.alipay.mobile.common.nbnet.api.upload;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.NBNetActionController;
import com.alipay.mobile.common.transport.http.HeaderMap;
import java.io.File;
import java.util.HashMap;

public class NBNetUploadRequest {
    public static final byte CONTENT_TYPE_DATAS = 1;
    public static final byte CONTENT_TYPE_FILE = 2;
    public static final byte CONTENT_TYPE_STREAM = 3;
    private File a;
    private byte[] b;
    private String c;
    private String d;
    private NBNetUploadCallbackWrapper e = new NBNetUploadCallbackWrapper();
    private byte f = -1;
    private NBNetActionController g;
    private HashMap<String, String> h = null;
    private boolean i = false;
    private HeaderMap<String, String> j;

    public NBNetUploadRequest(File file, String bizId, NBNetUploadCallback callback) {
        if (file == null) {
            throw new IllegalArgumentException("file may be null");
        } else if (!file.exists()) {
            throw new IllegalArgumentException(file.toString() + " file no exists");
        } else {
            this.a = file;
            this.c = bizId;
            this.e.setNBNetUploadCallback(callback);
            this.f = 2;
        }
    }

    public NBNetUploadRequest(byte[] data, String bizId, NBNetUploadCallback callback) {
        if (data == null) {
            throw new IllegalArgumentException("data may be null");
        }
        this.b = data;
        this.c = bizId;
        this.e.setNBNetUploadCallback(callback);
        this.f = 1;
    }

    public File getFile() {
        return this.a;
    }

    public byte[] getData() {
        return this.b;
    }

    public String getBizId() {
        return this.c;
    }

    public void setBizId(String bizId) {
        this.c = bizId;
    }

    public NBNetUploadCallback getCallbackWrapper() {
        return this.e;
    }

    public void setCallbackWrapper(NBNetUploadCallback callback) {
        this.e.setNBNetUploadCallback(this.e);
    }

    public byte getContentType() {
        return this.f;
    }

    public void setContentType(byte contentType) {
        this.f = contentType;
    }

    public NBNetActionController getNBNetActionController() {
        return this.g;
    }

    public void setNBNetActionController(NBNetActionController nbNetActionController) {
        this.g = nbNetActionController;
    }

    public boolean isRepeatable() {
        return this.f == 1 || this.f == 2;
    }

    public void cancel() {
        if (this.g != null) {
            this.g.cancel();
        }
    }

    public void stop() {
        if (this.g != null) {
            this.g.stop();
        }
    }

    public boolean isDataContentType() {
        return getContentType() == 1;
    }

    public boolean isFileContentType() {
        return getContentType() == 2;
    }

    public boolean isChunkedContentType() {
        return getContentType() == 3;
    }

    public String getFileNameExt() {
        return this.d;
    }

    public void setFileNameExt(String fileNameExt) {
        this.d = fileNameExt;
    }

    public void setExtInfo(String key, String value) {
        if (this.h == null) {
            this.h = new HashMap<>(1);
        }
        this.h.put(key, value);
    }

    public String getExtInfo(String key) {
        if (this.h == null) {
            return "";
        }
        return this.h.get(key);
    }

    public HashMap<String, String> getExtMap() {
        return this.h;
    }

    public boolean isPublicScope() {
        return this.i;
    }

    public void setPublicScope(boolean publicScope) {
        this.i = publicScope;
    }

    public void addHeader(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key maybe empty or null。");
        } else if (TextUtils.isEmpty(value)) {
            throw new IllegalArgumentException("value maybe empty or null。");
        } else {
            synchronized (this) {
                getHeaderMap().put(key, value);
            }
        }
    }

    public void removeHeader(String key) {
        if (this.j != null && !this.j.isEmpty()) {
            synchronized (this) {
                this.j.remove(key);
            }
        }
    }

    public HeaderMap<String, String> getHeaderMap() {
        if (this.j != null) {
            return this.j;
        }
        synchronized (this) {
            if (this.j == null) {
                this.j = new HeaderMap<>(3);
            }
        }
        return this.j;
    }

    public HeaderMap<String, String> refHeaderMap() {
        return this.j;
    }
}
