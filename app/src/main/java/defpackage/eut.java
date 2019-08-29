package defpackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: eut reason: default package */
/* compiled from: SpeexWriteClient */
public final class eut {
    protected int a = 8000;
    protected int b = 1;
    protected int c = 1;
    protected boolean d = false;
    eus e = null;
    private int f = 0;

    public final void a(String str, int i) {
        this.f = 0;
        this.a = i;
        this.b = 2;
        this.d = true;
        eus eus = new eus(this.f, this.a, this.b, this.c, this.d);
        this.e = eus;
        try {
            eus eus2 = this.e;
            File file = new File(str);
            file.delete();
            eus2.a = new FileOutputStream(file);
            eus eus3 = this.e;
            int i2 = eus3.g;
            int i3 = eus3.l;
            eus3.l = i3 + 1;
            byte[] a2 = eus.a(2, 0, i2, i3, 1, new byte[]{80});
            int i4 = eus3.c;
            int i5 = eus3.b;
            int i6 = eus3.d;
            boolean z = eus3.f;
            int i7 = eus3.e;
            byte[] bArr = new byte[80];
            euq.a(bArr, 0, (String) "Speex   ");
            euq.a(bArr, 8, (String) "speex-1.2rc");
            System.arraycopy(new byte[11], 0, bArr, 17, 11);
            euq.a(bArr, 28, 1);
            euq.a(bArr, 32, 80);
            euq.a(bArr, 36, i4);
            euq.a(bArr, 40, i5);
            euq.a(bArr, 44, 4);
            euq.a(bArr, 48, i6);
            euq.a(bArr, 52, -1);
            euq.a(bArr, 56, 160 << i5);
            euq.a(bArr, 60, z ? 1 : 0);
            euq.a(bArr, 64, i7);
            euq.a(bArr, 68, 0);
            euq.a(bArr, 72, 0);
            euq.a(bArr, 76, 0);
            eus.a(a2, 22, eur.a(eur.a(0, a2, 0, a2.length), bArr, 0, 80));
            eus3.a.write(a2);
            eus3.a.write(bArr);
            int i8 = eus3.g;
            int i9 = eus3.l;
            eus3.l = i9 + 1;
            byte[] a3 = eus.a(0, 0, i8, i9, 1, new byte[]{(byte) ("Encoded with:test by gauss".length() + 8)});
            byte[] bArr2 = new byte[("Encoded with:test by gauss".length() + 8)];
            int length = "Encoded with:test by gauss".length();
            euq.a(bArr2, 0, length);
            euq.a(bArr2, 4, (String) "Encoded with:test by gauss");
            euq.a(bArr2, length + 0 + 4, 0);
            eus.a(a3, 22, eur.a(eur.a(0, a3, 0, a3.length), bArr2, 0, bArr2.length));
            eus3.a.write(a3);
            eus3.a.write(bArr2);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
