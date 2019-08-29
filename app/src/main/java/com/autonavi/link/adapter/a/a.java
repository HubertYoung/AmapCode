package com.autonavi.link.adapter.a;

import com.autonavi.link.adapter.endian.ByteOrderedInputStream;
import com.autonavi.link.adapter.endian.LittleEndianInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/* compiled from: DispatcherEngine */
public class a {
    private ByteOrderedInputStream a;
    private byte[] b;
    private b c;

    public void a(b bVar) {
        this.c = bVar;
    }

    public synchronized void a(byte[] bArr, int i) throws IOException {
        this.b = bArr;
        this.a = new LittleEndianInputStream(new ByteArrayInputStream(this.b));
        switch (i) {
            case 34:
                b();
                break;
            case 35:
                c();
                break;
            case 36:
                f();
                break;
            case 38:
                i();
                break;
            case 42:
                d();
                break;
            case 45:
                j();
                break;
            case 46:
                k();
                break;
            case 51:
                a();
                g();
                break;
            case 52:
                h();
                break;
            case 54:
                e();
                break;
        }
        this.a.close();
    }

    private String a() {
        byte b2 = this.b[5];
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf((b2 & 240) >> 4));
        sb.append(".");
        sb.append(b2 & 15);
        return sb.toString();
    }

    private void b() {
        if (this.c != null) {
            this.c.f();
        }
    }

    private void c() {
        if (this.c != null) {
            try {
                this.c.a(this.a.readInt(), this.a.readInt());
            } catch (IOException unused) {
            }
        }
    }

    private void d() {
        if (this.c != null) {
            try {
                this.c.a(this.a.readBoolean());
            } catch (IOException unused) {
            }
        }
    }

    private void e() {
        if (this.c != null) {
            try {
                this.c.a(this.a.readInt());
            } catch (IOException unused) {
            }
        }
    }

    private void f() {
        if (this.c != null) {
            try {
                this.c.a(this.a.readBoolean(), this.a.read(), this.a.readBoolean());
            } catch (IOException unused) {
            }
        }
    }

    private void g() {
        if (this.c != null) {
            try {
                this.c.b(this.a.readBoolean());
            } catch (IOException unused) {
            }
        }
    }

    private void h() {
        int i;
        try {
            if (this.c != null) {
                byte[] bArr = new byte[this.a.readInt()];
                this.a.read(bArr);
                byte[] bArr2 = new byte[this.a.readInt()];
                this.a.read(bArr2);
                boolean z = false;
                try {
                    i = this.a.readInt();
                } catch (Exception unused) {
                    i = 0;
                }
                if (i == 1) {
                    z = true;
                }
                this.c.a(new String(bArr, "UTF-8"), new String(bArr2, "UTF-8"), z);
            }
        } catch (UnsupportedEncodingException unused2) {
        } catch (IOException unused3) {
        }
    }

    private void i() {
        if (this.c != null) {
            try {
                this.c.a(this.a.read(), this.a.readBoolean());
            } catch (IOException unused) {
            }
        }
    }

    private void j() {
        if (this.c != null) {
            byte[] bArr = new byte[((this.b.length - 12) - 4)];
            System.arraycopy(this.b, 12, bArr, 0, bArr.length);
            this.c.b(bArr);
        }
    }

    private void k() {
        if (this.c != null) {
            byte[] bArr = new byte[((this.b.length - 12) - 4)];
            System.arraycopy(this.b, 12, bArr, 0, bArr.length);
            this.c.a(bArr);
        }
    }
}
