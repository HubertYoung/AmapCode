package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

/* renamed from: bos reason: default package */
/* compiled from: MultipartEntity */
public final class bos implements boo, boq {
    private static byte[] a = "--------7da3d81520810".getBytes();
    private static byte[] b = MultipartUtility.LINE_FEED.getBytes();
    private static byte[] c = "--".getBytes();
    private byte[] d;
    private String e;
    private String f;
    private bou g;
    private List<defpackage.bpi.a> h;

    /* renamed from: bos$a */
    /* compiled from: MultipartEntity */
    static class a {
        long a;
        long b;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public bos(List<defpackage.bpi.a> list, String str) {
        this.h = list;
        this.f = str == null ? "UTF-8" : str;
        String hexString = Double.toHexString(Math.random() * 65535.0d);
        this.d = hexString.getBytes();
        StringBuilder sb = new StringBuilder("multipart/form-data; boundary=");
        sb.append(new String(a));
        sb.append(hexString);
        this.e = sb.toString();
    }

    public final String b() {
        return this.e;
    }

    public final long a(OutputStream outputStream) throws IOException {
        long j;
        OutputStream outputStream2 = outputStream;
        a aVar = new a(0);
        Iterator<defpackage.bpi.a> it = this.h.iterator();
        while (true) {
            j = 0;
            if (!it.hasNext()) {
                break;
            }
            defpackage.bpi.a next = it.next();
            long j2 = aVar.a;
            if (next.b != null) {
                j = next.b.length();
            }
            aVar.a = j2 + j;
        }
        long j3 = 0;
        for (defpackage.bpi.a next2 : this.h) {
            if (next2.b != null && next2.b.exists()) {
                String str = next2.a;
                File file = next2.b;
                long a2 = a(outputStream2, c, a, this.d) + j;
                String name = file.getName();
                String guessContentTypeFromName = HttpURLConnection.guessContentTypeFromName(name);
                if (guessContentTypeFromName == null) {
                    guessContentTypeFromName = FilePart.DEFAULT_CONTENT_TYPE;
                }
                String replaceFirst = guessContentTypeFromName.replaceFirst("\\/jpg$", "/jpeg");
                StringBuilder sb = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb.append(str);
                sb.append("\"; filename=\"");
                sb.append(name);
                sb.append("\"");
                j3 += a2 + a(outputStream2, sb.toString().getBytes()) + a(outputStream2, "Content-Type: ".concat(String.valueOf(replaceFirst)).getBytes(), b) + a(outputStream2, new FileInputStream(file), aVar);
            } else if (!TextUtils.isEmpty(next2.c)) {
                String str2 = next2.a;
                String str3 = next2.c;
                StringBuilder sb2 = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb2.append(str2);
                sb2.append("\"");
                j3 += a(outputStream2, c, a, this.d) + 0 + a(outputStream2, sb2.toString().getBytes(), b) + a(outputStream2, String.valueOf(str3).getBytes(this.f));
            } else if (bpv.a(5)) {
                StringBuilder sb3 = new StringBuilder("Multipart send error, invalid param: ");
                sb3.append(next2.a == null ? "" : next2.a);
                sb3.append("/");
                sb3.append(next2.b == null ? "" : next2.b.getAbsolutePath());
                bpv.d("ANet-MultipartEntity", sb3.toString());
            }
            j = 0;
        }
        if (j3 > j) {
            j3 += a(outputStream2, c, a, this.d, c);
        }
        outputStream.flush();
        return j3;
    }

    public final void a(bou bou) {
        this.g = bou;
    }

    private static long a(OutputStream outputStream, byte[]... bArr) throws IOException {
        long j = 0;
        for (byte[] bArr2 : bArr) {
            outputStream.write(bArr2);
            j += (long) bArr2.length;
        }
        outputStream.write(b);
        return j + ((long) b.length);
    }

    private long a(OutputStream outputStream, InputStream inputStream, a aVar) throws IOException {
        byte[] bArr = new byte[1024];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read >= 0) {
                outputStream.write(bArr, 0, read);
                j += (long) read;
                if (this.g != null) {
                    this.g.a(aVar.a, aVar.b + j);
                }
            } else {
                aVar.b += j;
                inputStream.close();
                outputStream.write(b);
                return j + ((long) b.length);
            }
        }
    }
}
