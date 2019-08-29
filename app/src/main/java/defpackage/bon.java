package defpackage;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/* renamed from: bon reason: default package */
/* compiled from: FileUploadEntity */
public final class bon implements boo, bop, boq {
    public String a;
    private File b;
    private bou c;

    public bon(File file) {
        this.b = file;
    }

    public final void a(bou bou) {
        this.c = bou;
    }

    public final int a() {
        if (this.b == null) {
            return 0;
        }
        return (int) this.b.length();
    }

    public final String b() {
        if (!TextUtils.isEmpty(this.a)) {
            return this.a;
        }
        StringBuilder sb = new StringBuilder("multipart/form-data;file=");
        sb.append(this.b == null ? "" : this.b.getName());
        return sb.toString();
    }

    public final long a(OutputStream outputStream) throws IOException {
        FileInputStream fileInputStream;
        long j = 0;
        if (this.b == null || !this.b.exists()) {
            if (bpv.a(5)) {
                StringBuilder sb = new StringBuilder("upload error, invalid file");
                sb.append(this.b != null ? this.b.getAbsolutePath() : "!");
                bpv.d("ANet-FileUploadEntity", sb.toString());
            }
            return 0;
        }
        try {
            fileInputStream = new FileInputStream(this.b);
            try {
                byte[] bArr = new byte[65536];
                long length = this.b.length();
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read >= 0) {
                        outputStream.write(bArr, 0, read);
                        j += (long) read;
                        if (this.c != null) {
                            this.c.a(length, j);
                        }
                    } else {
                        outputStream.flush();
                        bow.a(fileInputStream);
                        return j;
                    }
                }
            } catch (Throwable th) {
                th = th;
                bow.a(fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            bow.a(fileInputStream);
            throw th;
        }
    }
}
