package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import java.util.ArrayList;

public class WidgetContainer extends ConstraintWidget {
    protected ArrayList<ConstraintWidget> aL = new ArrayList<>();

    public void g() {
        this.aL.clear();
        super.g();
    }

    public final void a(ConstraintWidget constraintWidget) {
        this.aL.add(constraintWidget);
        if (constraintWidget.H != null) {
            ((WidgetContainer) constraintWidget.H).b(constraintWidget);
        }
        constraintWidget.H = this;
    }

    public final void b(ConstraintWidget constraintWidget) {
        this.aL.remove(constraintWidget);
        constraintWidget.H = null;
    }

    public final void b(int i, int i2) {
        super.b(i, i2);
        int size = this.aL.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.aL.get(i3).b(r(), s());
        }
    }

    public final void x() {
        super.x();
        if (this.aL != null) {
            int size = this.aL.size();
            for (int i = 0; i < size; i++) {
                ConstraintWidget constraintWidget = this.aL.get(i);
                constraintWidget.b(p(), q());
                if (!(constraintWidget instanceof ConstraintWidgetContainer)) {
                    constraintWidget.x();
                }
            }
        }
    }

    public void B() {
        x();
        if (this.aL != null) {
            int size = this.aL.size();
            for (int i = 0; i < size; i++) {
                ConstraintWidget constraintWidget = this.aL.get(i);
                if (constraintWidget instanceof WidgetContainer) {
                    ((WidgetContainer) constraintWidget).B();
                }
            }
        }
    }

    public final void a(Cache cache) {
        super.a(cache);
        int size = this.aL.size();
        for (int i = 0; i < size; i++) {
            this.aL.get(i).a(cache);
        }
    }

    public final void F() {
        this.aL.clear();
    }

    public final ConstraintWidgetContainer E() {
        ConstraintWidget constraintWidget = this.H;
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) this;
        while (constraintWidget != null) {
            ConstraintWidget constraintWidget2 = constraintWidget.H;
            if (constraintWidget instanceof ConstraintWidgetContainer) {
                constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget;
            }
            constraintWidget = constraintWidget2;
        }
        return constraintWidgetContainer;
    }
}
