package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable.Type;
import java.util.Arrays;

public class ArrayLinkedVariables {
    int a = 0;
    final ArrayRow b;
    final Cache c;
    int[] d = new int[this.h];
    int[] e = new int[this.h];
    float[] f = new float[this.h];
    int g = -1;
    private int h = 8;
    private SolverVariable i = null;
    private int j = -1;
    private boolean k = false;

    ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.b = arrayRow;
        this.c = cache;
    }

    public final void a(SolverVariable solverVariable, float f2) {
        if (f2 == 0.0f) {
            a(solverVariable, true);
        } else if (this.g == -1) {
            this.g = 0;
            this.f[this.g] = f2;
            this.d[this.g] = solverVariable.b;
            this.e[this.g] = -1;
            solverVariable.j++;
            solverVariable.a(this.b);
            this.a++;
            if (!this.k) {
                this.j++;
                if (this.j >= this.d.length) {
                    this.k = true;
                    this.j = this.d.length - 1;
                }
            }
        } else {
            int i2 = this.g;
            int i3 = 0;
            int i4 = -1;
            while (i2 != -1 && i3 < this.a) {
                if (this.d[i2] == solverVariable.b) {
                    this.f[i2] = f2;
                    return;
                }
                if (this.d[i2] < solverVariable.b) {
                    i4 = i2;
                }
                i2 = this.e[i2];
                i3++;
            }
            int i5 = this.j + 1;
            if (this.k) {
                if (this.d[this.j] == -1) {
                    i5 = this.j;
                } else {
                    i5 = this.d.length;
                }
            }
            if (i5 >= this.d.length && this.a < this.d.length) {
                int i6 = 0;
                while (true) {
                    if (i6 >= this.d.length) {
                        break;
                    } else if (this.d[i6] == -1) {
                        i5 = i6;
                        break;
                    } else {
                        i6++;
                    }
                }
            }
            if (i5 >= this.d.length) {
                i5 = this.d.length;
                this.h *= 2;
                this.k = false;
                this.j = i5 - 1;
                this.f = Arrays.copyOf(this.f, this.h);
                this.d = Arrays.copyOf(this.d, this.h);
                this.e = Arrays.copyOf(this.e, this.h);
            }
            this.d[i5] = solverVariable.b;
            this.f[i5] = f2;
            if (i4 != -1) {
                int[] iArr = this.e;
                iArr[i5] = iArr[i4];
                this.e[i4] = i5;
            } else {
                this.e[i5] = this.g;
                this.g = i5;
            }
            solverVariable.j++;
            solverVariable.a(this.b);
            this.a++;
            if (!this.k) {
                this.j++;
            }
            if (this.a >= this.d.length) {
                this.k = true;
            }
            if (this.j >= this.d.length) {
                this.k = true;
                this.j = this.d.length - 1;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(SolverVariable solverVariable, float f2, boolean z) {
        if (f2 != 0.0f) {
            if (this.g == -1) {
                this.g = 0;
                this.f[this.g] = f2;
                this.d[this.g] = solverVariable.b;
                this.e[this.g] = -1;
                solverVariable.j++;
                solverVariable.a(this.b);
                this.a++;
                if (!this.k) {
                    this.j++;
                    if (this.j >= this.d.length) {
                        this.k = true;
                        this.j = this.d.length - 1;
                    }
                }
                return;
            }
            int i2 = this.g;
            int i3 = 0;
            int i4 = -1;
            while (i2 != -1 && i3 < this.a) {
                if (this.d[i2] == solverVariable.b) {
                    float[] fArr = this.f;
                    fArr[i2] = fArr[i2] + f2;
                    if (this.f[i2] == 0.0f) {
                        if (i2 == this.g) {
                            this.g = this.e[i2];
                        } else {
                            int[] iArr = this.e;
                            iArr[i4] = iArr[i2];
                        }
                        if (z) {
                            solverVariable.b(this.b);
                        }
                        if (this.k) {
                            this.j = i2;
                        }
                        solverVariable.j--;
                        this.a--;
                    }
                    return;
                }
                if (this.d[i2] < solverVariable.b) {
                    i4 = i2;
                }
                i2 = this.e[i2];
                i3++;
            }
            int i5 = this.j + 1;
            if (this.k) {
                if (this.d[this.j] == -1) {
                    i5 = this.j;
                } else {
                    i5 = this.d.length;
                }
            }
            if (i5 >= this.d.length && this.a < this.d.length) {
                int i6 = 0;
                while (true) {
                    if (i6 >= this.d.length) {
                        break;
                    } else if (this.d[i6] == -1) {
                        i5 = i6;
                        break;
                    } else {
                        i6++;
                    }
                }
            }
            if (i5 >= this.d.length) {
                i5 = this.d.length;
                this.h *= 2;
                this.k = false;
                this.j = i5 - 1;
                this.f = Arrays.copyOf(this.f, this.h);
                this.d = Arrays.copyOf(this.d, this.h);
                this.e = Arrays.copyOf(this.e, this.h);
            }
            this.d[i5] = solverVariable.b;
            this.f[i5] = f2;
            if (i4 != -1) {
                int[] iArr2 = this.e;
                iArr2[i5] = iArr2[i4];
                this.e[i4] = i5;
            } else {
                this.e[i5] = this.g;
                this.g = i5;
            }
            solverVariable.j++;
            solverVariable.a(this.b);
            this.a++;
            if (!this.k) {
                this.j++;
            }
            if (this.j >= this.d.length) {
                this.k = true;
                this.j = this.d.length - 1;
            }
        }
    }

    public final float a(SolverVariable solverVariable, boolean z) {
        if (this.i == solverVariable) {
            this.i = null;
        }
        if (this.g == -1) {
            return 0.0f;
        }
        int i2 = this.g;
        int i3 = 0;
        int i4 = -1;
        while (i2 != -1 && i3 < this.a) {
            if (this.d[i2] == solverVariable.b) {
                if (i2 == this.g) {
                    this.g = this.e[i2];
                } else {
                    int[] iArr = this.e;
                    iArr[i4] = iArr[i2];
                }
                if (z) {
                    solverVariable.b(this.b);
                }
                solverVariable.j--;
                this.a--;
                this.d[i2] = -1;
                if (this.k) {
                    this.j = i2;
                }
                return this.f[i2];
            }
            i3++;
            i4 = i2;
            i2 = this.e[i2];
        }
        return 0.0f;
    }

    public final void a() {
        int i2 = this.g;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            SolverVariable solverVariable = this.c.c[this.d[i2]];
            if (solverVariable != null) {
                solverVariable.b(this.b);
            }
            i2 = this.e[i2];
            i3++;
        }
        this.g = -1;
        this.j = -1;
        this.k = false;
        this.a = 0;
    }

    static boolean a(SolverVariable solverVariable) {
        return solverVariable.j <= 1;
    }

    /* access modifiers changed from: 0000 */
    public final void a(ArrayRow arrayRow, ArrayRow arrayRow2) {
        int i2 = this.g;
        while (true) {
            int i3 = 0;
            while (i2 != -1 && i3 < this.a) {
                if (this.d[i2] == arrayRow2.a.b) {
                    float f2 = this.f[i2];
                    a(arrayRow2.a, false);
                    ArrayLinkedVariables arrayLinkedVariables = arrayRow2.d;
                    int i4 = arrayLinkedVariables.g;
                    int i5 = 0;
                    while (i4 != -1 && i5 < arrayLinkedVariables.a) {
                        a(this.c.c[arrayLinkedVariables.d[i4]], arrayLinkedVariables.f[i4] * f2, false);
                        i4 = arrayLinkedVariables.e[i4];
                        i5++;
                    }
                    arrayRow.b += arrayRow2.b * f2;
                    i2 = this.g;
                } else {
                    i2 = this.e[i2];
                    i3++;
                }
            }
            return;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(ArrayRow arrayRow, ArrayRow[] arrayRowArr) {
        int i2 = this.g;
        while (true) {
            int i3 = 0;
            while (i2 != -1 && i3 < this.a) {
                SolverVariable solverVariable = this.c.c[this.d[i2]];
                if (solverVariable.c != -1) {
                    float f2 = this.f[i2];
                    a(solverVariable, true);
                    ArrayRow arrayRow2 = arrayRowArr[solverVariable.c];
                    if (!arrayRow2.e) {
                        ArrayLinkedVariables arrayLinkedVariables = arrayRow2.d;
                        int i4 = arrayLinkedVariables.g;
                        int i5 = 0;
                        while (i4 != -1 && i5 < arrayLinkedVariables.a) {
                            a(this.c.c[arrayLinkedVariables.d[i4]], arrayLinkedVariables.f[i4] * f2, true);
                            i4 = arrayLinkedVariables.e[i4];
                            i5++;
                        }
                    }
                    arrayRow.b += arrayRow2.b * f2;
                    arrayRow2.a.b(arrayRow);
                    i2 = this.g;
                } else {
                    i2 = this.e[i2];
                    i3++;
                }
            }
            return;
        }
    }

    /* access modifiers changed from: 0000 */
    public final SolverVariable a(boolean[] zArr, SolverVariable solverVariable) {
        int i2 = this.g;
        int i3 = 0;
        SolverVariable solverVariable2 = null;
        float f2 = 0.0f;
        while (i2 != -1 && i3 < this.a) {
            if (this.f[i2] < 0.0f) {
                SolverVariable solverVariable3 = this.c.c[this.d[i2]];
                if ((zArr == null || !zArr[solverVariable3.b]) && solverVariable3 != solverVariable && (solverVariable3.g == Type.SLACK || solverVariable3.g == Type.ERROR)) {
                    float f3 = this.f[i2];
                    if (f3 < f2) {
                        solverVariable2 = solverVariable3;
                        f2 = f3;
                    }
                }
            }
            i2 = this.e[i2];
            i3++;
        }
        return solverVariable2;
    }

    /* access modifiers changed from: 0000 */
    public final SolverVariable a(int i2) {
        int i3 = this.g;
        int i4 = 0;
        while (i3 != -1 && i4 < this.a) {
            if (i4 == i2) {
                return this.c.c[this.d[i3]];
            }
            i3 = this.e[i3];
            i4++;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final float b(int i2) {
        int i3 = this.g;
        int i4 = 0;
        while (i3 != -1 && i4 < this.a) {
            if (i4 == i2) {
                return this.f[i3];
            }
            i3 = this.e[i3];
            i4++;
        }
        return 0.0f;
    }

    public final float b(SolverVariable solverVariable) {
        int i2 = this.g;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            if (this.d[i2] == solverVariable.b) {
                return this.f[i2];
            }
            i2 = this.e[i2];
            i3++;
        }
        return 0.0f;
    }

    public String toString() {
        String str = "";
        int i2 = this.g;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" -> ");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(this.f[i2]);
            sb3.append(" : ");
            String sb4 = sb3.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(this.c.c[this.d[i2]]);
            str = sb5.toString();
            i2 = this.e[i2];
            i3++;
        }
        return str;
    }
}
