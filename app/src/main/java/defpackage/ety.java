package defpackage;

import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.danikula.videocache.ProxyCacheException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* renamed from: ety reason: default package */
/* compiled from: FileCache */
public final class ety implements eth {
    public File a;
    private final etx b;
    private RandomAccessFile c;

    public ety(File file, etx etx) throws ProxyCacheException {
        File file2;
        if (etx == null) {
            try {
                throw new NullPointerException();
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder("Error using file ");
                sb.append(file);
                sb.append(" as disc cache");
                throw new ProxyCacheException(sb.toString(), e);
            }
        } else {
            this.b = etx;
            File parentFile = file.getParentFile();
            if (parentFile.exists()) {
                if (!parentFile.isDirectory()) {
                    StringBuilder sb2 = new StringBuilder("File ");
                    sb2.append(parentFile);
                    sb2.append(" is not directory!");
                    throw new IOException(sb2.toString());
                }
            } else if (!parentFile.mkdirs()) {
                throw new IOException(String.format("Directory %s can't be created", new Object[]{parentFile.getAbsolutePath()}));
            }
            boolean exists = file.exists();
            if (exists) {
                file2 = file;
            } else {
                File parentFile2 = file.getParentFile();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(file.getName());
                sb3.append(".download");
                file2 = new File(parentFile2, sb3.toString());
            }
            this.a = file2;
            this.c = new RandomAccessFile(this.a, exists ? UploadQueueMgr.MSGTYPE_REALTIME : "rw");
        }
    }

    public final synchronized long a() throws ProxyCacheException {
        try {
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("Error reading length of file ");
            sb.append(this.a);
            throw new ProxyCacheException(sb.toString(), e);
        }
        return (long) ((int) this.c.length());
    }

    public final synchronized int a(byte[] bArr, long j) throws ProxyCacheException {
        try {
            this.c.seek(j);
        } catch (IOException e) {
            throw new ProxyCacheException(String.format("Error reading %d bytes with offset %d from file[%d bytes] to buffer[%d bytes]", new Object[]{Integer.valueOf(8192), Long.valueOf(j), Long.valueOf(a()), Integer.valueOf(bArr.length)}), e);
        }
        return this.c.read(bArr, 0, 8192);
    }

    public final synchronized void a(byte[] bArr, int i) throws ProxyCacheException {
        try {
            if (d()) {
                StringBuilder sb = new StringBuilder("Error append cache: cache file ");
                sb.append(this.a);
                sb.append(" is completed!");
                throw new ProxyCacheException(sb.toString());
            }
            this.c.seek(a());
            this.c.write(bArr, 0, i);
        } catch (IOException e) {
            throw new ProxyCacheException(String.format("Error writing %d bytes to %s from buffer with size %d", new Object[]{Integer.valueOf(i), this.c, Integer.valueOf(8192)}), e);
        }
    }

    public final synchronized void b() throws ProxyCacheException {
        try {
            this.c.close();
            this.b.a(this.a);
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("Error closing file ");
            sb.append(this.a);
            throw new ProxyCacheException(sb.toString(), e);
        }
    }

    public final synchronized void c() throws ProxyCacheException {
        if (!d()) {
            b();
            File file = new File(this.a.getParentFile(), this.a.getName().substring(0, this.a.getName().length() - 9));
            if (!this.a.renameTo(file)) {
                StringBuilder sb = new StringBuilder("Error renaming file ");
                sb.append(this.a);
                sb.append(" to ");
                sb.append(file);
                sb.append(" for completion!");
                throw new ProxyCacheException(sb.toString());
            }
            this.a = file;
            try {
                this.c = new RandomAccessFile(this.a, UploadQueueMgr.MSGTYPE_REALTIME);
                this.b.a(this.a);
            } catch (IOException e) {
                StringBuilder sb2 = new StringBuilder("Error opening ");
                sb2.append(this.a);
                sb2.append(" as disc cache");
                throw new ProxyCacheException(sb2.toString(), e);
            }
        }
    }

    public final synchronized boolean d() {
        return !this.a.getName().endsWith(".download");
    }
}
