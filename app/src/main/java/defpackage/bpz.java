package defpackage;

import com.alibaba.wireless.security.SecExceptionCode;

/* renamed from: bpz reason: default package */
/* compiled from: ParallelThreadPoolStatus */
final class bpz {
    int[] a;
    int[] b;

    public bpz(int[] iArr) {
        if (iArr == null || iArr.length != 5) {
            throw new IllegalArgumentException("parallelNumber length must be 5");
        }
        this.b = iArr;
        this.a = new int[5];
        for (int i = 0; i < iArr.length; i++) {
            this.a[i] = 0;
        }
    }

    public final int a() {
        int i = 0;
        for (int i2 : this.b) {
            i += i2;
        }
        return i;
    }

    public final void a(int i) {
        int i2 = i / 100;
        int[] iArr = this.a;
        iArr[i2] = iArr[i2] + 1;
    }

    public static boolean a(c cVar, int[] iArr, int[] iArr2, String str) {
        boolean z;
        if (cVar.b >= 500) {
            cVar.b = SecExceptionCode.SEC_ERROR_DYN_ENC_UNKNOWN_ERROR;
        } else if (cVar.b < 0) {
            cVar.b = 0;
        }
        int i = cVar.b;
        int i2 = i / 100;
        int i3 = i % 100;
        String str2 = "";
        int i4 = i2;
        while (true) {
            if (i4 < 0) {
                z = false;
                break;
            } else if (iArr2[i4] < iArr[i4]) {
                z = true;
                break;
            } else {
                i4--;
            }
        }
        if (z && i4 != i2) {
            cVar.b = (i4 * 100) + i3;
            StringBuilder sb = new StringBuilder(",fix prior:");
            sb.append(cVar.b);
            str2 = sb.toString();
        }
        String str3 = "";
        String str4 = "";
        for (int i5 = 0; i5 < iArr.length; i5++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str4);
            sb2.append(iArr[i5]);
            sb2.append(",");
            str4 = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str3);
            sb3.append(iArr2[i5]);
            sb3.append(",");
            str3 = sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append("task:");
        sb4.append(cVar.a);
        sb4.append(",isPoolFull:");
        sb4.append(!z);
        sb4.append(",prior:");
        sb4.append(i);
        sb4.append(str2);
        sb4.append(",max:");
        sb4.append(str4);
        sb4.append("now:");
        sb4.append(str3);
        bpv.b("ANet-ThreadPool", sb4.toString());
        if (!z) {
            return true;
        }
        return false;
    }
}
