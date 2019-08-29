package com.google.protobuf.micro;

import com.autonavi.map.core.MapCustomizeManager;
import java.io.InputStream;
import java.util.Vector;

public final class b {
    private final byte[] a;
    private int b;
    private int c;
    private int d;
    private final InputStream e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;

    private b(InputStream inputStream) {
        this.h = Integer.MAX_VALUE;
        this.j = 64;
        this.k = MapCustomizeManager.VIEW_SEARCH_ALONG;
        this.a = new byte[4096];
        this.b = 0;
        this.d = 0;
        this.e = inputStream;
    }

    private b(byte[] bArr, int i2, int i3) {
        this.h = Integer.MAX_VALUE;
        this.j = 64;
        this.k = MapCustomizeManager.VIEW_SEARCH_ALONG;
        this.a = bArr;
        this.b = i3 + i2;
        this.d = i2;
        this.e = null;
    }

    public static b a(InputStream inputStream) {
        return new b(inputStream);
    }

    public static b a(byte[] bArr, int i2, int i3) {
        return new b(bArr, i2, i3);
    }

    private boolean a(boolean z) {
        if (this.d < this.b) {
            throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
        } else if (this.g + this.b != this.h) {
            this.g += this.b;
            this.d = 0;
            this.b = this.e == null ? -1 : this.e.read(this.a);
            if (this.b == 0 || this.b < -1) {
                StringBuilder sb = new StringBuilder("InputStream#read(byte[]) returned invalid result: ");
                sb.append(this.b);
                sb.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(sb.toString());
            } else if (this.b == -1) {
                this.b = 0;
                if (!z) {
                    return false;
                }
                throw d.a();
            } else {
                p();
                int i2 = this.g + this.b + this.c;
                if (i2 <= this.k && i2 >= 0) {
                    return true;
                }
                throw d.h();
            }
        } else if (!z) {
            return false;
        } else {
            throw d.a();
        }
    }

    private void p() {
        this.b += this.c;
        int i2 = this.g + this.b;
        if (i2 > this.h) {
            this.c = i2 - this.h;
            this.b -= this.c;
            return;
        }
        this.c = 0;
    }

    public final int a() {
        if (n()) {
            this.f = 0;
            return 0;
        }
        this.f = j();
        if (this.f != 0) {
            return this.f;
        }
        throw d.d();
    }

    public final void a(int i2) {
        if (this.f != i2) {
            throw d.e();
        }
    }

    public final void a(e eVar) {
        int j2 = j();
        if (this.i >= this.j) {
            throw d.g();
        }
        int c2 = c(j2);
        this.i++;
        eVar.a(this);
        a(0);
        this.i--;
        d(c2);
    }

    public final void b() {
        int a2;
        do {
            a2 = a();
            if (a2 == 0) {
                return;
            }
        } while (b(a2));
    }

    public final boolean b(int i2) {
        switch (f.a(i2)) {
            case 0:
                e();
                return true;
            case 1:
                m();
                return true;
            case 2:
                f(j());
                return true;
            case 3:
                b();
                a(f.a(f.b(i2), 4));
                return true;
            case 4:
                return false;
            case 5:
                l();
                return true;
            default:
                throw d.f();
        }
    }

    public final int c(int i2) {
        if (i2 < 0) {
            throw d.b();
        }
        int i3 = i2 + this.g + this.d;
        int i4 = this.h;
        if (i3 > i4) {
            throw d.a();
        }
        this.h = i3;
        p();
        return i4;
    }

    public final long c() {
        return k();
    }

    public final long d() {
        return k();
    }

    public final void d(int i2) {
        this.h = i2;
        p();
    }

    public final int e() {
        return j();
    }

    public final byte[] e(int i2) {
        if (i2 < 0) {
            throw d.b();
        } else if (this.g + this.d + i2 > this.h) {
            f((this.h - this.g) - this.d);
            throw d.a();
        } else if (i2 <= this.b - this.d) {
            byte[] bArr = new byte[i2];
            System.arraycopy(this.a, this.d, bArr, 0, i2);
            this.d += i2;
            return bArr;
        } else if (i2 < 4096) {
            byte[] bArr2 = new byte[i2];
            int i3 = this.b - this.d;
            System.arraycopy(this.a, this.d, bArr2, 0, i3);
            this.d = this.b;
            while (true) {
                a(true);
                int i4 = i2 - i3;
                if (i4 > this.b) {
                    System.arraycopy(this.a, 0, bArr2, i3, this.b);
                    i3 += this.b;
                    this.d = this.b;
                } else {
                    System.arraycopy(this.a, 0, bArr2, i3, i4);
                    this.d = i4;
                    return bArr2;
                }
            }
        } else {
            int i5 = this.d;
            int i6 = this.b;
            this.g += this.b;
            this.d = 0;
            this.b = 0;
            int i7 = i6 - i5;
            int i8 = i2 - i7;
            Vector vector = new Vector();
            while (i8 > 0) {
                byte[] bArr3 = new byte[Math.min(i8, 4096)];
                int i9 = 0;
                while (i9 < bArr3.length) {
                    int read = this.e == null ? -1 : this.e.read(bArr3, i9, bArr3.length - i9);
                    if (read == -1) {
                        throw d.a();
                    }
                    this.g += read;
                    i9 += read;
                }
                i8 -= bArr3.length;
                vector.addElement(bArr3);
            }
            byte[] bArr4 = new byte[i2];
            System.arraycopy(this.a, i5, bArr4, 0, i7);
            for (int i10 = 0; i10 < vector.size(); i10++) {
                byte[] bArr5 = (byte[]) vector.elementAt(i10);
                System.arraycopy(bArr5, 0, bArr4, i7, bArr5.length);
                i7 += bArr5.length;
            }
            return bArr4;
        }
    }

    public final void f(int i2) {
        if (i2 < 0) {
            throw d.b();
        } else if (this.g + this.d + i2 > this.h) {
            f((this.h - this.g) - this.d);
            throw d.a();
        } else if (i2 <= this.b - this.d) {
            this.d += i2;
        } else {
            int i3 = this.b - this.d;
            this.g += this.b;
            this.d = 0;
            this.b = 0;
            while (i3 < i2) {
                int skip = this.e == null ? -1 : (int) this.e.skip((long) (i2 - i3));
                if (skip <= 0) {
                    throw d.a();
                }
                i3 += skip;
                this.g += skip;
            }
        }
    }

    public final boolean f() {
        return j() != 0;
    }

    public final String g() {
        int j2 = j();
        if (j2 > this.b - this.d || j2 <= 0) {
            return new String(e(j2), "UTF-8");
        }
        String str = new String(this.a, this.d, j2, "UTF-8");
        this.d += j2;
        return str;
    }

    public final a h() {
        int j2 = j();
        if (j2 > this.b - this.d || j2 <= 0) {
            return a.a(e(j2));
        }
        a a2 = a.a(this.a, this.d, j2);
        this.d += j2;
        return a2;
    }

    public final int i() {
        return j();
    }

    public final int j() {
        int i2;
        byte o = o();
        if (o >= 0) {
            return o;
        }
        byte b2 = o & Byte.MAX_VALUE;
        byte o2 = o();
        if (o2 >= 0) {
            i2 = o2 << 7;
        } else {
            b2 |= (o2 & Byte.MAX_VALUE) << 7;
            byte o3 = o();
            if (o3 >= 0) {
                i2 = o3 << 14;
            } else {
                b2 |= (o3 & Byte.MAX_VALUE) << 14;
                byte o4 = o();
                if (o4 >= 0) {
                    i2 = o4 << 21;
                } else {
                    byte b3 = b2 | ((o4 & Byte.MAX_VALUE) << 21);
                    byte o5 = o();
                    byte b4 = b3 | (o5 << 28);
                    if (o5 >= 0) {
                        return b4;
                    }
                    for (int i3 = 0; i3 < 5; i3++) {
                        if (o() >= 0) {
                            return b4;
                        }
                    }
                    throw d.c();
                }
            }
        }
        return b2 | i2;
    }

    public final long k() {
        long j2 = 0;
        for (int i2 = 0; i2 < 64; i2 += 7) {
            byte o = o();
            j2 |= ((long) (o & Byte.MAX_VALUE)) << i2;
            if ((o & 128) == 0) {
                return j2;
            }
        }
        throw d.c();
    }

    public final int l() {
        return (o() & 255) | ((o() & 255) << 8) | ((o() & 255) << 16) | ((o() & 255) << 24);
    }

    public final long m() {
        byte o = o();
        byte o2 = o();
        return ((((long) o2) & 255) << 8) | (((long) o) & 255) | ((((long) o()) & 255) << 16) | ((((long) o()) & 255) << 24) | ((((long) o()) & 255) << 32) | ((((long) o()) & 255) << 40) | ((((long) o()) & 255) << 48) | ((((long) o()) & 255) << 56);
    }

    public final boolean n() {
        return this.d == this.b && !a(false);
    }

    public final byte o() {
        if (this.d == this.b) {
            a(true);
        }
        byte[] bArr = this.a;
        int i2 = this.d;
        this.d = i2 + 1;
        return bArr[i2];
    }
}
