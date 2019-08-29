package android.support.constraint.solver.widgets;

import java.util.Arrays;

public class Helper extends ConstraintWidget {
    protected ConstraintWidget[] as = new ConstraintWidget[4];
    protected int at = 0;

    public final void a(ConstraintWidget constraintWidget) {
        if (this.at + 1 > this.as.length) {
            this.as = (ConstraintWidget[]) Arrays.copyOf(this.as, this.as.length * 2);
        }
        this.as[this.at] = constraintWidget;
        this.at++;
    }

    public final void d() {
        this.at = 0;
    }
}
