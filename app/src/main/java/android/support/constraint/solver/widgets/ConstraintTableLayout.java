package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;

public class ConstraintTableLayout extends ConstraintWidgetContainer {
    private boolean aM = true;
    private int aN = 0;
    private int aO = 0;
    private int aP = 8;
    private ArrayList<VerticalSlice> aQ = new ArrayList<>();
    private ArrayList<HorizontalSlice> aR = new ArrayList<>();
    private ArrayList<Guideline> aS = new ArrayList<>();
    private ArrayList<Guideline> aT = new ArrayList<>();
    private LinearSystem aU = null;

    class HorizontalSlice {
        ConstraintWidget a;
        ConstraintWidget b;

        HorizontalSlice() {
        }
    }

    class VerticalSlice {
        ConstraintWidget a;
        ConstraintWidget b;
        int c = 1;

        VerticalSlice() {
        }
    }

    public final boolean d() {
        return true;
    }

    private void G() {
        if (this.aM && this.aN != 1) {
            this.aN = 1;
            K();
            I();
        }
    }

    private void H() {
        if (!this.aM && this.aN != 1) {
            this.aO = 1;
            L();
            I();
        }
    }

    public final void a(LinearSystem linearSystem) {
        super.a(linearSystem);
        int size = this.aL.size();
        if (size != 0) {
            I();
            if (linearSystem == this.b) {
                int size2 = this.aS.size();
                int i = 0;
                while (true) {
                    boolean z = true;
                    if (i >= size2) {
                        break;
                    }
                    Guideline guideline = this.aS.get(i);
                    if (z() != DimensionBehaviour.WRAP_CONTENT) {
                        z = false;
                    }
                    guideline.a(z);
                    guideline.a(linearSystem);
                    i++;
                }
                int size3 = this.aT.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    Guideline guideline2 = this.aT.get(i2);
                    guideline2.a(A() == DimensionBehaviour.WRAP_CONTENT);
                    guideline2.a(linearSystem);
                }
                for (int i3 = 0; i3 < size; i3++) {
                    ((ConstraintWidget) this.aL.get(i3)).a(linearSystem);
                }
            }
        }
    }

    private void I() {
        int size = this.aL.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += ((ConstraintWidget) this.aL.get(i2)).ab;
        }
        int i3 = size + i;
        if (this.aM) {
            if (this.aN == 0) {
                G();
            }
            int i4 = i3 / this.aN;
            if (this.aN * i4 < i3) {
                i4++;
            }
            if (this.aO != i4 || this.aS.size() != this.aN - 1) {
                this.aO = i4;
                L();
            } else {
                return;
            }
        } else {
            if (this.aO == 0) {
                H();
            }
            int i5 = i3 / this.aO;
            if (this.aO * i5 < i3) {
                i5++;
            }
            if (this.aN != i5 || this.aT.size() != this.aO - 1) {
                this.aN = i5;
                K();
            } else {
                return;
            }
        }
        M();
    }

    public final void a(LinearSystem linearSystem, String str) {
        this.aU = linearSystem;
        super.a(linearSystem, str);
        J();
    }

    private void J() {
        if (this.aU != null) {
            int size = this.aS.size();
            for (int i = 0; i < size; i++) {
                LinearSystem linearSystem = this.aU;
                StringBuilder sb = new StringBuilder();
                sb.append(this.ad);
                sb.append(".VG");
                sb.append(i);
                this.aS.get(i).a(linearSystem, sb.toString());
            }
            int size2 = this.aT.size();
            for (int i2 = 0; i2 < size2; i2++) {
                LinearSystem linearSystem2 = this.aU;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.ad);
                sb2.append(".HG");
                sb2.append(i2);
                this.aT.get(i2).a(linearSystem2, sb2.toString());
            }
        }
    }

    private void K() {
        this.aQ.clear();
        float f = 100.0f / ((float) this.aN);
        int i = 0;
        ConstraintWidget constraintWidget = this;
        float f2 = f;
        while (i < this.aN) {
            VerticalSlice verticalSlice = new VerticalSlice();
            verticalSlice.a = constraintWidget;
            if (i < this.aN - 1) {
                Guideline guideline = new Guideline();
                guideline.j(1);
                guideline.H = this;
                guideline.k((int) f2);
                f2 += f;
                verticalSlice.b = guideline;
                this.aS.add(guideline);
            } else {
                verticalSlice.b = this;
            }
            ConstraintWidget constraintWidget2 = verticalSlice.b;
            this.aQ.add(verticalSlice);
            i++;
            constraintWidget = constraintWidget2;
        }
        J();
    }

    private void L() {
        this.aR.clear();
        float f = 100.0f / ((float) this.aO);
        ConstraintWidget constraintWidget = this;
        float f2 = f;
        int i = 0;
        while (i < this.aO) {
            HorizontalSlice horizontalSlice = new HorizontalSlice();
            horizontalSlice.a = constraintWidget;
            if (i < this.aO - 1) {
                Guideline guideline = new Guideline();
                guideline.j(0);
                guideline.H = this;
                guideline.k((int) f2);
                f2 += f;
                horizontalSlice.b = guideline;
                this.aT.add(guideline);
            } else {
                horizontalSlice.b = this;
            }
            ConstraintWidget constraintWidget2 = horizontalSlice.b;
            this.aR.add(horizontalSlice);
            i++;
            constraintWidget = constraintWidget2;
        }
        J();
    }

    private void M() {
        int size = this.aL.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.aL.get(i2);
            int i3 = i + constraintWidget.ab;
            HorizontalSlice horizontalSlice = this.aR.get(i3 / this.aN);
            VerticalSlice verticalSlice = this.aQ.get(i3 % this.aN);
            ConstraintWidget constraintWidget2 = verticalSlice.a;
            ConstraintWidget constraintWidget3 = verticalSlice.b;
            ConstraintWidget constraintWidget4 = horizontalSlice.a;
            ConstraintWidget constraintWidget5 = horizontalSlice.b;
            constraintWidget.a(Type.LEFT).b(constraintWidget2.a(Type.LEFT), this.aP);
            if (constraintWidget3 instanceof Guideline) {
                constraintWidget.a(Type.RIGHT).b(constraintWidget3.a(Type.LEFT), this.aP);
            } else {
                constraintWidget.a(Type.RIGHT).b(constraintWidget3.a(Type.RIGHT), this.aP);
            }
            switch (verticalSlice.c) {
                case 1:
                    constraintWidget.a(Type.LEFT).a(Strength.STRONG);
                    constraintWidget.a(Type.RIGHT).a(Strength.WEAK);
                    break;
                case 2:
                    constraintWidget.a(Type.LEFT).a(Strength.WEAK);
                    constraintWidget.a(Type.RIGHT).a(Strength.STRONG);
                    break;
                case 3:
                    constraintWidget.a(DimensionBehaviour.MATCH_CONSTRAINT);
                    break;
            }
            constraintWidget.a(Type.TOP).b(constraintWidget4.a(Type.TOP), this.aP);
            if (constraintWidget5 instanceof Guideline) {
                constraintWidget.a(Type.BOTTOM).b(constraintWidget5.a(Type.TOP), this.aP);
            } else {
                constraintWidget.a(Type.BOTTOM).b(constraintWidget5.a(Type.BOTTOM), this.aP);
            }
            i = i3 + 1;
        }
    }

    public final void b(LinearSystem linearSystem) {
        super.b(linearSystem);
        if (linearSystem == this.b) {
            int size = this.aS.size();
            for (int i = 0; i < size; i++) {
                this.aS.get(i).b(linearSystem);
            }
            int size2 = this.aT.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.aT.get(i2).b(linearSystem);
            }
        }
    }
}
