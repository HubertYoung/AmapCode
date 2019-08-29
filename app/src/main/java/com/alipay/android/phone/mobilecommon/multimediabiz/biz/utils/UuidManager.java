package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidManager {
    private static UuidManager a;
    private Context b = AppUtils.getApplicationContext();
    private UUID c;

    private UuidManager() {
        a();
    }

    public static UuidManager get() {
        if (a == null) {
            synchronized (UuidManager.class) {
                try {
                    a = new UuidManager();
                }
            }
        }
        return a;
    }

    public UUID getUUID() {
        return this.c;
    }

    private synchronized void a() {
        if (this.c == null) {
            this.c = c();
            if (this.c == null) {
                this.c = UUID.randomUUID();
                b();
            }
        }
    }

    private void b() {
        if (this.c != null) {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            buffer.putLong(this.c.getMostSignificantBits());
            buffer.putLong(this.c.getLeastSignificantBits());
            File dataFile = new File(this.b.getFilesDir(), "dj.u");
            try {
                FileUtils.safeCopyToFile(buffer.array(), dataFile);
                FileUtils.copyFile(dataFile, new File(FileUtils.getMediaDir("multimedia"), "dj.u"));
            } catch (IOException e) {
                Logger.E((String) "UuidManager", (Throwable) e, (String) "saveUUID error", new Object[0]);
            }
        }
    }

    private UUID c() {
        byte[] data = d();
        if (data != null) {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            this.c = new UUID(buffer.getLong(), buffer.getLong());
        }
        return this.c;
    }

    private byte[] d() {
        File dataFile = new File(this.b.getFilesDir(), "dj.u");
        if (!FileUtils.checkFile(dataFile)) {
            File sdDataFile = new File(FileUtils.getMediaDir("multimedia"), "dj.u");
            if (FileUtils.checkFile(sdDataFile)) {
                FileUtils.copyFile(sdDataFile, dataFile);
            }
            dataFile = sdDataFile;
        }
        FileInputStream in = null;
        try {
            FileInputStream in2 = new FileInputStream(dataFile);
            try {
                byte[] data = new byte[16];
                if (in2.read(data) != 16) {
                    data = null;
                }
                IOUtils.closeQuietly((InputStream) in2);
                FileInputStream fileInputStream = in2;
                return data;
            } catch (IOException e) {
                e = e;
                in = in2;
            } catch (Throwable th) {
                th = th;
                in = in2;
                IOUtils.closeQuietly((InputStream) in);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            try {
                Logger.E((String) "UuidManager", (Throwable) e, (String) "loadUUID error", new Object[0]);
                IOUtils.closeQuietly((InputStream) in);
                return null;
            } catch (Throwable th2) {
                th = th2;
                IOUtils.closeQuietly((InputStream) in);
                throw th;
            }
        }
    }
}
