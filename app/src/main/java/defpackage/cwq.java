package defpackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/* renamed from: cwq reason: default package */
/* compiled from: CharacterEncoder */
public abstract class cwq {
    protected PrintStream a;

    /* access modifiers changed from: protected */
    public abstract void a(OutputStream outputStream, byte[] bArr, int i, int i2) throws IOException;

    private static int a(InputStream inputStream, byte[] bArr) throws IOException {
        for (int i = 0; i < 57; i++) {
            int read = inputStream.read();
            if (read == -1) {
                return i;
            }
            bArr[i] = (byte) read;
        }
        return 57;
    }

    private void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        int a2;
        byte[] bArr = new byte[57];
        this.a = new PrintStream(outputStream);
        do {
            a2 = a(inputStream, bArr);
            if (a2 != 0) {
                int i = 0;
                while (i < a2) {
                    int i2 = i + 3;
                    if (i2 <= a2) {
                        a(outputStream, bArr, i, 3);
                    } else {
                        a(outputStream, bArr, i, a2 - i);
                    }
                    i = i2;
                }
            } else {
                return;
            }
        } while (a2 >= 57);
    }

    public final String a(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a((InputStream) new ByteArrayInputStream(bArr), (OutputStream) byteArrayOutputStream);
            return byteArrayOutputStream.toString("8859_1");
        } catch (Exception unused) {
            throw new Error("CharacterEncoder.encode internal error");
        }
    }
}
