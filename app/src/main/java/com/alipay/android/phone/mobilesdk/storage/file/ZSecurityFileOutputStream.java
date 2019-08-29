package com.alipay.android.phone.mobilesdk.storage.file;

import android.content.ContextWrapper;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ZSecurityFileOutputStream extends ZFileOutputStream {
    private ArrayList<byte[]> byteList = new ArrayList<>();
    private int byteSize = 0;
    private ContextWrapper mContext;

    public ZSecurityFileOutputStream(File file, ContextWrapper context) {
        super(file);
        this.mContext = context;
        if (this.mContext == null) {
            throw new NullPointerException();
        }
    }

    public void write(byte[] buffer) {
        this.byteList.add(buffer);
        this.byteSize += buffer.length;
    }

    public void write(byte[] buffer, int byteOffset, int byteCount) {
        int size = byteCount;
        if (byteCount > buffer.length) {
            size = buffer.length;
        }
        byte[] sBuffer = new byte[size];
        System.arraycopy(buffer, byteOffset, sBuffer, 0, size);
        this.byteList.add(sBuffer);
        this.byteSize += size;
    }

    @Deprecated
    public void write(int oneByte) {
        super.write(oneByte);
    }

    public void close() {
        byte[] finalByte = new byte[this.byteSize];
        int pos = 0;
        if (this.byteList != null && !this.byteList.isEmpty()) {
            for (int i = 0; i < this.byteList.size(); i++) {
                byte[] item = this.byteList.get(i);
                if (this.byteSize >= item.length + pos) {
                    System.arraycopy(item, 0, finalByte, pos, item.length);
                }
                pos += item.length;
            }
            this.byteList.clear();
            try {
                byte[] enByte = TaobaoSecurityEncryptor.encrypt(this.mContext, finalByte);
                super.write(enByte, 0, enByte.length);
                super.close();
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }
}
