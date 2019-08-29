package android.support.constraint.solver;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class ArrayRow implements Row {
    SolverVariable a = null;
    public float b = 0.0f;
    boolean c = false;
    public final ArrayLinkedVariables d;
    public boolean e = false;

    public ArrayRow(Cache cache) {
        this.d = new ArrayLinkedVariables(this, cache);
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(SolverVariable solverVariable) {
        ArrayLinkedVariables arrayLinkedVariables = this.d;
        if (arrayLinkedVariables.g != -1) {
            int i = arrayLinkedVariables.g;
            int i2 = 0;
            while (i != -1 && i2 < arrayLinkedVariables.a) {
                if (arrayLinkedVariables.d[i] == solverVariable.b) {
                    return true;
                }
                i = arrayLinkedVariables.e[i];
                i2++;
            }
        }
        return false;
    }

    public final ArrayRow a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = (float) i;
        }
        if (!z) {
            this.d.a(solverVariable, -1.0f);
            this.d.a(solverVariable2, 1.0f);
            this.d.a(solverVariable3, 1.0f);
        } else {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
            this.d.a(solverVariable3, -1.0f);
        }
        return this;
    }

    public final ArrayRow b(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i) {
        boolean z = false;
        if (i != 0) {
            if (i < 0) {
                i *= -1;
                z = true;
            }
            this.b = (float) i;
        }
        if (!z) {
            this.d.a(solverVariable, -1.0f);
            this.d.a(solverVariable2, 1.0f);
            this.d.a(solverVariable3, -1.0f);
        } else {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
            this.d.a(solverVariable3, 1.0f);
        }
        return this;
    }

    public final ArrayRow a(LinearSystem linearSystem, int i) {
        this.d.a(linearSystem.a(i), 1.0f);
        this.d.a(linearSystem.a(i), -1.0f);
        return this;
    }

    public final ArrayRow a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.d.a(solverVariable, -1.0f);
        this.d.a(solverVariable2, 1.0f);
        this.d.a(solverVariable3, f);
        this.d.a(solverVariable4, -f);
        return this;
    }

    public final ArrayRow b(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f) {
        this.d.a(solverVariable3, 0.5f);
        this.d.a(solverVariable4, 0.5f);
        this.d.a(solverVariable, -0.5f);
        this.d.a(solverVariable2, -0.5f);
        this.b = -f;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final void b(SolverVariable solverVariable) {
        if (this.a != null) {
            this.d.a(this.a, -1.0f);
            this.a = null;
        }
        float a2 = this.d.a(solverVariable, true) * -1.0f;
        this.a = solverVariable;
        if (a2 != 1.0f) {
            this.b /= a2;
            ArrayLinkedVariables arrayLinkedVariables = this.d;
            int i = arrayLinkedVariables.g;
            int i2 = 0;
            while (i != -1 && i2 < arrayLinkedVariables.a) {
                float[] fArr = arrayLinkedVariables.f;
                fArr[i] = fArr[i] / a2;
                i = arrayLinkedVariables.e[i];
                i2++;
            }
        }
    }

    public final SolverVariable a(boolean[] zArr) {
        return this.d.a(zArr, (SolverVariable) null);
    }

    public final void a() {
        this.d.a();
        this.a = null;
        this.b = 0.0f;
    }

    public final void a(Row row) {
        if (row instanceof ArrayRow) {
            ArrayRow arrayRow = (ArrayRow) row;
            this.a = null;
            this.d.a();
            for (int i = 0; i < arrayRow.d.a; i++) {
                this.d.a(arrayRow.d.a(i), arrayRow.d.b(i), true);
            }
        }
    }

    public void c(SolverVariable solverVariable) {
        float f = 1.0f;
        if (solverVariable.d != 1) {
            if (solverVariable.d == 2) {
                f = 1000.0f;
            } else if (solverVariable.d == 3) {
                f = 1000000.0f;
            } else if (solverVariable.d == 4) {
                f = 1.0E9f;
            } else if (solverVariable.d == 5) {
                f = 1.0E12f;
            }
        }
        this.d.a(solverVariable, f);
    }

    public final SolverVariable b() {
        return this.a;
    }

    public String toString() {
        String str;
        String str2;
        boolean z;
        String str3;
        if (this.a == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append("0");
            str = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(this.a);
            str = sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(" = ");
        String sb4 = sb3.toString();
        if (this.b != 0.0f) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append(this.b);
            str2 = sb5.toString();
            z = true;
        } else {
            str2 = sb4;
            z = false;
        }
        int i = this.d.a;
        for (int i2 = 0; i2 < i; i2++) {
            SolverVariable a2 = this.d.a(i2);
            if (a2 != null) {
                float b2 = this.d.b(i2);
                int i3 = (b2 > 0.0f ? 1 : (b2 == 0.0f ? 0 : -1));
                if (i3 != 0) {
                    String solverVariable = a2.toString();
                    if (!z) {
                        if (b2 < 0.0f) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str2);
                            sb6.append("- ");
                            str2 = sb6.toString();
                            b2 *= -1.0f;
                        }
                    } else if (i3 > 0) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(str2);
                        sb7.append(" + ");
                        str2 = sb7.toString();
                    } else {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(str2);
                        sb8.append(" - ");
                        str2 = sb8.toString();
                        b2 *= -1.0f;
                    }
                    if (b2 == 1.0f) {
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append(str2);
                        sb9.append(solverVariable);
                        str3 = sb9.toString();
                    } else {
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append(str2);
                        sb10.append(b2);
                        sb10.append(Token.SEPARATOR);
                        sb10.append(solverVariable);
                        str3 = sb10.toString();
                    }
                    str2 = str3;
                    z = true;
                }
            }
        }
        if (z) {
            return str2;
        }
        StringBuilder sb11 = new StringBuilder();
        sb11.append(str2);
        sb11.append("0.0");
        return sb11.toString();
    }
}
