package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: bxq reason: default package */
/* compiled from: CompatHelper */
public final class bxq {
    private static bxq a;
    private Lock b = new ReentrantLock();
    private Context c;

    private bxq(Context context) {
        this.c = context;
    }

    public static synchronized bxq a(Context context) {
        bxq bxq;
        synchronized (bxq.class) {
            try {
                if (a == null) {
                    a = new bxq(context);
                }
                bxq = a;
            }
        }
        return bxq;
    }

    public final boolean a() {
        FileOutputStream fileOutputStream;
        this.b.lock();
        try {
            String b2 = b();
            InputStream inputStream = null;
            File file = !TextUtils.isEmpty(b2) ? new File(b2) : null;
            if (file == null || (file.exists() && file.isFile())) {
                this.b.unlock();
                return true;
            }
            File parentFile = file.getParentFile();
            if (parentFile != null && (!parentFile.exists() || !parentFile.isDirectory())) {
                parentFile.mkdirs();
            }
            try {
                InputStream open = this.c.getApplicationContext().getAssets().open("common/AJXDB_landmark.db");
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = null;
                    inputStream = open;
                    try {
                        e.printStackTrace();
                        ahe.a((Closeable) inputStream);
                        ahe.a((Closeable) fileOutputStream);
                        this.b.unlock();
                        return true;
                    } catch (Throwable th) {
                        th = th;
                        ahe.a((Closeable) inputStream);
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream = null;
                    inputStream = open;
                    ahe.a((Closeable) inputStream);
                    ahe.a((Closeable) fileOutputStream);
                    throw th;
                }
                try {
                    ahe.a(open, fileOutputStream);
                    ahe.a((Closeable) open);
                    ahe.a((Closeable) fileOutputStream);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    inputStream = open;
                    e.printStackTrace();
                    ahe.a((Closeable) inputStream);
                    ahe.a((Closeable) fileOutputStream);
                    this.b.unlock();
                    return true;
                } catch (Throwable th3) {
                    th = th3;
                    inputStream = open;
                    ahe.a((Closeable) inputStream);
                    ahe.a((Closeable) fileOutputStream);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                fileOutputStream = null;
                e.printStackTrace();
                ahe.a((Closeable) inputStream);
                ahe.a((Closeable) fileOutputStream);
                this.b.unlock();
                return true;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                ahe.a((Closeable) inputStream);
                ahe.a((Closeable) fileOutputStream);
                throw th;
            }
        } finally {
            this.b.unlock();
        }
    }

    public final String b() {
        if (this.c == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 17) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.c.getApplicationInfo().dataDir);
            sb.append("/databases/AJXDB_landmark.db");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder("/data/data/");
        sb2.append(this.c.getPackageName());
        sb2.append("/databases/AJXDB_landmark.db");
        return sb2.toString();
    }
}
