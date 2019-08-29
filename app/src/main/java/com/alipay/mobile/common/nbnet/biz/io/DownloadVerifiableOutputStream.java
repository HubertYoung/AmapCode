package com.alipay.mobile.common.nbnet.biz.io;

import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.biz.download.ProgressObserver;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetVerifyException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.MD5Utils;
import com.alipay.mobile.common.utils.HexStringUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;

public class DownloadVerifiableOutputStream extends FileOutputStream {
    private static final String a = DownloadVerifiableOutputStream.class.getSimpleName();
    private final File b;
    private final int c;
    private final String d;
    private final ProgressObserver e;
    private int f = 0;
    private MessageDigest g;

    public DownloadVerifiableOutputStream(File localFile, int fileLenght, String fileMd5, ProgressObserver progressListener) {
        super(localFile, true);
        this.b = localFile;
        this.c = fileLenght;
        this.d = fileMd5;
        this.e = progressListener;
        b();
    }

    private void b() {
        if (this.b.exists() && this.b.length() > 0) {
            this.f = (int) this.b.length();
            c();
        } else if (this.d != null) {
            try {
                this.g = MessageDigest.getInstance("MD5");
            } catch (Throwable e2) {
                NBNetLogCat.b(a, e2);
            }
        }
    }

    private void c() {
        if (this.e != null && this.f > 0 && this.c > 0) {
            this.e.a(this.b, this.f, this.c);
        }
    }

    public final void a() {
        if (!this.b.exists()) {
            throw new NBNetVerifyException("file not found:" + this.b.getAbsolutePath());
        } else if (this.c != 0 && ((long) this.c) != this.b.length()) {
            throw new NBNetVerifyException("download response filelength not matching, except " + this.c + ", but " + this.b.length());
        } else if (this.d != null) {
            byte[] cacheFileMd5Byte = null;
            if (this.g != null) {
                cacheFileMd5Byte = this.g.digest();
            }
            if (cacheFileMd5Byte == null) {
                long startTime = System.currentTimeMillis();
                cacheFileMd5Byte = MD5Utils.a(this.b);
                NBNetLogCat.a(a, String.format("monitor_perf: digest from cache file, cost_time: %d", new Object[]{Long.valueOf(System.currentTimeMillis() - startTime)}));
            }
            long startTime2 = System.currentTimeMillis();
            String cacheFileMd5 = HexStringUtil.bytesToHexString(cacheFileMd5Byte);
            NBNetLogCat.b(a, String.format("monitor_perf: encodeHexString md5 byte len: %d, cost_time: %d", new Object[]{Integer.valueOf(cacheFileMd5Byte.length), Long.valueOf(System.currentTimeMillis() - startTime2)}));
            if (!TextUtils.equals(this.d, cacheFileMd5)) {
                throw new NBNetVerifyException("download response md5 not matching, except " + this.d + ", but " + cacheFileMd5);
            }
        }
    }

    public void write(byte[] buffer, int byteOffset, int byteCount) {
        super.write(buffer, byteOffset, byteCount);
        this.f += byteCount;
        c();
        if (this.g != null) {
            this.g.update(buffer, byteOffset, byteCount);
        }
    }

    public void write(int oneByte) {
        super.write(oneByte);
        this.f++;
        c();
        if (this.g != null) {
            this.g.update((byte) oneByte);
        }
    }
}
