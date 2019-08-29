package defpackage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* renamed from: epn reason: default package */
/* compiled from: ButterworthFilter */
public class epn {
    private static final String a = "epn";
    private static Map<String, epn> b = new HashMap();
    private double[][] c = new double[3][];
    private double[][] d = new double[3][];
    private float[] e = new float[3];

    private epn() {
        for (int i = 0; i < this.c.length; i++) {
            this.c[i] = new double[6];
            Arrays.fill(this.c[i], 0.0d);
        }
        for (int i2 = 0; i2 < this.d.length; i2++) {
            this.d[i2] = new double[6];
            Arrays.fill(this.c[i2], 0.0d);
        }
    }

    public static epn a(int i) {
        String concat = "sensor".concat(String.valueOf(i));
        epn epn = b.get(concat);
        if (epn != null) {
            return epn;
        }
        epn epn2 = new epn();
        b.put(concat, epn2);
        return epn2;
    }

    public final float[] a(float[] fArr) {
        float[] fArr2 = fArr;
        Arrays.fill(this.e, 0.0f);
        for (int i = 0; i < fArr2.length; i++) {
            float[] fArr3 = this.e;
            float f = fArr2[i];
            double[] dArr = this.c[i];
            double[] dArr2 = this.d[i];
            int i2 = 0;
            while (i2 < 5) {
                int i3 = i2 + 1;
                dArr[i2] = dArr[i3];
                dArr2[i2] = dArr2[i3];
                i2 = i3;
            }
            dArr[5] = (double) f;
            double d2 = 0.0d;
            dArr2[5] = 0.0d;
            double d3 = 0.0d;
            for (int i4 = 0; i4 < 6; i4++) {
                int i5 = (6 - i4) - 1;
                d2 += dArr[i4] * epo.b()[i5];
                d3 += dArr2[i4] * epo.a()[i5];
            }
            dArr2[5] = (double) ((float) (d2 - d3));
            fArr3[i] = (float) dArr2[5];
        }
        return this.e;
    }

    public static void a() {
        b.clear();
    }
}
