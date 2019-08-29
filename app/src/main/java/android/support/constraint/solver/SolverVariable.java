package android.support.constraint.solver;

import java.util.Arrays;

public class SolverVariable {
    private static int k = 1;
    private static int l = 1;
    private static int m = 1;
    private static int n = 1;
    private static int o = 1;
    public String a;
    public int b = -1;
    int c = -1;
    public int d = 0;
    public float e;
    float[] f = new float[7];
    Type g;
    ArrayRow[] h = new ArrayRow[8];
    int i = 0;
    public int j = 0;

    /* renamed from: android.support.constraint.solver.SolverVariable$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                android.support.constraint.solver.SolverVariable$Type[] r0 = android.support.constraint.solver.SolverVariable.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                android.support.constraint.solver.SolverVariable$Type r1 = android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                android.support.constraint.solver.SolverVariable$Type r1 = android.support.constraint.solver.SolverVariable.Type.CONSTANT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                android.support.constraint.solver.SolverVariable$Type r1 = android.support.constraint.solver.SolverVariable.Type.SLACK     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                android.support.constraint.solver.SolverVariable$Type r1 = android.support.constraint.solver.SolverVariable.Type.ERROR     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                android.support.constraint.solver.SolverVariable$Type r1 = android.support.constraint.solver.SolverVariable.Type.UNKNOWN     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.SolverVariable.AnonymousClass1.<clinit>():void");
        }
    }

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    static void a() {
        l++;
    }

    public SolverVariable(Type type) {
        this.g = type;
    }

    public final void a(ArrayRow arrayRow) {
        int i2 = 0;
        while (i2 < this.i) {
            if (this.h[i2] != arrayRow) {
                i2++;
            } else {
                return;
            }
        }
        if (this.i >= this.h.length) {
            this.h = (ArrayRow[]) Arrays.copyOf(this.h, this.h.length * 2);
        }
        this.h[this.i] = arrayRow;
        this.i++;
    }

    public final void b(ArrayRow arrayRow) {
        int i2 = this.i;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.h[i3] == arrayRow) {
                for (int i4 = 0; i4 < (i2 - i3) - 1; i4++) {
                    int i5 = i3 + i4;
                    this.h[i5] = this.h[i5 + 1];
                }
                this.i--;
                return;
            }
        }
    }

    public final void c(ArrayRow arrayRow) {
        int i2 = this.i;
        for (int i3 = 0; i3 < i2; i3++) {
            this.h[i3].d.a(this.h[i3], arrayRow);
        }
        this.i = 0;
    }

    public final void b() {
        this.a = null;
        this.g = Type.UNKNOWN;
        this.d = 0;
        this.b = -1;
        this.c = -1;
        this.e = 0.0f;
        this.i = 0;
        this.j = 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.a);
        return sb.toString();
    }
}
