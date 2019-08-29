package defpackage;

import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

/* renamed from: bnb reason: default package */
/* compiled from: MultipartEntity */
final class bnb {
    private static byte[] b = "--------7da3d81520810".getBytes();
    private static byte[] c = MultipartUtility.LINE_FEED.getBytes();
    private static byte[] d = "--".getBytes();
    String a;
    private byte[] e;
    private String f;
    private List<bna> g;

    public bnb(List<bna> list, String str) {
        this.g = list;
        this.f = str;
        String hexString = Double.toHexString(Math.random() * 65535.0d);
        this.e = hexString.getBytes();
        StringBuilder sb = new StringBuilder("multipart/form-data; boundary=");
        sb.append(new String(b));
        sb.append(hexString);
        this.a = sb.toString();
    }

    public final void a(OutputStream outputStream) throws IOException {
        byte[] bArr;
        for (bna next : this.g) {
            String str = this.f;
            String str2 = next.a;
            Object obj = next.b;
            a(outputStream, d, b, this.e);
            if (obj instanceof File) {
                File file = (File) obj;
                String name = file.getName();
                String guessContentTypeFromName = HttpURLConnection.guessContentTypeFromName(name);
                if (guessContentTypeFromName == null) {
                    guessContentTypeFromName = FilePart.DEFAULT_CONTENT_TYPE;
                }
                String replaceFirst = guessContentTypeFromName.replaceFirst("\\/jpg$", "/jpeg");
                StringBuilder sb = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb.append(str2);
                sb.append("\"; filename=\"");
                sb.append(name);
                sb.append("\"");
                a(outputStream, sb.toString().getBytes());
                a(outputStream, "Content-Type: ".concat(String.valueOf(replaceFirst)).getBytes(), c);
                a(outputStream, (InputStream) new FileInputStream(file));
            } else {
                StringBuilder sb2 = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb2.append(str2);
                sb2.append("\"");
                a(outputStream, sb2.toString().getBytes(), c);
                if (obj instanceof InputStream) {
                    a(outputStream, (InputStream) obj);
                } else {
                    if (obj instanceof byte[]) {
                        bArr = (byte[]) obj;
                    } else {
                        bArr = String.valueOf(obj).getBytes(str);
                    }
                    a(outputStream, bArr);
                }
            }
        }
        a(outputStream, d, b, this.e, d);
        outputStream.flush();
    }

    private static void a(OutputStream outputStream, byte[]... bArr) throws IOException {
        for (byte[] write : bArr) {
            outputStream.write(write);
        }
        outputStream.write(c);
    }

    private static void a(OutputStream outputStream, InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read >= 0) {
                outputStream.write(bArr, 0, read);
            } else {
                inputStream.close();
                outputStream.write(c);
                return;
            }
        }
    }
}
