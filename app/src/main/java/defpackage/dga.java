package defpackage;

import android.text.TextUtils;
import com.autonavi.common.Callback.a;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* renamed from: dga reason: default package */
/* compiled from: NaviTtsFileLoader */
public final class dga {
    boolean a = true;
    boolean b = false;
    a c;
    a d;
    private String e;
    private long f;
    private String g;

    public dga(String str) {
        this.e = str;
    }

    private File a(InputStream inputStream) throws Exception {
        BufferedInputStream bufferedInputStream;
        FileOutputStream fileOutputStream;
        File file = new File(this.e);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile.exists() || parentFile.mkdirs()) {
                file.createNewFile();
            }
        }
        long j = 0;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            if (this.a) {
                j = file.length();
                fileOutputStream = new FileOutputStream(this.e, true);
            } else {
                fileOutputStream = new FileOutputStream(this.e);
            }
            long j2 = this.f + j;
            bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                try {
                    a(j2, j);
                    if (a()) {
                        ahe.a((Closeable) bufferedInputStream);
                        ahe.a((Closeable) bufferedOutputStream2);
                        return file;
                    } else if (a()) {
                        ahe.a((Closeable) bufferedInputStream);
                        ahe.a((Closeable) bufferedOutputStream2);
                        return file;
                    } else {
                        byte[] bArr = new byte[4096];
                        do {
                            int read = bufferedInputStream.read(bArr);
                            if (read != -1) {
                                bufferedOutputStream2.write(bArr, 0, read);
                                j += (long) read;
                                a(j2, j);
                            } else {
                                bufferedOutputStream2.flush();
                                a(j2, j);
                                ahe.a((Closeable) bufferedInputStream);
                                ahe.a((Closeable) bufferedOutputStream2);
                                if (!file.exists() || !this.b || TextUtils.isEmpty(this.g)) {
                                    return file;
                                }
                                File file2 = new File(file.getParent(), this.g);
                                while (file2.exists()) {
                                    String parent = file.getParent();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(System.currentTimeMillis());
                                    sb.append(this.g);
                                    file2 = new File(parent, sb.toString());
                                }
                                return file.renameTo(file2) ? file2 : file;
                            }
                        } while (!a());
                        ahe.a((Closeable) bufferedInputStream);
                        ahe.a((Closeable) bufferedOutputStream2);
                        return file;
                    }
                } catch (Throwable th) {
                    bufferedOutputStream = bufferedOutputStream2;
                    th = th;
                    ahe.a((Closeable) bufferedInputStream);
                    ahe.a((Closeable) bufferedOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                ahe.a((Closeable) bufferedInputStream);
                ahe.a((Closeable) bufferedOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream = null;
            ahe.a((Closeable) bufferedInputStream);
            ahe.a((Closeable) bufferedOutputStream);
            throw th;
        }
    }

    public final File a(bol bol) throws Exception {
        String str;
        InputStream inputStream = null;
        try {
            this.f = a(bol.d());
            int c2 = bol.c();
            if (c2 == 400 || c2 == 416) {
                File file = this.e != null ? new File(this.e) : null;
                if (file != null && file.exists()) {
                    ahe.a((Closeable) null);
                    return file;
                }
            }
            if (this.b) {
                String b2 = bps.b(bol.d(), "Content-Length");
                if (!TextUtils.isEmpty(b2)) {
                    int indexOf = b2.indexOf("filename=");
                    if (indexOf > 0) {
                        int i = indexOf + 9;
                        int indexOf2 = b2.indexOf(";", i);
                        if (indexOf2 < 0) {
                            indexOf2 = b2.length();
                        }
                        str = b2.substring(i, indexOf2);
                        this.g = str;
                    }
                }
                str = null;
                this.g = str;
            }
            InputStream b3 = bol.b();
            try {
                File a2 = a(b3);
                ahe.a((Closeable) b3);
                return a2;
            } catch (Throwable th) {
                Throwable th2 = th;
                inputStream = b3;
                th = th2;
                ahe.a((Closeable) inputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            ahe.a((Closeable) inputStream);
            throw th;
        }
    }

    private boolean a() {
        if (this.c != null) {
            return this.c.isCancelled();
        }
        return false;
    }

    private void a(long j, long j2) {
        if (this.d != null) {
            this.d.a(j, j2);
        }
    }

    private static long a(Map<String, List<String>> map) {
        String b2 = bps.b(map, "Content-Length");
        if (TextUtils.isEmpty(b2)) {
            return -1;
        }
        try {
            return Long.parseLong(b2);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }
}
