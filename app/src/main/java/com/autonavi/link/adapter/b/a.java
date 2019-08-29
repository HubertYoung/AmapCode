package com.autonavi.link.adapter.b;

import com.autonavi.link.adapter.a.b;

/* compiled from: DisassembleDataManager */
public class a {
    private b a;

    public void a(b bVar) {
        this.a = bVar;
    }

    public void a(byte[] bArr, int i) {
        if (this.a != null) {
            switch (com.autonavi.link.utils.a.a(bArr, 6)) {
                case 34:
                    a();
                    return;
                case 35:
                    a(bArr);
                    return;
                case 36:
                    c(bArr);
                    return;
                case 38:
                    d(bArr);
                    return;
                case 42:
                    e(bArr);
                    return;
                case 45:
                    c(bArr, i);
                    break;
                case 46:
                    b(bArr, i);
                    return;
                case 51:
                    f(bArr);
                    return;
                case 52:
                    g(bArr);
                    return;
                case 54:
                    b(bArr);
                    return;
            }
        }
    }

    private void a(byte[] bArr) {
        if (this.a != null) {
            try {
                this.a.a(com.autonavi.link.utils.a.b(bArr, 8), com.autonavi.link.utils.a.b(bArr, 12));
            } catch (Exception unused) {
            }
        }
    }

    private void a() {
        if (this.a != null) {
            try {
                this.a.f();
            } catch (Exception unused) {
            }
        }
    }

    private void b(byte[] bArr) {
        if (this.a != null) {
            try {
                this.a.a(com.autonavi.link.utils.a.b(bArr, 8));
            } catch (Exception unused) {
            }
        }
    }

    private void c(byte[] bArr) {
        if (this.a != null) {
            try {
                boolean z = false;
                boolean z2 = bArr[8] != 0;
                int b = com.autonavi.link.utils.a.b(bArr, 9);
                if (bArr[13] != 0) {
                    z = true;
                }
                this.a.a(z2, b, z);
            } catch (Exception unused) {
            }
        }
    }

    private void d(byte[] bArr) {
        if (this.a != null) {
            try {
                this.a.a(com.autonavi.link.utils.a.b(bArr, 8), bArr[12] != 0);
            } catch (Exception unused) {
            }
        }
    }

    private void e(byte[] bArr) {
        if (this.a != null) {
            try {
                this.a.a(bArr[8] != 0);
            } catch (Exception unused) {
            }
        }
    }

    private void f(byte[] bArr) {
        if (this.a != null) {
            try {
                this.a.b(bArr[8] != 0);
            } catch (Exception unused) {
            }
        }
    }

    private void g(byte[] bArr) {
        try {
            if (this.a != null) {
                int b = com.autonavi.link.utils.a.b(bArr, 8);
                byte[] bArr2 = new byte[b];
                System.arraycopy(bArr2, 0, bArr, 12, b);
                int i = b + 12;
                int b2 = com.autonavi.link.utils.a.b(bArr, i);
                byte[] bArr3 = new byte[b2];
                int i2 = i + 4;
                System.arraycopy(bArr3, 0, bArr, i2, b2);
                int b3 = com.autonavi.link.utils.a.b(bArr, i2 + b2);
                boolean z = true;
                if (b3 != 1) {
                    z = false;
                }
                this.a.a(new String(bArr2), new String(bArr3), z);
            }
        } catch (Exception unused) {
        }
    }

    private void b(byte[] bArr, int i) {
        if (this.a != null) {
            int i2 = (i - 12) - 4;
            try {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, 12, bArr2, 0, i2);
                this.a.a(bArr2);
            } catch (Exception unused) {
            }
        }
    }

    private void c(byte[] bArr, int i) {
        if (this.a != null) {
            int i2 = (i - 12) - 4;
            try {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, 12, bArr2, 0, i2);
                this.a.b(bArr2);
            } catch (Exception unused) {
            }
        }
    }
}
