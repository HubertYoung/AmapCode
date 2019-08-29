package com.alipay.android.phone.mobilesdk.storage.file;

import android.content.ContextWrapper;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.File;
import java.io.IOException;

public class ZSecurityFileInputStream extends ZFileInputStream {
    private static final String TAG = "ZSecurityFileInputStream";
    private int bytePos;
    private byte[] mBuffer;
    private ContextWrapper mContext;

    public ZSecurityFileInputStream(File file, ContextWrapper context) {
        super(file);
        this.mContext = context;
        if (this.mContext == null) {
            throw new NullPointerException();
        }
    }

    @Deprecated
    public long skip(long byteCount) {
        return super.skip(byteCount);
    }

    @Deprecated
    public int read() {
        return super.read();
    }

    public int available() {
        if (this.mBuffer == null) {
            initBuffer();
        }
        return this.mBuffer.length;
    }

    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) {
        if (this.mBuffer == null) {
            initBuffer();
        }
        if (this.mBuffer == null) {
            throw new IOException("TaobaoSecurityEncryptor.decrypt = null");
        }
        int totalLen = this.mBuffer.length;
        if (this.bytePos >= totalLen) {
            return -1;
        }
        if (this.bytePos + byteCount > totalLen) {
            byteCount = totalLen - this.bytePos;
        }
        System.arraycopy(this.mBuffer, this.bytePos, buffer, byteOffset, byteCount);
        this.bytePos += byteCount;
        return byteCount;
    }

    private void initBuffer() {
        int size = super.available();
        byte[] enBuffer = new byte[size];
        super.read(enBuffer, 0, size);
        super.close();
        try {
            this.mBuffer = TaobaoSecurityEncryptor.decrypt(this.mContext, enBuffer);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
            throw new IOException();
        }
    }
}
